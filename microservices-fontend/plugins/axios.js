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
    }
  })

  // code返回回调
  axios.onResponse((res) => {
    switch (res.data.respCode) {
      case 0:
        Vue.prototype.$notification['error']({
          message: '提示',
          description: res.data.respMsg,
          duration: 4,
        })
        break
      case 401:
        Vue.prototype.$notification['info']({
          message: '提示',
          description: 'token失效，请重新登录',
          duration: 4,
        })

        redirect('/login')
        break
      default:
        return res.data
    }
  })

  // 内部错误回调
  axios.onError((error) => {
    console.log(error)
  })
  Vue.prototype.$http = axios
}
