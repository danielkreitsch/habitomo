package com.danielkreitsch.habitatomo.backend.day

import com.danielkreitsch.habitatomo.backend.habit.Habit
import java.time.LocalDate
import java.time.LocalDateTime
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class DayService(private val dayRepo: DayRepository) {
  private val logger = LoggerFactory.getLogger(javaClass)

  final val allDays: MutableList<Day> =
      this.dayRepo.findAll().toList().map { dayEntity -> Day(dayEntity) }.toMutableList()

  val currentDay: Day
    get() {
      var date = LocalDate.now()
      if (LocalDateTime.now().hour <= 5) {
        date = date.minusDays(1)
      }
      return this.getDayByDate(date)!!
    }

  init {
    for (date in LocalDate.now().minusDays(1).datesUntil(LocalDate.now().plusWeeks(52))) {
      if (this.allDays.any { day -> day.date == date }) {
        continue
      }

      val dayEntity = DayEntity()
      dayEntity.date = date
      this.dayRepo.save(dayEntity)

      this.allDays.add(Day(dayEntity))
    }
  }

  fun getDayByDate(date: LocalDate): Day? {
    return this.allDays.firstOrNull { day -> day.date == date }
  }

  fun saveToday(habitsDone: List<Habit>) {
    this.logger.info(
        "Saving today's habits: " + habitsDone.joinToString(", ") { habit -> habit.name })

    // Save to database
    val day = this.currentDay
    day.habitsDone.clear()
    day.habitsDone.addAll(habitsDone.map { habit -> habit.name }.toList())
    this.dayRepo.save(day.entity)
  }
}
