<script setup lang="ts">

</script>

<template>
  <div class="container-fluid">
    <div class="row">
      <h1>回收评估</h1>
      <h3>寄售商品将经过专业质检，请如实选择商品选项</h3>
    </div>
    <div class="row">
      <div class="col-sm-4 center-col">
        <form action="#" method="post" target="_blank">
          <!-- 游戏名-->
          <label class="labelTitle"   >0.游戏名</label>

          <label>
            <input type="text" name="gameName" value="" placeholder="请输入游戏名">
          </label>
          <br>

          <!-- 1.版本选择 -->
          <label class="labelTitle">1.版本</label>
          <label class="radio-container">
            <input type="radio" name="guoHang" value="guohangF" class="radio-input">
            <span class="radio-custom">
            <span class="radio-text">非国行</span> <!-- 框内文字 -->
        </span>
          </label>

          <label class="radio-container">
            <input type="radio" name="guoHang" value="guohang" class="radio-input">
            <span class="radio-custom">
            <span class="radio-text">国行</span> <!-- 框内文字 -->
        </span>
          </label>
          <!-- 2.语言选择 -->
          <label class="labelTitle">2.语言</label>
          <label class="radio-container">
            <input type="radio" name="ChineseSupport" value="ChineseSupport" class="radio-input">
            <span class="radio-custom">
            <span class="radio-text">支持中文</span> <!-- 框内文字 -->
        </span>
          </label>
          <label class="radio-container">
            <input type="radio" name="ChineseSupport" value="ChineseSupportF" class="radio-input">
            <span class="radio-custom">
            <span class="radio-text">不支持中文</span> <!-- 框内文字 -->
        </span>
          </label>
          <!-- 3.外观呈色 -->
          <label class="labelTitle">3.外观呈色</label>
          <label class="radio-container">
            <input type="radio" name="appearance" value="appearanceA" class="radio-input">
            <span class="radio-custom">
            <span class="radio-text">光盘无划痕</span> <!-- 框内文字 -->
        </span>
          </label>
          <label class="radio-container">
            <input type="radio" name="appearance" value="appearanceB" class="radio-input">
            <span class="radio-custom">
            <span class="radio-text">光盘轻微划痕</span> <!-- 框内文字 -->
        </span>
          </label>
          <label class="radio-container">
            <input type="radio" name="appearance" value="appearanceC" class="radio-input">
            <span class="radio-custom">
            <span class="radio-text">光盘严重划痕</span> <!-- 框内文字 -->
        </span>
          </label>

          <!-- 4.包装盒 -->
          <label class="labelTitle">4.包装盒</label>
          <label class="radio-container">
            <input type="radio" name="packagingBox" value="packagingBoxA" class="radio-input">
            <span class="radio-custom">
            <span class="radio-text">包装盒彩页齐全完好</span> <!-- 框内文字 -->
        </span>
          </label>
          <label class="radio-container">
            <input type="radio" name="packagingBox" value="packagingBoxA" class="radio-input">
            <span class="radio-custom">
            <span class="radio-text">包装盒彩页齐全损坏</span> <!-- 框内文字 -->
        </span>
          </label>
          <label class="radio-container">
            <input type="radio" name="packagingBox" value="packagingBoxB" class="radio-input">
            <span class="radio-custom">
            <span class="radio-text">包装盒彩页齐全完好</span> <!-- 框内文字 -->
        </span>
          </label>
          <label class="radio-container">
            <input type="radio" name="packagingBox" value="packagingBoxC" class="radio-input">
            <span class="radio-custom">
                    <span class="radio-text">无包装盒</span> <!-- 框内文字 -->
                 </span>
          </label>
          <input type="submit" value="提交">

        </form>
      </div>
      <div class="col-sm-4 side-col">
        <div class="labelTitle">评估的游戏名： <span class="labelContent">XXX</span></div>
        <div class="labelTitle">版本： <span class="labelContent">国行</span></div>
        <div class="labelTitle">语言： <span class="labelContent">支持中文</span></div>
        <div class="labelTitle">外观呈色： <span class="labelContent">光盘无划痕</span></div>
        <div class="labelTitle">包装盒： <span class="labelContent">无包装盒</span></div>
        <div class="labelTitle">线上评估的价格： <span class="labelContent">200 元</span></div>
        <div>
          <button>确认回收</button>
        </div>
      </div>

    </div>

  </div>
</template>

<style scoped>
.container-fluid{
  margin-left: 300px;
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

</style>