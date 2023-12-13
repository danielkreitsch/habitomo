import { Injectable } from "@angular/core"
import { environment } from "../../environments/environment"
import { Habit } from "../classes/habit"
import { HttpClient } from "@angular/common/http"

@Injectable({
  providedIn: "root",
})
export class HabitService {
  constructor(private http: HttpClient) {}

  getHabits(): Promise<Habit[]> {
    return new Promise((resolve) => {
      this.http.get<Habit[]>(environment.serviceUrl + "/habits").subscribe((unsortedHabits) => {
        const habits = []
        unsortedHabits.forEach((habit) => {
          if (habit.skipped) {
            return
          }

          habits.push(habit)
        })
        habits.sort((a, b) => a.order - b.order)
        resolve(habits)
      })
    })
  }

  updateHabit(habit: Habit) {
    return new Promise((resolve) => {
      this.http
        .put<Habit[]>(environment.serviceUrl + "/habits/" + habit.name, {
          done: habit.done,
        })
        .subscribe(() => {
          resolve(null)
        })
    })
  }

  skipHabit(habit: Habit) {
    return new Promise((resolve) => {
      this.http
        .post<Habit[]>(environment.serviceUrl + "/habits/" + habit.name + "/skip", null)
        .subscribe(() => {
          resolve(null)
        })
    })
  }
}
