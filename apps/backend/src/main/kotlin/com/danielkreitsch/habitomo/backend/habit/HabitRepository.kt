package com.danielkreitsch.habitomo.backend.habit

import java.util.*
import org.springframework.data.repository.CrudRepository

interface HabitRepository : CrudRepository<HabitEntity, Int> {
  fun findByPageId(id: String): Optional<HabitEntity>
}
