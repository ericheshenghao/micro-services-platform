/**
 * 全局配置文件
 */

const BASE_URL =
  process.env.NODE_ENV !== 'production'
    ? // 生产模式用域名访问
      'http://localhost:9001'
    : 'http://gateway.siques.cn'
//  将我们的网关改为域名访问
export default {
  API_URL: BASE_URL,

  client_id: 'webApp',
  client_secret: 123456,
  grant_type: 'password_code',

  //配置菜单的属性
  navTheme: 'dark', // theme for nav menu
  primaryColor: '#52C41A', // primary color of ant design
  layout: 'sidemenu', // nav menu position: `sidemenu` or `topmenu`
  contentWidth: 'Fluid', // layout of content: `Fluid` or `Fixed`, only works when layout is topmenu
  fixedHeader: true, // sticky header
  fixSiderbar: true, // sticky siderbar
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
