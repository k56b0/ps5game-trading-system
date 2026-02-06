<script  setup lang="ts">
import { ref, reactive } from 'vue'
import { inject } from 'vue';
import type { AxiosInstance, AxiosResponse } from 'axios'
import {useLoginStore} from '@/store/login.ts'

const axios = inject('axios');
// 使用响应式状态
let isSignup = ref(false)

// 表单数据
const form = reactive({
  email: '',
  password: ''
})
// 定义组件事件
const emit = defineEmits(['login-success', 'login-error'])

// 切换状态
function toggleSignup(){
  isSignup.value = !isSignup.value
  console.log(isSignup.value)
}

//发送登陆请求
async function handleLogin(){
  // 重置表单
  const resetForm = () => {
    form.email = ''
    form.password = ''
  }
  // 表单验证
  if (!form.email.trim()) {
    alert('请输入邮箱')
    return
  }

  if (!form.password.trim()) {
    alert('请输入密码')
    return
  }

  // 邮箱格式验证
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
  if (!emailRegex.test(form.email)) {
    alert('请输入有效的邮箱地址')
    return
  }
  try {
    // 使用POST请求，数据放在请求体中
    const response = await (axios as AxiosInstance).post('/user/login', {
      email: form.email.trim(),
      passw: form.password
    })

    const result =  reactive(response.data)

    if (result.code === 200) {
      // 登录成功
     let user = result.data
      // // 保存用户信息到本地存储
      // localStorage.setItem('user', JSON.stringify(user))
      // 重置表单
      resetForm()
      alert('登录成功!')

      console.log('登录成功:', user)
      useLoginStore().isLoginMark=true
      useLoginStore().myInfo=user

    } else {
      // 登录失败
      const errorMsg = result.message || '登录失败，请检查邮箱和密码'
      emit('login-error', errorMsg)
      alert(errorMsg)
    }
  } catch (error) {
    console.error('登录请求失败:', error)

    let errorMessage = '登录失败，请稍后重试'

    if ((error as any).response) {
    emit('login-error', errorMessage)
    alert(errorMessage)
  }
}


}
// // 如果需要暴露方法给父组件
// defineExpose({
//   handleLogin
// })


</script>
<template>
<!--  使用响应式状态控制类名-->

  <div :class="['dowebok', { 's--signup': isSignup }]">


    <div class="form sign-in">
      <form @submit.prevent="handleLogin" >
        <h2>欢迎回来</h2>
        <label>
          <span>邮箱</span>
          <input v-model="form.email" type="email" />
        </label>
        <label>
          <span>密码</span>
          <input v-model="form.password" type="password" />
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
        <form action="#" method="post">
          <label>
            <span>用户名</span>
            <input type="text" />
          </label>
          <label>
            <span>邮箱</span>
            <input type="email" />
          </label>
          <label>
            <span>密码</span>
            <input type="password" />
          </label>
          <button type="button" class="submit">注 册</button>
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