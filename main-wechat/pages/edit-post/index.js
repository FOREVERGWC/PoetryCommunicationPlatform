import {
  addPost
} from "../../engine/post"

let richText;
// pages/edit-post/index.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    value: '',
    title: '',
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
  onShow() {

  },
  inputTitle(e) {
    this.setData({
      title: e.detail.value
    })
  },
  bindinput(e) {
    this.setData({
      value: e.detail.value.detail.html
    })
  },
  async getEditorContent() {
    if (!this.data.title) {
      wx.showToast({
        title: '请输入标题',
      })
      return
    }
    if (!this.data.value) {
      wx.showToast({
        title: '请输入内容',
      })
      return
    }
   let { code } =  await addPost({
      title: this.data.title,
      content: this.data.value
    })
    if(code != 200){
      wx.showToast({
        icon:'error',
        title: '发帖失败',
      })
    }
    else{ wx.showToast({
      title: '发帖成功',
    })
      wx.navigateBack()
    }
  },

  //编辑器初始化完成时触发，可以获取组件实例
  onEditorReady() {
    console.log('[onEditorReady callback]')
    richText = this.selectComponent('#richText'); //获取组件实例
  },

  //插入图片
  insertImageEvent() {
    wx.chooseImage({
      count: 1,
      success: res => {
        let path = res.tempFilePaths[0];
        //调用子组件（富文本组件）方法，图片应先上传再插入，不然预览时无法查看图片。
        richText.insertImageMethod(path).then(res => {
          console.log('[insert image success callback]=>', res)
        }).catch(res => {
          console.log('[insert image fail callback]=>', res)
        });
      }
    })
  },
})