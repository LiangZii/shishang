<template>
  <div>
    <h2>用户信息       </h2>
        <!--<p>对表格进行筛选，可快速查找到自己想看的数据。</p> -->
        <hr>
        <br><br>

        

    <template>
      <el-table
        :cell-style="tableRowClassName"
        :data="tableData5"
        style="width: 100%"
        >

      <el-table-column
        prop="userId"
        label="用户编号"
        width="200">
      </el-table-column>
      <el-table-column
          prop="name"
          label="用户名"
          width="200">
        </el-table-column>
        <el-table-column
          prop="password"
          label="密码"
          width="60">
        </el-table-column>
        <el-table-column
          prop="register"
          label="注册时间"
          width="200">
        </el-table-column>

        <el-table-column
          prop="star"
          label="菜谱收藏数目"
          width="110">
        </el-table-column>
        <el-table-column
          prop="note"
          label="发布笔记数目"
          width="110">
        </el-table-column>

        
        
        
        <el-table-column label="操作" width="170">
          
          <template slot-scope="scope">
            
            <el-button
              type="danger"
              size="mini"
              v-role-btn="['superAdmin','admin']"
              @click="deletexx(scope.$index, scope.row)">封禁</el-button>
          
          </template>
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
  props: {
    title: {
      type: String,
      default: "维修信息"
    },
    dialogVisible: {
      type: Boolean,
      default: false
    },
    userId: {
      type: String,
      default: ""
    }
  },
  data () {
    return {
      roleName: false,
      roleData: "",
      
      rules: {},
      xxindex:-1,
      tableData5: [{}],

      dialogVisible: false,
      rowxx:-1
      
    }
  },
 
  created (){
    axios.get('http://8.130.113.124:8180/users/selectAll').then(
            response => {
              console.log('请求成功了',response.data.data)
              this.tableData5=response.data.data
              
              this.tableData5[0].password="******"
              this.tableData5[1].password="******"
              this.tableData5[2].password="******"
              this.tableData5[3].password="******"
              this.tableData5[4].password="******"
              this.tableData5[5].password="******"
              this.tableData5[6].password="******"
              this.tableData5[7].password="******"
              this.tableData5[8].password="******"
              this.tableData5[9].password="******"
              this.tableData5[10].password="******"
              this.tableData5[11].password="******"
              this.tableData5[12].password="******"
              this.tableData5[13].password="******"
              this.tableData5[14].password="******"
              this.tableData5[15].password="******"
              this.tableData5[16].password="******"
              this.tableData5[17].password="******"
              this.tableData5[18].password="******"
              this.tableData5[19].password="******"
              this.tableData5[20].password="******"
              this.tableData5[21].password="******"

            },
            error => {
              console.log('请求失败了',error.message)
            }
          )
  },
  methods: {
     
     tableRowClassName({row}){
        return row.userId==this.xxindex?'color: red;':'color: black'
      },

      deletexx(index,row){
        

      this.$confirm("确认封禁？")
        .then(_ => {
          this.xxindex=this.tableData5[index].userId;
          this.tableRowClassName(this.rowxx)
        })
        .catch(_ => {});
      },
      

   
  }
}
</script>

<style scoped>

</style>

