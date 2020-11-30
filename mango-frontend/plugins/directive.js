import Vue from 'vue'

Vue.directive('draggable', {
  inserted(el, binding, vnode) {
    const _el = el
    const ref = vnode.context.$refs[binding.value]

    const mgl = _el.offsetLeft
    const mgt = _el.offsetTop
    const maxWidth = ref.clientWidth
    const maxHeight = ref.clientHeight
    // 尺寸变化的元素的初始宽度
    const elWidth = _el.clientWidth
    const elHeight = _el.clientHeight

    ref.onmousedown = (e) => {
      // 鼠标相对元素的距离

      let disX = e.offsetX

      document.onmousemove = (e) => {
        let left = e.clientX

        // 已经是缩进状态
        if (vnode.context.isCollapse == true) {
          if (left > elWidth) {
            vnode.context.isCollapse = false
          }
        } else {
          _el.style.width = left + 'px'
        }

        if (left < elWidth) {
          vnode.context.isCollapse = true
        }

        // vnode.elm.parentElement.parentNode.__vue__._data.width = left + 'px'
        // vnode.context.width = left + 'px'
        // vnode.elm.__vue__.width = left + 'px'
        // ref.style.left = e.clientX - elWidth - mgl - maxWidth / 2 + 'px'

        document.onmouseup = (e) => {
          vnode.context.width = left + 'px'
          ref.onmousemove = null
          el.onmousemove = null
          document.onmousemove = null
          document.onmouseup = null
        }
      }
    }
  },
})
