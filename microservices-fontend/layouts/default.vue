<template>
  <div id="app">
    <pro-layout
      :menus="menus"
      :collapsed="collapsed"
      :mediaQuery="query"
      :isMobile="isMobile"
      :handleMediaQuery="handleMediaQuery"
      :handleCollapse="handleCollapse"
      :i18nRender="i18nRender"
      v-bind="settings"
    >
      <!-- 1.0.0+ 版本 pro-layout 提供 API，
          我们推荐使用这种方式进行 LOGO 和 title 自定义
    -->

      <template v-slot:menuHeaderRender>
        <div>
          <img src="@/assets/logo.svg" />
          <h1>{{ title }}</h1>
        </div>
      </template>

      <setting-drawer :settings="settings" @change="handleSettingChange" />
      <template v-slot:rightContentRender>
        <MenuTabs style="width: 100%"> </MenuTabs>
      </template>

      <template v-slot:headerContentRender>
        <right-content
          :top-menu="settings.layout === 'topmenu'"
          :is-mobile="isMobile"
          :theme="settings.theme"
        />
      </template>
      <template v-slot:footerRender>
        <global-footer />
      </template>

      <div style="padding-top: 35px">
        <Nuxt :keep-alive="true" max="10" />
      </div>
    </pro-layout>
  </div>
</template>

<script lang="ts">
import { Vue, Component } from 'nuxt-property-decorator'
import { SettingDrawer, updateTheme } from '@/pro-layout'
import { i18nRender } from '@/locales'
import { mapState } from 'vuex'
import {
  CONTENT_WIDTH_TYPE,
  SIDEBAR_TYPE,
  TOGGLE_MOBILE_TYPE,
} from '@/store/mutation-types'

import defaultSettings from '@/plugins/config/defaultSettings'
import { findNavTree } from '@/api/menu'

@Component({
  components: {
    SettingDrawer: SettingDrawer,
  },
})
export default class defaultLayout extends Vue {
  // preview.pro.antdv.com only use.
  isProPreviewSite =
    process.env.VUE_APP_PREVIEW === 'true' &&
    process.env.NODE_ENV !== 'development'
  // end

  // base
  menus = []
  // 侧栏收起状态
  collapsed = false
  title = defaultSettings.title
  settings: any = {
    // 布局类型
    layout: defaultSettings.layout, // 'sidemenu', 'topmenu'
    // CONTENT_WIDTH_TYPE
    contentWidth:
      defaultSettings.layout === 'sidemenu'
        ? CONTENT_WIDTH_TYPE.Fluid
        : defaultSettings.contentWidth,
    // 主题 'dark' | 'light'
    theme: defaultSettings.navTheme,
    // 主色调
    primaryColor: defaultSettings.primaryColor,
    fixedHeader: defaultSettings.fixedHeader,
    fixSiderbar: defaultSettings.fixSiderbar,
    colorWeak: defaultSettings.colorWeak,

    hideHintAlert: false,
    hideCopyButton: false,
  }
  // 媒体查询
  query = {}

  // 是否手机模式
  isMobile = false

  async created() {
    const res = await findNavTree()

    this.menus = res.datas

    // 处理侧栏收起状态
    this.$watch('collapsed', () => {
      this.$store.commit('modules/app/SIDEBAR_TYPE', this.collapsed)
    })
    this.$watch('isMobile', () => {
      this.$store.commit('modules/app/TOGGLE_MOBILE_TYPE', this.isMobile)
    })
  }
  mounted() {
    const userAgent = navigator.userAgent
    if (userAgent.indexOf('Edge') > -1) {
      this.$nextTick(() => {
        this.collapsed = !this.collapsed
        setTimeout(() => {
          this.collapsed = !this.collapsed
        }, 16)
      })
    }

    // first update color
    // TIPS: THEME COLOR HANDLER!! PLEASE CHECK THAT!!
    if (
      process.env.NODE_ENV !== 'production' ||
      process.env.VUE_APP_PREVIEW === 'true'
    ) {
      updateTheme(this.settings.primaryColor)
    }
  }

  i18nRender = i18nRender
  handleMediaQuery(val: any) {
    this.query = val
    if (this.isMobile && !val['screen-xs']) {
      this.isMobile = false
      return
    }
    if (!this.isMobile && val['screen-xs']) {
      this.isMobile = true
      this.collapsed = false
      this.settings.contentWidth = CONTENT_WIDTH_TYPE.Fluid
      // this.settings.fixSiderbar = false
    }
  }
  handleCollapse(val: any) {
    this.collapsed = val
  }
  handleSettingChange({ type, value }: any) {
    console.log('type', type, value)
    type && (this.settings[type] = value)
    switch (type) {
      case 'contentWidth':
        this.settings[type] = value
        break
      case 'layout':
        if (value === 'sidemenu') {
          this.settings.contentWidth = CONTENT_WIDTH_TYPE.Fluid
        } else {
          this.settings.fixSiderbar = false
          this.settings.contentWidth = CONTENT_WIDTH_TYPE.Fixed
        }
        break
    }
  }
}
</script>

<style lang="less"></style>
