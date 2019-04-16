package com.miaoshaproject.controller;

import com.miaoshaproject.controller.viewobject.ItemVO;
import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.response.CommonReturnType;
import com.miaoshaproject.service.ItemService;
import com.miaoshaproject.service.model.ItemModel;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Controller("item")
@RequestMapping("/item")
//响应Ajax请求，解决跨域请求问题
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
//@CrossOrigin(origins = {"*"},allowCredentials = "true")
public class ItemController extends BaseController {

    @Autowired
    private ItemService itemService;


    //商品列表页面浏览
    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType listItem() {
        List<ItemModel> itemModellist = itemService.listItem();

        //使用stream api 将list内的Itemmodel -> ItemVO
        List<ItemVO> itemVOList = itemModellist.stream().map(itemModel -> {
            ItemVO itemVO = this.convertFromItemModel(itemModel);
            return itemVO;
        }).collect(Collectors.toList());

        return CommonReturnType.create(itemVOList);

    }


    //商品详情页浏览
    @RequestMapping(value = "/get", method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType getItem(
            @RequestParam(name = "id") Integer id
    ) {
        ItemModel itemModel = itemService.getItemById(id);
        ItemVO itemVO = convertFromItemModel(itemModel);
        return CommonReturnType.create(itemVO);
    }


    //创建商品的controller
    @RequestMapping(value = "/create", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType createItem(
            @RequestParam(name = "title") String title,
            @RequestParam(name = "description") String decription,
            @RequestParam(name = "price") BigDecimal price,
            @RequestParam(name = "stock") Integer stock,
            @RequestParam(name = "imgUrl") String imgUrl
    ) throws BusinessException {
        //封装service请求用来创建商品
        ItemModel itemModel = new ItemModel();
        itemModel.setTitle(title);
        itemModel.setDescription(decription);
        itemModel.setPrice(price);
        itemModel.setStock(stock);
        itemModel.setImgUrl(imgUrl);
        ItemModel itemModelFromReturn = itemService.createItem(itemModel);
        ItemVO itemVO = convertFromItemModel(itemModelFromReturn);


        return CommonReturnType.create(itemVO);

    }

    private ItemVO convertFromItemModel(ItemModel itemModel) {
        if (itemModel == null)
            return null;
        ItemVO itemVO = new ItemVO();
        BeanUtils.copyProperties(itemModel, itemVO);
        if (itemModel.getPromoteModel() != null) {
            itemVO.setPromoteStatus(itemModel.getPromoteModel().getStatus());
            itemVO.setPromoteId(itemModel.getPromoteModel().getId());
            itemVO.setPromotePrice(itemModel.getPromoteModel().getPromotePrice());
            itemVO.setPromoteStatDate(itemModel.getPromoteModel().getStartDate().toString(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")));
        } else {
            itemVO.setPromoteStatus(0);
        }

        return itemVO;
    }

}
