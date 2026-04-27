//一种规范
//定义一个接口，用于限制person对象的具体属性
import type {game} from "@/types/game.ts";
import type {favorite} from "@/types/favorite.ts";

export interface user {
    id:   string,
    userName: string,
    gender:  string,
    birth: string,
    password: string,
    checkIn: string,
    email: string,
    address: string,
    answer: string,
    checkTime: string,
    money: number,
    isManage:number,
}
export interface userItemMap {
    id:   string,
    userName: string,
    gender:  string,
    birth: string,
    password: string,
    checkIn: string,
    email: string,
    address: string,
    answer: string,
    checkTime: string,
    money: number,
    isManage:number,
    checked: boolean  // 前端专属的选中状态
}
export type users = user[];
export type userItemMaps = userItemMap[];