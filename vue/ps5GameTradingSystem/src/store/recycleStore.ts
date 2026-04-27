// 引入defineStore用于创建store
import { defineStore } from 'pinia'
import httpAxios from '@/utils/http'
import type {recycle, recycleItems, recycles} from '@/types/recycle.ts'
import type {gameItemMaps, games} from "@/types/game.ts";

// 定义并暴露一个store
export const useRecycleStore = defineStore('recycleStore', {
    actions: {
        async queryAll(data: { userId: string; pageNum: number }) {
            // ========== 加固防御：userId 为空直接返回 ==========
            if (!data.userId || data.userId.trim() === '') {
                console.error('userId 不能为空，无法查询')
                return
            }

            try {
                // 路径现在一定是正确的
                const response = await httpAxios.get(`/recycle/queryAll/${data.userId}/${data.pageNum}`)
                const result = response.data

                if (result.code === 200) {
                    this.myRecycles = result.data.records
                    this.current = result.data.current
                    this.pages = result.data.pages
                    this.total = result.data.total
                } else {
                    console.error('查询失败', result.message)
                }
            } catch (error) {
                console.error('网络错误或请求异常:', error)
            }
        },

        async add(data: recycle) {
            try {
                const response = await httpAxios.post('/recycle/add', data)
                const result = response.data
                if (result.code === 200) {
                    if (result.data != null) {
                        this.newRecycle = result.data
                    } else {
                        console.log('添加成功')
                    }
                }
            } catch (error) {
                console.error('添加失败:', error)
            }
        },
        async queryAllAll(data: {pageNum: number }) {
            try {
                // 路径现在一定是正确的
                const response = await httpAxios.get(`/recycle/queryAllAll/${data.pageNum}`)
                const result = response.data

                if (result.code === 200) {
                    this.myRecycles = result.data.records
                    this.current = result.data.current
                    this.pages = result.data.pages
                    this.total = result.data.total
                    // 将后端数据映射为前端所需格式
                    this.myRecycleMap = this.myRecycles.map(item => ({
                        id:   item.id,
                        userId: item.userId,
                        gameName: item.gameName,
                        valuation: item.valuation,
                        recyclingStatus: item.recyclingStatus,
                        version: item.version,
                        language: item.language,
                        appearance: item.appearance,
                        box: item.box,
                        checked: false // 初始化未选中
                    }))
                } else {
                    console.error('查询失败', result.message)
                }
            } catch (error) {
                console.error('网络错误或请求异常:', error)
            }
        },
        // 修改 update 方法
        async update(data: any) {
            try {
                const response = await httpAxios.post('/recycle/update', data);
                const result = response.data;
                if (result.code === 200) {
                    this.myRecycles = result.data;
                    console.log("更新成功");
                    return true;   // ✅ 添加返回 true
                } else {
                    console.error("更新失败", result.message);
                    return false;  // ✅ 添加返回 false
                }
            } catch (error) {
                console.error('更新失败:', error);
                return false;    // ✅ 添加返回 false
            }
        },

// 修改 delete 方法
        async delete(id: string) {
            try {
                const response = await httpAxios.delete(`/recycle/delete/${id}`);
                const result = response.data;
                if (result.code === 200) {
                    console.log("删除成功");
                    return true;   // ✅ 添加返回 true
                } else {
                    console.error("删除失败", result.message);
                    return false;  // ✅ 添加返回 false
                }
            } catch (error) {
                console.error('删除失败:', error);
                return false;    // ✅ 添加返回 false
            }
        },

// 修改 add 方法（如果你之前没加返回值，一并加上）
        async addM(user: any) {
            try {
                const response = await httpAxios.post('/recycle/addM', user);
                const result = response.data;
                if (result.code === 200) {
                    console.log('添加用户成功');
                    return true;
                } else {
                    console.error('添加用户失败', result.message);
                    return false;
                }
            } catch (error) {
                console.error('网络错误', error);
                return false;
            }
        },
    },

    state() {
        return {
            current: 0,
            pages: 0,
            total: 0,
            newRecycle: {} as recycle,
            myRecycles: [] as recycles,
            myRecycleMap:<recycleItems>([]),
        }
    },

    getters: {}
})