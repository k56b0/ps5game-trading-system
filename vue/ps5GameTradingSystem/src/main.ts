/*引入部分*/
import { createApp } from 'vue'
// 引入 Bootstrap CSS
import 'bootstrap/dist/css/bootstrap.min.css'
// 如果需要 Bootstrap 的 JavaScript 功能（如下拉菜单、弹出框等）
import 'bootstrap/dist/js/bootstrap.bundle.min.js'
//引入 Bootstrap Icons
import 'bootstrap-icons/font/bootstrap-icons.css'
//引入根组件
import App from './App.vue'
// 引入路由器
import router from './router/index.ts'
//引入axios
import axios from 'axios';
//引入createPinia，用于创建pinia
import { createPinia } from 'pinia'

/*创建部分*/
//创建一个应用
const app=createApp(App)

//创建全局可用的axios实例
const axiosInstance = axios.create({
    baseURL: 'http://localhost:7777/game/'
});
// 提供axios实例
app.provide('axios', axiosInstance);
// 挂载到全局属性
app.config.globalProperties.$axios = axiosInstance
//创建pinia
const pinia = createPinia()
/* 使用插件 */
//使用路由器
app.use(router)
app.use(pinia)
//挂载整个应用到 app容器中
app.mount('#app')
