<template>
  <div>
    <el-card shadow="hover" style="margin: 20px">{{ info.title }}</el-card>
    <el-card style="margin-left: 20px; margin-right: 20px">
      <el-row>
        <el-col :span="2">
          <el-row style="align-items: center">
            <el-avatar :src="avatar" shape="square" style="border: 1px solid gray"></el-avatar>
          </el-row>
          <el-row style="align-items: center; margin-top: 5px">
            <span>{{ info.createBy }}</span>
          </el-row>
        </el-col>
        <el-col :span="22">
          <span v-html="info.content"></span>
          <el-row>
            <span style="color: gray; float: right">1楼&emsp;{{ info.createTime }}</span>
          </el-row>
        </el-col>
      </el-row>
    </el-card>
    <el-card shadow="always" style="margin-top: 20px; margin-left: 20px; margin-right: 20px">
      <div style="display: flex; align-items: flex-end; margin-bottom: 20px">
        <h1 style="display: inline-block; margin: 0;">回复</h1>
        <div class="total" style="display: inline-block; margin-left: 15px">{{ total }}</div>
      </div>
      <span :class="sort === 'asc' ? 'sort' : ''" style="cursor: pointer" @click="getReplyList('asc')">正序</span>
      <el-divider direction="vertical"></el-divider>
      <span :class="sort === 'desc' ? 'sort' : ''" style="cursor: pointer" @click="getReplyList('desc')">倒序</span>
      <el-row>
        <el-col :span="1">
          <el-avatar :src="avatar" style="border: 1px solid gray"></el-avatar>
        </el-col>
        <el-col :span="23">
          <span v-if="replyId" style="padding-top: 5px; padding-bottom: 5px">回复<span style="color: #66ccff">@{{ replyName }}</span></span>
          <el-form ref="info" :model="form" :rules="rules">
            <el-form-item prop="reply">
              <el-input v-model="form.reply" placeholder="一时的嘴臭，极致的享受" type="textarea"></el-input>
            </el-form-item>
          </el-form>
        </el-col>
      </el-row>
      <el-row>
        <el-button style="float: right" type="primary" @click="handleReply">发布</el-button>
      </el-row>
      <Reply :comments="replyList" :handle-reply-person="handleReplyPerson"></Reply>
    </el-card>
  </div>
</template>

<script>

import {readPosts} from "@/api/system/posts";
import store from "@/store";
import {addReply, listReply} from "@/api/system/reply";
import Reply from "@/views/system/posts/components/reply";

export default {
  name: 'PostsDetail',
  components: {Reply},
  data() {
    return {
      avatar: store.getters.avatar,
      info: {},
      replyList: [],
      total: 0,
      replyId: null,
      replyName: '',
      sort: 'asc',
      form: {},
      rules: {
        reply: [{required: true, message: '回复内容不能为空', trigger: 'blur'}]
      }
    }
  },
  created() {
    const postsId = this.$route.params && this.$route.params.postsId
    this.info.id = postsId
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
        this.info = res.data
      })
    },
    getReplyList(type) {
      const form = {
        orderByColumn: 'create_time',
        isAsc: type === 'asc' ? 'asc' : 'desc',
        postsId: this.info.id
      }
      this.sort = type
      listReply(form).then(res => {
        this.replyList = res.rows
        this.total = res.title
      })
    },
    handleReply() {
      this.$refs.info.validate(valid => {
        if (!valid) return
        const form = {
          content: this.form.reply,
          postsId: this.info.id,
          replyId: this.replyId
        }
        addReply(form).then(res => {
          if (res.code === 200) {
            this.form.reply = ''
            this.replyId = null
            this.replyName = ''
            this.$message.success('回复成功！')
          } else {
            this.$message.error('回复失败！')
          }
        }).finally(() => {
          this.getReplyList('desc')
        })
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

::v-deep .sort {
  cursor: pointer;
  font-weight: bold;
}
</style>
