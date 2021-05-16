import vue from 'vue'
import { getUid } from '@/utils/util'
import qs from 'qs'

const userApi = {
  Login: 'uaa/oauth/token',
  Logout: 'uaa/oauth/removeToken',
  captchaUri: 'uaa/oauth/captcha/',
  ForgePassword: '/auth/forge-password',
  Register: '/auth/register',
  twoStepCode: '/auth/2step-code',
  SendSms: '/account/sms',
  SendSmsErr: '/account/sms_err',
  // get my info
  userInfo: '/core/pri/user/info',
  UserMenu: '/user/nav',
  uid: '',
}

/**
 * 使用password_code方式获取token
 * @param parameter
 * @returns {*}
 */
export function login(parameter) {
  return vue.prototype.$http({
    url: userApi.Login,
    method: 'post',
    data: qs.stringify({
      ...parameter,
      deviceId: userApi.uid,
      client_id: 'webApp',
      client_secret: 123456,
      grant_type: 'password_code',
    }),
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded',
    },
  })
}

export function getCaptcha() {
  userApi.uid = getUid()
  return vue.prototype.$http({
    url: userApi.captchaUri + userApi.uid,
    method: 'get',
    responseType: 'blob',
  })
}

export function getSmsCaptcha(parameter) {
  return vue.prototype.$http({
    url: userApi.SendSms,
    method: 'post',
    data: parameter,
  })
}

export function getUserInfo() {
  return vue.prototype.$http({
    url: userApi.userInfo,
    method: 'get',
  })
}

export function getCurrentUserNav() {
  return vue.prototype.$http({
    url: userApi.UserMenu,
    method: 'get',
  })
}

export function logout() {
  return vue.prototype.$http({
    url: userApi.Logout,
    method: 'get',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8',
    },
  })
}

/**
 * get user 2step code open?
 * @param parameter {*}
 */
export function get2step(parameter) {
  return vue.prototype.$http({
    url: userApi.twoStepCode,
    method: 'post',
    data: parameter,
  })
}