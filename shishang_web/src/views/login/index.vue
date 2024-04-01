<template>
  <div id="login">
    

    <div class="loginConbox">
      
      <div class="header">
        
        <!--<div class="logo">-->
        <!--<img src="../../assets/logo.png">-->
        <!--</div>-->
      </div>
      <div ref="vantaRef" style="width:100%;height:100vh">
      <div class="loginBox">
        
        <div class="loginCon">
        
          <p class="title" style="444444"><br><br>欢迎来到食尚后台管理系统</p>
          <p class="title">制作者：周宏伟,李子健，李亮</p>
          <br><br>
          <el-card shadow="always" class="login-module" v-if="smdl">
            <div slot="header" class="clearfix formTitlt">
              <span>密码登录</span>
              
            
            </div>
            <el-form :model="loginForm" status-icon label-width="100px" class="demo-ruleForm">
              <el-form-item>
                <el-input type="text" v-model="loginForm.username" auto-complete="off" placeholder="请输入登录账号"></el-input>
              </el-form-item>
              <el-form-item>
                <el-input type="password" v-model="loginForm.password" auto-complete="off"
                          placeholder="请输入登录密码"></el-input>
              </el-form-item>
              <el-form-item>
                <el-button class="subBtn" type="primary" @click="loginxx">登录</el-button>
              </el-form-item>
            </el-form>
          </el-card>

          
        </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import axios from 'axios'
import * as THREE from 'three'
import Clouds from 'vanta/src/vanta.clouds'
import mock from '@/mock/index.js'
export default {
  data () {
    return {
      smdl: true,
      loginForm: {
        username: "200001",
        password: "123456",
      },
      xxx:123,
      yyy:456,
      zzz:{},

      permissions: this.$store.getters.info.role,
      options: {
        role: this.$store.getters.info.role,
        permissions: this.$store.getters.info.permissions
      }
    }
  },
  mounted() {
    this.vantaEffect = Clouds({
      el: this.$refs.vantaRef,
      THREE: THREE
    })
  },
  beforeDestroy() {
    if (this.vantaEffect) {
      this.vantaEffect.destroy()
    }
  },
  methods: {
    loginxx(){
      // let params = new FormData();
      // params.append('mobile','18289454846');
      // params.append('ver_code','123456');
     
            axios({
                  url:'http://localhost:8080/login',
                  method: 'post',
                  data: {
                        id:this.loginForm.username
                        },

                  }).then(response => {
                    console.log("哈哈哈，成功了",response.data.data)
                    console.log("两者是：",this.loginForm.password,response.data.data)
                    
                    if(this.loginForm.password==response.data.data.password)
                    {
                      if(response.data.data.role=="01" || this.loginForm.username=="200001")
                      {
                        this.options = {
                        role: "superAdmin",
                        permissions: "超级管理员"
                        }
                      }
                      else
                      {
                        this.options = {
                          role: "ordinary",
                          permissions: "普通用户"
                        }
                      }

                      this.$store.dispatch("setRole", this.options)

                      this.$store.dispatch("setToken", this.loginForm.username).then(() => {
                      this.$router.push({path: "/"})
                      console.log("密码对了")

                      localStorage.setItem("user", this.loginForm.username);
                      })
                    }
            },
            error => {
              console.log('请求失败了',error.message)
            }
          )
        },

    // submitForm () {
    //   let that = this
    //   if (this.loginForm.username === "" || this.loginForm.password === "") {
    //     this.$message({
    //       showClose: true,
    //       message: "账号或密码不能为空",
    //       type: "error"
    //     })
    //     return false
    //   } else {
    //     // 真实请求参考
    //     // this.$request.fetchLogin({
    //     //   username: that.loginForm.username,
    //     //   password: that.loginForm.password
    //     // }).then(res => {
    //     //   that.$restBack(res.data, () => {
    //     //     that.$store.dispatch("setToken", res.data.data.access_token).then(res => {
    //     //       that.$router.push({path: "/"})
    //     //     })
    //     //   }, "登录成功")
    //     // }).catch((err) => {
    //     //   console.log(err)
    //     // })

    //     // 将 username 设置为 token 存储在 store，仅为测试效果，实际存储 token 以后台返回为准
    //     that.$store.dispatch("setToken", that.loginForm.username).then(() => {
    //       that.$router.push({path: "/"})
    //     }).catch(res => {
    //       that.$message({
    //         showClose: true,
    //         message: res,
    //         type: "error"
    //       })
    //     })
    //   }
    //},
  },
  // mounted () {
  //   this.message()
  // }
}
</script>
<style lang="scss">
  #login {
    width: 100%;
    height: 100%;
    background-color: #2d3a4b00;
    .loginConbox{
      background: #2d3a4b00;
    }
    .header {
      height: 0px;
      position: relative;
      background: #2d3a4b00;
    }

    .loginBox {
      .iconcolor {
        color: #409EFF;
      }

      padding: 74px 0 118px;

      .loginCon {
        width: 990px;
        margin: auto;
        position: relative;
        height: 388px;

        .el-card__header {
          border-bottom: 0px;
        }
        .title{
          font-size: 16px;
          font-weight: 600;
          color: #409eff;
          width: 500px;
          float: left;
          margin-top: 0px;
          &:first-child{
            font-size: 34px;
            margin-top: 50px;
            margin-bottom: 30px;
          }
        }
        .login-module {
          width: 380px;
          height: 325px;
          margin-top: 60px;
          border: none;
          position: absolute;
          right: 0;

          .formTitlt {
            font-size: 18px;
            font-weight: 400;

            .titIconbox {
              float: right;

              .pointer {
                cursor: pointer;
              }
            }
          }

          .smalltxt {
            text-align: right;

            .a {
              text-decoration: none;
              color: #999999;
              font-size: 12px;
              margin-left: 8px;
            }
          }
        }

        .el-form-item__content {
          margin-left: 0px !important;

          .subBtn {
            width: 100%;
          }
        }
      }

      .el-input__inner, .el-button, .el-card, .el-message {
        border-radius: 0px !important;
      }

      .el-form-item__content .ico {
        position: absolute;
        top: 0px;
        left: 0px;
        z-index: 999;
        width: 40px;
        height: 39px;
        text-align: center;
        border-right: 1px solid #ccc;
      }

      .ewmbox {
        width: 100%;
        height: 240px;
        margin-top: -25px;

        .ewm {
          width: 140px;
          height: 140px;
          margin: 20px auto;

          p {
            font-size: 12px;
            padding-left: 40px;
            margin: 0;
          }
        }

        .ewmicon {
          width: 140px;
          margin: 20px auto 0;

          .iconfont {
            float: left;
          }

          p {
            font-size: 12px;
            padding-left: 40px;
            margin: 0;
          }
        }
      }
    }
  }
</style>
