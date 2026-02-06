import {createRouter,createWebHistory} from 'vue-router'
import Home from '@/views/Home.vue';
import SignUp from '@/views/SignUp.vue';
import ProblemFeedBack from "@/views/ProblemFeedBack.vue";
import PersonalCenter from "@/views/PersonalCenter.vue";
import MyInfo from "@/views/MyInfo.vue";
import MyOrder from "@/views/MyOrder.vue";
import MyFavorites from "@/views/MyFavorites.vue";
import MyRecycle from "@/views/MyRecycle.vue";
import MyWallet from "@/views/MyWallet.vue";
import Recycling from "@/views/Recycling.vue";
import ItemDetail from "@/views/ItemDetail.vue";
import ShoppingCart from "@/views/ShoppingCart.vue";
import SearchPage from "@/views/SearchPage.vue";
import Test from "@/views/Test.vue";
import PromptPage from "@/views/PromptPage.vue";

const router = createRouter({
    history:createWebHistory(),
    routes:[
        {
            path:'/Home',
            component:Home
        },
        {
            path:'/ProblemFeedBack',
            component:ProblemFeedBack
        },
        {
            path:'/PersonalCenter',
            component:PersonalCenter,
            children:[
                {
                    // 子集不需要写 '/'
                    path:'MyInfo',
                    component:MyInfo
                },
                {
                    path:'MyOrder',
                    component:MyOrder
                },
                {
                    path:'MyFavorites',
                    component:MyFavorites
                },
                {
                    path:'MyRecycle',
                    component:MyRecycle
                },
                {
                    path:'MyWallet',
                    component:MyWallet
                },

            ]
        },
        {
            path:'/SignUp',
            component:SignUp
        },
        {
            path:'/Recycling',
            component:Recycling
        },
        {
            path:'/ItemDetail',
            component:ItemDetail
        },
        {
            path:'/ShoppingCart',
            component:ShoppingCart
        },
        {
            path:'/SearchPage',
            component:SearchPage
        },
        {
            path:'/Test',
            component:Test
        },
        {
            path:'/PromptPage',
            component:PromptPage
        },

        {
            // 路径 '/' 的重定向
         path:'/',
         redirect:'/Home'
        },


    ]
})
export default router