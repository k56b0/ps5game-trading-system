<script setup lang="ts">
import {RouterLink}from 'vue-router'
import {useGameStore} from '@/store/gameStore.ts'
import {useRouter} from "vue-router";
import {computed, onMounted, ref} from "vue";
import {storeToRefs} from "pinia";
const { title }=defineProps(['title'])
const router = useRouter()
const useGame = useGameStore()

const {PushPageGames1,PushPageGames2,PushPageGames3}=storeToRefs(useGame)

const PushPageGames = computed(() => {
  switch (title) {
    case '热销商品': return PushPageGames1.value
    case '新品上市': return PushPageGames2.value
    default: return PushPageGames3.value
  }
})

onMounted(() => {
  useGame.queryPushPage(title)
})
//计算折扣价——既读取又修改
function computedPrice(val1:number,val2:number){
  return (val1*val2).toFixed(0)
}

//查询游戏详情
async function showDetail(gameName:string) {
  try {
    // 1. 先查询游戏详情（等待完成）
    await useGame.queryByName(gameName)
    // 2. 查询成功后跳转到详情页
    // 方式一：通过 query 传递参数（适用于任何路由配置）
    // router.push({ path: '/GameDetails', query: { name: gameName } })
    // 方式二：如果路由配置了动态参数（如 /GameDetails/:gameName），可以使用 params
    // router.push({ name: 'GameDetails', params: { gameName } })
    // 方式三：如果不需要参数，直接跳转
    router.push('/GameDetails')
  } catch (error) {
    console.error('查询游戏详情失败', error)
    // 可在此处处理错误，例如提示用户
  }
}
</script>

<template>
  <!--热销商品开始-->
  <div class="container">
    <div class="row">
      <div class="col col-md-10 align-self-start">
        {{title}}
      </div>
      <div class="col align-self-end">
        <RouterLink to="/SearchGames"  class="nav-link" >
            查看全部 <i class="bi bi-arrow-up-right-square"></i>
        </RouterLink>
      </div>
    </div>
    <ul>
      <li v-for="(game) in PushPageGames  ">
        <div><img :src="game.img" alt="" @click="showDetail(game.gameName)"></div>
        <div class="description">
          <h3>{{game.gameName}}</h3>
          <p>{{game.category}}</p>
          <h4>{{computedPrice(game.price,game.discount)}}元</h4>
        </div>
      </li>


    </ul>
  </div>
  <!--热销商品结束-->
</template>

<style scoped>
/***************************热销商品 start********************************/
.container {
  width: 1226px;
  height: 310px;
  margin: 0 auto;
}

.container ul {
  width: 1226px;
  height: 250px;
  margin-bottom: 40px;
  list-style: none;
}

.container ul li {
  width: 234px;
  height: 240px;
  float: left;
  background: #fafafa;
  box-sizing: border-box;
  padding-top: 35px;
  margin-right: 14px;
  text-align: center;
  transition: all .6s;
  list-style: none;
}

.container ul li img {
  width: 160px;
  margin: 0 auto 10px;
  height: 140px;
  cursor: pointer;
}

.container ul li .description h3 {
  margin: 0 20px 3px;
  font-size: 14px;
  font-weight: 400;
  cursor: pointer;
}

.container ul li .description p {
  height: 18px;
  margin: 0 20px 12px;
  font-size: 12px;
  color: #b0b0b0;
}

.container ul li .description h4 {
  color: #ff6709;
  font-weight: normal;
}

.container ul li:nth-child(1) {
  border-top: 1px solid #ffac13;
}

.container ul li:nth-child(2) {
  border-top: 1px solid #83c44e;
}

.container ul li:nth-child(3) {
  border-top: 1px solid #2196f3;
}

.container ul li:nth-child(4) {
  border-top: 1px solid #e53935;
}



</style>