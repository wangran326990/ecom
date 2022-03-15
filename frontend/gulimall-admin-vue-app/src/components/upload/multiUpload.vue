<template>
  <div>
    <el-upload
      :action="uploadPath"
      :data="dataObj"
      :list-type="listType"
      :file-list="fileList"
      :before-upload="beforeUpload"
      :on-remove="handleRemove"
      :on-success="handleUploadSuccess"
      :on-preview="handlePreview"
      :limit="maxCount"
      :on-exceed="handleExceed"
      :show-file-list="showFile"
    >
      <i class="el-icon-plus"></i>
    </el-upload>
    <el-dialog :visible.sync="dialogVisible">
      <img width="100%" :src="dialogImageUrl" alt />
    </el-dialog>
  </div>
</template>
<script>
import { policy } from "./policy";
import { getUUID } from '@/utils'
export default {
  name: "multiUpload",
  props: {
    //图片属性数组
    value: Array,
    //最大上传图片数量
    maxCount: {
      type: Number,
      default: 30
    },
    listType:{
      type: String,
      default: "picture-card"
    },
    showFile:{
      type: Boolean,
      default: true
    }

  },
  data() {
    return {
        dataObj: {
          'acl': "public-read",
          'content-type': "image/jpeg",
          'key': "947ad09d-0d0b-459c-834f-129850844f8f_WeChat Image_20220224193319.jpg",
          'policy': "eyJleHBpcmF0aW9uIjoiMjAyMi0wMy0xMlQxNTo0MDoyMi41ODcyNTUxMDBaIiwiY29uZGl0aW9ucyI6W3siYnVja2V0IjoiYXdzLXByb2R1Y3QifSx7ImtleSI6Ijk0N2FkMDlkLTBkMGItNDU5Yy04MzRmLTEyOTg1MDg0NGY4Zl9XZUNoYXQgSW1hZ2VfMjAyMjAyMjQxOTMzMTkuanBnIn0seyJhY2wiOiJwdWJsaWMtcmVhZCJ9LHsic3VjY2Vzc19hY3Rpb25fc3RhdHVzIjoiMjAxIn0sWyJzdGFydHMtd2l0aCIsIiRDb250ZW50LVR5cGUiLCIiXSxbImNvbnRlbnQtbGVuZ3RoLXJhbmdlIiwwLDEzMTA3MjAwMF0seyJ4LWFtei1hbGdvcml0aG0iOiJBV1M0LUhNQUMtU0hBMjU2In0seyJ4LWFtei1jcmVkZW50aWFsIjoiQUtJQVlCM1JLN1BQSUpTNTNHTUQvMjAyMjAzMTIvY2EtY2VudHJhbC0xL3MzL2F3czRfcmVxdWVzdCJ9LHsieC1hbXotZGF0ZSI6IjIwMjIwMzEyVDAwMDAwMFoifV19",
          'success_action_status': "201",
          'x-amz-algorithm': "AWS4-HMAC-SHA256",
          'x-amz-credential': "AKIAYB3RK7PPIJS53GMD/20220312/ca-central-1/s3/aws4_request",
          'x-amz-date': "20220312T000000Z",
          'x-amz-signature': "8b05efb6cd4f0fd6a30f4877f54e3698f86a444ef3b5086f32713598f4df275f"
          // callback:'',
        },
        uploadPath:"",
      dialogVisible: false,
      dialogImageUrl: null
    };
  },
  computed: {
    fileList() {
      let fileList = [];
      for (let i = 0; i < this.value.length; i++) {
        fileList.push({ url: this.value[i] });
      }

      return fileList;
    }
  },
  mounted() {},
  methods: {
    emitInput(fileList) {
      let value = [];
      for (let i = 0; i < fileList.length; i++) {
        value.push(fileList[i].url);
      }
      this.$emit("input", value);
    },
    handleRemove(file, fileList) {
      this.emitInput(fileList);
    },
    handlePreview(file) {
      this.dialogVisible = true;
      this.dialogImageUrl = file.url;
    },
    beforeUpload(file) {
      let _self = this;
      return new Promise((resolve, reject) => {
        policy(file)
          .then(response => {
            console.log("这是什么${filename}");
            _self.uploadPath = response.s3Params.endpoint_url;
            _self.dataObj = response.s3Params.params;
            resolve(true);
          })
          .catch(err => {
            console.log("出错了...",err)
            reject(false);
          });
      });
    },
    handleUploadSuccess(res, file) {
      this.fileList.push({
        name: file.name,
        // url: this.dataObj.host + "/" + this.dataObj.dir + "/" + file.name； 替换${filename}为真正的文件名
        url: this.uploadPath + "/" + this.dataObj.key.replace("${filename}",file.name)
      });
      this.emitInput(this.fileList);
    },
    handleExceed(files, fileList) {
      this.$message({
        message: "最多只能上传" + this.maxCount + "张图片",
        type: "warning",
        duration: 1000
      });
    }
  }
};
</script>
<style>
</style>


