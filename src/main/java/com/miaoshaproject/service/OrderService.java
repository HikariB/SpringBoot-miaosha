package com.miaoshaproject.service;

import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.service.model.OrderModel;

public interface OrderService {
    //有两种处理促销下单的方法
    //1.通过前端url上传秒杀活动id，下单接口内校验对应id是否始于对应商品且活动已开始
    //2.直接在下单接口内判断对应商品是否存在秒杀活动，若存在且进行中则以秒杀价格下单
    //推荐第一种


    OrderModel createOrder(Integer userId, Integer itemId, Integer promoteId, Integer amount) throws BusinessException;


}
