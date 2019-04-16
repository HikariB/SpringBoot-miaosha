package com.miaoshaproject.service.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class ItemModel {
    private Integer id;

    @NotBlank(message = "商品名称不能为空")
    private String title;

    @NotNull(message = "商品价格不能为空")
    @Min(value = 0,message = "商品价格必须大于0")
    private BigDecimal price;
    //商品库存

    @NotNull(message = "库存不能不填")
    private Integer stock;

    @NotBlank(message = "请填写商品详情")
    private String description;

    private Integer sales;
    //商品图片描述url
    @NotBlank(message = "图片信息不能为空")
    private String imgUrl;


    //使用聚合模型 嵌套类
    //promoteModel 不为空，表示存在还未结束的秒杀活动信息
    private PromoteModel promoteModel;

    public PromoteModel getPromoteModel() {
        return promoteModel;
    }

    public void setPromoteModel(PromoteModel promoteModel) {
        this.promoteModel = promoteModel;
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
