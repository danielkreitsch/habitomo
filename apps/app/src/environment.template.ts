const env = []

export const environment = {
  production: env["IS_PRODUCTION"] ?? false,
  serviceUrl: env["BACKEND_URL"] ?? "http://localhost:8080",
}
