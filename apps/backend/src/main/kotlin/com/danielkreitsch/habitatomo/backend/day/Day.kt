package com.danielkreitsch.habitatomo.backend.day

import com.fasterxml.jackson.annotation.JsonIgnore
import java.time.LocalDate

class Day(@JsonIgnore val entity: DayEntity) {
  val id: Int
    get() = this.entity.id

  val date: LocalDate
    get() = this.entity.date

  val habitsDone: MutableList<String>
    get() = this.entity.habitsDone
}
