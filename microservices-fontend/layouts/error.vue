<template>
  <a-result status="500" title="500" sub-title="Sorry, the server is wrong.">
    <template #extra>
      <a-button type="primary" @click="$router.push('/')"> Back Home </a-button>
    </template>
  </a-result>
</template>

<script>
export default {
  props: ['error'],
}
</script>
<style lang="scss" scoped></style>
