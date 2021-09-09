package com.xe.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xe.core.entity.DbUser;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author DEXTER
 * @since 2021-06-30
 */
public interface DbUserMapper extends BaseMapper<DbUser> {

    List<DbUser> selectSqls(String sql);
}
