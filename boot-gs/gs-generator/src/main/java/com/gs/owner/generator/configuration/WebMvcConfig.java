package com.gs.owner.generator.configuration;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.gs.common.util.constant.Constant;
import com.gs.owner.generator.common.converter.MyIBaseEnumConverter;
import com.gs.owner.generator.common.encryption.GsRequestBodyAdvice;
import com.gs.owner.generator.common.encryption.GsResponseBodyAdvice;
import com.gs.owner.generator.interceptor.GlobalInterceptor;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.*;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import java.util.ArrayList;
import java.util.List;

/**
 * 静态资源配置
 * Created by guoshuai on 2018/8/26 0026.
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport{

    /**
     * 视图解析器
     * @param templateResolver
     * @return
     */
    @Bean
    public ITemplateResolver initSpringResourceTemplateResolver(SpringResourceTemplateResolver templateResolver) {
        templateResolver.setPrefix("classpath:/page/");
        return templateResolver;
    }

//    /**
//     * 入参解密
//     * @return
//     */
//    @Bean
//    public GsRequestBodyAdvice initGsRequestBodyAdvice(){
//        return new GsRequestBodyAdvice();
//    }
//
//    /**
//     * 出参加密
//     * @return
//     */
//    @Bean
//    public GsResponseBodyAdvice initGsResponseBodyAdvice(){
//        return new GsResponseBodyAdvice();
//    }

    /**
     * 过滤器
     * @return
     */
    @Bean
    public FilterRegistrationBean filterRegistrationBean(){
        // 字符编码
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding(Constant.ENCODING_UTF8);
        // 请求和响应都强制utf-8编码
        characterEncodingFilter.setForceEncoding(true);

        FilterRegistrationBean registration = new FilterRegistrationBean();
        // 注入过滤器
        registration.setFilter(characterEncodingFilter);
        // 拦截规则
        registration.addUrlPatterns("/*");
        // 过滤器名称
        registration.setName("CharacterEncodingFilter");
        // 过滤器顺序
        registration.setOrder(1);
        return registration;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 定义fastjson转换器
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();

        // fastjson 配置
        FastJsonConfig fastJsonConfig = new FastJsonConfig();

        fastJsonConfig.setSerializerFeatures(
                // 避免循环引用
                SerializerFeature.DisableCircularReferenceDetect,
                // 是否输出值为null的字段
                SerializerFeature.WriteMapNullValue,
                // 字符类型字段如果为null,输出为"",而非null
                SerializerFeature.WriteNullStringAsEmpty,
                // List字段如果为null,输出为[],而非null
                SerializerFeature.WriteNullListAsEmpty,
                // Boolean字段如果为null,输出为false,而非null
                SerializerFeature.WriteNullBooleanAsFalse,
                // 用枚举toString()值输出
                SerializerFeature.WriteEnumUsingToString
                );
        fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);

        // 处理中文乱码
        List<MediaType> fastMediaTypes = new ArrayList<MediaType>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);

        fastJsonHttpMessageConverter.setSupportedMediaTypes(fastMediaTypes);

        converters.add(fastJsonHttpMessageConverter);
    }

    /**
     * 解决跨域问题
     * @param registry
     */
    @Override
    protected void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods(
                        RequestMethod.GET.name(),
                        RequestMethod.HEAD.name(),
                        RequestMethod.POST.name(),
                        RequestMethod.PUT.name(),
                        RequestMethod.PATCH.name(),
                        RequestMethod.DELETE.name(),
                        RequestMethod.OPTIONS.name(),
                        RequestMethod.TRACE.name())
                .maxAge(3600);
    }

    /**
     * 添加拦截器
     * @param registry
     */
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        // 全局拦截器
        registry.addInterceptor(new GlobalInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }

    /**
     * 静态资源处理
     * @param registry
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        super.addResourceHandlers(registry);
    }

    /**
     * 类型转换工厂
     * @param registry
     */
    @Override
    protected void addFormatters(FormatterRegistry registry) {
        // 枚举类自动映射
        registry.addConverterFactory(new MyIBaseEnumConverter());
    }
}
