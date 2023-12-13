package com.danielkreitsch.habitatomo.backend.day

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "days")
class DayEntity {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Int = 0

  var date: LocalDate = LocalDate.now()

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "days_habits_done", joinColumns = arrayOf(JoinColumn(name = "day_id")))
  @Column(name = "habit_id")
  var habitsDone: MutableList<String> = ArrayList()
}
