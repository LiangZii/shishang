<template>
  <div>
    <h2>故障设备查询</h2>
        <!--<p>对表格进行筛选，可快速查找到自己想看的数据。</p> -->
    <hr>
    
        <a style="color:black">您要查询的故障名称</a>
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
        style="width: 100%">

        <el-table-column
          prop="faultid"
          label="故障编号"
          width="150">
        </el-table-column>
        <el-table-column
          prop="faultname"
          label="故障名称"
          width="150">
        </el-table-column>

        <el-table-column
          prop="comment"
          label="故障描述"
          width="250">
        </el-table-column>

        <el-table-column
          prop="handletime"
          label="开始处理时间"
          width="150">
        </el-table-column>

        <el-table-column
          prop="resetime"
          label="复位运行时间"
          width="150">
        </el-table-column>
        
        <el-table-column
          prop="lostpower"
          label="发电量损失"
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
    axios.get('http://localhost:8080/search1').then(
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
        this.tableDataxx= JSON.parse(JSON.stringify(this.tableData5))
        const length = this.tableDataxx.length
        for(var i = 0 ; i < length ; i++){
        // console.log(this.AccountData[i])
        console.log("xx",this.tableDataxx,"5",this.tableData5)
        if( this.tableDataxx[0].faultname.includes(this.textxx)){
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

