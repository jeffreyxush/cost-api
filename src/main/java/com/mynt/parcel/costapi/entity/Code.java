package com.mynt.parcel.costapi.entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("Code")
public class Code {

    @Value("${code.success}")
    private int success;

    @Value("${code.fail}")
    private int fail;

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public int getFail() {
        return fail;
    }

    public void setFail(int fail) {
        this.fail = fail;
    }




}
