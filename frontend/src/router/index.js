import { createRouter, createWebHistory } from 'vue-router'

const routes = [
    {path:"/",component: () => import('@/views/HomeView.vue')},
    {path: "/register", component: () => import('@/views/RegisterView.vue')},
    {path: "/login",component: () => import('@/views/LoginView.vue')},
]

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes
})

export default router;
