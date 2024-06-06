<template>
  <!-- 唯一div -->
  <div class="search">
    <!-- 新增页面 -->
    <add
      v-if="currView == 'add'"
      :formData="formData"
      @close="currView = 'index'"
      @submited="submited"
    />
    <!-- 编辑页面 -->
    <edit
      v-if="currView == 'edit'"
      @close="currView = 'index'"
      @submited="submited"
      :data="formData"
    />
    <!-- 卡片盒子 -->
    <Card v-show="currView == 'index'">
      <!-- 上方按钮与搜索层 -->
      <Row v-show="openSearch" @keydown.enter.native="handleSearch">
        <!-- Form父级盒子 -->
        <Form ref="searchForm" :model="searchForm" inline :label-width="0">
          <!-- 样式盒子 -->
          <Form-item
            ref="searchForm"
            :model="searchForm"
            inline
            :label-width="0"
            style="display: flex"
          >
            <!-- 名称子级盒子 -->
            <Form-item label="" prop="name">
              <Input
                type="text"
                v-model="searchForm.name"
                placeholder="请输入名称"
                clearable
                style="width: 200px"
              />
            </Form-item>
            <!--分类子级盒子 -->
            <Form-item label="" prop="type">
              <Input
                type="text"
                v-model="searchForm.type"
                placeholder="请输入分类"
                clearable
                style="width: 200px"
              />
            </Form-item>
            <!-- 按钮层 -->
            <Form-item style="margin-left: 10px" class="br">
              <!-- 搜索按钮 -->
              <Button
                @click="handleSearch"
                type="primary"
                icon="ios-search"
                size="small"
                ghost
                shape="circle"
                >搜索</Button
              >
              <!-- 重置按钮 -->
              <Button
                @click="handleReset"
                type="warning"
                size="small"
                ghost
                shape="circle"
                icon="md-refresh"
                >重置</Button
              >
              <!-- 新增按钮 -->
              <Button
                @click="add"
                type="info"
                size="small"
                icon="md-add"
                ghost
                shape="circle"
                >新增</Button
              >
              <!-- 删除按钮 -->
              <Button
                @click="delAll"
                type="error"
                icon="md-trash"
                size="small"
                ghost
                shape="circle"
                >删除</Button
              >
              <Button
                @click="copyRowData"
                type="info"
                size="small"
                icon="ios-copy"
                ghost
                shape="circle"
                >复制</Button
              >

              <!-- 导出按钮 -->
              <Button
                @click="excelData"
                type="success"
                icon="md-paper-plane"
                size="small"
                ghost
                shape="circle"
                >导出</Button
              >
              <!-- 导出模版按钮 -->
              <Button
                type="success"
                icon="md-paper-plane"
                size="small"
                ghost
                shape="circle"
              >
                <a href="/excel_template.xlsx">下载模板</a>
              </Button>
              <!-- 批量上传按钮 -->
              <Button
                type="success"
                icon="md-paper-plane"
                size="small"
                ghost
                shape="circle"
                @click="selectFiles"
              >
                批量上传
              </Button>

              <!-- 隐藏的文件输入 -->
              <input
                type="file"
                ref="fileInput"
                multiple
                accept=".xls, .xlsx"
                style="display: none"
                @change="handleFilesUpload"
              />
            </Form-item>

            <!-- 右侧俩按钮栏 -->
            <Form-item style="position: fixed; right: 50px; top: 110px">
              <!-- 列筛选按钮 -->
              <Poptip
                trigger="hover"
                content="自定义表格的显示列,适应屏幕显示"
                placement="left"
              >
                <Button
                  type="info"
                  @click="showFilterPanelFlag = !showFilterPanelFlag"
                  class="showFilterPanelFlag"
                  icon="ios-settings"
                  size="small"
                  ghost
                  >列筛选
                </Button>
              </Poptip>
            </Form-item>
          </Form-item>
        </Form>
      </Row>

      <!-- 样式盒子 -->
      <Row class="operation" style="position: relative">
        <transition>
          <div v-show="showFilterPanelFlag" class="filter-panel">
            <CheckboxGroup v-model="selected">
              <div v-for="item in mycolumns" :key="item.key">
                <Checkbox
                  :label="item.title"
                  style="margin: 2px 5px"
                ></Checkbox>
              </div>
            </CheckboxGroup>
          </div>
        </transition>
      </Row>
      <!-- 样式层 -->
      <Row v-show="openTip"> </Row>
      <!-- 表格层盒子 -->
      <Row>
        <!-- 根据后台数据生成的表格 -->
        <Table
          :loading="loading"
          border
          :columns="columns"
          :data="data"
          ref="table"
          sortable="custom"
          @on-sort-change="changeSort"
          @on-selection-change="changeSelect"
          @on-row-click="rowClick"
          :row-class-name="rowClassNmae"
        ></Table>
      </Row>
      <!-- 分页栏 -->
      <Row type="flex" justify="end" class="page">
        <!-- 分页器,可控制每页显示的条数 -->
        <Page
          :current="searchForm.pageNumber"
          :total="total"
          :page-size="searchForm.pageSize"
          @on-change="changePage"
          @on-page-size-change="changePageSize"
          :page-size-opts="[10, 20, 50]"
          size="small"
          show-total
          show-elevator
          show-sizer
        ></Page>
      </Row>
    </Card>
  </div>
</template>

<script>
import {
  getAdminAssetList,
  deleteAdminAsset,
  generateQRCode,
  submitData,
  returnData,
  uploadExcelFile,
} from "./api.js";
import add from "./add.vue";
import edit from "./edit.vue";
import "viewerjs/dist/viewer.css";
import Viewer from "viewerjs";
import uploadFileInput from "@/views/template/upload-file-input";
import * as XLSX from "xlsx";

export default {
  name: "single-window",
  components: {
    add,
    edit,
    uploadFileInput,
  },
  data() {
    return {
      selected: [
        "选择",
        "序号",
        "名称",
        "类别",
        "固定资产编码",
        "购置日期",
        "序列号",
        // "采购组织形式",
        // "采购合同编码",
        // "折旧方法",
        // "累计折旧",
        // "净值",
        // "存放仓库",
        // "不含税价",
        // "含税价",
        // "供应商ID",
        // "供应商",
        // "领用部门ID",
        // "使用部门",
        // "使用人ID",
        // "使用人",
        "二维码图片",
        "物料图片",
        //"状态",
        //"备注",
        "操作",
      ],
      showUploadDialog: false, // 控制上传对话框显示
      fileList: [], // 选中的文件列表
      qrCodeImgUrl: null, // 存储二维码图片的URL
      showQRCodeModal: false, // 控制展示二维码模态框的变量

      modal1: false,
      openSearch: true, // 显示搜索
      openTip: true, // 显示提示
      formData: {},
      // 假设formData是用于存储表单数据的对象
      // formData:
      //   this.$props.formData ||
      //   {
      //     /* 默认值 */
      //   },
      currView: "index",
      loading: true, // 表单加载状态
      searchForm: {
        // 搜索框初始化对象
        pageNumber: 1, // 当前页数
        pageSize: 10, // 页面大小
        sort: "createTime", // 默认排序字段
        order: "desc", // 默认排序方式
      },
      selectList: [], // 多选数据
      selectCount: 0, // 多选计数
      selectRow: 0,
      columns: [
        // 表头
        {
          type: "selection",
          width: 60,
          title: "选择",
          align: "center",
          fixed: "left",
        },
        {
          title: "序号",
          width: 85,
          align: "center",
          fixed: "left",
          sortType: true,
          render: (h, params) => {
            return h(
              "span",
              params.index +
                (this.searchForm.pageNumber - 1) * this.searchForm.pageSize +
                1
            );
          },
        },
        {
          title: "名称",
          key: "name",
          minWidth: 120,
          sortable: false,
        },
        {
          title: "类别",
          key: "type",
          minWidth: 120,
          sortable: false,
        },
        {
          title: "固定资产编码",
          key: "code",
          minWidth: 120,
          sortable: false,
        },
        {
          title: "购置日期",
          key: "date",
          minWidth: 120,
          sortable: false,
          render: (h, params) => {
            // Assuming params.row.date contains the purchase date as a string
            const purchaseDate = params.row.date;
            // Convert the purchaseDate to a JavaScript Date object
            const dateObj = new Date(purchaseDate);
            // Format the date in your desired format (e.g., YYYY-MM-DD)
            const formattedDate = `${dateObj.getFullYear()}-${(
              dateObj.getMonth() + 1
            )
              .toString()
              .padStart(2, "0")}-${dateObj
              .getDate()
              .toString()
              .padStart(2, "0")}`;
            // Render the formatted date
            return h("div", formattedDate);
          },
        },

        {
          title: "序列号",
          key: "serialNumber",
          minWidth: 120,
          sortable: false,
        },
        {
          title: "采购组织形式",
          key: "purchasingForm",
          minWidth: 120,
          sortable: false,
        },
        {
          title: "采购合同编码",
          key: "purchaseContractCode",
          minWidth: 120,
          sortable: false,
        },
        {
          title: "折旧方法",
          key: "depreciationMethod",
          minWidth: 120,
          sortable: false,
        },
        {
          title: "累计折旧",
          key: "accumulatedDepreciation",
          minWidth: 120,
          sortable: false,
        },
        {
          title: "净值",
          key: "netValue",
          minWidth: 120,
          sortable: false,
        },
        {
          title: "存放仓库",
          key: "warehouse",
          minWidth: 120,
          sortable: false,
        },
        {
          title: "不含税价",
          key: "price",
          minWidth: 120,
          sortable: false,
        },
        {
          title: "含税价",
          key: "taxPrice",
          minWidth: 120,
          sortable: false,
        },

        {
          title: "供应商",
          key: "supplier",
          minWidth: 120,
          sortable: false,
        },

        // {
        //   title: "使用部门",
        //   key: "使用部门",
        //   minWidth: 120,
        //   sortable: false,
        // },

        // {
        //   title: "使用人",
        //   key: "使用人",
        //   minWidth: 120,
        //   sortable: false,
        // },
        {
          title: "二维码图片",
          key: "qrCode",
          minWidth: 120,
          sortable: false,
          render: (h, params) => {
            // 添加时间戳来避免缓存问题
            const qrCodeUrl = params.row.qrCode
              ? `${params.row.qrCode}?t=${new Date().getTime()}`
              : "";

            return qrCodeUrl
              ? h("img", {
                  attrs: {
                    src: qrCodeUrl,
                    style: "cursor: pointer;", // 显示可点击的光标
                  },
                  style: {
                    width: "100px", // 设定一个固定宽度
                    height: "auto", // 保持图片比例
                  },
                  on: {
                    click: () => this.viewImage(qrCodeUrl), // 绑定点击事件
                    load: () => console.log("Image loaded successfully"),
                    error: () => console.error("Image failed to load"),
                  },
                })
              : h("span", "未生成");
          },
        },

        {
          title: "物料图片",
          key: "qrCode",
          minWidth: 120,
          sortable: false,
          render: (h, params) => {
            return params.row.imageUrl
              ? h("img", {
                  attrs: {
                    src: params.row.imageUrl,
                    style: "cursor: pointer;", // 显示可点击的光标
                  },
                  style: {
                    width: "100%",
                    height: "auto", // 保持图片比例
                  },
                  on: {
                    click: () => this.viewImage(params.row.imageUrl), // 绑定点击事件
                  },
                })
              : h("span", "未生成");
          },
        },

        {
          title: "备注",
          key: "remark",
          minWidth: 120,
          sortable: false,
        },
        {
          title: "状态",
          key: "status",
          minWidth: 120,
          sortable: false,
          render: (h, params) => {
            if (params.row.status == 0) {
              return h("div", [
                h(
                  "span",
                  {
                    style: {
                      color: "#111111",
                    },
                  },
                  "未入库"
                ),
              ]);
            } else {
              return h("div", [
                h(
                  "span",
                  {
                    style: {
                      color: "#ff0000",
                    },
                  },
                  "异常"
                ),
              ]);
            }
          },
        },

        {
          title: "操作",
          key: "action",
          align: "center",
          width: 200,
          render: (h, params) => {
            var that = this;
            return h("div", [
              h(
                "Button",
                {
                  props: {
                    type: "primary",
                    size: "small",
                    ghost: true,
                    icon: "ios-create-outline",
                  },
                  style: {
                    marginRight: "5px",
                  },
                  on: {
                    click: () => {
                      this.generateAndShowQRCode(params.row);
                    },
                  },
                },
                "生成二维码图片"
              ),
              h(
                "Button",
                {
                  props: {
                    type: "primary",
                    size: "small",
                    ghost: true,
                    icon: "ios-create-outline",
                    disabled:
                      params.row.registerApprovalStatus === 1 ||
                      params.row.registerApprovalStatus === 2, // 根据条件禁用按钮
                  },
                  style: {
                    marginRight: "5px",
                  },
                  on: {
                    click: () => {
                      this.submitFx(params.row); // 确保传递当前行的数据
                    },
                  },
                },
                "申请审核"
              ),
              h(
                "Button",
                {
                  props: {
                    type: "primary",
                    size: "small",
                    ghost: true,
                    icon: "ios-create-outline",
                    disabled:
                      params.row.registerApprovalStatus !== 1 ||
                      params.row.status !== 0, // 根据条件禁用按钮
                  },
                  style: {
                    marginRight: "5px",
                  },
                  on: {
                    click: () => {
                      this.returnFx(params.row); // 确保传递当前行的数据
                    },
                  },
                },
                "撤回审核"
              ),
              h(
                "Button",
                {
                  props: {
                    type: "primary",
                    size: "small",
                    ghost: true,
                    icon: "ios-create-outline",
                    disabled:
                      params.row.registerApprovalStatus === 2 ||
                      params.row.registerApprovalStatus === 1, // 根据条件禁用按钮
                  },
                  style: {
                    marginRight: "5px",
                  },
                  on: {
                    click: () => {
                      this.edit(params.row);
                    },
                  },
                },
                "编辑"
              ),
            ]);
          },
        },
      ],
      data: [], // 表单数据
      pageNumber: 1, // 当前页数
      pageSize: 10, // 页面大小
      total: 0, // 表单数据总数
      showFilterPanelFlag: false,
    };
  },
  methods: {
    // 加载调用
    init() {
      this.getDataList();
    },
    copyRowData() {
      // 确保选中了行
      if (!this.selectRow || Object.keys(this.selectRow).length === 0) {
        this.$Message.warning("请选择一行进行复制");
        return;
      }
      // 假设您有行数据在 selectRow
      let copyData = { ...this.selectRow };

      delete copyData.id; // 删除或修改不应该直接复制的属性
      delete copyData.qrCode; // 删除或修改不应该直接复制的属性
      delete copyData.imageUrl; // 删除或修改不应该直接复制的属性
      delete copyData.status; // 删除或修改不应该直接复制的属性
      delete copyData.registerApprovalStatus; // 删除或修改不应该直接复制的属性
      delete copyData.requestApprovalStatus; // 删除或修改不应该直接复制的属性
      delete copyData.repairApprovalStatus; // 删除或修改不应该直接复制的属性
      delete copyData.returnApprovalStatus; // 删除或修改不应该直接复制的属性
      delete copyData.disposeApprovalStatus; // 删除或修改不应该直接复制的属性
      delete copyData.retireApprovalStatus; // 删除或修改不应该直接复制的属性
      if (!(copyData.date instanceof Date)) {
        copyData.date = new Date(copyData.date);
      }

      // // Ensure accumulatedDepreciation is a number
      // copyData.accumulatedDepreciation = Number(
      //   copyData.accumulatedDepreciation
      // );

      // // Ensure netValue is a float
      // copyData.netValue = parseFloat(copyData.netValue);

      // // Convert price to a number using parseFloat to handle potential decimal values
      // copyData.price = parseFloat(copyData.price);

      // // Convert taxPrice to a number, similar to price
      // copyData.taxPrice = parseFloat(copyData.taxPrice);
      // 预填充到 formData 并切换视图
      this.formData = { ...copyData };
      console.log(this.formData);
      //this.formData = copyData;
      this.currView = "add";
    },
    // 触发文件选择
    selectFiles() {
      this.$refs.fileInput.click(); // 触发 file input
    },

    // 处理文件的选择
    handleFilesUpload(event) {
      const file = event.target.files[0]; // Get the file from the event
      if (!file) {
        alert("Please select a file.");
        return;
      }

      let formData = new FormData();
      formData.append("file", file);

      // Use the uploadExcelFile API function
      uploadExcelFile(formData)
        .then((response) => {
          // Assuming the server response includes some success message or data
          console.log("File uploaded successfully:", response);
          alert("File uploaded successfully!");
        })
        .catch((error) => {
          // Handle errors more effectively
          console.error("Error uploading file:", error);
          alert(
            "Error uploading file: " +
              (error.response ? error.response.data : "Network error")
          );
        });
    },

    beforeUpload(file) {
      this.fileList.push(file);
      return false; // 阻止自动上传
    },
    submitUpload() {
      if (this.fileList.length > 0) {
        const formData = new FormData();
        this.fileList.forEach((file) => {
          formData.append("file", file.raw); // 使用file.raw获取File对象
        });
        // 调用API函数上传文件
        uploadExcelFile(formData)
          .then((response) => {
            // 处理响应
            console.log(response);
            this.$message.success("上传成功");
          })
          .catch((error) => {
            console.error(error);
            this.$message.error("上传失败");
          })
          .finally(() => {
            this.fileList = []; // 清空文件列表
          });
      } else {
        this.$message.warning("请选择文件");
      }
    },
    handleSuccess(response, file, fileList) {
      // 处理上传成功的响应
      console.log(response);
    },
    // 修改submitFx方法，让它接收一个rowData参数
    submitFx(rowData) {
      // 确认提交当前行的数据
      this.$Modal.confirm({
        title: "确认提交审核?",
        content: `您确认要提交这条登记单吗?`, // 简化内容，因为是单条提交
        loading: true,
        onOk: () => {
          const id = rowData.id; // 直接使用rowData中的id
          submitData({ ids: id })
            .then((res) => {
              //打印id
              console.log(id);
              this.$Modal.remove();
              if (res.success) {
                this.$Message.success("提交成功");
                this.getDataList(); // 重新获取数据列表
              } else {
                this.$Message.error("提交失败");
              }
            })
            .catch((error) => {
              this.$Message.error(`提交过程中发生错误: ${error.message}`);
            });
        },
      });
    },
    returnFx(rowData) {
      // 确认提交当前行的数据
      this.$Modal.confirm({
        title: "确认撤回审核?",
        content: `您确认要撤回这条登记单吗?`, // 简化内容，因为是单条提交
        loading: true,
        onOk: () => {
          const id = rowData.id; // 直接使用rowData中的id
          returnData({ ids: id })
            .then((res) => {
              //打印id
              console.log(id);
              this.$Modal.remove();
              if (res.success) {
                this.$Message.success("撤回成功");
                this.getDataList(); // 重新获取数据列表
              } else {
                this.$Message.error("撤回失败");
              }
            })
            .catch((error) => {
              this.$Message.error(`撤回过程中发生错误: ${error.message}`);
            });
        },
      });
    },
    importExcel(ev) {
      debugger;
      let file = ev.raw;
      if (!file) return;
      //读取FILE中的数据
      this.readFile(file).then((data) => {
        let workbook = XLSX.read(data, { type: "binary" });
        console.log(workbook);
      });
    },
    readFile(file) {
      return new Promise((resolve) => {
        let reader = new FileReader();
        reader.readAsBinaryString(file);
        reader.onload = (ev) => {
          // workbook打印出数据,表示成功
          resolve(ev.target.result);
        };
      });
    },
    handleFileInput(url) {
      console.log("File uploaded with URL:", url);
      // You can add the URL to an array or process it as needed
    },
    handleFileChange(url) {
      console.log("File change detected:", url);
      // Handle any additional logic needed after a file change
    },
    viewImage(imageSrc) {
      let image = new Image();
      image.src = imageSrc;
      const viewer = new Viewer(image, {
        hidden: function () {
          viewer.destroy();
        },
      });
      viewer.show();
    },

    generateAndShowQRCode(row) {
      generateQRCode({ id: row.id })
        .then((res) => {
          // 假设后端返回新的二维码URL在 res.qrCode
          let qrCodeUrl = `${res.qrCode}?t=${new Date().getTime()}`;
          this.$set(row, "qrCode", qrCodeUrl);
          console.log(qrCodeUrl);
          //this.$set(row, "qrCode", res.qrCode); // 使用Vue的$set方法确保响应性
          // 强制刷新组件或指定的子组件
          this.$forceUpdate(); // 强制Vue重新渲染组件
          this.viewImage(res.qrCode);
        })
        .catch((error) => {
          console.error("生成二维码失败：", error);
          this.$message.error("生成二维码失败");
        });
    },

    // 提交
    submited() {
      this.currView = "index";
      this.getDataList();
    },
    // 当前页数
    changePage(v) {
      this.searchForm.pageNumber = v;
      this.getDataList();
      this.clearSelectAll();
    },
    // 页面大小
    changePageSize(v) {
      this.searchForm.pageSize = v;
      this.getDataList();
    },
    rowClick(row, index) {
      this.selectRow = { ...row };
    },

    // // 表格点击
    // rowClick(row, index) {
    //   this.selectRow = row;
    // },
    // 表格类名添加
    rowClassNmae(row, index) {
      if (row.id == this.selectRow.id) {
        return "rowClassNmaeColor";
      }
      return "";
    },
    //通过excel批量导入
    importExcel() {
      this.$refs.uploadExcel.click();
    },

    // 导出Excel
    excelData() {
      this.$refs.table.exportCsv({
        filename: "导出结果",
      });
    },
    // 搜索
    handleSearch() {
      this.searchForm.pageNumber = 1;
      this.searchForm.pageSize = 10;
      this.getDataList();
    },
    // 重置搜索条件
    handleReset() {
      this.$refs.searchForm.resetFields();
      this.searchForm.pageNumber = 1;
      this.searchForm.pageSize = 10;
      // 重新加载数据
      this.getDataList();
    },
    // 切换查询排序规则
    changeSort(e) {
      this.searchForm.sort = e.key;
      this.searchForm.order = e.order;
      if (e.order === "normal") {
        this.searchForm.order = "";
      }
      this.getDataList();
    },
    // 全选
    clearSelectAll() {
      this.$refs.table.selectAll(false);
    },
    // 多选
    changeSelect(e) {
      this.selectList = e;
      this.selectCount = e.length;
    },
    // 获取渲染
    getDataList() {
      this.loading = true;
      getAdminAssetList(this.searchForm).then((res) => {
        this.loading = false;
        if (res.success) {
          //在获取数据后，使用sort方法对res.result.records进行排序，按照数据的ID从小到大排序。然后在渲染序号时使用排序后的索引。
          this.data = res.result.records.sort((a, b) => a.id - b.id);
          this.total = res.result.total;
        }
        //打印data
        console.log(this.data);
      });
    },
    // 新增
    add() {
      this.formData = {}; // 重置 formData 为一个空对象
      this.currView = "add";
    },
    // 编辑
    edit(v) {
      // 转换null为""
      for (let attr in v) {
        if (v[attr] == null) {
          v[attr] = "";
        }
      }
      let str = JSON.stringify(v);
      let data = JSON.parse(str);
      this.formData = data;
      this.currView = "edit";
    },
    // 删除
    remove(v) {
      this.$Modal.confirm({
        title: "确认删除",
        // 记得确认修改此处
        content: "您确认要删除 " + v.name + " ?",
        loading: true,
        onOk: () => {
          // 删除
          deleteAdminAsset({
            ids: v.id,
          }).then((res) => {
            this.$Modal.remove();
            if (res.success) {
              this.$Message.success("操作成功");
              this.getDataList();
            }
          });
        },
      });
    },
    // 批量删除
    delAll() {
      if (this.selectCount <= 0) {
        this.$Message.warning("您还未选择要删除的数据");
        return;
      }
      this.$Modal.confirm({
        title: "确认删除",
        content: "您确认要删除所选的 " + this.selectCount + " 条数据?",
        loading: true,
        onOk: () => {
          let ids = "";
          this.selectList.forEach(function (e) {
            ids += e.id + ",";
          });
          ids = ids.substring(0, ids.length - 1);
          // 批量删除
          deleteAdminAsset({
            ids: ids,
          }).then((res) => {
            this.$Modal.remove();
            if (res.success) {
              this.$Message.success("操作成功");
              this.clearSelectAll();
              this.getDataList();
            }
          });
        },
      });
    },
  },
  mounted() {
    this.init();
    // 列筛选
    this.mycolumns = this.columns;
    let showcolumns = [];
    for (var i = 0; i < this.selected.length; i++) {
      var item = this.selected[i];
      for (var j = 0; j < this.columns.length; j++) {
        if (this.columns[j].title == item) {
          showcolumns.push(this.columns[j]);
        }
      }
    }
    this.columns = showcolumns;
  },
  watch: {
    // 列筛选
    selected: function (newcolumns) {
      let showcolumns = [];
      for (var i = 0; i < this.mycolumns.length; i++) {
        var item = this.mycolumns[i];
        if (item.title == undefined) showcolumns.push(item);
        else if (newcolumns.includes(item.title)) showcolumns.push(item);
      }
      this.columns = showcolumns;
    },
  },
};
</script>

<style lang="less">
// @import "../../../styles/table-common.less";
.search {
  .operation {
    margin-bottom: 2vh;
  }

  .select-count {
    font-weight: 600;
    color: #40a9ff;
  }

  .select-clear {
    margin-left: 10px;
  }

  .page {
    margin-top: 2vh;
  }

  .drop-down {
    margin-left: 5px;
  }
}

.filter-panel {
  width: 166px;
  min-height: 120px;
  height: 200px;
  position: absolute;
  background-color: white;
  z-index: 9999;
  margin-left: 1px;
  overflow-y: scroll;
  border: 1px solid blue;
  top: 35px;
  right: 10px;
}

.openSearch {
  position: absolute;
  right: 240px;
}

.openTip {
  position: absolute;
  right: 130px;
}

.showFilterPanelFlag {
  position: static !important;
  right: 10px;
  margin-right: 10px;
}

.ivu-table td {
  height: 38px !important;
}

.ivu-table-cell-with-expand {
  height: 38px !important;
  line-height: 38px !important;
}

.ivu-table .rowClassNmaeColor td {
  background-color: #b0b3b6 !important;
  color: #515a6e !important;
  font-size: 18px;
}

.spanTS {
  display: flex;
  flex-direction: column;
  margin-top: 30px;
}
</style>
