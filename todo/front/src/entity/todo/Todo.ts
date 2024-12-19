import { DateTimeFormatter, LocalDateTime } from '@js-joda/core'
import { Transform } from 'class-transformer'

export default class Todo {
  public id = 0

  public title = ''

  public content = ''

  @Transform(({ value }) => LocalDateTime.parse(value, DateTimeFormatter.ISO_LOCAL_DATE_TIME))
  public regDate = LocalDateTime.now()

  // dayjs, monet, datefns

  public getDisplayRegDate() {
    return this.regDate.format(DateTimeFormatter.ofPattern('yyyy년 MM월 dd일 HH시'))
  }
}
