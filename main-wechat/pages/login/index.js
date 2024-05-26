// pages/login/index.js
import {
  getUserInfo,
  login,
  loginByUserName
} from "../../engine/me";
const app = getApp();
Page({
  /**
   * 页面的初始数据
   */
  data: {
    username: "",
    password: ""
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {

  },
  loginByUsername: async function () {
    if (!this.data.username) {
      wx.showToast({
        icon: "error",
        title: '请输入账号',
      })
      return
    }
    if (!this.data.password) {
      wx.showToast({
        icon: "error",
        title: '请输入密码',
      })
      return
    }
    const {
      token
    }  = await loginByUserName({
      username: this.data.username,
      password: this.data.password
    });
    app.globalData.userInfo.token = token
    const userInfo = await getUserInfo();
    app.globalData.userInfo = {
      token,
      ...userInfo.user
    }
    wx.setStorageSync('userInfo', JSON.stringify(app.globalData.userInfo))
    wx.navigateBack()
    if (res.code !== 200) {
      wx.showModal({
        title: "错误",
        content: res.msg,
        showCancel:false
      })
    }
  },
  login: async function (e) {
    const wxUserInfo = e.detail.userInfo;
    let {
      code
    } = await wx.login();
    const {
      token
    } = await login({
      js_code: code,
      grant_type: "authorization_code"
    });
    app.globalData.userInfo.token = token
    const userInfo = await getUserInfo();
    userInfo.user.avatar = wxUserInfo.avatarUrl
    userInfo.user.nickName = wxUserInfo.nickName
    userInfo.user.sex = wxUserInfo.gender
    app.globalData.userInfo = {
      token,
      ...userInfo.user
    }
    wx.setStorageSync('userInfo', JSON.stringify(app.globalData.userInfo))
    wx.navigateBack()
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow() {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload() {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh() {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom() {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage() {

  }
})