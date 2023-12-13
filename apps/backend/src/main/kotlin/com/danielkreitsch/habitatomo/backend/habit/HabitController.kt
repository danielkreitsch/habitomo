package com.danielkreitsch.habitatomo.backend.habit

import com.danielkreitsch.habitatomo.backend.day.DayService
import com.danielkreitsch.habitatomo.backend.user.User
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/habits")
class HabitController(private val habitService: HabitService, private val dayService: DayService) {
  private val logger = LoggerFactory.getLogger(javaClass)

  @GetMapping
  fun getHabits(@RequestAttribute user: User): List<Habit> {
    return this.habitService.getHabitsByUser(user)
  }

  @GetMapping("/current")
  fun getCurrentHabit(@RequestAttribute user: User): Habit? {
    return this.habitService
        .getHabitsByUser(user)
        .filter { habit -> !habit.skipped && !habit.done }
        .minByOrNull { habit -> habit.order }
  }

  @GetMapping("/{habitName}")
  fun getHabit(@RequestAttribute user: User, @PathVariable habitName: String): Habit? {
    return this.habitService.getHabitsByUser(user).firstOrNull { habit -> habit.name == habitName }
  }

  @PutMapping("/{habitName}")
  fun updateHabit(
      @RequestAttribute user: User,
      @PathVariable habitName: String,
      @RequestBody habitUpdate: HabitUpdate
  ) {
    val habit =
        this.habitService.getHabitsByUser(user).firstOrNull { h -> h.name == habitName } ?: return
    if (habit.isAutomated) {
      if (habitUpdate.token != "1234Code") {
        this.logger.info(
            "Updating habit ${habit.name} of user ${habit.userId} is not allowed via app")
        return
      }
    } else {
      val previousHabitNotDone =
          this.habitService.getHabitsByUser(user).any { h ->
            !h.skipped && !h.done && h.order < habit.order
          }
      if (previousHabitNotDone) {
        this.logger.info(
            "Updating habit ${habit.name} of user ${habit.userId} is not allowed because previous habit is not done")
        return
      }
    }
    habit.done = habitUpdate.done
    this.habitService.saveHabit(habit)
    this.logger.info(
        "Changed status of habit ${habit.name} of user ${habit.userId} to " +
            (if (habit.done) "done" else "undone"))
  }

  @PostMapping("/{habitName}/skip")
  fun skipHabit(@RequestAttribute user: User, @PathVariable habitName: String) {
    val habit =
        this.habitService.getHabitsByUser(user).firstOrNull { h -> h.name == habitName } ?: return
    habit.skipped = true
    this.habitService.saveHabit(habit)
    this.logger.info("Skipped habit ${habit.name} of user ${habit.userId}")
  }

  @PostMapping("/reset")
  fun resetAllHabits() {
    this.dayService.saveToday(this.habitService.allHabits.filter { habit -> habit.done }.toList())
    for (habit in this.habitService.allHabits) {
      habit.skipped = false
      habit.done = false
      this.habitService.saveHabit(habit)
    }
  }

  data class HabitUpdate(val done: Boolean, val token: String?)
}
