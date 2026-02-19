// 引入defineStore用于创建store
import {defineStore, storeToRefs} from 'pinia'
import httpAxios from '@/utils/http'
import type {orderDetails, orders,order} from '@/types/order.ts'
import type { shoppingCars,shoppingCarItems,shoppingCar } from '@/types/shoppingCar.ts'
import { v4 as uuidv4 } from 'uuid'
import dayjs from 'dayjs'
import {useUserStore} from '@/store/userStore.ts'
import {computed, onMounted, onUnmounted, ref, type WritableComputedRef} from "vue";






// 定义并暴露一个store
export const useOrderStore = defineStore('orderStore', {
    // 动作
    actions:{
        async queryAll(params: { userId: string; pageNum: number }) {
            try {
                // 根据实际 baseURL 调整路径
                const response = await httpAxios.get('/order/queryAll', { params });
                const result = response.data;

                if (result.code === 200) {
                    // 将records存入响应式变量
                    this.myOrders = result.data.records
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

        newOrder(selected:shoppingCarItems) {
            const userStore =useUserStore()
            const {myInfo}=storeToRefs(userStore)
            // currentTime 是 Ref<string> 类型，.value 为字符串
            const currentTime = ref(dayjs().format('YYYY-MM-DD HH:mm:ss'))

            // 正确声明 timer 类型：number 或 null（初始无定时器）
            let timer: number | null = null
            onMounted(() => {
                // 启动定时器，返回 number 类型的 ID
                timer = setInterval(() => {
                    currentTime.value = dayjs().format('YYYY-MM-DD HH:mm:ss')
                }, 1000)
            })
            onUnmounted(() => {
                // 清除定时器前需确保 timer 不为 null
                if (timer !== null) {
                    clearInterval(timer)
                    timer = null // 可选，重置
                }
            })
            //计算金额
            // 计算属性——既读取又修改
            let computedSum:WritableComputedRef<number,number> = computed({
                // 读取
                get(){
                    return 1
                },
                // 修改
                set(val){
                    console.log('有人修改了fullName',val)
                }
            })
            // 生成订单ID
            const orderId = uuidv4();

            // 生成订单详情数组，每个详情有自己的ID
            const OrderDetails:orderDetails = selected.map(item => ({
                id: uuidv4(),                // 每个详情独立ID
                orderId: orderId,
                gameName: item.gameName,
                price: item.price,            // 假设购物车项中有单价字段
                quantity: item.quantity,
            }));

            // 计算总金额
            const totalSum = OrderDetails.reduce((sum, item) => sum + (item.price * item.quantity), 0);

            // 构建订单对象
            const newOrder: order = {
                id: orderId,
                userId: myInfo.value.id,
                orderTime: currentTime.value,  // 注意currentTime是ref，需要.value
                orderAddress: myInfo.value.address,
                sum: totalSum,
                orderDetails: OrderDetails,
            };
            // // 更新state（可选，如果希望保存到state中供其他地方使用）
            // this.newAOrder = newOrder;
            // this.newOrderDetails = orderDetails;
            return newOrder;
        }

    },
    // 状态
    state(){
        return {
            current:0,
            pages:0,
            total:0,
            newOrderDetails: [] as orderDetails,
            newAOrder: {
                id: '',
                userId: '',
                orderTime:  '',
                orderAddress: '',
                sum: 0,
                orderDetails:[] as orderDetails,
            },
            myOrders: [] as orders,  // 类型断言
        }
    },
    // 计算
    getters:{}
})