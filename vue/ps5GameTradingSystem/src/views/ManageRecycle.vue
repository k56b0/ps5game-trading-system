<script setup lang="ts">
import { computed, onMounted, ref } from 'vue';
import PageTurning from "@/components/PageTurning.vue";
import { storeToRefs } from "pinia";
import type { recycle } from '@/types/recycle.ts';
import { useRecycleStore } from '@/store/recycleStore.ts';
import PromptMsg from "@/components/PromptMsg.vue";

const recycleStore = useRecycleStore();
const { myRecycleMap, current, pages } = storeToRefs(recycleStore);
const toastMessage = ref('');
const hoverRecycleId = ref<string | number | null>(null);

// 编辑模态框
const showEditModal = ref(false);
const selectedRecycle = ref<recycle | null>(null);

const openEditModal = (recycle: recycle) => {
  selectedRecycle.value = JSON.parse(JSON.stringify(recycle));
  showEditModal.value = true;
};

const closeEditModal = () => {
  showEditModal.value = false;
  selectedRecycle.value = null;
};

const saveRecycleChanges = async () => {
  if (!selectedRecycle.value) return;
  const success = await recycleStore.update(selectedRecycle.value);
  if (success) {
    toastMessage.value = '回收订单更新成功';
    await recycleStore.queryAllAll({ pageNum: current.value });
    closeEditModal();
  } else {
    toastMessage.value = '更新失败，请检查网络或联系管理员';
  }
};

// 分页
const handlePageChange = async (newPage: number) => {
  current.value = newPage;
  await recycleStore.queryAllAll({ pageNum: newPage });
};
const currentPage = computed(() => current.value);
const allPage = computed(() => pages.value);

onMounted(async () => {
  await recycleStore.queryAllAll({ pageNum: 1 });
});

// 多选相关
const selectedCount = computed(() => myRecycleMap.value.filter(r => r.checked).length);
const selectAll = computed({
  get: () => myRecycleMap.value.length > 0 && myRecycleMap.value.every(r => r.checked),
  set: (val) => myRecycleMap.value.forEach(r => r.checked = val)
});
function toggleSelectAll(event: any) {
  selectAll.value = event.target.checked;
}

// 删除单个
async function deleteOne(id: string) {
  const success = await recycleStore.delete(id);
  if (success) {
    toastMessage.value = '删除成功';
    await recycleStore.queryAllAll({ pageNum: current.value });
  } else {
    toastMessage.value = '删除失败';
  }
}

// 批量删除
async function handleBatchDelete() {
  const selectedIds = myRecycleMap.value.filter(r => r.checked).map(r => r.id);
  if (selectedIds.length === 0) {
    toastMessage.value = "未选中任何回收订单，无法执行批量删除。";
    return;
  }
  const isConfirmed = confirm(`确定要删除选中的 ${selectedIds.length} 个回收订单吗？此操作不可恢复。`);
  if (!isConfirmed) return;
  for (let id of selectedIds) {
    await recycleStore.delete(id as string);
  }
  await recycleStore.queryAllAll({ pageNum: current.value });
}

// ---------- 添加回收订单 ----------
const showAddModal = ref(false);
const generateUUID = (): string => {
  if (typeof crypto !== 'undefined' && crypto.randomUUID) {
    return crypto.randomUUID();
  }
  return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, (c) => {
    const r = Math.random() * 16 | 0;
    const v = c === 'x' ? r : (r & 0x3 | 0x8);
    return v.toString(16);
  });
};

const newRecycle = ref<recycle>({
  id: '',
  userId: '',
  gameName: '',
  valuation: 0,
  recyclingStatus: '',
  version: '',
  language: '',
  appearance: '',
  box: '',
});

const openAddModal = () => {
  newRecycle.value = {
    id: generateUUID(),
    userId: '',
    gameName: '',
    valuation: 0,
    recyclingStatus: '',
    version: '',
    language: '',
    appearance: '',
    box: '',
  };
  showAddModal.value = true;
};

const closeAddModal = () => {
  showAddModal.value = false;
};

const saveNewRecycle = async () => {
  if (!newRecycle.value.gameName || !newRecycle.value.userId) {
    toastMessage.value = '请填写游戏名称和用户ID';
    return;
  }
  const success = await recycleStore.addM(newRecycle.value);
  if (success) {
    toastMessage.value = '回收订单添加成功';
    await recycleStore.queryAllAll({ pageNum: current.value });
    closeAddModal();
  } else {
    toastMessage.value = '添加失败，请检查网络或联系管理员';
  }
};
</script>

<template>
  <div class="container mt-4">
    <div class="d-flex justify-content-between align-items-center mb-3">
      <div class="d-flex gap-2">
        <span class="fw-normal text-secondary small">已选中 <span class="badge bg-primary rounded-pill">{{ selectedCount }}</span> 个回收订单</span>
      </div>
      <div>
        <button class="btn btn-sm btn-outline-danger" @click="handleBatchDelete">
          <i class="bi bi-trash3"></i> 批量删除
        </button>
        <button class="btn btn-sm btn-outline-success" @click="openAddModal">
          <i class="bi bi-plus-circle"></i> 添加回收订单
        </button>
      </div>
    </div>

    <table class="table table-hover table-hover-custom">
      <thead>
      <tr>
        <th class="align-middle text-center"><input class="form-check-input" type="checkbox" :checked="selectAll" @change="toggleSelectAll"></th>
        <th>回收ID</th>
        <th>游戏名称</th>
        <th>估价</th>
        <th>回收状态</th>
        <th>版本</th>
        <th>语言</th>
        <th>外观</th>
        <th>包装盒</th>
        <th>操作</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="item in myRecycleMap" :key="item.id"
          @mouseenter="hoverRecycleId = item.id"
          @mouseleave="hoverRecycleId = null"
          :class="{ 'hover-active': hoverRecycleId === item.id }">
        <td class="align-middle text-center"><input class="form-check-input row-checkbox" type="checkbox" v-model="item.checked"></td>
        <td>{{ item.id }}</td>
        <td>{{ item.gameName }}</td>
        <td>{{ item.valuation }}</td>
        <td>{{ item.recyclingStatus }}</td>
        <td>{{ item.version }}</td>
        <td>{{ item.language }}</td>
        <td>{{ item.appearance }}</td>
        <td>{{ item.box }}</td>
        <td>
          <div class="table-actions" :style="{ opacity: hoverRecycleId === item.id ? '1' : '0' }">
            <button class="btn btn-sm btn-outline-primary me-1" @click="openEditModal(item)" title="编辑">
              <i class="bi bi-pencil-square"></i>
            </button>
            <button class="btn btn-sm btn-outline-danger" @click="deleteOne(item.id)" title="删除">
              <i class="bi bi-trash"></i>
            </button>
          </div>
        </td>
      </tr>
      </tbody>
    </table>
    <PageTurning :currentPage="currentPage" :allPage="allPage" @update:currentPage="handlePageChange"/>
  </div>

  <!-- 编辑回收订单模态框 -->
  <div v-if="showEditModal && selectedRecycle" class="modal fade show" style="display: block; background-color: rgba(0,0,0,0.5);" tabindex="-1">
    <div class="modal-dialog modal-lg">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title">编辑回收订单信息</h5>
          <button type="button" class="btn-close" @click="closeEditModal"></button>
        </div>
        <div class="modal-body">
          <form @submit.prevent="saveRecycleChanges">
            <div class="row mb-3">
              <label class="col-sm-3 col-form-label">游戏名称</label>
              <div class="col-sm-8">
                <input type="text" class="form-control" v-model="selectedRecycle.gameName" required>
              </div>
            </div>
            <div class="row mb-3">
              <label class="col-sm-3 col-form-label">用户ID</label>
              <div class="col-sm-8">
                <input type="text" class="form-control" v-model="selectedRecycle.userId" required>
              </div>
            </div>
            <div class="row mb-3">
              <label class="col-sm-3 col-form-label">估价</label>
              <div class="col-sm-8">
                <input type="number" step="0.01" class="form-control" v-model="selectedRecycle.valuation">
              </div>
            </div>
            <div class="row mb-3">
              <label class="col-sm-3 col-form-label">回收状态</label>
              <div class="col-sm-8">
                <input type="radio" value="待回收" v-model="selectedRecycle.recyclingStatus">
                <label>待回收</label>
                <input type="radio" value="回收成功" v-model="selectedRecycle.recyclingStatus">
                <label>回收成功</label>
                <input type="radio" value="回收失败" v-model="selectedRecycle.recyclingStatus">
                <label>回收失败</label>
              </div>
            </div>
            <div class="row mb-3">
              <label class="col-sm-3 col-form-label">版本</label>
              <div class="col-sm-8">
                <input type="text" class="form-control" v-model="selectedRecycle.version">
              </div>
            </div>
            <div class="row mb-3">
              <label class="col-sm-3 col-form-label">语言</label>
              <div class="col-sm-8">
                <input type="text" class="form-control" v-model="selectedRecycle.language">
              </div>
            </div>
            <div class="row mb-3">
              <label class="col-sm-3 col-form-label">外观</label>
              <div class="col-sm-8">
                <input type="text" class="form-control" v-model="selectedRecycle.appearance">
              </div>
            </div>
            <div class="row mb-3">
              <label class="col-sm-3 col-form-label">包装盒</label>
              <div class="col-sm-8">
                <input type="text" class="form-control" v-model="selectedRecycle.box">
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

  <!-- 添加回收订单模态框 -->
  <div v-if="showAddModal" class="modal fade show" style="display: block; background-color: rgba(0,0,0,0.5);" tabindex="-1">
    <div class="modal-dialog modal-lg">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title">添加回收订单</h5>
          <button type="button" class="btn-close" @click="closeAddModal"></button>
        </div>
        <div class="modal-body">
          <form @submit.prevent="saveNewRecycle">
            <div class="row mb-3">
              <label class="col-sm-3 col-form-label">游戏名称</label>
              <div class="col-sm-8">
                <input type="text" class="form-control" v-model="newRecycle.gameName" required>
              </div>
            </div>
            <div class="row mb-3">
              <label class="col-sm-3 col-form-label">用户ID</label>
              <div class="col-sm-8">
                <input type="text" class="form-control" v-model="newRecycle.userId" required>
              </div>
            </div>
            <div class="row mb-3">
              <label class="col-sm-3 col-form-label">估价</label>
              <div class="col-sm-8">
                <input type="number" step="0.01" class="form-control" v-model="newRecycle.valuation">
              </div>
            </div>
            <div class="row mb-3">
              <label class="col-sm-3 col-form-label">回收状态</label>
              <div class="col-sm-8">
                <div class="form-check form-check-inline">
                  <input class="form-check-input" type="radio" id="addStatusPending" value="待回收" v-model="newRecycle.recyclingStatus">
                  <label class="form-check-label" for="addStatusPending">待回收</label>
                </div>
                <div class="form-check form-check-inline">
                  <input class="form-check-input" type="radio" id="addStatusSuccess" value="回收成功" v-model="newRecycle.recyclingStatus">
                  <label class="form-check-label" for="addStatusSuccess">回收成功</label>
                </div>
                <div class="form-check form-check-inline">
                  <input class="form-check-input" type="radio" id="addStatusFail" value="回收失败" v-model="newRecycle.recyclingStatus">
                  <label class="form-check-label" for="addStatusFail">回收失败</label>
                </div>
              </div>
            </div>
            <div class="row mb-3">
              <label class="col-sm-3 col-form-label">版本</label>
              <div class="col-sm-8">
                <div class="form-check form-check-inline">
                  <input class="form-check-input" type="radio" id="versionDomestic" value="国行" v-model="newRecycle.version">
                  <label class="form-check-label" for="versionDomestic">国行</label>
                </div>
                <div class="form-check form-check-inline">
                  <input class="form-check-input" type="radio" id="versionForeign" value="非国行" v-model="newRecycle.version">
                  <label class="form-check-label" for="versionForeign">非国行</label>
                </div>
              </div>
            </div>
            <div class="row mb-3">
              <label class="col-sm-3 col-form-label">语言</label>
              <div class="col-sm-8">
                <div class="form-check form-check-inline">
                  <input class="form-check-input" type="radio" id="langSupport" value="支持中文" v-model="newRecycle.language">
                  <label class="form-check-label" for="langSupport">支持中文</label>
                </div>
                <div class="form-check form-check-inline">
                  <input class="form-check-input" type="radio" id="langNoSupport" value="不支持中文" v-model="newRecycle.language">
                  <label class="form-check-label" for="langNoSupport">不支持中文</label>
                </div>
              </div>
            </div>
            <div class="row mb-3">
              <label class="col-sm-3 col-form-label">外观</label>
              <div class="col-sm-8">
                <div class="form-check form-check-inline">
                  <input class="form-check-input" type="radio" id="appearA" value="光盘无划痕" v-model="newRecycle.appearance">
                  <label class="form-check-label" for="appearA">光盘无划痕</label>
                </div>
                <div class="form-check form-check-inline">
                  <input class="form-check-input" type="radio" id="appearB" value="光盘轻微划痕" v-model="newRecycle.appearance">
                  <label class="form-check-label" for="appearB">光盘轻微划痕</label>
                </div>
                <div class="form-check form-check-inline">
                  <input class="form-check-input" type="radio" id="appearC" value="光盘严重划痕" v-model="newRecycle.appearance">
                  <label class="form-check-label" for="appearC">光盘严重划痕</label>
                </div>
              </div>
            </div>
            <div class="row mb-3">
              <label class="col-sm-3 col-form-label">包装盒</label>
              <div class="col-sm-8">
                <div class="form-check form-check-inline">
                  <input class="form-check-input" type="radio" id="boxGood" value="包装盒彩页齐全完好" v-model="newRecycle.box">
                  <label class="form-check-label" for="boxGood">包装盒彩页齐全完好</label>
                </div>
                <div class="form-check form-check-inline">
                  <input class="form-check-input" type="radio" id="boxDamaged" value="包装盒彩页齐全损坏" v-model="newRecycle.box">
                  <label class="form-check-label" for="boxDamaged">包装盒彩页齐全损坏</label>
                </div>
                <div class="form-check form-check-inline">
                  <input class="form-check-input" type="radio" id="boxNone" value="无包装盒" v-model="newRecycle.box">
                  <label class="form-check-label" for="boxNone">无包装盒</label>
                </div>
              </div>
            </div>
            <div class="row">
              <div class="col-sm-3 offset-sm-3">
                <button type="submit" class="btn btn-primary">添加订单</button>
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
/* 保持原有样式不变 */
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