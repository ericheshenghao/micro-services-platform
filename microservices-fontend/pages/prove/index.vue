<template>
  <div>
    <div>
      是否授权{{ this.param.client_id }} 访问您账号的{{ this.param.scope }}权限
    </div>
    <template>
      <a-radio-group name="radioGroup" :default-value="1">
        <a-radio :value="1"> 允许 </a-radio>
        <a-radio :value="2"> 拒绝 </a-radio>
      </a-radio-group>
    </template>

    <div>
      <a-button :loading="isLoading" type="primary" @click="allow">
        授权
      </a-button>
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
  q = this.$route.query
  param = {
    client_id: this.q['client_id'],
    redirect_uri: this.q['redirect_uri'],
    scope: this.q['scope'],
    state: this.q['state'],
  }
  isLoading = false

  allow() {
    this.isLoading = true
    this.$store
      .dispatch('modules/user/authorizationId', this.param)
      .then((res) => {
        this.isLoading = false
        window.location =
          this.param.redirect_uri +
          '?code=' +
          res.data +
          '&state=' +
          this.param.state
      })
  }
}
</script>

<style lang="scss" scoped></style>
