<template>
  <header id="header" class="header fixed-top d-flex align-items-center">
    <div class="d-flex align-items-center justify-content-between">
      <a href="" class="logo d-flex align-items-center">
        <span class="d-none d-lg-block">Gear5Post</span>
      </a>
    </div>

    <ul class="d-flex label">
      <li><router-link to="/" class="nav-link px-2 "> Trang chủ</router-link></li>
      <li class="dropdown">
        <router-link to="/merchandises" class="nav-link px-2 dropdown-toggle" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
          Hàng hóa
        </router-link>
        <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
          <li><router-link to="/merchandises" class="dropdown-item">Danh sách</router-link></li>
          <li><router-link to="/merchandises/create" class="dropdown-item">Thêm hàng hóa</router-link></li>
          <li><router-link to="/merchandises/" class="dropdown-item">Thống kê</router-link></li>
        </ul>
      </li>
    </ul>


    <nav class="header-nav ms-auto">
      <ul class="d-flex align-items-center">
        <li class="nav-item dropdown pe-3" v-if="!auth">
          <router-link to="/login" type="button" class="btn btn-outline-primary me-2">Login</router-link>
          <router-link to="/register" type="button" class="btn btn-warning">Sign-up</router-link>
        </li>
        <li class="nav-item dropdown pe-3" v-if="auth">

          <a class="nav-link nav-profile d-flex align-items-center pe-0" href="#" data-bs-toggle="dropdown">
            <img src="../assets/images/woman.png" alt="Profile" class="rounded-circle">
            <span class="d-none d-md-block dropdown-toggle ps-2">{{ message }}</span>
          </a>

          <ul class="dropdown-menu dropdown-menu-end dropdown-menu-arrow profile">
            <li class="dropdown-header">
              <h6>{{ message }}</h6>
              <span>Khách hàng</span>
            </li>
            <li>
              <hr class="dropdown-divider">
            </li>

            <li>
              <router-link to="/auth" class="dropdown-item d-flex align-items-center" >
                <i class="bi bi-person"></i>
                <span>Thông tin cá nhân</span>
              </router-link>
            </li>
            <li>
              <hr class="dropdown-divider">
            </li>

            <li>
              <a class="dropdown-item d-flex align-items-center" href="">
                <i class="bi bi-gear"></i>
                <span>Cài đặt tài khoản</span>
              </a>
            </li>
            <li>
              <hr class="dropdown-divider">
            </li>

            <li>
              <a class="dropdown-item d-flex align-items-center" href="">
                <i class="bi bi-question-circle"></i>
                <span>Trợ giúp</span>
              </a>
            </li>
            <li>
              <hr class="dropdown-divider">
            </li>

            <li>
              <a class="dropdown-item d-flex align-items-center" href="#">
                <i class="bi bi-box-arrow-right"></i>
                <router-link to="/" @click="logout" class="">Đăng xuất</router-link>
              </a>
            </li>

          </ul>
        </li>
      </ul>
    </nav>
  </header>
</template>

<script>
import {computed} from "vue";
import axios from "axios";
import {useStore} from "vuex";

export default {
  name: "NavbarComponent",
  setup(){
    const store = useStore();
    const auth = computed(() => store.state.auth);
    const message = computed(() => store.state.message);
    const logout = async () =>{
      localStorage.removeItem('token');
      axios.defaults.headers.common['Authorization'] = '';
      await store.dispatch('setMessage','')
      await store.dispatch('setAuth',false);
    }

    return {
      auth,
      logout,
      message
    }
  }
}
</script>
<style scoped>
ul.d-flex {
  display: flex;
  align-items: center;
  justify-content: flex-start;
  padding: 0;
  margin: 0;
  list-style-type: none;
}
.nav-link {
  margin-right: 10px;
}
.dropdown-toggle::after {
  display: none;
}
</style>