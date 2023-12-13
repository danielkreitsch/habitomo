import { Component } from "@angular/core"
import { Habit } from "../../classes/habit"
import { HabitService } from "../../services/habit.service"
import { AlertController, IonicModule } from "@ionic/angular"
import { CommonModule } from "@angular/common"
import { FormsModule } from "@angular/forms"
import { addIcons } from "ionicons"
import { playForwardOutline, repeatOutline, timerOutline } from "ionicons/icons"

@Component({
  standalone: true,
  imports: [IonicModule, CommonModule, FormsModule],
  selector: "app-home",
  templateUrl: "home.page.html",
  styleUrls: ["home.page.scss"],
})
export class HomePage {
  userId = 0
  habits: Habit[]
  nextHabit: Habit
  habitsCount: number
  habitsDoneCount: number
  progress: number
  ignoreFetch: boolean

  constructor(private habitService: HabitService, private alertController: AlertController) {
    if (localStorage.getItem("userId") !== null) {
      this.userId = parseInt(localStorage.getItem("userId"), 10)
    }

    addIcons({
      playForwardOutline,
      repeatOutline,
      timerOutline,
    })
  }

  ngOnInit() {
    this.fetchHabits()
    setInterval(() => this.fetchHabits(), 1000)
  }

  fetchHabits() {
    if (this.ignoreFetch) {
      return
    }
    this.habitService.getHabits().then((habits) => {
      if (this.ignoreFetch) {
        return
      }
      this.habits = habits
      this.updateHabitVisualization()
    })
  }

  updateHabitVisualization() {
    this.habitsCount = 0
    this.habitsDoneCount = 0
    this.nextHabit = null

    this.habits.forEach((habit) => {
      if (!habit.done && this.nextHabit == null) {
        this.nextHabit = habit
      }

      this.habitsCount++

      if (habit.done) {
        this.habitsDoneCount++
      }
    })

    this.progress = this.habitsDoneCount / this.habitsCount
  }

  onLoginClick(userId: number) {
    localStorage.setItem("userId", userId.toString())
    this.userId = userId
  }

  onHabitStatusChange(habit: Habit) {
    if (habit.automated) {
      this.presentAlert("Du kannst diese Aufgabe nicht selber abhaken!")
      setTimeout(() => {
        habit.done = !habit.done
      }, 1)
      return
    }
    if (this.habits.find((h) => !h.skipped && !h.done && h.order < habit.order) != null) {
      this.presentAlert("Du musst zuerst alle vorherigen Aufgaben abschließen!")
      setTimeout(() => {
        habit.done = !habit.done
      }, 1)
      return
    }
    this.ignoreFetch = true
    this.habitService.updateHabit(habit).then(() => (this.ignoreFetch = false))
    this.updateHabitVisualization()
  }

  onHabitSkipClick(habit: Habit) {
    this.ignoreFetch = true
    this.habitService.skipHabit(habit).then(() => (this.ignoreFetch = false))
    this.updateHabitVisualization()
  }

  /**
   * For a better performance.
   */
  trackHabits(index: number, habit: Habit) {
    return habit.id
  }

  async presentAlert(message: string) {
    const alert = await this.alertController.create({
      subHeader: "Nicht möglich",
      message,
      buttons: ["OK"],
    })
    await alert.present()
  }
}
