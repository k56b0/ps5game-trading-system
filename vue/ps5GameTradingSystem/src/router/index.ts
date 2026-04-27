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
import ShoppingCart from "@/views/ShoppingCart.vue";
import PromptPage from "@/views/PromptPage.vue";
import SearchGames from "@/views/SearchGames.vue";
import GameDetails from "@/views/GameDetails.vue";
import FindPassword from "@/views/FindPassword.vue";
import Management from "@/views/Management.vue";
import ManageGame from "@/views/ManageGame.vue";
import ManageUser from "@/views/ManageUser.vue";
import ManageRecycle from "@/views/ManageRecycle.vue";


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
            path:'/ShoppingCart',
            component:ShoppingCart
        },
        {
            path:'/PromptPage',
            component:PromptPage
        },
        {
            path:'/SearchGames',
            component:SearchGames
        },
        {
            path:'/GameDetails',
            component:GameDetails
        },

        {
            path:'/FindPassword',
            component:FindPassword
        },
        {
            path:'/Management',
            component:Management,
            children:[
                {
                    // 子集不需要写 '/'
                    path:'ManageGame',
                    component:ManageGame
                },
                {
                    path:'ManageUser',
                    component:ManageUser
                },
                {
                    path:'ManageRecycle',
                    component:ManageRecycle
                },
            ]
        },

        {
            // 路径 '/' 的重定向
         path:'/',
         redirect:'/Home'
        },


    ]
})
export default router