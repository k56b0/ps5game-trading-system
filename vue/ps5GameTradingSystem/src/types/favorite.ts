export interface favorite {
    id:string
    userId:string
    gameName:string
}
export interface favoriteItem {
    id: string        // 对应后端的 id
    gameName: string      // 对应后端的 gameName
    checked: boolean  // 前端专属的选中状态
}
export type favorites = favorite[];
export type favoriteItems = favoriteItem[];