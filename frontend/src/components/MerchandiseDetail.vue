<template>
  <div class="card " :style="{ backgroundImage: 'url(' + 'data:image/jpeg;base64,' + currentImage + ')'
  , backgroundSize: 'cover' }"
  >
    <div style="background-color: rgba(255, 255, 255, 0.9);">
      <h5 class="card-title text-center text-primary">{{item.name}}</h5>
      <p class="text-center">Mã hàng hóa : <span>{{item.id}}</span> <a @click="copyToClipboard(item.id)"><i class="bi bi-copy"></i></a></p>
      <div class="card-body">
        <div class="image-container mb-3">
          <div v-for="(image, index) in item.images" :key="index">
            <img :src="'data:image/jpeg;base64,' + image" alt="" style="height: 5rem" @click="changeBackground(image)">
          </div>
        </div>
        <p class="text-center">
          <span class="badge bg-primary" v-if="item.status === 'PROCESSING' ">{{item.status}}</span>
          <span class="badge bg-secondary" v-if="item.status === 'ARCHIVE' ">{{item.status}}</span>
          <span class="badge bg-success" v-if="item.status === 'PENDING' ">{{item.status}}</span>
        </p>
        <p class="d-flex justify-content-between">
          <span>Trọng lượng : {{item.weight}}</span>
          <span>Giá thu hộ : {{item.price}}</span>
        </p>
        <p class="card-text">
          Thông tin người gửi :
          <ul>
            <li>Họ tên : {{item.nameSender}}</li>
            <li>Số điện thoại : {{item.phoneSender}}</li>
            <li>Địa chỉ : {{item.addressSender}}</li>
          </ul>
        </p>
        <p class="card-text">
          Thông tin người nhận :
          <ul>
            <li>Họ tên : {{ item.nameReceiver }}</li>
            <li>Số điện thoại : {{ item.phoneReceiver }}</li>
            <li>Địa chỉ : {{item.addressReceiver}}</li>
          </ul>
        </p>
        <p class="card-text"></p>
      </div>
      <div class="card-footer text-center">
        <button @click="deleteItem(item)"
            class="btn btn-secondary btn-sm me-3" v-if="item.status === 'ARCHIVE' || item.status === 'PROCESSING' ">
          Xóa
        </button>
<!--        <span class="badge bg-success" v-if="item.status === 'PENDING' ">{{item.status}}</span>-->
      </div>
    </div>

  </div>
</template>
<script>

import MerchandiseEdit from "@/views/merchandises/merchandise-edit.vue";

export default {
  name: "MerchandiseDetail",
  components: {MerchandiseEdit},
  props:['item','index'],
  data() {
    return {
      currentImage: this.item.images[0],
    }
  },
  methods: {
    copyToClipboard(text) {
      navigator.clipboard.writeText(text).then(() => {
        console.log('sao chép thành công');
      }).catch(err => {
        console.error('không thể sao chép : ', err);
      });
    },
    async deleteItem(item) {
      this.$emit('delete-item', item);
    },
    changeBackground(image) {
      this.currentImage = image;
    },
  }
}
</script>
<style scoped>
.image-container {
  display: flex;
  justify-content: start;
  gap: 10px;
}
.card {
  border: 2px solid blue;
}

</style>