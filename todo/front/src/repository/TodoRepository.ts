import type Login from '@/entity/user/Login'
import HttpRepository from '@/repository/HttpRepository'
import { inject, singleton } from 'tsyringe'
import type TodoWrite from '@/entity/todo/TodoWrite'
import { plainToClass, plainToInstance } from 'class-transformer'
import Todo from '@/entity/todo/Todo'
import Paging from '@/entity/data/Paging'

@singleton()
export default class TodoRepository {
  constructor(@inject(HttpRepository) private readonly httpRepository: HttpRepository) {}

  public write(request: TodoWrite) {
    return this.httpRepository.post({
      path: '/api/todos',
      body: request,
    })
  }

  public get(todoId: number): Promise<Todo> {
    return this.httpRepository
      .get({
        path: `/api/todos/${todoId}`,
      })
      .then((response) => {
        return plainToInstance(Todo, response)
      })
  }

  public getList(page: number) {
    return this.httpRepository
      .get({
        path: `/api/todos?page=${page}&size=3`,
      })
      .then((response) => {
        const paging = plainToInstance<Paging<Todo>, any>(Paging, response)
        const items = plainToClass<Todo, any[]>(Todo, response.items)
        paging.setItems(items)
        return paging
      })
  }
}
