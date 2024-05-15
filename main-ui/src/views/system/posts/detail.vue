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
  </div>
</template>

<script>

import {readPosts} from "@/api/system/posts";

export default {
  name: 'PostsDetail',
  data() {
    return {
      form: {}
    }
  },
  created() {
    const postsId = this.$route.params && this.$route.params.postsId
    this.getDetail(postsId)
  },
  methods: {
    getDetail(postsId) {
      readPosts(postsId).then(res => {
        this.form = res.data
      })
    }
  }
}
</script>

<style lang="scss" scoped>
::v-deep img {
  width: auto;
  max-height: 500px;
}
</style>
