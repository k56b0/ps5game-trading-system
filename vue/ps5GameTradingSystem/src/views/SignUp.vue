<script  setup lang="ts">
import { ref, reactive } from 'vue'
import {useUserStore} from '@/store/userStore.ts'
import {useOrderStore} from '@/store/orderStore.ts'
import {useRecycleStore} from '@/store/recycleStore.ts'
import {useRechargeRecordStore} from '@/store/rechargeRecordStore.ts'
import {useFavoriteStore} from '@/store/favoriteStore.ts'
import {useShoppingCarStore} from '@/store/shoppingCarStore.ts'
import { v4 as uuidv4 } from 'uuid'

// 使用响应式状态
let isSignup = ref(false)

// 表单数据
const formDate = reactive({
  email: '',
  password: '',
  userName:'',
})
// user pinia
const userStore =useUserStore()
const orderStore=useOrderStore()
const recycleStore =useRecycleStore()
const rechargeRecordStore =useRechargeRecordStore()
const useFavorite = useFavoriteStore()
const useShoppingCar = useShoppingCarStore()
// 切换登陆状态
function toggleSignup(){
  isSignup.value = !isSignup.value
}

//发送登陆请求
async function handleLogin(){
  // 重置表单
  const resetForm = () => {
    formDate.email = ''
    formDate.password = ''
  }
  // 表单验证
  if (!formDate.email.trim()) {
    alert('请输入邮箱')
    return
  }
  if (!formDate.password.trim()) {
    alert('请输入密码')
    return
  }
  // 邮箱格式验证
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
  if (!emailRegex.test(formDate.email)) {
    alert('请输入有效的邮箱地址')
    return
  }
  //联系后端，进行登陆验证，
  await userStore.login({
    email: formDate.email.trim(),
    password: formDate.password
  })
  //查询用户订单
  await orderStore.queryAll(
      {
        userId:userStore.myInfo.id,
        pageNum:1
      }
  )
  //查询用户回收记录
  await recycleStore.queryAll(
      {
        userId:userStore.myInfo.id,
        pageNum:1
      }
  )
  //查询用户充值记录
  await rechargeRecordStore.queryAll(
      {
        userId:userStore.myInfo.id,
        pageNum:1
      }
  )
  //查询收藏夹信息
  await useFavorite.queryAll(
      {
        userId:userStore.myInfo.id,
        pageNum:1
      }
  )
  //查询购物车信息
  await useShoppingCar.queryAll(
      {
        userId:userStore.myInfo.id,
        pageNum:1
      }
  )

  alert('登录成功!')
  // 重置表单
  resetForm()

}
//获取 UUID
const uuid = ref('');
const generateNewUUID = () => {
  uuid.value = uuidv4();
};

async function handleRegister(){
  generateNewUUID()
  await userStore.register(
      {
        id:uuid.value,
        userName:formDate.userName,
        email: formDate.email.trim(),
        password: formDate.password
      }
  )
}
</script>
<template>
<!--  使用响应式状态控制类名-->

  <div :class="['dowebok', { 's--signup': isSignup }]">


    <div class="form sign-in">
      <form @submit.prevent="handleLogin" >
        <h2>欢迎回来</h2>
        <label>
          <span>邮箱</span>
          <input v-model="formDate.email" type="email" />
        </label>
        <label>
          <span>密码</span>
          <input v-model="formDate.password" type="password" />
        </label>
        <p class="forgot-pass"><a href="javascript:">忘记密码？</a></p>
        <button type="submit" class="submit">登 录</button>
      </form>
      <button type="button" class="fb-btn">暂 不 登 录</button>

    </div>
    <div class="sub-cont">
      <div class="img">
        <div class="img__text m--up">
          <h2>还未注册？</h2>
          <p>马上注册，畅享更多服务！</p>
        </div>
        <div class="img__text m--in">
          <h2>已有帐号？</h2>
          <p>立即登录，老友重逢！</p>
        </div>
        <div class="img__btn">
          <button class="m--up" @click="toggleSignup" >注 册</button>
          <button class="m--in" @click="toggleSignup">登 录</button>
        </div>
      </div>
      <div class="form sign-up">
        <h2>立即注册</h2>
        <form @submit.prevent="handleRegister">
          <label>
            <span>用户名</span>
            <input type="text" v-model="formDate.userName"/>
          </label>
          <label>
            <span>邮箱</span>
            <input type="email" v-model="formDate.email" />
          </label>
          <label>
            <span>密码</span>
            <input type="password"  v-model="formDate.password"/>
          </label>
          <button type="submit" class="submit">注 册</button>
        </form>
        <button type="button" class="fb-btn">暂 不 注 册</button>
      </div>
    </div>
  </div>
  <div class="fill">
  </div>

</template>

<style scoped>
input, button {
  border: none;
  outline: none;
  background: none;
  font-family: 'Open Sans', Helvetica, Arial, sans-serif;
}

.tip {
  font-size: 20px;
  margin: 40px auto 50px;
  text-align: center;
}

.dowebok {
  overflow: hidden;
  position: absolute;
  left: 50%;
  top: 50%;
  width: 900px;
  height: 550px;
  margin: -300px 0 0 -450px;
  background: #fff;
}

.form {
  position: relative;
  width: 640px;
  height: 100%;
  transition: -webkit-transform 0.6s ease-in-out;
  transition: transform 0.6s ease-in-out;
  transition: transform 0.6s ease-in-out, -webkit-transform 0.6s ease-in-out;
  padding: 50px 30px 0;
}

.sub-cont {
  overflow: hidden;
  position: absolute;
  left: 640px;
  top: 0;
  width: 900px;
  height: 100%;
  padding-left: 260px;
  background: #fff;
  transition: -webkit-transform 0.6s ease-in-out;
  transition: transform 0.6s ease-in-out;
  transition: transform 0.6s ease-in-out, -webkit-transform 0.6s ease-in-out;
}

.dowebok.s--signup .sub-cont {
  -webkit-transform: translate3d(-640px, 0, 0);
  transform: translate3d(-640px, 0, 0);
}

button {
  display: block;
  margin: 0 auto;
  width: 260px;
  height: 36px;
  border-radius: 30px;
  color: #fff;
  font-size: 15px;
  cursor: pointer;
}

.img {
  overflow: hidden;
  z-index: 2;
  position: absolute;
  left: 0;
  top: 0;
  width: 260px;
  height: 100%;
  padding-top: 360px;
}

.img:before {
  content: '';
  position: absolute;
  right: 0;
  top: 0;
  width: 900px;
  height: 100%;
  background-image: url('@/assets/images/2026年1月.jpg');
  background-size: cover;
  transition: -webkit-transform 0.6s ease-in-out;
  transition: transform 0.6s ease-in-out;
  transition: transform 0.6s ease-in-out, -webkit-transform 0.6s ease-in-out;
}

.img:after {
  content: '';
  position: absolute;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.6);
}

.dowebok.s--signup .img:before {
  -webkit-transform: translate3d(640px, 0, 0);
  transform: translate3d(640px, 0, 0);
}

.img__text {
  z-index: 2;
  position: absolute;
  left: 0;
  top: 50px;
  width: 100%;
  padding: 0 20px;
  text-align: center;
  color: #fff;
  transition: -webkit-transform 0.6s ease-in-out;
  transition: transform 0.6s ease-in-out;
  transition: transform 0.6s ease-in-out, -webkit-transform 0.6s ease-in-out;
}

.img__text h2 {
  margin-bottom: 10px;
  font-weight: normal;
}

.img__text p {
  font-size: 14px;
  line-height: 1.5;
}

.dowebok.s--signup .img__text.m--up {
  -webkit-transform: translateX(520px);
  transform: translateX(520px);
}
.img__text.m--in {
  -webkit-transform: translateX(-520px);
  transform: translateX(-520px);
}

.dowebok.s--signup .img__text.m--in {
  -webkit-transform: translateX(0);
  transform: translateX(0);
}

.img__btn {
  overflow: hidden;
  z-index: 2;
  position: relative;
  width: 100px;
  height: 36px;
  margin: 0 auto;
  background: transparent;
  color: #fff;
  text-transform: uppercase;
  font-size: 15px;
  cursor: pointer;
}
.img__btn:after {
  content: '';
  /* z-index: 2;*/
  position: absolute;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  border: 2px solid #fff;
  border-radius: 30px;

  /* 新增：不拦截点击 */
  pointer-events: none;
  z-index: 1;
}

.img__btn button {
  position: absolute;
  left: 0;
  top: 0;
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 100%;
  transition: -webkit-transform 0.6s;
  transition: transform 0.6s;
  transition: transform 0.6s, -webkit-transform 0.6s;

  /* 确保按钮在伪元素之上 */
  z-index: 10;
  /* 确保可以点击 */
  pointer-events: auto;
}

.img__btn button.m--in {
  -webkit-transform: translateY(-72px);
  transform: translateY(-72px);
}

.dowebok.s--signup .img__btn button.m--in {
  -webkit-transform: translateY(0);
  transform: translateY(0);
}

.dowebok.s--signup .img__btn button.m--up {
  -webkit-transform: translateY(72px);
  transform: translateY(72px);
}

h2 {
  width: 100%;
  font-size: 26px;
  text-align: center;
}

label {
  display: block;
  width: 260px;
  margin: 25px auto 0;
  text-align: center;
}

label span {
  font-size: 12px;
  color: #909399;
  text-transform: uppercase;
}

input {
  display: block;
  width: 100%;
  margin-top: 5px;
  padding-bottom: 5px;
  font-size: 16px;
  border-bottom: 1px solid rgba(0, 0, 0, 0.4);
  text-align: center;
}

.forgot-pass {
  margin-top: 15px;
  text-align: center;
  font-size: 12px;
  color: #cfcfcf;
}

.forgot-pass a {
  color: #cfcfcf;
}

.submit {
  margin-top: 40px;
  margin-bottom: 20px;
  background: #d4af7a;
  text-transform: uppercase;
}

.fb-btn {
  border: 2px solid #d3dae9;
  color: #8fa1c7;
}
.fb-btn span {
  font-weight: bold;
  color: #455a81;
}

.sign-in {
  transition-timing-function: ease-out;
}
.dowebok.s--signup .sign-in {
  transition-timing-function: ease-in-out;
  transition-duration: 0.6s;
  -webkit-transform: translate3d(640px, 0, 0);
  transform: translate3d(640px, 0, 0);
}

.sign-up {
  -webkit-transform: translate3d(-900px, 0, 0);
  transform: translate3d(-900px, 0, 0);
}
.dowebok.s--signup .sign-up {
  -webkit-transform: translate3d(0, 0, 0);
  transform: translate3d(0, 0, 0);
}
.fill{
  position: relative;
  color: #fdfdfd;
  width: 400px;
  height: 1000px;
  border: none;
}

</style>