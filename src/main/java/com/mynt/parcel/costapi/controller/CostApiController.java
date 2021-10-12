package com.mynt.parcel.costapi.controller;

import com.mynt.parcel.costapi.common.ResultModel;
import com.mynt.parcel.costapi.entity.*;
import com.mynt.parcel.costapi.service.ICalculateService;
import com.mynt.parcel.costapi.service.IVoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@RestController
public class CostApiController {

    @Autowired
    private Message message;

    @Autowired
    private Rule rule;

    @Autowired
    private Code code;

    @Resource
    private ICalculateService calculateService;



    /**
     * calculate cost and return result
     *
     * @param weight
     * @param height
     * @param length
     * @param width
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

        //parcel's weight exceeds 50kg , then reject
        if (parcel.getWeight() > 50) {

            return calculateService.calculate( parcel,
                    0, 0,
                    message.getReject(),code.getFail(),
                    voucherCode);
        }

        //Weight exceeds 10kg then return Heavy Parcel, PHP 20 * Weight (kg)
        if (parcel.getWeight() > 10) {
            return calculateService.calculate(parcel,
                    parcel.getWeight(), rule.getHeavy(),
                    message.getHeavy(),code.getSuccess(),
                    voucherCode);
        }

        //Volume is less than 1500 cm3,PHP 0.03 * Volume
        parcel.setVolume(parcel.getLength() * parcel.getWidth());

        if (parcel.getVolume() < 1500) {
            return calculateService.calculate(parcel,
                    parcel.getVolume(), rule.getSmall(),
                    message.getSmall(),code.getSuccess(),
                    voucherCode);
        }

        //Volume is less than 2500 cm3 ,PHP 0.04 * Volume
        if (parcel.getVolume() < 2500) {
            return calculateService.calculate(parcel,
                    parcel.getVolume(), rule.getMedium(),
                    message.getMedium(),code.getSuccess(),
                    voucherCode);

        }

        //else then large parcel PHP 0.05 * Volume
        return calculateService.calculate(
                parcel, parcel.getVolume(), rule.getLarge(),
                message.getLarge(),code.getSuccess(),
                voucherCode);
    }

}
