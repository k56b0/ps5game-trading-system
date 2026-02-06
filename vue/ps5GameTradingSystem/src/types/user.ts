//一种规范
//定义一个接口，用于限制person对象的具体属性
export interface userInterface {
    id:   number,
    name: string,
    birth:  string,
    address: string,
    email: string,
    passw: string,
    checkin: string,
    answer: string,
}

// 一个自定义类型
export type Persons = Array<userInterface>
// export type Persons =PersonInter[]