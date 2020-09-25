package com.hdax.ssm.realm;

import com.hdax.ssm.entity.User;
import com.hdax.ssm.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

public class CustomRealm extends AuthorizingRealm {
    @Autowired
    private UserService UserService;
    /**
     * 认证方法
     * token 令牌  用户点击登录时 将你的账号密码存放在一个令牌里，那你登录是不是再shiro里边被作为认证啊
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //拿到账号
        String username = token.getPrincipal().toString();
        System.out.println(username);
        //数据库查询 根据你的账号去数据库查，查到后会有一个对象
        User user = null;
        try {
            user = UserService.login(username);

        } catch (Exception e) {
            e.printStackTrace();
        }
        if(user == null){
            System.out.println("认证是null");
            return null;
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user,user.getPassword(), ByteSource.Util.bytes(user.getSalt()),getName());
        return authenticationInfo;
    }
    /**
     * 授权方法
     * 当一个用户认证成功之后  才会进行授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        return authorizationInfo;
    }
}
