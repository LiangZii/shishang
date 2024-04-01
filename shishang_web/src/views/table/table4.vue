<template>
  <el-dialog :title="title" width="800px" :visible.sync="visible" destroy-on-close @close="closeCallback">
    <div class="card">
    <p class="title"><i class="fa fa-th-large fa-lg"></i>机构资料</p>
    <el-form :model="ruleForm2" status-icon :rules="rules" ref="ruleForm2" label-width="100px" class="demo-ruleForm">
      <el-form-item label="机构编号" prop="x1">
        <el-input v-model="ruleForm2.xx1" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="机构名称" prop="x2">
        <el-input v-model="ruleForm2.xx2" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="机构主要职能" prop="x3">
        <el-input v-model="ruleForm2.xx3" autocomplete="off"></el-input>
      </el-form-item>
      
      <el-form-item label="机构负责人" prop="x4">
        <el-input v-model="ruleForm2.xx4" autocomplete="off"></el-input>
      </el-form-item>
      
      <el-form-item label="手机号" prop="x5">
        <el-input v-model="ruleForm2.xx5" autocomplete="off"></el-input>
      </el-form-item>

      
      <el-form-item>
        <el-button type="primary" @click="submitForm('ruleForm2')">确 定</el-button>
      </el-form-item>
    </el-form>
    </div>
  </el-dialog>
</template>

<script>
import axios from 'axios'
export default {
  name: "userInfo",
  props: {
    title: {
      type: String,
      default: "账号信息"
    },
    dialogVisible: {
      type: Boolean,
      default: true
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
      rules: {}
    }
  },
  methods: {
    handleAvatarSuccess (res, file) {
      this.ruleForm2.avatar = res.data[0]
    },
    closeCallback () {
      this.$emit("successCallback")
    },
    
    submitForm (formName) {
        this.$message({
        showClose: true,
        message: "新增成功",
        type: "success"
      })

      axios({
            url:'http://localhost:8080/erji2',
            method: 'post',
            data: {
              jigouid:this.ruleForm2.xx1,
              name:this.ruleForm2.xx2,
              function:this.ruleForm2.xx3,
              manageri:this.ruleForm2.xx4,
              phone:this.ruleForm2.xx5
                  },
      })
    },
    resetForm (formName) {
      this.$refs[formName].resetFields()
    }
  },
  mounted () {
    // this.getList()
  }
}
</script>
<style scoped>
  .demo-ruleForm {
    width: 460px;
    padding-top: 25px;
  }
  .el-select {
    width: 100%;
  }
  .card {
    width: 560px;
    padding-bottom: 15px;
    margin: 0px auto;
  }
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
</style>
