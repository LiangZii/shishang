<template>
  <div>
    <h2>设备状态查询</h2>
        <!--<p>对表格进行筛选，可快速查找到自己想看的数据。</p> -->
    <hr>
    
        <a style="color:black">您要查询的风场编号和机组编号（以-分割）</a>
        <el-input
        placeholder="请输入内容"
        prefix-icon="el-icon-search"
        v-model="textxx"
        style="width: 300px;height: 30px;">
        </el-input>
      <el-button @click="search()">查询</el-button>
    <br><br>
    <template>

      <el-table
        :data="tableDataxx"
        :header-cell-style="{'text-align':'center'}"
        :cell-style="{'text-align':'center'}"
        style="width:90%">

        <el-table-column
          prop="crewid"
          label="风场编号"
          width="150">
        </el-table-column>
        <el-table-column
          prop="crewbh"
          label="机组编号"
          width="150">
        </el-table-column>

        <el-table-column
          prop="status"
          label="运行状况"
          width="250">
        </el-table-column>

        <el-table-column
          prop="cost"
          label="维修费用"
          width="150">
        </el-table-column>

        <el-table-column
          prop="quantity"
          label="备件消耗"
          width="150">
        </el-table-column>
        
      </el-table>
    </template>
  </div>
</template>

<script>
import axios from 'axios'
import mock from '@/mock/index.js'
export default {
  name: "filterTable",
  data () {
    return {
      textxx:"",
      tableData5: [{}],
      tableDataxx:[{}]
    }
  },
  created (){
    axios.get('http://localhost:8080/search3').then(
            response => {
              console.log('请求成功了',response.data.data)
              this.tableData5=response.data.data
              this.tableDataxx=response.data.data
            },
            error => {
              console.log('请求失败了',error.message)
            }
          )
  },
  methods: {
    search(){
        // 数组长度
        this.tableDataxx=JSON.parse(JSON.stringify(this.tableData5))
        const length = this.tableDataxx.length
        for(var i = 0 ; i < length ; i++){
        // console.log(this.AccountData[i])
        console.log("xx",this.tableDataxx,"5",this.tableData5)
        var ttt=this.textxx.split("-")
        var ttt1=ttt[0]
        var ttt2=ttt[1]

        if( this.tableDataxx[0].crewid.includes(ttt1) &&  this.tableDataxx[0].crewbh.includes(ttt2)){
          //   下标为零表示 从 数组的新的第一个 判断 ，因为每次都把第一个删除
          this.tableDataxx.push(this.tableDataxx[0])
          this.tableDataxx.shift()     
        }else{
          this.tableDataxx.shift()   
        }
        }
        console.log("xx",this.tableDataxx,"5",this.tableData5)
    },

    handleEdit (index, row) {
      console.log(index, row)
      this.$message({
        showClose: true,
        message: index,
        row,
        type: "success"
      })
    },
    filterHandler (value, row, column) {
      const property = column["property"]
      return row[property] === value
    },
    formatter (row, column) {
      return row.address
    },
    filterTag (value, row) {
      return row.tag === value
    }
  }
}
</script>

<style scoped>

</style>

