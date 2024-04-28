<template>
  <div class="row">
    <div class="card col-8">
      <div class="card-body">
        <h5 class="card-title">Danh sách hàng hóa</h5>
        <div class="list-group" v-for="(item, index) in merchandises" :key="index" @click="selectItem(item)">
          <MerchandiseItem :item="item" :index="index"/>
        </div>
      </div>
    </div>
    <div class="col-4" v-if="selectedItem">
      <MerchandiseDetail :item="selectedItem" :index="selectedIndex" @delete-item="deleteItem"/>
    </div>
  </div>
</template>
<script>
import { onMounted, ref } from 'vue';
import MerchandiseService from "@/services/merchandise.service.js";
import MerchandiseItem from "@/components/MerchandiseItem.vue";
import MerchandiseDetail from "@/components/MerchandiseDetail.vue";

export default {
  name: "merchandise-list",
  mounted() {
    document.title = 'Hàng hóa'
  },
  components:{
    MerchandiseItem,
    MerchandiseDetail
  },
  setup() {
    const merchandises = ref([]);
    const selectedItem = ref(null);
    const selectedIndex = ref(null);
    onMounted( async () => {
      const response = await MerchandiseService.getAllMerchandises();
      merchandises.value = response.data;
    });
    const selectItem = (item, index) => {
      selectedItem.value = item;
      selectedIndex.value = index;
    };
    return {
      merchandises,
      selectedItem,
      selectedIndex,
      selectItem
    }
  },
  methods: {
    async deleteItem(item) {
      let index = this.merchandises.indexOf(item);
      if (index > -1) {
        this.merchandises.splice(index, 1);
      }
      this.selectedItem = null;
      const response = await MerchandiseService.deleteOneMerchandise(item.id);
      if (response.status === 200) {
        this.resultMessage = { type: 'success', message: 'Xóa hàng hóa thành công' };
        this.getResultMessageText();
      } else {
        this.resultMessage = { type: 'error', message: 'Xóa thất bại' };
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

</style>