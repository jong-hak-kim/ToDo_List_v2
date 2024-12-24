import type { HttpRequestConfig } from '@/http/AxiosHttpClient'
import AxiosHttpClient from '@/http/AxiosHttpClient'
import { inject, singleton } from 'tsyringe'
import { plainToInstance } from 'class-transformer'
import Null from '@/entity/data/Null'
import Paging from '@/entity/data/Paging'

@singleton()
export default class HttpRepository {
  constructor(@inject(AxiosHttpClient) private readonly httpClient: AxiosHttpClient) {}

  public get<T>(config: HttpRequestConfig, clazz: { new (...args: any[]): T }): Promise<T> {
    return this.httpClient.request({ ...config, method: 'GET' }).then((response) => plainToInstance(clazz, response))
  }

  public getList<T>(config: HttpRequestConfig, clazz: { new (...args: any[]): T }): Promise<Paging<T>> {
    return this.httpClient.request({ ...config, method: 'GET' }).then((response) => {
      const paging = plainToInstance<Paging<T>, any>(Paging, response)
      const items = plainToInstance<T, any>(clazz, response.items)
      paging.setItems(Array.isArray(items) ? items : [items])
      return paging
    })
  }

  public async post<T>(config: HttpRequestConfig, clazz?: { new (...args: any[]): T }): Promise<T> {
    const response = await this.httpClient.request({ ...config, method: 'POST' })
    if (clazz) {
      return plainToInstance(clazz, response.data)
    } else {
      return plainToInstance(Null, response.data) as unknown as T
    }
  }

  public async patch<T>(config: HttpRequestConfig, clazz?: { new (...args: any[]): T }): Promise<T> {
    const response = await this.httpClient.request({ ...config, method: 'PATCH' })
    if (clazz) {
      return plainToInstance(clazz, response.data)
    } else {
      return plainToInstance(Null, response.data) as unknown as T
    }
  }

  public async delete<T>(config: HttpRequestConfig, clazz?: { new (...args: any[]): T }): Promise<T> {
    const response = await this.httpClient.request({ ...config, method: 'DELETE' })
    if (clazz) {
      return plainToInstance(clazz, response.data)
    } else {
      return plainToInstance(Null, response.data) as unknown as T
    }
  }
}
