import {
  listPosts
} from "../../engine/post";
import {
  baseUrl
} from "../../utils/util";

// pages/post/index.js
Page({
  /**
   * 页面的初始数据
   */
  data: {
    list: [],
  },
  navToPostDetail(e) {
    wx.navigateTo({
      url: `/pages/post-detail/index?id=${e.currentTarget.dataset.id}`,
    })
  },
  onShow: function () {
    this.getTabBar().init();
    this.listPosts();
  },
  async listPosts() {
    const res = await listPosts();
    res.rows.forEach(e => {
      e.content = e.content.replace("/dev-api", baseUrl)
    })
    this.setData({
      list: res.rows
    })
    console.log(res)
  },
  navigateToEditPost(){
    wx.navigateTo({
      url: `/pages/edit-post/index`,
    })
  }
})