package com.mynt.parcel.costapi.controller;

import com.mynt.parcel.costapi.common.ResultModel;
import com.mynt.parcel.costapi.entity.*;
import com.mynt.parcel.costapi.service.ICalculateService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@RestController
public class CostApiController {

    @Resource
    private ICalculateService calculateService;

    /**
     * calculate cost and return result
     *
     * @param weight
     * @param height
     * @param length
     * @param width
     * @param voucherCode
     * @return cost result and parcel
     *         if the return code equal -1 then reject the delivery request
     *         if the return code equal 0 then then return the correct cost
     *
     */
    @RequestMapping(value = "/getCostApi", method = RequestMethod.GET)
    @ResponseBody
    public ResultModel getCostApi(@RequestParam(required = true) double weight,
                                  @RequestParam(required = true) double height,
                                  @RequestParam(required = true) double length,
                                  @RequestParam(required = true) double width,
                                  @RequestParam(required = false) String voucherCode


    ) {
        Parcel parcel = new Parcel();
        parcel.setWeight(weight);
        parcel.setHeight(height);
        parcel.setLength(length);
        parcel.setWidth(width);

        return calculateService.getCost(voucherCode, parcel);
    }

}
