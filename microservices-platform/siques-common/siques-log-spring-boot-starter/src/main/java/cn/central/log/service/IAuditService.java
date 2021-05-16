package cn.central.log.service;


import cn.central.log.model.Audit;

/**
 * 审计日志接口
 * @author he
 * @date 2020/2/3
 */
public interface IAuditService {
    /**
     *
     * @param audit
     */
    void save(Audit audit);
}
