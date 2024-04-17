<template>
  <main class="container">
    <div class="pt-3">
      <form @submit.prevent="submit">
        <h1 class="h3 mb-3 card-title text-center">Đăng ký</h1>
        <div class="form-floating form-group">
          <input v-model="data.email" type="email" class="form-control form-control-lg" :class="{ 'is-invalid': errors.email }"  placeholder="name@example.com" @input="onChange('email')">
          <label >Email address</label>
          <p v-if="errors.email" class="text-danger label">{{ errors.email }}</p>
        </div>
        <div class="form-floating form-group">
          <input v-model="data.password" type="password" class="form-control form-control-lg" :class="{ 'is-invalid': errors.password }"  placeholder="password" @input="onChange('password')">
          <label >Password</label>
          <p v-if="errors.password" class="text-danger label">{{ errors.password }}</p>
        </div>
        <div class="form-floating form-group">
          <input v-model="data.passwordConfirm" type="password" class="form-control form-control-lg" :class="{ 'is-invalid': errors.passwordConfirm }" placeholder="password confirm" @input="onChange('passwordConfirm')">
          <label >Password Confirm</label>
          <p v-if="errors.passwordConfirm" class="text-danger label">{{ errors.passwordConfirm }}</p>
        </div>
        <button class="w-100 btn btn-lg btn-success" type="submit">Đăng ký</button>
      </form>
    </div>
  </main>
</template>

<script>
import { reactive } from "vue";
import { useRouter } from "vue-router";
import AuthService from "@/services/auth.service.js";

export default {
  name: 'RegisterView',
  mounted() {
    document.title = 'Đăng ký';
  },
  setup() {
    const data = reactive({
      email:'',
      password:'',
      passwordConfirm: ''
    });
    const errors = reactive({
      email: '',
      password: '',
      passwordConfirm: ''
    });
    const router = useRouter();
    const onChange = (field) => {
      errors[field] = '';
    }
    const submit = async () => {
      if(!data.email) errors.email = 'Email là rỗng.';
      if(!data.password) errors.password = 'Password là rỗng.';
      if(!data.passwordConfirm) errors.passwordConfirm = 'PasswordConfirm là rỗng.';

      if(data.password !== data.passwordConfirm) {
        errors.passwordConfirm = 'Mật khẩu và mật khẩu xác nhận không khớp. Vui lòng nhập lại.';
      }

      if(Object.values(errors).every(error => !error)) {
        await AuthService.signup(data)
        await router.push('/login');
      }
    }
    return{
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
