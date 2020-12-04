import vue from 'vue'

const userApi = {
  Login: '/core/pub/login',
  Logout: '/core/pub/logout',
  captchaUri: 'http://localhost:9001/core/pub/captcha.jpg',
  ForgePassword: '/auth/forge-password',
  Register: '/auth/register',
  twoStepCode: '/auth/2step-code',
  SendSms: '/account/sms',
  SendSmsErr: '/account/sms_err',
  // get my info
  UserInfo: '/core/pri/user/info',
  UserMenu: '/user/nav',
}

/**
 * login func
 * parameter: {
 *     username: '',
 *     password: '',
 *     remember_me: true,
 *     captcha: '12345'
 * }
 * @param parameter
 * @returns {*}
 */
export function login(parameter) {
  return vue.prototype.$http({
    url: userApi.Login,
    method: 'post',
    data: parameter,
  })
}

export function getCaptchaUri() {
  return userApi.captchaUri
}

export function getSmsCaptcha(parameter) {
  return vue.prototype.$http({
    url: userApi.SendSms,
    method: 'post',
    data: parameter,
  })
}

export function getInfo() {
  return vue.prototype.$http({
    url: userApi.UserInfo,
    method: 'get',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8',
    },
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
