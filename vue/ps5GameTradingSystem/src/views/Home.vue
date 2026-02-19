<script setup lang="ts">
import ShowGoods from "@/components/ShowGoods.vue";
import {useRouter} from 'vue-router'
import {ref} from "vue";
const router = useRouter()
let searchText = ref('')
function searchByName() {
  router.push(
      {
        path:'/SearchGames',
        query:{
          searchText:searchText.value
        }
      }
  ) // 或者你想去的默认页面
}
const list=["角色扮演","射击","动作","体育","竞速","恐怖","经营","联机","开放世界"
]

function searchByCategory(categoryText:string) {
  router.push(
      {
        path:'/SearchGames',
        query:{
          categoryText:categoryText
        }
      }
  ) // 或者你想去的默认页面
}
</script>

<template>
  <div class="container-fluid wapper">
    <div class="navbar navbar-expand-lg bg-body-tertiary ">
      <div class="container-fluid">

        <div class="navbar-brand custom-navbar" href="" >
                  <span class="navbar-toggler-icon"></span>
        </div>
        <div class="collapse navbar-collapse custom-collapse " >
          <ul class="navbar-nav">
            <li class="nav-item">
              <a class="nav-link" href="#section1">新品上市</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="#section2">即将推出</a>
            </li>
          </ul>
        </div>
        <div class="collapse navbar-collapse"  >
          <div class="search">
            <input type="search" v-model="searchText" placeholder="按照游戏名搜索">
            <button type="submit" @click="searchByName">
              <i  class="bi bi-search"></i>
            </button>
          </div>
        </div>

      </div>
    </div>
    <div class="banner-container">
      <!-- 左侧菜单 -->
      <div class="side-menu">
        <ul>
          <li v-for="(item) in list" :key="item" @click="searchByCategory(item)">
            <a class="add">{{item}}</a><span>></span></li>
        </ul>
      </div>

      <!-- Bootstrap轮播图 开始 -->
      <div id="gameCarousel" class="carousel slide" data-bs-ride="carousel" data-bs-interval="2400">
        <!-- 指示器 -->
        <div class="carousel-indicators">
          <button type="button" data-bs-target="#gameCarousel" data-bs-slide-to="0" class="active"></button>
          <button type="button" data-bs-target="#gameCarousel" data-bs-slide-to="1"></button>
          <button type="button" data-bs-target="#gameCarousel" data-bs-slide-to="2"></button>
          <button type="button" data-bs-target="#gameCarousel" data-bs-slide-to="3"></button>
          <button type="button" data-bs-target="#gameCarousel" data-bs-slide-to="4"></button>
        </div>

        <!-- 轮播内容 -->
        <div class="carousel-inner">
          <div class="carousel-item active">
            <img src="@/assets/images/游戏封面图/最终幻想7重制版-1.jpg" class="d-block w-100" alt="最终幻想7重生">
          </div>
          <div class="carousel-item">
            <img src="@/assets/images/游戏封面图/宇宙机器人-1.jpg" class="d-block w-100" alt="宇宙机器人">
          </div>
          <div class="carousel-item">
            <img src="@/assets/images/游戏封面图/最终幻想7重生-1.jpg" class="d-block w-100" alt="最终幻想7重生">
          </div>
          <div class="carousel-item">
            <img src="@/assets/images/游戏封面图/宇宙机器人-1.jpg" class="d-block w-100" alt="宇宙机器人">
          </div>
          <div class="carousel-item">
            <img src="@/assets/images/游戏封面图/最终幻想7重生-1.jpg" class="d-block w-100" alt="宇宙机器人">
          </div>
        </div>

        <!-- Bootstrap控制按钮 -->
        <button class="carousel-control-prev" type="button" data-bs-target="#gameCarousel" data-bs-slide="prev">
          <span class="carousel-control-prev-icon" aria-hidden="true"></span>
          <span class="visually-hidden">上一个</span>
        </button>
        <button class="carousel-control-next" type="button" data-bs-target="#gameCarousel" data-bs-slide="next">
          <span class="carousel-control-next-icon" aria-hidden="true"></span>
          <span class="visually-hidden">下一个</span>
        </button>


      </div>
      <!-- Bootstrap轮播图 结束 -->
    </div>
    <!--  展示区-->
    <div class="panel-body align-items-center">
        <ShowGoods title="热销商品"></ShowGoods>
        <ShowGoods title="新品上市" id="section1"></ShowGoods>
        <ShowGoods title="即将推出" id="section2"></ShowGoods>
    </div>
  </div>
<!--  底部背景广告位-->
  <div class="aboutBG">
    <div class="about">
      <div class="policy">
        <ul>
          <li>
            <i class="bi bi-signpost-2"></i>极速质检
          </li>
          <li>
            <i class="bi bi-7-square"></i>7天无理由退货
          </li>
          <li>
            <i class="bi bi-bank"></i>正品保障
          </li>
          <li>
            <i class="bi bi-bag-check"></i>顺丰快递
          </li>
        </ul>

      </div>
    </div>
  </div>

</template>

<style scoped>

/***************************小导航栏开始 logoNav start********************************/

.custom-navbar{
  margin-left: 12%;
  margin-right: 14%;
}
.navbar-toggler-icon{
  background-image: url("@/assets/images/手柄logo-1.jpg");
}
.custom-collapse ul li:hover {
  border-bottom: 2px solid #ff6702;
}

.search input[type="search"] {
  width: 240px;
  height: 40px;
  border: 1px solid #e0e0e0;
  line-height: 50px;
  outline: 0;
  padding: 0 10px;
  position: absolute;
  box-sizing: border-box;
  top: 0;
  right: 49px;
  transition: all .2s;
  margin: 10px;
  margin-right: 10%;
}

.search button{
  width: 50px;
  height: 40px;
  position: absolute;
  top: 0;
  right: 0;
  border: 1px solid #e0e0e0;
  font-size: 24px;
  color: #616161;
  background-color: #fff;
  line-height: 24px;
  outline: 0;
  cursor: pointer;
  transition: all .2s;
  margin: 10px;
  margin-right: 10%;
}
.search button[type="submit"]:hover {
  background-color: #ff6700;
  border-color: #ff6700;
  color: #fff;
}

/***************************小导航栏结束 logoNav end********************************/
/* 轮播图样式开始 */
.banner-container {
  width: 1226px;
  margin: 0 auto;
  position: relative;
  overflow: hidden;
}

.carousel-item {
  height: 400px;
}

.carousel-item img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.carousel-indicators {
  right: 30px;
  bottom: 15px;
  left: auto;
  margin: 0;
  justify-content: flex-end;
}

.carousel-indicators button {
  width: 10px;
  height: 10px;
  border-radius: 50%;
  border: 2px solid rgba(255, 255, 255, 0.3);
  background-color: rgba(0, 0, 0, 0.4);
  margin: 0 5px;
  transition: all 0.5s;
  opacity: 1;
}

.carousel-indicators button.active,
.carousel-indicators button:hover {
  background-color: rgba(255, 255, 255, 0.4);
  border-color: rgba(0, 0, 0, 0.4);
}

/* 左侧菜单样式 */
.side-menu {
  position: absolute;
  top: 0;
  left: 0;
  width: 234px;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.6);
  padding: 20px 0;
  z-index: 10;
}

.side-menu ul {
  list-style: none;
  padding: 0;
  margin: 0;
}

.side-menu li {
  position: relative;
  cursor: pointer;
  transition: all 0.3s;
}


.side-menu a {
  display: block;
  color: #fff;
  padding: 10px 0 10px 30px;
  text-decoration: none;
  font-size: 14px;
}

.side-menu span {
  position: absolute;
  right: 20px;
  top: 50%;
  transform: translateY(-50%);
  color: rgba(255, 255, 255, 0.5);
}


/* 轮播控制按钮 */
.carousel-control-prev,
.carousel-control-next {
  width: 40px;
  opacity: 0;
  transition: opacity 0.3s;
}

.carousel:hover .carousel-control-prev,
.carousel:hover .carousel-control-next {
  opacity: 1;
}

.carousel-control-prev {
  left: 234px;
}

.carousel-control-next {
  right: 0;
}

.carousel-control-prev-icon,
.carousel-control-next-icon {
  background-color: rgba(0, 0, 0, 0.3);
  border-radius: 50%;
  width: 50px;
  height: 50px;
  background-size: 50%;
  transition: all 0.3s;
}

.carousel-control-prev-icon:hover,
.carousel-control-next-icon:hover {
  background-color: rgba(0, 0, 0, 0.5);
}
/* 轮播图样式结束 */
/* 展示区 */
.panel-body{
  margin-top: 10px;
  width: 82%;
  height: auto;
  border: 1px solid #e0e0e0;
  background-color: #fff;
  margin-left: 9%;
}

/* 底部背景广告位  */
.aboutBG {
  width: 100%;
  height: 100px;
  background-color: #fff;
}

.about {
  width: 1226px;
  height: 272px;
  margin: 0 auto;
  padding-left: 100px;
}

.about .policy ul {
  width: 1226px;
  height: 80px;
  border-bottom: 1px solid #e0e0e0;
}

.about .policy ul li {
  list-style:none;
  width: 243px;
  height: 25px;
  line-height: 25px;
  margin-top: 27px;
  float: left;
  text-align: center;
  font-size: 16px;
  color: #616161;
  border-right: 1px solid #e0e0e0;
  cursor: pointer;
  transition: all .3s;
}

.about .policy ul li:last-child {
  border: none;
}

.about .policy ul li i {
  font-size: 30px;
  margin-right: 5px;
  vertical-align: -5px;
}

.about .policy ul li:hover {
  color: #ff6700;
}

.about .survey {
  width: 1226px;
  height: 192px;
  float: left;
  padding: 40px 0;
  box-sizing: border-box;
}

.about .survey dl {
  float: left;
  width: 160px;
  height: 112px;
}

.about .survey dl dt {
  width: 160px;
  height: 17px;
  margin-bottom: 26px;
}

.about .survey dl dd {
  width: 160px;
  height: 18px;
  margin-top: 10px;
  font-size: 12px;
}

.about .survey dl dd a {
  color: #757575;
}

.about .survey dl dd a:hover {
  color: #ff6700;
}

.about .survey .contact {
  float: right;
  width: 251px;
  height: 112px;
  border-left: 1px solid #e0e0e0;
  text-align: center;
  color: #616161;
}

.about .survey .contact h2 {
  margin-bottom: 5px;
  font-size: 22px;
  color: #ff6700;
  font-weight: normal;
}

.about .survey .contact p {
  margin-bottom: 16px;
  font-size: 12px;
}

.about .survey .contact span {
  width: 118px;
  height: 28px;
  display: inline-block;
  border: 1px solid #ff6700;
  background-color: #fff;
  color: #ff6700;
  font-size: 12px;
  line-height: 28px;
  cursor: pointer;
  transition: all .4s;
}

.about .survey .contact span:hover {
  background-color: #ff6700;
  color: #fff;
}
.add:hover{
  color: #0d6efd;
}

</style>