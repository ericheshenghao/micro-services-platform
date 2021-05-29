import { checkToken } from '@/api/auth'

export default function ({ route, store, redirect }) {
  let token = store.state.modules.user.token

  // 登录界面
  if (route.path == '/login') {
    // token非空
    if (token !== '') {
      if (route.query['client_id']) {
        let res = checkToken(token)
        if (res.code == 0) {
          return
        }
        return redirect({ name: 'prove', query: route.query })
      }
      return redirect('/')
    }

    // 非登录界面无token
  } else {
    if (token == '') {
      if (route.query['client_id']) {
        return redirect({ name: 'login', query: route.query })
      }
      return redirect('/login')
    }
  }
}
