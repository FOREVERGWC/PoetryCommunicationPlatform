<template>
  <el-form ref="form" :model="form" :rules="rules" label-width="80px">
    <el-form-item label="标题" prop="title">
      <el-input v-model="form.title" placeholder="请输入内容" type="textarea"/>
    </el-form-item>
    <el-form-item label="内容">
      <editor v-model="form.content" :min-height="192"/>
    </el-form-item>
    <el-form-item v-if="form.id" label="状态" prop="status">
      <el-select v-model="form.status" placeholder="请选择状态">
        <el-option
          v-for="dict in dict.type.biz_posts_status"
          :key="dict.value"
          :label="dict.label"
          :value="dict.value"
        ></el-option>
      </el-select>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="submitForm">确 定</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
import {addPosts} from "@/api/system/posts";

export default {
  dicts: ['biz_posts_status'],
  data() {
    return {
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        title: [
          {required: true, message: "标题不能为空", trigger: "blur"}
        ],
        content: [
          {required: true, message: "内容不能为空", trigger: "blur"}
        ],
        status: [
          {required: true, message: "状态不能为空", trigger: "change"}
        ]
      }
    }
  },
  methods: {
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          addPosts(this.form).then(response => {
            this.$modal.msgSuccess("新增成功")
            this.form = {}
            this.$router.push('/biz/posts')
          })
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>

</style>
