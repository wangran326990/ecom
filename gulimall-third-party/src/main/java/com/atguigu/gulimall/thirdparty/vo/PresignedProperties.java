package com.atguigu.gulimall.thirdparty.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@Data
@JsonPropertyOrder({ "key", "acl", "success-action-status", "policy", "content-type","x-amz-algorithm", "x-amz-credential","x-amz-date","x-amz-signature"})
public class PresignedProperties {
    private String key;
    private String acl;
    @JsonProperty("success_action_status")
    private String successActionStatus;
    @JsonProperty("policy")
    private String policy;
    @JsonProperty("content-type")
    private String contentType;
    @JsonProperty("x-amz-algorithm")
    private String xAMZAlgorithm;
    @JsonProperty("x-amz-credential")
    private String xAMZCredential;
    @JsonProperty("x-amz-date")
    private String xAMZDate;
    @JsonProperty("x-amz-signature")
    private String xAMZSignature;
}
