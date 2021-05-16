package cn.siques.job.core.enums;

/**
 * @author : heshenghao
 * @date : 15:41 2021/5/16
 */
public class RegistryConfig {
    public static final int BEAT_TIMEOUT = 30;
    public static final int DEAD_TIMEOUT = BEAT_TIMEOUT * 3;

    public enum RegistType{ EXECUTOR, ADMIN }
}
