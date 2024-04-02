<template>
  <div>
    <ul class="related_list">
        <template v-for="(item,index) in props.relatedList" :key="index">
            <li v-if="item">
                <img :src="item.imgUrl" :data-id="item.gameId" @click="toDetail">
                <div class="game-info">
                    <span class="name" :data-id="item.gameId" @click="toDetail" :title="item.gameName">{{ item.gameName }}</span>
                    <span class="info">开发商：{{ item.developer }}</span>
                    <span class="score">评分：{{ item.score }}</span>
                    <div class="tags">
                        <TagsComponent :tags="item.tags" :limit=3 :isBig="false"></TagsComponent>
                        <!-- <span v-for="(tag,t_index) in item.tags" :key="t_index">{{ tag }}</span> -->
                    </div>
                </div>
            </li>
        </template>
    </ul>
  </div>
</template>

<script setup>
import { toDetail } from '@/utils/tools';
import { defineProps } from 'vue';
import TagsComponent from '../TagsComponent.vue';

const props = defineProps({
    relatedList:{
        type:Array,
        default:()=>[]
    }
})

// const related_list = reactive([
    // {
    //     img_url:'https://s1.hdslb.com/bfs/game/faa556b00d29fffc88281c1ee038b1b7f23aa5c2.jpg@150w_150h_1c_100q.webp',
    //     name:'明日方舟',
    //     info:'运营厂商：鹰角网络',
    //     score:'7.5',
    //     tags:["策略", "架空文明", "末世"]
    // },{
//         img_url:'https://s1.hdslb.com/bfs/game/b6e59889b5749d8046e6d6eb84c86081d57db097.png@150w_150h_1c_100q.webp',
//         name:'公主连结Re:Dive',
//         info:'运营厂商：bilibili游戏',
//         score:'8.4',
//         tags:["角色扮演"]
//     },{
//         img_url:'https://s1.hdslb.com/bfs/game/ca49392b97e1efe6d24a22f3c12e4d67f026cbf9.png@150w_150h_1c_100q.webp',
//         name:'原神',
//         info:'运营厂商：上海米哈游影铁科技有限公司',
//         score:'6.5',
//         tags:["角色扮演", "二次元", "冒险"]
//     },{
//         img_url:'https://s1.hdslb.com/bfs/game/b108e3f091dad6d43cd1cd35c119216e87011a8f.png@150w_150h_1c_100q.webp',
//         name:'崩坏：星穹铁道',
//         info:'运营厂商：米哈游',
//         score:'8.2',
//         tags:["角色扮演", "冒险"]
//     },{
//         img_url:'https://s1.hdslb.com/bfs/game/d255b3268533779ae362ed3686699c2a570de298.png@150w_150h_1c_100q.webp',
//         name:'命运-冠位指定（Fate/GO）',
//         info:'运营厂商：bilibili游戏',
//         score:'6.0',
//         tags:["卡牌", "fate", "厨向"]
//     }
// ])
</script>

<style scoped>
.related_list{
    display: flex;
    flex-direction: column;
}

.related_list>li{
    display: flex;
    margin-bottom: 20px;
}

.related_list>li>img{
    height: 80px;
    width: 80px;
    border-radius: 10px;
    margin-right: 10px;
    cursor: pointer;
}

.game-info{
    display: flex;
    flex-direction: column;
    justify-content: space-between;
}

.game-info>.name{
    font-size: 16px;
    white-space:nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
    width: 310px;
    cursor: pointer;
    margin-bottom: 3px;
}

.game-info>.info{
    font-size: 12px;
    margin-bottom: 3px;
    color: rgba(0,0,0,.6);
    white-space:nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
}

.game-info>.score{
    font-size: 12px;
    /* margin-bottom: 3px; */
    color: #34B8F2;
}

.game-info>.name:hover{
    color: #24B8F2;
}
.game-info>.tags>span:hover{
    color: #24B8F2;
    border: 1px solid #24B8F2;
}
</style>