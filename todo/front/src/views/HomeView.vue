<script setup lang="ts">
import axios from 'axios'
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const todos = ref([])

const router = useRouter()

axios.get('/api/todos?page=1&size=5').then(response => {
  response.data.forEach((r: any) => {
    todos.value.push(r)
  })
})
</script>

<template>
  <ul>
    <li v-for="todo in todos" :key="todo.id">
      <div>
        <router-link :to="{name: `read`, params: {todoId: todo.id}}">{{ todo.title }}</router-link>
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
