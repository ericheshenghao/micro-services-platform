import vue from 'vue'

const userApi = {
  userList: '/activiti/instance',
}

export function getActivitiList(parameter) {
  return vue.prototype.$http({
    url: userApi.userList,
    method: 'post',
    data: parameter,
  })
}
