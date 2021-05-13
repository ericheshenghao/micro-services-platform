import { login, getUserInfo } from '@/api/login'
import { welcome } from '@/utils/util'

export const state = () => ({
  token: '',
  name: '',
  welcome: '',
  avatar: '',
  permissions: [],
  info: {},
})

export const mutations = {
  SET_TOKEN: (state, token) => {
    state.token = token
  },
  SET_NAME: (state, { name, welcome }) => {
    state.name = name
    state.welcome = welcome
  },
  SET_AVATAR: (state, avatar) => {
    state.avatar = avatar
  },
  SET_PERMISSIONS: (state, permissions) => {
    state.permissions = permissions
  },
  SET_INFO: (state, info) => {
    state.info = info
  },
}

export const actions = {
  async Login({ commit }, userInfo) {
    const res = await login(userInfo)

    commit('SET_TOKEN', res.access_token)
  },

  // 获取用户信息
  async GetInfo({ commit }) {
    const res = await getUserInfo()
    commit('SET_INFO', res.data)
    commit('SET_PERMISSIONS', res.data.permissions)
  },

  // 登出
  Logout({ commit, state }) {
    commit('SET_TOKEN', '')

    // return new Promise((resolve) => {
    //   logout(state.token)
    //     .then(() => {

    //       resolve()
    //     })
    //     .catch(() => {
    //       resolve()
    //     })
    //     .finally(() => {})
    // })
  },
}
