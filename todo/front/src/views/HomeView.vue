<script setup lang="ts">
import axios from 'axios'
import { ref } from 'vue'

const todos = ref([])

axios.get('/api/todos?page=1&size=5').then(response => {
  response.data.forEach((r: any) => {
    todos.value.push(r);
  })
})
</script>

<template>
  <ul>
    <li v-for="todo in todos" :key="todo.id">
      <div>
        {{ todo.title }}
      </div>

      <div>
        {{ todo.content }}
      </div>
    </li>
  </ul>
</template>

<style scoped>
li {
  margin-bottom: 1rem;
}

li:last-child {
  margin-bottom: 0;
}
</style>
