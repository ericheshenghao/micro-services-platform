<template>
  <div class="error-page">
    <div class="img" style></div>
    <div class="content">
      <h1>404</h1>
      <div class="desc">{{$t("error.title")}}</div>
      <div class="actions">
        <router-link :to="{path:'/'}">
          <el-button type="primary">{{$t("error.back")}}</el-button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: ['error'],
}
</script>
<style lang="scss" scoped>
.img {
  background: url(../static/bg/404.svg) no-repeat;
}
</style>