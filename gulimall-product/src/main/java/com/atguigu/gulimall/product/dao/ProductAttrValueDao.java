package com.atguigu.gulimall.product.dao;

import com.atguigu.gulimall.product.entity.ProductAttrValueEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.atguigu.gulimall.product.vo.SkuItemSaleAttrVo;
import com.atguigu.gulimall.product.vo.SpuItemAttrGroupVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * spu属性值
 * 
 * @author Ethan
 * @email hongshengmo@163.com
 * @date 2020-05-27 15:38:36
 */
@Mapper
public interface ProductAttrValueDao extends BaseMapper<ProductAttrValueEntity> {

    List<SpuItemAttrGroupVo> getProductGroupAttrsBySpuId(@Param("spuId") Long spuId, @Param("catalogId") Long catalogId);
}
