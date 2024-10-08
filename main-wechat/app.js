// app.js'
App({
  onLaunch() {
    // 展示本地存储能力
    wx.getSystemInfo({
        success: e => {
          this.globalData.StatusBar = e.statusBarHeight;
          let custom = wx.getMenuButtonBoundingClientRect();
          this.globalData.Custom = custom;
          this.globalData.CustomBar = custom.bottom + custom.top - e.statusBarHeight;
        }
      });
     let userInfo = wx.getStorageSync('userInfo');
     userInfo = userInfo? userInfo: '{}'
      this.globalData.userInfo = JSON.parse(userInfo);
  },
  globalData: {
    userInfo: {
      token: ''
    }
  }
})