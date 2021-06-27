package cn.siques.job.core.biz.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author : heshenghao
 * @date : 15:35 2021/5/16
 */
@Data
public class RegistryParam implements Serializable {
    private static final long serialVersionUID = 42L;

    private String registerGroup;
    private String registryKey;
    private String registryValue;

    public RegistryParam() {
    }

    public RegistryParam(String registGroup, String registryKey, String registryValue) {
        this.registerGroup = registGroup;
        this.registryKey = registryKey;
        this.registryValue = registryValue;
    }

}