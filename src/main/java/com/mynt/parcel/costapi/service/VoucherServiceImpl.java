package com.mynt.parcel.costapi.service;

import com.alibaba.fastjson.JSONObject;
import com.mynt.parcel.costapi.common.SystemConfig;
import com.mynt.parcel.costapi.entity.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@Service
public class VoucherServiceImpl implements IVoucherService {

    @Autowired
    private SystemConfig systemConfig;

    @Override
    public Voucher getVoucher(String code) {
        Voucher voucher = new Voucher();
        if (systemConfig.getVoucherUrl() != null) {
            RestTemplate restTemplate = new RestTemplate();

            String url = String.format(systemConfig.getVoucherUrl(),code);

            try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            System.out.println(response.getBody());
            JSONObject jsonObject = JSONObject.parseObject(response.getBody());

                if(jsonObject!=null && jsonObject.getJSONObject("error")==null) {
                   voucher= JSONObject.toJavaObject(jsonObject, Voucher.class);
                   System.out.println("voucher discount"+voucher.getDiscount());
                }
                else {
                    //invalid voucher code
                    System.out.println("no voucher found ");

                    return null;
                }
            }
            catch (Exception exception)
            {
                System.out.println("Voucher API can not find voucher ,Response cannot convert to voucher class");
                return null;

            }
            return voucher;

        } else {
            return null;
        }
    }


    /**
     * judge voucher expired or not
     *
     * @param voucherCode
     * @return voucher with expired status
     */
    public Voucher getVoucherWithExpiredStatus(String voucherCode) {
        //call voucher api
        Voucher voucher = getVoucher(voucherCode);

        if (voucher != null) {
            Date date = new Date();

            //if the voucher is expired then no need to calculate discount
            int isExpired = voucher.getExpiry().compareTo(date);

            //if isExpired >0 then voucher not expired
            if (isExpired > 0) {
                voucher.setExpired(false);
            }
            //voucher is expired, no need minus voucher discount
            else {
                voucher.setExpired(true);
            }
            return voucher;
        }
        return null;
    }
}
