<template>
  <div class="row">
    <AuthInfo :item="profile" />
    <AuthUpdate :item="profile"/>
  </div>
</template>
<script>
import AuthInfo from "@/components/AuthInfo.vue";
import AuthUpdate from "@/components/AuthUpdate.vue";
import CustomerService from "@/services/customer.service.js";

export default {
  name: "auth-page",
  components: {AuthUpdate, AuthInfo},
  async mounted() {
    document.title='Thông tin cá nhân'
    const response = await CustomerService.get();
    this.profile = response.data
    console.log(response.data)
  },
  data() {
    return {
      profile: ''
    }
  },
  methods: {

  },
}
</script>
