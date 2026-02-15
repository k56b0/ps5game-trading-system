<script setup lang="ts">
import {RouterLink}from 'vue-router'
</script>

<template>
  <!--热销商品开始-->
  <div class="container">
    <div class="row">
      <div class="col col-md-10 align-self-start">
        热销商品
      </div>
      <div class="col align-self-end">
        <RouterLink to="/SearchGames"  class="nav-link" >
            查看全部 <i class="bi bi-arrow-up-right-square"></i>
        </RouterLink>
      </div>
    </div>
    <ul>
      <li>
        <div><img src="@/assets/images/游戏封面图/双影奇境-1.jpg" alt=""></div>
        <div class="description">
          <h3>双影奇境</h3>
          <p>多人</p>
          <h4>139元</h4>
        </div>
      </li>
      <li>
        <div><img src="@/assets/images/游戏封面图/天国拯救2-1.jpg" alt=""></div>
        <div class="description">
          <h3>天国：拯救2</h3>
          <p>角色扮演</p>
          <h4>228元</h4>
        </div>
      </li>
      <li>
        <div><img src="@/assets/images/游戏封面图/最终幻想7重制版-1.jpg" alt=""></div>
        <div class="description">
          <h3>最终幻想7重制版</h3>
          <p>角色扮演</p>
          <h4>98元</h4>
        </div>
      </li>
      <li>
        <div><img src="@/assets/images/游戏封面图/真三国无双起源-1.jpeg" alt=""></div>
        <div class="description">
          <h3>真三国无双起源</h3>
          <p>动作</p>
          <h4>349元</h4>
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
  height: 100px;
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