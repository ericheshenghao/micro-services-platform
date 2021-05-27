<template>
  <div class="d-flex auth"><Nuxt /></div>
</template>

<script>
export default {
  props: ['auth'],
}
</script>
<style lang="scss" scoped>
.auth {
  justify-content: center;
  align-items: center;
  height: 60%;
}
</style>
