<script setup lang="ts">
import { computed, onMounted, ref } from 'vue';
import PageTurning from "@/components/PageTurning.vue";
import { storeToRefs } from "pinia";
import type { user } from '@/types/user.ts';
import { useUserStore } from '@/store/userStore.ts';
import PromptMsg from "@/components/PromptMsg.vue";

const userStore = useUserStore();
const { myUsersMap, current, pages } = storeToRefs(userStore);
const toastMessage = ref('');
const hoverUserId = ref<string | number | null>(null);

// 编辑模态框
const showEditModal = ref(false);
const selectedUser = ref<user | null>(null);

const openEditModal = (user: user) => {
  selectedUser.value = JSON.parse(JSON.stringify(user));
  showEditModal.value = true;
};

const closeEditModal = () => {
  showEditModal.value = false;
  selectedUser.value = null;
};

const saveUserChanges = async () => {
  if (!selectedUser.value) return;
  const success = await userStore.update(selectedUser.value);
  if (success) {
    toastMessage.value = '用户信息更新成功';
    await userStore.queryAll({ pageNum: current.value });
    closeEditModal();
  } else {
    toastMessage.value = '更新失败，请检查网络或联系管理员';
  }
};

// 分页
const handlePageChange = async (newPage: number) => {
  current.value = newPage;
  await userStore.queryAll({ pageNum: newPage });
};
const currentPage = computed(() => current.value);
const allPage = computed(() => pages.value);

onMounted(async () => {
  await userStore.queryAll({ pageNum: 1 });
});

// 多选相关
const selectedCount = computed(() => myUsersMap.value.filter(u => u.checked).length);
const selectAll = computed({
  get: () => myUsersMap.value.length > 0 && myUsersMap.value.every(u => u.checked),
  set: (val) => myUsersMap.value.forEach(u => u.checked = val)
});
function toggleSelectAll(event: any) {
  selectAll.value = event.target.checked;
}

// 删除单个
async function deleteOne(id: string) {
  const success = await userStore.delete(id);
  if (success) {
    toastMessage.value = '删除成功';
    await userStore.queryAll({ pageNum: current.value });
  } else {
    toastMessage.value = '删除失败';
  }
}

// 批量删除
async function handleBatchDelete() {
  const selectedIds = myUsersMap.value.filter(u => u.checked).map(u => u.id);
  if (selectedIds.length === 0) {
    toastMessage.value = "未选中任何用户，无法执行批量删除。";
    return;
  }
  const isConfirmed = confirm(`确定要删除选中的 ${selectedIds.length} 个用户吗？此操作不可恢复。`);
  if (!isConfirmed) return;
  for (let id of selectedIds) {
    await userStore.delete(id as string);
  }
  await userStore.queryAll({ pageNum: current.value });
}

// ---------- 添加用户 ----------
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

const newUser = ref<user>({
  id: '',
  userName: '',
  gender: '',
  birth: '',
  password: '',
  checkIn: '0',
  email: '',
  address: '',
  answer: '',
  checkTime: '',
  money: 0,
  isManage: 1   // 默认为普通用户
});

const openAddModal = () => {
  newUser.value = {
    id: generateUUID(),
    userName: '',
    gender: '',
    birth: '',
    password: '',
    checkIn: '0',
    email: '',
    address: '',
    answer: '',
    checkTime: new Date().toISOString().slice(0, 19).replace('T', ' '), // ✅ 添加当前时间，格式：YYYY-MM-DD HH:mm:ss
    money: 0,
    isManage: 1
  };
  showAddModal.value = true;
};

const closeAddModal = () => {
  showAddModal.value = false;
};

const saveNewUser = async () => {
  if (!newUser.value.userName || !newUser.value.email) {
    toastMessage.value = '请填写用户名和邮箱';
    return;
  }
  // 可选的简单密码默认值
  if (!newUser.value.password) {
    newUser.value.password = '123456';
  }
  const success = await userStore.add(newUser.value);
  if (success) {
    toastMessage.value = '用户添加成功';
    await userStore.queryAll({ pageNum: current.value });
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
        <span class="fw-normal text-secondary small">已选中 <span class="badge bg-primary rounded-pill">{{ selectedCount }}</span> 名用户</span>
      </div>
      <div>
        <button class="btn btn-sm btn-outline-danger" @click="handleBatchDelete">
          <i class="bi bi-trash3"></i> 批量删除
        </button>
        <button class="btn btn-sm btn-outline-success" @click="openAddModal">
          <i class="bi bi-person-plus"></i> 添加用户
        </button>
      </div>
    </div>

    <table class="table table-hover table-hover-custom">
      <thead>
      <tr>
        <th class="align-middle text-center"><input class="form-check-input" type="checkbox" :checked="selectAll" @change="toggleSelectAll"></th>
        <th>用户名</th>
        <th>生日</th>
        <th>性别</th>
        <th>地址</th>
        <th>邮箱</th>
        <th>签到天数</th>
        <th>找回密码答案</th>
        <th>操作</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="user in myUsersMap" :key="user.id"
          @mouseenter="hoverUserId = user.id"
          @mouseleave="hoverUserId = null"
          :class="{ 'hover-active': hoverUserId === user.id }">
        <td class="align-middle text-center"><input class="form-check-input row-checkbox" type="checkbox" v-model="user.checked"></td>
        <td>{{ user.userName }}</td>
        <td>{{ user.birth }}</td>
        <td>{{ user.gender }}</td>
        <td>{{ user.address }}</td>
        <td>{{ user.email }}</td>
        <td>{{ user.checkIn }}</td>
        <td>{{ user.answer }}</td>
        <td>
          <div class="table-actions" :style="{ opacity: hoverUserId === user.id ? '1' : '0' }">
            <button class="btn btn-sm btn-outline-primary me-1" @click="openEditModal(user)" title="编辑">
              <i class="bi bi-pencil-square"></i>
            </button>
            <button class="btn btn-sm btn-outline-danger" @click="deleteOne(user.id)" title="删除">
              <i class="bi bi-trash"></i>
            </button>
          </div>
        </td>
      </tr>
      </tbody>
    </table>
    <PageTurning :currentPage="currentPage" :allPage="allPage" @update:currentPage="handlePageChange"/>
  </div>

  <!-- 编辑用户模态框 -->
  <div v-if="showEditModal && selectedUser" class="modal fade show" style="display: block; background-color: rgba(0,0,0,0.5);" tabindex="-1">
    <div class="modal-dialog modal-lg">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title">编辑用户信息</h5>
          <button type="button" class="btn-close" @click="closeEditModal"></button>
        </div>
        <div class="modal-body">
          <form @submit.prevent="saveUserChanges">
            <div class="row mb-3">
              <label class="col-sm-3 col-form-label">用户名</label>
              <div class="col-sm-8">
                <input type="text" class="form-control" v-model="selectedUser.userName" required>
              </div>
            </div>
            <div class="row mb-3">
              <label class="col-sm-3 col-form-label">生日</label>
              <div class="col-sm-8">
                <input type="date" class="form-control" v-model="selectedUser.birth">
              </div>
            </div>
            <div class="row mb-3">
              <label class="col-sm-3 col-form-label">性别</label>
              <div class="col-sm-8">
                <select class="form-control" v-model="selectedUser.gender">
                  <option value="">未选择</option>
                  <option value="男">男</option>
                  <option value="女">女</option>
                </select>
              </div>
            </div>
            <div class="row mb-3">
              <label class="col-sm-3 col-form-label">地址</label>
              <div class="col-sm-8">
                <input type="text" class="form-control" v-model="selectedUser.address">
              </div>
            </div>
            <div class="row mb-3">
              <label class="col-sm-3 col-form-label">邮箱</label>
              <div class="col-sm-8">
                <input type="email" class="form-control" v-model="selectedUser.email" required>
              </div>
            </div>
            <div class="row mb-3">
              <label class="col-sm-3 col-form-label">密码</label>
              <div class="col-sm-8">
                <input type="password" class="form-control" v-model="selectedUser.password">
              </div>
            </div>
            <div class="row mb-3">
              <label class="col-sm-3 col-form-label">找回密码答案</label>
              <div class="col-sm-8">
                <input type="text" class="form-control" v-model="selectedUser.answer">
              </div>
            </div>
            <div class="row mb-3">
              <label class="col-sm-3 col-form-label">是否管理员</label>
              <div class="col-sm-8">
                <select class="form-control" v-model="selectedUser.isManage">
                  <option :value="0">管理员</option>
                  <option :value="1">普通用户</option>
                </select>
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

  <!-- 添加用户模态框 -->
  <div v-if="showAddModal" class="modal fade show" style="display: block; background-color: rgba(0,0,0,0.5);" tabindex="-1">
    <div class="modal-dialog modal-lg">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title">添加新用户</h5>
          <button type="button" class="btn-close" @click="closeAddModal"></button>
        </div>
        <div class="modal-body">
          <form @submit.prevent="saveNewUser">
            <div class="row mb-3">
              <label class="col-sm-3 col-form-label">用户名</label>
              <div class="col-sm-8">
                <input type="text" class="form-control" v-model="newUser.userName" required>
              </div>
            </div>
            <div class="row mb-3">
              <label class="col-sm-3 col-form-label">生日</label>
              <div class="col-sm-8">
                <input type="date" class="form-control" v-model="newUser.birth">
              </div>
            </div>
            <div class="row mb-3">
              <label class="col-sm-3 col-form-label">性别</label>
              <div class="col-sm-8">
                <select class="form-control" v-model="newUser.gender">
                  <option value="">未选择</option>
                  <option value="男">男</option>
                  <option value="女">女</option>
                </select>
              </div>
            </div>
            <div class="row mb-3">
              <label class="col-sm-3 col-form-label">地址</label>
              <div class="col-sm-8">
                <input type="text" class="form-control" v-model="newUser.address">
              </div>
            </div>
            <div class="row mb-3">
              <label class="col-sm-3 col-form-label">邮箱</label>
              <div class="col-sm-8">
                <input type="email" class="form-control" v-model="newUser.email" required>
              </div>
            </div>
            <div class="row mb-3">
              <label class="col-sm-3 col-form-label">密码</label>
              <div class="col-sm-8">
                <input type="password" class="form-control" v-model="newUser.password" placeholder="默认123456">
              </div>
            </div>
            <div class="row mb-3">
              <label class="col-sm-3 col-form-label">找回密码答案</label>
              <div class="col-sm-8">
                <input type="text" class="form-control" v-model="newUser.answer">
              </div>
            </div>
            <div class="row mb-3">
              <label class="col-sm-3 col-form-label">是否管理员</label>
              <div class="col-sm-8">
                <select class="form-control" v-model="newUser.isManage">
                  <option :value="0">管理员</option>
                  <option :value="1">普通用户</option>
                </select>
              </div>
            </div>
            <div class="row">
              <div class="col-sm-3 offset-sm-3">
                <button type="submit" class="btn btn-primary">添加用户</button>
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
/* 样式保持原样，可酌情微调 */
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