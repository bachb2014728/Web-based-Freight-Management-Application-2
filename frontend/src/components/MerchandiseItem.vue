<template>
  <a href="#" class="list-group-item list-group-item-action" aria-current="true">
    <div class="d-flex w-100 justify-content-between">
      <p class="mb-1">Mã hàng hóa : <span>{{item.id}}</span> <a @click="copyToClipboard(item.id)"><i class="bi bi-copy"></i></a></p>
      <small>
        {{formattedDate}}
        <span class="badge bg-warning" v-if="item.status === 'AWAITING' ">{{item.status}}</span>
        <span class="badge bg-info" v-if="item.status === 'PROGRESS' ">{{item.status}}</span>
      </small>
    </div>
    <span class="d-flex w-100 justify-content-between">
      <h5 class="mb-1">{{item.name}}</h5>
     <router-link :to="`/merchandises/${item.id}/update`" :id="item.id"> <i class="bi bi-pencil-square"></i></router-link>
    </span>
  </a>
</template>
<script>
export default {
  name: "MerchandiseItem",
  props:['item','index'],
  computed:{
    formattedDate() {
      if (this.item.createdAt) {
        let date = new Date(this.item.createdAt);
        return date.toLocaleDateString('vi-VN', {day: '2-digit', month: '2-digit', year: 'numeric'});
      }
      return '';
    }
  },
  methods: {
    copyToClipboard(text) {
      navigator.clipboard.writeText(text).then(() => {
        console.log('sao chép thành công');
      }).catch(err => {
        console.error('không thể sao chép : ', err);
      });
    }
  }

}
</script>

<style scoped>

</style>