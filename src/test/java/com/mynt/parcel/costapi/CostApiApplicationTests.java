package com.mynt.parcel.costapi;

import com.mynt.parcel.costapi.common.ResultModel;
import com.mynt.parcel.costapi.entity.Voucher;
import com.mynt.parcel.costapi.service.IVoucherService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@SpringBootTest
class CostApiApplicationTests {

    @Resource
    private IVoucherService voucherService;
    @Test
    void contextLoads() {
    }

    @Test
    @RequestMapping(value = "/getVoucherApi", method = RequestMethod.GET)
    @ResponseBody
    public ResultModel getVoucherApi(@RequestParam(required = true) String code

    ) {
        Voucher voucher =voucherService.getVoucher(code);
        return new ResultModel(0,"good",voucher);
    }
}
