import { reactive } from 'vue';
import axios from 'axios';

axios.defaults.baseURL = 'http://localhost:8000/api/v1/auth/';

let refresh = false;
const data = reactive({
    token: localStorage.getItem('token')
});

axios.interceptors.response.use((response) => {

    if (response && response.status === 200) {
        return response;
    } else {
        console.error('No response from server');
        return Promise.reject(response);
    }
}, async (error) => {
    if (error.response && error.response.status === 403 && !refresh) {
        refresh = true;
        const response = await axios.post('refresh', data, { withCredentials: true });
        console.log(response);
        if (response && response.status === 200) {
            axios.defaults.headers.common['Authorization'] = `Bearer ${response.data.token}`;
            return axios(error.config);
        }
    }

    refresh = false;
    return Promise.reject(error);
});

export default {
    name: 'App',
    setup() {
        return {
            token: data.token
        }
    }
};
