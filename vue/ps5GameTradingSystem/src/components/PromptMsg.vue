<script setup lang="ts">
import { ref, watch } from 'vue'
const props = defineProps({
  message: {
    type: String,
    default: ''
  }
})

const emit = defineEmits(['clearMessage'])

const visible = ref(false)
let timer:any = null

// 监听 message 变化，当有新的非空消息时显示弹窗
watch(() => props.message, (newVal) => {
  if (newVal && newVal.trim() !== '') {
    visible.value = true

    // 清除之前的定时器
    if (timer) clearTimeout(timer)

    // 3 秒后隐藏并通知父组件
    timer = setTimeout(() => {
      visible.value = false
      emit('clearMessage')
    }, 3000)
  }
}, { immediate: true })

defineOptions({ name: 'PromptMsg' })  // 新增
</script>

<template>
  <Teleport to="body">
    <div
        class="toast custom-toast"
        :class="{ show: visible }"
        role="alert"
        aria-live="assertive"
        aria-atomic="true"
        style="z-index: 9999;"
    >
      <div class="toast-body">
        {{ message }}
      </div>
    </div>
  </Teleport>

</template>
<style scoped>
.custom-toast {
  position: fixed;
  bottom: 800px;    /* 距离底部 20px */
  left: 1430px;      /* 距离左边 20px */
  max-width: 200px;
  width: auto;
  padding: 0.5rem;  /* 减小内边距，相当于 Bootstrap 的 p-2 */
  z-index: 9999;
}
</style>

