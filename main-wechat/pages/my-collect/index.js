import {
  deleteFavorite,
  listFavorite
} from "../../engine/favorite"

// pages/my-collect/index.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    list: []
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

  /**
   * 生命周期函数--监听页面显示
   */
  async getList() {
    let res = await listFavorite({})
    res.rows.forEach(e => {
      e.bizPoetry.content = e.bizPoetry.content.slice(0, 15)
    })
    this.setData({
      list: res.rows
    })
  },
  async onShow() {
    this.getList()
  },
  async onDelete(e) {
    const id = e.currentTarget.dataset.id
    await deleteFavorite(id);
    wx.showToast({
      title: '删除成功',
    })
    this.getList()
  },
  navToPoetryDetail(e) {
    wx.navigateTo({
      url: `/pages/poetry-detail/index?id=${e.currentTarget.dataset.id}`,
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