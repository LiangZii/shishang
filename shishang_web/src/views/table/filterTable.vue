<template>
  <div>
    <h2>用户权限管理</h2>
        <!--<p>对表格进行筛选，可快速查找到自己想看的数据。</p> -->

    <template>

      <el-table
        :data="tableData5"
        style="width: 100%">

        <el-table-column
          prop="name"
          label="姓名"
          width="90">
        </el-table-column>

        <el-table-column
          prop="id"
          label="工号"
          width="50">
        </el-table-column>
        <el-table-column
          prop="role"
          label="角色"
          width="100">
        </el-table-column>
        <el-table-column
          prop="repairaction"
          label="维修动作"
          width="200">
        </el-table-column>
        <el-table-column
          prop="jurisdiction"
          label="当前拥有权限"
          width="360">
        </el-table-column>

        
        <el-table-column label="操作" width="85">
          
          <template slot-scope="scope">
            <el-button
              size="mini"
              @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
          
          </template>
        </el-table-column>
        
      </el-table>
    </template>

     <template>
  <el-dialog :title="title" width="800px" :visible.sync="visible" destroy-on-close @close="closeCallback">
    <div class="card">
    <p class="title"><i class="fa fa-th-large fa-lg"></i>用户权限信息</p>
    <el-form :model="ruleForm2" status-icon :rules="rules" ref="ruleForm2" label-width="100px" class="demo-ruleForm">
      <el-form-item label="姓名" style="width:700px">
        <el-input v-model="ruleForm2.xxname" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="工号" style="width:700px">
        <el-input v-model="ruleForm2.xxid" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="角色" style="width:700px">
        <el-input v-model="ruleForm2.xxrole" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="维修内容" style="width:700px">
        <el-input v-model="ruleForm2.xxWcontent" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="当前拥有权限" style="width:700px">
        <el-input v-model="ruleForm2.xxq1" autocomplete="off"></el-input>
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
      default: "账号信息"
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
        xxname:"",
        xxid:"",
        xxrole:"",
        xxWcontent:"",
        xxq1:"",
        xxindex:0
      },
      rules: {},
  
      tableData5: [{}]
    }
  },
  created (){
    axios.get('http://localhost:8080/filtertable').then(
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
    handleEdit (index, row) {
      this.visible=true
      this.xxindex=index
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
      this.tableData5.splice(this.xxindex,1)
      this.tableData5.splice(this.xxindex,0,{


        name:this.ruleForm2.xxname,
        id:this.ruleForm2.xxid,
        role:this.ruleForm2.xxrole,  
        Wcontent:this.ruleForm2.xxWcontent,
        q1:this.ruleForm2.xxq1,
    })
    },
  }
}
</script>

<style scoped>

</style>

