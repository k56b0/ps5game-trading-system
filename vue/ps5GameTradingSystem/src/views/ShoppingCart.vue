<script setup lang="ts">

import {computed, reactive, ref} from 'vue';
import PageTurning from "@/components/PageTurning.vue";

// 响应式数据
const hoverOrderId = ref(null);
const selectedOrder = ref(null);
const showModal = ref(false);

// 订单数据
const gameCarList = reactive([
  {
    id: 1,
    gameName: '最终幻想1',
    quantity:1,
    totalPrice: 5600,
    checked: false
  },
  {
    id: 2,
    gameName: '最终幻想2',
    quantity:1,
    totalPrice: 12800,
    checked: false
  },
  {
    id: 3,
    gameName: '最终幻想3',
    quantity:1,
    totalPrice: 3250,
    checked: false
  },
  {
    id: 4,
    gameName: '最终幻想4',
    quantity:1,
    totalPrice: 8900,
    checked: false
  }
]);
//订单详情
// 订单数据
const orderDetails = reactive([
  {
    id:1,
    gameName: '小小梦魇2',
    price: '34',
    quantity: '2',
  },
  {
    id:2,
    gameName: '绝地求生',
    price: '66',
    quantity: '10',
  },

]);


// 格式化日期
const formatDate = (dateString:any) => {
  return dateString; // 这里可以添加更复杂的日期格式化逻辑
};

// 查看订单
const viewOrder = (order:any) => {
  selectedOrder.value = order;
  showModal.value = true;
};

// 关闭模态框
const closeModal = () => {
  showModal.value = false;
  selectedOrder.value = null;
};

// ---------- 2. 计算属性：已选中数量 ----------
const selectedCount = computed(() => {
  return gameCarList.filter(game => game.checked).length
})

// ---------- 3. 全选状态：使用计算属性的 get/set ----------
const selectAll = computed({
  // getter：是否所有行的 checked 都为 true
  get() {
    return gameCarList.length > 0 && gameCarList.every(game => game.checked)
  },
  // setter：全选/全不选（当用户点击全选复选框时触发）
  set(value) {
    gameCarList.forEach(game => (game.checked = value))
  }
})
// 全选复选框的 change 事件（实际上可以直接使用 v-model，但为了清晰调用 setter）
function toggleSelectAll(event:any) {
  selectAll.value = event.target.checked
}
</script>

<template>
  <div class="container mt-4">
    <!-- ========= 多选功能配套：工具栏 / 批量操作演示 ========= -->
    <div class="d-flex justify-content-between align-items-center mb-3">
      <div class="d-flex gap-2">
        <span class="fw-normal text-secondary small">已选中 <span id="selectedCount" class="badge bg-primary rounded-pill">0</span> 款游戏</span>
      </div>
      <div>
        <!-- 批量删除演示按钮 —— 体现多选的实用价值 -->
        <button class="btn btn-sm btn-outline-danger" id="batchDeleteBtn">
          <i class="bi bi-trash3"></i> 批量删除
        </button>
        <button class="btn btn-sm btn btn-outline-success " id="batchDeleteBtn">
          <i class="bi bi-bag-heart-fill"></i> 提交订单
        </button>
      </div>
    </div>
    <table class="table table-hover table-hover-custom">
      <thead>
      <tr>
        <!-- 多选全选列：宽度极小，居中对齐 -->
        <th
            class="form-check-input"
            type="checkbox"
            :checked="selectAll"
            @change="toggleSelectAll"
            aria-label="全选当前页游戏"
        >
          <input class="form-check-input" type="checkbox" id="selectAll" aria-label="全选当前页游戏">
        </th>
        <th>序号</th>
        <th>游戏名</th>
        <th>数量</th>
        <th>价格</th>
        <th>操作</th>
      </tr>
      </thead>
      <tbody>
      <tr
          v-for="(g,index) in gameCarList"
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
        <td>{{ g.quantity }}</td>
        <td>¥{{ g.totalPrice.toLocaleString() }}</td>
        <td>
          <div
              class="table-actions"
              :style="{ opacity: hoverOrderId === g.id ? '1' : '0' }"
          >
            <button
                class="btn btn-sm btn-outline-primary me-1"
                @click="viewOrder(g)"
                title="查看游戏详情">
              <i class="bi bi-eye"></i>
            </button>
          </div>
        </td>
      </tr>
      </tbody>
    </table>

    <!-- 订单详情模态框 -->
    <div v-if="selectedOrder" class="modal fade show" style="display: block; background-color: rgba(0,0,0,0.5);" v-show="showModal">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">订单详情</h5>
            <button type="button" class="btn-close" @click="closeModal"></button>
          </div>
          <div class="modal-body">
            <table class="table table-custom-primary">
              <thead>
              <tr>
                <th>游戏名</th>
                <th>单价</th>
                <th>数量</th>
              </tr>

              </thead>
              <tbody>
              <tr
                  v-for="d in orderDetails"
                  :key="d.id">
                <td>{{d.gameName}}</td>
                <td>{{d.price}}</td>
                <td>{{d.quantity}}</td>
              </tr>
              </tbody>
            </table>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" @click="closeModal">关闭</button>
          </div>
        </div>
      </div>
    </div>
    <PageTurning/>
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