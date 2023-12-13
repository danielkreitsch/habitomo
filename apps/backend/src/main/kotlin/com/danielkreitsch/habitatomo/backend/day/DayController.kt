package com.danielkreitsch.habitatomo.backend.day

import com.danielkreitsch.habitatomo.backend.day.*
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/days")
class DayController(private val dayService: DayService) {
  private val logger = LoggerFactory.getLogger(this::class.java)

  @GetMapping
  fun getDays(): List<Day> {
    return this.dayService.allDays
  }

  @GetMapping("/{dayId}")
  fun getDay(@PathVariable dayId: Int): Day? {
    return this.dayService.allDays.firstOrNull { day -> day.id == dayId }
  }

  @PutMapping("/{dayId}")
  fun updateDay(@PathVariable dayId: Int) {
    val day = this.dayService.allDays.firstOrNull { d -> d.id == dayId } ?: return
  }
}
