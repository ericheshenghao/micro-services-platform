package cn.central.service.impl;

import cn.central.common.page.PageRequest;
import cn.central.common.page.PageResult;
import cn.central.controller.dto.DbConfig;
import cn.central.entity.gen.GenConfig;
import cn.central.service.SysCodeGenService;
import cn.central.utils.CodeGenUtil;
import cn.central.utils.DbUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;

import com.github.pagehelper.PageHelper;
import com.zaxxer.hikari.HikariDataSource;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.zip.ZipOutputStream;

/**
 * @author he
 */
@Service
public class SysCodeGenServiceImpl implements SysCodeGenService {
    private final String TABLE_SQL_TEMPLATE = "select table_name tableName, engine, table_comment tableComment, create_time createTime from information_schema.tables where table_schema = (select database()) %s order by create_time desc";

    private final String COLUMN_SQL_TEMPLATE = "select column_name columnName, data_type dataType, column_comment columnComment, column_key columnKey, extra from information_schema.columns where table_name = ? and table_schema = (select database()) order by ordinal_position";

    private final String COUNT_SQL_TEMPLATE = "select count(1) from (%s)tmp";

    private final String PAGE_SQL_TEMPLATE = " limit ?,?";

    @Autowired
    DbUtil dbUtil;

   @SneakyThrows
   @Override
   public PageResult findPage(PageRequest page){
       HikariDataSource dataSource = dbUtil.buildDb();
       PageHelper.startPage(page.getPageNum(),page.getPageSize());

       Db db = new Db(dataSource);
       String paramSql = StrUtil.EMPTY;
       String sql = String.format(TABLE_SQL_TEMPLATE,paramSql);
       String countSql = String.format(COUNT_SQL_TEMPLATE, sql);
       List<Entity> query = db.query(sql + PAGE_SQL_TEMPLATE, (page.getPageNum()-1)*page.getPageSize(), page.getPageSize());
       BigDecimal count = (BigDecimal) db.queryNumber(countSql);

       dataSource.close();
       PageResult pageResult = new PageResult();
       pageResult.setPageNum(page.getPageNum());
       pageResult.setPageSize(page.getPageSize());
       pageResult.setTotal(count.longValue());
       pageResult.setRecords(query);

       return  pageResult;
   }

    @Override
    public List<DbConfig> queryDbList() {
        return   dbUtil.getDbList();
    }


    /**
     * 生成代码
     *
     * @param genConfig 生成配置
     * @return 代码压缩文件
     */
    @Override
    public byte[] generatorCode(GenConfig genConfig) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);

        //查询表信息
        Entity table = queryTable(genConfig.getTableName());
        //查询列信息
        List<Entity> columns = queryColumns(genConfig.getTableName());
        //生成代码
        CodeGenUtil.generatorCode(genConfig, table, columns, zip);
        IoUtil.close(zip);
        return outputStream.toByteArray();
    }

    @SneakyThrows
    private Entity queryTable(String  tableName) {
        HikariDataSource dataSource = dbUtil.buildDb();
        Db db = new Db(dataSource);

        String paramSql = StrUtil.EMPTY;
        if (StrUtil.isNotBlank(tableName)) {
            paramSql = "and table_name = ?";
        }
        String sql = String.format(TABLE_SQL_TEMPLATE, paramSql);
        Entity entity = db.queryOne(sql, tableName);

        dataSource.close();
        return entity;
    }

    @SneakyThrows
    private List<Entity> queryColumns(String tableName) {
        HikariDataSource dataSource = dbUtil.buildDb();
        Db db = new Db(dataSource);

        List<Entity> query = db.query(COLUMN_SQL_TEMPLATE, tableName);

        dataSource.close();
        return query;
    }



}
