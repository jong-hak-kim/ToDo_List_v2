<script setup lang="ts">
import { onMounted, ref } from 'vue'
import axios from 'axios'
import router from '@/router'

const props = defineProps({
  todoId: {
    type: [Number, String],
    require: true
  }
})

const todo = ref({
  id: 0,
  title: '',
  content: ''
})

const moveToEdit = () => {
  router.push({ name: 'edit', params: { todoId: props.todoId } })
}

onMounted(() => {
  axios.get(`/api/todos/${props.todoId}`).then(response => {
    todo.value = response.data
  })
})

</script>

<template>
  <h2>{{ todo.title }}</h2>
  <div>{{ todo.content }}</div>

  <el-button type="warning" @click="moveToEdit()">수정</el-button>
</template>
