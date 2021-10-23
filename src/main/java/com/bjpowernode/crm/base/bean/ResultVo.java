package com.bjpowernode.crm.base.bean;

import lombok.Data;

@Data
public class ResultVo<T> {

    private boolean isOk;
    private String message;
    private T t;
    }

