<template>
  <div :class="wrpCls">
    <avatar-dropdown
      :menu="showMenu"
      :current-user="currentUser"
      :class="prefixCls"
    />
    <select-lang :class="prefixCls" />
  </div>
</template>

<script>
import SelectLang from '@/components/SelectLang'
import { Vue, Component, Prop } from 'nuxt-property-decorator'

@Component({
  components: {
    'select-lang': SelectLang,
  },
})
export default class RightContent extends Vue {
  @Prop({
    type: String,
    default: 'ant-pro-global-header-index-action',
  })
  prefixCls

  @Prop({
    type: Boolean,
    default: () => false,
  })
  isMobile

  @Prop({
    type: Boolean,
    required: true,
  })
  topMenu

  @Prop({
    type: String,
    required: true,
  })
  theme

  showMenu = true
  currentUser = {}

  get wrpCls() {
    return {
      'ant-pro-global-header-index-right': true,
      [`ant-pro-global-header-index-${
        this.isMobile || !this.topMenu ? 'light' : this.theme
      }`]: true,
    }
  }

  mounted() {
    setTimeout(() => {
      this.currentUser = {
        name: 'Serati Ma',
      }
    }, 1500)
  }
}
</script>
