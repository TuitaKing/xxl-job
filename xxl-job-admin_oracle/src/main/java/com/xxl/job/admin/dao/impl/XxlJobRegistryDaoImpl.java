package com.xxl.job.admin.dao.impl;

import com.xxl.job.admin.core.model.XxlJobRegistry;
import com.xxl.job.admin.dao.IXxlJobRegistryDao;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by xuxueli on 16/9/30.
 */
@Repository
public class XxlJobRegistryDaoImpl implements IXxlJobRegistryDao {

    @Resource
    public SqlSessionTemplate sqlSessionTemplate;

    @Override
    public int removeDead(int timeout) {
        Timestamp time=subtractTimepoint(timeout);
        return sqlSessionTemplate.delete("XxlJobRegistryMapper.removeDead", time);
    }

    @Override
    public List<XxlJobRegistry> findAll(int timeout) {
        Timestamp time=subtractTimepoint(timeout);
        return sqlSessionTemplate.selectList("XxlJobRegistryMapper.findAll", time);
    }

    @Override
    public int registryUpdate(String registryGroup, String registryKey, String registryValue) {
     Map<String, Object> params = new HashMap<String, Object>();
        params.put("registryGroup", registryGroup);
        params.put("registryKey", registryKey);
        params.put("registryValue", registryValue);
        params.put("update_time",new Timestamp((new Date()).getTime()));
        return sqlSessionTemplate.update("XxlJobRegistryMapper.registryUpdate", params);
    }

    @Override
    public int registrySave(String registryGroup, String registryKey, String registryValue) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("registryGroup", registryGroup);
        params.put("registryKey", registryKey);
        params.put("registryValue", registryValue);
        params.put("update_time",new Timestamp((new Date()).getTime()));
        return sqlSessionTemplate.update("XxlJobRegistryMapper.registrySave", params);
    }

    private Timestamp subtractTimepoint(int timeout){
        Date now =new Date();
         Calendar cc = Calendar.getInstance();
        cc.setTime(now);
        cc.add(Calendar.SECOND, -timeout);
        Timestamp res=new Timestamp(cc.getTime().getTime());
        return res;
    }

}
