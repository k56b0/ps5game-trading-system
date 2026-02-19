export interface shoppingCar {
    id:string
    userId:string
    gameName:string
    quantity:number
    price:number
}
export interface shoppingCarItem {
    id: string        // 对应后端的 id
    userId:string
    gameName: string      // 对应后端的 gameName
    quantity:number
    price:number
    checked: boolean  // 前端专属的选中状态
}
export type shoppingCars = shoppingCar[];
export type shoppingCarItems = shoppingCarItem[];
