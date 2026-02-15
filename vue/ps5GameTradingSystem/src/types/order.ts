export interface order {
    id:   string,
    userId: string,
    orderTime:  string,
    orderAddress: string,
    sum: number,
    orderDetails:orderDetails,
}
export interface orderDetail {
    id:   string,
    orderId: string,
    gameName:  string,
    price: number,
    quantity: number,
}
// 自定义类型
export type orderDetails = Array<orderDetail>
export type orders = order[]