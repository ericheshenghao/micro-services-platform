/**
 * 全局配置文件
 */

const BASE_URL =
  process.env.NODE_ENV !== 'production'
    ? 'http://localhost:'
    : 'http://139.198.189.114:'
export default {
  API_URL: BASE_URL + '9001/',

  CAPTCHA_SERVICE: BASE_URL + '9001/core/pub/captcha.jpg',

  //配置菜单的属性
  navTheme: 'dark', // theme for nav menu
  primaryColor: '#52C41A', // primary color of ant design
  layout: 'sidemenu', // nav menu position: `sidemenu` or `topmenu`
  contentWidth: 'Fluid', // layout of content: `Fluid` or `Fixed`, only works when layout is topmenu
  fixedHeader: false, // sticky header
  fixSiderbar: false, // sticky siderbar
  colorWeak: false,
  menu: {
    locale: true,
  },
  title: 'Siques Admin ',

  iconfontUrl: '',
  production:
    process.env.NODE_ENV === 'production' &&
    process.env.VUE_APP_PREVIEW !== 'true',
}