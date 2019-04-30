package com.leaderment.sales.pojo;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @ClassName Product
 * @Description TODO
 * @Author peter
 * @Date 19-4-9 下午4:22
 * @Version 1.0
 **/
@Entity
public class Product {
    private int productId;
    private String productModelNumber;
    private int businessUnitId;
    private int brandId;
    private int productCategoryId;
    private BigDecimal productLong;
    private BigDecimal productWidth;
    private BigDecimal productHeight;
    private BigDecimal productVolume;
    private BigDecimal productNetweight;
    private BigDecimal productGrossweight;
    private String productMaterial;
    private String productPackageContains;
    private BigDecimal productOrderDefect;
    private String productU8Code;
    private int status;
    private String productCertification;
    private String productDescriptionChs;
    private String productDescriptionEn;
    private int userId;
    private Timestamp cTime;
    private String productImgUrl;

    @Id
    @Column(name = "product_id", nullable = false)
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    @Basic
    @Column(name = "product_model_number", nullable = false, length = 45)
    public String getProductModelNumber() {
        return productModelNumber;
    }

    public void setProductModelNumber(String productModelNumber) {
        this.productModelNumber = productModelNumber;
    }

    @Basic
    @Column(name = "business_unit_id", nullable = false)
    public int getBusinessUnitId() {
        return businessUnitId;
    }

    public void setBusinessUnitId(int businessUnitId) {
        this.businessUnitId = businessUnitId;
    }

    @Basic
    @Column(name = "brand_id", nullable = false)
    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    @Basic
    @Column(name = "product_category_id", nullable = false)
    public int getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(int productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    @Basic
    @Column(name = "product_long", nullable = false, precision = 2)
    public BigDecimal getProductLong() {
        return productLong;
    }

    public void setProductLong(BigDecimal productLong) {
        this.productLong = productLong;
    }

    @Basic
    @Column(name = "product_width", nullable = false, precision = 2)
    public BigDecimal getProductWidth() {
        return productWidth;
    }

    public void setProductWidth(BigDecimal productWidth) {
        this.productWidth = productWidth;
    }

    @Basic
    @Column(name = "product_height", nullable = false, precision = 2)
    public BigDecimal getProductHeight() {
        return productHeight;
    }

    public void setProductHeight(BigDecimal productHeight) {
        this.productHeight = productHeight;
    }

    @Basic
    @Column(name = "product_volume", nullable = false, precision = 2)
    public BigDecimal getProductVolume() {
        return productVolume;
    }

    public void setProductVolume(BigDecimal productVolume) {
        this.productVolume = productVolume;
    }

    @Basic
    @Column(name = "product_netweight", nullable = false, precision = 2)
    public BigDecimal getProductNetweight() {
        return productNetweight;
    }

    public void setProductNetweight(BigDecimal productNetweight) {
        this.productNetweight = productNetweight;
    }

    @Basic
    @Column(name = "product_grossweight", nullable = false, precision = 2)
    public BigDecimal getProductGrossweight() {
        return productGrossweight;
    }

    public void setProductGrossweight(BigDecimal productGrossweight) {
        this.productGrossweight = productGrossweight;
    }

    @Basic
    @Column(name = "product_material", nullable = true, length = 255)
    public String getProductMaterial() {
        return productMaterial;
    }

    public void setProductMaterial(String productMaterial) {
        this.productMaterial = productMaterial;
    }

    @Basic
    @Column(name = "product_package_contains", nullable = true, length = 255)
    public String getProductPackageContains() {
        return productPackageContains;
    }

    public void setProductPackageContains(String productPackageContains) {
        this.productPackageContains = productPackageContains;
    }

    @Basic
    @Column(name = "product_order_defect", nullable = false, precision = 2)
    public BigDecimal getProductOrderDefect() {
        return productOrderDefect;
    }

    public void setProductOrderDefect(BigDecimal productOrderDefect) {
        this.productOrderDefect = productOrderDefect;
    }

    @Basic
    @Column(name = "product_u8_code", nullable = false, length = 45)
    public String getProductU8Code() {
        return productU8Code;
    }

    public void setProductU8Code(String productU8Code) {
        this.productU8Code = productU8Code;
    }

    @Basic
    @Column(name = "status", nullable = false)
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Basic
    @Column(name = "product_certification", nullable = true, length = 45)
    public String getProductCertification() {
        return productCertification;
    }

    public void setProductCertification(String productCertification) {
        this.productCertification = productCertification;
    }

    @Basic
    @Column(name = "product_description_chs", nullable = true, length = -1)
    public String getProductDescriptionChs() {
        return productDescriptionChs;
    }

    public void setProductDescriptionChs(String productDescriptionChs) {
        this.productDescriptionChs = productDescriptionChs;
    }

    @Basic
    @Column(name = "product_description_en", nullable = true, length = -1)
    public String getProductDescriptionEn() {
        return productDescriptionEn;
    }

    public void setProductDescriptionEn(String productDescriptionEn) {
        this.productDescriptionEn = productDescriptionEn;
    }

    @Basic
    @Column(name = "user_id", nullable = false)
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "c_time", nullable = false)
    public Timestamp getcTime() {
        return cTime;
    }

    public void setcTime(Timestamp cTime) {
        this.cTime = cTime;
    }

    @Basic
    @Column(name = "product_img_url", nullable = true, length = 255)
    public String getProductImgUrl() {
        return productImgUrl;
    }

    public void setProductImgUrl(String productImgUrl) {
        this.productImgUrl = productImgUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return productId == product.productId &&
                businessUnitId == product.businessUnitId &&
                brandId == product.brandId &&
                productCategoryId == product.productCategoryId &&
                status == product.status &&
                userId == product.userId &&
                Objects.equals(productModelNumber, product.productModelNumber) &&
                Objects.equals(productLong, product.productLong) &&
                Objects.equals(productWidth, product.productWidth) &&
                Objects.equals(productHeight, product.productHeight) &&
                Objects.equals(productVolume, product.productVolume) &&
                Objects.equals(productNetweight, product.productNetweight) &&
                Objects.equals(productGrossweight, product.productGrossweight) &&
                Objects.equals(productMaterial, product.productMaterial) &&
                Objects.equals(productPackageContains, product.productPackageContains) &&
                Objects.equals(productOrderDefect, product.productOrderDefect) &&
                Objects.equals(productU8Code, product.productU8Code) &&
                Objects.equals(productCertification, product.productCertification) &&
                Objects.equals(productDescriptionChs, product.productDescriptionChs) &&
                Objects.equals(productDescriptionEn, product.productDescriptionEn) &&
                Objects.equals(cTime, product.cTime) &&
                Objects.equals(productImgUrl, product.productImgUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, productModelNumber, businessUnitId, brandId, productCategoryId, productLong, productWidth, productHeight, productVolume, productNetweight, productGrossweight, productMaterial, productPackageContains, productOrderDefect, productU8Code, status, productCertification, productDescriptionChs, productDescriptionEn, userId, cTime, productImgUrl);
    }
}
