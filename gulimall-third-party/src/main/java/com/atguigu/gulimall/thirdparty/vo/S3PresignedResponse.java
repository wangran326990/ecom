package com.atguigu.gulimall.thirdparty.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class S3PresignedResponse {
    @JsonProperty("endpoint_url")
    private String endPointUrl;
    @JsonProperty("params")
    private PresignedProperties presignedProperties;
}
