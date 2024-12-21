<script setup lang="ts">
import Todo from '@/components/Todo.vue'
import { onMounted, reactive } from 'vue'
import TodoRepository from '@/repository/TodoRepository'
import { container } from 'tsyringe'
import Paging from '@/entity/data/Paging'

const TODO_REPOSITORY = container.resolve(TodoRepository)

type StateType = {
  todoList: Paging<Todo>
}

const state = reactive<StateType>({
  todoList: new Paging<Todo>(),
})

function getList(page = 1) {
  TODO_REPOSITORY.getList(page).then((todoList) => {
    state.todoList = todoList
  })
}

onMounted(() => {
  getList()
})
</script>
<template>
  <div class="content">
    <span class="totalCount">게시글 수: {{ state.todoList.totalCount }}</span>
    <ul class="todos">
      <li v-for="todo in state.todoList.items" :key="todo.id">
        <Todo :todo="todo" />
      </li>
    </ul>

    <el-pagination
      :background="true"
      v-model:current-page="state.todoList.page"
      layout="prev, pager, next"
      :total="state.todoList.totalCount"
      :default-page-size="3"
      @current-change="(page) => getList(page)"
    ></el-pagination>
  </div>
</template>

<style scoped lang="scss">
.content {
  padding: 0 1rem 0 1rem;
  margin-bottom: 2rem;
}

.totalCount {
  font-size: 0.88rem;
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
