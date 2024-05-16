<template>
  <div>
    <el-card shadow="hover" style="margin: 20px">{{ form.noticeTitle }}</el-card>
    <el-card style="margin-left: 20px; margin-right: 20px">
      <span v-html="form.noticeContent"></span>
    </el-card>
  </div>
</template>

<script>

import {getNotice} from "@/api/system/notice";

export default {
  name: 'NoticeDetail',
  data() {
    return {
      form: {}
    }
  },
  created() {
    const noticeId = this.$route.params && this.$route.params.noticeId
    this.getDetail(noticeId)
  },
  methods: {
    getDetail(noticeId) {
      getNotice(noticeId).then(res => {
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
