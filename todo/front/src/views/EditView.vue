<script setup lang="ts">
import { ref } from 'vue'

import axios from 'axios'
import { useRouter } from 'vue-router'

const router = useRouter()

const todo = ref({
  id: 0,
  title: '',
  content: ''
})

const props = defineProps({
  todoId: {
    type: [Number, String],
    require: true
  }
})

axios.get(`/api/todos/${props.todoId}`).then(response => {
  todo.value = response.data
})

const edit = () => {
  axios.patch(`/api/todos/${props.todoId}`, todo.value).then(() => {
    router.replace({ name: "home" })
  })
}

</script>

<template>
  <div>
    <el-input v-model="todo.title" />
  </div>

  <div class="mt-2">
    <el-input v-model="todo.content" type="textarea" rows="15" />
  </div>

  <div class="mt-2 d-flex justify-content-end">
    <el-button type="warning" @click="edit()">수정 완료</el-button>
  </div>
</template>

<style>

</style>
