package com.danielkreitsch.habitomo.backend.habit

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "habits")
class HabitEntity {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Int = 0

  var name: String = ""

  var pageId: String = ""

  var userId: Int = 1

  /** The variable is called 'sortOrder' instead of 'order' because of MySQL restrictions. */
  var sortOrder: Int = 0

  var readableName: String = ""

  var icon: String? = null

  var description: String? = null

  var estimatedMinutes = 0

  var automated: Boolean = false

  var skippable: Boolean = false

  var skipped: Boolean = false

  var done: Boolean = false

  var doneAt: LocalDateTime? = null
}
