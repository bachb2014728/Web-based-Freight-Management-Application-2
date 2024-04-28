<template>
  <div class="col-sm-8" v-if="updateItem">
    <h5 class="mb-3 text-center">Cập nhật thông tin</h5>
    <form @submit.prevent="updateProfile" class="needs-validation" novalidate="">
      <div class="row mb-3">
        <div class="col-sm">
          <label for="firstName" class="form-label">Họ: <em>{{updateItem.firstName}}</em></label>
          <input type="text" class="form-control" id="firstName"  v-model="updateItem.firstName">
        </div>
        <div class="col-sm">
          <label for="lastName" class="form-label">Tên: <em>{{updateItem.lastName}}</em></label>
          <input type="text" class="form-control" id="lastName"  v-model="updateItem.lastName">
        </div>
        <div class="col-sm">
          <label for="code" class="form-label">Mã định danh: <em>{{updateItem.codeId}}</em></label>
          <input type="text" class="form-control" id="code"  v-model="updateItem.codeId">
        </div>
      </div>
      <div class="row mb-3">
        <div class="col-sm">
          <label for="phone" class="form-label">Số điện thoại: <em>{{updateItem.phone}}</em></label>
          <input type="text" class="form-control" id="phone"  v-model="updateItem.phone">
        </div>
        <div class="col-sm">
          <label for="gender" class="form-label">Giới tính: <em>{{updateItem.gender}}</em></label>
          <select id="gender" class="form-control" v-model="updateItem.gender">
            <option disabled value="">Vui lòng chọn giới tính</option>
            <option value="Nam" id="Nam">Nam</option>
            <option value="Nữ" id="Nữ">Nữ</option>
            <option value="Khác" id="Khác">Khác</option>
          </select>
        </div>
        <div  class="col-sm">
          <label for="date" class="form-label">Ngày sinh: <em>{{ formattedDate }}</em></label>
          <input type="date" class="form-control" id="date"  v-model="updateItem.date">
        </div>
      </div>
      <div class="row mb-3" v-if="updateItem.address">
        <label for="address" class="form-label">Địa chỉ:
          <em>{{updateItem.address.ward.name}}, {{updateItem.address.district.name}}, {{updateItem.address.province.name}}</em>
        </label>
        <div id="address">
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
      </div>
      <div class="text-center">
        <button class=" btn btn-primary btn-sm" type="submit">Cập nhật</button>
      </div>
    </form>
  </div>
</template>
<script>
import axios from "axios";
import CustomerService from "@/services/customer.service.js";
import moment from 'moment';
const apiClient = axios.create({
  baseURL: 'https://vnprovinces.pythonanywhere.com/api/',
  withCredentials: false,
  headers: {
    Accept: 'application/json',
    'Content-Type': 'application/json'
  }
});
export default {
  name: "AuthUpdate",
  props:['item'],
  mounted() {
    this.updateItem = {...this.item};
    this.originalItem = {...this.item};
  },
  computed:{
    formattedDate() {
      if (this.updateItem.date) {
        let date = new Date(this.updateItem.date);
        return date.toLocaleDateString('vi-VN', {day: '2-digit', month: '2-digit', year: 'numeric'});
      }
      return '';
    }
  },
  data(){
    return{
      originalItem: null,
      cities: [], districts: [], wards:[],
      selectedCity: '', selectedDistrict: '', selectedWard: '',
      updateItem: {
        firstName: '',lastName:'', codeId: '', phone:'', gender: '', date: '', address:''
      },
    }
  },
  methods: {
    updateCityId(event) {
      const selectedOption = event.target.options[event.target.selectedIndex];
      this.selectedCity.code = selectedOption.getAttribute('data-id');
      this.updateItem.address.province = {name: this.selectedCity.name, code: this.selectedCity.id}
      this.getDistricts();
    },
    updateDistrictId(event) {
      const selectedOption = event.target.options[event.target.selectedIndex];
      this.selectedDistrict.code = selectedOption.getAttribute('data-id');
      this.updateItem.address.district = {name: this.selectedDistrict.name, code: this.selectedDistrict.id}
      this.getWards();
    },
    updateWardId(event){
      const selectedOption = event.target.options[event.target.selectedIndex];
      this.selectedWard.code = selectedOption.getAttribute('data-id');
      this.updateItem.address.ward = {name: this.selectedWard.name, code: this.selectedWard.id}
    },
    getProvinces() {
      apiClient.get('provinces/?basic=true&limit=100').then(response => {
        this.cities = response.data.results;
        this.citiesEnd = response.data.results;
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
    async updateProfile() {
      const data = {
        firstName: this.updateItem.firstName || this.originalItem.firstName,
        lastName: this.updateItem.lastName || this.originalItem.lastName,
        phone: this.updateItem.phone || this.originalItem.phone,
        gender: this.updateItem.gender || this.originalItem.gender,
        codeId: this.updateItem.codeId || this.originalItem.codeId,
        date: this.updateItem.date || this.originalItem.date,
        provinceCode: this.updateItem.address.province.code || this.originalItem.address.province.code,
        districtCode: this.updateItem.address.district.code || this.originalItem.address.district.code,
        wardCode: this.updateItem.address.ward.code || this.originalItem.address.ward.code,
        province: this.updateItem.address.province.name || this.originalItem.address.province.name,
        district: this.updateItem.address.district.name || this.originalItem.address.district.name,
        ward: this.updateItem.address.ward.name || this.originalItem.address.ward.name
      }
      console.log(data)
      const response = await CustomerService.update(data);
      if (response && response.status === 200){
        alert("Cap nhat thanh cong")
      }
    },
  },
  watch: {
    item: {
      handler(newVal) {
        this.updateItem = {...newVal};
        this.originalItem = {...newVal};
      },
      deep: true
    }
  },
  created() {
    this.getProvinces();
  }
}
</script>