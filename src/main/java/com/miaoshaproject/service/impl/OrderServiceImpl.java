package com.miaoshaproject.service.impl;

import com.miaoshaproject.dao.OrderDOMapper;
import com.miaoshaproject.dao.SequenceDOMapper;
import com.miaoshaproject.dataObject.OrderDO;
import com.miaoshaproject.dataObject.SequenceDO;
import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.error.EmBusinessError;
import com.miaoshaproject.service.ItemService;
import com.miaoshaproject.service.OrderService;
import com.miaoshaproject.service.UserService;
import com.miaoshaproject.service.model.ItemModel;
import com.miaoshaproject.service.model.OrderModel;
import com.miaoshaproject.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderDOMapper orderDOMapper;

    @Autowired
    private SequenceDOMapper sequenceDOMapper;


    @Override
    @Transactional
    public OrderModel createOrder(Integer userId, Integer itemId, Integer promoteId, Integer amount) throws BusinessException {
        //1.校验下单状态：下单的商品是否存在，用户是否合法，购买数量是否正确
        ItemModel itemModel = itemService.getItemById(itemId);
        if (itemModel == null)
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "item NOT existed");

        UserModel userModel = userService.getUserById(userId);
        if (userModel == null)
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "User NOT existed");

        if (amount <= 0 || amount > 99)
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "Purchase amount illegal");

        //校验活动信息

        if (promoteId != null) {
            //(1)校验对应活动是否存在这个使用商品
            if (promoteId.intValue() != itemModel.getPromoteModel().getId()) {
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "Promotion info Error");
                //(2)校验活动是否进行中
            } else if (itemModel.getPromoteModel().getStatus().intValue() != 2) {
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "Promotion Not Begin!");
            }
        }


        //2.落单减库存，支付减库存
        boolean result = itemService.deductStock(itemId, amount);
        if (!result)
            throw new BusinessException(EmBusinessError.STOCK_NOT_ENOUGH);

        //3.订单入库
        OrderModel orderModel = new OrderModel();
        orderModel.setUserId(userId);
        orderModel.setItemId(itemId);
        orderModel.setAmount(amount);
        orderModel.setPromoteId(promoteId);
        if (promoteId != null) {
            orderModel.setItemPrice(itemModel.getPromoteModel().getPromotePrice());
        } else {
            orderModel.setItemPrice(itemModel.getPrice());
        }
        orderModel.setOrderPrice(orderModel.getItemPrice().multiply(new BigDecimal(amount)));

        //生成交易流水号，订单号
        orderModel.setId(generateOrderNo());

        OrderDO orderDO = convertFromOrderModel(orderModel);
        orderDOMapper.insertSelective(orderDO);

        //增加商品的销量
        itemService.increaseSales(itemId, amount);

        //4.返回前端

        return orderModel;
    }


    //不管整个操作事务是否成功，该方法都将执行，不被回滚，保持唯一性
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private String generateOrderNo() {
        //订单号16位信息
        //前8位为时间信息，年月日
        StringBuilder stringBuilder = new StringBuilder();
        LocalDateTime now = LocalDateTime.now();
        String nowDate = now.format(DateTimeFormatter.ISO_DATE).replace("-", "");
        stringBuilder.append(nowDate);
        //中间6位自增序列
        //获取当前sequence
        int sequence = 0;
        SequenceDO sequenceDO = sequenceDOMapper.getSequenceByName("order_info");
        sequence = sequenceDO.getCurrentValue();
        sequenceDO.setCurrentValue(sequenceDO.getCurrentValue() + sequenceDO.getStep());
        sequenceDOMapper.updateByPrimaryKeySelective(sequenceDO);

        String sequenceStr = String.valueOf(sequence);
        for (int i = 0; i < 6 - sequenceStr.length(); ++i) {
            stringBuilder.append(0);
        }
        stringBuilder.append(sequenceStr);

        //最后2位分库分表位
        stringBuilder.append("00");

        return stringBuilder.toString();
    }

    private OrderDO convertFromOrderModel(OrderModel orderModel) {
        if (orderModel == null)
            return null;
        OrderDO orderDO = new OrderDO();
        BeanUtils.copyProperties(orderModel, orderDO);
        //类型不一致，需要手动转换
        orderDO.setItemPrice(orderModel.getItemPrice().doubleValue());
        orderDO.setOrderPrice(orderModel.getOrderPrice().doubleValue());
        return orderDO;
    }


//    public static void main(String[] args) {
//        LocalDateTime now = LocalDateTime.now();
//        System.out.println(now.format(DateTimeFormatter.ISO_DATE).replace("-",""));
//    }

}
