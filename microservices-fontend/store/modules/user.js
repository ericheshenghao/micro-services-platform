import { login, getInfo, logout } from '@/api/login'
import { welcome } from '@/utils/util'

export const state = () => ({
  token: '',
  name: '',
  welcome: '',
  avatar: '',
  roles: [],
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
  SET_ROLES: (state, roles) => {
    state.roles = roles
  },
  SET_INFO: (state, info) => {
    state.info = info
  },
}

export const actions = {
  Login({ commit }, userInfo) {
    console.log(userInfo)
    return new Promise((resolve, reject) => {
      login(userInfo)
        .then((response) => {
          console.log(response)
          const result = response.datas

          commit('SET_TOKEN', result.token)
          resolve()
        })
        .catch((error) => {
          reject(error)
        })
    })
  },

  // 获取用户信息
  GetInfo({ commit }) {
    return new Promise((resolve, reject) => {
      getInfo()
        .then((response) => {
          const result = response.datas

          // const role = result.role
          // role.permissions = result.role.permissions
          // role.permissions.map(per => {
          //   if (per.actionEntitySet != null && per.actionEntitySet.length > 0) {
          //     const action = per.actionEntitySet.map(action => { return action.action })
          //     per.actionList = action
          //   }
          // })
          // role.permissionList = role.permissions.map(permission => { return permission.permissionId })
          // commit('SET_ROLES', result.role)
          commit('SET_INFO', result)

          // reject(new Error('getInfo: roles must be a non-null array !'))

          commit('SET_NAME', { name: result.userName, welcome: welcome() })
          commit('SET_AVATAR', result.avatar)

          resolve(response)
        })
        .catch((error) => {
          reject(error)
        })
    })
  },

  // 登出
  Logout({ commit, state }) {
    commit('SET_TOKEN', '')
    commit('SET_ROLES', [])
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
