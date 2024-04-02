<template>
  <div>
    <CarouselComponent></CarouselComponent>
    <div class="index-content">
      <NewGameRecommend></NewGameRecommend>
      <div class="date-container">
        <GameRecommend></GameRecommend>
        <div>
          <TopList></TopList>
          <SortList></SortList>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import CarouselComponent from "./CarouselComponent.vue"
import NewGameRecommend from './NewGameRecommend.vue';
import GameRecommend from "./GameRecommend.vue"
import TopList from "./TopList.vue";
import SortList from "./SortList.vue"
</script>

<style scoped>
.index-content{
  width: 1200px;
  margin: 0 auto;
}

.date-container{
  display: flex;
  justify-content: space-between;
  margin: 10px 0;
}
</style>
