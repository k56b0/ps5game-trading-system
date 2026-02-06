// 引入defineStore用于创建store
import {defineStore} from 'pinia'

import {reactive} from "vue";
// 定义并暴露一个store
export const useLoginStore = defineStore('login',{
    // 动作
    actions:{},
    // 状态
    state(){
        return {
            isLoginMark:false,
            myInfo:{
                id:'默认值',
                name:'默认值',
                birth:'2025-01-01',
                address:'默认值',
                email:'默认值',
                passw:'默认值',
                checkin:'0',
                answer:'默认值'
            }
        }
    },
    // 计算
    getters:{}
})