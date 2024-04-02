<template>
  <div class="tags"> 
        <template v-for="(tag,index) in props.tags" :key="index">
            <span v-if="index<props.limit" @click="toSearch(tag,'tag')" :style="props.isBig?'font-size:12px':'font-size:10px'">{{ tag }}</span>
        </template>
    </div>
</template>

<script setup>
import { toSearch } from '@/utils/tools';
import { defineProps } from 'vue';
const props = defineProps({
    tags:{
        type:Array,
        default:()=>[]
    },
    // 限制显示的标签数
    limit:{
        type:Number,
        default:0
    },
    // 是否显示放大标签
    isBig:{
        type:Boolean,
        default:true
    }
})
</script>

<style scoped>
.tags{
    display: flex;
}

.tags>span{
    font-size: 10px;
    color: rgba(0,0,0,0.4);
    margin:5px 5px 5px 0;
    padding: 2px 5px;
    border-radius: 4px;
    border: 1px solid rgba(0,0,0,0.1);
    overflow: hidden;
    text-overflow: ellipsis;
    white-space:nowrap;
    cursor: pointer;
}

.tags>span:hover{
    color: #24B8F2;
    border: 1px solid #24B8F2;
}
</style>