<script setup lang="ts">
import PageTurning from "@/components/PageTurning.vue";
</script>

<template>
  <div class="container-fluid">
    <div class="my-account row">
      <div class="col-md-3">用户余额：</div>
      <div class="col-md-4">
        <div >100 元</div>
      </div>
    </div>
    <div class="top-up row">
      <div class="col-md-3">请输入充值金额</div>
      <div class="col-md-4">
        <form>
          <input min="0" type="number">
          <label>元</label>
          <button type="submit">确认充值</button>
        </form>
      </div>

    </div>
    <div class="account-record row">
      <table>
        <thead>
        <tr>

          <th>交易ID</th>
          <th>交易用户名</th>
          <th>交易时间</th>
          <th>金额</th>
          <th>交易状态</th>

        </tr>
        </thead>

        <tbody id="j_tb">
        <tr>
          <td>A1</td>
          <td>用户A</td>
          <td>2026-1-1</td>
          <td>100</td>
          <td>成功</td>
        </tr>

        </tbody>

      </table>
     <PageTurning/>

    </div>
  </div>
</template>

<style scoped>
.my-account{
  border: coral 1px solid;
}
.col-md-3{
  border: #104367 1px solid;
}
span{
  display: inline-block;
  font-size: 20px;
}



</style>