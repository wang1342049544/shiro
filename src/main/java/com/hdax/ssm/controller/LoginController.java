package com.hdax.ssm.controller;

import com.hdax.ssm.constant.Constants;
import com.hdax.ssm.dao.PermissionDao;
import com.hdax.ssm.dao.RolePermissionDao;
import com.hdax.ssm.dao.UserRoleDao;
import com.hdax.ssm.entity.Permission;
import com.hdax.ssm.entity.Role;
import com.hdax.ssm.entity.User;
import com.hdax.ssm.entity.UserRole;
import com.hdax.ssm.service.UserRoleService;
import com.hdax.ssm.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.*;

/**
 * shiro 控制器 登录配置
 * 跟application-shiro.xml     <bean id="shiroFilter" class="org.apache.shiro.spring.web.ShiroFilterFactoryBean">里边配置
 */
@RestController
@RequestMapping(path = "/user")
public class LoginController {
    @Autowired
    private UserService userService; //管理员表
    @Autowired
    private UserRoleService userRoleDao;//职位和管理关系表
    @Autowired
    private RolePermissionDao rolePermissionDao;//权限和职位关系表
    @Autowired
    private PermissionDao permissionDao; //权限表
    @GetMapping(path = "/login")
    public ModelAndView login(Model model) {
        return new ModelAndView("login");
    }


    //运行成功后自己去login登陆页面 ,因为application-shiro.xml配置已经帮你完成了
   /* @PostMapping(path = "/login")*/
    @RequestMapping("/login")
    @ResponseBody
    public Map<String, String> login(@Param("username") String uname, @Param("password") String upass,HttpServletRequest request,Model model) {

        Map<String, String> map = new HashMap<String, String>();
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(uname, upass);
        try {

            subject.login(token);
            map.put("code", "1");
            map.put("msg", "登录成功");
            return map;
        } catch (UnknownAccountException e) {
            map.put("code", "2");
            map.put("msg", Constants.LoginInfo.USERNAME);
            return map;
        } catch (IncorrectCredentialsException e) {
            map.put("code", "3");
            map.put("msg", Constants.LoginInfo.PASSWORD);
            return map;
        }
    }


    /**
     *
     * 登录成功后 去index页面
     *
     * @return
     */
    @RequestMapping("/main")
        public ModelAndView main(HttpServletRequest request) {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        System.out.println("user  是" +   user);

        //登录用户存session
        request.getSession().setAttribute(Constants.LoginInfo.USER, user);

        //1.得到登录的用户,将user表的id装进去
        UserRole userRole = userRoleDao.getUserRoleId(user.getId());
        System.out.println("userRole   "+userRole);
        //2.当前角色
       List<Integer> list = rolePermissionDao.getPermissionByRoleId(userRole.getRole_Id());
     List<Permission> list1 = permissionDao.queryByPermissionIds(list);
        //存放父节点


        List<Permission> list2 = new LinkedList<>();
        for (Permission permission : list1){
            if(permission.getParentid() == 0){
                list2.add(permission);
            }
        }
        System.out.println(list);
            System.out.println("list1 "+list1);
            System.out.println("list2 "+list2);
        request.getSession().setAttribute("list1",list1);
        request.getSession().setAttribute("list2",list2);
        return new ModelAndView("index");
    }
}
