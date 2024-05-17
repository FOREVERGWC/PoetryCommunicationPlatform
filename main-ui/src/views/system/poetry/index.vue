<template>
  <div class="app-container">
    <el-form v-show="showSearch" ref="queryForm" :inline="true" :model="queryParams" label-width="68px" size="small">
      <el-form-item label="标题" prop="title">
        <el-input
          v-model="queryParams.title"
          clearable
          placeholder="请输入标题"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="作者" prop="author">
        <el-input
          v-model="queryParams.author"
          clearable
          placeholder="请输入作者"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="朝代" prop="dynasty">
        <el-select v-model="queryParams.dynasty" clearable placeholder="请选择朝代">
          <el-option
            v-for="dict in dict.type.biz_poetry_dynasty"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="诗句" prop="content">
        <el-input
          v-model="queryParams.content"
          clearable
          placeholder="请输入诗句"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button icon="el-icon-search" size="mini" type="primary" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['system:poetry:add']"
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
          v-hasPermi="['system:poetry:edit']"
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
          v-hasPermi="['system:poetry:remove']"
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
          v-hasPermi="['system:poetry:export']"
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

    <el-table v-if="isAdmin" v-loading="loading" :data="poetryList" @selection-change="handleSelectionChange">
      <el-table-column align="center" type="selection" width="55"/>
      <el-table-column align="center" label="标题">
        <template v-slot="{row}">
          <router-link :to="`/biz/poetry-detail/index/${row.id}`">
            <el-link :underline="false" type="primary">《{{ row.title }}》</el-link>
          </router-link>
        </template>
      </el-table-column>
      <!--      <el-table-column align="center" label="内容" prop="content"/>-->
      <el-table-column align="center" label="作者" prop="author"/>
      <el-table-column align="center" label="朝代" prop="dynasty">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.biz_poetry_dynasty" :value="scope.row.dynasty"/>
        </template>
      </el-table-column>
      <!--      <el-table-column align="center" label="朗诵" prop="recitation"/>-->
      <!--      <el-table-column align="center" label="视频" prop="video"/>-->
      <!--      <el-table-column align="center" label="图片" prop="imgs"/>-->
      <el-table-column align="center" label="点击量" prop="click"/>
      <el-table-column align="center" label="评论量">
        <template v-slot="{row}">
          <span>{{ row.bizPoetryCommentList.length }}</span>
        </template>
      </el-table-column>
      <el-table-column v-if="isAdmin" align="center" label="备注" prop="remark"/>
      <el-table-column v-if="isAdmin" align="center" class-name="small-padding fixed-width" label="操作">
        <template slot-scope="scope">
          <el-button
            v-hasPermi="['system:poetry:edit']"
            icon="el-icon-edit"
            size="mini"
            type="text"
            @click="handleUpdate(scope.row)"
          >修改
          </el-button>
          <el-button
            v-hasPermi="['system:poetry:remove']"
            icon="el-icon-delete"
            size="mini"
            type="text"
            @click="handleDelete(scope.row)"
          >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-card v-for="item in poetryList" v-else :key="item.id" shadow="hover">
      <el-row>
        <router-link :to="`/biz/poetry-detail/index/${item.id}`">
          <el-link :underline="false">
            <h2 style="font-weight: bold">《{{ item.title }}》</h2>
          </el-link>
        </router-link>
      </el-row>
      <el-row>
        <el-tag>
          <svg-icon icon-class="author"/>
          {{ item.author }}
        </el-tag>
        <el-tag type="success">
          <dict-tag :options="dict.type.biz_poetry_dynasty" :value="item.dynasty"/>
        </el-tag>
        <el-tag type="info">
          <svg-icon icon-class="eye-open"/>
          {{ item.click }}
        </el-tag>
        <el-tag>
          <i class="el-icon-bell"></i>
          {{ item.bizPoetryCommentList.length }}
        </el-tag>
        <el-tag type="warning">
          <i class="el-icon-time"/>
          {{ item.createTime }}
        </el-tag>
      </el-row>
      <el-row>
        <span v-html="item.content"></span>
      </el-row>
    </el-card>

    <pagination
      v-show="total>0"
      :limit.sync="queryParams.pageSize"
      :page.sync="queryParams.pageNum"
      :total="total"
      @pagination="getList"
    />

    <!-- 添加或修改诗词对话框 -->
    <el-dialog :title="title" :visible.sync="open" append-to-body width="1200px">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入内容" type="textarea"/>
        </el-form-item>
        <el-form-item label="内容">
          <editor v-model="form.content" :min-height="192"/>
        </el-form-item>
        <el-form-item label="作者" prop="author">
          <el-input v-model="form.author" placeholder="请输入作者"/>
        </el-form-item>
        <el-form-item label="朝代" prop="dynasty">
          <el-select v-model="form.dynasty" placeholder="请选择朝代">
            <el-option
              v-for="dict in dict.type.biz_poetry_dynasty"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="朗诵" prop="recitation">
          <recitation-upload v-model="form.recitation" :file-type="['mp3']"></recitation-upload>
        </el-form-item>
        <el-form-item label="视频" prop="video">
          <video-upload v-model="form.video" :file-type="['mp4']"></video-upload>
        </el-form-item>
        <el-form-item label="图片" prop="imgs">
          <image-upload v-model="form.imgs"></image-upload>
          <!--          <file-upload v-model="form.imgs" :file-type="['png', 'jpg']"></file-upload>-->
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入内容" type="textarea"/>
        </el-form-item>
        <div v-if="form.id">
          <el-divider content-position="center">诗词评论信息</el-divider>
          <el-row :gutter="10" class="mb8">
            <!--            <el-col v-if="this.$auth.hasRole('admin')" :span="1.5">-->
            <!--              <el-button icon="el-icon-plus" size="mini" type="primary" @click="handleAddBizPoetryComment">添加</el-button>-->
            <!--            </el-col>-->
            <el-col :span="1.5">
              <el-button icon="el-icon-delete" size="mini" type="danger" @click="handleDeleteBizPoetryComment">删除</el-button>
            </el-col>
          </el-row>
          <el-table ref="bizPoetryComment" :data="bizPoetryCommentList" :row-class-name="rowBizPoetryCommentIndex" @selection-change="handleBizPoetryCommentSelectionChange">
            <el-table-column align="center" type="selection" width="50"/>
            <el-table-column align="center" label="序号" prop="index" width="50"/>
            <el-table-column label="用户ID" prop="userId" width="150">
              <template slot-scope="scope">
                <el-input v-model="scope.row.userId" disabled placeholder="请输入用户ID"/>
              </template>
            </el-table-column>
            <el-table-column align="center" label="内容" prop="content">
              <template slot-scope="scope">
                <editor v-model="scope.row.content" :min-height="192"/>
              </template>
            </el-table-column>
            <el-table-column label="点赞量" prop="favor" width="150">
              <template slot-scope="scope">
                <el-input v-model="scope.row.favor" disabled placeholder="请输入点赞量"/>
              </template>
            </el-table-column>
            <!--            <el-table-column label="创建者" prop="createBy" width="150">-->
            <!--              <template slot-scope="scope">-->
            <!--                <el-input v-model="scope.row.createBy" placeholder="请输入创建者"/>-->
            <!--              </template>-->
            <!--            </el-table-column>-->
            <el-table-column label="创建时间" prop="createTime" width="240">
              <template slot-scope="scope">
                <el-date-picker v-model="scope.row.createTime" clearable disabled placeholder="请选择创建时间" type="date" value-format="yyyy-MM-dd"/>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {addPoetry, delPoetry, getPoetry, listPoetry, updatePoetry} from "@/api/system/poetry";

export default {
  name: "Poetry",
  dicts: ['biz_poetry_dynasty'],
  data() {
    return {
      isAdmin: this.$auth.hasRole('admin'),
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 子表选中数据
      checkedBizPoetryComment: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 诗词表格数据
      poetryList: [],
      // 诗词评论表格数据
      bizPoetryCommentList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 更新时间时间范围
      daterangeCreateTime: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        title: null,
        content: null,
        author: null,
        dynasty: null,
        remark: ''
      },
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
        author: [
          {required: true, message: "作者不能为空", trigger: "blur"}
        ],
        dynasty: [
          {required: true, message: "朝代不能为空", trigger: "change"}
        ]
      }
    };
  },
  created() {
    this.getList();
    console.log(this.$router)
  },
  computed: {},
  methods: {
    /** 查询诗词列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeCreateTime && '' != this.daterangeCreateTime) {
        this.queryParams.params["beginCreateTime"] = this.daterangeCreateTime[0];
        this.queryParams.params["endCreateTime"] = this.daterangeCreateTime[1];
      }
      listPoetry(this.queryParams).then(response => {
        this.poetryList = response.rows;
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
        title: null,
        content: null,
        author: null,
        dynasty: null,
        recitation: null,
        video: null,
        imgs: '',
        click: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: ''
      };
      this.bizPoetryCommentList = [];
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
      this.title = "添加诗词";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getPoetry(id).then(response => {
        this.form = response.data;
        this.bizPoetryCommentList = response.data.bizPoetryCommentList;
        this.open = true;
        this.title = "修改诗词";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.bizPoetryCommentList = this.bizPoetryCommentList;
          if (this.form.id != null) {
            updatePoetry(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addPoetry(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除诗词编号为"' + ids + '"的数据项？').then(function () {
        return delPoetry(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 诗词评论序号 */
    rowBizPoetryCommentIndex({row, rowIndex}) {
      row.index = rowIndex + 1;
    },
    /** 诗词评论添加按钮操作 */
    handleAddBizPoetryComment() {
      let obj = {};
      obj.content = "";
      obj.userId = "";
      obj.favor = "";
      obj.createBy = "";
      obj.createTime = "";
      this.bizPoetryCommentList.push(obj);
    },
    /** 诗词评论删除按钮操作 */
    handleDeleteBizPoetryComment() {
      if (this.checkedBizPoetryComment.length == 0) {
        this.$modal.msgError("请先选择要删除的诗词评论数据");
      } else {
        const bizPoetryCommentList = this.bizPoetryCommentList;
        const checkedBizPoetryComment = this.checkedBizPoetryComment;
        this.bizPoetryCommentList = bizPoetryCommentList.filter(function (item) {
          return checkedBizPoetryComment.indexOf(item.index) == -1
        });
      }
    },
    /** 复选框选中数据 */
    handleBizPoetryCommentSelectionChange(selection) {
      this.checkedBizPoetryComment = selection.map(item => item.index)
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/poetry/export', {
        ...this.queryParams
      }, `poetry_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>

<style lang="scss" scoped>
.el-select {
  width: 100%;
}

.el-card {
  margin-bottom: 20px;
}

.el-tag {
  margin-right: 10px;
}

.el-icon-time, .el-icon-bell, .svg-icon {
  margin-right: 5px;
}
</style>
