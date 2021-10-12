package com.mynt.parcel.costapi.service;

import com.alibaba.fastjson.JSONObject;
import com.mynt.parcel.costapi.common.SystemConfig;
import com.mynt.parcel.costapi.entity.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
                }
                else {
                    //invalid voucher code
                    return null;
                }
            }
            catch (Exception exception)
            {
                System.out.println("Response cannot convert to voucher class");
                return null;

            }
            return voucher;

        } else {
            return null;
        }


    }

}
