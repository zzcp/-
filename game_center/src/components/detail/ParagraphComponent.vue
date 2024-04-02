<template>
    <div class="paragraph-box">
        <span class="title" v-if="props.title">{{ props.title }}</span>
        <p class="recent-time" v-if="props.updateInfo">{{ props.updateInfo}}</p>
        <pre :class="isShow?'':'hidden'">
            {{ props.paragraph }}
        </pre>                  
        <span class="show" @click="isShow=!isShow">{{ isShow?'收起':'展开' }}<i :class="'iconfont '+(isShow?'icon-window-up':'icon-window-down')"></i></span>
    </div>
</template>

<script setup>
import { defineProps,ref } from 'vue';
const props = defineProps({
    paragraph:{
        type:String,
        default:""
    },
    title:{
        type:String,
        default:""
    },
    updateInfo:{
        type:String,
        default:""
    }
})

const isShow = ref(false)

</script>

<style scoped>
.detail-paragraph{
  /* position: relative; */
  display: flex;
  flex-direction: column;
}

pre{
  position: relative;
  width: 100%;
  word-wrap: break-word;
  white-space:pre-line;
  font-family: "Microsoft Yahei","PingFang SC",Helvetica,Arial,sans-serif;
  font-size: 14px;
  color: rgba(0,0,0,0.6);
  line-height: 2;
}

.title{
  display: block;
  border-left:4px solid #24B8F2;
  padding: 0 15px;
  margin-top: 20px;
  margin-bottom: 20px;
}

.recent-time{
  /* font-size: 14px; */
  color: #999;
}


.hidden{
  text-overflow: ellipsis;
  overflow: hidden;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 5;
}

.show{
  /* position: absolute; */
  bottom:0px;
  right: 0px;
  text-align: right;
  font-size: 14px;
  color: #24B8F2;
  cursor: pointer;
}

</style>