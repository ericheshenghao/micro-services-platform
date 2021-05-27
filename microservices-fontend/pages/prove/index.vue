<template>
  <div>
    <div>是否授权app:xx 访问您的数据</div>
    <template>
      <a-radio-group name="radioGroup" :default-value="1">
        <a-radio :value="1"> 允许 </a-radio>
        <a-radio :value="2"> 拒绝 </a-radio>
      </a-radio-group>
    </template>

    <div>
      <a-button type="primary" @click="allow"> 授权 </a-button>
    </div>
  </div>
</template>

<script>
import { Vue, Component } from 'nuxt-property-decorator'
@Component({})
export default class Prove extends Vue {
  layout(context) {
    return 'auth'
  }

  allow() {
    let param = {}

    const q = this.$route.query
    param.client_id = q['client_id']
    param.redirect_uri = q['redirect_uri']
    param.scope = q['scope']
    param.state = q['state']

    this.$store
      .dispatch('modules/user/authorizationId', param)

      .then((res) => {
        console.log(res)
        window.location =
          param.redirect_uri + '?code=' + res.data + '&state=' + param.state
      })
  }
}
</script>

<style lang="scss" scoped></style>
