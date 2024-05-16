<template>
  <div>
    <el-card shadow="hover" style="margin: 20px">{{ form.title }}</el-card>
    <el-card style="margin-left: 20px; margin-right: 20px">
      <el-row>
        <el-col :span="2">
          <el-row style="align-items: center">
            <el-avatar :src="require('@/assets/images/profile.jpg')" shape="square" style="border: 1px solid gray"></el-avatar>
          </el-row>
          <el-row style="align-items: center; margin-top: 5px">
            <span>{{ form.createBy }}</span>
          </el-row>
        </el-col>
        <el-col :span="22">
          <span v-html="form.content"></span>
          <el-row>
            <span style="color: gray; float: right">1楼&emsp;{{ form.createTime }}</span>
          </el-row>
        </el-col>
      </el-row>
    </el-card>
    <el-card shadow="always" style="margin-top: 20px; margin-left: 20px; margin-right: 20px">
      <div style="display: flex; align-items: flex-end; margin-bottom: 20px">
        <h1 style="display: inline-block; margin: 0;">回复</h1>
        <div class="total" style="display: inline-block; margin-left: 15px">{{ total }}</div>
      </div>
      <span style="cursor: pointer" @click="getReplyList('asc')">正序</span>
      <el-divider direction="vertical"></el-divider>
      <span style="cursor: pointer" @click="getReplyList('desc')">倒序</span>
      <el-row>
        <el-col :span="1">
          <el-avatar :src="avatar" style="border: 1px solid gray"></el-avatar>
        </el-col>
        <el-col :span="23">
          <span v-if="replyId" style="padding-top: 5px; padding-bottom: 5px">回复<span style="color: #66ccff">@{{ replyName }}</span></span>
          <el-input v-model="reply" placeholder="一时的嘴臭，极致的享受" type="textarea"></el-input>
        </el-col>
      </el-row>
      <el-row>
        <el-button style="float: right" type="primary" @click="handleReply">发布</el-button>
      </el-row>
      <div v-for="item in replyList" :key="item.id">
        <el-row>
          <el-col :span="1">
            <el-avatar :src="getAvatar(item.user.avatar)" style="border: 1px solid gray"></el-avatar>
          </el-col>
          <el-col :span="23">
            <span style="color: #fb7299">{{ item.user.nickName || item.user.userName }}</span>
            <el-row>{{ item.content }}</el-row>
            <el-row>
              <span style="color: gray">{{ item.createTime }}</span>
              <svg-icon icon-class="favour" style="margin-left: 20px"/>
              0
              <svg-icon icon-class="dis-favour" style="margin-left: 20px"/>
              <span style="color: gray; cursor: pointer; margin-left: 20px" @click="handleReplyPerson(item)">回复</span>
            </el-row>
            <!-- todo 封装评论组件递归调用 -->
            <el-row>
              <div v-for="child in item.children" :key="child.id">
                <el-row>
                  <el-col :span="1">
                    <el-avatar :src="getAvatar(child.user.avatar)" style="border: 1px solid gray"></el-avatar>
                  </el-col>
                  <el-col :span="23">
                    <span style="color: #fb7299">{{ child.user.nickName || child.user.userName }}</span>
                    <el-row>{{ child.content }}</el-row>
                    <el-row>
                      <span style="color: gray">{{ child.createTime }}</span>
                      <svg-icon icon-class="favour" style="margin-left: 20px"/>
                      0
                      <svg-icon icon-class="dis-favour" style="margin-left: 20px"/>
                      <span style="color: gray; cursor: pointer; margin-left: 20px" @click="handleReplyPerson(child.id)">回复</span>
                    </el-row>
                  </el-col>
                </el-row>
              </div>
            </el-row>
          </el-col>
        </el-row>
        <el-divider></el-divider>
      </div>
    </el-card>
  </div>
</template>

<script>

import {readPosts} from "@/api/system/posts";
import store from "@/store";
import {addReply, listReply} from "@/api/system/reply";

export default {
  name: 'PostsDetail',
  data() {
    return {
      avatar: store.getters.avatar,
      form: {},
      replyList: [],
      total: 0,
      reply: '',
      replyId: null,
      replyName: ''
    }
  },
  created() {
    const postsId = this.$route.params && this.$route.params.postsId
    this.form.id = postsId
    this.getDetail(postsId)
    this.getReplyList('asc')
  },
  computed: {
    getAvatar() {
      return (path) => {
        return path === '' || path === null ? require("@/assets/images/profile.jpg") : process.env.VUE_APP_BASE_API + path
      }
    }
  },
  methods: {
    getDetail(postsId) {
      readPosts(postsId).then(res => {
        this.form = res.data
      })
    },
    getReplyList(type) {
      const form = {
        orderByColumn: 'create_time',
        isAsc: type === 'asc' ? 'asc' : 'desc',
        postsId: this.form.id
      }
      listReply(form).then(res => {
        this.replyList = res.data
        this.total = res.data.length
      })
    },
    handleReply(replyId) {
      const form = {
        content: this.reply,
        postsId: this.form.id,
        replyId: this.replyId
      }
      addReply(form).then(res => {
        if (res.code === 200) {
          this.reply = ''
          this.replyId = null
          this.replyName = ''
          this.$message.success('回复成功！')
        } else {
          this.$message.error('回复失败！')
        }
      }).finally(() => {
        this.getReplyList('des')
      })
    },
    handleReplyPerson(item) {
      this.replyId = item.id
      this.replyName = item.user.nickName || item.user.userName
    }
  }
}
</script>

<style lang="scss" scoped>
::v-deep img {
  width: auto;
  max-height: 500px;
}

.el-row {
  padding-top: 10px;
  padding-bottom: 10px;
}
</style>
