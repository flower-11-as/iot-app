package com.scrawl.iot.web.congufuration;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.google.common.collect.Lists;
import com.scrawl.iot.web.shiro.IotShiroRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Description:
 * Created by as on 2018/3/25.
 */
@Configuration
public class ShiroConfiguration {
    /**
     * Shiro生命周期处理器
     *
     * @return
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     * 配置以下两个bean(DefaultAdvisorAutoProxyCreator(可选)和AuthorizationAttributeSourceAdvisor)即可实现此功能
     *
     * @return
     */
    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    /**
     * web安全管理
     *
     * @return 默认安全管理对象
     */
    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(iotShiroRealm());
        securityManager.setCacheManager(ehCacheManager());
        return securityManager;
    }

    @Bean
    public AuthorizingRealm iotShiroRealm() {
        IotShiroRealm iotShiroRealm = new IotShiroRealm();
        iotShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return iotShiroRealm;
    }

    /**
     * hash 证书校验
     *
     * @return hash证书校验对象
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        // 配置hash算法名称
        credentialsMatcher.setHashAlgorithmName("md5");
        // 算法迭代次数
        credentialsMatcher.setHashIterations(2);
        return credentialsMatcher;
    }

    /**
     * 开启shiro aop注解支持.
     * 使用代理方式;所以需要开启代码支持;
     *
     * @param securityManager 安全管理
     * @return shiro aop注解支持
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    // 排除授权验证路径
    private List<String> excludeAuthPaths = Lists.newArrayList(
            "/swagger-resources/**",
            "/v2/api-docs",
            "/webjars/**",
            "/swagger-ui.html",
            "/css/**",
            "/js/**",
            "/fonts/**",
            "/img/**",
            "/docs/**",
            "/iotLogin"
    );

    /**
     * shiro 过滤器工厂Bean
     *
     * @return shiro过滤器工厂Bean
     */
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager());
        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setSuccessUrl("/index");
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        HashMap<String, String> filterChainMap = new LinkedHashMap<>();
        excludeAuthPaths.forEach(excludeAuthPath -> filterChainMap.put(excludeAuthPath, "anon"));
        filterChainMap.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainMap);
        return shiroFilterFactoryBean;
    }

    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }

    @Bean
    public EhCacheManager ehCacheManager() {
        EhCacheManager em = new EhCacheManager();
        em.setCacheManagerConfigFile("classpath:config/ehcache.xml");
        return em;
    }
}
