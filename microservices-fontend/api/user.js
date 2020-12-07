import vue from 'vue'

const userApi = {
  userList: '/core/pri/user/findPage',
  userRole: '/core/pri/user/findUserRoles',
  deleteBatch: '/core/pri/user/deleteBatch',
  saveUser: '/core/pri/user',
  deleteById: '/core/pri/user',
  searchUser: '/core/pri/user/searchUser',
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

export function updateUser(parameter) {
  return vue.prototype.$http({
    url: userApi.updateUser,
    method: 'get',
    data: parameter,
  })
}

export function deleteById(id) {
  return vue.prototype.$http({
    url: userApi.deleteById + `/${id}`,
    method: 'delete',
    data: parameter,
  })
}

export function deleteBatch(parameter) {
  return vue.prototype.$http({
    url: userApi.deleteBatch,
    method: 'post',
    data: parameter,
  })
}
