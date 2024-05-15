<template>
  <div>
    <el-table v-loading="loading" :data="postsList" @selection-change="handleSelectionChange">
      <el-table-column align="center" type="selection" width="55"/>
      <el-table-column align="center" label="标题" prop="title">
        <template v-slot="{row}">
          <router-link :to="`/biz/posts-detail/index/${row.id}`">
            <el-link :underline="false" type="primary">{{ row.title }}</el-link>
          </router-link>
        </template>
      </el-table-column>
<!--      <el-table-column align="center" label="内容" prop="content">-->
<!--        <template v-slot="{row}">-->
<!--          <span v-html="row.content"></span>-->
<!--        </template>-->
<!--      </el-table-column>-->
      <el-table-column align="center" label="点击量" prop="click"/>
      <el-table-column v-has-role="['admin']" align="center" label="状态" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.biz_posts_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column align="center" label="发帖时间" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" class-name="small-padding fixed-width" label="操作">
        <template slot-scope="scope">
          <el-button
            v-hasPermi="['system:posts:remove']"
            icon="el-icon-delete"
            size="mini"
            type="text"
            @click="handleDelete(scope.row)"
          >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import {delPosts, listPosts} from "@/api/system/posts";

export default {
  dicts: ['biz_posts_status'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 总条数
      total: 0,
      // 帖子表格数据
      postsList: [],
      // 查询参数
      queryParams: {
        userId: this.$store.state.user.id,
        delFlag: 0
      }
    }
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询帖子列表 */
    getList() {
      this.loading = true
      listPosts(this.queryParams).then(response => {
        this.postsList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除帖子编号为"' + ids + '"的数据项？').then(() => {
        return delPosts(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      })
    }
  }
}
</script>

<style lang="scss" scoped>

</style>
