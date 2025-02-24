package org.example.config;

import jakarta.servlet.FilterRegistration;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import jakarta.servlet.DispatcherType;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import java.util.EnumSet;


public class MySpringMVCDIspatcherSerlvetIntializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{SpringConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    public void onStartup(ServletContext aServletContext) throws ServletException {
        super.onStartup(aServletContext);
        registerCharacterEncodingFilter(aServletContext);
        registerHiddenFieldFilter(aServletContext);
    }

    private void registerHiddenFieldFilter(ServletContext aContext) {
        aContext.addFilter("hiddenHttpMethodFilter",
                new HiddenHttpMethodFilter()).addMappingForUrlPatterns(null ,true, "/*");
    }

    private void registerCharacterEncodingFilter(ServletContext aContext) {
        // Указываем типы диспетчеризации (REQUEST и FORWARD)
        EnumSet<DispatcherType> dispatcherTypes = EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD);

        // Создаем фильтр для кодировки
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);

        // Регистрируем фильтр в контексте сервлета
        FilterRegistration.Dynamic characterEncoding = aContext.addFilter("characterEncoding", characterEncodingFilter);

        // Добавляем маппинг для URL-шаблонов
        characterEncoding.addMappingForUrlPatterns(dispatcherTypes, true, "/*");
    }
}
