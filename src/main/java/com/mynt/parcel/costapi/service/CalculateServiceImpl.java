package com.mynt.parcel.costapi.service;

import com.mynt.parcel.costapi.common.ResultModel;
import com.mynt.parcel.costapi.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Date;

@Service
public class CalculateServiceImpl implements ICalculateService {


    @Resource
    private IVoucherService voucherService;

    @Autowired
    private Message message;

    @Autowired
    private Rule rule;

    @Autowired
    private Code code;

    /**
     * get cost and return result
     *
     * @param parcel
     * @param voucherCode
     * @param parcel
     * @param voucherCode
     * @return get cost through vouchercode and parcel
     */
    public ResultModel getCost(String voucherCode, Parcel parcel) {
        //parcel's weight exceeds 50kg , then reject
        ResultModel resultModel = new ResultModel();

        //set parcel volume
        parcel.setVolume(parcel.getLength() * parcel.getWidth() * parcel.getHeight());


        //Weight exceeds 50kg then return reject
        if (parcel.getWeight() > 50) {
             resultModel= calculate(parcel, parcel.getWeight(), rule.getReject(),
                                    message.getReject(),code.getFail(),
                                    voucherCode);
            return resultModel;
        }

        //Weight exceeds 10kg then return Heavy Parcel, PHP 20 * Weight (kg)
        if (parcel.getWeight() > 10) {
            resultModel = calculate(parcel, parcel.getWeight(), rule.getHeavy(),
                                    message.getHeavy(),code.getSuccess(),
                                    voucherCode);
            return  resultModel;
        }


        if (parcel.getVolume() < 1500) {
            resultModel = calculate(parcel,parcel.getVolume(), rule.getSmall(),message.getSmall(),
                                    code.getSuccess(), voucherCode);
            return resultModel;
        }

        //Volume is less than 2500 cm3 ,PHP 0.04 * Volume
        if (parcel.getVolume() < 2500) {
            resultModel = calculate(parcel,parcel.getVolume(), rule.getMedium(),message.getMedium(),
                                    code.getSuccess(), voucherCode);
            return  resultModel;
        }

        //else then large parcel PHP 0.05 * Volume
        resultModel = calculate(
                parcel, parcel.getVolume(),
                rule.getLarge(), message.getLarge(),
                code.getSuccess(), voucherCode);
        return  resultModel;
    }


    /**
     * calculate cost and return result
     *
     * @param parcel
     * @param weightOrVolume
     * @param voucherRule
     * @param voucherMessage
     * @param returnCode
     * @param voucherCode
     * @return cost result and parcel
     */
    @Override
    public ResultModel calculate(Parcel parcel, double weightOrVolume,
                                 double voucherRule, String voucherMessage,
                                 int returnCode, String voucherCode
    ) {

        ResultModel resultModel = new ResultModel();
        Voucher voucher = new Voucher();

        //set parcel default voucher expired is true
        //parcel.setVoucherExpired(true);
        //calculate cost
        double cost = weightOrVolume * voucherRule;

        //if the message is Reject then no need get voucher,the cost is zero
        //if message is not reject then calculate cost minus voucher
        if (voucherMessage!=null && voucherMessage.equals(message.getReject()))
        {
            parcel.setCost(0);
        }
        else {

            //get the discount
            voucher = voucherService.getDiscountbyVoucher(voucherCode);

            if(voucher!=null && voucher.getDiscount()>0 ) {
                if(voucher.isExpired()== false) {
                    //cost minus discount < 0, then cost =0;
                    cost = cost - voucher.getDiscount();
                    if (cost < 0) {
                        cost = 0;
                    }
                }
                parcel.setVoucher(voucher);
               /* parcel.setVoucherDiscount(voucher.getDiscount());
                parcel.setVoucherExpired(voucher.isExpired());*/
            }
            cost = (double) Math.round(cost * 100) / 100;
            parcel.setCost(cost);

        }

        resultModel.setCode(returnCode);
        resultModel.setMessage(voucherMessage);
        resultModel.setData(parcel);
        return resultModel;
    }

}
