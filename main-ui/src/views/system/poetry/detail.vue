<template>
  <div>
    <el-tabs v-model="activeName" type="card">
      <el-tab-pane label="详情" name="first">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-card shadow="hover" style="margin-left: 20px">
              <el-row>
                <h2 style="font-weight: bold">{{ form.title }}</h2>
              </el-row>
              <el-row>
                <el-tag>
                  <svg-icon icon-class="author"/>
                  {{ form.author }}
                </el-tag>
                <el-tag type="success">【{{ getDynasty(form.dynasty) }}】</el-tag>
                <el-tag type="info">
                  <svg-icon icon-class="eye-open"/>
                  {{ form.click }}
                </el-tag>
                <el-tag type="warning">
                  <i class="el-icon-time"/>
                  {{ form.createTime }}
                </el-tag>
              </el-row>
              <span v-html="form.content"></span>
              <el-tooltip :content="favorite ? '取消收藏' : '收藏'" effect="light" placement="right">
                <svg-icon :class="favorite ? 'favorite-select' : 'favorite'" :icon-class="favorite ? 'favorite-select' : 'favorite'" @click="handleFavorite"/>
              </el-tooltip>
            </el-card>
          </el-col>
          <el-col :span="12">
            <el-card shadow="always" style="margin-right: 20px">
              <el-carousel>
                <el-carousel-item v-for="item in imgs" :key="item">
                  <image-preview :src="item"></image-preview>
                </el-carousel-item>
              </el-carousel>
            </el-card>
          </el-col>
        </el-row>
      </el-tab-pane>
      <el-tab-pane label="音频" name="second">
        <vue-audio :audio-source="getUrl(form.recitation)"></vue-audio>
      </el-tab-pane>
      <el-tab-pane label="视频" name="third">
        <el-card style="margin-top: 10px; margin-left: 20px; margin-right: 20px">
          <video-player :src="getUrl(form.video)"></video-player>
        </el-card>
      </el-tab-pane>
    </el-tabs>
    <el-card shadow="always" style="margin-top: 20px; margin-left: 20px; margin-right: 20px">
      <div style="display: flex; align-items: flex-end; margin-bottom: 20px">
        <h1 style="display: inline-block; margin: 0;">评论</h1>
        <div class="total" style="display: inline-block; margin-left: 15px">{{ total }}</div>
      </div>
      <span style="cursor: pointer" @click="getCommentList('hot')">最热</span>
      <el-divider direction="vertical"></el-divider>
      <span style="cursor: pointer" @click="getCommentList('new')">最新</span>
      <el-row>
        <el-col :span="1">
          <el-avatar :src="avatar" style="border: 1px solid gray"></el-avatar>
        </el-col>
        <el-col :span="23">
          <el-input v-model="comment" placeholder="尊重是评论打动人心的入场券" type="textarea"></el-input>
        </el-col>
      </el-row>
      <el-row>
        <el-button style="float: right" type="primary" @click="handleComment">发布</el-button>
      </el-row>
      <div v-for="item in commentList" :key="item.id">
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
              {{ item.favor }}
              <svg-icon icon-class="dis-favour" style="margin-left: 20px"/>
              <span style="color: gray; cursor: pointer; margin-left: 20px">回复</span>
            </el-row>
          </el-col>
        </el-row>
        <el-divider></el-divider>
      </div>
    </el-card>
  </div>
</template>

<script>
import {readPoetry} from "@/api/system/poetry";
import {doFavorite, getFavoriteByPoetryId} from "@/api/system/favorite";
import {addComment, listComment} from "@/api/system/comment";
import store from "@/store";

export default {
  name: "PoetryDetail",
  dicts: ['biz_poetry_dynasty'],
  data() {
    return {
      avatar: store.getters.avatar,
      form: {},
      imgs: [],
      favorite: {},
      commentList: [],
      total: 0,
      activeName: 'first',
      comment: ''
    }
  },
  created() {
    const poetryId = this.$route.params && this.$route.params.poetryId
    this.getDetail(poetryId)
    this.getFavorite(poetryId)
  },
  computed: {
    getUrl() {
      return (path) => {
        return process.env.VUE_APP_BASE_API + path
      }
    },
    getAvatar() {
      return (path) => {
        return path === '' || path === null ? require("@/assets/images/profile.jpg") : process.env.VUE_APP_BASE_API + path
      }
    }
  },
  methods: {
    getDetail(poetryId) {
      readPoetry(poetryId).then(res => {
        this.form = res.data
        this.imgs = res.data.imgs.split(',')
        this.commentList = res.data.bizPoetryCommentList
        this.total = this.commentList.length
      })
    },
    getDynasty(val) {
      return this.selectDictLabel(this.dict.type.biz_poetry_dynasty, val)
    },
    getFavorite(poetryId) {
      getFavoriteByPoetryId(poetryId).then(res => {
        this.favorite = res.data
      })
    },
    getCommentList(type) {
      const form = {
        orderByColumn: type ? (type === 'hot' ? 'favor' : 'create_time') : '',
        isAsc: type ? 'desc' : '',
        poetryId: this.form.id
      }
      listComment(form).then(res => {
        this.commentList = res.rows
        this.total = res.total
      })
    },
    handleFavorite() {
      doFavorite(this.form.id).then(res => {
        this.favorite = res.data
        if (res.data) {
          this.$message.success('收藏成功！')
        } else {
          this.$message.success('取消成功！')
        }
      })
    },
    handleComment() {
      const form = {
        content: this.comment,
        poetryId: this.form.id
      }
      addComment(form).then(res => {
        if (res.code === 200) {
          this.comment = ''
          this.$message.success('评论成功！')
        } else {
          this.$message.error('评论失败！')
        }
      }).finally(() => {
        this.getCommentList('new')
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.el-row {
  padding-top: 10px;
  padding-bottom: 10px;
}

.el-tag {
  margin-right: 10px;
}

.el-icon-time, .svg-icon {
  margin-right: 5px;
}

.favorite, .favorite-select {
  cursor: pointer;
}

.favorite:hover {
  background-color: gray;
}

.favorite-select:hover {
  background-color: white;
}
</style>
