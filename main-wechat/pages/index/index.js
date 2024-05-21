import {
  listPoetry
} from "../../engine/index";
import {
  listNotices
} from "../../engine/notice";

// pages/index/index.js
Page({
  /**
   * 页面的初始数据
   */
  data: {
    searchText: '',
    list: [],
    notices: [],
    noticeIndex: 0,
    visible: true
  },
  onLoad() {
  },
  click() {
    this.setData({
      visible: !this.data.visible
    })
  },
  navToPoetryDetail(e) {
    wx.navigateTo({
      url: `/pages/poetry-detail/index?id=${e.currentTarget.dataset.id}`,
    })
  },
  async getList() {
    let res = await listPoetry({
      title: this.data.searchText
    });
    this.setData({
      list: res.rows
    })
  },
  onChange: function () {
    this.getList()
  },
  listNotices: async function () {
    const res = await listNotices();
    this.setData({
      notice: res.rows[0]
    })
  },
  onShow: function () {
    this.getTabBar().init();
    this.getList()
    this.listNotices();
  }
})