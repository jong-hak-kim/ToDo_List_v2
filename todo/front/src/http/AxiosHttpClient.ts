import type { AxiosInstance, AxiosRequestConfig, AxiosResponse } from 'axios'
import axios from 'axios'
import HttpError from '@/http/HttpError'

export type HttpRequestConfig = {
  method?: 'GET' | 'POST' | 'PUT' | 'PATCH' | 'DELETE'
  path: string
  params?: any
  body?: any
}

export default class AxiosHttpClient {
  private readonly client: AxiosInstance = axios.create({
    timeout: 3000,
    timeoutErrorMessage: '힝..',
  })

  public async request(config: HttpRequestConfig) {
    return this.client
      .request({
        method: config.method,
        url: config.path,
        params: config.params,
        data: config.body,
      })
      .then((response: AxiosResponse) => {
        return response.data
      })
      .catch((e) => {
        return Promise.reject(new HttpError(e))
      })
  }

  public async get(config: HttpRequestConfig) {
    return this.request({ ...config, method: 'GET' })
  }

  public async post(config: HttpRequestConfig) {
    return this.request({ ...config, method: 'POST' })
  }

  public async patch(config: HttpRequestConfig) {
    return this.request({ ...config, method: 'PATCH' })
  }

  public async delete(config: HttpRequestConfig) {
    return this.request({ ...config, method: 'DELETE' })
  }
}
