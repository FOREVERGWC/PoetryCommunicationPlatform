import {
  getPost,
  reply
} from "../../engine/post"
import {
  getReplyList
} from "../../engine/reply"
import {
  baseUrl
} from "../../utils/util"
const app = getApp();
// pages/post-detail/index.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    id: '',
    item: "",
    posts: {},
    commentValue: '',
    replyList: []
  },
  /**
   * 生命周期函数--监听页面加载
   */
  async onLoad(options) {
    this.setData({
      id: options.id
    })
    const res = await getPost(options.id)
    const res2 = await getReplyList({
      postsId: options.id
    })
    console.log(res.data)
    this.setData({
      posts: res.data,
      item: res.data.content.replace("/dev-api", baseUrl),
      replyList: res2.rows
    })
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow() {},

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

  },
  bindInput: function (e) {
    this.setData({
      commentValue: e.detail.value
    })
  },
  async sendComment() {
    if (!this.data.commentValue) {
      wx.showToast({
        title: '请输入文本',
        icon: 'error',
        duration: 2000
      })
      return
    }
    console.log(this.data.item)
    await reply({
      postsId: this.data.posts.id,
      userId: app.globalData.userInfo.id,
      content: this.data.commentValue
    })
    wx.showToast({
      title: '发布成功',
      icon: 'success',
      duration: 2000
    })
    const res = await getReplyList({
      postsId: this.data.posts.id
    })
    this.setData({
      replyList: res.rows
    })
  }
})