<script setup lang="ts"></script>

<template>
  <div class="title">
    <router-link :to="{ name: 'todo', params: { todoId: 1 } }">개발자의 서시</router-link>
  </div>

  <div class="regDate">Posted on 2024-12-17</div>

  <div class="content">
    윤동주는 일제강점기 시인이자 독립운동가로서, 그의 작품은 민족적 슬픔과 자기 성찰을 담고 있습니다. *"서시"*는 그의
    대표작입니다. 서시 죽는 날까지 하늘을 우러러 한 점 부끄럼이 없기를, 잎새에 이는 바람에도 나는 괴로워했다. 별을
    노래하는 마음으로 모든 죽어가는 것을 사랑해야지 그리고 나한테 주어진 길을 걸어가야겠다. 오늘 밤에도 별이 바람에
    스치운다.
  </div>

  <div class="readMore">
    <router-link :to="{ name: 'todo', params: { todoId: 1 } }">Read more</router-link>
  </div>
</template>

<style scoped lang="scss">
.title {
  a {
    color: inherit;
    text-decoration: none;
    font-size: 1.4rem;
    font-weight: 400;

    &:hover {
      text-decoration: underline;
    }
  }
}

.regDate {
  margin-top: 0.5rem;
  font-size: 0.78rem;
  font-weight: 300;
}

.content {
  margin-top: 0.88rem;
  font-weight: 300;
}

.readMore {
  margin-top: 0.3rem;

  a {
    font-size: 0.88rem;
    color: #3d8dcb;
    text-decoration: none;

    &:hover {
      text-decoration: underline;
    }
  }
}
</style>
