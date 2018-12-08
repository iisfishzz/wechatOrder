package com.su.sell.repository;

import com.su.sell.dataObject.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by 苏旭
 * 2017-05-09 11:39
 * 不用将此类添加@Component 标签或者交给管理
 */
public interface ProductInfoRepository extends JpaRepository<ProductInfo, String> {

    List<ProductInfo> findByProductStatus(Integer productStatus);
}
