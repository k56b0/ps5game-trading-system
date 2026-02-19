<script setup lang="ts">
import {useGameStore} from '@/store/gameStore.ts'
import {useFavoriteStore} from '@/store/favoriteStore.ts'
import {useShoppingCarStore} from '@/store/shoppingCarStore.ts'
import {useUserStore} from '@/store/userStore.ts'
import { v4 as uuidv4 } from 'uuid'
import {ref} from "vue";
import {storeToRefs} from "pinia";
import {useRouter } from 'vue-router'

const router = useRouter()
// 使用 pinia
const userStore =useUserStore()
const useGame =useGameStore()
const useFavorite = useFavoriteStore()
const useShoppingCar = useShoppingCarStore()
const { GameOneShow,discountedPrice} = storeToRefs(useGame);
const { myInfo} = storeToRefs(userStore);
const goBack = () => {
  // 返回上一页，如果上一页不存在则默认回到首页（可选）
  if (window.history.state?.back) {
    router.back()
  } else {
    router.push('/') // 或者你想去的默认页面
  }
}
//获取 UUID
const uuid = ref('');
const generateNewUUID = () => {
  uuid.value = uuidv4();
};
async function addFavorites(){
  generateNewUUID()
  await useFavorite.add(
      {
        id:uuid.value,
        userId:myInfo.value.id,
        gameName:GameOneShow.value.gameName,
      }
  )
  //查询收藏夹信息
  await useFavorite.queryAll(
      {
        userId:myInfo.value.id,
        pageNum:1
      }
  )
}
async function addShoppingCar(){
  generateNewUUID()
  await useShoppingCar.add(
      {
        id:uuid.value,
        userId:myInfo.value.id,
        price:discountedPrice.value ,
        gameName:GameOneShow.value.gameName,
        quantity:1
      }
  )
  //查询收藏夹信息
  await useShoppingCar.queryAll(
      {
        userId:myInfo.value.id,
        pageNum:1
      }
  )
}
</script>

<template>
  <div class="container">
    <div class="product-container">
      <div class="product-image">
        <img :src="GameOneShow.img" alt="">
      </div>

      <div class="product-details">
        <h2 class="product-title">{{GameOneShow.gameName}}</h2>

        <div class="product-price">
          ¥{{discountedPrice}} <span class="original-price">¥{{GameOneShow.price}}</span>
        </div>
        <div class="">
         <span class="original-price">{{GameOneShow.introduction}}</span>
        </div>

        <table class="table table-custom-primary">
          <thead>
          <tr>
            <th>属性</th>
            <th>详情</th>
          </tr>
          </thead>
          <tbody>
          <tr>
            <td>类型</td>
            <td>{{GameOneShow.category}}</td>
          </tr>
          <tr>
            <td>首发年份</td>
            <td>{{GameOneShow.year}} 年</td>
          </tr>
          <tr>
            <td>已售</td>
            <td>{{GameOneShow.sales}}件</td>
          </tr>
          <tr>
            <td>库存状态</td>
            <td>
                   <span class="stock-status in-stock">
                        <i class="fas fa-check-circle"></i> 剩余 {{GameOneShow.inventory}} 件
                        </span>
            </td>
          </tr>
          </tbody>
        </table>


          <div class="btn-group">
            <button class="btn btn-primary" @click="addFavorites">
              <i class="far fa-heart"></i> 添加到收藏夹
            </button>
            <button class="btn btn-success" @click="addShoppingCar">
              <i class="fas fa-shopping-cart"  ></i> 添加到购物车
            </button>
            <button class="btn btn-warning" @click="goBack">
              <i class="fas fa-shopping-cart"></i> 返回
            </button>
          </div>
      </div>
    </div>
  </div>

</template>

<style scoped>

.container {
  max-width: 1000px;
  width: 100%;
  margin: 0 auto;
}
.product-container {
  display: flex;
  flex-wrap: wrap;
  background: white;
  border-radius: 16px;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.08);
  overflow: hidden;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

/*加上去，鼠标放在product-container装饰的区域时的时候，会弹一下*/
/*.product-container:hover {
  transform: translateY(-5px);
  box-shadow: 0 15px 40px rgba(0, 0, 0, 0.12);
}*/

.product-image {
  flex: 1;
  min-width: 300px;
  max-width: 450px;
  padding: 30px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #f5f7fa 0%, #e4e8f0 100%);
}

.product-image img {
  max-width: 100%;
  max-height: 350px;
  object-fit: contain;
  border-radius: 8px;
  transition: transform 0.5s ease;
}

.product-image img:hover {
  transform: scale(1.05);
}

.product-details {
  flex: 1.2;
  min-width: 300px;
  padding: 35px;
  background-color: white;
}

.product-title {
  font-size: 1.8rem;
  color: #2c3e50;
  margin-bottom: 10px;
  padding-bottom: 10px;
  border-bottom: 1px solid #eee;
}
.product-price {
  font-size: 2rem;
  color: #e74c3c;
  font-weight: 700;
  margin: 20px 0;
}

.product-price .original-price {
  font-size: 1.2rem;
  color: #95a5a6;
  text-decoration: line-through;
  margin-left: 10px;
}

.stock-status {
  display: inline-flex;
  align-items: center;
  padding: 5px 12px;
  border-radius: 20px;
  font-weight: 600;
  font-size: 0.9rem;
}

.in-stock {
  background-color: #d5f4e6;
  color: #27ae60;
}
.btn {
  padding: 14px 28px;
  border: none;
  border-radius: 8px;
  font-size: 1rem;
  font-weight: 600;
  cursor: pointer;
  align-items: center;
  justify-content: center;
  transition: all 0.3s ease;
  min-width: 150px;
}

@keyframes pulse {
  0% { transform: scale(1); }
  50% { transform: scale(1.2); }
  100% { transform: scale(1); }
}
@media (max-width: 768px) {
  .product-container {
    flex-direction: column;
  }

  .product-image, .product-details {
    max-width: 100%;
  }

  .btn {
    min-width: 100%;
  }
}

@media (max-width: 480px) {
  .product-table th, .product-table td {
    padding: 10px 8px;
    font-size: 0.9rem;
  }

  .product-title {
    font-size: 1.5rem;
  }

  .product-price {
    font-size: 1.7rem;
  }
}

</style>