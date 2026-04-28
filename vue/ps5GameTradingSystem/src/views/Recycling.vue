<script setup lang="ts">
import { useRecycleStore } from '@/store/recycleStore.ts'
import { useUserStore } from '@/store/userStore.ts'
import type { recycle } from '@/types/recycle.ts'
import { storeToRefs } from 'pinia'
import { computed, onMounted, ref } from 'vue'
import { v4 as uuidv4 } from 'uuid'
import PromptMsg from '@/components/PromptMsg.vue'

// 提示
const toastMessage = ref('')
const loading = ref(false)

// Pinia
const recycleStore = useRecycleStore()
const userStore = useUserStore()
const { myInfo } = storeToRefs(userStore)
const { newRecycle } = storeToRefs(recycleStore)

// 表单
const gameName = ref('')
const version = ref('')
const language = ref('')
const appearance = ref('')
const box = ref('')

// 价格格式化
const formattedValuation = computed(() => {
  const val = newRecycle.value.valuation
  return val != null ? val.toFixed(2) : '0.00'
})

// UUID
const uuid = ref('')
const generateUUID = () => {
  uuid.value = uuidv4()
}

// 重置
const resetForm = () => {
  gameName.value = ''
  version.value = ''
  language.value = ''
  appearance.value = ''
  box.value = ''
  recycleStore.$patch({ newRecycle: {} as recycle })
}

// ==============================================
// 【第一步】仅评估价格（不清空，不生成订单）
// ==============================================
async function handleEvaluate() {
  if (!gameName.value.trim()) {
    toastMessage.value = '请输入游戏名'
    return
  }
  if (!version.value || !language.value || !appearance.value || !box.value) {
    toastMessage.value = '请完成所有选项'
    return
  }

  loading.value = true
  try {
    const data: recycle = {
      id: uuid.value,
      userId: myInfo.value.id,
      gameName: gameName.value.trim(),
      valuation: 0,
      recyclingStatus: '待回收',
      version: version.value === 'guohang' ? '国行' : '非国行',
      language: language.value === 'ChineseSupport' ? '支持中文' : '不支持中文',
      appearance: appearance.value === 'appearanceA'
          ? '光盘无划痕'
          : appearance.value === 'appearanceB'
              ? '光盘轻微划痕'
              : '光盘严重划痕',
      box: box.value === 'boxGood'
          ? '包装盒彩页齐全完好'
          : box.value === 'boxDamaged'
              ? '包装盒彩页齐全损坏'
              : '无包装盒',
    }

    // 交给后端评估价格 → 会把价格写入 newRecycle
    await recycleStore.add(data)
    toastMessage.value = '价格评估完成！'
  } catch (err) {
    toastMessage.value = '评估失败'
    console.error(err)
  } finally {
    loading.value = false
  }
}

// ==============================================
// 【第二步】确认提交 → 真正生成回收订单
// ==============================================
async function handleConfirmRecycle() {
  if (!newRecycle.value.valuation) {
    toastMessage.value = '请先点击评估价格'
    return
  }

  loading.value = true
  try {
    // 直接提交已评估好的回收单
    await recycleStore.add(newRecycle.value)

    // 刷新列表
    await recycleStore.queryAll({
      userId: myInfo.value.id,
      pageNum: 1,
    })

    toastMessage.value = '回收订单创建成功！'
    resetForm()
    generateUUID()
  } catch (err) {
    toastMessage.value = '提交失败'
    console.error(err)
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  generateUUID()
})
</script>

<template>
  <div class="recycle-page container py-5">
    <div class="row justify-content-center text-center mb-4">
      <h2 class="text-primary fw-bold">回收评估</h2>
      <h5 class="text-muted mt-2">
        回收邮寄地址：北京市朝阳区
      </h5>
    </div>
    <div class="row justify-content-center g-4">
      <!-- 左侧表单 -->
      <div class="col-md-5">
        <div class="card p-4 shadow-sm h-100">
          <form @submit.prevent="handleEvaluate">
            <div class="mb-4">
              <label class="form-label fw-bold">0. 游戏名称</label>
              <input
                  v-model="gameName"
                  type="text"
                  class="form-control border-bottom-only"
                  placeholder="请输入游戏名"
              />
            </div>

            <!-- 版本 -->
            <div class="mb-4">
              <label class="form-label fw-bold">1. 主机版本</label>
              <div class="d-flex gap-3 mt-2">
                <label class="radio-label">
                  <input v-model="version" type="radio" value="guohangF">
                  <span>非国行</span>
                </label>
                <label class="radio-label">
                  <input v-model="version" type="radio" value="guohang">
                  <span>国行</span>
                </label>
              </div>
            </div>

            <!-- 语言 -->
            <div class="mb-4">
              <label class="form-label fw-bold">2. 语言支持</label>
              <div class="d-flex gap-3 mt-2">
                <label class="radio-label">
                  <input v-model="language" type="radio" value="ChineseSupport">
                  <span>支持中文</span>
                </label>
                <label class="radio-label">
                  <input v-model="language" type="radio" value="ChineseSupportF">
                  <span>不支持中文</span>
                </label>
              </div>
            </div>

            <!-- 外观 -->
            <div class="mb-4">
              <label class="form-label fw-bold">3. 光盘外观</label>
              <div class="d-flex flex-column gap-2 mt-2">
                <label class="radio-label" v-for="item in [
                  { val: 'appearanceA', text: '光盘无划痕' },
                  { val: 'appearanceB', text: '光盘轻微划痕' },
                  { val: 'appearanceC', text: '光盘严重划痕' }
                ]" :key="item.val">
                  <input v-model="appearance" type="radio" :value="item.val">
                  <span>{{ item.text }}</span>
                </label>
              </div>
            </div>

            <!-- 包装盒 -->
            <div class="mb-4">
              <label class="form-label fw-bold">4. 包装盒状态</label>
              <div class="d-flex flex-column gap-2 mt-2">
                <label class="radio-label" v-for="item in [
                  { val: 'boxGood', text: '包装盒彩页齐全完好' },
                  { val: 'boxDamaged', text: '包装盒彩页齐全损坏' },
                  { val: 'boxNone', text: '无包装盒' }
                ]" :key="item.val">
                  <input v-model="box" type="radio" :value="item.val">
                  <span>{{ item.text }}</span>
                </label>
              </div>
            </div>

            <!-- 左侧表单底部：评估价格 -->
            <button
                type="button"
                class="btn btn-primary w-100 mt-3"
                :disabled="loading"
                @click="handleEvaluate"
            >
              {{ loading ? '评估中...' : '🧮 评估价格' }}
            </button>
          </form>
        </div>
      </div>

      <!-- 右侧预览 -->
      <div class="col-md-5">
        <div class="card p-4 shadow-sm h-100 bg-light">
          <h4 class="mb-3 text-success">📝 评估预览</h4>

          <div class="info-item">
            <span>游戏名：</span>
            <span>{{ gameName || '未填写' }}</span>
          </div>
          <div class="info-item">
            <span>版本：</span>
            <span>{{ version ? (version === 'guohang' ? '国行' : '非国行') : '未选择' }}</span>
          </div>

          <div class="info-item">
            <span>语言：</span>
            <span>{{ language ? (language === 'ChineseSupport' ? '支持中文' : '不支持中文') : '未选择' }}</span>
          </div>
          <div class="info-item">
            <span>外观：</span>
            <span>
              {{ appearance === 'appearanceA' ? '光盘无划痕' :
                appearance === 'appearanceB' ? '光盘轻微划痕' :
                    appearance === 'appearanceC' ? '光盘严重划痕' : '未选择' }}
            </span>
          </div>
          <div class="info-item">
            <span>包装盒：</span>
            <span>
              {{ box === 'boxGood' ? '包装盒彩页齐全完好' :
                box === 'boxDamaged' ? '包装盒彩页齐全损坏' :
                    box === 'boxNone' ? '无包装盒' : '未选择' }}
            </span>
          </div>

          <div class="price-item mt-4 p-3 bg-white rounded">
            <h5 class="mb-1">预估回收价格</h5>
            <h2 class="text-danger fw-bold">{{ formattedValuation }} 元</h2>
          </div>

          <!-- 右侧预览区：确认回收（生成真实订单） -->
          <button
              @click="handleConfirmRecycle"
              class="btn btn-success w-100 mt-4"
              :disabled="loading"
          >
            {{ loading ? '提交中...' : '✅ 确认回收' }}
          </button>
          <p class="text-muted small mt-3"><i class="bi bi-info-circle"></i>寄售商品将经过专业质检，请如实选择商品选项；</p>
        </div>
      </div>
    </div>
  </div>

  <PromptMsg :message="toastMessage" @clearMessage="toastMessage = ''" />
</template>

<style scoped>
.recycle-page {
  max-width: 1200px;
  margin: 0 auto;
}

/* 输入框仅下边框 */
.border-bottom-only {
  border: none;
  border-bottom: 2px solid #ccc;
  border-radius: 0;
  padding: 8px 0;
  transition: border-color 0.3s;
}
.border-bottom-only:focus {
  outline: none;
  border-bottom-color: #007bff;
  box-shadow: none;
}

/* 单选框美化 */
.radio-label {
  display: flex;
  align-items: center;
  cursor: pointer;
  padding: 6px 10px;
  border: 1px solid #ddd;
  border-radius: 6px;
  transition: all 0.2s;
}
.radio-label input {
  display: none;
}
.radio-label span {
  font-size: 15px;
  color: #444;
}
.radio-label:has(input:checked) {
  background: #007bff;
  border-color: #007bff;
}
.radio-label:has(input:checked) span {
  color: white;
}

/* 预览信息 */
.info-item {
  font-size: 16px;
  padding: 10px 0;
  border-bottom: 1px dashed #eee;
}
.info-item span:first-child {
  font-weight: bold;
  color: #333;
  width: 110px;
  display: inline-block;
}
.info-item span:last-child {
  color: #555;
}

.price-item {
  text-align: center;
  border: 1px solid #eee;
}

button {
  transition: all 0.2s;
}
button:disabled {
  opacity: 0.7;
  cursor: not-allowed;
}
</style>