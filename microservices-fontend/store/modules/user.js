import { login, getUserInfo, logout } from '@/api/auth'
import { welcome } from '@/utils/util'

export const state = () => ({
  token: '',
  name: '',
  welcome: '',
  avatar: '',
  tenant_id: '',
  permissions: [],
  info: {},
})

export const mutations = {
  SET_TOKEN: (state, token) => {
    state.token = token
  },
  SET_TENANTID: (state, id) => {
    state.tenant_id = id
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
  Login({ commit }, userInfo) {
    return new Promise((resolve, reject) => {
      login(userInfo)
        .then((res) => {
          commit('SET_TOKEN', res.access_token)
          commit('SET_TENANTID', res.x_tenant_id)
          resolve()
        })
        .catch((e) => {
          reject(e)
        })
        .finally(() => {})
    })
  },

  // 获取用户信息
  async GetInfo({ commit }) {
    const res = await getUserInfo()
    commit('SET_INFO', res.data)
    commit('SET_PERMISSIONS', res.data.permissions)
  },

  // 登出
  Logout({ commit, state }) {
    return new Promise((resolve) => {
      logout()
        .then(() => {
          resolve()
        })
        .catch(() => {
          resolve()
        })
        .finally(() => {
          commit('SET_TOKEN', '')
          commit('SET_TENANTID', '')
        })
    })
  },
}
