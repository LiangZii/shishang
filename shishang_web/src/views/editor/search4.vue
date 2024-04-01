<template>
  <div class="test2" style="width:950px;height:1px;">
      <div id="myChart4" style="width:100%;height:500px;float:left;"></div>
  </div>
</template>
<script>
import axios from 'axios'
import mock from '@/mock/index.js'
export default {
  name: 'test2',
  data () {
    return {
      myChart4: '',
      opinion: ['一号风场', '二号风场',"三号风场","四号风场","五号风场"],
      tabledata:[{}]
      
    }
  },
  created (){
    axios.get('http://localhost:8080/search4').then(
            response => {
              console.log('请求成功了',response.data.data)
              this.tabledata=JSON.parse(JSON.stringify(response.data.data))
              console.log("啦啦啦",this.tabledata.xx1)
              this.drawLine4()
            },
            error => {
              console.log('请求失败了',error.message)
            }
          )
          
  },
  mounted: function () {
    
  },
  methods: {
    drawLine4 () {
      this.myChart4 = this.$echarts.init(document.getElementById('myChart4'))
      this.myChart4.setOption({
        title: {
          text: '汇总与分析'
        },
        tooltip: {
          trigger: 'item',
          formatter: '{a} <br/>{b} : {c}'
        },
        legend: {
          
          x: 'center',
          y: 'top',
          data: ["故障数量", "维修数量", "总发电量损失（万kwh）","备件消耗量","经济性收益（万元）/天"]
        },
        xAxis: {
          data: this.opinion
        },
        yAxis: {},
        series: [{
          name: '故障数量',
          type: 'bar',
          data: this.tabledata.xx1,
          itemStyle: {
            emphasis: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: 'rgba(0, 0, 0, 0.5)'
            },
            color:"#FF6EC7"
          }
        },{
          name: '维修数量',
          type: 'bar',
          data: this.tabledata.xx2,
          itemStyle: {
            emphasis: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: 'rgba(0, 0, 0, 0.5)'
            },
            color: "#9B30FF"
          }
        },{
          name: '总发电量损失（万kwh）',
          type: 'bar',
          data: this.tabledata.xx3,
          itemStyle: {
            emphasis: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: 'rgba(0, 0, 0, 0.5)'
            },
            color: "#00FFFF"
            
          }
        },{
          name: '备件消耗量',
          type: 'bar',
          data: this.tabledata.xx4,
          itemStyle: {
            emphasis: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: 'rgba(0, 0, 0, 0.5)'
            },
            color: "#B3EE3A"
          }
        },{
          name: '经济性收益（万元）/天',
          type: 'bar',
          data: this.tabledata.xx5,
          itemStyle: {
            emphasis: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: 'rgba(0, 0, 0, 0.5)'
            },
            color: "#FFC125"
          }
        }]
      })
    }
  }
}
</script>
