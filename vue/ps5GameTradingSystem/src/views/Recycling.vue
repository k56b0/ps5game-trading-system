<script setup lang="ts">
import { useRecycleStore} from '@/store/recycleStore.ts'
import {useUserStore} from '@/store/userStore.ts'
import type {recycle} from '@/types/recycle.ts'
import {storeToRefs} from "pinia";
import {computed, onMounted, ref} from "vue";
import { v4 as uuidv4 } from 'uuid'


const useRecycle = useRecycleStore()
const useUser = useUserStore()

const {myInfo}=storeToRefs(useUser)
const {newRecycle}=storeToRefs(useRecycle)

const gameName = ref('')
const version = ref('')
const language = ref('')
const appearance = ref('')
const box = ref('')

const formattedValuation = computed(() => {
  const val = newRecycle.value.valuation // 可能为 undefined
  return val != null ? val.toFixed(2) : '0.00';
});
//获取 UUID
const uuid = ref('');
const generateNewUUID = () => {
  uuid.value = uuidv4();
};
onMounted(() => {
  generateNewUUID()
})
// 重置回收单
const reset = () => {
  newRecycle.value={} as recycle
  gameName.value = ''
  version.value = ''
  language.value = ''
  appearance.value = ''
  box.value = ''
}
async function handleAdd(){
  if(newRecycle.value.valuation ==null){
    const data: recycle = {
      id: uuid.value, // 或临时ID
      userId: myInfo.value.id,
      gameName: gameName.value,
      valuation: newRecycle.value.valuation, // 可能需要在提交后由后端计算，暂时留空
      recyclingStatus: '回收完毕', // 初始状态
      version: version.value === 'guohang' ? '国行' : '非国行',
      language: language.value === 'ChineseSupport' ? '支持中文' : '不支持中文',
      appearance: appearance.value=== 'appearanceA' ? '光盘无划痕' : appearance.value === 'appearanceB' ? '光盘轻微划痕' : '光盘严重划痕',
      box: box.value === 'boxGood' ? '包装盒彩页齐全完好' : box.value === 'boxDamaged' ? '包装盒彩页齐全损坏' : '无包装盒',
    }
    await useRecycle.add(data)
  }else{
    await useRecycle.add(newRecycle.value)
    reset()
    alert("回收成功")
  }

  //查询用户回收记录
  await useRecycle.queryAll(
      {
        userId:useUser.myInfo.id,
        pageNum:1
      }
  )
}
</script>

<template>
  <div class="container-fluid">
    <div class="row justify-content-center">
      <h1>回收评估</h1>
      <h3>寄售商品将经过专业质检，请如实选择商品选项</h3>
    </div>
    <div class="row justify-content-center">
      <div class="col-sm-4 center-col">
        <form @submit.prevent="handleAdd">
          <!-- 游戏名 -->
          <label class="labelTitle">0.游戏名</label>
          <label>
            <input type="text" v-model="gameName" placeholder="请输入游戏名">
          </label>
          <br>

          <!-- 1.版本选择 -->
          <label class="labelTitle">1.版本</label>
          <label class="radio-container">
            <input type="radio" v-model="version" value="guohangF" class="radio-input">
            <span class="radio-custom"><span class="radio-text">非国行</span></span>
          </label>
          <label class="radio-container">
            <input type="radio" v-model="version" value="guohang" class="radio-input">
            <span class="radio-custom"><span class="radio-text">国行</span></span>
          </label>

          <!-- 2.语言选择 -->
          <label class="labelTitle">2.语言</label>
          <label class="radio-container">
            <input type="radio" v-model="language" value="ChineseSupport" class="radio-input">
            <span class="radio-custom"><span class="radio-text">支持中文</span></span>
          </label>
          <label class="radio-container">
            <input type="radio" v-model="language" value="ChineseSupportF" class="radio-input">
            <span class="radio-custom"><span class="radio-text">不支持中文</span></span>
          </label>

          <!-- 3.外观呈色 -->
          <label class="labelTitle">3.外观呈色</label>
          <label class="radio-container">
            <input type="radio" v-model="appearance" value="appearanceA" class="radio-input">
            <span class="radio-custom"><span class="radio-text">光盘无划痕</span></span>
          </label>
          <label class="radio-container">
            <input type="radio" v-model="appearance" value="appearanceB" class="radio-input">
            <span class="radio-custom"><span class="radio-text">光盘轻微划痕</span></span>
          </label>
          <label class="radio-container">
            <input type="radio" v-model="appearance" value="appearanceC" class="radio-input">
            <span class="radio-custom"><span class="radio-text">光盘严重划痕</span></span>
          </label>

          <!-- 4.包装盒（注意 value 必须唯一） -->
          <label class="labelTitle">4.包装盒</label>
          <label class="radio-container">
            <input type="radio" v-model="box" value="boxGood" class="radio-input">
            <span class="radio-custom"><span class="radio-text">包装盒彩页齐全完好</span></span>
          </label>
          <label class="radio-container">
            <input type="radio" v-model="box" value="boxDamaged" class="radio-input">
            <span class="radio-custom"><span class="radio-text">包装盒彩页齐全损坏</span></span>
          </label>
          <label class="radio-container">
            <input type="radio" v-model="box" value="boxNone" class="radio-input">
            <span class="radio-custom"><span class="radio-text">无包装盒</span></span>
          </label>
          <input type="submit" value="提交">
        </form>
      </div>
      <div class="col-sm-4 side-col">
        <div class="labelTitle">评估的游戏名： <span class="labelContent">{{ gameName }}</span></div>
        <div class="labelTitle">版本： <span class="labelContent">{{ version === 'guohang' ? '国行' : '非国行' }}</span></div>
        <div class="labelTitle">语言： <span class="labelContent">{{ language === 'ChineseSupport' ? '支持中文' : '不支持中文' }}</span></div>
        <div class="labelTitle">外观呈色： <span class="labelContent">
  {{ appearance === 'appearanceA' ? '光盘无划痕' : appearance === 'appearanceB' ? '光盘轻微划痕' : '光盘严重划痕' }}
</span></div>
        <div class="labelTitle">包装盒： <span class="labelContent">
  {{ box === 'boxGood' ? '包装盒彩页齐全完好' : box === 'boxDamaged' ? '包装盒彩页齐全损坏' : '无包装盒' }}
</span></div>
        <div class="labelTitle">线上评估的价格： <span class="labelContent">{{formattedValuation}} 元</span></div>
        <div>
          <button @click="handleAdd">确认回收</button>
        </div>
      </div>

    </div>

  </div>
</template>

<style scoped>
.container-fluid{

}
.center-col{
  border: #4e7c46 1px solid;
}
.side-col{
  border: #6f42c1 1px solid;
}

.labelTitle{
  font-size: 20px;
  font-weight: bold;
  /*color: transparent; !* 默认透明 *!*/
  color: #1362b6; /* 默认透明 */
  transition: color 0.2s;
}
.labelContent{
  font-size: 20px;
  font-weight: bold;
  /*color: transparent; !* 默认透明 *!*/
  color: #4f6b88; /* 默认透明 */
  transition: color 0.2s;
}

.radio-input {
  display: none;
}

.radio-container {
  display: flex;
  align-items: center;
  cursor: pointer;
  margin: 10px 0;
}

.radio-custom {
  width: 200px;
  height: 20px;
  border: 2px solid #ddd;
  border-radius: 0;
  display: flex; /* Flexbox居中 */
  align-items: center; /* 垂直居中 */
  justify-content: center; /* 水平居中 */
  margin-right: 12px;
  transition: all 0.2s;
}

.radio-text {
  font-size: 16px;
  /*font-weight: bold;*/
  /*color: transparent; !* 默认透明 *!*/
  color: #007bff; /* 默认透明 */
  transition: color 0.2s;
}

.radio-input:checked ~ .radio-custom {
  border-color: #007bff;
  background-color: #007bff;
}

.radio-input:checked ~ .radio-custom .radio-text {
  color: white; /* 选中时显示白色文字 */
}

input[type="text"] {
  border:none;
  border-bottom: 1px solid black ;

}
input[type="submit"],button {
  display: block;
  margin: 0 auto;
  margin-left: 300px;
  position: relative;
  width: 80px;
  height: 36px;
  top:-35px;
  border-radius: 30px;
  color: #4880bd;
  font-size: 15px;
  cursor: pointer;
  border: solid black 1px;
}
h1,h3{
  margin-left: 300px;
}

</style>