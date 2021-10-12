package com.mynt.parcel.costapi.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by luliang
 */
@Component("systemConfig")
public class SystemConfig {

    public String getVoucherUrl() {
        return voucherUrl;
    }

    public void setVoucherUrl(String voucherUrl) {
        this.voucherUrl = voucherUrl;
    }

    @Value("${voucher.url}")
    private String voucherUrl;


}
