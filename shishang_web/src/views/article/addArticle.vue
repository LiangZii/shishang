<template>
  <div>
    <h2>设备基础信息      <el-button @click="xxhandleEdit()">新增</el-button></h2>
        <!--<p>对表格进行筛选，可快速查找到自己想看的数据</p> -->
        <hr>
        <br><br>
    
    <template>

      <el-table
        :data="tableData5"
        style="width: 100%">
        <el-table-column
          prop="crewbh"
          label="机组编号"
          width="50">
        </el-table-column>
        <el-table-column
          prop="crewid"
          label="风场编号"
          width="50">
        </el-table-column>
        <el-table-column
          prop="managerid"
          label="管理员编号"
          width="50">
        </el-table-column>
        <el-table-column
          prop="fieldname"
          label="风场名称"
          width="90">
        </el-table-column>
        <el-table-column
          prop="manufacturer"
          label="生产厂商"
          width="150">
        </el-table-column>
        <el-table-column
          prop="model"
          label="设备型号"
          width="70">
        </el-table-column>
        <el-table-column
          prop="installdate"
          label="安装日期"
          width="100">
        </el-table-column>
        <el-table-column
          prop="agelimit"
          label="使用年限"
          width="50">
        </el-table-column>
        <el-table-column
          prop="status"
          label="设备状态"
          width="50">
        </el-table-column>
        <el-table-column
          prop="address"
          label="安装地点"
          width="70">
        </el-table-column>
        <el-table-column
          prop="crewpower"
          label="机组发电量"
          width="80">
        </el-table-column>
        <el-table-column
          prop="sumpower"
          label="全场发电量"
          width="80">
        </el-table-column>
        
        
        <el-table-column label="操作" width="170">
          
          <template slot-scope="scope">
            <el-button
              size="mini"
              @click="handleEdit(scope.$index, scope.row)">{{scope.row.fieldname!="" ? "修改" : "新增"}}</el-button>
          <el-button
              type="danger"
              size="mini"
              v-role-btn="['superAdmin','admin']"
              @click="deletexx(scope.$index, scope.row)">删除</el-button>

          </template>
        </el-table-column>

        
      </el-table>
    </template>
    <!--xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx-->
    <template>
  <el-dialog :title="title" width="800px" :visible.sync="visible" destroy-on-close @close="closeCallback">
    <div class="card">
    <p class="title"><i class="fa fa-th-large fa-lg"></i>管理设备信息</p>
    <el-form :model="ruleForm2" status-icon :rules="rules" ref="ruleForm2" label-width="100px" class="demo-ruleForm">
      <el-form-item label="机组编号" style="width:700px" >
        <el-input v-model="ruleForm2.xx1" autocomplete="off" :placeholder="xxindex==-1? '':xxx1"></el-input>
      </el-form-item>
      <el-form-item label="风场编号" style="width:700px">
        <el-input v-model="ruleForm2.xx2" autocomplete="off" :placeholder="xxindex==-1? '':xxx2"></el-input>
      </el-form-item>
      <el-form-item label="管理员编号" style="width:700px">
        <el-input v-model="ruleForm2.xx3" autocomplete="off" :placeholder="xxindex==-1? '':xxx3"></el-input>
      </el-form-item>
      <el-form-item label="风场名称" style="width:700px">
        <el-input v-model="ruleForm2.xx4" autocomplete="off" :placeholder="xxindex==-1? '':xxx4"></el-input>
      </el-form-item>
      <el-form-item label="生产厂商" style="width:700px">
        <el-input v-model="ruleForm2.xx5" autocomplete="off" :placeholder="xxindex==-1? '':xxx5"></el-input>
      </el-form-item>
      <el-form-item label="设备型号" style="width:700px">
        <el-input v-model="ruleForm2.xx6" autocomplete="off" :placeholder="xxindex==-1? '':xxx6"></el-input>
      </el-form-item>
      <el-form-item label="安装日期" style="width:700px">
        <el-input v-model="ruleForm2.xx7" autocomplete="off" :placeholder="xxindex==-1? '':xxx7"></el-input>
      </el-form-item>
      <el-form-item label="使用年限" style="width:700px">
        <el-input v-model="ruleForm2.xx8" autocomplete="off" :placeholder="xxindex==-1? '':xxx8"></el-input>
      </el-form-item>
      <el-form-item label="设备状态" style="width:700px">
        <el-input v-model="ruleForm2.xx9" autocomplete="off" :placeholder="xxindex==-1? '':xxx9"></el-input>
      </el-form-item>
      <el-form-item label="安装地点" style="width:700px">
        <el-input v-model="ruleForm2.xx10" autocomplete="off" :placeholder="xxindex==-1? '':xxx10"></el-input>
      </el-form-item>
      <el-form-item label="机组发电量" style="width:700px">
        <el-input v-model="ruleForm2.xx11" autocomplete="off" :placeholder="xxindex==-1? '':xxx11"></el-input>
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
      default: "设备信息"
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
          xx7:"",
          xx8:"",
          xx9:"",
          xx10:"",
          xx11:"",
          xx12:""
        
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
      xxx8:"",
      xxx9:"",
      xxx10:"",
      xxx11:"",

    }
  },
    computed:{
      xxholder() {
      return this.xxindex == -1
        ? ""
        : this.tableData5[this.xxindex].crewbh;
    },
  },
  created (){
    axios.get('http://localhost:8080/addarticle').then(
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
      console.log("天天天",this.tableData5[this.xxindex].crewbh,this.tableData5[this.xxindex].crewid)

      axios({
            url:'http://localhost:8080/addarticle/delete',
            method: 'post',
            data: {
              params:{
                crewbh:this.tableData5[this.xxindex].crewbh,
                crewid:this.tableData5[this.xxindex].crewid,
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
      this.ruleForm2.xx8=""
      this.ruleForm2.xx9=""
      this.ruleForm2.xx10=""
      this.ruleForm2.xx11=""
      this.ruleForm2.xx12=""
      this.xxindex=index
      this.visible=true
      this.ruleForm2.xx1=this.tableData5[this.xxindex].crewbh
      this.ruleForm2.xx2=this.tableData5[this.xxindex].crewid
      this.ruleForm2.xx3=this.tableData5[this.xxindex].managerid
      this.ruleForm2.xx4=this.tableData5[this.xxindex].fieldname
      this.ruleForm2.xx5=this.tableData5[this.xxindex].manufacturer
      this.ruleForm2.xx6=this.tableData5[this.xxindex].model
      this.ruleForm2.xx7=this.tableData5[this.xxindex].installdate
      this.ruleForm2.xx8=this.tableData5[this.xxindex].agelimit
      this.ruleForm2.xx9=this.tableData5[this.xxindex].status
      this.ruleForm2.xx10=this.tableData5[this.xxindex].address
      this.ruleForm2.xx11=this.tableData5[this.xxindex].crewpower

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
      this.ruleForm2.xx8=""
      this.ruleForm2.xx9=""
      this.ruleForm2.xx10=""
      this.ruleForm2.xx11=""
      this.ruleForm2.xx12=""

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
        console.log("请求发送")
        axios({
            url:'http://localhost:8080/addarticle/insert',
            method: 'post',
            data: {
                  crewbh:this.ruleForm2.xx1,
                  crewid:this.ruleForm2.xx2,
                  managerid:this.ruleForm2.xx3,
                  fieldname:this.ruleForm2.xx4,
                  manufacturer:this.ruleForm2.xx5,
                  model:this.ruleForm2.xx6,
                  installdate:this.ruleForm2.xx7,
                  agelimit:this.ruleForm2.xx8,
                  status:this.ruleForm2.xx9,
                  address: this.ruleForm2.xx10,
                  crewpower:this.ruleForm2.xx11,
                  sumpower:this.ruleForm2.xx12
                  //表中字段去掉了全场发电量：sumpower,返回空
                  },
      })

      var Date1 = setTimeout(()=> {
                    axios.get('http://localhost:8080/addarticle').then(
                    response => {
                      console.log('请求成功了',response.data.data)
                      this.tableData5=response.data.data
                      console.log('tabledata5是：',this.tableData5)
                      console.log("啦啦啦啦啦啦")
                    },
                    error => {
                      console.log('请求失败了',error.message)
                    }
                  )
                    },3000)  // 3秒后刷新
      
      }
      else{
      
    console.log("啦啦啦",this.tableData5[this.xxindex].crewbh)
      axios({
            url:'http://localhost:8080/addarticle/update',
            method: 'post',
            data: {
              primarykey:this.tableData5[this.xxindex].crewbh,
              params:{
                  crewbh:this.tableData5[this.xxindex].crewbh,
                  crewid:this.ruleForm2.xx2,
                  managerid:this.ruleForm2.xx3,
                  fieldname:this.ruleForm2.xx4,
                  manufacturer:this.ruleForm2.xx5,
                  model:this.ruleForm2.xx6,
                  installdate:this.ruleForm2.xx7,
                  agelimit:this.ruleForm2.xx8,
                  status:this.ruleForm2.xx9,
                  address: this.ruleForm2.xx10,
                  crewpower:this.ruleForm2.xx11,
                  sumpower:this.ruleForm2.xx12
                  //修改，返回主键，crewbh
                  //表中字段去掉了全场发电量：sumpower,返回空
              }
                  },
      })
        console.log("xxindex是：",this.xxindex)
      this.tableData5.splice(this.xxindex,1)
      this.tableData5.splice(this.xxindex,0,{
                  crewbh:this.ruleForm2.xx1,
                  crewid:this.ruleForm2.xx2,
                  managerid:this.ruleForm2.xx3,
                  fieldname:this.ruleForm2.xx4,
                  manufacturer:this.ruleForm2.xx5,
                  model:this.ruleForm2.xx6,
                  installdate:this.ruleForm2.xx7,
                  agelimit:this.ruleForm2.xx8,
                  status:this.ruleForm2.xx9,
                  address: this.ruleForm2.xx10,
                  crewpower:this.ruleForm2.xx11,
                  sumpower:this.ruleForm2.xx12
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

