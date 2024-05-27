<template>
  <div class="main-content">
    <div id="main" style="width: 1200px; height: 600px; margin: 0 auto"></div>
  </div>
</template>
<script>
import * as echarts from "echarts";
export default {
  data() {
    return {};
  },
  created() {
    this.getData1();
  },
  methods: {
    getData1() {
      this.$nextTick(() => {
        this.$http({
          url: "list/getData2",
          method: "get",
        }).then(({ data }) => {
          console.log("🚀 ~ this.$nextTick ~ data:", data);
          var myChart = echarts.init(document.getElementById("main"));
          myChart.setOption({
            tooltip: {
              trigger: "axis",
            },
            xAxis: {
              type: "category",
              data: data.key,
            },
            color: ["#85c7ff", "#85c7ff"],
            yAxis: {
              type: "value",
            },
            series: [
              {
                name: "数量",
                data: data.val,
                type: "bar",
              },
            ],
          });
        });
      });
    },
  },
};
</script>
<style lang="scss" scoped></style>
