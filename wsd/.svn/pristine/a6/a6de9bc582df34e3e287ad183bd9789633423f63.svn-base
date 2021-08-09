<template>
  <div class="app-wrapper">
    <template>
      <main-sidebar class="sidebar-container" />
      <div class="main-container">
        <div class="fixed-header">
          <main-navbar />
        </div>
        <main-content></main-content>
      </div>
    </template>
  </div>
</template>
<script>
import MainNavbar from "./main-navbar";
import MainSidebar from "./main-sidebar";
import MainContent from "./main-content";
export default {
  name: "Main",
  components: {
    MainNavbar,
    MainSidebar,
    MainContent,
  },
  data() {
    return {}
  },
  created() { },
  mounted() { },
  methods: {}
};
</script>
<style lang="scss" scoped>
@import "~@/assets/scss/mixin.scss";
@import "~@/assets/scss/variables.scss";
.app-wrapper {
  @include clearfix;
  position: relative;
  height: 100%;
  width: 100%;
}
.fixed-header {
  position: fixed;
  top: 0;
  right: 0;
  z-index: 9;
  width: calc(100% - #{$sideBarWidth});
  transition: width 0.28s;
}
</style>
