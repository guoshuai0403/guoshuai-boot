package com.gs.owner.generator.common.converter;

import com.gs.common.util.enums.EnumUtil;
import com.gs.common.util.enums.IBaseEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

/**
 * springMVC枚举注入转换器
 * Created by guoshuai on 2018/5/31 0031.
 */
public final class MyIBaseEnumConverter implements ConverterFactory<String, IBaseEnum> {

    @Override
    public <T extends IBaseEnum> Converter<String, T> getConverter(
            Class<T> targetType) {
        return new StringToEnum(targetType);
    }

    private class StringToEnum<T extends Enum<?> & IBaseEnum> implements Converter<String, T> {

        private final Class<T> enumType;

        public StringToEnum(Class<T> enumType) {
            this.enumType = enumType;
        }

        public T convert(String source) {

            if (source.length() == 0) {
                return null;
            }
            return EnumUtil.getEnumByCode(this.enumType, Integer.parseInt(source));
        }
    }
}
