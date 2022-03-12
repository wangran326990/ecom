package com.atguigu.gulimall.thirdparty.vo;

import lombok.Data;

@Data
public class Config {
    private String s3Key;
    private String bucket;
    private String contentType;
    private String s3Secret;
    public static final String EXPIRATION_TIME = "EXPIRATION_TIME";
    public static final String AWS_S3_BUCKET = "AWS_S3_BUCKET";
    public static final String AWS_S3_KEY = "AWS_S3_KEY";
    public static final String CONTENT_LENGTH_MAX = "CONTENT_LENGTH_MAX";
    public static final String X_AWS_CREDENTIAL = "X_AWS_CREDENTIAL";
    public static final String AWS_DATE = "AWS_DATE";

}
