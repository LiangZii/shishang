<template>
  <div>
    <h2>检修信息管理       <el-button @click="xxhandleEdit()">新增</el-button></h2>
        <!--<p>对表格进行筛选，可快速查找到自己想看的数据。</p> -->
        <hr>
        <br><br>

    <template>

      <el-table
        :data="tableData5"
        style="width: 100%">

        <el-table-column
          prop="haulid"
          label="检修人员id"
          width="100">
        </el-table-column>
        <el-table-column
          prop="people"
          label="人员"
          width="100">
        </el-table-column>
        <el-table-column
          prop="mission"
          label="工作任务"
          width="150">
        </el-table-column>
        <el-table-column
          prop="regular"
          label="检修周期"
          width="100">
        </el-table-column>
        <el-table-column
          prop="comment"
          label="备注"
          width="100">
        </el-table-column>
        <el-table-column
          prop="content"
          label="检修内容"
          width="200">
        </el-table-column>
        <el-table-column
          prop="time"
          label="时间"
          width="150">
        </el-table-column>
        
        
        
        <el-table-column label="操作" width="170">
          
          <template slot-scope="scope">
            <el-button
              size="mini"
              @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
            <el-button
              type="danger"
              size="mini"
              v-role-btn="['superAdmin','admin']"
              @click="deletexx(scope.$index, scope.row)">删除</el-button>
          
          </template>
        </el-table-column>
        
      </el-table>
    </template>
     <template>
  <el-dialog :title="title" width="800px" :visible.sync="visible" destroy-on-close @close="closeCallback">
    <div class="card">
    <p class="title"><i class="fa fa-th-large fa-lg"></i>管理检修信息</p>

    <el-form :model="ruleForm2" status-icon :rules="rules" ref="ruleForm2" label-width="100px" class="demo-ruleForm">
      <el-form-item label="检修人员id" style="width:700px">
        <el-input v-model="ruleForm2.xx1" autocomplete="off" :placeholder="xxindex==-1? '':xxx1"></el-input>
      </el-form-item>
      <el-form-item label="人员" style="width:700px">
        <el-input v-model="ruleForm2.xx2" autocomplete="off" :placeholder="xxindex==-1? '':xxx2"></el-input>
      </el-form-item>
      <el-form-item label="工作任务" style="width:700px">
        <el-input v-model="ruleForm2.xx3" autocomplete="off" :placeholder="xxindex==-1? '':xxx3"></el-input>
      </el-form-item>
      <el-form-item label="检修周期" style="width:700px">
        <el-input v-model="ruleForm2.xx4" autocomplete="off" :placeholder="xxindex==-1? '':xxx4"></el-input>
      </el-form-item>
      <el-form-item label="备注" style="width:700px">
        <el-input v-model="ruleForm2.xx5" autocomplete="off" :placeholder="xxindex==-1? '':xxx5"></el-input>
      </el-form-item>
      <el-form-item label="检修内容" style="width:700px">
        <el-input v-model="ruleForm2.xx6" autocomplete="off" :placeholder="xxindex==-1? '':xxx6"></el-input>
      </el-form-item>
      <el-form-item label="时间" style="width:700px">
        <el-input v-model="ruleForm2.xx7" autocomplete="off" :placeholder="xxindex==-1? '':xxx7"></el-input>
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
      default: "检修信息"
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
        xx5:"",
        xx6:"",
        xx7:""
      },
      rules: {},
      xxindex:-1,
      tableData5: [{}],
      xxx1:"",
      xxx2:"",
      xxx3:"",
      xxx4:"",
      xxx5:"",
      xxx6:"",
      xxx7:"",

    }
  },
  computed:{
      xxholder() {
      return this.xxindex == -1
        ? ""
        : this.tableData5[this.xxindex].haulid;
    },
  },
  created (){
    axios.get('http://localhost:8080/erji').then(
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
      console.log("天天天",this.tableData5[this.xxindex].haulid)
      axios({
            url:'http://localhost:8080/erji/delete',
            method: 'post',
            data: {
              params:{
                haulid:this.tableData5[this.xxindex].haulid,
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
      this.ruleForm2.xx5=""
      this.ruleForm2.xx6=""
      this.ruleForm2.xx7=""

      this.xxindex=index
      this.visible=true

      this.ruleForm2.xx1=this.tableData5[this.xxindex].haulid
      this.ruleForm2.xx2=this.tableData5[this.xxindex].people
      this.ruleForm2.xx3=this.tableData5[this.xxindex].mission
      this.ruleForm2.xx4=this.tableData5[this.xxindex].regular
      this.ruleForm2.xx5=this.tableData5[this.xxindex].comment
      this.ruleForm2.xx6=this.tableData5[this.xxindex].content
      this.ruleForm2.xx7=this.tableData5[this.xxindex].time

    },
    xxhandleEdit()
    {
      this.ruleForm2.xx1=""
      this.ruleForm2.xx2=""
      this.ruleForm2.xx3=""
      this.ruleForm2.xx4=""
      this.ruleForm2.xx5=""
      this.ruleForm2.xx6=""
      this.ruleForm2.xx7=""

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
            url:'http://localhost:8080/erji/insert',
            method: 'post',
            data: {
                  haulid:this.ruleForm2.xx1,
                  people:this.ruleForm2.xx2,
                  mission:this.ruleForm2.xx3,
                  regular:this.ruleForm2.xx4,
                  comment:this.ruleForm2.xx5,
                  content:this.ruleForm2.xx6,
                  time:this.ruleForm2.xx7,
                  },
      })
      var Date1 = setTimeout(()=> {
                    axios.get('http://localhost:8080/erji').then(
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
      
      console.log("啦啦啦",this.tableData5[this.xxindex].people)
      axios({
            url:'http://localhost:8080/erji/update',
            method: 'post',
            data: {
              primarykey:this.tableData5[this.xxindex].haulid,
              params:{
                  haulid:this.tableData5[this.xxindex].haulid,
                  people:this.ruleForm2.xx2,
                  mission:this.ruleForm2.xx3,
                  regular:this.ruleForm2.xx4,
                  comment:this.ruleForm2.xx5,
                  content:this.ruleForm2.xx6,
                  time:this.ruleForm2.xx7,
              }
                  },
      })
        console.log("xxindex是：",this.xxindex)
      this.tableData5.splice(this.xxindex,1)
      this.tableData5.splice(this.xxindex,0,{
                  haulid:this.ruleForm2.xx1,
                  people:this.ruleForm2.xx2,
                  mission:this.ruleForm2.xx3,
                  regular:this.ruleForm2.xx4,
                  comment:this.ruleForm2.xx5,
                  content:this.ruleForm2.xx6,
                  time:this.ruleForm2.xx7,
    })
    }
      
    },
    closeCallback () {
      this.$emit("successCallback")
    }
  }
}
</script>

<style scoped>

</style>

