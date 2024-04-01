<template>
  <div>
    <h2>菜谱信息     </h2>
        <!--<p>对表格进行筛选，可快速查找到自己想看的数据。</p> -->
        <hr>
        <br><br>

    <template>

      <el-table
        :data="tableData5"
        style="width: 100%"
        >
        <el-table-column
          prop="src"
          label="编号"
          width="100">
        </el-table-column>

        <el-table-column
          prop="img"
          label="图片"
          height='40px'
          width="100">
          <template slot-scope="scope">
               <img :src="scope.row.img" min-width="70" height="70"/>
          </template>
        </el-table-column>
        <el-table-column
          prop="name"
          label="菜品名称"
          width="110">
        </el-table-column>
        <el-table-column
          prop="message"
          label="综合评分"
          width="80">
        </el-table-column>
        <el-table-column
          prop="methodMessage"
          label="步骤"
          width="400"
          height='1'
          show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          prop="foods"
          label="食材"
          width="110"
          show-overflow-tooltip>
        </el-table-column>
        
       

        
        
        <el-table-column label="操作" width="100">
          
          <template slot-scope="scope">
            <el-button
              type="danger"
              size="mini"
              v-role-btn="['superAdmin','admin']"
              @click="deletexx(scope.$index, scope.row)">删除</el-button>
          
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
  data () {
    return {
      xxindex:-1,
      tableData5: [{}],

      method:[{}],
      url:""

    }
  },
 
  created (){

    var Date1 = setTimeout(()=> {
      axios.get('http://8.130.113.124:8180/web/searchAll').then(
            response => {
              console.log('请求成功了',response.data.data[0].method,)

              this.tableData5=response.data.data

            },
            error => {
              console.log('请求失败了',error.message)
            }
          )
    
                    },1)  // 3秒后刷新
      

    
  },
  methods: {
     

    deletexx(index,row){
      this.xxindex=index
      console.log("天天天",this.tableData5[this.xxindex].src)

      this.url='http://8.130.113.124:8180/web/delete?src='+this.tableData5[this.xxindex].src

      axios({
            url:this.url,
            method: 'post',
            data: {
              params:{
                src:this.tableData5[this.xxindex].src,
              }
              
              }
      })
      this.tableData5.splice(index,1)
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
    
     
      
    
    closeCallback () {
      this.$emit("successCallback")
    },
  }
}
</script>

<style scoped>

</style>

