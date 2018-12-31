package com.su.sell.service.impl;

import com.su.sell.dataObject.ProductCategory;
import com.su.sell.repository.ProductCategoryRepository;
import com.su.sell.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 类目
 * Created by 廖师兄
 * 2017-05-09 10:16
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private ProductCategoryRepository repository;

    @Override
    @Cacheable(value = "categoryAll",key = "#categoryId") //这里的categoryId就是参数的categoryId
    public ProductCategory findOne(Integer categoryId) {
        return repository.findById(categoryId).get();
    }

    @Override
    @Cacheable(cacheNames = "categoryAll" , key = "254")
    public List<ProductCategory> findAll() {
        return repository.findAll();
    }

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList) {
        return repository.findByCategoryTypeIn(categoryTypeList);
    }


    @Override
    @CacheEvict(cacheNames = "categoryAll",key = "254")
    public ProductCategory save(ProductCategory productCategory) {
        return repository.save(productCategory);
    }
}
