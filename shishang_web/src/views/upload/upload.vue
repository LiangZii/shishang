<template>
  <div>
    <h2>备件基础资料信息管理      <el-button @click="xxhandleEdit()">新增</el-button></h2>
        <!--<p>对表格进行筛选，可快速查找到自己想看的数据。</p> -->
        <hr>
        <br><br>
    <template>

      <el-table
        :data="tableData5"
        style="width: 100%">

        <el-table-column
          prop="partid"
          label="备件id"
          width="50">
        </el-table-column>
        <el-table-column
          prop="name"
          label="中文名称"
          width="50">
        </el-table-column>
        <el-table-column
          prop="model"
          label="规格型号"
          width="50">
        </el-table-column>
        <el-table-column
          prop="quantity"
          label="数量"
          width="50">
        </el-table-column>
        <el-table-column
          prop="unit"
          label="单位"
          width="50">
        </el-table-column>
        <el-table-column
          prop="price"
          label="单价"
          width="50">
        </el-table-column>
        <el-table-column
          prop="indate"
          label="入库时间"
          width="100">
        </el-table-column>
        <el-table-column
          prop="outdate"
          label="出库时间"
          width="100">
        </el-table-column>
        <el-table-column
          prop="useitem"
          label="适用风机型号"
          width="100">
        </el-table-column>
        <el-table-column
          prop="sapid"
          label="SAP物料代码"
          width="110">
        </el-table-column>
        <el-table-column
          prop="stationid"
          label="场站物料代码"
          width="110">
        </el-table-column>
        <el-table-column
          prop="manufactorid"
          label="厂家物料编码"
          width="110">
        </el-table-column>
        <el-table-column
          prop="type"
          label="备件类型"
          width="50">
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
    <p class="title"><i class="fa fa-th-large fa-lg"></i>管理备件信息</p>
    <el-form :model="ruleForm2" status-icon :rules="rules" ref="ruleForm2" label-width="100px" class="demo-ruleForm">
      <el-form-item label="备件id" style="width:700px">
        <el-input v-model="ruleForm2.xx1" autocomplete="off" :placeholder="xxindex==-1? '':xxx1"></el-input>
      </el-form-item>
      <el-form-item label="中文名称" style="width:700px">
        <el-input v-model="ruleForm2.xx2" autocomplete="off" :placeholder="xxindex==-1? '':xxx2"></el-input>
      </el-form-item>
      <el-form-item label="规格型号" style="width:700px">
        <el-input v-model="ruleForm2.xx3" autocomplete="off" :placeholder="xxindex==-1? '':xxx3"></el-input>
      </el-form-item>
      <el-form-item label="数量" style="width:700px">
        <el-input v-model="ruleForm2.xx4" autocomplete="off" :placeholder="xxindex==-1? '':xxx4"></el-input>
      </el-form-item>
      <el-form-item label="单位" style="width:700px">
        <el-input v-model="ruleForm2.xx5" autocomplete="off" :placeholder="xxindex==-1? '':xxx5"></el-input>
      </el-form-item>
      <el-form-item label="单价" style="width:700px">
        <el-input v-model="ruleForm2.xx6" autocomplete="off" :placeholder="xxindex==-1? '':xxx6"></el-input>
      </el-form-item>
      <el-form-item label="入库时间" style="width:700px">
        <el-input v-model="ruleForm2.xx7" autocomplete="off" :placeholder="xxindex==-1? '':xxx7"></el-input>
      </el-form-item>
      <el-form-item label="出库时间" style="width:700px">
        <el-input v-model="ruleForm2.xx8" autocomplete="off" :placeholder="xxindex==-1? '':xxx8"></el-input>
      </el-form-item>
      <el-form-item label="适用风机型号" style="width:700px">
        <el-input v-model="ruleForm2.xx9" autocomplete="off" :placeholder="xxindex==-1? '':xxx9"></el-input>
      </el-form-item>
      <el-form-item label="SAP物料代码" style="width:700px">
        <el-input v-model="ruleForm2.xx10" autocomplete="off" :placeholder="xxindex==-1? '':xxx10"></el-input>
      </el-form-item>
      <el-form-item label="场站物料代码" style="width:700px">
        <el-input v-model="ruleForm2.xx11" autocomplete="off" :placeholder="xxindex==-1? '':xxx11"></el-input>
      </el-form-item>
      <el-form-item label="厂家物料编码" style="width:700px">
        <el-input v-model="ruleForm2.xx12" autocomplete="off" :placeholder="xxindex==-1? '':xxx12"></el-input>
      </el-form-item>
      <el-form-item label="备件类型" style="width:700px">
        <el-input v-model="ruleForm2.xx13" autocomplete="off" :placeholder="xxindex==-1? '':xxx13"></el-input>
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
      default: "备件信息"
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
          xx12:"",
          xx13:""
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
      xxx12:"",
      xxx13:""
    }
  },
  computed:{
      xxholder() {
      return this.xxindex == -1
        ? ""
        : this.tableData5[this.xxindex].partid;
    },
  },
  created (){
    axios.get('http://localhost:8080/upload').then(
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
      console.log("天天天",this.tableData5[this.xxindex].partid)
      axios({
            url:'http://localhost:8080/upload/delete',
            method: 'post',
            data: {
              params:{
                partid:this.tableData5[this.xxindex].partid,
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
      this.ruleForm2.xx13=""

      this.xxindex=index
      this.visible=true

      this.ruleForm2.xx1=this.tableData5[this.xxindex].partid
      this.ruleForm2.xx2=this.tableData5[this.xxindex].name
      this.ruleForm2.xx3=this.tableData5[this.xxindex].model
      this.ruleForm2.xx4=this.tableData5[this.xxindex].quantity
      this.ruleForm2.xx5=this.tableData5[this.xxindex].unit
      this.ruleForm2.xx6=this.tableData5[this.xxindex].price
      this.ruleForm2.xx7=this.tableData5[this.xxindex].indate
      this.ruleForm2.xx8=this.tableData5[this.xxindex].outdate
      this.ruleForm2.xx9=this.tableData5[this.xxindex].useitem
      this.ruleForm2.xx10=this.tableData5[this.xxindex].sapid
      this.ruleForm2.xx11=this.tableData5[this.xxindex].stationid
      this.ruleForm2.xx12=this.tableData5[this.xxindex].manufactorid
      this.ruleForm2.xx13=this.tableData5[this.xxindex].type

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
      this.ruleForm2.xx13=""

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
            url:'http://localhost:8080/upload/insert',
            method: 'post',
            data: {
                  partid:this.ruleForm2.xx1,
                  name:this.ruleForm2.xx2,
                  model:this.ruleForm2.xx3,
                  quantity:this.ruleForm2.xx4,
                  unit:this.ruleForm2.xx5,
                  price:this.ruleForm2.xx6,
                  indate:this.ruleForm2.xx7,
                  outdate:this.ruleForm2.xx8,
                  useitem:this.ruleForm2.xx9,
                  sapid:this.ruleForm2.xx10,
                  stationid:this.ruleForm2.xx11,
                  manufactorid:this.ruleForm2.xx12,
                  type:this.ruleForm2.xx13
                  },
      })
      var Date1 = setTimeout(()=> {
                    axios.get('http://localhost:8080/upload').then(
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
      
      console.log("啦啦啦",this.tableData5[this.xxindex].partid)
      axios({
            url:'http://localhost:8080/upload/update',
            method: 'post',
            data: {
              primarykey:this.tableData5[this.xxindex].partid,
              params:{
                  partid:this.tableData5[this.xxindex].partid,
                  name:this.ruleForm2.xx2,
                  model:this.ruleForm2.xx3,
                  quantity:this.ruleForm2.xx4,
                  unit:this.ruleForm2.xx5,
                  price:this.ruleForm2.xx6,
                  indate:this.ruleForm2.xx7,
                  outdate:this.ruleForm2.xx8,
                  useitem:this.ruleForm2.xx9,
                  sapid:this.ruleForm2.xx10,
                  stationid:this.ruleForm2.xx11,
                  manufactorid:this.ruleForm2.xx12,
                  type:this.ruleForm2.xx13
              }
                  },
      })
        console.log("xxindex是：",this.xxindex)
      this.tableData5.splice(this.xxindex,1)
      this.tableData5.splice(this.xxindex,0,{
                  partid:this.ruleForm2.xx1,
                  name:this.ruleForm2.xx2,
                  model:this.ruleForm2.xx3,
                  quantity:this.ruleForm2.xx4,
                  unit:this.ruleForm2.xx5,
                  price:this.ruleForm2.xx6,
                  indate:this.ruleForm2.xx7,
                  outdate:this.ruleForm2.xx8,
                  useitem:this.ruleForm2.xx9,
                  sapid:this.ruleForm2.xx10,
                  stationid:this.ruleForm2.xx11,
                  manufactorid:this.ruleForm2.xx12,
                  type:this.ruleForm2.xx13
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

