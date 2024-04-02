import {defineStore} from 'pinia'
import state from './state.js'
import actions from './actions.js' 

const store = defineStore('global',{
    state,
    actions
})

export default store;