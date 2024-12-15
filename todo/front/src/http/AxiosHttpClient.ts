import type { AxiosInstance, AxiosRequestConfig } from 'axios'
import axios from 'axios'
import HttpError from '@/http/HttpError'

export default class AxiosHttpClient {
  private readonly client: AxiosInstance = axios.create({
    timeout: 3000,
    timeoutErrorMessage: '힝..',
  })

  public async request(config: AxiosRequestConfig) {
    return this.client.request(config).catch((e) => {
      return Promise.reject(new HttpError(e))
    })
  }

  public async get(url: string) {
    return this.request({
      method: 'GET',
      url: url,
    })
  }

  public async post(url: string) {}

  public async patch() {}
}
