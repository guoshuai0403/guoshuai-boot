package com.gs.owner.generator.bean;

import com.gs.owner.generator.bean.enums.DataBasePosition;
import com.gs.owner.generator.bean.enums.DataBaseType;

import java.io.Serializable;
import java.util.List;

/**
 * 数据库连接相关信息
 * Created by guoshuai on 2018/8/24 0024.
 */
public class ConnectBean implements Serializable {

    /**
     * 数据库类型
     */
    private DataBaseType type;

    /**
     * 数据库位置
     */
    private DataBasePosition position;

    /**
     * 数据库ip
     */
    private String ip;

    /**
     * 数据库端口号
     */
    private Integer port;

    /**
     * 数据库用户名
     */
    private String username;

    /**
     * 数据库密码
     */
    private String password;

    /** 数据库名称 */
    private String dbName;

    /** 连库字符串 */
    private String url;

    /** 关联多个表对象 */
    private List<TableBean> tableBeans;

    public DataBaseType getType() {
        return type;
    }

    public void setType(DataBaseType type) {
        this.type = type;
    }

    public DataBasePosition getPosition() {
        return position;
    }

    public void setPosition(DataBasePosition position) {
        this.position = position;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
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

    public List<TableBean> getTableBeans() {
        return tableBeans;
    }

    public void setTableBeans(List<TableBean> tableBeans) {
        this.tableBeans = tableBeans;
    }

    public String getUrl() {
        if (DataBaseType.MYSQL.equals(this.getType())) {
            return "jdbc:mysql://"+ this.getIp() +":"+ this.getPort() +"/"+ this.getDbName()
                    +"?characterEncoding=utf8" +
                    "&autoReconnect=true" +
                    "&failOverReadOnly=false" +
                    "&connectTimeOut=30000" +
                    "&socketTimeOut=5000" +
                    "&useSSL=false" +
                    "&allowMultiQueries=true";
        }
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    @Override
    public String toString() {
        return "ConnectBean{" +
                "type=" + type +
                ", position=" + position +
                ", ip='" + ip + '\'' +
                ", port=" + port +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", tableBeans=" + tableBeans +
                '}';
    }
}
