<template>
  <!--顶部横幅图-->
    <div class="topBanner"></div>
  <!--  导航栏 开始-->
    <div class="navbar navbar-expand-lg  ">
      <div class="container-fluid">

        <div class="navbar-brand custom-navbar" href="" >PS5 游戏交易平台</div>

        <div class="collapse navbar-collapse "  >
          <ul class="navbar-nav">
            <li class="nav-item">
              <RouterLink to="/Home" class="nav-link" active-class="active">首页</RouterLink>
            </li>
            <li class="nav-item">
              <RouterLink  to="/Recycling" class="nav-link" active-class="active">二手回收</RouterLink>
            </li>
            <li class="nav-item">
              <RouterLink v-if="isLoginMark" to="/PersonalCenter/MyInfo" class="nav-link" active-class="active">个人中心</RouterLink>
              <RouterLink v-else to="/PromptPage" class="nav-link" active-class="active">个人中心</RouterLink>
            </li>
            <li class="nav-item">
              <RouterLink to="/ProblemFeedBack" class="nav-link" active-class="active">问题反馈</RouterLink>
            </li>
          </ul>
        </div>

        <div class="collapse navbar-collapse">
          <ul class="navbar-nav">
            <li class="nav-item" style="margin-left: 170px;">
              <RouterLink to="/SignUp" class="nav-link" >登录</RouterLink>
            </li>
            <li class="nav-item">
              <button class="nav-link" @click="userCheckIn">每日签到</button>
            </li>
          </ul>
          <div class="shoppingCart">
            <RouterLink v-if="isLoginMark" to="/ShoppingCart" class="nav-link" >
              <i class="bi bi-cart"></i>购物车( {{shoppingCarQuantity}} )
            </RouterLink>
            <RouterLink v-else to="/PromptPage" class="nav-link" >
              <i class="bi bi-cart"></i>购物车( {{shoppingCarQuantity}} )
            </RouterLink>
          </div>
        </div>
      </div>
    </div>
  <!--  导航栏 结束-->

  <!--展示区-->
  <div class="main-content">
    <RouterView></RouterView>
  </div>
  <!--尾部-->
  <footer>
    <div class="copy">
      <div class="bottomLogo"><img src="@/assets/images/手柄logo-1.jpg" alt="logo"></div>
      <div class="link">
        <a href="#">PS5交易商城</a>
        |
        <br>
        &copyPSTrade.com 京ICP证123456号 京ICP备123456号 京公网安备123456789 京网文[2026]1111-2222号
        <br>
        本网站所列数据，除特殊说明，所有数据均于自学习测试
      </div>
      <div class="verify">
        <img src="@/assets/images/底部/truste.png" alt="">
        <img src="@/assets/images/底部/v-logo-1.png" alt="">
        <img src="@/assets/images/底部/v-logo-2.png" alt="">
        <img src="@/assets/images/底部/v-logo-3.png" alt="">
      </div>
    </div>
    <div class="slogan"><img src="@/assets/images/底部/slogan2026.jpg" alt=""></div>

  </footer>
</template>

<script setup lang="ts">
// 引入 routerView,让路由器知道，把 视图放在哪里
import {RouterView,RouterLink}from 'vue-router'
import {useUserStore} from '@/store/userStore.ts'
/* 引入storeToRefs */
import { storeToRefs } from 'pinia'
import {useShoppingCarStore} from '@/store/shoppingCarStore.ts'
const useShoppingCar = useShoppingCarStore()
// 默认 没有登陆
// let isLoginMark = ref(false)
/* 使用storeToRefs转换useLoginStore()，随后解构 */
const {isLoginMark} = storeToRefs(useUserStore())
const {shoppingCarQuantity} = storeToRefs(useShoppingCar)
const {myInfo:{value:temp}} = storeToRefs(useUserStore())
function userCheckIn() {
  temp.checkIn +=1
}

</script>

<style scoped>

  .topBanner {
    width: 100%;
    height: 90px;
    background: url("@/assets/images/topBanner.jpg") no-repeat center center;
    cursor: pointer;
  }


  .custom-navbar{
    margin-left: 16%;
  }

  .main-content {
    margin: 0 auto;
    border-radius: 10px;
    width: 90%;
    height: auto;
    border: 1px solid;
  }
  /***************************尾部 footer start********************************/
  footer {
    margin: 0 auto;
    width: 90%;
    height: 160px;
    background-color: #fafafa;
    //border: 1px solid;
    border-radius: 10px;
  }

  .copy {
    width: 1226px;
    height: 57px;
    margin: 30px auto;
    box-sizing: border-box;
  }

  .copy .bottomLogo {
    float: left;
    margin-right: 10px;
  }

  .copy .link {
    width: 750px;
    height: 54px;
    float: left;
    color: #b0b0b0;
    font-size: 12px;
  }

  .copy .link a {
    color: #757575;
    font-size: 12px;
    line-height: 18px;
  }

  .copy .link a:hover {
    color: #ff6700;
  }

  .copy .verify {
    width: 380px;
    height: 28px;
    float: right;
    margin-top: 4px;
  }

  .copy .verify img {
    width: 85px;
    height: 28px;
    margin-left: 7px;
    cursor: pointer;
  }

  .slogan {
    width: 200px;
    height: 19px;
    margin: 0 auto;
  }

  /*************************** 尾部footer end********************************/

</style>

