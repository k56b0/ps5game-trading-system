// 引入defineStore用于创建store
import {defineStore} from 'pinia'
import httpAxios from '@/utils/http'
import type {rechargeRecords,rechargeRecord} from '@/types/rechargeRecord.ts'

// 定义并暴露一个store
export const useRechargeRecordStore = defineStore('rechargeRecordStore', {
    // 动作
    actions:{
        async queryAll(data: { userId: string; pageNum: number }) {
            try {
                // 根据实际 baseURL 调整路径
                /**
                 * 你用了单引号或双引号包裹字符串，而不是反引号（模板字符串标识符）。
                 * 错误写法：'/recycle/queryAll/${id}/${pageNum}'
                 * 正确写法：`/recycle/queryAll/${id}/${pageNum}`
                 */
                const response = await httpAxios.get(`/recharge_record/queryAll/${data.userId}/${data.pageNum}`);
                const result = response.data;

                if (result.code === 200) {
                    console.log('查询成功', result.data);
                    // 将records存入响应式变量
                    this.myRechargeRecords = result.data.records
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
        async save(data:rechargeRecord) {
            const response = await httpAxios.put("/recharge_record/save",data);
            const result = response.data;
            try {
            if (result.code === 200) {
                console.log('充值成功', result.data);

            } else {
                console.error('充值失败', result.message);
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
            myRechargeRecords: [] as rechargeRecords,  // 类型断言
        }
    },
    // 计算
    getters:{}
})