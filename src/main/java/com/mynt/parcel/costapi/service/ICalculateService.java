package com.mynt.parcel.costapi.service;

import com.mynt.parcel.costapi.common.ResultModel;
import com.mynt.parcel.costapi.entity.Parcel;

public interface ICalculateService {
    ResultModel calculate(Parcel parcel,
                          double weightOrVolume,
                          double rule,
                          String message,
                          int code,
                          String voucherCode
    );
}

