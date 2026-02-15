// 引入defineStore用于创建store
import {defineStore} from 'pinia'
import httpAxios from '@/utils/http'
import type { game,games } from '@/types/game.ts'

// 定义并暴露一个store
export const useGameStore = defineStore('gameStore', {
    // 动作
    actions:{
        async queryAll(pageNum: number ) {
            try {
                // 根据实际 baseURL 调整路径
                const response = await httpAxios.get(`/game/queryAll/${pageNum}`);
                const result = response.data;

                if (result.code === 200) {
                    console.log('查询成功', result.data);
                    // 将records存入响应式变量
                    this.Games = result.data.records
                    //存入翻页信息 直接赋值给 state 属性，Pinia 会自动处理响应性
                    this.current = result.data.current;
                    this.pages = result.data.pages;
                    this.total = result.data.total;
                } else {
                    console.error('查询失败', result.message);
                }
            } catch (error) {
                console.error('网络错误或请求异常:', error);
            }
        },
        async queryByName(gameName:string ) {
            try {
                // 根据实际 baseURL 调整路径
                const response = await httpAxios.get(`/game/queryByName/${gameName}`);
                const result = response.data;

                if (result.code === 200) {
                    this.GameOneShow=result.data
                } else {
                    console.error('查询失败', result.message);
                }
            } catch (error) {
                console.error('网络错误或请求异常:', error);
            }
        },

    },
    // 状态
    state(){
        return {
            current:0,
            pages:0,
            total:0,
            Games: [] as games,  // 类型断言
            GameOneShow: {} as game,
        }
    },
    // 计算
    getters:{
        discountedPrice():number{
            return Math.floor(this.GameOneShow.price*this.GameOneShow.discount)
        }
    }
})