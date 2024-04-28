import { createRouter, createWebHistory } from 'vue-router'

const routes = [
    {path:"/",component: () => import('@/views/HomeView.vue')},
    {
        path:"/merchandises",
        component: () => import('@/components/MerchandiseComponent.vue'),
        meta: {requiresAuth: true},
        children: [
            {
                path:'',
                component: () => import('@/views/merchandises/merchandise-list.vue')
            },
            {
                path: 'create',
                component: () => import('@/views/merchandises/merchandise-create.vue')
            },
            {
                path: ':id/update',
                component: () => import('@/views/merchandises/merchandise-edit.vue'),
            },
            {
                path: ':id',
                component: () => import('@/views/merchandises/merchandise-show.vue'),
                props: route =>({
                    id: (route.params.id)
                })
            }
        ]

    },
    {
        path:"/auth",
        component: () => import('@/views/auth-page.vue'),
        meta: {requiresAuth: true},
    },
    {path: "/register", component: () => import('@/views/RegisterView.vue')},
    {path: "/login",component: () => import('@/views/LoginView.vue')},
]

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes
})
router.beforeEach((to, from, next) => {
    if (to.matched.some(record => record.meta.requiresAuth)) {
        if (!isLoggedIn()) {
            next({ path: '/login' });
        } else {
            next();
        }
    } else {
        next();
    }
});

export default router;

function isLoggedIn() {
    return !!localStorage.getItem('token');
}