<script setup lang="ts">

import PageTurning from "@/components/PageTurning.vue";
import {useRoute, useRouter} from "vue-router";
import {computed, onMounted, ref} from "vue";
import {useGameStore} from '@/store/gameStore.ts'
import {storeToRefs} from "pinia";
const router = useRouter()
const route = useRoute()
const useGame = useGameStore()
const {Games,current, pages,currentCategory,categories}=storeToRefs(useGame)

let searchText = ref('')
/*处理页码变化 开始*/
const currentPage = computed(() => current.value)
const allPage = computed(() => pages.value)
const handlePageChange = async (newPage: number) => {
  // current.value = newPage   // 假设 store 中有 current 字段
  if (currentCategory.value) {
    // 如果当前有选中分类，则调用分类查询
    await useGame.queryCategory(currentCategory.value, newPage)
  } else {
    // 否则调用全部查询
    await useGame.queryAll(newPage)
  }
}
/*处理页码变化 结束*/
// 查询全部游戏
onMounted(async ()=>{
  if(route.query.searchText === undefined && route.query.categoryText=== undefined){
      await useGame.queryAll(1)
  }else  {
    const categoryText = route.query.categoryText;
    const searchText = route.query.searchText;
    // 如果参数存在且不为空字符串（或允许空字符串则去掉 trim 检查）
    if (searchText && typeof searchText === "string" && searchText.trim() !== '') {
      await queryByName(searchText as string);
    } else if(categoryText && typeof categoryText === "string" && categoryText.trim() !== ''){
      await showCategory(categoryText)
    }
    else {
      await useGame.queryAll(1);
    }
  }



})
//计算折扣价——既读取又修改
function computedPrice(val1:number,val2:number){
  return (val1*val2).toFixed(0)
}

async function showCategory(choice: string) {
  // 调用分类查询，从第 1 页开始
  await useGame.queryCategory(choice, 1)
  // 清除 URL 中的 searchText 参数（例如跳回首页，但保留其他参数）
  router.replace({ query: { ...route.query, categoryText: undefined } });
}

async function queryByName(name: string) {
  await useGame.queryByName(name, true)
  // 清除 URL 中的 searchText 参数（例如跳回首页，但保留其他参数）
  router.replace({ query: { ...route.query, searchText: undefined } });
}
//查询游戏详情
async function showDetail(gameName:string) {
  try {
    // 1. 先查询游戏详情（等待完成）
    await useGame.queryByName(gameName,false)
    await router.push('/GameDetails')
  } catch (error) {
    console.error('查询游戏详情失败', error)
    // 可在此处处理错误，例如提示用户
  }
}

async function goHome() {
  // useGame.resetFilters()   // 重置所有筛选条件
  await useGame.queryAll(1) // 重新查询第一页
}
</script>

<template>
  <div  class="container">
    <div class="top  bg-dark navbar ">
       <form >
            <input class=" " type="text" placeholder="请输入游戏名" aria-label="Search" v-model="searchText">
            <button class="btn btn-outline-success " type="button" @click="queryByName(searchText)">Search</button>
      </form>
    </div>
    <div class="index-right">
      <ul>
        <li  v-for="(game) in Games" :key="game.id">
          <img :src="game.img" alt="" @click="showDetail(game.gameName)">
          <div class="description">
            <h5 class="text-primary">{{game.gameName}}</h5>
            <h5 class="text-success" >{{computedPrice(game.price,game.discount)}}元</h5>
          </div>
        </li>
      </ul>
      <PageTurning
          class="pageTurning"
          :currentPage="currentPage"
          :allPage="allPage"
          @update:currentPage="handlePageChange"
      />

    </div>

    <div class="left ">
        <ul class="list-inline ">
          <li class="list-inline-item" @click="showCategory('销量排序')" >
            <span class="text-primary">按销量排序</span>
            <i class="bi bi-arrows-vertical active"></i>
          </li>
          <li class="list-inline-item" @click="showCategory('价格排序')">
            <span class="text-primary">按价格排序</span>
            <i class="bi bi-arrows-vertical active"></i>
          </li>
        </ul>
    </div>

    <div class="filter-wrapper ">
        <div class="filter-block">
          <div class="text-primary">发售类型</div>
          <ul class="filter-item-wrapper list-group">
            <li title="全部" class="list-group-item" @click="goHome">全部</li>
            <li title="新品" class="list-group-item" @click="showCategory('新品')">新品</li>
            <li title="即将发售" class="list-group-item"  @click="showCategory('即将发售')">即将发售</li>
            <li title="热门" class="list-group-item" @click="showCategory('热门')">热门</li>
            <!---->
          </ul>
        </div>
      <br>
        <div class="filter-block">
          <div class="text-primary">所有类型和主题</div>
          <ul class="filter-item-wrapper list-group ">
            <li title="全部" class="list-group-item" @click="goHome">全部</li>
            <li title="" class="list-group-item" @click="showCategory(c)" v-for="(c) in categories"  :key="c" >{{ c }}</li>
            <!---->
          </ul>

        </div>

      </div>

    </div>

</template>

<style scoped>
.container{
  display:grid;
  grid-template-areas:
             "top    top"
             "left   right"
             "filter right";
  grid-template-columns:  20% 80% ;
  grid-template-rows:     50px 40px auto ;
  width: 1200px;
  height: auto;
  text-align: center;
  position: relative;

}
.top{
  grid-area: top;
  display:flow;
}
.left{
  grid-area: left;
  border: black 1px solid;
  border-radius: 2px;
  background-color: lightblue;
  /*文字对齐模式：居中*/
  text-align: center;

}
.index-right{
  grid-area: right;
  /*文字对齐模式：居中*/
  text-align: center;
  min-height: 666px;
}


.filter-wrapper{
  grid-area: filter;
  border-radius: 2px;
  /*文字对齐模式：居中*/
  text-align: center;
}
/*销售类型 所有类型和主题的筛选*/
.filter-block .filter-item-wrapper{
  display: grid;
  grid-template-columns:  1fr 1fr ;
  /*  自动填充 行高都为 50px  */
  grid-auto-rows: 30px;
  border-radius: 2px;

}
.index-right ul{
  display: grid;
  grid-template-columns:  220px 220px 220px 220px  ;
  grid-gap: 10px;
  /*  自动填充 行高都为 50px  */
  grid-auto-rows: 260px;
  list-style: none;
}
.index-right ul  li{
  padding-top: 10px;
}
.index-right ul li img{
  padding-top: 10px;
}
.index-right ul li img:hover{
  transform: translateY(-5px);
  box-shadow: 0 15px 40px rgba(0, 0, 0, 0.12);
  cursor: pointer
}
.list-group-item:hover{
  cursor: pointer;          /* 悬停时变手形 */
  background-color: #af6060; /* 可选：加背景反馈 */
}
img{
  width:180px ;
  height: 200px;
}
.pageTurning{
  margin-left: 300px;
}
.list-group-item{
  margin:0;
  width: 120px;
  height: 40px;
}
.list-inline-item{
  margin-top: 8px;
  padding: 0;
}
.list-inline-item:hover{
  background-color: #872f56;
  cursor: pointer
}

</style>