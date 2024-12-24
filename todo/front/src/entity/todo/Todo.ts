import { DateTimeFormatter, LocalDateTime } from '@js-joda/core'
import { Transform } from 'class-transformer'

export default class Todo {
  public id = 0
  public title = ''
  public content = ''
  public regDate = ''

  public getDisplayRegDate() {
    const localDateTime = LocalDateTime.parse(this.regDate, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
    return localDateTime.format(DateTimeFormatter.ofPattern('yyyy년 MM월 dd일 HH시'))
  }

  public getDisplaySimpleRegDate() {
    const localDateTime = LocalDateTime.parse(this.regDate, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
    return localDateTime.format(DateTimeFormatter.ofPattern('yyyy-MM-dd'))
  }
}
