export class Habit {
  public id: number
  public notionPageId: string
  public order: number
  public name: string
  public readableName: string
  public icon: string
  public description: string
  public estimatedMinutes: number
  public automated: boolean
  public skippable: boolean
  public skipped: boolean
  public done: boolean
}
