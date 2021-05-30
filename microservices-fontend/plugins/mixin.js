import Vue from 'vue'
/* eslint-disable */
Vue.mixin({
  created: function () {},

  methods: {
    async mixMessage(fun, key) {
      this.$message.loading({ content: '提交表单...', key: key })
      const res = await fun
      if (res.code == 1) {
        this.$message.success({
          content: '表单提交成功!',
          key: key,
          duration: 2,
        })
      } else {
        this.$message.error('提交失败')
      }
      return res
    },
  },
})
