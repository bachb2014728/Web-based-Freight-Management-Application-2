<template>
  <main class="row">
    <div class="">
      <form @submit.prevent="submit">
        <h1 class="h3 mb-3 card-title text-center">Đăng ký</h1>
        <div class="row">
          <div class="col-sm">
            <div class="form-floating form-group mb-3">
              <input v-model="data.email" type="email" class="form-control form-control-lg" :class="{ 'is-invalid': errors.email }"  placeholder="name@example.com" @input="onChange('email')">
              <label >Địa chỉ email</label>
              <p v-if="errors.email" class="text-danger label">{{ errors.email }}</p>
            </div>
            <div class="form-floating form-group mb-3">
              <input v-model="data.firstName" type="text" class="form-control form-control-lg" placeholder="firstname">
              <label >Họ</label>
            </div>
            <div class="form-floating form-group mb-3">
              <input v-model="data.lastName" type="text" class="form-control form-control-lg" placeholder="lastname">
              <label >Tên</label>
            </div>
            <div class="form-floating form-group mb-3">
              <input v-model="data.password" type="password" class="form-control form-control-lg" :class="{ 'is-invalid': errors.password }"  placeholder="password" @input="onChange('password')">
              <label >Mật khẩu</label>
              <p v-if="errors.password" class="text-danger label">{{ errors.password }}</p>
            </div>

          </div>
          <div class="col-sm">
            <div class="form-floating form-group mb-3" >
              <select id="province" name="province" class="form-select mb-3" v-model="selectedCity" @change="updateCityId">
                <option value="" disabled selected>Chọn tỉnh thành</option>
                <option v-for="(city, index) in cities" :value="city" :key="index">{{ city.name }}</option>
              </select>
              <select id="district" name="district" class="form-select mb-3" v-model="selectedDistrict" @change="updateDistrictId">
                <option value="" disabled selected>Chọn quận huyện</option>
                <option v-for="(district, index) in districts" :value="district" :key="index">{{ district.name }}</option>
              </select>
              <select id="ward" name="ward" class="form-select mb-3" v-model="selectedWard" @change="updateWardId">
                <option value="" disabled selected>Chọn phường xã</option>
                <option v-for="(ward, index) in wards" :value="ward" :key="index">{{ ward.name }}</option>
              </select>
            </div>
            <div class="form-floating form-group mb-3">
              <input v-model="data.passwordConfirm" type="password" class="form-control form-control-lg" :class="{ 'is-invalid': errors.passwordConfirm }" placeholder="password confirm" @input="onChange('passwordConfirm')">
              <label >Nhập lại mật khẩu</label>
              <p v-if="errors.passwordConfirm" class="text-danger label">{{ errors.passwordConfirm }}</p>
            </div>
          </div>
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
import axios from "axios";

const apiClient = axios.create({
  baseURL: 'https://vnprovinces.pythonanywhere.com/api/',
  withCredentials: false,
  headers: {
    Accept: 'application/json',
    'Content-Type': 'application/json'
  }
});
export default {
  name: 'RegisterView',
  mounted() {
    document.title = 'Đăng ký';
  },
  data(){
    return{
      cities: [],
      districts: [],
      wards:[],
      selectedCity: '', selectedDistrict: '', selectedWard: '',
    }
  },
  setup() {
    const data = reactive({
      firstName:'', lastName:'',
      email:'', password:'', passwordConfirm: '',
      province:'',provinceId:'',districtId:'',district:'',ward:'',wardId:''
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
  },
  methods:{
    updateCityId(event) {
      const selectedOption = event.target.options[event.target.selectedIndex];
      this.selectedCity.code = selectedOption.getAttribute('data-id');
      this.getDistricts();
    },
    updateDistrictId(event) {
      const selectedOption = event.target.options[event.target.selectedIndex];
      this.selectedDistrict.code = selectedOption.getAttribute('data-id');
      this.getWards();
    },
    updateWardId(event){
      const selectedOption = event.target.options[event.target.selectedIndex];
      this.selectedWard.code = selectedOption.getAttribute('data-id');
    },
    getProvinces() {
      apiClient.get('provinces/?basic=true&limit=100').then(response => {
        this.cities = response.data.results;
      });
    },
    getDistricts() {
      if (this.selectedCity.id) {
        apiClient.get(`provinces/${this.selectedCity.id}`).then(response => {
          this.districts = response.data.districts;
        });
      }
    },
    getWards() {
      if (this.selectedDistrict.id) {
        apiClient.get(`/districts/${this.selectedDistrict.id}`).then(response => {
          this.wards = response.data.wards;
        });
      }
    },
    emitLocation() {
      this.data.district = this.selectedDistrict.name;
      this.data.districtId=this.selectedDistrict.id;
      this.data.province = this.selectedCity.name;
      this.data.provinceId = this.selectedCity.id;
      this.data.ward = this.selectedWard.name;
      this.data.wardId = this.selectedWard.id;
    },
  },
  watch: {
    selectedWard() {
      this.emitLocation();
    }
  },
  created() {
    this.getProvinces();
  }
}
</script>

<style scoped>
.is-invalid {
  border-color: red;
}
</style>
