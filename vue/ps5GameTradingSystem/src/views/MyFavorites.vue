<script setup lang="ts">

</script>

<template>
  <div class="table-responsive">
    <table class="table table-sm table-hover-custom">
      <thead>
        <tr>
          <th scope="col">序号</th>
          <th scope="col">游戏名</th>
          <th scope="col">单价</th>
          <th scope="col">数量</th>
          <th scope="col">总价</th>
          <th scope="col">操作</th>
        </tr>
      </thead>
      <tbody>
          <tr>
            <th scope="row">1</th>

            <td>小小梦魇2</td>
            <td>100元</td>
            <td>2个</td>
            <td>200元</td>
            <td>
              <div class="table-actions">
                <button class="btn btn-sm btn-outline-primary me-1">
                  <i class="bi bi-eye"></i></button>
                <button class="btn btn-sm btn-outline-success me-1">
                  <i class="bi bi-pencil"></i></button>
                <button class="btn btn-sm btn-outline-danger">
                  <i class="bi bi-trash"></i></button>
              </div>
            </td>
          </tr>
      </tbody>
    </table>
  </div>
</template>

<style scoped>


</style>