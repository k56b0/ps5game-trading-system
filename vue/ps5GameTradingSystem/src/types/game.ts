import type {favoriteItem} from "@/types/favorite.ts";

export interface game{
    id:string,
    englishName:string,
    gameName:string,
    price:number,
    discount:number,
    inventory:number,
    category:string
    sales:number,
    introduction:string,
    year:string,
    img:string,
}
export interface gameItemMap {
    id:string,
    englishName:string,
    gameName:string,
    price:number,
    discount:number,
    inventory:number,
    category:string
    sales:number,
    introduction:string,
    year:string,
    img:string,
    checked: boolean  // 前端专属的选中状态
}
export type games=game[]
export type gameItemMaps = gameItemMap[];