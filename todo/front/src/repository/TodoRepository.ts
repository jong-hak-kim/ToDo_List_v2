import type Login from '@/entity/user/Login'
import HttpRepository from '@/repository/HttpRepository'
import { inject, singleton } from 'tsyringe'
import type TodoWrite from '@/entity/todo/TodoWrite'

@singleton()
export default class TodoRepository {
  constructor(@inject(HttpRepository) private readonly httpRepository: HttpRepository) {}

  public write(request: TodoWrite) {
    return this.httpRepository.post({
      path: '/api/todos',
      body: request,
    })
  }
}
