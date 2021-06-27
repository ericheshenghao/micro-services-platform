package cn.central.oauth.entity;

import cn.central.common.model.SuperEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.bytebuddy.implementation.bind.annotation.Super;
import org.bouncycastle.math.raw.Mod;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 客户端信息.
 * 这里实现了 ClientDetails 接口
 * 个人建议不应该在实体类里面写任何逻辑代码
 * 而为了避免实体类耦合严重不应该去实现这个接口的
 * 但是这里为了演示和 {@link  } 不同的方式，所以就选择实现这个接口了
 * 另一种方式是写一个方法将它转化为默认实现 {@link BaseClientDetails} 比较好一点并且简单很多
 */
@Data
@TableName("sys_client_details")
@ApiModel(description = "客户端信息")
@EqualsAndHashCode()
public class SysClientDetails extends Model<SysClientDetails> {

    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "编号")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String id;

    /**
     * client id
     */
    private String clientId;

    /**
     * client 密钥
     */
    @JsonIgnore
    private String clientSecret;

    /**
     * 资源服务器名称
     */
    private String resourceIds;

    /**
     * 授权域
     */
    private String scopes;

    /**
     * 授权类型
     */
    private String grantTypes;

    /**
     * 重定向地址，授权码时必填
     */
    private String redirectUrl;

    /**
     * 授权信息
     */
    private String authorizations;

    /**
     * 授权令牌有效时间
     */
    private Integer accessTokenValiditySeconds;

    /**
     * 刷新令牌有效时间
     */
    private Integer refreshTokenValiditySeconds;

    /**
     * 自动授权请求域
     */
    private String autoApproveScopes;


}
