<template>
  <div v-if="resultMessage" :class="resultMessage.type">
    <div class="alert alert-success" role="alert">{{ getResultMessageText() }}</div>
  </div>
  <div class="card">
    <div class="card-body">
      <h5 class="card-title">Thông tin hàng hóa</h5>
      <form class="row g-3" @submit.prevent="handleUpload" enctype="multipart/form-data">
        <div class="row mb-3">
          <div class="col-sm">
            <label for="name" class="form-label">Tên hàng hóa</label>
            <input type="text" class="form-control"
                   :class="{ 'is-invalid': errors.name, 'is-valid': newItem.name && !errors.name }" id="name" name="name" v-model="newItem.name">
            <div class="text-danger">{{ errors.name }}</div>
          </div>
          <div class="col-sm">
            <label for="image" class="form-label">Hình ảnh</label>
            <input type="file" id="image" name="photos"  class="form-control" @change="onFileChange"  multiple>
          </div>
          <div class="col-sm">
            <label for="store" class="form-label">Chọn cửa hàng</label>
            <select id="store" name="store" class="form-select mb-3" v-model="newItem.store">
              <option value="" disabled selected>Chọn cửa hàng</option>
              <option v-for="(store, index) in stores" :value="store.id" :key="index">{{ store.name }}</option>
            </select>
          </div>
        </div>
        <div class="row mb-3">
          <div class="col-sm">
            <label for="weight" class="form-label">Trọng lượng</label>
            <input type="number" class="form-control"
                   :class="{ 'is-invalid': errors.weight, 'is-valid': newItem.weight && !errors.weight }" id="weight" name="weight" v-model="newItem.weight">
            <div class="text-danger">{{ errors.weight }}</div>
          </div>
          <div class="col-sm">
            <label for="price" class="form-label">Giá trị</label>
            <input type="number" class="form-control"
                   :class="{ 'is-invalid': errors.price, 'is-valid': newItem.price && !errors.price }" id="price" name="price" v-model="newItem.price">
            <div class="text-danger">{{ errors.price }}</div>
          </div>
          <div class="col-sm">
            <label for="status" class="form-label">Trạng thái</label>
            <select id="status" name="status" class="form-select mb-3" v-model="newItem.status" >
              <option value="" disabled selected>Chọn trạng thái</option>
              <option value="ARCHIVE" id="awaiting">Lưu trữ</option>
              <option value="PROCESSING" id="progress">Yêu cầu</option>
            </select>
          </div>
        </div>
        <div class="row">
          <div class="col-sm">
            <p  class="label text-center">Thông tin người gửi</p>
            <LocationComponent @location-changed="updateLocationStart" :item="sender" />
          </div>
          <div class="col-sm">
            <p  class="label text-center">Thông tin người nhận</p>
            <LocationComponent @location-changed="updateLocationEnd" :item="receiver"/>
          </div>
        </div>
        <div class="text-center">
          <button type="submit" class="btn btn-primary">Thêm hàng hóa</button>
        </div>
      </form>

    </div>
  </div>
</template>
<script>
import axios from "axios";
import LocationComponent from "@/components/LocationComponent.vue";
import {onMounted, ref} from "vue";
import CustomerService from "@/services/customer.service.js";

export default {
  name: "merchandise-create",
  mounted(){
    document.title = 'Thêm hàng hóa';
  },
  setup(){
    const stores = ref([]);
    onMounted(async ()=>{
      const response = await axios.get('http://localhost:8000/api/v1/stores');
      stores.value = response.data
    });
    return{
      stores
    }
  },
  components:{
    LocationComponent
  },
  data() {
    return {
      selectedFile: '', resultMessage: '', images: [], uploadImage: [],
      sender:'Sender', receiver:'Receiver',
      newItem: {
        name: '', weight: '', images: '', price: '',status:'', store:'',
        nameSender: '', phoneSender:'', provinceSender:'', districtSender:'', wardSender:'',
        nameReceiver:'',phoneReceiver:'', provinceReceiver:'', districtReceiver:'', wardReceiver:''
      },
      errors:{},
    }
  },
  methods: {
    onFileChange(event) {
      const files = event.target.files;
      if (files) {
        this.selectedFiles = Array.from(files);
      }
    },
    updateLocationStart(location) {
      this.newItem.nameSender = location.name;
      this.newItem.phoneSender = location.phone;
      this.newItem.provinceSender = { code: location.city.id, name: location.city.name };
      this.newItem.districtSender = { code: location.district.id, name: location.district.name };
      this.newItem.wardSender = { code: location.ward.id, name: location.ward.name };
    },
    updateLocationEnd(location) {
      this.newItem.nameReceiver = location.name;
      this.newItem.phoneReceiver = location.phone;
      this.newItem.provinceReceiver = { code: location.city.id, name: location.city.name };
      this.newItem.districtReceiver = { code: location.district.id, name: location.district.name };
      this.newItem.wardReceiver = { code: location.ward.id, name: location.ward.name };
    },

    async handleUpload() {
      this.errors = {};
      if (!this.newItem.name) {
        this.errors.name = 'Tên hàng hóa không được để trống';
      }
      if (!this.newItem.weight) {
        this.errors.weight = 'Trọng lượng không được để trống';
      }
      if (!this.newItem.price) {
        this.errors.price = 'Giá trị không được để trống';
      }
      if (Object.keys(this.errors).length > 0) {
        return;
      }
      const token = localStorage.getItem('token');
      for (const file of this.selectedFiles) {
        const index = this.selectedFiles.indexOf(file);
        let formData = new FormData();
        formData.append("image", file);
        let response = await axios.post('http://localhost:8000/api/v1/uploadToMongoDB', formData, {
          headers: {
            'Authorization': `Bearer ${token}`,
          }
        });
        if (response.status === 200) {
          console.log(response.data)
          this.uploadImage[index] = response.data.id;
        } else {
          this.resultMessage = { type: 'error', message: response.data.message };
          this.clearMessageAfterDelay();
        }
      }
      this.newItem.images=(this.uploadImage);
      await this.createItem();
      // try {
      //
      // } catch (error) {
      //   console.error('Lỗi không tải được ảnh:', error.message);
      //   this.resultMessage = { type: 'error', message: error.message };
      //   this.clearMessageAfterDelay();
      // }
    },
    async createItem() {
      const token = localStorage.getItem('token');
      console.log(this.newItem);
      const response = await axios.post('http://localhost:8000/api/v1/merchandises', this.newItem, {
        headers: {
          'Authorization': `Bearer ${token}`,
        }
      });

      if (response.status === 200) {
        console.log(response);
        this.resultMessage = { type: 'success', message: 'Thêm hàng hóa thành công' };
        this.getResultMessageText();
      } else {
        this.resultMessage = { type: 'error', message: 'Lỗi' };
      }
      this.clearMessageAfterDelay();
    },
    clearMessageAfterDelay() {
      setTimeout(() => (this.resultMessage = null), 5000);
    },
    getResultMessageText() {
      return this.resultMessage.type === 'success'
          ? 'Thành công: ' + this.resultMessage.message
          : 'Thất bại: ' + this.resultMessage.message;
    },
  }
}
</script>
<style scoped>
.is-invalid {
  border-color: #dc3545;
  padding-right: calc(1.5em + 0.75rem);
  background-image: url("data:image/svg+xml,%3csvg xmlns='http://www.w3.org/2000/svg' width='12' height='12' fill='none' stroke='%23dc3545' viewBox='0 0 12 12'%3e%3ccircle cx='6' cy='6' r='4.5'/%3e%3cpath stroke-linejoin='round' d='M5.8 3.6h.4L6 6.5z'/%3e%3ccircle cx='6' cy='8.2' r='.6' fill='%23dc3545' stroke='none'/%3e%3c/svg%3e");
  background-repeat: no-repeat;
  background-position: right calc(0.375em + 0.1875rem) center;
  background-size: calc(0.75em + 0.375rem) calc(0.75em + 0.375rem);
}

.is-valid {
  border-color: #28a745;
  padding-right: calc(1.5em + 0.75rem);
  background-image: url("data:image/svg+xml,%3csvg xmlns='http://www.w3.org/2000/svg' width='8' height='8' viewBox='0 0 8 8'%3e%3cpath fill='%2328a745' d='M2.3 6.73L.6 4.53c-.4-1.04.46-1.4 1.1-.8l1.1 1.4 3.4-3.8c.6-.63 1.6-.27 1.2.7l-4 4.6c-.43.5-.8.4-1.1.1z'/%3e%3c/svg%3e");
  background-repeat: no-repeat;
  background-position: right calc(0.375em + 0.1875rem) center;
  background-size: calc(0.75em + 0.375rem) calc(0.75em + 0.375rem);
}
</style>