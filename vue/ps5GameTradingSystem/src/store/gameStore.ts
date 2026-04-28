// 引入defineStore用于创建store
import {defineStore} from 'pinia'
import httpAxios from '@/utils/http'
import type {game, gameItemMap, gameItemMaps, games} from '@/types/game.ts'
import {ref} from "vue";
import type {favoriteItems} from "@/types/favorite.ts";

// 定义并暴露一个store
export const useGameStore = defineStore('gameStore', {
    // 动作
    actions:{
        // queryAll 也建议加上 isSearching 重置，保证切换回“全部”时状态正确
        async queryAll(pageNum: number) {
            try {
                const response = await httpAxios.get(`/game/queryAll/${pageNum}`);
                const result = response.data;
                if (result.code === 200) {
                    this.Games = result.data.records;
                    this.current = result.data.current;
                    this.pages = result.data.pages;
                    this.total = result.data.total;
                    this.currentCategory = null;
                    this.isSearching = false;           // 退出搜索模式
                    this.searchKeyword = '';

                    this.myGamesMap = this.Games.map(item => ({
                        id: item.id,
                        englishName: item.englishName,
                        gameName: item.gameName,
                        price: item.price,
                        discount: item.discount,
                        inventory: item.inventory,
                        category: item.category,
                        sales: item.sales,
                        introduction: item.introduction,
                        year: item.year,
                        img: item.img,
                        checked: false,
                    }));
                } else {
                    console.error('查询失败', result.message);
                }
            } catch (error) {
                console.error('网络错误或请求异常:', error);
            }
        },
        async queryByName(gameName: string, isSearch: boolean, pageNum = 1) {
            try {
                const response = await httpAxios.get(
                    `/game/queryByName/${encodeURIComponent(gameName)}/${pageNum}`
                );
                const result = response.data;
                if (result.code === 200) {
                    if (!isSearch) {
                        // 详情页（保持不变）
                        this.GameOneShow = result.data.records?.[0] || {};
                    } else {
                        // 搜索列表页
                        this.Games = result.data.records;
                        this.current = result.data.current;
                        this.pages = result.data.pages;
                        this.total = result.data.total;
                        this.searchKeyword = gameName;
                        this.isSearching = true;           // 标记为搜索状态

                        // ✅ 关键：必须映射到 myGamesMap，和 queryAll 保持一致
                        this.myGamesMap = this.Games.map(item => ({
                            id: item.id,
                            englishName: item.englishName,
                            gameName: item.gameName,
                            price: item.price,
                            discount: item.discount,
                            inventory: item.inventory,
                            category: item.category,
                            sales: item.sales,
                            introduction: item.introduction,
                            year: item.year,
                            img: item.img,
                            checked: false,
                        }));
                    }
                } else {
                    console.error('查询异常', result.message);
                    if (isSearch) {
                        this.Games = [];
                        this.myGamesMap = [];
                        this.searchKeyword = '';
                        this.isSearching = false;
                    }
                }
            } catch (error) {
                console.error('网络错误', error);
                if (isSearch) {
                    this.Games = [];
                    this.myGamesMap = [];
                    this.searchKeyword = '';
                    this.isSearching = false;
                }
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
        async delete(id:string){
            try {
                const response = await httpAxios.delete(`/game/delete/${id}`)
                //取出response.data，即后端的 Result 对象，并且做成响应式
                const result =  response.data
                if (result.code === 200) {
                    console.log("success")
                    return true
                }else {
                    console.error('删除失败', result.message);
                    return false;
                }
            }catch (error) {
                console.error('删除失败:', error)
            }
        },
        async update(game: game) {
            try {
                const response = await httpAxios.post('/game/update',game);
                const result = response.data;
                if (result.code === 200) {
                    console.log('更新成功');
                    return true;
                } else {
                    console.error('更新失败', result.message);
                    return false;
                }
            } catch (error) {
                console.error('网络错误', error);
                return false;
            }
        },
        async add(game: game) {   // 注意类型改为 game
            try {
                const response = await httpAxios.post('/game/add', game);
                const result = response.data;
                if (result.code === 200) {
                    console.log('添加成功');
                    return true;
                } else {
                    console.error('添加失败', result.message);
                    return false;
                }
            } catch (error) {
                console.error('网络错误', error);
                return false;
            }
        }
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
            myGamesMap:<gameItemMaps>([]),
            GameOneShow: {} as game,
            searchKeyword: '' as string,      // 当前搜索关键词
            isSearching: false, // 新增：是否处于搜索模式
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