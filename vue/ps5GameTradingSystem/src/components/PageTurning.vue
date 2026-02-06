<script setup lang="ts">

</script>

<template>
  <!--  翻页盒子 start-->
  <div class="demo-section">
<!--    1. 标准翻页样式-->
<!--    使用Bootstrap默认样式重新设计的翻页组件，适用于大多数场景。-->
    <div class="pagination-demo">
      <nav aria-label="标准翻页示例">
        <ul class="pagination">
          <li class="page-item">
            <a class="page-link" href="#" aria-label="首页">
              <span aria-hidden="true">首页</span>
            </a>
          </li>
          <li class="page-item disabled">
            <a class="page-link" href="#" aria-label="上一页">
              <span aria-hidden="true">上一页</span>
            </a>
          </li>
          <li class="page-item active" aria-current="page">
            <a class="page-link" href="#">1</a>
          </li>
          <li class="page-item">
            <a class="page-link" href="#">2</a>
          </li>
          <li class="page-item">
            <a class="page-link" href="#">3</a>
          </li>
          <li class="page-item">
            <a class="page-link" href="#">4</a>
          </li>
          <li class="page-item">
            <a class="page-link" href="#">5</a>
          </li>
          <li class="page-item">
            <a class="page-link" href="#" aria-label="下一页">
              <span aria-hidden="true">下一页</span>
            </a>
          </li>
          <li class="page-item">
            <a class="page-link" href="#" aria-label="末页">
              <span aria-hidden="true">末页</span>
            </a>
          </li>
        </ul>
      </nav>
    </div>
  </div>
  <!--   翻页盒子 end-->
</template>

<style scoped>
/*  翻页盒子的 CSS*/
body {
  background-color: #f8f9fa;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}
.container {
  max-width: 100%;
  margin: 0 auto;
}
.demo-header {
  text-align: center;
  margin-bottom: 40px;
  padding: 20px;
  background-color: #fff;
  border-radius: 10px;
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.05);
}
.demo-header h1 {
  color: #333;
  font-weight: 600;
  margin-bottom: 10px;
}
.demo-header p {
  color: #666;
  max-width: 800px;
  margin: 0 auto;
}
.demo-section {
  /*
  background-color: #fff;
  border-radius: 10px;

  padding: 30px;
  margin-bottom: 30px;
  */
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.05);
}
.demo-section h2 {
  color: #333;
  font-weight: 600;
  margin-bottom: 25px;
  padding-bottom: 15px;
  border-bottom: 1px solid #eee;
}
.pagination-demo {
  display: flex;
  justify-content: center;
  margin: 25px 0;
}
.code-container {
  background-color: #f8f9fa;
  border-radius: 8px;
  padding: 20px;
  margin-top: 30px;
  overflow-x: auto;
}
.code-container pre {
  margin: 0;
  font-family: 'Courier New', monospace;
}
.code-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 15px;
}
.code-header h3 {
  margin: 0;
  font-size: 1.1rem;
}
.copy-btn {
  background-color: #0d6efd;
  color: white;
  border: none;
  padding: 5px 15px;
  border-radius: 5px;
  cursor: pointer;
  font-size: 0.9rem;
  transition: background-color 0.3s;
}
.copy-btn:hover {
  background-color: #0b5ed7;
}
.feature-list {
  display: flex;
  flex-wrap: wrap;
  gap: 15px;
  margin-top: 20px;
}
.feature-item {
  flex: 1 1 200px;
  background-color: #f8f9fa;
  padding: 15px;
  border-radius: 8px;
  border-left: 4px solid #0d6efd;
}
.feature-item h4 {
  font-size: 1rem;
  margin-bottom: 8px;
  color: #333;
}
.feature-item p {
  font-size: 0.9rem;
  color: #666;
  margin: 0;
}
@media (max-width: 768px) {
  .demo-section {
    padding: 20px;
  }
  .pagination-lg .page-link {
    padding: 0.5rem 0.75rem;
    font-size: 1rem;
  }
  .pagination-sm .page-link {
    padding: 0.25rem 0.5rem;
    font-size: 0.8rem;
  }
}
</style>