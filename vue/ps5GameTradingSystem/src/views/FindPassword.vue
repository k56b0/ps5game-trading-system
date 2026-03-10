<script setup lang="ts">
import PromptMsg from "@/components/PromptMsg.vue";
import {useUserStore} from '@/store/userStore.ts'
import {reactive, ref} from "vue";
import {storeToRefs} from "pinia";

// user pinia
const userStore =useUserStore()
const {errorMsg} = storeToRefs(userStore);
//提示组件 信息初始化
const toastMessage = ref('')
// 表单数据
const formDate = reactive({
  email: '',
  password: '',
  answer:'',
})
async function handleFind() {
  await userStore.findPassword(
      {
        email:formDate.email,
        answer:formDate.answer,
        password:formDate.password
      }
  )
  toastMessage.value=errorMsg.value
}
</script>

<template>
  <div class="container">
    <div class="card reset-card shadow">
      <div class="card-header bg-primary text-white">
        <h4 class="mb-0">找回密码</h4>
      </div>
      <div class="card-body">
        <form @submit.prevent="handleFind">
          <!-- 邮箱 -->
          <div class="mb-3">
            <label for="email" class="form-label">邮箱地址</label>
            <input type="email" class="form-control" v-model="formDate.email" placeholder="请输入注册邮箱" required>
          </div>

          <!-- 密保答案 -->
          <div class="mb-3">
            <label for="answer" class="form-label">密保答案</label>
            <input type="text" class="form-control" v-model="formDate.answer" placeholder="问题：您的小学是？" required>
            <div class="form-text">请输入您注册时设置的密保答案</div>
          </div>

          <!-- 新密码 -->
          <div class="mb-3">
            <label for="password" class="form-label">新密码</label>
            <input type="password" class="form-control" v-model="formDate.password" placeholder="至少6位" minlength="6" required>
          </div>

          <button type="submit" class="btn btn-primary w-100">重置密码</button>
        </form>
      </div>
      <div class="card-footer text-muted text-center">
        请重置密码
      </div>
    </div>
  </div>
  <PromptMsg :message="toastMessage" @clearMessage="toastMessage = ''" />
</template>

<style scoped>
.container{
  width: 600px;
}
</style>