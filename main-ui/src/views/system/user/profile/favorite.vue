<template>
  <div>
    <el-table v-loading="loading" :data="favoriteList" @selection-change="handleSelectionChange">
      <el-table-column align="center" type="selection" width="55"/>
      <el-table-column align="center" label="诗词" prop="poetryId">
        <template v-slot="{row}">
          <router-link :to="`/biz/poetry-detail/index/${row.poetryId}`">
            <el-link :underline="false" type="primary">《{{ row.poetryTitle }}》</el-link>
          </router-link>
        </template>
      </el-table-column>
      <el-table-column align="center" label="收藏时间" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" class-name="small-padding fixed-width" label="操作">
        <template slot-scope="scope">
          <el-button
            v-hasPermi="['system:favorite:remove']"
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
import {delFavorite, listFavorite} from "@/api/system/favorite";

export default {
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
      // 用户诗词收藏表格数据
      favoriteList: [],
      // 查询参数
      queryParams: {
        userId: this.$store.state.user.id,
      }
    }
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询用户诗词收藏列表 */
    getList() {
      this.loading = true;
      listFavorite(this.queryParams).then(response => {
        this.favoriteList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
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
      this.$modal.confirm('是否确认删除用户诗词收藏编号为"' + ids + '"的数据项？').then(function () {
        return delFavorite(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
  }
}
</script>

<style lang="scss" scoped>

</style>
