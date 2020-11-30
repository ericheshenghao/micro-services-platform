package cn.central.oauth.service.impl;


import cn.central.oauth.dao.SysClientDetailsDao;
import cn.central.oauth.entity.SysClientDetails;
import cn.central.oauth.service.SysClientDetailsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientAlreadyExistsException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 客户端 相关操作.
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/1/6 下午1:37
 */
@Service
@RequiredArgsConstructor
public class SysClientDetailsServiceImpl extends ServiceImpl<SysClientDetailsDao, SysClientDetails> implements SysClientDetailsService {

    @Autowired
    private final SysClientDetailsDao SysClientDetailsDao;

    private final PasswordEncoder passwordEncoder;

    @Override
    public ClientDetails loadClientByClientId(String id) throws ClientRegistrationException {
        return SysClientDetailsDao.findFirstByClientId(id)
            .orElseThrow(() -> new ClientRegistrationException("Loading client exception."));
    }

    @Override
    public SysClientDetails findByClientId(String clientId) {
        return SysClientDetailsDao.findFirstByClientId(clientId)
            .orElseThrow(() -> new ClientRegistrationException("Loading client exception."));
    }

    @Override
    public void addClientDetails(SysClientDetails clientDetails) throws ClientAlreadyExistsException {
        clientDetails.setId(null);
        if (SysClientDetailsDao.findFirstByClientId(clientDetails.getClientId()).isPresent()) {
            throw new ClientAlreadyExistsException(String.format("Client id %s already exist.", clientDetails.getClientId()));
        }
        SysClientDetailsDao.insert(clientDetails);
    }

    @Override
    public void updateClientDetails(SysClientDetails clientDetails) throws NoSuchClientException {
        SysClientDetails exist = SysClientDetailsDao.findFirstByClientId(clientDetails.getClientId())
            .orElseThrow(() -> new NoSuchClientException("No such client!"));
        clientDetails.setClientSecret(exist.getClientSecret());
        SysClientDetailsDao.insert(clientDetails);
    }

    @Override
    public void updateClientSecret(String clientId, String clientSecret) throws NoSuchClientException {
        SysClientDetails exist = SysClientDetailsDao.findFirstByClientId(clientId)
            .orElseThrow(() -> new NoSuchClientException("No such client!"));
        exist.setClientSecret(passwordEncoder.encode(clientSecret));
        SysClientDetailsDao.insert(exist);
    }

    @Override
    public void removeClientDetails(String clientId) throws NoSuchClientException {
        SysClientDetailsDao.deleteById(clientId);
    }

    @Override
    public List<SysClientDetails> findAll() {
        return SysClientDetailsDao.findAll();
    }

}
