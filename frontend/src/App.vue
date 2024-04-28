<template>
  <Nav/>
  <main id="main" class="main">
    <router-view/>
  </main>

</template>
<script>
import Nav from "@/components/NavbarComponent.vue";
export default {
  components: {Nav}
}
</script>
