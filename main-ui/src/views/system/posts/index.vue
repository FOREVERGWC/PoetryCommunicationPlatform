<template>
  <div class="app-container">
    <el-form v-show="showSearch" ref="queryForm" :inline="true" :model="queryParams" label-width="68px" size="small">
      <el-form-item v-has-role="['admin']" label="主键ID" prop="id">
        <el-input
          v-model="queryParams.id"
          clearable
          placeholder="请输入主键ID"
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
      <el-form-item label="标题" prop="title">
        <el-input
          v-model="queryParams.title"
          clearable
          placeholder="请输入帖子标题"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <!--      <el-form-item label="点击量" prop="click">-->
      <!--        <el-input-->
      <!--          v-model="queryParams.click"-->
      <!--          clearable-->
      <!--          placeholder="请输入点击量"-->
      <!--          @keyup.enter.native="handleQuery"-->
      <!--        />-->
      <!--      </el-form-item>-->
      <el-form-item v-has-role="['admin']" label="状态" prop="status">
        <el-select v-model="queryParams.status" clearable placeholder="请选择状态">
          <el-option
            v-for="dict in dict.type.biz_posts_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <!--      <el-form-item label="逻辑删除" prop="delFlag">-->
      <!--        <el-input-->
      <!--          v-model="queryParams.delFlag"-->
      <!--          placeholder="请输入逻辑删除"-->
      <!--          clearable-->
      <!--          @keyup.enter.native="handleQuery"-->
      <!--        />-->
      <!--      </el-form-item>-->
      <el-form-item>
        <el-button icon="el-icon-search" size="mini" type="primary" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['system:posts:add']"
          icon="el-icon-plus"
          plain
          size="mini"
          type="primary"
          @click="handleAdd"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          v-has-role="['admin']"
          v-hasPermi="['system:posts:edit']"
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
          v-has-role="['admin']"
          v-hasPermi="['system:posts:remove']"
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
          v-hasPermi="['system:posts:export']"
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

    <el-table v-if="isAdmin" v-loading="loading" :data="postsList" @selection-change="handleSelectionChange">
      <el-table-column align="center" type="selection" width="55"/>
      <!--      <el-table-column label="主键ID" align="center" prop="id" />-->
      <!--      <el-table-column label="用户ID" align="center" prop="userId" />-->
      <el-table-column align="center" label="标题" prop="title">
        <template v-slot="{row}">
          <router-link :to="`/biz/posts-detail/index/${row.id}`">
            <el-link :underline="false" type="primary">{{ row.title }}</el-link>
          </router-link>
        </template>
      </el-table-column>
      <el-table-column align="center" label="内容" prop="content">
        <template v-slot="{row}">
          <span v-html="row.content"></span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="点击量" prop="click"/>
      <el-table-column v-if="isAdmin" align="center" label="状态" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.biz_posts_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column align="center" label="创建者" prop="createBy"/>
      <el-table-column align="center" label="创建时间" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" class-name="small-padding fixed-width" label="操作">
        <template slot-scope="scope">
          <el-button
            v-if="scope.row.createBy === username || isAdmin"
            v-hasPermi="['system:posts:edit']"
            icon="el-icon-edit"
            size="mini"
            type="text"
            @click="handleUpdate(scope.row)"
          >修改
          </el-button>
          <el-button
            v-if="scope.row.createBy === username || isAdmin"
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

    <el-card v-for="item in postsList" v-else :key="item.id" shadow="hover" style="margin-bottom: 20px">
      <el-row>
        <el-col :span="1">
          <i class="el-icon-s-opportunity"></i>
          <el-badge :value="item.click" class="item" type="primary"></el-badge>
        </el-col>
        <el-col :span="18">
          <router-link :to="`/biz/posts-detail/index/${item.id}`">
            <span style="font-size: 16px; font-weight: lighter; color: #1890ff">{{ item.title }}</span>
          </router-link>
          <span class="content" v-html="item.content"></span>
        </el-col>
        <el-col :span="5">
          <el-row>
            <el-col :span="12">
              <i class="el-icon-user"></i>{{ item.createBy }}
            </el-col>
            <el-col :span="12">{{ item.createTime }}</el-col>
          </el-row>
        </el-col>
      </el-row>
    </el-card>

    <pagination
      v-show="total>0"
      :limit.sync="queryParams.pageSize"
      :page.sync="queryParams.pageNum"
      :total="total"
      @pagination="getList"
    />

    <!-- 添加或修改帖子对话框 -->
    <el-dialog :title="title" :visible.sync="open" append-to-body width="900px">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <!--        <el-form-item label="用户ID" prop="userId">-->
        <!--          <el-input v-model="form.userId" placeholder="请输入用户ID"/>-->
        <!--        </el-form-item>-->
        <el-form-item label="标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入内容" type="textarea"/>
        </el-form-item>
        <el-form-item label="内容">
          <editor v-model="form.content" :min-height="192"/>
        </el-form-item>
        <!--        <el-form-item label="点击量" prop="click">-->
        <!--          <el-input v-model="form.click" placeholder="请输入点击量"/>-->
        <!--        </el-form-item>-->
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
        <!--        <el-form-item label="逻辑删除" prop="delFlag">-->
        <!--          <el-input v-model="form.delFlag" placeholder="请输入逻辑删除"/>-->
        <!--        </el-form-item>-->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {addPosts, delPosts, getPosts, listPosts, updatePosts} from "@/api/system/posts";
import store from "@/store";

export default {
  name: "Posts",
  dicts: ['biz_posts_status'],
  data() {
    return {
      username: store.getters.name,
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
      // 帖子表格数据
      postsList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 逻辑删除时间范围
      daterangeCreateTime: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        id: null,
        userId: null,
        title: null,
        content: null,
        click: null,
        status: this.$auth.hasRole('admin') ? '' : '0',
        delFlag: null,
        orderByColumn: 'create_time',
        isAsc: 'desc'
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        userId: [
          {required: true, message: "用户ID不能为空", trigger: "blur"}
        ],
        title: [
          {required: true, message: "标题不能为空", trigger: "blur"}
        ],
        content: [
          {required: true, message: "内容不能为空", trigger: "blur"}
        ],
        click: [
          {required: true, message: "点击量不能为空", trigger: "blur"}
        ],
        status: [
          {required: true, message: "状态不能为空", trigger: "change"}
        ],
        createBy: [
          {required: true, message: "创建者不能为空", trigger: "blur"}
        ],
        createTime: [
          {required: true, message: "创建时间不能为空", trigger: "blur"}
        ],
        delFlag: [
          {required: true, message: "逻辑删除不能为空", trigger: "blur"}
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询帖子列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeCreateTime && '' !== this.daterangeCreateTime) {
        this.queryParams.params["beginCreateTime"] = this.daterangeCreateTime[0];
        this.queryParams.params["endCreateTime"] = this.daterangeCreateTime[1];
      }
      listPosts(this.queryParams).then(response => {
        this.postsList = response.rows;
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
        userId: null,
        title: null,
        content: null,
        click: null,
        status: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        delFlag: null
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
      this.daterangeCreateTime = [];
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
      this.title = "添加帖子";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getPosts(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改帖子";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updatePosts(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addPosts(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除帖子编号为"' + ids + '"的数据项？').then(function () {
        return delPosts(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/posts/export', {
        ...this.queryParams
      }, `posts_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>

<style lang="scss" scoped>
::v-deep .el-select {
  width: 100%;
}

::v-deep .el-col .content img {
  max-height: 100px;
  width: auto;
}

::v-deep .el-table img {
  max-height: 60px;
  width: auto;
}
</style>
