package com.miaoshaproject.controller.viewobject;

import java.math.BigDecimal;

public class ItemVO {
    private Integer id;
    private String title;
    private BigDecimal price;
    //商品库存
    private Integer stock;
    private String description;
    private Integer sales;
    //商品图片描述url
    private String imgUrl;

    //0:没有秒杀活动 1:未开始 2:进行中
    private Integer promoteStatus;

    private BigDecimal promotePrice;

    //秒杀活动Id
    private Integer promoteId;

    private String promoteStatDate;

    public Integer getPromoteStatus() {
        return promoteStatus;
    }

    public void setPromoteStatus(Integer promoteStatus) {
        this.promoteStatus = promoteStatus;
    }

    public BigDecimal getPromotePrice() {
        return promotePrice;
    }

    public void setPromotePrice(BigDecimal promotePrice) {
        this.promotePrice = promotePrice;
    }

    public Integer getPromoteId() {
        return promoteId;
    }

    public void setPromoteId(Integer promoteId) {
        this.promoteId = promoteId;
    }

    public String getPromoteStatDate() {
        return promoteStatDate;
    }

    public void setPromoteStatDate(String promoteStatDate) {
        this.promoteStatDate = promoteStatDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
