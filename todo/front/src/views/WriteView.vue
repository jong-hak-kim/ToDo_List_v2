<script setup lang="ts">
import { reactive } from 'vue'
import TodoWrite from '@/entity/todo/TodoWrite'
import { container } from 'tsyringe'
import TodoRepository from '@/repository/TodoRepository'
import { ElMessage } from 'element-plus'
import type HttpError from '@/http/HttpError'
import { useRouter } from 'vue-router'

const state = reactive({
  todoWrite: new TodoWrite(),
})

const TODO_REPOSITORY = container.resolve(TodoRepository)

const router = useRouter()

function write() {
  TODO_REPOSITORY.write(state.todoWrite)
    .then(() => {
      ElMessage({ type: 'success', message: '글 등록이 완료되었습니다.' })
      router.replace('/')
    })
    .catch((e: HttpError) => {
      ElMessage({ type: 'error', message: e.getMessage() })
    })
}
</script>

<template>
  <el-form label-position="top">
    <el-form-item label="제목">
      <el-input v-model="state.todoWrite.title" size="large" placeholder="제목을 입력해주세요" />
    </el-form-item>

    <el-form-item label="내용">
      <el-input v-model="state.todoWrite.content" type="textarea" rows="15" alt="내용" />
    </el-form-item>

    <el-form-item>
      <el-button type="primary" style="..." @click="write()">등록 완료</el-button>
    </el-form-item>
  </el-form>
</template>

<style></style>
