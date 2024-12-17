<script setup lang="ts">
import Todo from '@/components/Todo.vue'
</script>
<template>
  <div class="content">
    <ul class="todos">
      <li>
        <Todo />
      </li>
      <li>
        <Todo />
      </li>
      <li>
        <Todo />
      </li>
    </ul>
  </div>
</template>

<style scoped lang="scss">
.content {
  height: calc(100vh - 60px - 2rem - 20px - 1.5rem);
  padding: 0 1rem 0 1rem;
}

.todos {
  list-style: none;
  padding: 0;

  li {
    margin-bottom: 2.4rem;

    &:last-child {
      margin-bottom: 0;
    }
  }
}
</style>
