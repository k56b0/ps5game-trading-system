
<template>
  <div class="container-fluid wapper">
    <h4 class="title">问题反馈</h4>
    <br>
    <div class="row">
      <div class="col-xl-3 col-md-3 col-lg-3 col-xl-12">
        <!--  导航区-->
       <ul  class="list-group">
         <li class="list-group-item ">
           <button @click="changeList1" class="btn btn-outline-primary" >网站介绍</button>
         </li>
         <li class="list-group-item">
           <button @click="changeList2" class="btn btn-outline-primary">购买介绍</button>
         </li>
         <li class="list-group-item">
           <button @click="changeList3" class="btn btn-outline-primary">回收介绍</button>
         </li>
         <li class="list-group-item">
           <button @click="changeList4" class="btn btn-outline-primary">反馈问题</button>
         </li>
       </ul>

      </div>
      <div class="col-xl-9 col-md-9 col-lg-9 col-xl-9">
        <div class="panel-body" v-if="isVisible1">
          <!--  展示区-->
          <p v-for="item in feedBack1" :key="item.id">{{item.content}}</p>
        </div>
        <div class="panel-body" v-if="isVisible2">
          <!--  展示区-->
          <p v-for="item in feedBack2" :key="item.id">{{item.content}}</p>
        </div>
        <div class="panel-body" v-if="isVisible3">
          <!--  展示区-->
          <p v-for="item in feedBack3" :key="item.id">{{item.content}}</p>
        </div>
        <div class="panel-body" v-if="isVisible4">
          <!--  展示区-->
          <p v-for="item in feedBack4" :key="item.id">{{item.content}}</p>
        </div>
      </div>
    </div>
  </div>

</template>
<script setup lang="ts">
import {ref,reactive,onMounted} from "vue";
let isVisible1 = ref(true)
let isVisible2 = ref(false)
let isVisible3 = ref(false)
let isVisible4 = ref(false)
let feedBack1 =[
  {id:'id1',content:'🌟宝藏PS5游戏天地，快乐起飞！'},
  {id:'id2',content:'🌟海量热门新作、经典大作任你淘'},
  {id:'id3',content:'秒速到货，玩转次世代！💃'},
  {id:'id4',content:'二手回收，冒险即刻开启~🎮'},
]
let feedBack2= [
  {id:'id1',content:'挑选游戏，加入 购物车'},
  {id:'id2',content:'填写信息，轻松结算'},
  {id:'id3',content:'整理收藏夹，快速生成订单'},
  {id:'id4',content:'坐等收货，畅玩即刻开启！'},
]
let feedBack3= [
  {id:'id1',content:'填写回收游戏信息'},
  {id:'id2',content:'等待线上评估结果'},
  {id:'id3',content:'寄出游戏等待验货'},
  {id:'id4',content:'坐等验收成功'},
]
let feedBack4= [
  {id:'id1',content:'问题反馈邮箱：1234567@qq.com'},
  {id:'id2',content:'客服电话：12345678'},
]


function changeList1(){
      isVisible1.value=true
      isVisible2.value=false
      isVisible3.value=false
      isVisible4.value=false
}
function changeList2(){
      isVisible1.value=false
      isVisible2.value=true
      isVisible3.value=false
      isVisible4.value=false

}
function changeList3(){
      isVisible1.value=false
      isVisible2.value=false
      isVisible3.value=true
      isVisible4.value=false
}
function changeList4(){
      isVisible1.value=false
      isVisible2.value=false
      isVisible3.value=false
      isVisible4.value=true
}

</script>

<style scoped>

.row{
  display: grid;
  grid-template-columns: 1fr 4fr;
}
.panel-body{
  background-color: #6f9e6a;
  padding:20px;
  border-radius: 10px;
  height: 400px;

}
.panel-body p{
  text-align: center;
}
h4{
  background-color: orange;
  text-align: center;
}
.list-group-item{
  text-align: center;
}

</style>