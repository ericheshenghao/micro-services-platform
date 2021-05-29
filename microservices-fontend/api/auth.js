import vue from 'vue'
import { getUid } from '@/utils/util'
import qs from 'qs'
import config from '@/plugins/config/defaultSettings'

const authApi = {
  loginUri: 'uaa/oauth/token',
  authorizationUri: 'uaa/validate/authorize',
  logoutUri: 'uaa/validate/removeToken',
  captchaUri: 'uaa/validate/captcha/',
  // get my info
  userInfo: '/core/pri/user/info',
  // token
  checkTokenUri: 'uaa/oauth/check_token',
}

/**
 * @param parameter
 * @returns {*}
 */
export function login(parameter) {
  return vue.prototype.$http({
    url: authApi.loginUri,
    method: 'post',
    data: qs.stringify({
      ...parameter,
      deviceId: authApi.uid,
      client_id: config.client_id,
      client_secret: config.client_secret,
      grant_type: config.grant_type,
    }),
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded',
    },
  })
}

export function logout(token) {
  return vue.prototype.$http({
    url: authApi.logoutUri,
    params: {
      token: token ? token : '',
    },
    method: 'get',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8',
    },
  })
}

export function authorizationId(parameter) {
  return vue.prototype.$http({
    url: authApi.authorizationUri,
    method: 'get',
    params: {
      client_id: parameter.client_id,
      redirect_uri: parameter.redirect_uri,
      response_type: 'code',
      scope: parameter.scope,
      state: parameter.state,
    },
  })
}

export function getCaptcha() {
  authApi.uid = getUid()
  return vue.prototype.$http({
    url: authApi.captchaUri + authApi.uid,
    method: 'get',
    responseType: 'blob',
  })
}

export function getUserInfo() {
  return vue.prototype.$http({
    url: authApi.userInfo,
    method: 'get',
  })
}

export function checkToken(parameter) {
  return vue.prototype.$http({
    url: authApi.checkTokenUri,
    method: 'post',
    params: { token: parameter },
  })
}
