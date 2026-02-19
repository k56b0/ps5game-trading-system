// 引入defineStore用于创建store
import {defineStore} from 'pinia'
import httpAxios from '@/utils/http'
import type { favorites,favorite,favoriteItems } from '@/types/favorite.ts'

// 定义并暴露一个store
export const useFavoriteStore = defineStore('favoriteStore', {
    // 动作
    actions:{
        async queryAll(data: { userId: string; pageNum: number }) {
            try {
                // 根据实际 baseURL 调整路径
                const response = await httpAxios.get(`/favorites/queryAll/${data.userId}/${data.pageNum}`);
                const result = response.data;

                if (result.code === 200) {
                    // 将records存入响应式变量
                    this.myFavorites = result.data.records
                    //存入翻页信息 直接赋值给 state 属性，Pinia 会自动处理响应性
                    this.current = result.data.current;
                    this.pages = result.data.pages;
                    this.total = result.data.total;
                    //一个 action 来获取后端数据并转换成前端所需格式
                    // 将后端数据映射为前端所需格式
                    this.myFavoritesMap = this.myFavorites.map(item => ({
                        id: item.id,
                        gameName: item.gameName,
                        checked: false // 初始化未选中
                    }))
                } else {
                    console.error('查询失败', result.message);
                }
            } catch (error) {
                console.error('网络错误或请求异常:', error);
            }
        },
        async add(data:favorite){
            try {
                const response = await httpAxios.post('/favorites/add',data)
                //取出response.data，即后端的 Result 对象，并且做成响应式
                const result =  response.data
                if (result.code === 200) {
                    console.log("success")
                }
            }catch (error) {
                console.error('添加失败:', error)
            }
        },
        async delete(id:string){
            try {
                const response = await httpAxios.delete(`/favorites/delete/${id}`)
                //取出response.data，即后端的 Result 对象，并且做成响应式
                const result =  response.data
                if (result.code === 200) {
                    console.log("success")
                }
            }catch (error) {
                console.error('删除失败:', error)
            }
        },
    },
    // 状态
    state(){
        return {
            current:0,
            pages:0,
            total:0,
            myFavorites: [] as favorites,  // 类型断言
            myFavoritesMap:<favoriteItems>([])
        }
    },
    // 计算
    getters:{}
})