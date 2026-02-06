<script setup lang="ts">
import {useLoginStore} from '@/store/login.ts'
import {reactive} from "vue";
import {storeToRefs} from "pinia";

// const {myInfo:{value:info}} = storeToRefs(useLoginStore())
const loginStore = storeToRefs(useLoginStore())

let myInfo=reactive({
  id:loginStore.myInfo.value.id,
  name:loginStore.myInfo.value.name,
  birth:loginStore.myInfo.value.birth,
  address:loginStore.myInfo.value.address,
  email:loginStore.myInfo.value.email,
  passw:loginStore.myInfo.value.passw,
  checkin:loginStore.myInfo.value.checkin,
  answer:loginStore.myInfo.value.answer,
})

</script>

<template>
  <div class="divItem Item5">
    <form action="#" method="post">
      <div class="container">
        <div class="row ">
          <div class="col ">
            <span>用户名</span>
          </div>
          <div class="col">
            <input type="text" :value="myInfo.name" />
          </div>
        </div>
        <div class="row">
          <div class="col">
            <span>生日</span>
          </div>
          <div class="col">
            <input type="date" :value="myInfo.birth"  />
          </div>
        </div>

        <div class="row">
          <div class="col">
            <span>地址</span>
          </div>
          <div class="col">
            <input type="text" :value="myInfo.address"  />
          </div>
        </div>
        <div class="row">
          <div class="col">
            <span>邮箱</span>
          </div>
          <div class="col">
            <input type="email" :value="myInfo.email" />
          </div>
        </div>

        <div class="row">
          <div class="col">
            <span>密码</span>
          </div>
          <div class="col">
            <input type="password" :value="myInfo.passw" required/>
          </div>
        </div>

        <div class="row">
          <div class="col">
            <span>已签到天数</span>
          </div>
          <div class="col">
            <span>{{myInfo.checkin}}</span>
          </div>
        </div>

        <div class="row">
          <div class="col">
            <span>找回密码的问题:</span>
            <span>你的小学是什么？</span>
          </div>
          <div class="col">
            <input type="text" :value="myInfo.answer" />
          </div>
        </div>
        <div class="row">
          <div class="col">
            <button  type="submit" class="submit">保存</button>
          </div>
        </div>
      </div>
    </form>
  </div>
</template>

<style scoped>
.divItem {
  border: black 1px solid;
  border-radius: 2px;
  background-color: lightblue;
  /*文字对齐模式：居中*/
  text-align: center;
}
/*个人信息开始*/
.Item5{
  border: none;

}
.Item5 form input{
  outline: none;
  background: none;
  border: none;
  border-bottom: solid black 1px;
  /*移除元素的外轮廓。不然你可以加一个框*/
}
/*个人信息 end*/

</style>