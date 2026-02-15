<script setup lang="ts">
import {useRecycleStore} from '@/store/recycleStore.ts'
import {useUserStore} from '@/store/userStore.ts'
import {computed} from "vue";
import PageTurning from "@/components/PageTurning.vue";
import {storeToRefs} from "pinia";


const useRecycle =useRecycleStore()
const useUser = useUserStore()
const {myRecycles,current, pages}=storeToRefs(useRecycle)

// 处理页码变化
const currentPage = computed(() => current.value)
const allPage = computed(() => pages.value)
const handlePageChange = (newPage:number) => {
  // 更新 store 中的当前页码
  current.value = newPage   // 假设 store 中有 current 字段
  // 重新请求对应页码的数据
  useRecycle.queryAll({
    userId:useUser.myInfo.id,
    pageNum:newPage})
}
</script>

<template>
  <div class="container">
    <div class="table-responsive">
      <table class="table table-sm table-hover-custom">
        <thead>
        <tr>
          <th scope="col">序号</th>
          <th scope="col">游戏名</th>
          <th scope="col">版本</th>
          <th scope="col">语言</th>
          <th scope="col">外观</th>
          <th scope="col">包装盒</th>
          <th scope="col">估价</th>
          <th scope="col">回收状态</th>
        </tr>
        </thead>
        <tbody>
        <tr  v-for="(recycle, index) in myRecycles" :key="recycle.id">
          <th scope="row">{{index+1}}</th>
          <td>{{recycle.gameName}}</td>
          <td>{{recycle.version}}</td>
          <td>{{recycle.language}}</td>
          <td>{{recycle.appearance}}</td>
          <td>{{recycle.box}}</td>
          <td>{{recycle.valuation}}</td>
          <td>{{recycle.recyclingStatus}}</td>
        </tr>
        </tbody>
      </table>
    </div>
    <PageTurning
        :currentPage="currentPage"
        :allPage="allPage"
        @update:currentPage="handlePageChange"
    />
  </div>

</template>

<style scoped>

</style>