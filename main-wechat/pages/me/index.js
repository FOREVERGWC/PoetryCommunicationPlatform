import {
  updateUserPhone
} from "../../engine/me";
import {
  baseUrl
} from "../../utils/util";

const app = getApp();

// pages/me/index.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    userInfo: app.globalData.userInfo,
    baseUrl,
    bottomModal: false,
    showWithInput: false,
    phoneError: false,
    phoneNumber: '',
    priceError: '',
  },
  onPhoneInput(e) {
    const {
      phoneError
    } = this.data;
    const isPhoneNumber = /^[1][3,4,5,7,8,9][0-9]{9}$/.test(e.detail.value);
    if (phoneError === isPhoneNumber) {
      this.setData({
        phoneError: !isPhoneNumber,
        phoneNumber: e.detail.value
      });
    }
  },

  onShow: function () {
    this.getTabBar().init();
    wx.getSetting({
      success(res) {
        if (res.authSetting['scope.userInfo']) {
          // 已经授权，可以直接调用 getUserInfo 获取头像昵称
          wx.getUserInfo({
            success: function (res) {
              console.log(res.userInfo)
            }
          })
        }
      }
    })
    this.setData({
      userInfo: app.globalData.userInfo
    })
  },
  navToMyCollect: function () {
    wx.navigateTo({
      url: '/pages/my-collect/index',
    })
  },
  bindPhone: function () {
    if (!this.data.userInfo) {
      wx.showModal({
        title: '提示',
        content: '未登陆'
      })
      return
    }
    this.setData({
      showWithInput: true
    })
  },
  closeDialog: function () {
    this.setData({
      showWithInput: false
    })
  },
  onConfirm: async function () {
    this.setData({
      showWithInput: false
    })
    this.setData({
      userInfo: {
        ...this.data.userInfo,
        phoneNumber: this.data.phoneNumber
      }
    })
    app.globalData.userInfo = {
      ...app.globalData.userInfo,
      phoneNumber: this.data.phoneNumber
    }
    await updateUserPhone({
      userName: this.data.userInfo.nickName,
      userId: this.data.userInfo.userId,
      phonenumber: this.data.phoneNumber
    })


  },
  navToMyPost: function () {
    wx.navigateTo({
      url: '/pages/my-post/index',
    })
  },
  logout() {
    this.setData({
      userInfo:{},
    })
    wx.setStorageSync('userInfo', JSON.stringify({}))
    app.globalData.userInfo = {}
  },
  navigateToLogin(params) {
    if (!this.data.userInfo.nickName) {
      wx.navigateTo({
        url: '/pages/login/index',
      })
    }
  }
})