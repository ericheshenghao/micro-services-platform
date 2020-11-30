import createPersistedState from 'vuex-persistedstate'
import { diff } from '~/plugins/util/util.js'
import { setStore, getStore } from '~/plugins/util/store'
import website from '~/plugins/config/website'

const isFirstPage = website.isFirstPage
const tagWel = website.fistPage

const tagObj = {
  label: '', //标题名称
  value: '', //标题的路径
  params: '', //标题的路径参数
  query: '', //标题的参数
  meta: {}, //额外参数
  group: [], //分组
}
//处理首个标签
function setFistTag(list) {
  if (list.length == 1) {
    list[0].close = false
  } else {
    list.forEach((ele) => {
      if (ele.value === tagWel.value && isFirstPage === false) {
        ele.close = false
      } else {
        ele.close = true
      }
    })
  }
}

export const state = () => ({
  tagList: [],
  tag: tagObj,
  tagWel: tagWel,
  // Auth: { token: '' },
  closingPage: '',
  primaryColor: '',
  themeColor: '#20222A',
  socketName: '',
  socketMsg: [],
})

export const mutations = {
  ON_THEME_CHANGE: (state, action) => {
    state.themeColor = action
  },
  ADD_TAG: (state, action) => {
    state.tag = action

    setStore({ name: 'tag', content: state.tag })
    if (state.tagList.some((ele) => diff(ele, action))) return
    state.tagList.push(action)
    setFistTag(state.tagList)
    setStore({ name: 'tagList', content: state.tagList })
  },
  DEL_TAG: (state, action) => {
    state.tagList = state.tagList.filter((item) => {
      return !diff(item, action)
    })
    setFistTag(state.tagList)
    setStore({ name: 'tagList', content: state.tagList })
  },
  DEL_ALL_TAG: (state) => {
    state.tagList = [state.tagWel]
    setStore({ name: 'tagList', content: state.tagList })
  },
  DEL_ALL: (state) => {
    state.tagList = []
    setStore({ name: 'tagList', content: [] })
  },
  DEL_TAG_OTHER: (state) => {
    state.tagList = state.tagList.filter((item) => {
      if (item.value === state.tag.value) {
        return true
      } else if (
        !website.isFirstPage &&
        item.value === website.fistPage.value
      ) {
        return true
      }
    })
    setFistTag(state.tagList)
    setStore({ name: 'tagList', content: state.tagList })
  },
  SET_TAG_LIST(state, tagList) {
    state.tagList = tagList
    setStore({ name: 'tagList', content: state.tagList })
  },
  storeAuth: (state, data) => {
    state.Auth = data
  },
  deleteAuth(state) {
    state.Auth.token = null
  },
  deleteAll(state) {
    state.Auth.token = null
  },
  logOut(state) {
    state.auth.loggedIn = false
  },
  SET_SOCKET_NAME(state, name) {
    state.socketName = name
  },
  PUSH_MSG(state, el) {
    if (state.socketMsg.length > 20) {
      state.socketMsg.splice(0, 2)
    }
    state.socketMsg.push(el)
  },
}

export const getters = {}

export const actions = {
  async checkAuth({ state }, permission) {
    console.log(state)
    const statu = await state.Auth.permissions.findIndex(
      (e) => e === permission
    )

    console.log(statu)
    return statu === -1 ? false : true
  },
}

const vuexSession = new createPersistedState({
  key: 'state',
  storage: window.localStorage,
})

export const plugins = [vuexSession]
