<template>
  <div>
    <el-row v-for="(item, index) in comments" :key="item.id">
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
          <span style="color: gray; float: right">
            <span>
              IP属地：{{ item.location }}
            </span>
            <span style="margin-left: 10px">
              来自<span style="color: #00afff">{{ item.os }}客户端</span>
            </span>
            <span style="margin-left: 10px">
              {{ index + 2 }}楼
            </span>
          </span>
        </el-row>
        <Reply v-if="item.children && item.children.length > 0" :comments="item.children" :get-avatar="getAvatar" :handle-reply-person="handleReplyPerson"/>
      </el-col>
    </el-row>
    <el-divider></el-divider>
  </div>
</template>

<script>
export default {
  name: 'Reply',
  props: {
    comments: Array,
    handleReplyPerson: Function
  },
  computed: {
    getAvatar() {
      return (path) => {
        return path === '' || path === null ? require("@/assets/images/profile.jpg") : process.env.VUE_APP_BASE_API + path
      }
    }
  },
}
</script>

<style lang="scss" scoped>
.el-row {
  padding-top: 10px;
  padding-bottom: 10px;
}
</style>
