package com.danielkreitsch.habitatomo.backend.day

import org.springframework.data.repository.CrudRepository

interface DayRepository : CrudRepository<DayEntity, Int> {}
