// 引入defineStore用于创建store
import {defineStore} from 'pinia'
import httpAxios from '@/utils/http'
import type { shoppingCars,shoppingCarItems,shoppingCar } from '@/types/shoppingCar.ts'
import type { order } from '@/types/order.ts'
import {useOrderStore} from '@/store/orderStore.ts'

// 定义并暴露一个store
export const useShoppingCarStore = defineStore('shoppingCarStore', {
    // 动作
    actions:{
        async queryAll(data: { userId: string; pageNum: number }) {
            try {
                // 根据实际 baseURL 调整路径
                const response = await httpAxios.get(`/shoppingcar/queryAll/${data.userId}/${data.pageNum}`);
                const result = response.data;

                if (result.code === 200) {
                    // 将records存入响应式变量
                    this.myShoppingCar = result.data.records
                    //存入翻页信息 直接赋值给 state 属性，Pinia 会自动处理响应性
                    this.current = result.data.current;
                    this.pages = result.data.pages;
                    this.total = result.data.total;
                    //一个 action 来获取后端数据并转换成前端所需格式
                    // 将后端数据映射为前端所需格式
                    this.myShoppingCarMap = this.myShoppingCar.map(item => ({
                        id: item.id,
                        userId:item.userId,
                        gameName: item.gameName,
                        quantity:item.quantity,
                        price:item.price,
                        checked: false // 初始化未选中
                    }))
                } else {
                    console.error('查询失败', result.message);
                }
            } catch (error) {
                console.error('网络错误或请求异常:', error);
            }
        },
        async add(data:shoppingCar){
            try {
                const response = await httpAxios.post('/shoppingcar/add',data)
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
                const response = await httpAxios.delete(`/shoppingcar/delete/${id}`)
                //取出response.data，即后端的 Result 对象，并且做成响应式
                const result =  response.data
                if (result.code === 200) {
                    console.log("success")
                }
            }catch (error) {
                console.error('删除失败:', error)
            }
        },
        async addToOrder(selectedNames :any){
            try {
                const orderStore=useOrderStore()
                // order 对象
                let data:order = orderStore.newOrder(selectedNames)

                const response = await httpAxios.post("/shoppingcar/addToOrder",data)
                //取出response.data，即后端的 Result 对象，并且做成响应式
                const result =  response.data
                if (result.code === 200) {
                    console.log("success")
                }
            }catch (error) {
                console.error('添加失败:', error)
            }
        }
    },
    // 状态
    state(){
        return {
            current:0,
            pages:0,
            total:0,
            myShoppingCar: [] as shoppingCars,  // 类型断言
            myShoppingCarMap:<shoppingCarItems>([])
        }
    },
    // 计算
    getters:{
        shoppingCarQuantity():number{
            if(this.myShoppingCar ==null){
                return 0
            }
            return this.myShoppingCar.length
        }
    }
})