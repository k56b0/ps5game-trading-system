<script setup lang="ts">
import {computed, ref} from 'vue';
import PageTurning from "@/components/PageTurning.vue";
import { useOrderStore} from '@/store/orderStore.ts'
import {storeToRefs} from "pinia";
import type {order} from '@/types/order.ts'
import {useUserStore} from '@/store/userStore.ts'

// 使用 pinia
const useOrder =useOrderStore()
const useUser = useUserStore()
// 在模板中可以直接使用 myOrders（无需 .value，因为模板中会自动解包）
const { myOrders,current, pages } = storeToRefs(useOrder); // myOrders 是 ref

// 响应式数据
const hoverOrderId = ref(null);
const selectedOrder = ref<order | null>(null) //加入类型判断
const showModal = ref(false);
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
// 处理页码变化
const handlePageChange = (newPage:number) => {
  // 更新 store 中的当前页码
  current.value = newPage   // 假设 store 中有 current 字段
  // 重新请求对应页码的数据
  useOrder.queryAll({
    userId:useUser.myInfo.id,
    pageNum:newPage})
}

const currentPage = computed(() => current.value)
const allPage = computed(() => pages.value)
</script>

<template>
  <!-- 临时调试 -->
<!--  <div style="background: #eee; padding: 10px">-->
<!--    当前页 ref: {{ current }} (类型: {{ typeof current }}),-->
<!--    当前页值: {{ current}} (类型: {{ typeof current }}),-->
<!--    总页数: {{ pages }},-->
<!--    总记录: {{ total }}-->
<!--  </div>-->
  <div class="container mt-4">
    <table class="table table-hover table-hover-custom">
      <thead>
      <tr>
        <th>订单号</th>
        <th>订单时间</th>
        <th>订单地址</th>
        <th>总价</th>
        <th>操作</th>
      </tr>
      </thead>
      <tbody>
      <tr
          v-for="order in myOrders"
          :key="order.id"
          @mouseenter="(hoverOrderId as any)= order.id"
          @mouseleave="hoverOrderId = null"
          :class="{ 'hover-active': hoverOrderId === order.id }"
      >
        <td>{{ order.id }}</td>
        <td>{{ formatDate(order.orderTime) }}</td>
        <td>{{ order.orderAddress }}</td>
        <td>¥{{ order.sum.toLocaleString() }}</td>
        <td>
          <div
              class="table-actions"
              :style="{ opacity: hoverOrderId === order.id ? '1' : '0' }"
          >
            <button
                class="btn btn-sm btn-outline-primary me-1"
                @click="viewOrder(order)"
                title="查看订单">
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
                  v-for="d in selectedOrder?.orderDetails"
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
<!--    <button @click="handlePageChange(current - 1)">上一页</button>-->
<!--    <button @click="handlePageChange(current + 1)">下一页</button>-->
    <PageTurning
        :currentPage="currentPage"
        :allPage="allPage"
        @update:currentPage="handlePageChange"/>
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