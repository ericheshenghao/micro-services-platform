import dotenv from 'dotenv'
dotenv.config()
import config from './plugins/config/website.js'
export default {
  loadingIndicator: {
    name: 'circle',
    color: '#3B8070',
    background: 'white',
  },

  /*
   ** Nuxt rendering mode
   ** See https://nuxtjs.org/api/configuration-mode
   */
  mode: 'spa',
  /*
   ** Nuxt target
   ** See https://nuxtjs.org/api/configuration-target
   */
  target: 'server',
  /*
   ** Headers of the page
   ** See https://nuxtjs.org/api/configuration-head
   */
  head: {
    title: process.env.npm_package_name || '',
    meta: [
      { charset: 'utf-8' },
      { name: 'viewport', content: 'width=device-width, initial-scale=1' },
      {
        hid: 'description',
        name: 'description',
        content: process.env.npm_package_description || '',
      },
    ],
    script: [
      // {
      //   src:
      //     'https://webapi.amap.com/maps?v=1.4.11&key=20bc75bcb6199e0a85986de691a4e5d6&plugin=AMap.PlaceSearch',
      // },
      // {
      //   src:
      //     'https://webapi.amap.com/maps?v=1.4.15&key=20bc75bcb6199e0a85986de691a4e5d6&plugin=AMap.MarkerClusterer',
      // },
      // {
      //   src: 'https://webapi.amap.com/ui/1.0/main.js?v=1.0.11',
      // },
      // {
      //   src: 'https://cdn.staticfile.org/Sortable/1.10.0-rc2/Sortable.min.js',
      // },
      // {
      //   src: 'https://cdnjs.cloudflare.com/ajax/libs/FileSaver.js/1.3.8/FileSaver.min.js',
      // },
      // {
      //   src: 'https://cdnjs.cloudflare.com/ajax/libs/xlsx/0.14.1/xlsx.full.min.js',
      // },
    ],
    link: [
      { rel: 'icon', type: 'image/x-icon', href: '/favicon.ico' },
      {
        rel: 'stylesheet',
        href:
          'https://netdna.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css',
      },
    ],
  },
  /*
   ** Global CSS
   */
  css: [
    'element-ui/lib/theme-chalk/index.css',
    'element-ui/lib/theme-chalk/display.css',
    './css/global.scss',
  ],
  /*
   ** Plugins to load before mounting the App
   ** https://nuxtjs.org/guide/plugins
   */

  router: {
    middleware: ['authenticated', 'worktab'],
  },
  plugins: [
    '@/plugins/axios',
    '@/plugins/main',
    '@/plugins/directive',
    '@/plugins/lang/index.js',
    { src: '@/plugins/mixin.js', ssr: false },
    // '@/plugins/localStorage.js',
    // '@/plugins/config/website.js',
  ],
  /*
   ** Auto import components
   ** See https://nuxtjs.org/api/configuration-components
   */
  components: true,
  /*
   ** Nuxt.js dev-modules
   */
  buildModules: ['@nuxt/typescript-build'],
  /*
   ** Nuxt.js modules
   */
  modules: [
    // Doc: https://axios.nuxtjs.org/usage
    '@nuxtjs/axios',
    '@nuxtjs/pwa',
    '@nuxtjs/auth',
    'nuxt-socket-io',
    // Doc: https://github.com/nuxt/content
    '@nuxt/content',
  ],
  io: {
    // module options
    sockets: [
      {
        name: 'main',
        url: 'http://localhost:8091',
      },
    ],
  },
  /*
   ** Axios module configuration
   ** See https://axios.nuxtjs.org/options
   */
  axios: {
    credentials: true,
  },
  /*
   ** Content module configuration
   ** See https://content.nuxtjs.org/configuration
   */
  auth: {
    redirect: {
      login: false,

      callback: false,
      home: false,
    },
    strategies: {
      local: {
        endpoints: {
          token: {
            // property: 'data.data.token',
            // required: true,
            // type: 'Bearer'
          },
          login: {
            url: config.LOGIN_URL,
            method: 'post',
            propertyName: 'token',
          },
          logout: {
            url: config.LOGOUT_URL,
            method: 'get',
            propertyName: 'token',
          },
          user: false,
        },
        // tokenRequired: true,
        // tokenType: 'bearer'
      },
    },
  },

  content: {},
  /*
   ** Build configuration
   ** See https://nuxtjs.org/api/configuration-build/
   */
  build: {
    transpile: [/^element-ui/],
  },
  env: {},
}
