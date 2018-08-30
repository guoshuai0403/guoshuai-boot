package com.gs.common.service.handler;

import com.gs.common.util.enums.EnumUtil;
import com.gs.common.util.enums.IBaseEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * mybatis 与java 实体类枚举类型转换
 * Created by guoshuai on 2018/8/30 0030.
 */
public class EnumTypeHandler<E extends IBaseEnum> extends BaseTypeHandler<IBaseEnum> {
    private Class<E> clazz;

    public EnumTypeHandler(Class<E> enumType) {
        if (enumType == null)
            throw new IllegalArgumentException("Type argument cannot be null");
        this.clazz = enumType;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, IBaseEnum parameter, JdbcType jdbcType) throws SQLException {
        if (parameter == null) {
            ps.setObject(i, null);
        } else {
            ps.setInt(i, parameter.getCode());
        }
    }

    @Override
    public IBaseEnum getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return EnumUtil.getEnumByCode(clazz, rs.getInt(columnName));
    }

    @Override
    public IBaseEnum getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return EnumUtil.getEnumByCode(clazz, rs.getInt(columnIndex));
    }

    @Override
    public IBaseEnum getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return EnumUtil.getEnumByCode(clazz, cs.getInt(columnIndex));
    }
}
