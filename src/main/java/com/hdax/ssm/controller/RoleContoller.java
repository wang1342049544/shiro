package com.hdax.ssm.controller;


import com.hdax.ssm.dao.PermissionDao;
import com.hdax.ssm.dao.RoleDao;
import com.hdax.ssm.dao.RolePermissionDao;
import com.hdax.ssm.entity.Permission;
import com.hdax.ssm.entity.Role;
import com.hdax.ssm.pojo.PermissionBean;
import com.hdax.ssm.pojo.StateBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping(path = "/role")
//@Scope
public class RoleContoller {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PermissionDao permissionDao;
    @Autowired
    private RolePermissionDao rolePermissionDao;
    @RequestMapping(path = "/roleList")
    @ResponseBody
    public List<Role> queryAll(){
        List<Role> list = roleDao.queryAll();
        return list;
    }


    //去页面
    @GetMapping(path = "/go")
    public ModelAndView list(){
        return new ModelAndView("Role") ;
    }


    /*分配权限*/

    @RequestMapping("/doFenPei")
    @ResponseBody
    public List<PermissionBean> doFenPei(int roleid){
//        //得到当前角色的所有权限
     List<Permission> listAll = rolePermissionDao.queryByRoleId(roleid);

        //获取根节点
        List<PermissionBean> list = permissionDao.query();
        for (PermissionBean perm : list){
            int id = perm.getId();
            //查询子节点
            List<PermissionBean> list1 = permissionDao.queryId(id);
            perm.setNodes(list1);
        }
        System.out.println("lsit "+list);

        //回显当前角色拥有的所有权限
        for (PermissionBean per : list){
            StateBean stateBean = new StateBean();
            for (Permission permission : listAll){
                if(per.getId() == permission.getPermissionId()){
                    stateBean.setChecked(false);
                }

            }
          per.setState(stateBean);

            //所有子节点
            List<PermissionBean> listSon = per.getNodes();

            for (PermissionBean permission1 : listSon){
                StateBean stateBean1 = new StateBean();

                for (Permission permission11 : listAll){
                    if (permission1.getId() == permission11.getPermissionId()){
                        stateBean.setChecked(true);
                        stateBean1.setChecked(true);
                    }
                }
                permission1.setState(stateBean1);
            }

      }
        return list;
    }






}
