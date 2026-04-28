// 引入defineStore用于创建store
import {defineStore} from 'pinia'
import {reactive} from "vue";
import httpAxios from '@/utils/http'
import type {user, userItemMaps, users} from '@/types/user.ts'
// 定义并暴露一个store
export const useUserStore = defineStore('userStore',{
    // 动作
    actions:{
        async login(data:any){
            //开始发送请求
            try {
                // 使用POST请求，数据放在请求体中。
                // response.data就是后端的 Result 对象,response.data.data就是Result 对象的data属性

                const response = await httpAxios.post('/user/login', data)
                //取出response.data，即后端的 Result 对象，并且做成响应式
                const result =  response.data
                if (result.code === 200) {
                    // 登录成功
                    let user = result.data
                    // console.log('登录成功:', user)
                    //让pinia存一下
                    this.isLoginMark=true
                    this.myInfo=user
                    // ✅【新加】登录成功后保存到本地存储
                    localStorage.setItem('userInfo', JSON.stringify(user))
                    alert('登录成功')

                } else {
                    // 登录失败
                    const errorMsg = '登录失败，请检查邮箱和密码'
                    alert(errorMsg)
                }
            } catch (error) {
                console.error('登录请求失败:', error)
            }
        },
        async register(data:any){
            try {
                const response = await httpAxios.post('/user/register',data)
                //取出response.data，即后端的 Result 对象，并且做成响应式
                const result =  response.data
                if (result.code === 200) {
                    console.log("success")
                }
            }catch (error) {
                console.error('更新失败:', error)
            }
        },
        async findPassword(data:any){
            try {
                const response = await httpAxios.post('/user/findPassword',data)
                //取出response.data，即后端的 Result 对象，并且做成响应式
                const result =  response.data
                if (result.code === 200) {
                    this.errorMsg = '修改密码成功'
                    console.log("success")

                }else{
                    this.errorMsg = result.message
                    console.log(this.errorMsg)
                }
            }catch (error) {
                console.error('更新失败:', error)
            }
        },
        async queryAll(data: { pageNum: number }) {
            try {
                // 根据实际 baseURL 调整路径
                const response = await httpAxios.get(`/user/queryAll/${data.pageNum}`);
                const result = response.data;

                if (result.code === 200) {
                    // 将records存入响应式变量
                    this.myUsers = result.data.records
                    //存入翻页信息 直接赋值给 state 属性，Pinia 会自动处理响应性
                    this.current = result.data.current;
                    this.pages = result.data.pages;
                    this.total = result.data.total;
                    //一个 action 来获取后端数据并转换成前端所需格式
                    // 将后端数据映射为前端所需格式
                    this.myUsersMap = this.myUsers.map(item => ({
                        id:   item.id,
                        userName: item.userName,
                        gender: item.gender,
                        birth: item.birth,
                        password: item.password,
                        checkIn: item.checkIn,
                        email: item.email,
                        address: item.address,
                        answer: item.answer,
                        checkTime: item.checkTime,
                        money: item.money,
                        isManage:item.isManage,
                        checked: false // 初始化未选中
                    }))
                } else {
                    console.error('查询失败', result.message);
                }
            } catch (error) {
                console.error('网络错误或请求异常:', error);
            }
        },
        // 修改 update 方法
        async update(data: any) {
            try {
                const response = await httpAxios.post('/user/update', data);
                const result = response.data;
                if (result.code === 200) {
                    this.myInfo = result.data;
                    console.log("更新成功");
                    return true;   // ✅ 添加返回 true
                } else {
                    console.error("更新失败", result.message);
                    return false;  // ✅ 添加返回 false
                }
            } catch (error) {
                console.error('更新失败:', error);
                return false;    // ✅ 添加返回 false
            }
        },

// 修改 delete 方法
        async delete(id: string) {
            try {
                const response = await httpAxios.delete(`/user/delete/${id}`);
                const result = response.data;
                if (result.code === 200) {
                    console.log("删除成功");
                    return true;   // ✅ 添加返回 true
                } else {
                    console.error("删除失败", result.message);
                    return false;  // ✅ 添加返回 false
                }
            } catch (error) {
                console.error('删除失败:', error);
                return false;    // ✅ 添加返回 false
            }
        },

// 修改 add 方法（如果你之前没加返回值，一并加上）
        async add(user: any) {
            try {
                const response = await httpAxios.post('/user/add', user);
                const result = response.data;
                if (result.code === 200) {
                    console.log('添加用户成功');
                    return true;
                } else {
                    console.error('添加用户失败', result.message);
                    return false;
                }
            } catch (error) {
                console.error('网络错误', error);
                return false;
            }
        },
        //签到方法
        async checkIn(id: string) {
            try {
                // 后端使用 @RequestParam，所以用 params 传递
                const response = await httpAxios.post('/user/checkIn', null, { params: { id } });
                const result = response.data;
                if (result.code === 200) {
                    // 更新 store 中的签到天数和签到时间
                    this.myInfo.checkIn = result.data;     // 假设后端返回最新签到天数
                    this.myInfo.checkTime = new Date().toISOString().slice(0, 19).replace('T', ' ');
                    console.log('签到成功');
                    return true;
                } else {
                    console.error('签到失败', result.message);
                    return false;
                }
            } catch (error) {
                console.error('签到请求失败:', error);
                return false;
            }
        },
        // ✅【直接加进你的 actions】
        logout() {
            this.isLoginMark = false
            this.myInfo = reactive({
                id:'',
                userName:'',
                gender:'',
                birth:'2025-01-01',
                password:'默认值',
                checkIn:'0',
                email:'1111111@qq.com',
                address:'默认值',
                answer:'',
                checkTime:'2025-01-01 00:00:00 ',
                money:0,
                isManage:1
            })
            // ✅【新加】退出时清除本地存储
            localStorage.removeItem('userInfo')
        }
    },
    // 状态
    state(){
        return {
            errorMsg:'',
            current:0,
            pages:0,
            total:0,
            myUsers: [] as users,  // 类型断言
            myUsersMap:<userItemMaps>([]),
            isLoginMark: localStorage.getItem('userInfo') ? true : false,
            myInfo: localStorage.getItem('userInfo')
                ? JSON.parse(localStorage.getItem('userInfo')!)
                : reactive<user>({
                    id:'',
                    userName:'',
                    gender:'',
                    birth:'2025-01-01',
                    password:'默认值',
                    checkIn:'0',
                    email:'1111111@qq.com',
                    address:'默认值',
                    answer:'',
                    checkTime:'2025-01-01 00:00:00 ',
                    money:0,
                    isManage:1
                })
        }
    },
    // 计算
    getters:{}
})