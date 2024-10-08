<template>
  <div class="app-container">
    <el-form v-show="showSearch" ref="queryForm" :inline="true" :model="queryParams" label-width="68px" size="small">
      <el-form-item label="诗词ID" prop="poetryId">
        <el-input
          v-model="queryParams.poetryId"
          clearable
          placeholder="请输入诗词ID"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item v-has-role="['admin']" label="用户ID" prop="userId">
        <el-input
          v-model="queryParams.userId"
          clearable
          placeholder="请输入用户ID"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button icon="el-icon-search" size="mini" type="primary" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          v-has-role="['admin']"-->
<!--          v-hasPermi="['system:favorite:add']"-->
<!--          icon="el-icon-plus"-->
<!--          plain-->
<!--          size="mini"-->
<!--          type="primary"-->
<!--          @click="handleAdd"-->
<!--        >新增-->
<!--        </el-button>-->
<!--      </el-col>-->
      <el-col :span="1.5">
        <el-button
          v-has-role="['admin']"
          v-hasPermi="['system:favorite:edit']"
          :disabled="single"
          icon="el-icon-edit"
          plain
          size="mini"
          type="success"
          @click="handleUpdate"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['system:favorite:remove']"
          :disabled="multiple"
          icon="el-icon-delete"
          plain
          size="mini"
          type="danger"
          @click="handleDelete"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['system:favorite:export']"
          icon="el-icon-download"
          plain
          size="mini"
          type="warning"
          @click="handleExport"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="favoriteList" @selection-change="handleSelectionChange">
      <el-table-column align="center" type="selection" width="55"/>
      <!--      <el-table-column label="主键ID" align="center" prop="id" />-->
      <el-table-column align="center" label="诗词" prop="poetryId">
        <template v-slot="{row}">
          <router-link :to="`/biz/poetry-detail/index/${row.poetryId}`">
            <el-link :underline="false" type="primary">《{{ row.poetryTitle }}》</el-link>
          </router-link>
        </template>
      </el-table-column>
      <el-table-column align="center" label="创建者" prop="createBy" v-if="isAdmin"/>
      <el-table-column align="center" label="创建时间" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" class-name="small-padding fixed-width" label="操作">
        <template slot-scope="scope">
          <el-button
            v-has-role="['admin']"
            v-hasPermi="['system:favorite:edit']"
            icon="el-icon-edit"
            size="mini"
            type="text"
            @click="handleUpdate(scope.row)"
          >修改
          </el-button>
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

    <pagination
      v-show="total>0"
      :limit.sync="queryParams.pageSize"
      :page.sync="queryParams.pageNum"
      :total="total"
      @pagination="getList"
    />

    <!-- 添加或修改用户诗词收藏对话框 -->
    <el-dialog :title="title" :visible.sync="open" append-to-body width="500px">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="诗词ID" prop="poetryId">
          <el-input v-model="form.poetryId" placeholder="请输入诗词ID"/>
        </el-form-item>
        <el-form-item label="用户ID" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入用户ID"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {addFavorite, delFavorite, getFavorite, listFavorite, updateFavorite} from "@/api/system/favorite";

export default {
  name: "Favorite",
  data() {
    return {
      isAdmin: this.$auth.hasRole('admin'),
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 用户诗词收藏表格数据
      favoriteList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        poetryId: null,
        userId: this.$auth.hasRole('admin') ? null : this.$store.state.user.id
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        poetryId: [
          {required: true, message: "诗词ID不能为空", trigger: "blur"}
        ],
        userId: [
          {required: true, message: "用户ID不能为空", trigger: "blur"}
        ],
        createBy: [
          {required: true, message: "创建者不能为空", trigger: "blur"}
        ],
        createTime: [
          {required: true, message: "创建时间不能为空", trigger: "blur"}
        ]
      }
    };
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
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        poetryId: null,
        userId: this.$auth.hasRole('admin') ? null : this.$store.state.user.id,
        createBy: null,
        createTime: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加用户诗词收藏";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getFavorite(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改用户诗词收藏";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateFavorite(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addFavorite(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
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
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/favorite/export', {
        ...this.queryParams
      }, `favorite_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
