package com.hdax.ssm.fitter;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

@Component(value = "formFilter")
public class MyFormAuthenticationFilte extends FormAuthenticationFilter {
    /**
     * 重写一个方法 不是必须要重写的
     *输入 iss 直接出来
     * 成功之后 进行重定向 这样shiro 认证成功之后 登录成功后 就会跳转到mian 里边的index
     * getSuccessUrld和shiro配置文件successUrl 相对应 一个set 一个get
     *
     */
    @Override
    protected void issueSuccessRedirect(ServletRequest request, ServletResponse response) throws Exception {
        WebUtils.issueRedirect(request,response,getSuccessUrl(),null,true);
    }
}
