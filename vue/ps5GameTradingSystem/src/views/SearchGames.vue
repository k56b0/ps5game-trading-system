<script setup lang="ts">

import PageTurning from "@/components/PageTurning.vue";
import {RouterLink} from "vue-router";
</script>

<template>
  <div  class="container">
    <div class="top  bg-dark navbar ">
       <form >
            <input class=" " type="text" placeholder="请输入游戏名" aria-label="Search">
            <button class="btn btn-outline-success " type="submit">Search</button>
      </form>
    </div>
    <div class="index-right">
      <!--        content盒子-->
      <ul>
        <li>
          <img src="https://cdn.shopify.com/s/files/1/0151/1123/files/Final-Fantasy-VII-Remake-Intergrade-R2_800x800.jpg?v=1697432701" alt="">
          <div class="description">
            <h5 class="text-primary">最终幻想7重制版</h5>
            <h5 class="text-success">149元</h5>
          </div>
        </li>
        <li>
        <img src="https://cdn.shopify.com/s/files/1/0151/1123/files/Final-Fantasy-VII-Remake-Intergrade-R2_800x800.jpg?v=1697432701" alt="">
        <div class="description">
          <h5 class="text-primary">最终幻想7重制版</h5>
          <h5 class="text-success">149元</h5>
        </div>
      </li>
        <li>
          <img src="https://cdn.shopify.com/s/files/1/0151/1123/files/Final-Fantasy-VII-Remake-Intergrade-R2_800x800.jpg?v=1697432701" alt="">
          <div class="description">
            <h5 class="text-primary">最终幻想7重制版</h5>
            <h5 class="text-success">149元</h5>
          </div>
        </li>
        <li>
          <img src="https://cdn.shopify.com/s/files/1/0151/1123/files/Final-Fantasy-VII-Remake-Intergrade-R2_800x800.jpg?v=1697432701" alt="">
          <div class="description">
            <h5 class="text-primary">最终幻想7重制版</h5>
            <h5 class="text-success">149元</h5>
          </div>
        </li>
        <li>
          <img src="https://cdn.shopify.com/s/files/1/0151/1123/files/Final-Fantasy-VII-Remake-Intergrade-R2_800x800.jpg?v=1697432701" alt="">
          <div class="description">
            <h5 class="text-primary">最终幻想7重制版</h5>
            <h5 class="text-success">149元</h5>
          </div>
        </li>
        <li>
          <img src="https://cdn.shopify.com/s/files/1/0151/1123/files/Final-Fantasy-VII-Remake-Intergrade-R2_800x800.jpg?v=1697432701" alt="">
          <div class="description">
            <h5 class="text-primary">最终幻想7重制版</h5>
            <h5 class="text-success">149元</h5>
          </div>
        </li>
      </ul>
      <PageTurning/>

    </div>

    <div class="left ">
        <ul class="list-inline ">
          <li class="list-inline-item">
            <span>按销量排序</span>
            <i class="bi bi-arrows-vertical active"></i>
          </li>
          <li class="list-inline-item">
            <span>按价格排序</span>
            <i class="bi bi-arrows-vertical active"></i>
          </li>
        </ul>
    </div>

    <div class="filter-wrapper ">
        <div class="filter-block">
          <div class="text-primary">发售类型</div>
          <ul class="filter-item-wrapper list-group">
            <li title="全部" class="list-group-item">全部</li>
            <li title="新品" class="list-group-item">新品</li>
            <li title="即将发售" class="list-group-item">即将发售</li>
            <li title="热门" class="list-group-item">热门</li>
            <!---->
          </ul>
        </div>

        <div class="filter-block">
          <div class="text-primary">所有类型和主题</div>
          <ul class="filter-item-wrapper list-group ">
            <li title="全部" class="list-group-item">全部</li>
            <li title="" class="list-group-item">射击</li>
            <li title="" class="list-group-item">建造 模拟</li>
            <li title="" class="list-group-item">回合制策略</li>
            <li title="" class="list-group-item">体育模拟</li>
            <li title="" class="list-group-item">恐怖</li>
            <li title="" class="list-group-item">休闲</li>
            <li title="" class="list-group-item">策略</li>
            <li title="" class="list-group-item">即时战略</li>
            <li title="" class="list-group-item">竟速</li>
            <li title="" class="list-group-item">科幻</li>
            <li title="" class="list-group-item">JRPG</li>
            <li title="" class="list-group-item">恋爱模拟</li>
            <li title="" class="list-group-item">塔防</li>
            <li title="" class="list-group-item">竟速模拟</li>
            <li title="" class="list-group-item">太空</li>
            <li title="" class="list-group-item">解谜</li>
            <li title="" class="list-group-item">农场模拟</li>
            <li title="" class="list-group-item">卡牌及桌游</li>
            <li title="" class="list-group-item">开放世界</li>
            <li title="" class="list-group-item">城市营造</li>
            <li title="" class="list-group-item">动漫</li>
            <li title="" class="list-group-item">视觉小说</li>
            <li title="" class="list-group-item">生存</li>
            <li title="" class="list-group-item">格斗及武术</li>
            <li title="" class="list-group-item">剧情丰富</li>
            <li title="" class="list-group-item">团队制</li>
            <li title="" class="list-group-item">沙盒</li>
            <li title="" class="list-group-item">体育</li>
            <li title="" class="list-group-item">悬疑及推理</li>
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
.index-right ul li{
  padding-top: 10px;
}
.list-group-item:hover{
  cursor: pointer;          /* 悬停时变手形 */
  background-color: #af6060; /* 可选：加背景反馈 */
}
img{
  width:180px ;
  height: 200px;
}

</style>