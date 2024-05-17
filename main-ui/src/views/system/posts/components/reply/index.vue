<template>
  <div>
    <el-row v-for="(item, index) in comments" :key="item.id">
      <el-col :span="1">
        <el-avatar :src="getAvatar(item.user.avatar)" shape="square" style="border: 1px solid gray"></el-avatar>
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
              来自<span style="color: #00afff; cursor: pointer">{{ item.os }}客户端</span>
            </span>
            <span style="margin-left: 10px">
              {{ index + 2 }}楼
            </span>
          </span>
        </el-row>
        <el-row v-for="child in item.children" :key="child.id">
          <el-col :span="1">
            <el-avatar :src="getAvatar(child.user.avatar)" shape="square" style="border: 1px solid gray"></el-avatar>
          </el-col>
          <el-col :span="23">
            <span style="color: #fb7299">{{ child.user.nickName || child.user.userName }}</span>
            <el-row v-if="child.replyId !== item.id">
              <span style="margin-right: 5px">回复</span>
              <span style="color: #00afff; cursor: pointer">@{{ getItem(item.children, child.replyId) }}</span>
            </el-row>
            <el-row>{{ child.content }}</el-row>
            <el-row>
              <span style="color: gray">{{ child.createTime }}</span>
              <svg-icon icon-class="favour" style="margin-left: 20px"/>
              0
              <svg-icon icon-class="dis-favour" style="margin-left: 20px"/>
              <span style="color: gray; cursor: pointer; margin-left: 20px" @click="handleReplyPerson(child)">回复</span>
              <span style="color: gray; float: right">
                <span>
                  IP属地：{{ child.location }}
                </span>
                <span style="margin-left: 10px">
                  来自<span style="color: #00afff; cursor: pointer">{{ child.os }}客户端</span>
                </span>
              </span>
            </el-row>
          </el-col>
        </el-row>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: 'Reply',
  props: {
    comments: Array,
    handleReplyPerson: Function
  },
  data() {
    return {
      list: []
    }
  },
  computed: {
    getAvatar() {
      return (path) => {
        return path === '' || path === null ? require("@/assets/images/profile.jpg") : process.env.VUE_APP_BASE_API + path
      }
    },
    getReplyName() {
      return (list, id) => {
        return list.map(item.id === id).userName
      }
    }
  },
  methods: {
    getItem(children, targetId) {
      const item = children.find(item => item.id === targetId)
      console.log(item, targetId)
      return item?.user.nickName || item?.user.userName
    }
  }
}
</script>

<style lang="scss" scoped>
.el-row {
  padding-top: 10px;
  padding-bottom: 10px;
}
</style>
