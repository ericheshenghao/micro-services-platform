package cn.central.common.model;

/**
 * @author Shenghao he
 */
public enum CodeEnum {
    /** http状态码成功*/
    SUCCESS(1),
    /** http状态码失败*/
    ERROR(0);

    private Integer code;
    CodeEnum(Integer code){
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
