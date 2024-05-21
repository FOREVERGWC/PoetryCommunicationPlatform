import {
  favoriteChange,
  getPoetry,
  sendComment
} from "../../engine/index"
import { baseUrl } from "../../utils/util";

const app = getApp();

// pages/post-detail/index.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    id: '',
    poetry: {},
    commentValue: '',
    current: 0,
    autoplay: false,
    duration: 500,
    interval: 5000,
  },
  onTap(e) {
    const {
      index
    } = e.detail;
    console.log(index);
  },
  bindInput: function (e) {
    this.setData({
      commentValue: e.detail.value
    })
  },
  /**
   * 生命周期函数--监听页面加载
   */
  async sendComment() {
    if (!this.data.commentValue) {
      wx.showToast({
        title: '请输入文本',
        icon: 'error',
        duration: 2000
      })
      return
    }
    await sendComment({
      poetryId: this.data.poetry.id,
      userId: app.globalData.userInfo.id,
      content: this.data.commentValue
    })
    wx.showToast({
      title: '发布成功',
      icon: 'success',
      duration: 2000
    })
    this.loadPoetry(this.data.id)
  },
  onLoad(options) {
    this.loadPoetry(options.id)
    this.setData({
      id: options.id
    })
  },
  async loadPoetry(id) {
    let res = await getPoetry(id);
    this.setData({
      poetry: res.data
    })
  },
  async favorite() {
    this.setData({
      poetry: {
        ...this.data.poetry,
        favorite: !this.data.poetry.favorite,
        favorites: this.data.poetry.favorite ? this.data.poetry.favorites + 1 : this.data.poetry.favorites - 1,
      }
    })
    await favoriteChange(this.data.id)
  }
})