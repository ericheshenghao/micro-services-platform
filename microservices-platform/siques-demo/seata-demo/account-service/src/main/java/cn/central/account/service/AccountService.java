package cn.central.account.service;

import cn.central.account.mapper.AccountMapper;
import cn.central.account.model.Account;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author : heshenghao
 * @date : 19:05 2021/5/15
 */
@Slf4j
@Service
public class AccountService {
    @Resource
    private AccountMapper accountMapper;

    public void reduce(String userId,int money){
        if ("U002".equals(userId)) {
            throw new RuntimeException("this is a mock Exception");
        }

        QueryWrapper<Account> wrapper = new QueryWrapper<>();
        wrapper.setEntity(new Account().setUserId(userId));
        Account account = accountMapper.selectOne(wrapper);
        account.setMoney(account.getMoney() - money);
        accountMapper.updateById(account);
    }


}
