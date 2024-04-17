import './assets/main.css'

import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store';
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap/dist/js/bootstrap.bundle.min.js'
import AuthService from "@/services/auth.service.js";
import axios from "axios";

async function checkAuthentication() {
    const token = localStorage.getItem('token');
    if (token != null) {
        const response = await  AuthService.profile({
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });
        await store.dispatch('setMessage', response.data.username); // update message
        await store.dispatch('setAuth', true);
    } else {
        await store.dispatch('setAuth', false);
    }
}

checkAuthentication().then(() => {
    createApp(App).use(store).use(router).mount('#app');
});
