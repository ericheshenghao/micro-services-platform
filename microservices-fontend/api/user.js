import vue from 'vue'

const userApi = {
  userList: '/core/user/findPage',
  userRole: '/core/user/findUserRoles',
  deleteBatch: '/core/user/deleteBatch',
  saveUser: '/core/user',
  deleteById: '/core/user',
  changeStatus: '/core/user',
  searchUser: '/core/user/searchUser',
}

export function searchUser(parameter) {
  return vue.prototype.$http({
    url: userApi.searchUser,
    method: 'post',
    data: parameter,
  })
}

export function getUserList(parameter) {
  return vue.prototype.$http({
    url: userApi.userList,
    method: 'post',
    data: parameter,
  })
}

export function findUserRole(parameter) {
  return vue.prototype.$http({
    url: userApi.userRole,
    method: 'get',
    params: parameter,
  })
}

export function saveUser(parameter) {
  return vue.prototype.$http({
    url: userApi.saveUser,
    method: 'post',
    data: parameter,
  })
}

export function deleteById(id) {
  return vue.prototype.$http({
    url: userApi.deleteById + `/${id}`,
    method: 'delete',
  })
}

export function deleteBatch(parameter) {
  return vue.prototype.$http({
    url: userApi.deleteBatch,
    method: 'post',
    data: parameter,
  })
}

export function changeStatus(parameter) {
  return vue.prototype.$http({
    url: userApi.changeStatus + '/' + parameter.id,
    method: 'put',
    data: parameter,
  })
}
