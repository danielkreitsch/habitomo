import { HttpInterceptorFn } from "@angular/common/http"

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const userId = localStorage.getItem("userId") ?? 0
  req = req.clone({
    setHeaders: {
      Authorization: userId.toString(),
    },
  })
  return next(req)
}
