<template>
  <div>
    <h1>{{ msg }}</h1>
    <button @click="fetchData">获取数据</button>
  </div>
</template>
<script setup lang="ts">
import { ref,onMounted } from 'vue'
import { inject } from 'vue';
import type { AxiosInstance, AxiosResponse } from 'axios'

const axios = inject('axios');
let msg = ref('默认值')

onMounted(()=>{
  fetchData()
  console.log('挂载完毕')
})
const fetchData = async () => {
  /**
   fetch方法
   try {
   const response = await fetch('http://localhost:8080/api/hello')
   const data = await response.text()
   msg.value = data  // 注意：ref 需要使用 .value
   } catch (error) {
   console.error('Error fetching data:', error)
   }
   */
  try {
    // 使用 axios 发起请求（比 fetch 更简洁）
    const response = await (axios as AxiosInstance).get('/hello')

    // axios 自动解析响应体，不需要手动调用 .text() 或 .json()
    msg.value = response.data  // axios 的 data 属性

    // axios 还提供更多信息（可选）
    console.log('状态码:', response.status)
    console.log('状态文本:', response.statusText)
    console.log('响应头:', response.headers)
  } catch (error) {
    console.error('请求失败:', error)

    // axios 提供更详细的错误信息
    if ((error as any).response) {
      // 服务器返回了错误状态码 (4xx, 5xx)
      console.log('错误状态码:', (error as any).response.status)
      console.log('错误数据:', (error as any).response.data)
    } else if ((error as any).request) {
      // 请求已发送但无响应
      console.log('无响应:', (error as any).request)
    } else {
      // 请求配置出错
      console.log('配置错误:', (error as any).message)
    }
  }
}

</script>

<style scoped>

</style>