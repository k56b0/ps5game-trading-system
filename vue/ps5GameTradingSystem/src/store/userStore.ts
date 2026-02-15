// 引入defineStore用于创建store
import {defineStore} from 'pinia'
import {reactive} from "vue";
import httpAxios from '@/utils/http'
import type { user } from '@/types/user.ts'
// 定义并暴露一个store
export const useUserStore = defineStore('userStore',{
    // 动作
    actions:{
        async login(data:any){
            //开始发送请求
            try {
                // 使用POST请求，数据放在请求体中。
                // response.data就是后端的 Result 对象,response.data.data就是Result 对象的data属性

                const response = await httpAxios.post('/user/login', data)
                //取出response.data，即后端的 Result 对象，并且做成响应式
                const result =  response.data
                if (result.code === 200) {
                    // 登录成功
                    let user = result.data
                    // console.log('登录成功:', user)
                    //让pinia存一下
                    this.isLoginMark=true
                    this.myInfo=user

                } else {
                    // 登录失败
                    const errorMsg = result.message || '登录失败，请检查邮箱和密码'
                    alert(errorMsg)
                }
            } catch (error) {
                console.error('登录请求失败:', error)
            }
        },

        async update(data:any){
            try {
                const response = await httpAxios.post('/user/update',data)
                //取出response.data，即后端的 Result 对象，并且做成响应式
                const result =  response.data
                if (result.code === 200) {
                    //让pinia存一下
                    this.myInfo = result.data
                    console.log("success")
                }
            }catch (error) {
                console.error('更新失败:', error)
            }
        },

        async register(data:any){
            try {
                const response = await httpAxios.post('/user/register',data)
                //取出response.data，即后端的 Result 对象，并且做成响应式
                const result =  response.data
                if (result.code === 200) {
                    console.log("success")
                }
            }catch (error) {
                console.error('更新失败:', error)
            }
        }
    },
    // 状态
    state(){
        return {
            isLoginMark:false,
            myInfo:reactive<user>({
                id:'',
                userName:'',
                gender:'',
                birth:'2025-01-01',
                password:'默认值',
                checkIn:'0',
                email:'1111111@qq.com',
                address:'默认值',
                answer:'',
                checkTime:'2025-01-01 00:00:00 ',
                money:0,
            })
        }
    },
    // 计算
    getters:{}
})