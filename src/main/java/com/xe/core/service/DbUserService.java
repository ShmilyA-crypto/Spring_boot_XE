package com.xe.core.service;

import com.xe.core.entity.DbUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author DEXTER
 * @since 2021-06-30
 */
public interface DbUserService extends IService<DbUser> {

    List<DbUser> selectSqls(String sql);

}
