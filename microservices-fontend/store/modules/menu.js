import { diff } from '~/utils/util.js'

const isFirstPage = false
const tagWel = {
  label: '首页',
  value: '/',
  params: {},
  query: {},
  meta: {
    i18n: 'dashboard',
  },
}
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
  tagList: [
    {
      label: '首页',
      value: '/',
      params: {},
      query: {},
      meta: {
        i18n: 'dashboard',
      },
      group: [],
      close: false,
    },
  ],
  tagWel: {
    label: '首页',
    value: '/',
    params: {},
    query: { label: '首页' },
    meta: {
      i18n: 'dashboard',
    },
  },
  tag: tagObj,

  // Auth: { token: '' },
  closingPage: '',
  primaryColor: '',
  themeColor: '#20222A',
})

export const mutations = {
  ADD_TAG: (state, action) => {
    state.tag = action

    if (state.tagList.some((ele) => diff(ele, action))) return
    state.tagList.push(action)
  },
  DEL_TAG: (state, action) => {
    state.tagList = state.tagList.filter((item) => {
      return !diff(item, action)
    })
    setFistTag(state.tagList)
  },
  DEL_ALL_TAG: (state) => {
    state.tagList = [state.tagWel]
  },
  DEL_ALL: (state) => {
    state.tagList = []
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
  },
  SET_TAG_LIST(state, tagList) {
    state.tagList = tagList
  },
}
