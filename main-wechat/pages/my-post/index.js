import {
  listMyPosts
} from "../../engine/post"
const app = getApp();
// pages/my-post/index.js
Page({

  /**
   * 页面的初始数据
   */
  data: {

  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {

  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady() {

  },
  navToPostDetail(e) {
    wx.navigateTo({
      url: `/pages/post-detail/index?id=${e.currentTarget.dataset.id}`,
    })
  },
  /**
   * 生命周期函数--监听页面显示
   */
  async onShow() {
    let res = await listMyPosts({}, app.globalData.userInfo.userId)
    this.setData({
      list: res.rows
    })
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