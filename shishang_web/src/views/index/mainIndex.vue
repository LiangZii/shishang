<template>
  <div>
    
    <el-row :gutter="10" >
      <el-col :span="12">
        <div class="card kjfs" >
          <p class="title"><i class="fa fa-th-large fa-lg"></i>热度榜单</p>
          <template>
        <el-table
        :data="tableData6"
        style="width: 100%">
        <el-table-column
          prop="deviceid"
          label="排名"
          width="60">
        </el-table-column>
        <el-table-column
          prop="devicename"
          label="菜谱名"
          width="160">
        </el-table-column>
        <el-table-column
          prop="faultdate"
          label="点击值"
          width="100">
        </el-table-column>
        <el-table-column
          prop="fieldname"
          label="作者"
          width="150">
        </el-table-column>
      </el-table>
    </template>
        </div>
      </el-col>

  
      
      <el-col :span="12">
        <div class="card bbxx">
          <p class="title"><i class="fa fa-server"></i>待办事项</p>
          <template>
        <el-table
        :data="tableData8"
        style="width: 100%">
        <el-table-column
          prop="deviceid"
          label="事项编号"
          width="100">
        </el-table-column>
        <el-table-column
          prop="devicename"
          label="事项内容"
          width="200">
        </el-table-column>
        <el-table-column
          prop="time"
          label="需要完成时间"
          width="110">
        </el-table-column>
        <el-table-column
          prop="fieldname"
          label="紧急程度"
          width="80">
        </el-table-column>
        
      </el-table>
    </template>
        </div>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="24">
        <line-echarts id="lineEcharts" height="300px" ref="echarts"></line-echarts>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import LineEcharts from "../../components/ECharts/lineEcharts"
import axios from 'axios'
import mock from '@/mock/index.js'
//import Maintable from "../table/maintable"
export default {
  name: "mainIndex",
  data () {
    return {
      tableData6: [{}],
      tableData8: [{}],
    }
  },
created (){
    axios.get('http://localhost:8080/mainindex').then(
            response => {
              console.log('请求成功了',response.data.data)
              this.tableData6=response.data.data.data1
              this.tableData8=response.data.data.data3
            },
            error => {
              console.log('请求失败了',error.message)
            }
          )
  },
  components: {LineEcharts},
  mounted () {
    this.selfAdaption()
  },
  methods: {
    // echart自适应
    selfAdaption () {
      let that = this
      setTimeout(() => {
        window.onresize = function () {
          if (that.$refs.echarts) {
            that.$refs.echarts.chart.resize()
          }
        }
      }, 10)
    }
  }
}
</script>

<style lang="scss">
  $top:top;
  $bottom:bottom;
  $left:left;
  $right:right;
  $leftright: ($left, $right);
  $pinkk: #eec2d3;
  $bluee: #409eff;
  $yelloww: #f4d884;
  $grennn: #89dda0;
  $purplee: #78a2ea;
  $lightBluee: #b8d6ff;

  $list: bluee pinkk yelloww grennn purplee lightBluee;
  $list1: $bluee $pinkk $yelloww $grennn $purplee $lightBluee;
  %shadow{
    background: #fff;
    -webkit-box-shadow: 4px 4px 40px rgba(0, 0, 0, 0.2);
    box-shadow: 4px 4px 40px rgba(0, 0, 0, 0.2);
    border-color: rgba(0, 0, 0, 0.2);
    .title{
      font-size: 14px;
      padding: 10px;
      i{
        margin-#{$right}: 5px;
      }
    }
  }

  @mixin flex($direction:row,$content:space-between){
    display: flex;
    flex-direction: $direction;
    justify-content: $content;
  }
  .card{
    color: #666;
    @extend %shadow;

    ul{
      @include flex;
      li{
        flex: 1;
        a{
          color: #666666;
          align-items:center;
          padding-#{$top}: 20px;
          padding-#{$bottom}: 20px;
          @include flex(column);
          span{
            height: 44px;
          }
          .num{
            line-height: 44px;
            font-size: 42px;
            color: $bluee;
            margin: 0px;
          }
        }
        .kjfs-bluee{
          color: $bluee;
        }
        .kjfs-pinkk{
          color: $pinkk;
        }
        .kjfs-yelloww{
          color: $yelloww;
        }
        .kjfs-grennn{
          color: $grennn;
        }
        .kjfs-purplee{
          color: $purplee;
        }
        .kjfs-lightBluee{
          color: $lightBluee;
        }
        &:hover{
          .kjfs-bluee{
            color: #ffffff;
            background: $bluee;
          }
          .kjfs-pinkk{
            color: #ffffff;
            background: $pinkk;
          }
          .kjfs-yelloww{
            color: #ffffff;
            background: $yelloww;
          }
          .kjfs-grennn{
            color: #ffffff;
            background: $grennn;
          }
          .kjfs-purplee{
            color: #ffffff;
            background: $purplee;
          }
          .kjfs-lightBluee{
            color: #ffffff;
            background: $lightBluee;
          }
        }
      }
    }
    .table{
      padding: 21px;
      p{
        height: 52px;
        line-height: 52px;
        border: 1px solid #cccccc;
        overflow: hidden;
        border-#{$top}: none;
        @include flex( null,start);
        &:first-child{
          border-#{$top}: 1px solid #cccccc;
        }
        span{
        }
        .tit{
          width: 90px;
          min-width: 90px;
          height: 100%;
          text-align: center;
          border-#{$right}: 1px solid #cccccc;
          margin-#{$right}: 18px;
        }
        span.gitbox{
          flex: 1;
          height: 100%;
          overflow: hidden;
          @include flex(row,start);
          a{
            &:first-child{
              margin-#{$right}: 30px;
            }
          }
        }
      }
    }
  }
  #lineEcharts{
    margin-#{$top}: 30px;
    padding-#{$top}: 30px;
    @extend %shadow;
  }

</style>
