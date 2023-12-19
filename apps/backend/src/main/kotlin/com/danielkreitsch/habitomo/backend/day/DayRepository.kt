package com.danielkreitsch.habitomo.backend.day

import org.springframework.data.repository.CrudRepository

interface DayRepository : CrudRepository<DayEntity, Int> {}
