import Vue from 'vue'
import configs from './config/defaultSettings.js'
export default ({ app, store, route, redirect }) => {
  const axios = app.$axios

  // 基本配置
  axios.defaults.timeout = 5000
  axios.defaults.headers.post['Content-Type'] = 'application/json'
  axios.defaults.withCredentials = true
  axios.defaults.baseURL = configs.API_URL

  // 请求回调
  axios.onRequest((config) => {
    config.baseURL = configs.API_URL
    //设置token
    if (store.state.modules.user && store.state.modules.user.token) {
      config.headers.Authorization = store.state.modules.user.token
    }
  })

  // code返回回调
  axios.onResponse((res) => {
    return res.data
  })

  // 内部错误回调
  axios.onError((error) => {
    if (error.response.status == 401) {
      store.dispatch('modules/user/Logout')
      redirect('/login')
    }
  })
  Vue.prototype.$http = axios
}
