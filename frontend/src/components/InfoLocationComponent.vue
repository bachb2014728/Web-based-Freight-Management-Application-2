<template>
  <div class="mb-3 row">
    <div class="col-sm">
      <label for="nameSender" class="form-label">Tên đầy đủ : <em>{{item.name}}</em></label>
      <input type="text" class="form-control" id="nameSender" name="nameSender" v-model="item.name">
    </div>
    <div class="col-sm">
      <label for="phoneSender" class="form-label">Số điện thoại : <em>{{item.phone}}</em></label>
      <input type="text" class="form-control" id="phoneSender" name="phoneSender" v-model="item.phone" >
    </div>
  </div>
  <div class="mb-3">
    <label for="addressStart" class="form-label">Địa chỉ : <em>{{selectedWard}}, {{selectedDistrict}}, {{selectedCity}}</em> </label>
    <div id="addressStart">
      <select id="provincesStart" name="provinceSender" class="form-select mb-3" v-model="selectedCity" @change="updateCityId">
        <option value="" disabled selected>Chọn tỉnh thành</option>
        <option v-for="(city, index) in cities" :value="city.name" :key="index" :data-id="city.id">{{ city.name }}</option>
      </select>
      <select id="districtsStart" name="districtSender" class="form-select mb-3" v-model="selectedDistrict" @change="updateDistrictId">
        <option value="" disabled selected>Chọn quận huyện</option>
        <option v-for="(district, index) in districts" :value="district.name" :key="index" :data-id="district.id">{{ district.name }}</option>
      </select>
      <select id="wardsStart" name="wardSender" class="form-select mb-3" v-model="selectedWard" @change="updateWardId">
        <option value="" disabled selected>Chọn phường xã</option>
        <option v-for="(ward, index) in wards" :value="ward.name" :key="index" :data-id="ward.id">{{ ward.name }}</option>
      </select>
    </div>
  </div>
</template>
<script>
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
  name: "InfoLocationComponent",
  props:['item'],
  data(){
    return{
      cities: [], districts: [], wards:[],
      selectedCity: this.item.address.province.name, selectedDistrict: this.item.address.district.name, selectedWard: this.item.address.ward.name,
      selectedCityId:this.item.address.province.code, selectedDistrictId: this.item.address.district.code, selectedWardId: this.item.address.ward.code,
      name: this.item.name, phone: this.item.phone,
      updateItem:{
        province: '', district: '',ward: ''
      }
    }
  },

  methods: {
    updateCityId(event) {
      const selectedOption = event.target.options[event.target.selectedIndex];
      this.selectedCityId = selectedOption.getAttribute('data-id');
      this.updateItem.province= {name: this.selectedCity , code: this.selectedCityId}
      this.getDistricts();
    },
    updateDistrictId(event) {
      const selectedOption = event.target.options[event.target.selectedIndex];
      this.selectedDistrictId = selectedOption.getAttribute('data-id');
      this.updateItem.district = {name: this.selectedDistrict, code: this.selectedDistrictId}
      this.getWards();
    },
    updateWardId(event){
      const selectedOption = event.target.options[event.target.selectedIndex];
      this.selectedWardId = selectedOption.getAttribute('data-id');
      this.updateItem.ward = {name: this.selectedWard, code: this.selectedWardId}
    },
    getProvinces() {
      apiClient.get('provinces/?basic=true&limit=100').then(response => {
        this.cities = response.data.results;
      });
    },
    getDistricts() {
      return apiClient.get(`provinces/${this.selectedCityId}`).then(response => {
        this.districts = response.data.districts;
        const currentDistrict = this.districts.find(district => district.id === this.selectedDistrictId);
        if (currentDistrict) {
          this.selectedDistrict = currentDistrict.name;
        }
      });
    },
    getWards() {
      return apiClient.get(`/districts/${this.selectedDistrictId}`).then(response => {
        this.wards = response.data.wards;
        const currentWard = this.wards.find(ward => ward.id === this.selectedWardId);
        if (currentWard) {
          this.selectedWard = currentWard.name;
        }
      });
    },
    emitLocation() {
      this.$emit('location-updated', {
        name: this.name,
        phone: this.phone,
        city: this.updateItem.province,
        district: this.updateItem.district,
        ward: this.updateItem.ward
      });
    }
  },
  watch: {
    selectedWard() {
      this.emitLocation();
    },
  },
  mounted() {
    this.getProvinces();
    this.getDistricts();
    this.getWards()
  }
}
</script>
