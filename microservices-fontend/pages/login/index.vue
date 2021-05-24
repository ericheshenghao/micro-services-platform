<template>
  <div class="main">
    <a-form
      id="formLogin"
      class="user-layout-login"
      ref="formLogin"
      :form="form"
      @submit="handleSubmit"
    >
      <a-tabs
        :activeKey="customActiveKey"
        :tabBarStyle="{ textAlign: 'center', borderBottom: 'unset' }"
        @change="handleTabClick"
      >
        <a-tab-pane key="tab1" tab="账号密码登录">
          <a-alert
            v-if="isLoginError"
            type="error"
            showIcon
            style="margin-bottom: 24px"
            message="账户或密码错误（admin)"
          />
          <a-form-item>
            <a-input
              size="large"
              type="text"
              placeholder="账户: admin"
              v-decorator="[
                'username',
                {
                  rules: [
                    { required: true, message: '请输入帐户名或邮箱地址' },
                    { validator: handleLogincodeOrEmail },
                  ],
                  validateTrigger: 'change',
                },
              ]"
            >
              <a-icon
                slot="prefix"
                type="user"
                :style="{ color: 'rgba(0,0,0,.25)' }"
              />
            </a-input>
          </a-form-item>

          <a-form-item>
            <a-input-password
              size="large"
              placeholder="密码: admin or ant.design"
              v-decorator="[
                'password',
                {
                  rules: [{ required: true, message: '请输入密码' }],
                  validateTrigger: 'blur',
                },
              ]"
            >
              <a-icon
                slot="prefix"
                type="lock"
                :style="{ color: 'rgba(0,0,0,.25)' }"
              />
            </a-input-password>
          </a-form-item>

          <a-row :gutter="16">
            <a-col class="gutter-row" :span="16">
              <a-form-item>
                <a-input
                  size="large"
                  type="text"
                  placeholder="验证码"
                  v-decorator="[
                    'validCode',
                    {
                      rules: [{ required: true, message: '请输入验证码' }],
                      validateTrigger: 'blur',
                    },
                  ]"
                >
                  <a-icon
                    slot="prefix"
                    type="mail"
                    :style="{ color: 'rgba(0,0,0,.25)' }"
                  />
                </a-input>
              </a-form-item>
            </a-col>
            <a-col class="gutter-row" :span="8">
              <img
                v-if="showCaptch"
                @click="changeCaptch"
                style="height: 40px; width: 100%; object-fit: cover"
                :src="captchaUrl"
                alt=""
              />
            </a-col>
          </a-row>
        </a-tab-pane>
        <a-tab-pane key="tab2" tab="手机号登录">
          <a-form-item>
            <a-input
              size="large"
              type="text"
              placeholder="手机号"
              v-decorator="[
                'mobile',
                {
                  rules: [
                    {
                      required: true,
                      pattern: /^1[34578]\d{9}$/,
                      message: '请输入正确的手机号',
                    },
                  ],
                  validateTrigger: 'change',
                },
              ]"
            >
              <a-icon
                slot="prefix"
                type="mobile"
                :style="{ color: 'rgba(0,0,0,.25)' }"
              />
            </a-input>
          </a-form-item>

          <a-row :gutter="16">
            <a-col class="gutter-row" :span="16">
              <a-form-item>
                <a-input
                  size="large"
                  type="text"
                  placeholder="验证码"
                  v-decorator="[
                    'validCode',
                    {
                      rules: [{ required: true, message: '请输入验证码' }],
                      validateTrigger: 'blur',
                    },
                  ]"
                >
                  <a-icon
                    slot="prefix"
                    type="mail"
                    :style="{ color: 'rgba(0,0,0,.25)' }"
                  />
                </a-input>
              </a-form-item>
            </a-col>
            <a-col class="gutter-row" :span="8">
              <a-button
                class="getCaptcha"
                tabindex="-1"
                :disabled="state.smsSendBtn"
                @click.stop.prevent="getCaptcha"
                v-text="
                  (!state.smsSendBtn && '获取验证码') || state.time + ' s'
                "
              ></a-button>
            </a-col>
          </a-row>
        </a-tab-pane>
      </a-tabs>

      <a-form-item>
        <a-checkbox v-decorator="['rememberMe', { valuePropName: 'checked' }]"
          >自动登录</a-checkbox
        >
        <router-link :to="'/'" class="forge-password" style="float: right"
          >忘记密码</router-link
        >
      </a-form-item>

      <a-form-item style="margin-top: 24px">
        <a-button
          size="large"
          type="primary"
          htmlType="submit"
          class="login-button"
          :loading="state.loginBtn"
          :disabled="state.loginBtn"
          >确定</a-button
        >
      </a-form-item>

      <div class="user-login-other">
        <span>其他登录方式</span>
        <a>
          <a-icon class="item-icon" type="alipay-circle"></a-icon>
        </a>
        <a>
          <a-icon class="item-icon" type="taobao-circle"></a-icon>
        </a>
        <a>
          <a-icon class="item-icon" type="weibo-circle"></a-icon>
        </a>
        <router-link class="register" :to="'/'">注册账户</router-link>
      </div>
    </a-form>

    <two-step-captcha
      v-if="requiredTwoStepCaptcha"
      :visible="stepCaptchaVisible"
      @success="stepCaptchaSuccess"
      @cancel="stepCaptchaCancel"
    ></two-step-captcha>
  </div>
</template>

<script lang="ts">
import { Vue, Component } from 'nuxt-property-decorator'
import { getCaptcha } from '~/api/auth.js'
import { timeFix } from '@/utils/util'
@Component({})
export default class login extends Vue {
  layout(context: any) {
    return 'login'
  }

  captchaUrl: any = ''
  async mounted() {
    this.getCaptchaUrl()
  }

  async getCaptchaUrl() {
    const res = await getCaptcha()

    this.captchaUrl = window.URL.createObjectURL(res)
  }

  changeCaptch() {
    this.showCaptch = false
    setTimeout(async () => {
      this.getCaptchaUrl()
      this.showCaptch = true
    }, 500)
  }

  customActiveKey = 'tab1'
  loginBtn = false
  // login type= 0 email, 1 username, 2 telephone
  loginType = 0
  isLoginError = false
  requiredTwoStepCaptcha = false
  stepCaptchaVisible = false

  showCaptch = true
  state = {
    time: 60,
    loginBtn: false,
    // login type= 0 email, 1 username, 2 telephone
    loginType: 0,
    smsSendBtn: false,
  }

  form: any // 此处不能赋值
  beforeCreate() {
    // 使用beforeCreate后可输入可校验
    this.form = this.$form.createForm(this)
  }

  created() {}

  handleLogincodeOrEmail(rule: any, value: any, callback: any) {
    const { state } = this
    const regex = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/
    if (regex.test(value)) {
      state.loginType = 0
    } else {
      state.loginType = 1
    }
    callback()
  }

  handleTabClick(key: any) {
    this.customActiveKey = key
    // this.form.resetFields()
  }

  handleSubmit(e: any) {
    e.preventDefault()
    const {
      form: { validateFields },
      state,
      customActiveKey,
    } = this

    state.loginBtn = true

    const validateFieldsKey =
      customActiveKey === 'tab1'
        ? ['username', 'password', 'validCode']
        : ['mobile', 'validCode']

    validateFields(
      validateFieldsKey,
      { force: true },
      (err: any, values: any) => {
        if (!err) {
          const loginParams = { ...values }
          delete loginParams.username
          loginParams[!state.loginType ? 'email' : 'username'] = values.username
          // loginParams.password = md5(values.password)

          loginParams.password = values.password
          // console.log('login form', loginParams)
          this.$store
            .dispatch('modules/user/Login', loginParams)

            .then((res: any) => {
              this.loginSuccess(res)
            })

            .finally(() => {
              state.loginBtn = false
            })
        } else {
          setTimeout(() => {
            state.loginBtn = false
          }, 600)
        }
      }
    )
  }

  loginSuccess(res: any) {
    this.$router.push({ path: '/' })
    // 延迟 1 秒显示欢迎信息
    setTimeout(() => {
      this.$notification.success({
        message: '欢迎',
        description: `${timeFix()}，欢迎回来`,
      })
    }, 1000)
    this.isLoginError = false
  }
}
</script>

<style lang="less" scoped>
.user-layout-login {
  label {
    font-size: 14px;
  }

  .getCaptcha {
    display: block;
    width: 100%;
    height: 40px;
  }

  .forge-password {
    font-size: 14px;
  }

  button.login-button {
    padding: 0 15px;
    font-size: 16px;
    height: 40px;
    width: 100%;
  }

  .user-login-other {
    text-align: left;
    margin-top: 24px;
    line-height: 22px;

    .item-icon {
      font-size: 24px;
      color: rgba(0, 0, 0, 0.2);
      margin-left: 16px;
      vertical-align: middle;
      cursor: pointer;
      transition: color 0.3s;

      &:hover {
        color: #1890ff;
      }
    }

    .register {
      float: right;
    }
  }
}
</style>
