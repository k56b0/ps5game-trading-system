<script setup lang="ts">
import { computed, onMounted, ref } from 'vue';
import PageTurning from "@/components/PageTurning.vue";
import { storeToRefs } from "pinia";
import type { game } from '@/types/game.ts';      // 注意引入游戏类型
import { useUserStore } from '@/store/userStore.ts';
import { useGameStore } from "@/store/gameStore.ts";
import PromptMsg from "@/components/PromptMsg.vue";

const useGame = useGameStore();
const useUser = useUserStore();
const { myGamesMap, current, pages } = storeToRefs(useGame);
const toastMessage = ref('');
const hoverOrderId = ref<string | number | null>(null);
// 编辑模态框相关
const showEditModal = ref(false);
const selectedGame = ref<game | null>(null);   // 当前正在编辑的游戏对象

// 打开编辑模态框，并复制一份游戏数据（避免直接修改原表）
const openEditModal = (game: game) => {
  // 深拷贝一份，避免表单修改直接影响到表格中的显示（直到保存成功才刷新）
  selectedGame.value = JSON.parse(JSON.stringify(game));
  showEditModal.value = true;
};

// 关闭模态框
const closeEditModal = () => {
  showEditModal.value = false;
  selectedGame.value = null;
};

// 保存修改
const saveGameChanges = async () => {
  if (!selectedGame.value) return;

  const success = await useGame.update(selectedGame.value);
  if (success) {
    toastMessage.value = '游戏信息更新成功';
    // 刷新当前页的游戏列表（保持当前页码）
    await useGame.queryAll(current.value);
    closeEditModal();
  } else {
    toastMessage.value = '更新失败，请检查网络或联系管理员';
  }
};

// 页码切换、删除、批量删除等函数保持原样...
const handlePageChange = async (newPage: number) => {
  current.value = newPage;
  await useGame.queryAll(newPage);
};
const currentPage = computed(() => current.value);
const allPage = computed(() => pages.value);

onMounted(async () => {
  await useGame.queryAll(1);
});

const selectedCount = computed(() => myGamesMap.value.filter(g => g.checked).length);
const selectAll = computed({
  get: () => myGamesMap.value.length > 0 && myGamesMap.value.every(g => g.checked),
  set: (val) => myGamesMap.value.forEach(g => g.checked = val)
});
function toggleSelectAll(event: any) {
  selectAll.value = event.target.checked;
}
async function deleteOne(id: string) {
  await useGame.delete(id);
  await useGame.queryAll(1);
}
async function handleBatchDelete() {
  const selectedIds = myGamesMap.value.filter(g => g.checked).map(g => g.id);
  if (selectedIds.length === 0) {
    toastMessage.value = "未选中任何游戏，无法执行批量删除。";
    return;
  }
  const isConfirmed = confirm(`确定要删除选中的 ${selectedIds.length} 个游戏吗？此操作不可恢复。`);
  if (!isConfirmed) return;
  for (let id of selectedIds) {
    await useGame.delete(id as string);
  }
  await useGame.queryAll(1);
}
// 添加游戏模态框
const showAddModal = ref(false);
const newGame = ref<game>({
  id: '',           // 稍后生成 UUID
  gameName: '',
  englishName: '',
  price: 0,
  discount: 1,
  inventory: 0,
  category: '',
  sales: 0,
  introduction: '',
  year: '',
  img: ''
});
// 生成随机 UUID (格式: xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx)
const generateUUID = (): string => {
  // 优先使用 crypto.randomUUID()
  if (typeof crypto !== 'undefined' && crypto.randomUUID) {
    return crypto.randomUUID();
  }
  // 降级方案 (简单随机数，但不保证是标准 UUID)
  return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, (c) => {
    const r = Math.random() * 16 | 0;
    const v = c === 'x' ? r : (r & 0x3 | 0x8);
    return v.toString(16);
  });
};
const openAddModal = () => {
  // 重置表单
  newGame.value = {
    id: generateUUID(),   // ← 生成新的 UUID
    gameName: '',
    englishName: '',
    price: 0,
    discount: 1,
    inventory: 0,
    category: '',
    sales: 0,
    introduction: '',
    year: '',
    img: ''
  };
  showAddModal.value = true;
};
const closeAddModal = () => {
  showAddModal.value = false;
};
const saveNewGame = async () => {
  // 验证必填字段
  if (!newGame.value.gameName || !newGame.value.price || !newGame.value.category) {
    toastMessage.value = '请填写游戏名称、价格和种类';
    return;
  }

  // 如果 year 是纯数字年份，转换为 YYYY-01-01（与编辑保持一致）
  if (newGame.value.year && !newGame.value.year.includes('-')) {
    const yearNum = parseInt(newGame.value.year);
    if (!isNaN(yearNum)) {
      newGame.value.year = `${yearNum}-01-01`;
    }
  }

  // 确保有 id（理论上打开模态框时已生成，但再检查一下）
  if (!newGame.value.id) {
    newGame.value.id = generateUUID();
  }

  const success = await useGame.add(newGame.value);   // add 方法需要接收完整的 game 对象（含 id）
  if (success) {
    toastMessage.value = '游戏添加成功';
    await useGame.queryAll(current.value);
    closeAddModal();
  } else {
    toastMessage.value = '添加失败，请检查网络或联系管理员';
  }
};
</script>

<template>
  <div class="container mt-4">
    <!-- 已选中数量 + 批量删除按钮（不变） -->
    <div class="d-flex justify-content-between align-items-center mb-3">
      <div class="d-flex gap-2">
        <span class="fw-normal text-secondary small">已选中 <span class="badge bg-primary rounded-pill">{{ selectedCount }}</span> 款游戏</span>
      </div>
      <div>
        <button class="btn btn-sm btn-outline-danger" @click="handleBatchDelete">
          <i class="bi bi-trash3"></i> 批量删除
        </button>
        <button class="btn btn-sm btn-outline-success" @click="openAddModal">
          <i class="bi bi-shield-plus"></i> 添加游戏
        </button>
      </div>
    </div>

    <table class="table table-hover table-hover-custom">
      <thead>
      <tr>
        <th class="align-middle text-center"><input class="form-check-input" type="checkbox" :checked="selectAll" @change="toggleSelectAll"></th>
        <th>游戏编号</th>
        <th>游戏名</th>
        <th>游戏原价</th>
        <th>打折比例</th>
        <th>库存</th>
        <th>种类</th>
        <th>已售</th>
        <th>操作</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="game in myGamesMap" :key="game.id"
          @mouseenter="hoverOrderId = game.id"
          @mouseleave="hoverOrderId = null"
          :class="{ 'hover-active': hoverOrderId === game.id }">
        <td class="align-middle text-center"><input class="form-check-input row-checkbox" type="checkbox" v-model="game.checked"></td>
        <td>{{ game.id }}</td>
        <td>{{ game.gameName }}</td>
        <td>{{ game.price }}</td>
        <td>{{ game.discount }}</td>
        <td>{{ game.inventory }}</td>
        <td>{{ game.category }}</td>
        <td>{{ game.sales }}</td>
        <td>
          <div class="table-actions" :style="{ opacity: hoverOrderId === game.id ? '1' : '0' }">
            <button class="btn btn-sm btn-outline-primary me-1" @click="openEditModal(game)" title="查看/编辑">
              <i class="bi bi-eye"></i>
            </button>
            <button class="btn btn-sm btn-outline-danger" @click="deleteOne(game.id)" title="删除">
              <i class="bi bi-trash"></i>
            </button>
          </div>
        </td>
      </tr>
      </tbody>
    </table>
    <PageTurning :currentPage="currentPage" :allPage="allPage" @update:currentPage="handlePageChange"/>
  </div>

  <!-- 编辑模态框 -->
  <div v-if="showEditModal && selectedGame" class="modal fade show" style="display: block; background-color: rgba(0,0,0,0.5);" tabindex="-1">
    <div class="modal-dialog modal-lg">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title">编辑游戏信息</h5>
          <button type="button" class="btn-close" @click="closeEditModal"></button>
        </div>
        <div class="modal-body">
          <form @submit.prevent="saveGameChanges">
            <div class="row mb-3">
              <label class="col-sm-3 col-form-label">游戏名称</label>
              <div class="col-sm-8">
                <input type="text" class="form-control" v-model="selectedGame.gameName" required>
              </div>
            </div>
            <div class="row mb-3">
              <label class="col-sm-3 col-form-label">英文名</label>
              <div class="col-sm-8">
                <input type="text" class="form-control" v-model="selectedGame.englishName">
              </div>
            </div>
            <div class="row mb-3">
              <label class="col-sm-3 col-form-label">原价</label>
              <div class="col-sm-8">
                <input type="number" step="0.01" class="form-control" v-model="selectedGame.price" required>
              </div>
            </div>
            <div class="row mb-3">
              <label class="col-sm-3 col-form-label">折扣 (0~1)</label>
              <div class="col-sm-8">
                <input type="number" step="0.01" min="0" max="1" class="form-control" v-model="selectedGame.discount" required>
              </div>
            </div>
            <div class="row mb-3">
              <label class="col-sm-3 col-form-label">库存</label>
              <div class="col-sm-8">
                <input type="number" step="1" class="form-control" v-model="selectedGame.inventory" required>
              </div>
            </div>
            <div class="row mb-3">
              <label class="col-sm-3 col-form-label">种类</label>
              <div class="col-sm-8">
                <input type="text" class="form-control" v-model="selectedGame.category" required>
              </div>
            </div>
            <div class="row mb-3">
              <label class="col-sm-3 col-form-label">已售</label>
              <div class="col-sm-8">
                <input type="number" class="form-control" v-model="selectedGame.sales" required>
              </div>
            </div>
            <div class="row mb-3">
              <label class="col-sm-3 col-form-label">介绍</label>
              <div class="col-sm-8">
                <textarea class="form-control" rows="3" v-model="selectedGame.introduction"></textarea>
              </div>
            </div>
            <div class="row mb-3">
              <label class="col-sm-3 col-form-label">发行年份</label>
              <div class="col-sm-8">
                <input type="date" class="form-control" v-model="selectedGame.year">
              </div>
            </div>
            <div class="row mb-3">
              <label class="col-sm-3 col-form-label">图片URL</label>
              <div class="col-sm-8">
                <input type="text" class="form-control" v-model="selectedGame.img">
              </div>
            </div>
            <div class="row">
              <div class="col-sm-3 offset-sm-3">
                <button type="submit" class="btn btn-primary">保存修改</button>
                <button type="button" class="btn btn-secondary ms-2" @click="closeEditModal">取消</button>
              </div>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
  <!-- 添加游戏模态框 -->
  <div v-if="showAddModal" class="modal fade show" style="display: block; background-color: rgba(0,0,0,0.5);" tabindex="-1">
    <div class="modal-dialog modal-lg">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title">添加新游戏</h5>
          <button type="button" class="btn-close" @click="closeAddModal"></button>
        </div>
        <div class="modal-body">
          <form @submit.prevent="saveNewGame">
            <div class="row mb-3">
              <label class="col-sm-3 col-form-label">游戏名称</label>
              <div class="col-sm-8">
                <input type="text" class="form-control" v-model="newGame.gameName" required>
              </div>
            </div>
            <div class="row mb-3">
              <label class="col-sm-3 col-form-label">英文名</label>
              <div class="col-sm-8">
                <input type="text" class="form-control" v-model="newGame.englishName">
              </div>
            </div>
            <div class="row mb-3">
              <label class="col-sm-3 col-form-label">原价</label>
              <div class="col-sm-8">
                <input type="number" step="0.01" class="form-control" v-model="newGame.price" required>
              </div>
            </div>
            <div class="row mb-3">
              <label class="col-sm-3 col-form-label">折扣 (0~1)</label>
              <div class="col-sm-8">
                <input type="number" step="0.01" min="0" max="1" class="form-control" v-model="newGame.discount" required>
              </div>
            </div>
            <div class="row mb-3">
              <label class="col-sm-3 col-form-label">库存</label>
              <div class="col-sm-8">
                <input type="number" step="1" class="form-control" v-model="newGame.inventory" required>
              </div>
            </div>
            <div class="row mb-3">
              <label class="col-sm-3 col-form-label">种类</label>
              <div class="col-sm-8">
                <select class="form-control" v-model="newGame.category" required>
                  <option value="" disabled>请选择游戏种类</option>
                  <option value="射击">射击</option>
                  <option value="体育">体育</option>
                  <option value="恐怖">恐怖</option>
                  <option value="策略">策略</option>
                  <option value="JRPG">JRPG</option>
                  <option value="动作">动作</option>
                  <option value="经营">经营</option>
                  <option value="竞速">竞速</option>
                  <option value="开放世界">开放世界</option>
                  <option value="角色扮演">角色扮演</option>
                  <option value="双人">双人</option>
                  <option value="联机">联机</option>
                </select>
              </div>
            </div>
            <div class="row mb-3">
              <label class="col-sm-3 col-form-label">已售</label>
              <div class="col-sm-8">
                <input type="number" class="form-control" v-model="newGame.sales" required>
              </div>
            </div>
            <div class="row mb-3">
              <label class="col-sm-3 col-form-label">介绍</label>
              <div class="col-sm-8">
                <textarea class="form-control" rows="3" v-model="newGame.introduction"></textarea>
              </div>
            </div>
            <div class="row mb-3">
              <label class="col-sm-3 col-form-label">发行年份</label>
              <div class="col-sm-8">
                <input type="date" class="form-control" v-model="newGame.year">
              </div>
            </div>
            <div class="row mb-3">
              <label class="col-sm-3 col-form-label">图片URL</label>
              <div class="col-sm-8">
                <input type="text" class="form-control" v-model="newGame.img">
              </div>
            </div>
            <div class="row">
              <div class="col-sm-3 offset-sm-3">
                <button type="submit" class="btn btn-primary">添加游戏</button>
                <button type="button" class="btn btn-secondary ms-2" @click="closeAddModal">取消</button>
              </div>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>

  <PromptMsg :message="toastMessage" @clearMessage="toastMessage = ''" />
</template>

<style scoped>
/* 原有样式保持不变，可适当调整 */
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
.table {
  width: 100%;
  overflow-x: auto;
}
.btn-outline-primary:hover {
  background-color: #0d6efd;
  color: white;
}
</style>