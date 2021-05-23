package cn.central.log.monitor;

import lombok.extern.slf4j.Slf4j;

/**
 * 日志埋点工具类
 *
 * @author he
 */
@Slf4j
public class PointUtil {
    private static final String MSG_PATTERN = "{}|{}|{}";

    private PointUtil() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 格式为：{时间}|{来源}|{对象id}|{类型}|{对象属性(以&分割)}
     * 例子1：2016-07-27 23:37:23|business-center|1|user-login|ip=xxx.xxx.xx&userName=张三&userType=后台管理员
     * 例子2：2016-07-27 23:37:23|file-center|c0a895e114526786450161001d1ed9|file-upload|fileName=xxx&filePath=xxx
     *
     * @param id      对象id
     * @param type    类型
     * @param message 对象属性
     */
    public static void info(String id, String type, String message) {
        log.info(MSG_PATTERN, id, type, message);
    }

    /**
     * debug方式的日志会持久化到目录，注解方式日志会持久化到db或者直接打印
     * @param id
     * @param type
     * @param message
     */
    public static void debug(String id, String type, String message) {
        log.debug(MSG_PATTERN, id, type, message);
    }
}
