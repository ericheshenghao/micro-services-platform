import { loadLanguageAsync } from '@/locales'

export const state = () => ({
  sideCollapsed: false,
  isMobile: false,
  theme: 'dark',
  layout: '',
  contentWidth: '',
  fixedHeader: false,
  fixedSidebar: false,
  autoHideHeader: false,
  color: '',
  weak: false,
  multiTab: true,
  lang: 'en-US',
  _antLocale: {},
})

export const mutations = {
  SIDEBAR_TYPE: (state, type) => {
    state.sideCollapsed = type
  },
  TOGGLE_MOBILE_TYPE: (state, isMobile) => {
    state.isMobile = isMobile
  },
  TOGGLE_NAV_THEME: (state, theme) => {
    state.theme = theme
  },
  TOGGLE_LAYOUT: (state, mode) => {
    state.layout = mode
  },
  TOGGLE_FIXED_HEADER: (state, mode) => {
    state.fixedHeader = mode
  },
  TOGGLE_FIXED_SIDEBAR: (state, mode) => {
    state.fixedSidebar = mode
  },
  TOGGLE_CONTENT_WIDTH: (state, type) => {
    state.contentWidth = type
  },
  TOGGLE_HIDE_HEADER: (state, type) => {
    state.autoHideHeader = type
  },
  TOGGLE_COLOR: (state, color) => {
    state.color = color
  },
  TOGGLE_WEAK: (state, mode) => {
    state.weak = mode
  },
  APP_LANGUAGE: (state, lang, antd = {}) => {
    state.lang = lang
    state._antLocale = antd
  },
  TOGGLE_MULTI_TAB: (state, bool) => {
    state.multiTab = bool
  },
}

export const action = {
  setLang({ commit }, lang) {
    return new Promise((resolve, reject) => {
      commit(APP_LANGUAGE, lang)
      loadLanguageAsync(lang)
        .then(() => {
          resolve()
        })
        .catch((e) => {
          reject(e)
        })
    })
  },
}
