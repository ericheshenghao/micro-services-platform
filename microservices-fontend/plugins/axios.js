import Vue from 'vue'
import configs from './config/defaultSettings.js'

export default ({ app, store, route, redirect }) => {
  const axios = app.$axios

  // 基本配置
  axios.defaults.timeout = 15000
  axios.defaults.headers.post['Content-Type'] = 'application/json'
  axios.defaults.withCredentials = true
  axios.defaults.baseURL = configs.API_URL

  // 请求回调
  axios.onRequest((config) => {
    config.baseURL = configs.API_URL
    //设置token
    if (store.state.modules.user && store.state.modules.user.token) {
      config.headers.Authorization = 'Bearer ' + store.state.modules.user.token
      config.headers['x-tenant-header'] = store.state.modules.user.tenant_id
    }
  })

  // code返回回调
  axios.onResponse((res) => {
    res = res.data
    switch (res.code) {
      case 0:
        notification(res.msg)
        throw new Error(res.msg)
      case 500:
        notification(res.msg)
        throw new Error(res.msg)
      case 503:
        notification('服务暂时不可用')
        throw new Error(res.msg)
      case 401:
        notification('token失效，请重新登录')
        store.dispatch('modules/user/Logout').then(() => {
          redirect('/login')
        })

        throw new Error(res.msg)
      default:
        return res
    }
  })

  // 内部错误回调
  axios.onError((error) => {
    if (
      error.code == 'ECONNABORTED' &&
      error.message.indexOf('timeout') != -1
    ) {
      notification('请求超时')
    }
  })
  Vue.prototype.$http = axios
}

function notification(msg) {
  Vue.prototype.$notification['info']({
    key: msg,
    message: '提示',
    description: msg,
    duration: 4,
  })
}
