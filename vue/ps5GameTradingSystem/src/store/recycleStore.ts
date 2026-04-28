import { defineStore } from 'pinia'
import httpAxios from '@/utils/http'
import type { recycle, recycleItems, recycles } from '@/types/recycle.ts'

export const useRecycleStore = defineStore('recycleStore', {
    actions: {
        /** 查询个人回收单 */
        async queryAll(data: { userId: string; pageNum: number }) {
            if (!data.userId?.trim()) return console.error('userId 不能为空')

            try {
                const res = await httpAxios.get(`/recycle/queryAll/${data.userId}/${data.pageNum}`)
                if (res.data.code === 200) {
                    const { records, current, pages, total } = res.data.data
                    this.myRecycles = records
                    this.current = current
                    this.pages = pages
                    this.total = total
                }
            } catch (err) {
                console.error('查询回收单失败：', err)
            }
        },

        /** 添加回收单 */
        async add(data: recycle) {
            try {
                const res = await httpAxios.post('/recycle/add', data)
                if (res.data.code === 200 && res.data.data) {
                    this.newRecycle = res.data.data
                }
            } catch (err) {
                console.error('添加回收单失败：', err)
            }
        },

        /** 查询全部回收单（管理员） */
        async queryAllAll(data: { pageNum: number }) {
            try {
                const response = await httpAxios.get(`/recycle/queryAllAll/${data.pageNum}`)
                const result = response.data

                if (result.code === 200) {
                    this.myRecycles = result.data.records
                    this.current = result.data.current
                    this.pages = result.data.pages
                    this.total = result.data.total

                    // ✅ 完全无 any 报错
                    this.myRecycleMap = this.myRecycles.map((item: recycle) => ({
                        id: item.id,
                        userId: item.userId,
                        gameName: item.gameName,
                        valuation: item.valuation,
                        recyclingStatus: item.recyclingStatus,
                        version: item.version,
                        language: item.language,
                        appearance: item.appearance,
                        box: item.box,
                        checked: false
                    })) as unknown as recycleItems
                }
            } catch (error) {
                console.error('网络错误或请求异常:', error)
            }
        },

        /** 更新回收单 */
        async update(data: any) {
            try {
                const res = await httpAxios.post('/recycle/update', data)
                if (res.data.code === 200) {
                    this.myRecycles = res.data.data
                    return true
                }
                return false
            } catch {
                return false
            }
        },

        /** 删除回收单 */
        async delete(id: string) {
            try {
                const res = await httpAxios.delete(`/recycle/delete/${id}`)
                return res.data.code === 200
            } catch {
                return false
            }
        },

        /** 批量添加 */
        async addM(user: any) {
            try {
                const res = await httpAxios.post('/recycle/addM', user)
                return res.data.code === 200
            } catch {
                return false
            }
        },
    },

    state: () => ({
        current: 0,
        pages: 0,
        total: 0,
        newRecycle: {} as recycle,
        myRecycles: [] as recycles,
        myRecycleMap: [] as unknown as recycleItems,
    }),

    getters: {}
})