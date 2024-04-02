<template>
  <div class="side-bar" :class="scrollTop>=550?'fixed':''" :style="scrollTop>=550?'display:block':'display:none'" @click="handleToTop">
  </div>
</template>

<script setup>
import { ref } from 'vue';

const scrollTop = ref(0)
window.addEventListener("scroll",()=>{
    scrollTop.value=document.documentElement.scrollTop
})

function handleToTop(){
    document.documentElement.scrollTop=0
}
</script>

<style scoped>
.side-bar{
    /* position: fixed; */
    bottom: 200px;
    right: 5%;
    height: 40px;
    width: 40px;
    /* background-color: #24B8F2; */
    background-image: url(../assets/totop.png);
    background-size: 40px 40px;
    /* background-position: -100px 0px; */
    z-index: 900;
    cursor: pointer;
}
</style>