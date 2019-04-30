package com.leaderment.sales.mapper.jpa;


import com.leaderment.sales.pojo.ProductSupplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductSupplierMapper extends JpaRepository<ProductSupplier, Integer> {

    ProductSupplier findByProductSupplierIdAndStatus(Integer ProductSupplierId, int status);


}
