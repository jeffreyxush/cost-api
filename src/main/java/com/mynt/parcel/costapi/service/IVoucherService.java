package com.mynt.parcel.costapi.service;

import com.mynt.parcel.costapi.entity.Voucher;

public interface IVoucherService {
    Voucher getVoucher(String code);
}

