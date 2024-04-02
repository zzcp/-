import Axios from 'axios'
import config from './config'
export default async(url,params={},method="GET")=>{
    try {  
        const response =await Axios({  
            method,  
            url: config.url + url,  
            params,  
        }); 
        return response.data;  
    } catch (error) {  
        throw error.response;  
    }
}