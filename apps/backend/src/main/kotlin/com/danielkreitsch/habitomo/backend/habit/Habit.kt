package com.danielkreitsch.habitomo.backend.habit

import com.fasterxml.jackson.annotation.JsonIgnore
import java.time.LocalDateTime

class Habit(@JsonIgnore val entity: HabitEntity) {
  val id: Int
    get() = this.entity.id

  val pageId: String
    get() = this.entity.pageId

  val userId: Int
    get() = this.entity.userId

  val order: Int
    get() = this.entity.sortOrder

  val name: String
    get() = this.entity.name

  val readableName: String
    get() = this.entity.readableName

  val icon: String?
    get() = this.entity.icon

  val description: String?
    get() = this.entity.description

  val estimatedMinutes: Int
    get() = this.entity.estimatedMinutes

  val isAutomated: Boolean
    get() = this.entity.automated

  val isSkippable: Boolean
    get() = this.entity.skippable

  var skipped: Boolean
    get() = this.entity.skipped
    set(value) {
      this.entity.skipped = value
    }

  var done: Boolean
    get() = this.entity.done
    set(value) {
      this.entity.done = value

      if (this.entity.done) {
        this.entity.doneAt = LocalDateTime.now()
      } else {
        this.entity.doneAt = null
      }
    }
}
