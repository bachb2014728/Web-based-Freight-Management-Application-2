<template>
  <div v-if="resultMessage" :class="resultMessage.type">
    <div class="alert alert-success" role="alert">{{ getResultMessageText() }}</div>
  </div>
  <div class="row" v-if="merchandise">
    <div class="col-sm">
      <div class="card" >
        <div class="card-body">
          <p  class="label text-center">Thông tin hàng hóa</p>
          <form class="g-3" @submit.prevent="handleUpload" enctype="multipart/form-data">
            <div class="mb-3">
              <label for="name" class="form-label">Tên hàng hóa :
                <em>{{merchandise.name}}</em>
              </label>
              <input type="text" class="form-control"
                     :class="{ 'is-invalid': errors.name, 'is-valid': merchandiseUpdate.name && !errors.name }" id="name" name="name" v-model="merchandiseUpdate.name">
              <div class="text-danger">{{ errors.name }}</div>
            </div>
            <div class="row mb-3">
              <div class="col-sm">
                <label for="weight" class="form-label">Trọng lượng :
                  <em>{{merchandise.weight}}</em>
                </label>
                <input type="number" class="form-control"
                       :class="{ 'is-invalid': errors.weight, 'is-valid': merchandiseUpdate.weight && !errors.weight }" id="weight" name="weight" v-model="merchandiseUpdate.weight">
                <div class="text-danger">{{ errors.weight }}</div>
              </div>
              <div class="col-sm">
                <label for="price" class="form-label">Giá trị :
                  <em>{{merchandise.price}}</em>
                </label>
                <input type="number" class="form-control"
                       :class="{ 'is-invalid': errors.price, 'is-valid': merchandiseUpdate.price && !errors.price }"id="price" name="price" v-model="merchandiseUpdate.price">
                <div class="text-danger">{{ errors.price }}</div>
              </div>
            </div>
            <div class="row mb-4">
              <div class="col-sm">
                <label for="image" class="form-label">Hình ảnh</label>
                <input type="file" id="image" name="photos"  class="form-control" @change="onFileChange"  multiple>
              </div>
            </div>
            <div class="row mb-4">
              <div class="image-container">
                <div v-for="(image, index) in merchandise.images" :key="index">
                  <img :src="'data:image/jpeg;base64,' + image" alt="" style="height: 5rem" class="g-3">
                </div>
              </div>
            </div>
            <div class="text-center">
              <button type="submit" class="btn btn-primary">Cập nhật hàng hóa</button>
            </div>
          </form>
        </div>
      </div>
    </div>
    <div class="col-sm">
      <div class="card">
        <div class="card-body">
          <p  class="label text-center">Thông tin người gửi </p>
          <LocationUpdateComponent :item="merchandise.sender" :type="sender" :id="merchandise.id"/>
        </div>
      </div>
    </div>
    <div class="col-sm">
      <div class="card">
        <div class="card-body">
          <p  class="label text-center">Thông tin người nhận </p>
          <LocationUpdateComponent :item="merchandise.receiver" :type="receiver" :id="merchandise.id"/>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {onMounted, ref} from "vue";
import MerchandiseService from "@/services/merchandise.service.js";
import {useRoute} from "vue-router";
import InfoLocationComponent from "@/components/InfoLocationComponent.vue";
import axios from "axios";
import LocationUpdateComponent from "@/components/LocationUpdateComponent.vue";

export default {
  name: "merchandise-edit",
  mounted() {
    document.title='Cập nhật hàng hóa';
  },
  components: {
    LocationUpdateComponent,
  },
  setup(){
    const merchandise = ref();
    let merchandiseUpdate = ref({});
    const selectedFiles = ref([]);
    const route = useRoute();
    const sender = ref('sender')
    const receiver = ref('receiver')
    onMounted(async () => {
      const idProduct = route.params.id;
      const response = await MerchandiseService.getOneMerchandise(idProduct);
      response.data.id = idProduct;
      console.log(response)
      merchandise.value = response.data;
      merchandiseUpdate.value = {...merchandise.value};
    })
    return{
      receiver, sender, merchandise, selectedFiles, merchandiseUpdate,
    }
  },
  data() {
    return {
      selectedFile: '',
      resultMessage: '',
      images: [],
      uploadImage: [],
      updateMerchandise :{name: '', weight: '', images: '', price: ''},
      errors:{},
    }
  },
  methods:{
    onFileChange(event) {
      const files = event.target.files;
      if (files) {
        this.selectedFiles = Array.from(files);
      }
    },

    async handleUpload(){
      this.errors = {};
      if (!this.merchandiseUpdate.name) {
        this.errors.name = 'Tên hàng hóa không được để trống';
      }
      if (!this.merchandiseUpdate.weight) {
        this.errors.weight = 'Trọng lượng không được để trống';
      }
      if (!this.merchandiseUpdate.price) {
        this.errors.price = 'Giá trị không được để trống';
      }
      if (Object.keys(this.errors).length > 0) {
        return;
      }
      const token = localStorage.getItem('token');
      if(this.selectedFiles.length > 0){
        for (const file of this.selectedFiles) {

          const index = this.selectedFiles.indexOf(file);
          let formData = new FormData();
          formData.append("image", file);
          let response = await axios.post('http://localhost:8000/api/v1/uploadToMongoDB', formData, {
            headers: {
              'Authorization': `Bearer ${token}`,
            }
          });
          console.log(response.status)
          if (response.status === 200) {
            console.log(response.data)
            this.uploadImage[index] = response.data.id;
          } else {
            this.resultMessage = { type: 'error', message: response.data.message };
            this.clearMessageAfterDelay();
          }
        }
        this.merchandiseUpdate.images=(this.uploadImage);
      }else {
        this.merchandiseUpdate.images=[];
      }
      await this.updateItem()

    },
    async updateItem() {
      const response = await MerchandiseService.updateOneMerchandise(this.merchandiseUpdate.id,this.merchandiseUpdate)
      if (response.status === 200) {
        this.resultMessage = { type: 'success', message: 'Cập nhật hàng hóa thành công' };
        this.getResultMessageText();
      } else {
        this.resultMessage = { type: 'error', message: 'Cập nhật thất bại' };
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
  },

}
</script>
<style scoped>
.card{
  height: 30rem;
}
.image-container {
  display: flex;
  justify-content: start;
  gap: 10px;
}
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