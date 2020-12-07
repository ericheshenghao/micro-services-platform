const getters = {
  isMobile: (state) => state.modules.app.isMobile,
  lang: (state) => state.modules.app.lang,
  theme: (state) => state.modules.app.theme,
  color: (state) => state.modules.app.color,
  token: (state) => state.modules.user.token,
  avatar: (state) => state.modules.user.avatar,
  nickname: (state) => state.modules.user.name,
  welcome: (state) => state.modules.user.welcome,
  roles: (state) => state.modules.user.roles,
  userInfo: (state) => state.modules.user.info,
  multiTab: (state) => state.modules.app.multiTab,
  tagList: (state) => state.modules.menu.tagList,
  tag: (state) => state.modules.menu.tag,
}

export default getters
