package cn.central.utils;



import cn.central.config.DbProperty;
import cn.central.controller.dto.DbConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Configuration
@EnableConfigurationProperties(DbProperty.class)
public class DbUtil {
    
 
   @Autowired
   private DbProperty dbProperty;

   private DbConfig dbConfig;




    public void setDbSource(DbConfig dbConfig)  {
        this.dbConfig =dbConfig;
    }


    public HikariDataSource buildDb()  {
        List<DbConfig> dbList = getDbList();
        HikariDataSource dataSource = new HikariDataSource();

        if(dbConfig!=null){
            List<DbConfig> collect = dbList.stream().filter(s -> s.getUrl().equals(dbConfig.getUrl())).collect(Collectors.toList());
            DbConfig dbConfig = collect.get(0);
             dataSource = setConfig(dataSource, dbConfig);
        }else{
            dataSource = setConfig(dataSource, dbList.get(0));
        }

        return dataSource;
    }


    public List<DbConfig> getDbList() {
        List<DbConfig> multiplesource = dbProperty.getMultiplesource();
        return  multiplesource;
    }

    public HikariDataSource setConfig(HikariDataSource dataSource,DbConfig dbConfig){
        dataSource.setJdbcUrl(dbConfig.getUrl());
        dataSource.setUsername(dbConfig.getUsername());
        dataSource.setPassword(dbConfig.getPassword());
        return dataSource;
    }
}
