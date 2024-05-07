<template>
  <main class="row">
    <div class="col-sm-8">
      <form @submit.prevent="submit">
        <h1 class="h3 mb-3 card-title text-center">Đăng nhập</h1>
        <div class="mb-3">
          <div class="form-floating form-group">
            <input v-model="data.email" type="email" class="form-control form-control-lg" :class="{ 'is-invalid': errors.email }"  placeholder="name@example.com" @input="onChange('email')">
            <label>Địa chỉ email</label>
            <p v-if="errors.email" class="text-danger label"> {{ errors.email }}</p>
          </div>
        </div>
        <div class="mb-3">
          <div class="form-floating form-group">
            <input v-model="data.password" type="password" class="form-control form-control-lg" :class="{ 'is-invalid': errors.password }"  placeholder="password" @input="onChange('password')">
            <label>Mật khẩu</label>
            <p v-if="errors.password" class="text-danger label"> {{ errors.password }}</p>
          </div>
        </div>
        <button class="w-100 btn btn-lg btn-success" type="submit">Đăng nhập</button>
      </form>
    </div>
    <div class="col-sm-4">
      <img src="../assets/images/cargo-truck.png" alt="Tra cứu vận chuyển" class="img-fluid">
    </div>
  </main>
</template>

<script>
import { reactive } from "vue";
import { useRouter } from "vue-router";
import axios from "axios";
import { useStore } from 'vuex';
import AuthService from "@/services/auth.service.js";

export default {
  name: "LoginView",
  mounted() {
    document.title = 'Đăng nhập'
  },
  setup() {
    const data = reactive({
      email: '',
      password: ''
    });
    const errors = reactive({
      email: '',
      password: ''
    });
    const store = useStore();
    const router = useRouter();
    const onChange = (field) => {
      errors[field] = '';
    }
    const submit = async () => {
      try {
        if (!data.email) errors.email = 'Email là rỗng.';
        if (!data.password) errors.password = 'Password là rỗng.';

        if (Object.values(errors).every(error => !error)) {
          const response = await AuthService.login(data);

          if (response && response.status === 200) {
            await store.dispatch('setAuth', true);
            localStorage.setItem('token', response.data.token);
            axios.defaults.headers.common['Authorization'] = `Bearer ${response.data.token}`;
            await router.push('/');
          } else {
            errors.email = 'Email hoặc mật khẩu không đúng.';
            errors.password = 'Email hoặc mật khẩu không đúng.';
          }
        }
      } catch (error) {
        console.error(error);
      }
    };

    return {
      data,
      submit,
      errors,
      onChange
    }
  }
}
</script>

<style scoped>
.is-invalid {
  border-color: red;
}
</style>
