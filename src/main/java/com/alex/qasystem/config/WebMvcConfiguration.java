package com.alex.qasystem.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * ��չSpringMVC����
 *
 * @author Alex
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    /**
     * ��ǰҪ����һ��ҳ����Ҫ�ȴ�����Controller�����࣬��д������ת��ҳ��
     * ���������ú�Ͳ���Ҫ��ô�鷳�ˣ�ֱ�ӷ���http://localhost:8080/toLogin����ת��login.htmlҳ����
     *
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/toLogin").setViewName("login");
    }

}
