package com.su.sell.dataObject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.su.sell.enums.ProductStatusEnum;
import com.su.sell.util.EnumUtil;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品
 * Created by 廖师兄
 * 2017-05-09 11:30
 */
@Entity
@Data
@DynamicUpdate  /** 自动更新时间. */
public class ProductInfo {

    @Id  /** 主键.  */
    private String productId;

    /** 名字. */
    private String productName;

    /** 单价. */
    private BigDecimal productPrice;

    /** 库存. */
    private Integer productStock;

    /** 描述. */
    private String productDescription;

    /** 小图. */
    private String productIcon;

    /** 状态, 0正常1下架. */
    private Integer productStatus = ProductStatusEnum.UP.getCode();

    /** 类目编号. */
    private Integer categoryType;

    private Date createTime;

    private Date updateTime;
    @JsonIgnore
    public ProductStatusEnum ProductStatusEnum(){
        return EnumUtil.getEnumByCode(productStatus,ProductStatusEnum.class);
    }

}
