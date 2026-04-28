<template>
  <div class="feedback-wrapper container-xl py-4">
    <h4 class="page-title">问题反馈</h4>

    <div class="grid-layout">
      <!-- 左侧导航 -->
      <div class="sidebar">
        <ul class="nav-list">
          <li>
            <button
                @click="showPanel(1)"
                class="nav-btn"
                :class="{ active: activePanel === 1 }"
            >
              网站介绍
            </button>
          </li>
          <li>
            <button
                @click="showPanel(2)"
                class="nav-btn"
                :class="{ active: activePanel === 2 }"
            >
              购买介绍
            </button>
          </li>
          <li>
            <button
                @click="showPanel(3)"
                class="nav-btn"
                :class="{ active: activePanel === 3 }"
            >
              回收介绍
            </button>
          </li>
          <li>
            <button
                @click="showPanel(4)"
                class="nav-btn"
                :class="{ active: activePanel === 4 }"
            >
              反馈问题
            </button>
          </li>
        </ul>
      </div>

      <!-- 右侧内容 -->
      <div class="content-card">
        <div v-show="activePanel === 1" class="panel-content">
          <p v-for="item in feedBack1" :key="item.id">{{ item.content }}</p>
        </div>
        <div v-show="activePanel === 2" class="panel-content">
          <p v-for="item in feedBack2" :key="item.id">{{ item.content }}</p>
        </div>
        <div v-show="activePanel === 3" class="panel-content">
          <p v-for="item in feedBack3" :key="item.id">{{ item.content }}</p>
        </div>
        <div v-show="activePanel === 4" class="panel-content">
          <p v-for="item in feedBack4" :key="item.id">{{ item.content }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'

// 代码大幅精简：用一个变量控制切换
const activePanel = ref(1)

function showPanel(num: number) {
  activePanel.value = num
}

// 内容完全保留不变
const feedBack1 = [
  { id: 'id1', content: '🌟宝藏PS5游戏天地，快乐起飞！' },
  { id: 'id2', content: '🌟海量热门新作、经典大作任你淘' },
  { id: 'id3', content: '秒速到货，玩转次世代！💃' },
  { id: 'id4', content: '二手回收，冒险即刻开启~🎮' },
]
const feedBack2 = [
  { id: 'id1', content: '挑选游戏，加入购物车' },
  { id: 'id2', content: '确认信息，轻松结算' },
  { id: 'id3', content: '整理收藏夹，快速生成订单' },
  { id: 'id4', content: '坐等收货，畅玩即刻开启！' },
]
const feedBack3 = [
  { id: 'id1', content: '填写回收游戏信息' },
  { id: 'id2', content: '等待线上评估结果' },
  { id: 'id3', content: '寄出游戏等待验货' },
  { id: 'id4', content: '坐等验收成功' },
]
const feedBack4 = [
  { id: 'id1', content: '问题反馈邮箱：1234567@qq.com' },
  { id: 'id2', content: '客服电话：12345678' },
]
</script>

<style scoped>
/* 整体容器 */
.feedback-wrapper {
  max-width: 1200px;
  margin: 0 auto;
}

/* 标题 */
.page-title {
  background: linear-gradient(90deg, #ff9a56, #ff6b6b);
  color: white;
  text-align: center;
  padding: 14px 20px;
  border-radius: 12px;
  font-size: 20px;
  font-weight: bold;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

/* 网格布局 */
.grid-layout {
  display: grid;
  grid-template-columns: 1fr 4fr;
  gap: 24px;
  margin-top: 20px;
}

/* 左侧导航 */
.sidebar {
  height: 100%;
}

.nav-list {
  list-style: none;
  padding: 0;
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.nav-btn {
  width: 100%;
  padding: 14px 12px;
  border: 1px solid #e0e0e0;
  border-radius: 10px;
  background: white;
  font-size: 15px;
  cursor: pointer;
  transition: all 0.25s ease;
  box-shadow: 0 1px 3px rgba(0,0,0,0.05);
}

/* 选中高亮 */
.nav-btn.active {
  background: #409eff;
  color: white;
  border-color: #409eff;
  font-weight: bold;
  transform: translateX(4px);
}

.nav-btn:hover:not(.active) {
  border-color: #409eff;
  color: #409eff;
}

/* 右侧内容卡片 */
.content-card {
  background: #f8f9fa;
  border-radius: 14px;
  padding: 30px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
  min-height: 300px;
  display: flex;
  align-items: center;
  justify-content: center;
}

/* 内容展示 */
.panel-content {
  width: 100%;
  animation: fadeIn 0.3s ease;
}

.panel-content p {
  font-size: 16px;
  line-height: 2.2;
  color: #333;
  margin: 10px 0;
  text-align: center;
}

/* 淡入动画 */
@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(10px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}
</style>