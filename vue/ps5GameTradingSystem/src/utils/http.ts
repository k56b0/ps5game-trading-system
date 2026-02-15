// src/utils/http.ts
import axios, {type AxiosInstance} from 'axios'

// 1. 定义响应数据结构（根据后端实际格式调整）
export interface ApiResponse<T = any> {
    code: number
    data: T
    message: string
}
// 2. 创建 axios 实例
const httpAxios: AxiosInstance = axios.create({
    baseURL: 'http://localhost:7777/game/'
})
export default httpAxios