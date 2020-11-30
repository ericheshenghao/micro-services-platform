import Vue from 'vue'
export default function ({ route, store, redirect }) {
  // If the user is not authenticated
  // true true
  if (route.path != '/sys/login' && !store.state.auth.loggedIn) {
    return redirect('/sys/login')
  }

  // 用户已存在想访问登录页面
  if (route.path == '/sys/login' && store.state.auth.loggedIn) {
    return redirect('/')
  }
}
