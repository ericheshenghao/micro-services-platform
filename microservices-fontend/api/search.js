import vue from 'vue'

const searchApi = {
  searchIndices: '/search/admin/indices',
  userIndices: '/core/pri/user/search',
}

export function searchIndices(parameter) {
  return vue.prototype.$http({
    url: searchApi.searchIndices,
    method: 'post',
    data: parameter,
  })
}

export function userIndices(parameter) {
  return vue.prototype.$http({
    url: searchApi.userIndices,
    method: 'post',
    data: parameter,
  })
}
