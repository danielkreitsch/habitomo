import { CapacitorConfig } from "@capacitor/cli"

const config: CapacitorConfig = {
  appId: "io.ionic.starter",
  appName: "habitatomo",
  webDir: "../../dist/apps/app",
  bundledWebRuntime: false,
  server: {
    androidScheme: "https",
  },
}

export default config
