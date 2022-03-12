<template> 
  <div>
    <el-upload
      :action="uploadPath"
      :data="dataObj"
      list-type="picture"
      :multiple="false" :show-file-list="showFileList"
      :file-list="fileList"
      :before-upload="beforeUpload"
      :on-remove="handleRemove"
      :on-success="handleUploadSuccess"
      :on-preview="handlePreview">
      <el-button size="small" type="primary">点击上传</el-button>
      <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过10MB</div>
    </el-upload>
    <el-dialog :visible.sync="dialogVisible">
      <img width="100%" :src="fileList[0].url" alt="">
    </el-dialog>
  </div>
</template>
<script>
   import {policy} from './policy'
   import { getUUID } from '@/utils'

  export default {
    name: 'singleUpload',
    props: {
      value: String
    },
    computed: {
      imageUrl() {
        return this.value;
      },
      imageName() {
        if (this.value != null && this.value !== '') {
          return this.value.substr(this.value.lastIndexOf("/") + 1);
        } else {
          return null;
        }
      },
      fileList() {
        return [{
          name: this.imageName,
          url: this.imageUrl
        }]
      },
      showFileList: {
        get: function () {
          return this.value !== null && this.value !== ''&& this.value!==undefined;
        },
        set: function (newValue) {
        }
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
        dialogVisible: false
      };
    },
    methods: {
      emitInput(val) {
        this.$emit('input', val)
      },
      handleRemove(file, fileList) {
        this.emitInput('');
      },
      handlePreview(file) {
        this.dialogVisible = true;
      },
      beforeUpload(file) {
        let _self = this;
        return new Promise((resolve, reject) => {
          policy(file).then(response => {
            console.log("响应的数据",response);
            _self.uploadPath = response.s3Params.endpoint_url;
            _self.dataObj = response.s3Params.params;
            
            console.log("响应的数据222。。。",_self.dataObj);
            
            resolve(true)
          }).catch(err => {
            reject(false)
          })
        })
      },
      handleUploadSuccess(res, file) {
        console.log("上传成功...")
        this.showFileList = true;
        this.fileList.pop();
        this.fileList.push({name: file.name, url: this.uploadPath + '/' + this.dataObj.key.replace("${filename}",file.name) });
        this.emitInput(this.fileList[0].url);
      }
    }
  }
</script>
<style>

</style>


