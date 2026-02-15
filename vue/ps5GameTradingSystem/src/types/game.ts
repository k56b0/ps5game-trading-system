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
    year:number,
    img:string,
}
export type games=game[]