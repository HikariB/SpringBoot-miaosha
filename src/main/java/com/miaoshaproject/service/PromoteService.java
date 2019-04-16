package com.miaoshaproject.service;

import com.miaoshaproject.service.model.PromoteModel;

public interface PromoteService {
    //根据itemId获取正在或者即将进行的促销时间
    PromoteModel getPromoteModel(Integer itemId);
}
