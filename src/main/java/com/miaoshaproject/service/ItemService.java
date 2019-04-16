package com.miaoshaproject.service;

import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.service.model.ItemModel;

import java.util.List;

public interface ItemService {

    //创建商品
    public ItemModel createItem(ItemModel itemModel) throws BusinessException;

    //商品列表浏览
    public List<ItemModel> listItem();

    //商品详情浏览
    public ItemModel getItemById(Integer id);


    //库存扣减
    boolean deductStock(Integer itemId, Integer amount);

    //商品销量增加
    void increaseSales(Integer itemId,Integer amount);


}
