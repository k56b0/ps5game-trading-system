<script setup lang="ts">
import {useShoppingCarStore} from '@/store/shoppingCarStore.ts'
import {useOrderStore} from '@/store/orderStore.ts'
import {useUserStore} from '@/store/userStore.ts'
import {useGameStore} from '@/store/gameStore.ts'
import {computed, reactive, ref} from 'vue';
import PageTurning from "@/components/PageTurning.vue";
import {storeToRefs} from "pinia";
import { useRouter } from 'vue-router'

const router = useRouter()
// user pinia
const useUser =useUserStore()
const useShoppingCar = useShoppingCarStore()
const useOrder=useOrderStore()
const useGame =useGameStore()
const {myShoppingCarMap,current, pages}=storeToRefs(useShoppingCar)
const {myInfo}=storeToRefs(useUser)

// 响应式数据
const hoverOrderId = ref(null);
// 格式化日期
const formatDate = (dateString:any) => {
  return dateString; // 这里可以添加更复杂的日期格式化逻辑
};

/*选择服务 开始*/
// 1.计算属性：已选中数量
const selectedCount = computed(() => {
  return myShoppingCarMap.value.filter(game => game.checked).length
})

// 2.全选状态：使用计算属性的 get/set ----------
const selectAll = computed({
  // getter：是否所有行的 checked 都为 true
  get() {
    return myShoppingCarMap.value.length > 0 && myShoppingCarMap.value.every(game => game.checked)
  },
  // setter：全选/全不选（当用户点击全选复选框时触发）
  set(value) {
    myShoppingCarMap.value.forEach(game => (game.checked = value))
  }
})

// 全选复选框的 change 事件（实际上可以直接使用 v-model，但为了清晰调用 setter）
function toggleSelectAll(event:any) {
  selectAll.value = event.target.checked
}
//删除一个
async function deleteOne(id:string) {
  await useShoppingCar.delete(id)
  //查询收藏夹信息
  await useShoppingCar.queryAll(
      {
        userId:myInfo.value.id,
        pageNum:1
      }
  )
}
// 批量删除
async function handleBatchDelete() {
  const selectedNames = myShoppingCarMap.value
      .filter(game => game.checked)
      .map(game => game.id)

  if (selectedNames.length === 0) {
    alert('未选中任何游戏，无法执行批量删除。')
    return
  }
  for (let i = 0; i < selectedNames.length; i++) {
    await useShoppingCar.delete(selectedNames[i] as string)
  }
  //查询购物车信息
  await useShoppingCar.queryAll(
      {
        userId:myInfo.value.id,
        pageNum:1
      }
  )
}
//提交订单
async function handleCommitOrder(){
  const selected = myShoppingCarMap.value
      .filter(game => game.checked)
  if (selected.length === 0) {
    alert('未选中任何游戏，无法执行订单提交。')
    return
  }
  await useShoppingCar.addToOrder(selected)
  //查询购物车信息
  await useShoppingCar.queryAll(
      {
        userId:myInfo.value.id,
        pageNum:1
      }
  )
  //查询订单信息
  await useOrder.queryAll(
      {
        userId:myInfo.value.id,
        pageNum:1
      }
  )
}
/*处理页码变化 开始*/
const currentPage = computed(() => current.value)
const allPage = computed(() => pages.value)
const handlePageChange = (newPage:number) => {
  // 更新 store 中的当前页码
  current.value = newPage   // 假设 store 中有 current 字段
  // 重新请求对应页码的数据
  useShoppingCar.queryAll({
    userId:useUser.myInfo.id,
    pageNum:newPage})
}
/*处理页码变化 结束*/
//查询游戏详情
async function showDetail(gameName:string) {
  try {
    // 1. 先查询游戏详情（等待完成）
    await useGame.queryByName(gameName,false)
    // 2. 查询成功后跳转到详情页
    // 方式一：通过 query 传递参数（适用于任何路由配置）
    // router.push({ path: '/GameDetails', query: { name: gameName } })
    // 方式二：如果路由配置了动态参数（如 /GameDetails/:gameName），可以使用 params
    // router.push({ name: 'GameDetails', params: { gameName } })
    // 方式三：如果不需要参数，直接跳转
    router.push('/GameDetails')
  } catch (error) {
    console.error('查询游戏详情失败', error)
  }
}
</script>

<template>
  <div class="container mt-4">
    <!-- ========= 多选功能配套：工具栏 / 批量操作演示 ========= -->
    <div class="d-flex justify-content-between align-items-center mb-3">
      <div class="d-flex gap-2">
        <span class="fw-normal text-secondary small">已选中 <span id="selectedCount" class="badge bg-primary rounded-pill"
            >{{ selectedCount }}</span> 款游戏</span>
      </div>
      <div>
        <!-- 批量删除演示按钮 —— 体现多选的实用价值 -->
        <button class="btn btn-sm btn-outline-danger" id="batchDeleteBtn" @click="handleBatchDelete">
          <i class="bi bi-trash3"></i> 批量删除
        </button>
        <button class="btn btn-sm btn btn-outline-success " id="batchDeleteBtn" @click="handleCommitOrder">
          <i class="bi bi-bag-heart-fill"></i> 提交订单
        </button>
      </div>
    </div>
    <table class="table table-hover table-hover-custom">
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
        <th>序号</th>
        <th>游戏名</th>
        <th>单价</th>
        <th>数量</th>
        <th>操作</th>
      </tr>
      </thead>
      <tbody>
      <tr
          v-for="(g,index) in myShoppingCarMap"
          :key="g.id"
          @mouseenter="(hoverOrderId as any)= g.id"
          @mouseleave="hoverOrderId = null"
          :class="{ 'hover-active': hoverOrderId === g.id }"
      >
        <td class="align-middle text-center">
          <!-- 使用 v-model 双向绑定每一行的选中状态 -->
          <input
              class="form-check-input row-checkbox"
              type="checkbox"
              v-model="g.checked"
              :aria-label="'选择' + g.gameName"

          />
        </td>
        <td>{{ index+1 }}</td>
        <td>{{ g.gameName }}</td>
        <td>{{ g.price }}</td>
        <td>{{ g.quantity }}</td>
        <td>
          <div
              class="table-actions"
              :style="{ opacity: hoverOrderId === g.id ? '1' : '0' }"
          >
            <button
                class="btn btn-sm btn-outline-primary me-1"
                @click="showDetail(g.gameName)"
                title="查看">
              <i class="bi bi-eye"></i>
            </button>
            <button class="btn btn-sm btn-outline-danger" title="删除" @click="deleteOne(g.id)">
              <i class="bi bi-trash"></i></button>
          </div>
        </td>
      </tr>
      </tbody>
    </table>

    <PageTurning
        :currentPage="currentPage"
        :allPage="allPage"
        @update:currentPage="handlePageChange"
    />
  </div>

</template>

<style scoped>
.table-hover-custom tbody tr {
  transition: background-color 0.3s ease;
}

.table-hover-custom tbody tr.hover-active {
  background-color: rgba(0, 123, 255, 0.05);
}

.table-actions {
  opacity: 0;
  transition: opacity 0.3s ease;
  display: flex;
  gap: 4px;
}

/* 移动端适配 */
@media (max-width: 768px) {
  .table-actions {
    flex-direction: column;
    gap: 2px;
  }

  .table-actions button {
    width: 100%;
    margin: 2px 0;
  }
}

/* 模态框动画 */
.modal.fade.show {
  transition: opacity 0.3s ease;
}

/* 表格响应式处理 */
.table {
  width: 100%;
  overflow-x: auto;
}

/* 按钮悬停效果 */
.btn-outline-primary:hover {
  background-color: #0d6efd;
  color: white;
}


</style>