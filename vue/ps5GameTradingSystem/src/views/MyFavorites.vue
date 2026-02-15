<script setup lang="ts">
import {  computed } from 'vue'
import PageTurning from "@/components/PageTurning.vue";
import {useUserStore} from '@/store/userStore.ts'
import {useFavoriteStore} from '@/store/favoriteStore.ts'
import {useGameStore} from '@/store/gameStore.ts'
import { useRouter } from 'vue-router'
import {storeToRefs} from "pinia";

const router = useRouter()
const useUser = useUserStore()
const useFavorite = useFavoriteStore()
const useGame =useGameStore()
const {myFavoritesMap,current, pages}=storeToRefs(useFavorite)
const {myInfo}=storeToRefs(useUser)

/*选择服务 开始*/
// 1.计算属性：已选中数量
const selectedCount = computed(() => {
  return myFavoritesMap.value.filter(game => game.checked).length
})

// 2.全选状态：使用计算属性的 get/set ----------
const selectAll = computed({
  // getter：是否所有行的 checked 都为 true
  get() {
    return myFavoritesMap.value.length > 0 && myFavoritesMap.value.every(game => game.checked)
  },
  // setter：全选/全不选（当用户点击全选复选框时触发）
  set(value) {
    myFavoritesMap.value.forEach(game => (game.checked = value))
  }
})

// 全选复选框的 change 事件（实际上可以直接使用 v-model，但为了清晰调用 setter）
function toggleSelectAll(event:any) {
  selectAll.value = event.target.checked
}
//删除一个
async function deleteOne(id:string) {
  await useFavorite.delete(id)
  //查询收藏夹信息
  await useFavorite.queryAll(
      {
        userId:myInfo.value.id,
        pageNum:1
      }
  )
}

// 批量删除
async function handleBatchDelete() {
  const selectedNames = myFavoritesMap.value
      .filter(game => game.checked)
      .map(game => game.id)

  if (selectedNames.length === 0) {
    alert('未选中任何游戏，无法执行批量删除。')
    return
  }
  for (let i = 0; i < selectedNames.length; i++) {
    await useFavorite.delete(selectedNames[i] as string)
  }
  //查询收藏夹信息
  await useFavorite.queryAll(
      {
        userId:myInfo.value.id,
        pageNum:1
      }
  )
}
/*选择服务 结束*/
/*处理页码变化 开始*/
const currentPage = computed(() => current.value)
const allPage = computed(() => pages.value)
const handlePageChange = (newPage:number) => {
  // 更新 store 中的当前页码
  current.value = newPage   // 假设 store 中有 current 字段
  // 重新请求对应页码的数据
  useFavorite.queryAll({
    userId:useUser.myInfo.id,
    pageNum:newPage})
}
/*处理页码变化 结束*/

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
  <div class="container ">
    <div class="container-fluid">
      <!-- ========= 多选功能配套：工具栏 / 批量操作演示 ========= -->
      <div class="d-flex justify-content-between align-items-center mb-3">
        <div class="d-flex gap-2">
          <span class="fw-normal text-secondary small">已选中 <span id="selectedCount" class="badge bg-primary rounded-pill">{{selectedCount}}</span> 款游戏</span>
        </div>
        <div>
          <button class="btn btn-sm btn-outline-danger" id="batchDeleteBtn" @click="handleBatchDelete">
            <i class="bi bi-trash3"></i> 批量删除
          </button>
        </div>
      </div>
    <div class="table-responsive mt-4">
      <table  class="table table-sm table-hover-custom" id="gameTable">
        <thead>
        <tr>
          <!-- 多选全选列：宽度极小，居中对齐 -->
          <th scope="col" class="align-middle text-center">
            <input class="form-check-input"
                   type="checkbox" id="selectAll"
                   :checked="selectAll"
                   @change="toggleSelectAll"
                   aria-label="全选当前页游戏">
          </th>
          <th scope="col" class="align-middle text-center">序号</th>
          <th scope="col" class="align-middle text-center">游戏名</th>
          <th scope="col" class="align-middle text-center">操作</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="(game, index) in myFavoritesMap" :key="game.id">
          <td class="align-middle text-center">
            <!-- 使用 v-model 双向绑定每一行的选中状态 -->
            <input
                class="form-check-input row-checkbox"
                type="checkbox"
                v-model="game.checked"
                :aria-label="'选择' + game.gameName"
            />
          </td>
          <td class="align-middle text-center">{{ index + 1 }}</td>

          <td class="align-middle text-center">{{game.gameName}}</td>
          <td  class="align-middle text-center">
            <div class="table-actions">
              <button class="btn btn-sm btn-outline-primary me-1" title="添加到购物车" >
                <i class="bi bi-gift-fill"></i></button>
              <button class="btn btn-sm btn-outline-primary me-1" title="查看" @click="showDetail(game.gameName)">
                <i class="bi bi-eye"></i></button>
              <button class="btn btn-sm btn-outline-danger" title="删除" @click="deleteOne(game.id)">
                <i class="bi bi-trash"></i></button>
            </div>
          </td>
        </tr>
        </tbody>

      </table>
      <p class="text-muted small mt-3"><i class="bi bi-info-circle"></i> 全选复选框支持“半选”状态（仅部分勾选时显示）。点击“批量删除”会模拟删除选中游戏(仅弹窗演示)。</p>
    </div>
  </div>
    <PageTurning
        :currentPage="currentPage"
        :allPage="allPage"
        @update:currentPage="handlePageChange"
    />
  </div>
</template>

<style scoped>
/* 让表格内所有单元格默认垂直居中对齐，保证多选框、序号、按钮视觉一致 */
.table td, .table th {
  vertical-align: middle;
}
/* 自定义 hover 效果保留 (您原有的 table-hover-custom 可自行扩展，这里保留类名但不影响功能) */
.table-hover-custom tbody tr:hover {
  background-color: rgba(0, 123, 255, 0.06);
  transition: background-color 0.2s;
}
/* 给操作列的按钮组一点右间距，更透气 */
.table-actions {
  white-space: nowrap;
}

</style>