<template>
  <div>
    <Card>
      <div slot="title">
        <div class="edit-head">
          <a @click="close" class="back-title">
            <Icon type="ios-arrow-back" />返回
          </a>
          <div class="head-name">添加</div>
          <span></span>
          <a @click="close" class="window-close">
            <Icon type="ios-close" size="31" class="ivu-icon-ios-close" />
          </a>
        </div>
      </div>
      <Form
        ref="form"
        :model="form"
        :label-width="100"
        :rules="formValidate"
        label-position="left"
      >
        <FormItem label="名称" prop="name">
          <Input v-model="form.name" clearable style="width: 570px" />
        </FormItem>
        <FormItem label="类别" prop="type">
          <Select v-model="form.type" clearable style="width: 570px">
            <Option value="土地、 房屋及构筑物">土地、 房屋及构筑物</Option>
            <Option value="通用设备">通用设备</Option>
            <Option value="专用设备">专用设备</Option>
            <Option value="文物和陈列品">文物和陈列品</Option>
            <Option value="图书档案">图书档案</Option>
            <Option value="家具、 用具、 装具及动植物"
              >家具、 用具、 装具及动植物</Option
            >
          </Select>
        </FormItem>
        <FormItem label="固定资产编码" prop="code">
          <Input v-model="form.code" clearable style="width: 570px" />
        </FormItem>
        <FormItem label="购置日期" prop="date">
          <div style="display: flex; align-items: center">
            <DatePicker
              v-model="form.date"
              type="date"
              clearable
              style="width: 570px"
            >
            </DatePicker>
            <div style="font-size: 12px; color: #999">
              发票右上角处的开票日期
            </div>
          </div>
        </FormItem>

        <FormItem label="序列号" prop="serialNumber">
          <Input v-model="form.serialNumber" clearable style="width: 570px" />
        </FormItem>
        <FormItem label="采购组织形式" prop="purchasingForm">
          <Select v-model="form.purchasingForm" clearable style="width: 570px">
            <Option value="分散采购">分散采购</Option>
            <Option value="政府批量集中采购">政府批量集中采购</Option>
          </Select>
        </FormItem>
        <FormItem label="采购合同编码" prop="purchaseContractCode">
          <Input
            v-model="form.purchaseContractCode"
            clearable
            style="width: 570px"
          />
        </FormItem>
        <FormItem label="折旧方法" prop="depreciationMethod">
          <Select
            v-model="form.depreciationMethod"
            clearable
            style="width: 570px"
          >
            <Option value="直线法">直线法</Option>
            <Option value="加速折旧法">加速折旧法</Option>
          </Select>
        </FormItem>
        <FormItem label="累计折旧" prop="accumulatedDepreciation">
          <Input
            v-model="form.accumulatedDepreciation"
            clearable
            style="width: 570px"
          />
        </FormItem>
        <FormItem label="净值" prop="netValue">
          <Input v-model="form.netValue" clearable style="width: 570px" />
        </FormItem>
        <FormItem label="存放仓库" prop="warehouse">
          <Select v-model="form.warehouse" clearable style="width: 570px">
            <Option
              v-for="(item, index) in wareList"
              :key="index"
              :value="item.name"
              >{{ item.name }}</Option
            ></Select
          >
        </FormItem>
        <FormItem label="不含税价" prop="price">
          <Input v-model="form.price" clearable style="width: 570px" />
        </FormItem>
        <FormItem label="含税价" prop="taxPrice">
          <Input v-model="form.taxPrice" clearable style="width: 570px" />
        </FormItem>
        <FormItem label="供应商" prop="supplier">
          <Input v-model="form.supplier" clearable style="width: 570px" />

          <Button type="error" @click="openSelectSupplierWindow"
            >导入供应商</Button
          >
        </FormItem>
        <!-- <FormItem label="二维码编号" prop="qrCode">
          <Input v-model="form.qrCode" clearable style="width: 570px" />
        </FormItem> -->
        <FormItem label="物料图片" prop="imageUrl">
          <upload-pic-input
            v-model="form.imageUrl"
            style="width: 570px"
          ></upload-pic-input>
        </FormItem>
        <FormItem label="备注" prop="remark">
          <Input v-model="form.remark" clearable style="width: 570px" />
        </FormItem>
        <FormItem label="状态" prop="status">
          <Select v-model="form.status" clearable style="width: 570px">
            <Option value="0">未入库</Option>
          </Select>
        </FormItem>

        <Form-item class="br">
          <Button @click="handleSubmit" :loading="submitLoading" type="primary"
            >提交并保存</Button
          >
          <Button @click="handleReset">重置</Button>
          <Button type="dashed" @click="close">关闭</Button>
        </Form-item>
      </Form>
    </Card>
    <supplierSelect v-model="showOwnerWindowFlag" @submited="ownerSubmited" />
  </div>
</template>
  
  <script>
import { addAdminAsset, getAllWareList, getAllUnitList } from "./api.js";
import supplierSelect from "./supplierSelect.vue";
import uploadPicInput from "@/views/template/upload-pic-input";

export default {
  name: "add",
  components: {
    uploadPicInput,
    supplierSelect,
  },
  props: {
    // 定义 formData 属性，告诉 Vue 这个组件可以接收名为 formData 的 prop
    formData: {
      type: Object,
      default: () => ({}), // 如果没有传入 formData，就使用一个空对象作为默认值
    },
  },
  data() {
    return {
      showOwnerWindowFlag: false,
      submitLoading: false, // 表单提交状态

      form: this.formData || {
        // 添加或编辑表单对象初始化数据
        name: "", // 名称
        type: "", // 类别
        code: "", // 固定资产编码
        date: "", // Default to today's date
        serialNumber: "", // 序列号
        purchasingForm: "", // 采购组织形式
        purchaseContractCode: "", // 采购合同编码
        depreciationMethod: "", // 折旧方法
        accumulatedDepreciation: "", // 累计折旧
        netValue: "", // 净值
        warehouse: "", // 存放仓库
        price: "", // 不含税价
        taxPrice: "", // 含税价
        supplier: "", // 供应商
        // qrCode: "", // 二维码编号
        imageUrl: "", // 物料图片
        remark: "", // 备注
        status: "0", // 状态
      },
      formData:
        this.$props.formData ||
        {
          /* 默认值 */
        },
      // 表单验证规则
      formValidate: {
        name: [
          { required: true, message: "The name is required", trigger: "blur" },
        ],
        type: [
          {
            required: true,
            message: "The type is required",
            trigger: "change",
          },
        ],
        code: [
          {
            required: true,
            message: "The asset code is required",
            trigger: "blur",
          },
        ],
        // date: [
        //   {
        //     validator: this.validateDate,
        //     message: "The date cannot be in the future.",
        //     trigger: "change",
        //   },
        // ],
        serialNumber: [
          {
            required: true,
            message: "The serial number is required",
            trigger: "blur",
          },
        ],
        purchasingForm: [
          {
            required: true,
            message: "The purchasing form is required",
            trigger: "change",
          },
        ],
        purchaseContractCode: [
          {
            required: true,
            message: "The contract code is required",
            trigger: "blur",
          },
        ],
        depreciationMethod: [
          {
            required: true,
            message: "The depreciation method is required",
            trigger: "change",
          },
        ],
        warehouse: [
          {
            required: true,
            message: "The warehouse is required",
            trigger: "change",
          },
        ],
        // price: [
        //   {
        //     required: true,
        //     message: "The price is required",
        //     trigger: "change",
        //   },
        // ],
        // netValue: [
        //   {
        //     required: true,
        //     message: "The net value is required",
        //     trigger: "blur",
        //   },
        // ],
        // accumulatedDepreciation: [
        //   {
        //     required: true,
        //     message: "The accumulatedDepreciation is required",
        //     trigger: "blur",
        //   },
        // ],
        // taxPrice: [
        //   {
        //     required: true,
        //     message: "The taxPrice is required",
        //     trigger: "blur",
        //   },
        // ],
      },
      wareList: [],
      unitList: [],
    };
  },
  methods: {
    init() {
      this.getAllWareListFx();
      this.getAllUnitListFx();
    },

    validateDate(rule, value, callback) {
      if (value) {
        const selectedDate = new Date(value); // 确保value是日期对象
        const today = new Date();
        // 设置时间为午夜，避免时区导致的小时数差异
        selectedDate.setHours(0, 0, 0, 0);
        today.setHours(0, 0, 0, 0);
        console.log("s", selectedDate);
        console.log("t", today);
        if (selectedDate > today) {
          callback(new Error("The date cannot be in the future."));
        } else {
          callback();
        }
      } else {
        callback(new Error("The date is required."));
      }
    },
    openSelectSupplierWindow() {
      this.showOwnerWindowFlag = true;
    },
    ownerSubmited(e) {
      console.log(e);
      this.form.supplierId = e.id;
      this.form.supplier = e.name;
      this.showOwnerWindowFlag = false;
    },
    handleReset() {
      this.$refs.form.resetFields();
    },
    getAllWareListFx() {
      var that = this;
      getAllWareList().then((res) => {
        if (res.success) {
          that.wareList = res.result;
          //打印warelist

          console.log(res.result);
        }
      });
    },
    getAllUnitListFx() {
      var that = this;
      getAllUnitList().then((res) => {
        if (res.success) {
          that.unitList = res.result;
        }
      });
    },

    formatDate(d) {
      // if (!(d instanceof Date)) {
      //   d = new Date(d);
      // }
      // 获取年份
      let year = d.getFullYear();

      // 获取月份，月份从0开始所以加1，确保月份为两位数
      let month = d.getMonth() + 1;
      month = month < 10 ? "0" + month : month;

      // 获取日期，确保日期为两位数
      let date = d.getDate();
      date = date < 10 ? "0" + date : date;

      // 组合成 yyyy-MM-dd 格式
      return year + "-" + month + "-" + date;
    },
    handleSubmit() {
      if (this.form.date) {
        this.form.date = this.formatDate(this.form.date);
      }
      console.log(this.form);
      this.$refs.form.validate((valid) => {
        if (valid) {
          addAdminAsset(this.form).then((res) => {
            this.submitLoading = false;

            //打印

            console.log(this.form);
            if (res.success) {
              this.$Message.success("操作成功");
              this.submited();
            } else {
              this.$Message.error("操作失败");
            }
          });
        }
      });
    },
    close() {
      this.$emit("close", true);
    },
    submited() {
      this.$emit("submited", true);
    },
  },
  mounted() {
    this.init();
  },
};
</script>
  
  <style lang="less">
// 建议引入通用样式 具体路径自行修改 可删除下面样式代码
// @import "../../../styles/single-common.less";
.edit-head {
  display: flex;
  align-items: center;
  justify-content: space-between;
  position: relative;

  .back-title {
    color: #515a6e;
    display: flex;
    align-items: center;
  }

  .head-name {
    display: inline-block;
    height: 20px;
    line-height: 20px;
    font-size: 16px;
    color: #17233d;
    font-weight: 500;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }

  .window-close {
    z-index: 1;
    font-size: 12px;
    position: absolute;
    right: 0px;
    top: -5px;
    overflow: hidden;
    cursor: pointer;

    .ivu-icon-ios-close {
      color: #999;
      transition: color 0.2s ease;
    }
  }

  .window-close .ivu-icon-ios-close:hover {
    color: #444;
  }
}
</style>
  