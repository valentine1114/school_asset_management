好的，我们可以使用一个表格来显示当前一周的可借用时段，表头显示日期、预约情况和操作列。如果某个日期已经被预约，操作列中的预约按钮将变为灰色，预约情况列将显示借用人名或显示“当前时段无法预约”。

以下是修改后的代码：

```vue
<template>
  <div class="search full-height">
    <Card>
      <!-- 上方按钮与搜索层 -->
      <Row v-show="openSearch" @keydown.enter.native="handleSearch">
        <Form ref="searchForm" :model="searchForm" inline :label-width="0">
          <Form-item style="display: flex">
            <!-- 资产名称子级盒子 -->
            <Form-item label="" prop="name">
              <Input
                type="text"
                v-model="searchForm.name"
                placeholder="请输入资产名称"
                clearable
                style="width: 200px"
              />
            </Form-item>
            <!-- 按钮层 -->
            <Form-item style="margin-left: 10px" class="br">
              <Button
                @click="handleSearch"
                type="primary"
                icon="ios-search"
                size="small"
                ghost
                shape="circle"
                >搜索</Button
              >
              <Button
                @click="handleReset"
                type="warning"
                size="small"
                ghost
                shape="circle"
                icon="md-refresh"
                >重置</Button
              >
            </Form-item>
          </Form-item>
        </Form>
      </Row>
      <!-- 表格层盒子 -->
      <Row class="full-height">
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
          :row-class-name="rowClassName"
          class="full-height"
        ></Table>
      </Row>
      <!-- 分页栏 -->
      <Row type="flex" justify="end" class="page">
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
    <!-- 弹窗 -->
    <Modal
      v-model="timeModalVisible"
      title="选择可借用时间段"
      :mask="false"
      :mask-closable="false"
      draggable
      width="600px"
      @on-ok="confirmBooking"
    >
      <Row :gutter="16">
        <Col span="24">
          <Table :columns="bookingColumns" :data="bookingData" border></Table>
        </Col>
      </Row>
    </Modal>
  </div>
</template>

<script>
export default {
  name: "asset-sharing",
  data() {
    return {
      timeModalVisible: false,
      selectedDate: null,
      selectedAsset: null,
      openSearch: true, // 确保搜索栏显示
      searchForm: {
        pageNumber: 1,
        pageSize: 10,
        name: "",
      },
      columns: [
        { type: "selection", width: 60, align: "center" },
        {
          title: "序号",
          width: 85,
          align: "center",
          render: (h, params) => h("span", params.index + 1),
        },
        { title: "资产名称", key: "name", align: "center" },
        { title: "使用人", key: "user", align: "center" },
        { title: "所在部门", key: "department", align: "center" },
        {
          title: "操作",
          key: "action",
          align: "center",
          render: (h, params) => this.renderActions(h, params),
        },
      ],
      data: [
        {
          id: 1,
          name: "显微镜",
          user: "张三",
          department: "生物实验室",
          status: "闲置中",
        },
        {
          id: 2,
          name: "投影仪",
          user: "李四",
          department: "会议室",
          status: "闲置中",
        },
        {
          id: 3,
          name: "3D打印机",
          user: "王五",
          department: "工程实验室",
          status: "正在使用",
        },
      ],
      loading: false,
      total: 3,
      bookings: [], // 模拟预约数据
      bookingColumns: [
        { title: "日期", key: "date", align: "center" },
        { title: "预约情况", key: "status", align: "center" },
        {
          title: "操作",
          key: "action",
          align: "center",
          render: (h, params) => {
            const isBooked = params.row.isBooked;
            return h(
              "Button",
              {
                props: {
                  type: isBooked ? "default" : "primary",
                  size: "small",
                  disabled: isBooked,
                },
                on: {
                  click: () => {
                    if (!isBooked) {
                      this.selectedDate = params.row.date;
                    }
                  },
                },
              },
              isBooked ? "已预约" : "预约"
            );
          },
        },
      ],
      bookingData: [],
    };
  },
  methods: {
    handleSearch() {
      const filteredData = this.data.filter((asset) =>
        asset.name.includes(this.searchForm.name)
      );
      this.total = filteredData.length;
      this.data = filteredData;
    },
    handleReset() {
      this.searchForm.name = "";
      this.getDataList();
    },
    changePage(page) {
      this.searchForm.pageNumber = page;
      this.getDataList();
    },
    changePageSize(size) {
      this.searchForm.pageSize = size;
      this.getDataList();
    },
    getDataList() {
      // 使用模拟数据
      this.data = [
        {
          id: 1,
          name: "显微镜",
          user: "张三",
          department: "生物实验室",
          status: "闲置中",
        },
        {
          id: 2,
          name: "投影仪",
          user: "李四",
          department: "综合办公室",
          status: "闲置中",
        },
        {
          id: 3,
          name: "3D打印机",
          user: "王五",
          department: "工程实验室",
          status: "正在使用",
        },
      ];
      this.total = this.data.length;
      // 模拟预约数据
      this.bookings = [
        { asset_id: 1, date: "2024-06-04", user_id: 1, user_name: "陈六" },
        { asset_id: 1, date: "2024-06-01", user_id: 2, user_name: "李四" },
      ];
    },
    renderActions(h, params) {
      return h(
        "Button",
        {
          props: {
            type: "primary",
            size: "small",
          },
          on: {
            click: () => {
              this.showTimeModal(params.row);
            },
          },
        },
        "可用时段"
      );
    },
    showTimeModal(asset) {
      this.selectedAsset = asset;
      this.timeModalVisible = true;

      // 生成下一周的日期并检查预约情况
      const today = new Date();
      const nextWeek = [];
      for (let i = 1; i <= 7; i++) {
        const nextDate = new Date(today);
        nextDate.setDate(today.getDate() + i);
        const dateString = nextDate.toISOString().split("T")[0];
        const booking = this.bookings.find(
          (b) => b.asset_id === asset.id && b.date === dateString
        );
        nextWeek.push({
          date: dateString,
          status: booking ? `已预约: ${booking.user_name}` : "可预约",
          isBooked: !!booking,
        });
      }
      this.bookingData = nextWeek;
    },
    confirmBooking() {
      if (!this.selectedDate) {
        this.$Message.warning("请选择日期");
        return;
      }
      this.$Message.success(
        `借用了${this.selectedAsset.name} 在 ${this.selectedDate}`
      );
      this.timeModalVisible = false;

      // 更新模拟数据
      this.bookings.push({
        asset_id: this.selectedAsset.id,
        date: this.selectedDate,
        user_id: 1, // 假设当前用户ID为1
        user_name: "当前用户",
      });

      // 更新表格数据
      this.showTimeModal(this.selectedAsset);
    },
  },
  mounted() {
    this.getDataList();
  },
};
</script>

<style scoped>
.search {
  height: 100vh;
  display: flex;
  flex-direction: column;
}

.full-height {
  flex: 1;
  overflow: auto;
}

.page {
  margin-top: 2vh;
}

.drop-down {
  margin-left: 5px;
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
