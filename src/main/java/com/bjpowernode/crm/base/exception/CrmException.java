package com.bjpowernode.crm.base.exception;

public class CrmException extends RuntimeException {

    private CrmExceptionEnum crmExceptionEnum;

    public CrmException(CrmExceptionEnum crmExceptionEnum) {
//        调用父类的构造方法将枚举
        super(crmExceptionEnum.getMessage());

        this.crmExceptionEnum = crmExceptionEnum;
    }
}
