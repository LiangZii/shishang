<template>
  <el-dialog :title="title" width="800px" :visible.sync="visible" destroy-on-close @close="closeCallback">
    <div class="card">
    <p class="title"><i class="fa fa-th-large fa-lg"></i>检修信息</p>
    <el-form :model="ruleForm2" status-icon :rules="rules" ref="ruleForm2" label-width="100px" class="demo-ruleForm">
      <el-form-item label="工作任务">
        <el-input v-model="ruleForm2.xxGtask" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="人员">
        <el-input v-model="ruleForm2.xxpeo" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="检修内容">
        <el-input v-model="ruleForm2.xxcontent" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="检修周期">
        <el-input v-model="ruleForm2.xxperiod" autocomplete="off"></el-input>
      </el-form-item>
      
      <el-form-item label="时间">
        <el-input v-model="ruleForm2.xxtime" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="备注">
        <el-input v-model="ruleForm2.xxbz" autocomplete="off"></el-input>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="submitForm()">确 定</el-button>
      </el-form-item>
    </el-form>
    </div>
  </el-dialog>
</template>

<script>
import axios from 'axios'
import mock from '@/mock/index.js'
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
      visible: this.dialogVisible,
      ruleForm2: {
        xxGtask:"",
        xxpeo:"",
        xxcontent:"",
        xxperiod:"",
        xxtime:"",
        xxbz:""
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
    submitForm () {
      axios({
            url:'http://localhost:8080/erji2',
            method: 'post',
            data: {
                  task:this.ruleForm2.xxGtask,
                  name:this.ruleForm2.xxpeo,
                  content:this.ruleForm2.xxcontent,
                  period:this.ruleForm2.xxperiod,
                  date:this.ruleForm2.xxtime,
                  b1:this.ruleForm2.xxbz
                  },
      })

  }
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