<template>
  <div>
      <!-- 卡片视图 -->
      <el-card>
          <!-- 提示区域 -->
          <el-alert title="添加菜谱信息" type="info" center show-icon :closable="false"></el-alert>

          <!-- 步骤条区域 -->
          <el-steps :space="300" :active="activeIndex - 0" finish-status="success" align-center>
              <el-step title="填写菜谱基本信息"></el-step>
              <el-step title="填写菜谱步骤"></el-step>
              <el-step title="完成"></el-step>
          </el-steps>

          <!-- tab栏区域 -->
          <el-tabs :tab-position="'left'" v-model="activeIndex" style="height: 800px;">

      <el-tab-pane 
        label="基本信息" name='0'>

    <div class="card1">
    <p class="title"><i class="fa fa-th-large fa-lg"></i></p>
    <el-form :model="ruleForm2" status-icon :rules="rules" ref="ruleForm2" label-width="100px" class="demo-ruleForm">
      <el-form-item label="菜谱编号" style="width:700px">
        <el-input v-model="ruleForm2.xx1" autocomplete="off" :placeholder="xxindex==-1? '':xxx2"></el-input>
      </el-form-item>
      <el-form-item label="菜谱名称" style="width:700px">
        <el-input v-model="ruleForm2.xx2" autocomplete="off" :placeholder="xxindex==-1? '':xxx2"></el-input>
      </el-form-item>
      <el-form-item label="封面图片" style="width:700px">
        <el-upload
          action=""
          list-type="picture-card"
          :on-preview="handlePictureCardPreview"
          :file-list="fileList"
          :on-success="handleSuccess"
          :on-remove="handleRemove"
          :on-change="UploadImage1">
          <img v-if="license1" :src="license1" style="width:100%;height:100%;border-radius: 1rem;">
          <i v-else class="el-icon-plus"></i>
        </el-upload>

      </el-form-item>
      <el-form-item label="食材" style="width:700px">
        <el-input v-model="ruleForm2.xx3" autocomplete="off" :placeholder="xxindex==-1? '':xxx4"></el-input>
      </el-form-item>
      <el-form-item label="综合评分" style="width:700px">
        <el-input v-model="ruleForm2.xx4" autocomplete="off" :placeholder="xxindex==-1? '':xxx5"></el-input>
      </el-form-item>

    </el-form>

    
    </div>
    
     </el-tab-pane>
              
              <el-tab-pane label="菜谱步骤" name='1'>

    <div class="card2">
    <p class="title"><i class="fa fa-th-large fa-lg"></i></p>
    <el-form :model="stepxx" status-icon :rules="rules" ref="ruleForm2" label-width="100px" class="demo-ruleForm">
      <el-form-item label="步骤一" style="width:700px">
        <el-input v-model="stepxx.xx1" autocomplete="off" :placeholder="xxindex==-1? '':xxx2"></el-input>
      </el-form-item>

      <el-form-item label="图片" style="width:700px">
        <el-upload
          action=""
          list-type="picture-card"
          :file-list="fileList"
          :on-success="handleSuccess"
          :on-remove="handleRemove"
          :on-change="UploadImage2">
          <img v-if="license2" :src="license2" style="width:100%;height:100%;border-radius: 1rem;">
          <i v-else class="el-icon-plus"></i>
        </el-upload>

      </el-form-item>

      <el-form-item label="步骤二" style="width:700px">
        <el-input v-model="stepxx.xx2" autocomplete="off" :placeholder="xxindex==-1? '':xxx2"></el-input>
      </el-form-item>

      <el-form-item label="图片" style="width:700px">
        <el-upload
          action=""
          list-type="picture-card"
          :file-list="fileList"
          :on-success="handleSuccess"
          :on-remove="handleRemove"
          :on-change="UploadImage3">
          <img v-if="license3" :src="license3" style="width:100%;height:100%;border-radius: 1rem;">
          <i v-else class="el-icon-plus"></i>
        </el-upload>

      </el-form-item>

      <el-form-item label="步骤三" style="width:700px">
        <el-input v-model="stepxx.xx3" autocomplete="off" :placeholder="xxindex==-1? '':xxx2"></el-input>
      </el-form-item>

      <el-form-item label="图片" style="width:700px">
        <el-upload
          action=""
          list-type="picture-card"
          :file-list="fileList"
          :on-success="handleSuccess"
          :on-remove="handleRemove"
          :on-change="UploadImage4">
          <img v-if="license4" :src="license4" style="width:100%;height:100%;border-radius: 1rem;">
          <i v-else class="el-icon-plus"></i>
        </el-upload>
        

      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="submitForm()">确 定</el-button>
      </el-form-item>
    </el-form>
    </div>

  </el-tab-pane>

          </el-tabs>
        </el-card>
  </div>
</template>

<script>
import axios from 'axios'
import mock from '@/mock/index.js'
export default {
data() {
  return {
    activeIndex: '0',

    pictureurlxx:"",
    pictureurl:"",

    license1:"",
    license2:"",
    license3:"",
    license4:"",
    fileList:[],

    ruleForm2: {
          xx1:"",
          xx2:"",
          xx3:"",
          xx4:""
    },
    stepxx: {
          xx1:"",
          xx2:"",
          xx3:""
    }
  }
  
},
methods:{

  handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },

  //上传图片的方法
  UploadImage1(file,filelist) {

      
  let fd = new FormData()
  fd.append('photo', file.raw) //传给后台接收的名字 file
  axios.post('http://8.130.113.124:8180/web/uploadImage', fd, {headers: {'Content-Type': 'multipart/form-data'}}).then(response => {
  //上传成功后返回的数据,
  console.log("上传图片到:"+response.data);
  // 将图片地址给到表单.
  this.pictureurlxx=response.data
  console.log("滴滴滴",this.pictureurl)
  this.license1=response.data
  },
  
  error => {
  console.log('图片请求失败了',error.message)
  }
)

},

UploadImage2(file,filelist) {

      
let fd = new FormData()
fd.append('photo', file.raw) //传给后台接收的名字 file
axios.post('http://8.130.113.124:8180/web/uploadImage', fd, {headers: {'Content-Type': 'multipart/form-data'}}).then(response => {
//上传成功后返回的数据,
console.log("上传图片到:"+response.data);
// 将图片地址给到表单.
this.pictureurl=this.pictureurl+response.data+';'
console.log("滴滴滴",this.pictureurl)
this.license2=response.data
},

error => {
console.log('图片请求失败了',error.message)
}
)

},

UploadImage3(file,filelist) {

      
let fd = new FormData()
fd.append('photo', file.raw) //传给后台接收的名字 file
axios.post('http://8.130.113.124:8180/web/uploadImage', fd, {headers: {'Content-Type': 'multipart/form-data'}}).then(response => {
//上传成功后返回的数据,
console.log("上传图片到:"+response.data);
// 将图片地址给到表单.
this.pictureurl=this.pictureurl+response.data+';'
console.log("滴滴滴",this.pictureurl)
this.license3=response.data
},

error => {
console.log('图片请求失败了',error.message)
}
)

},

UploadImage4(file,filelist) {

      
let fd = new FormData()
fd.append('photo', file.raw) //传给后台接收的名字 file
axios.post('http://8.130.113.124:8180/web/uploadImage', fd, {headers: {'Content-Type': 'multipart/form-data'}}).then(response => {
//上传成功后返回的数据,
console.log("上传图片到:"+response.data);
// 将图片地址给到表单.
this.pictureurl=this.pictureurl+response.data+';'
console.log("滴滴滴",this.pictureurl)
this.license4=response.data
},

error => {
console.log('图片请求失败了',error.message)
}
)

},


submitForm(){
  console.log("111",this.ruleForm2.xx1);
  console.log("222",this.ruleForm2.xx2);
  console.log("333",this.ruleForm2.xx3);
  console.log("444",this.ruleForm2.xx4);
  console.log("555",this.stepxx.xx1+';'+this.stepxx.xx2+';'+this.stepxx.xx3);
  console.log("666",this.pictureurl);


  axios({
            url:'http://8.130.113.124:8180/web/publish',
            method: 'post',
            data: {
                  src:this.ruleForm2.xx1,
                  name:this.ruleForm2.xx2,
                  foods:this.ruleForm2.xx3,
                  message:this.ruleForm2.xx4,

                  methodMessage:this.stepxx.xx1+';'+this.stepxx.xx2+';'+this.stepxx.xx3,
                  methodSrc:this.pictureurl,
                  img:this.pictureurlxx
                  },
      })
}

  
},

}
</script>

<style lang="less" scoped>

</style>

