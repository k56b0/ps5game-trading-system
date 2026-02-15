<script setup lang="ts">
// 引入 routerView,让路由器知道，把 视图放在哪里
import {RouterView,RouterLink}from 'vue-router'
</script>

<template>
  <div class="container-fluid">
<!--    导航区-->
    <div class=" Item1">
      <span class="text"><i class="bi bi-playstation"></i>  个人中心</span>
    </div>
    <div class=" Item3">
      <br>
      <ul class="list-group custom-item ">
        <RouterLink to="/PersonalCenter/MyInfo"  class="list-group-item" active-class="active">我的信息</RouterLink>
        <RouterLink to="/PersonalCenter/MyOrder"  class="list-group-item" active-class="active">订单信息</RouterLink>
        <RouterLink to="/PersonalCenter/MyFavorites"  class="list-group-item" active-class="active">收藏夹</RouterLink>
        <RouterLink to="/PersonalCenter/MyRecycle"  class="list-group-item" active-class="active">回收记录</RouterLink>
        <RouterLink to="/PersonalCenter/MyWallet"  class="list-group-item" active-class="active">我的钱包</RouterLink>
      </ul>
    </div>
    <div class=" Item4 ">
          <!--   展示区-->
          <RouterView></RouterView>
    </div>
  </div>

</template>

<style scoped>
/*展示个人主页 盒子 grid布局*/
.container-fluid {
  width: 100%;
  height:auto;
  position: relative;
  top: 10px;
  left: 14%;
  overflow: auto;
  /*transform: translate(-20%, -20%);*/
  /*justify-content: center;*/
  display: grid;
  grid-template-areas:
             "title1 title2"
            " list  content"; /* 2.区域划分 当前为 两行 两列 */
  grid-template-columns: 150px 1000px ;
  grid-template-rows: 40px 700px;

}
.row {
  border: black 1px solid;
  border-radius: 2px;
  background-color: lightblue;
  /*文字对齐模式：居中*/
  text-align: center;
}
.Item1{
  grid-area: title1;

}
.Item3{
  grid-area: list;
}
.Item4{
  grid-area: content;

}

/*设置文字样式*/
.text{
  font-size: 20px;
}
a{
  color:#df5000;
}
</style>