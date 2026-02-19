// 引入defineStore用于创建store
import {defineStore} from 'pinia'
import httpAxios from '@/utils/http'
import type { game,games } from '@/types/game.ts'
import {ref} from "vue";

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
                    // 将records存入响应式变量
                    this.Games = result.data.records
                    //存入翻页信息 直接赋值给 state 属性，Pinia 会自动处理响应性
                    this.current = result.data.current;
                    this.pages = result.data.pages;
                    this.total = result.data.total;
                    this.currentCategory=null;
                } else {
                    console.error('查询失败', result.message);
                }
            } catch (error) {
                console.error('网络错误或请求异常:', error);
            }
        },
        async queryByName(gameName:string,isSearch:boolean ) {
            try {
                // 根据实际 baseURL 调整路径
                const response = await httpAxios.get(`/game/queryByName/${gameName}`);
                const result = response.data;

                if (result.code === 200) {
                    if(!isSearch){
                        this.GameOneShow=result.data
                    }else{
                        this.Games= [result.data]
                        this.current = 1;
                        this.pages = 1;
                    }
                } else {
                    console.error('查询失败', result.message);
                }
            } catch (error) {
                console.error('网络错误或请求异常:', error);
            }
        },
        async queryPushPage(choice:string ) {
            try {
                // 根据实际 baseURL 调整路径
                const response = await httpAxios.get(`/game/queryPushPage/${choice}`);

                const result = response.data;
                if (result.code === 200) {
                    if(choice=="热销商品"){
                        this.PushPageGames1=result.data.records
                    }else if(choice=="新品上市"){
                        this.PushPageGames2=result.data.records
                    }else{
                        this.PushPageGames3=result.data.records
                    }

                } else {
                    console.error('查询失败', result.message);
                }
            } catch (error) {
                console.error('网络错误或请求异常:', error);
            }
        },
        async queryCategory(choice:string,pageNum: number ) {
            try {
                // 根据实际 baseURL 调整路径
                const response = await httpAxios.get(`/game/queryCategory/${choice}/${pageNum}`);
                const result = response.data;
                if (result.code === 200) {
                    // 将records存入响应式变量
                    this.Games = result.data.records
                    this.current = result.data.current;
                    this.pages = result.data.pages;
                    this.currentCategory=choice;
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
            categories:[ "射击","体育","恐怖","策略","JRPG","动作","经营","竞速","开放世界","角色扮演","双人","联机"],
            PushPageGames1: [] as games,  // 类型断言
            PushPageGames2: [] as games,  // 类型断言
            PushPageGames3: [] as games,  // 类型断言
            Games: [] as games,  // 类型断言
            GameOneShow: {} as game,
            currentCategory : null as string | null// 新增：当前选中的分类，null 表示全部
        }
    },
    // 计算
    getters:{
        discountedPrice():number{
            return Math.floor(this.GameOneShow.price*this.GameOneShow.discount)
        }
    }
})