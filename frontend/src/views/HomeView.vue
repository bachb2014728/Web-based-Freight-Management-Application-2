<template>
  <div class="row">
    <div class="col-sm">
      <div class="input-group mb-3">
        <input type="search"
               v-model="inputData"
               @input="fetchData"
               class="form-control"
               aria-describedby="button-addon2"
               placeholder="Nhập mã hóa đơn" aria-label="Nhập mã hóa đơn" spellcheck="false" data-ms-editor="true"
        >
        <button class="btn btn-outline-secondary" type="button" id="button-addon2" @click="submitData">Tìm kiếm</button>
      </div>
    </div>
    <div class="col-sm">
      <div v-if="cardData" >
        <h4 class="text-center">Hóa đơn</h4>
        <ul>
          <li>Mã hàng hóa : {{cardData.id}}</li>
          <li>Tên hàng hóa : {{cardData.name}}</li>
          <li>Giá trị : {{cardData.price}}</li>
          <li>Thông tin người nhận: {{cardData.receiver}}</li>
          <li>Ngày tạo : {{cardData.createdAt}}</li>
          <li>Ngày cập nhật  : {{cardData.updatedOn}}</li>
          <li>Trạng thái :
            <span v-if="cardData.status === 'ARCHIVE'" class="badge text-bg-info">Đang lưu trữ</span>
            <span v-if="cardData.status === 'PROCESSING'" class="badge text-bg-secondary">Đang yêu cầu</span>
            <span v-if="cardData.status === 'PENDING'" class="badge text-bg-primary">Chờ xử lý</span>
            <span v-if="cardData.status === 'IN_PROGRESS' " class="badge text-bg-warning">Đang vận chuyển</span>
            <span v-if="cardData.status === 'SUCCESS' " class="badge text-bg-success">Giao hàng thành công</span>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>


<script>

import MerchandiseService from "@/services/merchandise.service.js";
import moment from "moment";

export default {
  name: "HomeView",
  mounted(){
    document.title = 'Trang chủ'
  },
  data() {
    return {
      inputData: '',
      cardData: null,
    };
  },
  methods: {
    async fetchData() {
      if (!this.inputData) {
        this.cardData = null;
        return;
      }
      try {
        const response = await MerchandiseService.getOneMerchandise(this.inputData);
        this.cardData = {
          id: response.data.id,
          name:response.data.name,
          price: response.data.price+'đ',
          receiver : response.data.receiver.name +', '+response.data.receiver.phone,
          createdAt:  moment(response.data.createdAt).format('DD-MM-YYYY'),
          updatedOn: moment(response.data.updatedOn).format('DD-MM-YYYY'),
          status: response.data.status
        };
      } catch (error) {
        console.error(error);
      }
    },
    submitData() {
    },
  },
}
</script>
