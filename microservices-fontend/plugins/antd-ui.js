import Vue from 'vue'
import Antd from 'ant-design-vue/lib'
import ProLayout, { PageHeaderWrapper } from '@/pro-layout'

import themePluginConfig from '../config/themePluginConfig'
import Viser from 'viser-vue'
Vue.use(Viser)
window.umi_plugin_ant_themeVar = themePluginConfig.theme
Vue.component('pro-layout', ProLayout)
Vue.component('page-container', PageHeaderWrapper)
// Vue.component('page-header-wrapper', PageHeaderWrapper)

Vue.use(Antd)
