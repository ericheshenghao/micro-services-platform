<template>
  <div></div>
</template>

<script>
import { Vue, Component } from 'nuxt-property-decorator'
@Component({})
export default class Name extends Vue {
  layout(context) {
    return 'auth'
  }

  mounted() {}

  removeToken() {}
}
</script>

<style lang="scss" scoped></style>
