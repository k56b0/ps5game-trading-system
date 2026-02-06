<script setup lang="ts">

</script>

<template>
  <div class="container-fluid">
    <div class="table-responsive">
      <table class="table table-custom-primary">
        <thead>
          <tr>
            <th scope="col">订单号</th>
            <th scope="col">用户名</th>
            <th scope="col">订单时间</th>
            <th scope="col">配送地址</th>
            <th scope="col">购买游戏信息</th>
            <th scope="col">总金额</th>
          </tr>

        </thead>
        <tbody>
          <tr>
            <th scope="row">ASDF1</th>
            <td>张三</td>
            <td>2026-1-1</td>
            <td>江南皮革厂</td>
            <td>
              <ul class="list-group">
                <li  class="list-group-item"> 游戏名：小小梦魇2|数量：1个|单价：100元</li>
                <li  class="list-group-item"> 游戏名：小小梦魇2|数量：1个|单价：100元</li>
              </ul>
            </td>
            <td>300元</td>
          </tr>
        </tbody>
      </table>
    </div>

  </div>
</template>

<style scoped>
.list-group-item{
  border:none;
  margin: 0;
  padding: 0;
}

</style>