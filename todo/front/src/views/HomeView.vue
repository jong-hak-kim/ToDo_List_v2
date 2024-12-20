<script setup lang="ts">
import Todo from '@/components/Todo.vue'
import { onMounted, reactive } from 'vue'
import TodoRepository from '@/repository/TodoRepository'
import { container } from 'tsyringe'

const TODO_REPOSITORY = container.resolve(TodoRepository)

const state = reactive({
  todoList: [],
})

function getList() {
  TODO_REPOSITORY.getList().then((todoList) => {
    state.todoList = todoList
    console.log('>>>', state.todoList)
  })
}

onMounted(() => {
  getList()
})
</script>
<template>
  <div class="content">
    <ul class="todos">
      <li v-for="todo in state.todoList" :key="todo.id">
        <Todo :todo="todo" />
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
