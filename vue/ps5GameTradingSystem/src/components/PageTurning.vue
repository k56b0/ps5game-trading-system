<script setup lang="ts">
import { computed } from 'vue'

const props = defineProps<{
  currentPage: number
  allPage: number    // 总页数
}>()

const emit = defineEmits<{
  (e: 'update:currentPage', page: number): void
}>()

// 跳转到指定页
const goToPage = (page: number) => {
  if (page < 1 || page > props.allPage) return
  emit('update:currentPage', page)
}

// 生成中间显示的页码（不含首页和末页）
const middlePages = computed<(number | string)[]>(() => {
  const total = props.allPage
  const current = props.currentPage
  const delta = 2 // 当前页前后各显示几页

  // 如果总页数很少，直接返回所有页码（但首页末页已在外部，这里应返回中间部分）
  if (total <= 1) return []

  // 收集需要显示的数字页码（不包括首页和末页）
  const range: number[] = []
  for (let i = 2; i < total; i++) {
    if (i >= current - delta && i <= current + delta) {
      range.push(i)
    }
  }

  // 插入省略号
  const result: (number | string)[] = []
  let prev = 1 // 首页是1
  for (const page of range) {
    if (page - prev === 2) {
      // 中间只差一页，直接补上
      result.push(prev + 1)
    } else if (page - prev > 2) {
      result.push('...')
    }
    result.push(page)
    prev = page
  }

  // 处理末页之前的省略号
  if (prev < total - 1) {
    if (total - prev === 2) {
      result.push(prev + 1)
    } else if (total - prev > 2) {
      result.push('...')
    }
  }

  return result
})
</script>

<template>
  <div class="container">
    <ul class="pagination">
      <!-- 首页 -->
      <li class="page-item" :class="{ disabled: currentPage === 1 }">
        <a class="page-link" href="#" @click.prevent="goToPage(1)">首页</a>
      </li>

      <!-- 上一页 -->
      <li class="page-item" :class="{ disabled: currentPage === 1 }">
        <a class="page-link" href="#" @click.prevent="goToPage(currentPage - 1)">上一页</a>
      </li>

      <!-- 中间页码（动态生成） -->
      <template v-for="item in middlePages" :key="item">
        <li v-if="item === '...'" class="page-item disabled">
          <span class="page-link">...</span>
        </li>
        <li v-else class="page-item" :class="{ active: item === currentPage }">
          <a class="page-link" href="#" @click.prevent="goToPage(item as number)">{{ item }}</a>
        </li>
      </template>

      <!-- 下一页 -->
      <li class="page-item" :class="{ disabled: currentPage === allPage }">
        <a class="page-link" href="#" @click.prevent="goToPage(currentPage + 1)">下一页</a>
      </li>

      <!-- 末页 -->
      <li class="page-item" :class="{ disabled: currentPage === allPage }">
        <a class="page-link" href="#" @click.prevent="goToPage(allPage)">末页</a>
      </li>
    </ul>
  </div>
</template>

<style scoped>
.pagination {
  display: flex;
  justify-content: center;
  margin: 15px 0;
}
.pagination .page-link {
  width: 60px;          /* 你想要的具体宽度 */
  text-align: center;
  padding: 0.375rem 0;  /* 适当调整上下内边距，左右内边距被宽度限制 */
}
</style>