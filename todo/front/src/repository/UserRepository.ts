import type Login from '@/entity/user/Login'
import HttpRepository from '@/repository/HttpRepository'
import { inject, singleton } from 'tsyringe'
import UserProfile from '@/entity/user/UserProfile'

@singleton()
export default class UserRepository {
  constructor(@inject(HttpRepository) private readonly httpRepository: HttpRepository) {}

  public login(request: Login) {
    return this.httpRepository.post({
      path: '/api/auth/login',
      body: request,
    })
  }

  public getProfile() {
    return this.httpRepository.get(
      {
        path: '/api/users/me',
      },
      UserProfile,
    )
  }
}
