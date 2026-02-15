<script setup lang="ts">
import { useUserStore} from '@/store/userStore.ts'
import {reactive} from "vue";
import {storeToRefs} from "pinia";
// const {myInfo:{value:info}} = storeToRefs(useLoginStore())
const userStore = storeToRefs(useUserStore())

let myInfo=reactive({
  id:userStore.myInfo.value.id,
  userName:userStore.myInfo.value.userName,
  birth:userStore.myInfo.value.birth,
  address:userStore.myInfo.value.address,
  email:userStore.myInfo.value.email,
  password:userStore.myInfo.value.password,
  checkIn:userStore.myInfo.value.checkIn,
  gender:userStore.myInfo.value.gender,
  answer:userStore.myInfo.value.answer,
  checkTime:userStore.myInfo.value.checkTime,
  money:userStore.myInfo.value.money,
})

//发送修改请求
async function handleUpdate(){
  await useUserStore().update(myInfo)
}

</script>

<template>
  <div class="container">
<!--    水平表单-->
    <form @submit.prevent="handleUpdate">
    <!-- 使用 row 和 col-form-label -->
      <!-- 使用 row 和 mb-3（间距） -->
      <div class="row mb-md-1 py-sm-1">
        <label class="col-sm-2 col-form-label">用户名</label>
        <div class="col-sm-8">
          <input type="text" class="form-control"  placeholder="请输入用户名" v-model="myInfo.userName">
        </div>
      </div>
      <div class="row mb-md-1 py-sm-1">
        <label class="col-sm-2 col-form-label">生日</label>
        <div class="col-sm-8">
          <input type="date" class="form-control" placeholder="请输入生日" v-model="myInfo.birth">
        </div>
      </div>
      <div class="row mb-md-1 py-sm-1">
        <label class="col-sm-2 col-form-label">性别</label>
        <div class="col-sm-8">
          <input type="text" class="form-control"  placeholder="请输入性别" v-model="myInfo.gender">
        </div>
      </div>
      <div class="row mb-md-1 py-sm-1">
        <label class="col-sm-2 col-form-label">地址</label>
        <div class="col-sm-8">
          <input type="text" class="form-control" placeholder="请输入地址" v-model="myInfo.address">
        </div>
      </div>
      <div class="row mb-md-1 py-sm-1">
        <label class="col-sm-2 col-form-label">邮箱</label>
        <div class="col-sm-8">
          <input type="email" class="form-control"  v-model="myInfo.email" required>
        </div>
      </div>
      <div class="row mb-md-1 py-sm-1">
        <label class="col-sm-2 col-form-label">密码</label>
        <div class="col-sm-8">
          <input type="password" class="form-control" placeholder="请输入新密码" v-model="myInfo.password" required>
        </div>
      </div>
      <div class="row mb-md-1 py-sm-1">
        <label class="col-sm-2 col-form-label">签到天数</label>
        <div class="col-sm-8">
          <span >{{myInfo.checkIn}}</span>
        </div>
      </div>
      <div class="row mb-md-1 py-sm-1">
        <label class="col-sm-2 col-form-label">找回密码</label>
        <div class="col-sm-8">
          <span>问题设置：你的小学是什么？</span>
          <input type="text" class="form-control" placeholder="请输入答案" v-model="myInfo.answer" />
        </div>
      </div>

      <div class="row mb-md-1 py-sm-1">
        <div class="col-sm-2">
            <button  type="submit"  class="submit form-control">保存信息</button>
        </div>
      </div>
    </form>
  </div>

</template>

<style scoped>
.container{
  border: none;
}
.container form input{
  outline: none;
  background: none;
  border: none;
  border-bottom: solid black 1px;
  /*移除元素的外轮廓。不然你可以加一个框*/
}
.submit{
  margin-left: 400px;
  text-align: center;
}

</style>