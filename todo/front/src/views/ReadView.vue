<script setup lang="ts">
import { onMounted, reactive } from 'vue'
import { container } from 'tsyringe'
import TodoRepository from '@/repository/TodoRepository'
import Todo from '@/entity/todo/Todo'
import { DateTimeFormatter } from '@js-joda/core'
import { ElMessage, ElMessageBox } from 'element-plus'
import { useRouter } from 'vue-router'

const props = defineProps<{
  todoId: number
}>()

const TODO_REPOSITORY = container.resolve(TodoRepository)

type StateType = {
  todo: Todo
}

const state = reactive<StateType>({
  todo: new Todo(),
})

function getPost() {
  TODO_REPOSITORY.get(props.todoId)
    .then((todo: Todo) => {
      state.todo = todo
    })
    .catch((e) => {
      console.error(e)
    })
}

const router = useRouter()

function remove() {
  ElMessageBox.confirm('정말로 삭제하시겠습니까?', 'Warning', {
    title: '삭제',
    confirmButtonText: '삭제',
    cancelButtonText: '취소',
    type: 'warning',
  }).then(() => {
    TODO_REPOSITORY.delete(props.todoId).then(() => {
      ElMessage({ type: 'success', message: '성공적으로 삭제되었습니다.' })
      router.back()
    })
  })
}

onMounted(() => {
  getPost()
})
</script>

<template>
  <div v-if="state.todo != null">
    <el-row>
      <el-col :span="22" :offset="1">
        <h2 class="title">{{ state.todo.title }}</h2>
      </el-col>
    </el-row>

    <el-row>
      <el-col span="10" :offset="7">
        <div class="title">
          <div class="regDate">Posted on {{ state.todo.getDisplayRegDate() }}</div>
        </div>
      </el-col>
    </el-row>

    <el-row>
      <el-col>
        <div class="content">{{ state.todo.content }}</div>
        <div class="footer">
          <div class="edit">수정</div>
          <div class="delete" @click="remove()">삭제</div>
        </div>
      </el-col>
    </el-row>

    <el-row class="comments">
      <el-col>
        <Comments />
      </el-col>
    </el-row>
  </div>
</template>

<style scoped lang="scss">
.title {
  font-size: 1.8rem;
  font-weight: 400;
  text-align: center;
}

.regDate {
  margin-top: 0.5rem;
  font-size: 0.78rem;
  font-weight: 300;
}

.content {
  margin-top: 1.88rem;
  font-weight: 300;

  word-break: break-all;
  white-space: break-spaces;
  line-height: 1.4;
  min-height: 5rem;
}

hr {
  border-color: #f9f9f9;
  margin: 1.2rem 0;
}

.footer {
  margin-top: 1rem;
  display: flex;
  font-size: 0.78rem;
  justify-content: flex-end;
  gap: 0.8rem;

  .delete {
    color: red;
  }
}

.comments {
  margin-top: 4.8rem;
}
</style>
