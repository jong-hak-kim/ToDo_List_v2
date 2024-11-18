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

  <el-row>
    <el-col>
      <h2 class="title">{{ todo.title }}</h2>

      <div class="sub d-flex">
        <div class="category">개발</div>
        <div class="regDate">2024-11-18 23:59:59</div>
      </div>
    </el-col>
  </el-row>

  <el-row class="mt-3">
    <el-col>
      <div class="content">{{ todo.content }}</div>
    </el-col>
  </el-row>

  <el-row class="mt-3">
    <el-col>
      <div class="d-flex justify-content-end">
        <el-button type="warning" @click="moveToEdit()">수정</el-button>
      </div>
    </el-col>
  </el-row>
</template>

<style scoped lang="scss">
.title {
  font-size: 1.6rem;
  font-weight: 600;
  color: #383838;
  margin: 0;
}

.sub {
  margin-top: 10px;
  font-size: 0.78rem;

  .regDate {
    margin-left: 10px;
    color: #6b6b6b;
  }
}

.content {
  font-size: 0.95rem;
  margin-top: 12px;
  color: #616161;
  white-space: break-spaces;
  line-height: 1.5;
}

</style>
