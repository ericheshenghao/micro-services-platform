<template>
  <div></div>
</template>
<script lang='ts'>
import { Vue, Component } from 'nuxt-property-decorator'
@Component({
  components: {},
})
export default class index extends Vue {
  asyncData({ store }: any) {
    return {}
  }
  head() {
    return {
      title: '',
    }
  }
}
</script>
<style lang='scss' scoped>
</style>