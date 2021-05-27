export default function ({ route, store, redirect }) {
  // If the user is not authenticated
  // true true
  // if (route.path != '/sys/login' && !store.state.auth.loggedIn) {
  //   return redirect('/sys/login')
  // }
  // // 用户已存在想访问登录页面

  if (
    route.path == '/login' &&
    store.state.modules.user.token &&
    route.query['client_id']
  ) {
    return redirect({ name: 'prove', query: route.query })
  }

  if (route.path == '/login' && !store.state.modules.user.token == '') {
    return redirect('/')
  }

  if (route.path != '/login' && store.state.modules.user.token == '') {
    return redirect('/login')
  }
}
