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

  public get(todoId: number) {
    return this.httpRepository.get<Todo>(
      {
        path: `/api/todos/${todoId}`,
      },
      Todo,
    )
  }

  public getList(page: number) {
    return this.httpRepository.getList<Todo>(
      {
        path: `/api/todos?page=${page}&size=3`,
      },
      Todo,
    )
  }

  public delete(todoId: number) {
    return this.httpRepository.delete({
      path: `/api/todos/${todoId}`,
    })
  }
}
