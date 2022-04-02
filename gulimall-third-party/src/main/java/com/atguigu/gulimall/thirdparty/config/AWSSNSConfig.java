package com.atguigu.gulimall.thirdparty.config;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AWSSNSConfig {

    @Value("${cloud.aws.region.static}")
    private String region;



    @Bean
    public AmazonSNS amazonSnsClient(AWSCredentialsProvider awsCredentialsProvider) {
        AmazonSNSClientBuilder builder = AmazonSNSClient.builder();
        builder.withCredentials(awsCredentialsProvider);
        builder.withRegion(region);
        AmazonSNS client = builder.build();
        return client;
    }

}
