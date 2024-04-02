<template>
  <div>
    <HeaderComponent></HeaderComponent>
    <router-view v-slot="{ Component }">
        <transition>
            <component :is="Component" />
        </transition>
    </router-view>
    <FooterComponent></FooterComponent>
    <!-- <IndexComponent></IndexComponent> -->
  </div>
</template>

<script setup>
import HeaderComponent from './components/HeaderComponent.vue';
import FooterComponent from './components/FooterComponent.vue';
// import IndexComponent from "./components/index/IndexComponent.vue"
</script>

<style>
/* @import url(./assets/iconfont/iconfont.css); */
*{
  padding: 0;
  margin: 0;
}

a{
  text-decoration: none;
}

li{
  list-style: none;
}

html {
    scroll-behavior: smooth;
}


#app {
  height: calc(100vh);
  width: calc(100vw);
  min-width: 1200px;
  max-width: 100%;
}

.flex{
  display: flex!important;
}

.block{
  display: block!important;;
}

.none{
  display: none!important;
}

.fixed{
  position: fixed;
}

</style>
