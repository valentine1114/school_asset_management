<template>
  <div>
    <Button @click="toggleCategory">
      切换到
      {{
        currentCategory === "department"
          ? "仓库"
          : currentCategory === "warehouse"
          ? "科研项目"
          : "部门"
      }}分类
    </Button>
    <Button @click="toggleChartType">
      切换到
      {{
        chartType === "pie"
          ? "柱状图"
          : chartType === "column"
          ? "折线图"
          : "饼图"
      }}
    </Button>
    <div class="chart-title">
      {{ getCategoryName() }} - {{ getChartTypeName() }}
    </div>
    <div id="container"></div>
  </div>
</template>

<script>
import { Pie, Column, Line, measureTextWidth } from "@antv/g2plot";
import {
  getAssetDataByWarehouse,
  getAssetDataByDepartment,
  getAssetDataByResearchProject,
} from "./api.js"; // 假设你有一个api.js文件处理API请求

export default {
  name: "AssetStatistics",
  data() {
    return {
      assetData: [],
      currentCategory: "department", // 初始分类
      chartType: "pie", // 可选：pie, column, line
      chartInstance: null,
    };
  },
  methods: {
    async toggleCategory() {
      if (this.currentCategory === "department") {
        this.currentCategory = "warehouse";
      } else if (this.currentCategory === "warehouse") {
        this.currentCategory = "researchProject";
      } else {
        this.currentCategory = "department";
      }
      await this.fetchAssetData();
      this.renderChart();
    },
    toggleChartType() {
      if (this.chartType === "pie") {
        this.chartType = "column";
      } else if (this.chartType === "column") {
        this.chartType = "line";
      } else {
        this.chartType = "pie";
      }
      this.renderChart();
    },
    async fetchAssetData() {
      try {
        let res;
        if (this.currentCategory === "department") {
          res = await getAssetDataByDepartment();
        } else if (this.currentCategory === "warehouse") {
          res = await getAssetDataByWarehouse();
        } else {
          res = await getAssetDataByResearchProject();
        }
        if (res.success) {
          this.assetData = res.result;
        }
      } catch (error) {
        console.error("Failed to fetch data:", error);
      }
    },
    renderStatistic(containerWidth, text, style) {
      const { width: textWidth, height: textHeight } = measureTextWidth(
        text,
        style
      );
      const R = containerWidth / 2;
      let scale = 1;
      if (containerWidth < textWidth) {
        scale = Math.min(
          Math.sqrt(
            Math.abs(
              Math.pow(R, 2) /
                (Math.pow(textWidth / 2, 2) + Math.pow(textHeight, 2))
            )
          ),
          1
        );
      }
      const textStyleStr = `width:${containerWidth}px;`;
      return `<div style="${textStyleStr};font-size:${scale}em;line-height:${
        scale < 1 ? 1 : "inherit"
      };">${text}</div>`;
    },
    renderChart() {
      if (this.chartInstance && this.chartInstance.destroy) {
        this.chartInstance.destroy();
      }

      const config = {
        appendPadding: 10,
        data: this.assetData,
        meta: {
          value: {
            formatter: (v) => `${v}元`,
          },
        },
        label: {
          style: {
            textAlign: "center",
          },
          autoRotate: false,
          content: (data) => `${data.value}元`,
        },
      };

      if (this.chartType === "pie") {
        config.angleField = "value";
        config.colorField = "title";
        config.radius = 1;
        config.innerRadius = 0.64;
        config.statistic = {
          title: {
            offsetY: -4,
            customHtml: (container, view, datum) => {
              const { width, height } = container.getBoundingClientRect();
              const d = Math.sqrt(
                Math.pow(width / 2, 2) + Math.pow(height / 2, 2)
              );
              const text = datum ? datum.title : "固定资产金额分布";
              return this.renderStatistic(d, text, { fontSize: 28 });
            },
          },
          content: {
            offsetY: 4,
            style: {
              fontSize: "32px",
            },
            customHtml: (container, view, datum, data) => {
              const { width } = container.getBoundingClientRect();
              const text = datum
                ? ` ${datum.value}元`
                : `总计：${data.reduce((r, d) => r + d.value, 0)}元`;
              return this.renderStatistic(width, text, { fontSize: 32 });
            },
          },
        };
        this.chartInstance = new Pie("container", config);
      } else if (this.chartType === "column") {
        config.xField = "title";
        config.yField = "value";
        config.label.position = "middle";
        this.chartInstance = new Column("container", config);
      } else if (this.chartType === "line") {
        config.xField = "title";
        config.yField = "value";
        this.chartInstance = new Line("container", config);
      }

      this.chartInstance.render();
    },
    getCategoryName() {
      switch (this.currentCategory) {
        case "department":
          return "部门";
        case "warehouse":
          return "仓库";
        case "researchProject":
          return "科研项目";
        default:
          return "";
      }
    },
    getChartTypeName() {
      switch (this.chartType) {
        case "pie":
          return "饼图";
        case "column":
          return "柱状图";
        case "line":
          return "折线图";
        default:
          return "";
      }
    },
  },
  mounted() {
    this.fetchAssetData().then(() => {
      this.renderChart();
    });
  },
};
</script>

<style lang="less" scoped>
#container {
  width: 100%;
  height: 700px;
  margin-top: 20px;
}

.chart-title {
  font-size: 24px;
  text-align: center;
  margin: 20px 0;
}

.antvTitle {
  font-size: 20px;
  text-align: center;
  justify-content: center;
  display: flex;

  .antvSecondTitle {
    color: #ff9900;
    font-size: 22px;
  }
}
</style>
