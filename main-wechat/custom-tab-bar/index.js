Component({
  data: {
    value: 0,
    tabBar: [{
        icon: 'home',
        label: '诗词',
        url: "/pages/index/index"
      }, {
        icon: 'chat',
        label: '帖子',
        url: "/pages/post/index"
      },
      {
        icon: 'user',
        label: '我的',
        url: "/pages/me/index"
      },
    ]
  },
  attached() {},
  methods: {
    onChange(e) {

      wx.switchTab({
        url: this.data.tabBar[e.detail.value].url
      });
    },
    init() {
      const page = getCurrentPages().pop();
      this.setData({
        value: this.data.tabBar.findIndex(item => item.url === `/${page.route}`)
      });
    }
  },
})