import { Routes } from "@angular/router"
import { HomePage } from "./pages/home/home.page"

export const routes: Routes = [
  {
    path: "",
    //redirectTo: "home",
    pathMatch: "full",
    component: HomePage,
    //loadComponent: () => import("./pages/home/home.page").then((m) => m.HomePage),
  },
]
