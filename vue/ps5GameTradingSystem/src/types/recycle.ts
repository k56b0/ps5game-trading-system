export interface recycle{
    id:   string,
    userId: string,
    gameName: string,
    valuation: number,
    recyclingStatus: string,
    version: string,
    language: string,
    appearance: string,
    box: string,
}
export interface recycleItem {
    id:   string,
    userId: string,
    gameName: string,
    valuation: number,
    recyclingStatus: string,
    version: string,
    language: string,
    appearance: string,
    box: string,
    checked: boolean  // 前端专属的选中状态
}
export type recycles =recycle[]
export type recycleItems = recycleItem[];