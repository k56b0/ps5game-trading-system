<script setup lang="ts">
import PageTurning from "@/components/PageTurning.vue";
import { useRoute, useRouter } from "vue-router";
import { computed, onMounted, ref } from "vue";
import { useGameStore } from '@/store/gameStore.ts';
import { storeToRefs } from "pinia";

const router = useRouter();
const route = useRoute();
const gameStore = useGameStore();

const { Games, current, pages, currentCategory, categories, searchKeyword } = storeToRefs(gameStore);
const searchText = ref("");

// 分页
const currentPage = computed(() => current.value);
const allPage = computed(() => pages.value);

const handlePageChange = async (newPage: number) => {
  if (searchKeyword.value) {
    // 搜索模式：带关键词和页码
    await gameStore.queryByName(searchKeyword.value, true, newPage);
  } else if (currentCategory.value) {
    // 分类模式
    await gameStore.queryCategory(currentCategory.value, newPage);
  } else {
    // 全部模式
    await gameStore.queryAll(newPage);
  }
};

// 计算折后价
const computedPrice = (price: number, discount: number) => {
  return (price * discount).toFixed(0);
};

// 分类筛选
const showCategory = async (choice: string) => {
  // 清空搜索词，避免冲突
  gameStore.searchKeyword = '';
  await gameStore.queryCategory(choice, 1);
  router.replace({ query: { categoryText: choice } });
};

// 搜索
const queryByName = async (name: string) => {
  const trimmed = name.trim();
  if (!trimmed) {
    // 搜索词为空 → 回到全部游戏
    gameStore.searchKeyword = '';
    await gameStore.queryAll(1);
    router.replace({ query: {} });
    return;
  }
  await gameStore.queryByName(trimmed, true, 1);
  router.replace({ query: { searchText: trimmed } });
};

// 详情
const showDetail = async (gameName: string) => {
  await gameStore.queryByName(gameName, false);
  await router.push("/GameDetails");
};

// 回到全部
const goHome = async () => {
  gameStore.searchKeyword = '';
  gameStore.currentCategory = null;
  await gameStore.queryAll(1);
  router.replace({ query: {} });
};

onMounted(async () => {
  const { searchText: qs, categoryText: qc } = route.query;

  if (typeof qs === "string" && qs.trim()) {
    await queryByName(qs);
  } else if (typeof qc === "string" && qc.trim()) {
    await showCategory(qc);
  } else {
    await gameStore.queryAll(1);
  }
});
</script>

<template>
  <div class="container">
    <!-- 搜索栏 -->
    <div class="search-bar">
      <input
          v-model="searchText"
          type="text"
          placeholder="搜索游戏名称"
          class="search-input"
      />
      <button class="search-btn" @click="queryByName(searchText)">搜索</button>
    </div>

    <!-- 排序 -->
    <div class="sort-bar">
      <li @click="showCategory('销量排序')">销量排序</li>
      <li @click="showCategory('价格排序')">价格排序</li>
    </div>

    <!-- 筛选区 -->
    <div class="filter-section">
      <div class="filter-card">
        <h5>发售类型</h5>
        <ul>
          <li @click="goHome">全部</li>
          <li @click="showCategory('新品')">新品</li>
          <li @click="showCategory('即将发售')">即将发售</li>
          <li @click="showCategory('热门')">热门</li>
        </ul>
      </div>

      <div class="filter-card">
        <h5>游戏类型</h5>
        <ul>
          <li @click="goHome">全部</li>
          <li v-for="c in categories" :key="c" @click="showCategory(c)">{{ c }}</li>
        </ul>
      </div>
    </div>

    <!-- 游戏列表 -->
    <div class="game-list">
      <ul>
        <li v-for="game in Games" :key="game.id">
          <img :src="game.img" alt="" @click="showDetail(game.gameName)" />
          <div class="game-info">
            <h4 class="game-title">{{ game.gameName }}</h4>
            <p class="game-price">¥{{ computedPrice(game.price, game.discount) }}</p>
          </div>
        </li>
      </ul>

      <PageTurning
          class="page-turning"
          :currentPage="currentPage"
          :allPage="allPage"
          @update:currentPage="handlePageChange"
      />
    </div>
  </div>
</template>

<style scoped>
.container {
  max-width: 1300px;
  margin: 0px auto;
  display: grid;
  grid-template-areas:
    "search search"
    "sort game"
    "filter game";
  grid-template-columns: 240px 1fr;
  grid-template-rows: auto auto 1fr;
  gap: 20px;
}

/* 搜索 */
.search-bar {
  grid-area: search;
  display: flex;
  gap: 12px;
  align-items: center;
  background: #f8f9fa;
  padding: 14px 20px;
  border-radius: 12px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06);
}

.search-input {
  flex: 1;
  padding: 10px 16px;
  border: 1px solid #ddd;
  border-radius: 8px;
  font-size: 14px;
  outline: none;
  transition: border 0.2s;
}

.search-input:focus {
  border-color: #409eff;
}

.search-btn {
  padding: 10px 20px;
  background: #409eff;
  color: white;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  transition: background 0.2s;
}

.search-btn:hover {
  background: #337ecc;
}

/* 排序 */
.sort-bar {
  grid-area: sort;
  display: flex;
  gap: 10px;
  background: #ffffff;
  padding: 14px;
  border-radius: 10px;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.05);
  list-style: none;
}

.sort-bar li {
  flex: 1;
  text-align: center;
  padding: 8px 10px;
  background: #f1f5f9;
  border-radius: 6px;
  cursor: pointer;
  transition: 0.2s;
  font-size: 14px;
}

.sort-bar li:hover {
  background: #409eff;
  color: white;
}

/* 筛选 */
.filter-section {
  grid-area: filter;
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.filter-card {
  background: white;
  padding: 16px;
  border-radius: 10px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06);
}

.filter-card h5 {
  margin: 0 0 10px 4px;
  font-size: 15px;
  color: #333;
}

.filter-card ul {
  list-style: none;
  padding: 0;
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 8px;
}

.filter-card li {
  padding: 6px 8px;
  background: #f8f9fa;
  border-radius: 6px;
  text-align: center;
  font-size: 13px;
  cursor: pointer;
  transition: 0.2s;
}

.filter-card li:hover {
  background: #409eff;
  color: white;
}

/* 游戏列表 */
.game-list {
  grid-area: game;
}

.game-list ul {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 20px;
  list-style: none;
  padding: 0;
}

.game-list li {
  background: white;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 3px 10px rgba(0, 0, 0, 0.08);
  transition: transform 0.2s, box-shadow 0.2s;
  cursor: pointer;
}

.game-list li:hover {
  transform: translateY(-6px);
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.12);
}

.game-list img {
  width: 100%;
  height: 220px;
  object-fit: cover;
}

.game-info {
  padding: 12px 14px;
  text-align: center;
}

.game-title {
  font-size: 15px;
  margin: 0 0 6px 0;
  color: #222;
}

.game-price {
  font-size: 16px;
  color: #ff4d4f;
  font-weight: bold;
  margin: 0;
}

.page-turning {
  margin-top: 10px;
  display: flex;
  justify-content: center;
}
</style>