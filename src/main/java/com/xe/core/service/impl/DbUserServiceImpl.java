package com.xe.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xe.core.entity.DbUser;
import com.xe.core.mapper.DbUserMapper;
import com.xe.core.service.DbUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author DEXTER
 * @since 2021-06-30
 */
@Service
public class DbUserServiceImpl extends ServiceImpl<DbUserMapper, DbUser> implements DbUserService {

    @Autowired
    DbUserMapper dbUserMapper;

    @Override
    public List<DbUser> selectSqls(String sql) {
        return dbUserMapper.selectSqls(sql);
    }
}
