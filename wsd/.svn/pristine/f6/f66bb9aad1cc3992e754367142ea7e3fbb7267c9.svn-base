<template>
  <main class="app-main">
    <transition name="fade-transform" mode="out-in">
      <router-view />
    </transition>
  </main>
</template>
<script>
export default {
  name: "AppMain",
};
</script>
<style lang="scss" scoped>
.app-main {
  min-height: calc(100vh - 50px);
  width: 100%;
  position: relative;
  overflow: hidden;
}
.fixed-header + .app-main {
  padding-top: 50px;
}
</style>
<style lang="scss">
// fix css style bug in open el-dialog
.el-popup-parent--hidden {
  .fixed-header {
    padding-right: 15px;
  }
}
</style>
