package com.xe.core.entity;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author DEXTER
 * @since 2021-07-01
 */
public class DbDatasource implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    private Integer dsId;

    private String username;

    private String password;

    /**
     * 数据库类型
     */
    private String dsType;

    /**
     * 地址
     */
    private String dsHost;

    /**
     * 端口
     */
    private int dsPort;

    /**
     * 数据库名称
     */
    private String dbName;


    public Integer getDsId() {
        return dsId;
    }

    public void setDsId(Integer dsId) {
        this.dsId = dsId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDsType() {
        return dsType;
    }

    public void setDsType(String dsType) {
        this.dsType = dsType;
    }

    public String getDsHost() {
        return dsHost;
    }

    public void setDsHost(String dsHost) {
        this.dsHost = dsHost;
    }

    public int getDsPort() {
        return dsPort;
    }

    public void setDsPort(int dsPort) {
        this.dsPort = dsPort;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    @Override
    public String toString() {
        return "DbDatasource{" +
        "dsId=" + dsId +
        ", username=" + username +
        ", password=" + password +
        ", dsType=" + dsType +
        ", dsHost=" + dsHost +
        ", dsPort=" + dsPort +
        ", dbName=" + dbName +
        "}";
    }
}
