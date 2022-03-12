import http from '@/utils/httpRequest.js'
export function policy(file) {
    console.log(file);
   return  new Promise((resolve,reject)=>{
        http({
            url: http.adornUrl(`/thirdparty/oss/policy`),
            method: "get",
            params: http.adornParams({fileName: file.name, contentType: file.type})
        }).then(({ data }) => {
            resolve(data);
        })
    });
}