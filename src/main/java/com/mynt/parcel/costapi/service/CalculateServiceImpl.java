package com.mynt.parcel.costapi.service;

import com.mynt.parcel.costapi.common.ResultModel;
import com.mynt.parcel.costapi.entity.Parcel;
import com.mynt.parcel.costapi.entity.Voucher;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class CalculateServiceImpl implements ICalculateService {


    @Resource
    private IVoucherService voucherService;


    /**
     * calculate cost and return result
     *
     * @param parcel
     * @param weightOrVolume
     * @param rule
     * @param message
     * @param code
     * @param voucherCode
     * @return cost result and parcel
     */
    @Override
    public ResultModel calculate(
            Parcel parcel,
            double weightOrVolume,
            double rule,
            String message,
            int code,
            String voucherCode
    ) {

        ResultModel resultModel = new ResultModel();

        //calculate cost
        double cost = weightOrVolume * rule;

        //if using voucher
        parcel = getCostbyVoucher(voucherCode, cost, parcel);

        resultModel.setCode(code);
        resultModel.setMessage(message);
        resultModel.setData(parcel);
        return resultModel;
    }

    private Parcel getCostbyVoucher(String voucherCode, double cost, Parcel parcel) {
        Voucher voucher = voucherService.getVoucher(voucherCode);

        if (voucher != null) {
            Date date = new Date();

            int isExpired = voucher.getExpiry().compareTo(date);

            //if isExpired >0 then voucher not expired
            if (isExpired < 0) {
                if (voucher != null && voucher.getDiscount() > 0) {

                    cost = cost - voucher.getDiscount();
                    if (cost < 0) {
                        cost = 0;

                    }
                    parcel.setVoucherDiscount(voucher.getDiscount());
                    parcel.setCost(cost);

                }

            } else {
                parcel.setCost(cost);

            }
        }
        return parcel;
    }


}
