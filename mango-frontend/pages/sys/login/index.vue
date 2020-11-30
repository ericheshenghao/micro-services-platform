<template>
  <div class="lowin">
    <div class="lowin-brand">
      <img src="~/static/kodinger.jpg" alt="logo" />
    </div>
    <div class="lowin-wrapper">
      <div v-loading="loading" class="lowin-box lowin-login">
        <div class="lowin-box-inner">
          <el-form ref="loginForm" :rules="rules" :model="loginForm">
            <p>Sign in to continue</p>
            <div class="lowin-group">
              <label>
                loginCode
                <a href="#" class="login-back-link">Sign in?</a>
              </label>
              <el-form-item prop="loginCode">
                <el-input
                  v-model="loginForm.loginCode"
                  type="account"
                  autocomplete="account"
                  name="loginCode"
                />
              </el-form-item>
            </div>
            <div class="lowin-group password-group">
              <label>
                Password
                <a href="#" class="forgot-link">Forgot Password?</a>
              </label>
              <el-form-item prop="password">
                <el-input
                  v-model="loginForm.password"
                  type="password"
                  name="password"
                  autocomplete="current-password"
                />
              </el-form-item>
            </div>
            <div class="lowin-group password-group">
              <label>Captcha</label>
              <el-form-item prop="captcha">
                <el-input
                  v-model="loginForm.captcha"
                  type="captcha"
                  name="captcha"
                />
              </el-form-item>
              <img
                v-if="isRefresh"
                class="pointer"
                @click="refreshCaptcha"
                :src="config.CAPTCHA_SERVICE"
              />
            </div>

            <el-button @click="login" class="lowin-btn login-btn"
              >Sign In</el-button
            >

            <div class="text-foot"></div>
          </el-form>
        </div>
      </div>
    </div>

    <footer class="lowin-footer"></footer>
  </div>
</template>
<script lang="ts">
import { Vue, Component } from 'nuxt-property-decorator'
import config from '~/plugins/config/website.js'
@Component({
  components: {},
})
export default class login extends Vue {
  layout(context: any) {
    return 'login'
  }
  config: any = config

  isRefresh = true

  loading = false
  http = Vue.prototype.$http

  loginForm = { loginCode: '', password: '', captcha: '' }

  rules = {
    loginCode: [
      { required: true, message: '请输入用户名', trigger: 'blur' },
      { min: 2, max: 10, message: '长度在 2 到 10 个字符', trigger: 'blur' },
      {
        required: true,
        pattern: /^[\u4E00-\u9FA5_a-zA-Z0-9.·-]+$/,
        message: '姓名不支持特殊字符',
        trigger: 'blur',
      },
    ],
    password: [
      { required: true, validator: this.validatePass, trigger: 'blur' },
    ],
    captcha: [{ required: true, validator: this.validateCatcha }],
  }

  validatePass(rule: any, value: any, callback: any) {
    if (value === '') {
      callback(new Error('请输入密码'))
    } else {
      callback()
    }

    // if (this.charLength(value) && this.lowercase(value) && this.number(value)) {
    //   callback()
    // } else {
    //   callback(new Error('密码必须包含数字、字母，区分大小写'))
    // }
  }

  mounted() {
    this.$store.commit('DEL_ALL')
  }

  validateCatcha(rule: any, value: any, callback: any) {
    if (value === '') {
      callback(new Error('请输入验证码'))
    } else {
      callback()
    }
  }

  charLength(value: any) {
    if (value.length >= 7) {
      return true
    }
  }

  lowercase(value: any) {
    const regex = /^(?=.*[a-z]).+$/

    if (regex.test(value)) {
      return true
    }
  }
  number(value: any) {
    const regex = /^(?=.*[0-9]).+$/

    if (regex.test(value)) {
      return true
    }
  }

  refreshCaptcha() {
    this.isRefresh = false
    setTimeout(() => {
      this.isRefresh = true
    }, 300)
  }

  login() {
    this.loading = true
    const ref: any = this.$refs.loginForm

    ref.validate(async (valid: any) => {
      if (valid) {
        const res = await this.$auth.loginWith('local', {
          data: this.loginForm,
        })
        setTimeout(() => {
          this.loading = false
        }, 500)

        if (res.data.resp_code == 0) {
          // 设置token
          this.$store.commit('storeAuth', res.data.datas)

          this.$router.push('/')
        } else {
          this.$notify({
            title: '',
            message: res.data.resp_msg,
            position: 'top-right',
            type: 'error',
          })
        }
      } else {
        // eslint-disable-next-line
        setTimeout(() => {
          this.loading = false
        }, 500)
        console.log('error submit!!')
        return false
      }
    })
  }
}
</script>
<style lang="scss" scoped></style>
