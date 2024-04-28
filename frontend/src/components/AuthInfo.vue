
<template>
  <div class="col-sm-4">
    <h5 class="mb-3 text-center">Thông tin</h5>
    <ul class="list-group mb-3" v-if="item">
      <li class="list-group-item d-flex justify-content-between lh-sm">
        <div>
          <h6 class="my-0">Họ tên</h6>
        </div>
        <span class="text-body-secondary">{{item.firstName}} {{item.lastName}}</span>
      </li>
      <li class="list-group-item d-flex justify-content-between lh-sm">
        <div>
          <h6 class="my-0">Số điện thoại:</h6>
        </div>
        <span class="text-body-secondary">{{item.phone}}</span>
      </li>
      <li class="list-group-item d-flex justify-content-between lh-sm">
        <div>
          <h6 class="my-0">Địa chỉ :</h6>
        </div>
        <span class="text-body-secondary">
          {{item.address.ward.name}}, {{item.address.district.name}}, {{item.address.province.name}}
        </span>
      </li>
      <li class="list-group-item d-flex justify-content-between lh-sm">
        <div>
          <h6 class="my-0">Giới tính :</h6>
        </div>
        <span class="text-body-secondary">{{item.gender}}</span>
      </li>
      <li class="list-group-item d-flex justify-content-between lh-sm">
        <div>
          <h6 class="my-0">Ngày sinh :</h6>
        </div>
        <span class="text-body-secondary">{{formattedDate}}</span>
      </li>
      <li class="list-group-item d-flex justify-content-between lh-sm">
        <div>
          <h6 class="my-0">Username: </h6>
        </div>
        <span class="text-body-secondary">{{item.user.email}}</span>
      </li>
      <li class="list-group-item d-flex justify-content-between lh-sm">
        <div>
          <h6 class="my-0">Mã định danh: </h6>
        </div>
        <span class="text-body-secondary">{{item.codeId}}</span>
      </li>
      <li class="list-group-item d-flex justify-content-between lh-sm">
        <div>
          <h6 class="my-0">Vai trò: </h6>
        </div>
        <span class="text-body-secondary">{{item.user.roles[0].name}}</span>
      </li>
      <li class="list-group-item d-flex justify-content-between lh-sm">
        <div>
          <h6 class="my-0">Ngày tạo tài khoản: </h6>
        </div>
        <span class="text-body-secondary"></span>
      </li>
    </ul>
  </div>
</template>
<script>
export default {
  name: "AuthInfo",
  props:['item'],
  computed:{
    formattedDate() {
      if (this.item.date) {
        let date = new Date(this.item.date);
        return date.toLocaleDateString('vi-VN', {day: '2-digit', month: '2-digit', year: 'numeric'});
      }
      return '';
    }
  },
  methods:{}
}
</script>