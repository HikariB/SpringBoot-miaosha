package com.miaoshaproject.service.impl;

import com.miaoshaproject.dao.PromoteDOMapper;
import com.miaoshaproject.dataObject.PromoteDO;
import com.miaoshaproject.service.PromoteService;
import com.miaoshaproject.service.model.PromoteModel;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PromoteServiceImpl implements PromoteService {

    @Autowired
    PromoteDOMapper promoteDOMapper;

    @Override
    public PromoteModel getPromoteModel(Integer itemId) {
        //获取对应商品的秒杀活动信息
        PromoteDO promoteDO = promoteDOMapper.selectByItemId(itemId);

        //DO -> Model
        PromoteModel promoteModel = convertModelFromPromoteDO(promoteDO);

        if (promoteModel == null)
            return null;

        //判断当前时间是否即将进行或者正在进行秒杀活动
        DateTime now = new DateTime();
        if (promoteModel.getStartDate().isAfterNow()) {
            promoteModel.setStatus(1); //还未开始
        } else if (promoteModel.getEndDate().isBeforeNow()) {
            promoteModel.setStatus(3); //Finished
        } else {
            promoteModel.setStatus(2);//On going
        }
        //
        return promoteModel;
    }

    private PromoteModel convertModelFromPromoteDO(PromoteDO promoteDO) {
        if (promoteDO == null)
            return null;
        PromoteModel promoteModel = new PromoteModel();
        BeanUtils.copyProperties(promoteDO, promoteModel);
        promoteModel.setPromotePrice(new BigDecimal(promoteDO.getPromotePrice()));
        promoteModel.setStartDate(new DateTime(promoteDO.getStartDate()));
        promoteModel.setEndDate(new DateTime(promoteDO.getEndDate()));
        return promoteModel;
    }
}
