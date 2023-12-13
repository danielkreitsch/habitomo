package com.danielkreitsch.habitatomo.backend.habit

import com.danielkreitsch.habitatomo.backend.notion.NotionService
import com.danielkreitsch.habitatomo.backend.user.User
import kotlinx.coroutines.runBlocking
import org.jraf.klibnotion.model.emoji.Emoji
import org.jraf.klibnotion.model.richtext.RichTextList
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class HabitService(
    private val habitRepo: HabitRepository,
    private val notionService: NotionService
) {
  private val logger = LoggerFactory.getLogger(javaClass)

  val allHabits: MutableList<Habit> = ArrayList()

  fun getHabitsByUser(user: User): List<Habit> {
    return this.allHabits.filter { habit -> habit.userId == user.id }.toList()
  }

  init {
    runBlocking { loadHabitsFromNotion() }
  }

  suspend fun loadHabitsFromNotion() {
    try {
      val pages = this.notionService.getPages("0f02ea566c794f69945257bb74737a2f")
      for (page in pages) {
        var habitEntity = this.habitRepo.findByPageId(page.id).orElse(null)
        if (habitEntity == null) {
          habitEntity = HabitEntity()
        }
        habitEntity.name =
            (page.propertyValues.first { v -> v.name == "Name" }.value as RichTextList).plainText!!
        habitEntity.pageId = page.id
        habitEntity.userId =
            (page.propertyValues.first { v -> v.name == "UserId" }.value as Long).toInt()
        habitEntity.sortOrder =
            (page.propertyValues.first { v -> v.name == "Order" }.value as Long).toInt()
        habitEntity.readableName =
            (page.propertyValues.first { v -> v.name == "ReadableName" }.value as RichTextList)
                .plainText!!
        habitEntity.icon = if (page.icon != null) (page.icon as Emoji).value else ""
        habitEntity.description =
            (page.propertyValues.first { v -> v.name == "Description" }.value as RichTextList)
                .plainText
        habitEntity.estimatedMinutes =
            (page.propertyValues.first { v -> v.name == "EstimatedMinutes" }.value as Long).toInt()
        habitEntity.automated =
            page.propertyValues.first { v -> v.name == "IsAutomated" }.value as Boolean
        habitEntity.skippable =
            page.propertyValues.first { v -> v.name == "IsSkippable" }.value as Boolean
        this.habitRepo.save(habitEntity)
        this.logger.info(
            "Habit loaded: " + habitEntity.readableName + " (" + habitEntity.name + ")")
      }

      for (habitEntity in this.habitRepo.findAll()) {
        val page = this.notionService.getPage(habitEntity.pageId)
        if (page.archived) {
          this.habitRepo.delete(habitEntity)
        } else {
          this.allHabits.add(Habit(habitEntity))
        }
      }
    } catch (ex: Exception) {
      ex.printStackTrace()
    }
  }

  fun saveHabit(habit: Habit) {
    this.habitRepo.save(habit.entity)
  }
}
