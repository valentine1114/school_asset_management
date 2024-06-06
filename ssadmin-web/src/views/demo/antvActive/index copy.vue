<template>
  <div>
    <Button @click="toggleCategory">
      切换到 {{ currentCategory === "department" ? "仓库" : "部门" }}分类
    </Button>
    <div id="container"></div>
  </div>
</template>

<script>
import { Pie, measureTextWidth } from "@antv/g2plot";
import { getAntvVoListByWarehouse, getAntvVoListByDepartment } from "./api.js";
export default {
  name: "test-page",
  components: {},
  props: {},
  data() {
    return {
      depCountData: [],
      currentCategory: "department", // 初始分类
      piePlot: {},
    };
  },
  methods: {
    init() {
      this.initAntvFx();
      this.getAntvVoListFx();
    },
    async toggleCategory() {
      this.currentCategory =
        this.currentCategory === "department" ? "warehouse" : "department";
      await this.getAntvVoListFx();
      this.initAntvFx();
    },
    async getAntvVoListFx() {
      try {
        let res;
        if (this.currentCategory === "department") {
          res = await getAntvVoListByDepartment();
        } else {
          res = await getAntvVoListByWarehouse();
        }
        if (res.success) {
          this.depCountData = res.result; // 更新数据
          this.initAntvFx(); // 重新初始化图表
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
    initAntvFx() {
      var that = this;
      var data = this.depCountData; // 这里应该是从API获取的数据
      // 如果已经存在一个图表实例，先销毁它

      // 检查是否存在图表实例并且它有destroy方法
      if (this.piePlot && this.piePlot.destroy) {
        this.piePlot.destroy();
      }

      this.piePlot = new Pie("container", {
        appendPadding: 10,
        data,
        angleField: "value",
        colorField: "title",
        radius: 1,
        innerRadius: 0.64,
        meta: {
          value: {
            formatter: (v) => `${v}元`,
          },
        },
        label: {
          type: "inner",
          offset: "-50%",
          style: {
            textAlign: "center",
          },
          autoRotate: false,
          content: "{value}",
        },
        statistic: {
          title: {
            offsetY: -4,
            customHtml: (container, view, datum) => {
              const { width, height } = container.getBoundingClientRect();
              const d = Math.sqrt(
                Math.pow(width / 2, 2) + Math.pow(height / 2, 2)
              );
              const text = datum ? datum.title : "固定资产金额分布";
              return that.renderStatistic(d, text, { fontSize: 28 });
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
              return that.renderStatistic(width, text, { fontSize: 32 });
            },
          },
        },
        // ...其余配置
      });
      this.piePlot.render();
      // ...其余逻辑
    },
  },
  mounted() {
    //this.init();
    this.currentCategory = "department"; // 设置初始分类为部门
    this.getAntvVoListFx(); // 获取数据并初始化图表
  },
};
</script>

<style lang="less" scoped>
#container {
  width: 100%;
  height: 700px;
  margin-top: 20px;
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
