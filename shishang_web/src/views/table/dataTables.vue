<template>
  <div>
    <h2>用户信息管理      <el-button @click="xxhandleEdit()">新增</el-button></h2>
        <!--<p>对表格进行筛选，可快速查找到自己想看的数据。</p> -->
        <hr>
        <br><br>

    <template>

      <el-table
        :data="tableData5"
        style="width: 90%">
        <el-table-column
          prop="id"
          label="工号"
          width="50">
        </el-table-column>
        <el-table-column
          prop="password"
          label="密码"
          width="150">
        </el-table-column>
        <el-table-column
          prop="name"
          label="姓名"
          width="90">
        </el-table-column>
        <el-table-column
          prop="role"
          label="角色"
          width="100">
        </el-table-column>
        <el-table-column
          prop="jurisdiction"
          label="权限"
          width="300">
        </el-table-column>

        
        
        <el-table-column label="操作" width="170">
          
          <template slot-scope="scope">
            <el-button
              size="mini"
              @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
            <el-button
              type="danger"
              size="mini"
              @click="deletexx(scope.$index, scope.row)">删除</el-button>
          
          </template>
        </el-table-column>
        
      </el-table>
    </template>

     <template>
  <el-dialog :title="title" width="800px" :visible.sync="visible" destroy-on-close @close="closeCallback">
    <div class="card">
    <p class="title"><i class="fa fa-th-large fa-lg"></i>管理用户信息</p>
    <el-form :model="ruleForm2" status-icon :rules="rules" ref="ruleForm2" label-width="100px" class="demo-ruleForm">
      <el-form-item label="工号" style="width:700px">
        <el-input v-model="ruleForm2.xx1" autocomplete="off" :placeholder="xxindex==-1? '':xxx1"></el-input>
      </el-form-item>
      <el-form-item label="密码" style="width:700px">
        <el-input v-model="ruleForm2.xx2" autocomplete="off" :placeholder="xxindex==-1? '':xxx2"></el-input>
      </el-form-item>
      <el-form-item label="姓名" style="width:700px">
        <el-input v-model="ruleForm2.xx3" autocomplete="off" :placeholder="xxindex==-1? '':xxx3"></el-input>
      </el-form-item>
      <el-form-item label="角色" style="width:700px">
        <el-input v-model="ruleForm2.xx4" autocomplete="off" :placeholder="xxindex==-1? '':xxx4"></el-input>
      </el-form-item>


      <el-form-item>
        <el-button type="primary" @click="submitForm()">确 定</el-button>
      </el-form-item>
    </el-form>
    </div>
  </el-dialog>
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
      default: "用户信息"
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
      visible: this.dialogVisible,
      ruleForm2: {
        xx1:"",
        xx2:"",
        xx3:"",
        xx4:"",
        xx5:""
        
      },
      rules: {},
      xxindex:0,
  
      tableData5: [{}],
      xxx1:"",
      xxx2:"",
      xxx3:"",
      xxx4:"",

    }
  },
  computed:{
      xxholder() {
      return this.xxindex == -1
        ? ""
        : this.tableData5[this.xxindex].id;
    },
  },
  created (){
    axios.get('http://localhost:8080/datatable').then(
            response => {
              console.log('请求成功了',response.data.data)
              this.tableData5=response.data.data
            },
            error => {
              console.log('请求失败了',error.message)
            }
          )
  },
  methods: {
    deletexx(index,row){
      this.xxindex=index
      console.log("天天天",this.tableData5[this.xxindex].id)
      axios({
            url:'http://localhost:8080/datatable/delete',
            method: 'post',
            data: {
              params:{
                id:this.tableData5[this.xxindex].id,
              }
              
              }
      })
      this.tableData5.splice(index,1)
    },
    handleEdit (index, row) {
      this.ruleForm2.xx1=""
      this.ruleForm2.xx2=""
      this.ruleForm2.xx3=""
      this.ruleForm2.xx4=""

      this.xxindex=index
      this.visible=true

      this.ruleForm2.xx1=this.tableData5[this.xxindex].id
      this.ruleForm2.xx2=this.tableData5[this.xxindex].password
      this.ruleForm2.xx3=this.tableData5[this.xxindex].name
      this.ruleForm2.xx4=this.tableData5[this.xxindex].role
    },
    xxhandleEdit()
    {
      this.ruleForm2.xx1=""
      this.ruleForm2.xx2=""
      this.ruleForm2.xx3=""
      this.ruleForm2.xx4=""

      this.xxindex=-1
      this.visible=true
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
    },
    submitForm(){
      if(this.xxindex==-1){
        this.$message({
        showClose: true,
        message: "新增成功",
        type: "success"
      })
      }
      else{
        this.$message({
        showClose: true,
        message: "修改成功",
        type: "success"
      })
      }
      if(this.xxindex==-1)
      {
        axios({
            url:'http://localhost:8080/datatable/insert',
            method: 'post',
            data: {
                  id:this.ruleForm2.xx1,
                  password:this.ruleForm2.xx2,
                  name:this.ruleForm2.xx3,
                  role:this.ruleForm2.xx4,
                  jurisdiction:this.ruleForm2.xx5,
                  },
      })
      var Date1 = setTimeout(()=> {
                    axios.get('http://localhost:8080/datatable').then(
                    response => {
                      console.log('请求成功了',response.data.data)
                      this.tableData5=response.data.data
                      console.log('tabledata5是：',this.tableData5)
                    },
                    error => {
                      console.log('请求失败了',error.message)
                    }
                  )
                    },3000)  // 3秒后刷新
      
      }
      else{
      
      console.log("啦啦啦",this.tableData5[this.xxindex].id)
      axios({
            url:'http://localhost:8080/datatable/update',
            method: 'post',
            data: {
              primarykey:this.tableData5[this.xxindex].id,
              params:{
                  id:this.tableData5[this.xxindex].id,
                  password:this.ruleForm2.xx2,
                  name:this.ruleForm2.xx3,
                  role:this.ruleForm2.xx4,
                  jurisdiction:this.ruleForm2.xx5,
              }
                  },
      })
        console.log("xxindex是：",this.xxindex)
      this.tableData5.splice(this.xxindex,1)
      this.tableData5.splice(this.xxindex,0,{
                  id:this.ruleForm2.xx1,
                  password:this.ruleForm2.xx2,
                  name:this.ruleForm2.xx3,
                  role:this.ruleForm2.xx4,
                  jurisdiction:this.ruleForm2.xx5,
    })
    }
      
    },
    closeCallback () {
      this.$emit("successCallback")
    },
  }
}
</script>

<style scoped>

</style>

