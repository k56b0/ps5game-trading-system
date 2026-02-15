<script setup lang="ts">
import PageTurning from "@/components/PageTurning.vue"
import {useRechargeRecordStore} from '@/store/rechargeRecordStore.ts'
import {useUserStore} from '@/store/userStore.ts'
import {computed, onMounted, onUnmounted, ref} from "vue"
import {storeToRefs} from "pinia"
import { v4 as uuidv4 } from 'uuid'
import dayjs from 'dayjs'

//时间
// currentTime 是 Ref<string> 类型，.value 为字符串
/**
 currentTime 已经是字符串：dayjs().format(...) 返回字符串，ref() 将其包装为响应式对象，
 在模板中直接使用 {{ currentTime }} 即可显示字符串。
 timer 不能是字符串：因为 setInterval 返回的是数值 ID，必须用数值才能清除。
 在 TypeScript 中明确类型可以避免此类错误。
 */
// currentTime 是 Ref<string> 类型，.value 为字符串
const currentTime = ref(dayjs().format('YYYY-MM-DD HH:mm:ss'))

// 正确声明 timer 类型：number 或 null（初始无定时器）
let timer: number | null = null

onMounted(() => {
  // 启动定时器，返回 number 类型的 ID
  timer = setInterval(() => {
    currentTime.value = dayjs().format('YYYY-MM-DD HH:mm:ss')
  }, 1000)
})

onUnmounted(() => {
  // 清除定时器前需确保 timer 不为 null
  if (timer !== null) {
    clearInterval(timer)
    timer = null // 可选，重置
  }
})
const useUser = useUserStore()
const useRechargeRecord =useRechargeRecordStore()
const {myRechargeRecords,current, pages}=storeToRefs(useRechargeRecord)
const {myInfo}=storeToRefs(useUser)

//充值的临时数字
let tempNumber =0
// 处理页码变化
const currentPage = computed(() => current.value)
const allPage = computed(() => pages.value)
const handlePageChange = (newPage:number) => {
  // 更新 store 中的当前页码
  current.value = newPage   // 假设 store 中有 current 字段
  // 重新请求对应页码的数据
  useRechargeRecord.queryAll({
    userId:useUser.myInfo.id,
    pageNum:newPage})
}

// 提交充值信息的方法
const uuid = ref('');
const generateNewUUID = () => {
  uuid.value = uuidv4();
};
async function handleUpdate(){
  generateNewUUID()
  await useRechargeRecord.save(
      {
        id:uuid.value,
        userId: myInfo.value.id,
        time: currentTime.value,
        quantity: tempNumber,
      }
  )
  //更新一下数据
  //查询用户充值记录
  await useRechargeRecord.queryAll(
      {
        userId:myInfo.value.id,
        pageNum:1
      }
  )
  //重新获得一下用户信息联系后端，进行登陆验证，
  await useUser.login({
    email: myInfo.value.email,
    password: myInfo.value.password
  })
}
</script>

<template>
  <div class="container mt-4">

    <div class=" row ">
      <div class="col-md-3">用户余额：</div>
      <div class="col-md-4">{{myInfo.money}} 元</div>
    </div>
    <div class="top-up row">
      <div class="col-md-3">充值金额</div>
      <div class="col-md-9">
        <form @submit.prevent="handleUpdate">
          <div class="row mb-md-1 py-sm-1">
            <div class="col-sm-8 ">
              <input min="0" type="number" class="form-control " v-model="tempNumber"  placeholder="请输入金额" >
              <button type="submit" class="form-control btn btn-primary" >确认充值</button>
            </div>
          </div>
        </form>
      </div>

    </div>
    <div class="account-record row  mt-4">
      <table class="table table-sm table-hover-custom">
        <thead>
        <tr>
          <th scope="col">序号</th>
          <th scope="col">充值时间</th>
          <th scope="col">金额</th>
        </tr>
        </thead>
        <tbody>
        <tr  v-for="(rechargeRecord, index) in myRechargeRecords" :key="rechargeRecord.id">
          <th scope="row">{{index+1}}</th>
          <td>{{rechargeRecord.time}}</td>
          <td>{{rechargeRecord.quantity}}</td>
        </tr>
        </tbody>
      </table>
      <PageTurning
          :currentPage="currentPage"
          :allPage="allPage"
          @update:currentPage="handlePageChange"
      />

    </div>
  </div>
</template>

<style scoped>




</style>