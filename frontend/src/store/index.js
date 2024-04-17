import { createStore } from 'vuex'

export default createStore({
    state: {
        auth: false,
        message: ''
    },
    mutations: {
        setAuth(state, auth){
            state.auth = auth;
        },
        setMessage(state, value) {
            state.message = value;
        },
    },
    actions: {
        setAuth({ commit }, auth){
            commit('setAuth',auth);
        },
        setMessage({ commit }, value) {
            commit('setMessage', value);
        },
    },
    modules: {
    }
})
