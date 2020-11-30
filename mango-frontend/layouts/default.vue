<template>
  <el-container style="display: -webkit-box !important">
    <el-menu
      v-loading="loading"
      element-loading-background="hsla(0,0%,28%,1)"
      background-color="#20222A"
      text-color="hsla(0,0%,100%,.7)"
      active-text-color="#ffd04b"
      style="overflow-y: scroll"
      class="el-menu-vertical-demo"
      :collapse="isCollapse"
      :unique-opened="true"
    >
      <el-menu-item class="fs-4 text-white">
        <i class="el-icon-menu text-white"></i>
        <span slot="title">Siques-Admin</span>
      </el-menu-item>
      <el-submenu
        v-for="(menu, index) in menus"
        :key="index"
        :index="`${index}`"
      >
        <template slot="title">
          <i :class="menu.icon"></i>
          <span slot="title">{{ menu.name }}</span>
        </template>

        <template v-for="(submenu, index1) in menu.children">
          <el-menu-item
            v-if="submenu.type != 2"
            :key="`${index}-${index1}`"
            :index="`${submenu.url}`"
            @click="changeRoute(submenu.url, submenu.name)"
          >
            <i :class="submenu.icon"></i>
            <span slot="title">{{ submenu.name }}</span>
          </el-menu-item>
        </template>
      </el-submenu>
    </el-menu>

    <el-container class="h-100">
      <el-header class="d-flex jc-between ai-center">
        <div class="fs-8 pl-3">
          <el-button
            @click="isCollapse = !isCollapse"
            :icon="isCollapse ? `el-icon-s-unfold` : 'el-icon-s-fold'"
            type="text"
            size="medium"
          ></el-button>
        </div>
        <div class="d-flex ai-center pr-4">
          <Themepicker
            @onThemeChange="onThemeChange"
            :default="themeColor"
          ></Themepicker>
          <div class="pl-3">
            <el-tooltip content="全屏" placement="bottom" effect="dark">
              <el-button
                icon="el-icon-full-screen"
                @click="fullScreen"
                type="text"
              ></el-button>
            </el-tooltip>
          </div>

          <div class="pl-3">
            <el-tooltip content="切换语言" placement="bottom" effect="dark">
              <el-button
                @click="changeLanguage()"
                class="fa fa-language"
                type="text"
              ></el-button>
            </el-tooltip>
          </div>

          <div class="pl-3">{{ this.$store.state.Auth.name }}</div>
          <el-dropdown @command="handleCommand">
            <span class="el-dropdown-link">
              <i class="el-icon-arrow-down el-icon--right"></i>
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item command="logout">{{
                $t('navbar.logOut')
              }}</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </div>
      </el-header>
      <el-container>
        <div class="bg-light h-100">
          <div class="bg-white mb-2" style="z-index: 1">
            <Menutags></Menutags>
          </div>

          <el-main class="h-100 d-flex">
            <!-- <div ref="drag" class="resize">
              <div class="resize-bar"></div>
            </div>-->
            <Nuxt :keep-alive="true" max="10" />
          </el-main>
        </div>
      </el-container>
    </el-container>
  </el-container>
</template>
<script lang="ts">
import { Vue, Component } from 'nuxt-property-decorator'

@Component({
  components: {},
})
export default class MenuLayOut extends Vue {
  themeColor = this.$store.state.themeColor
  menus: any = []
  loading = true
  http = Vue.prototype.$http
  isCollapse = false
  color = ''
  mounted() {
    setTimeout(() => {
      this.fetchMenu()
    }, 300)
  }

  get closingPage() {
    return this.$store.state.closingPage
  }

  handleOpen(key: any, keyPath: any) {
    console.log(key, keyPath)
  }

  onThemeChange(val: any) {
    this.$store.commit('ON_THEME_CHANGE', val)
  }

  async fetchMenu() {
    this.loading = true

    const res = await this.http.get('/pri/menu/findNavTree', {
      prefix: 'core',
    })

    setTimeout(() => {
      this.loading = false
      this.menus = res.data.datas
    }, 500)
  }

  handleCommand(command: any) {
    switch (command) {
      case 'logout':
        this.logOut()
        break

      default:
        break
    }
  }

  logOut() {
    this.$auth.logout()
    this.$store.commit('deleteAll')
    this.$router.push('sys/login')
  }
  fullscreen = false
  fullScreen() {
    let element = document.documentElement

    if (element.requestFullscreen) {
      element.requestFullscreen()
    }
  }

  lang = 'en'
  changeLanguage() {
    this.lang === 'en' ? (this.lang = 'zh') : (this.lang = 'en')
    console.log(this.$i18n)
    const i18: any = this.$i18n
    i18.locale = this.lang
    this.$notify({
      title: '',
      message: '切换成功',
      position: 'bottom-right',
      type: 'success',
    })
  }

  changeRoute(url: string, name: string) {
    console.log(url)
    if (url.indexOf('http') != -1) {
      window.open(url, '_blank')
    } else {
      this.$router.push({
        path: url,
        query: { label: name },
      })
    }
  }
}
</script>
<style lang="scss">
#__layout {
  height: 100vh;
}
.el-header,
.el-footer {
  padding: 0 !important;
  height: 50px !important;
}

.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 200px;
  min-height: 400px;
}

.el-aside {
  text-align: left;

  /* background-color: #545c64; */
}

.el-main {
  padding: 0 !important;
}

.el-container {
  height: 100% !important;
}

.resize {
  background-color: white;
  transform: translateZ(0px);
  width: 10px;
  z-index: 3;
  height: 100%;
  position: relative;
  cursor: e-resize;
  &-bar {
    background-color: rgb(76, 154, 255);
    opacity: 0;
    height: 100%;
    width: 2px;
    transition: opacity 200ms ease 0s;
  }

  &:hover {
    .resize-bar {
      opacity: 1;
    }
  }
}
</style>
