package com.mynt.parcel.costapi;

import com.mynt.parcel.costapi.common.ResultModel;
import com.mynt.parcel.costapi.entity.Voucher;
import com.mynt.parcel.costapi.service.IVoucherService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@SpringBootTest
class CostApiApplicationTests {

    @Resource
    private IVoucherService voucherService;
    @Test
    void contextLoads() {
    }

}
