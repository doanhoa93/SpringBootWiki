package net.liuxuan.SprKi.controller.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import net.liuxuan.SprKi.entity.DTO.BaseDTO;
import net.liuxuan.SprKi.entity.labthink.Department;
import net.liuxuan.SprKi.entity.security.Authorities;
import net.liuxuan.SprKi.entity.security.DbUser;
import net.liuxuan.SprKi.entity.security.LogActionType;
import net.liuxuan.SprKi.entity.user.UserDetailInfo;
import net.liuxuan.SprKi.repository.labthink.DepartmentRepository;
import net.liuxuan.SprKi.service.user.UserDetailInfoService;
import net.liuxuan.spring.Helper.RequestHelper;
import net.liuxuan.spring.Helper.SecurityLogHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.SprKi.controller.admin.UserManagement
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2016/4/14 10:02
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2016/4/14  |    Moses       |     Created
 */
@Controller
@RequestMapping("/admin")
public class UserManagementController {
    private static Logger log =  LoggerFactory.getLogger(UserManagementController.class);
    @Autowired
    UserDetailInfoService userDetailInfoService;
    @Resource
    DepartmentRepository departmentRepository;

    @RequestMapping("userManage")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getPages( Map<String, Object> model) {
////
        UserDetailInfo userDetailInfo = new UserDetailInfo();
        List<DbUser> dbUsers = userDetailInfoService.listAllUsers();
        model.put("user", userDetailInfo);
        model.put("dbUsers", dbUsers);
        return "admin/" + "userManage" + " :: middle";
//        return "admin/profile :: middle";
    }

    @RequestMapping("user")
//    @ResponseBody
    public String userManage(@ModelAttribute("dto") BaseDTO dto, UserDetailInfo userDetailInfo, HttpServletRequest request,
                             HttpServletResponse response, Map<String, Object> model, RedirectAttributesModelMap redirectAttributesModelMap) {
//
        log.info("===userManage logged ,the dto value is : {}", dto.toString());
        log.info("===userManage logged ,the value is : {}", userDetailInfo.toString());
//        log.info("===userManage logged ,the value is : {}",userDetailInfo.getUsers().toString());


        DbUser u = new DbUser();
        u.setUsername(dto.sid);

        switch (dto.action) {
            case "edit":

                userDetailInfo = userDetailInfoService.findUserDetailInfoByUsers(u);
                if (userDetailInfo == null) {
                    redirectAttributesModelMap.put("error", "无用户，请检查进入入口");
//                    model.put("error","无用户，请检查进入入口");
                }
//                redirectAttributesModelMap.put("user", userDetailInfo);
                model.put("user", userDetailInfo);
                return "admin/snipplets/div_user :: useredit";
//                break;
            case "roleEdit":
                userDetailInfo = userDetailInfoService.findUserDetailInfoByUsers(u);
                if (userDetailInfo == null) {
                    redirectAttributesModelMap.put("error", "无用户，请检查进入入口");
//                    model.put("error","无用户，请检查进入入口");
                }
                List<String> authslist = userDetailInfoService.listRoles();
                redirectAttributesModelMap.put("user", userDetailInfo);
                redirectAttributesModelMap.put("authslist", authslist);
                model.put("user", userDetailInfo);
                model.put("authslist", authslist);
                Set<Authorities> authoritiesSet = userDetailInfo.getDbUser().getAuths();
                Set<String> userauth = new HashSet<String>();
                authoritiesSet.forEach(auths -> userauth.add(auths.getAuthority()));
                model.put("userauth", userauth);

                return "admin/snipplets/div_user :: roleEdit";
//                break;
            default:
                redirectAttributesModelMap.addFlashAttribute("dto", dto);
                redirectAttributesModelMap.addFlashAttribute("userDetailInfo", userDetailInfo);
                return "redirect:/admin/user_ajax";
//                break;
        }
//        return "";
    }

    @RequestMapping("user_ajax")
//    @ResponseBody
    public void userManageAjax(@ModelAttribute("dto") BaseDTO dto, UserDetailInfo userDetailInfo, HttpServletRequest request,
                               HttpServletResponse response) throws IOException {
//        response.setContentType("application/json");
        Map<String, Object> rtnData = new HashMap<String, Object>();

        log.info("===userManageAjax logged ,the value is : {}", dto.toString());
        log.info("===userManageAjax logged ,the value is : {}", userDetailInfo.toString());
//        log.info("===userManage logged ,the value is : {}",userDetailInfo.getUsers().toString());


        response.setContentType("application/json");
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        switch (dto.action) {
            case "add":
                if (userDetailInfoService.checkUsersExists(userDetailInfo)) {
                    log.info("===userManageAjax logged ,新用户已存在 : {}");
                    rtnData.put("error", "ERROR_UserExists");
                    rtnData.put("status", "fail");
                    rtnData.put("msg", "用户已经存在");
                } else {
                    rtnData.put("status", "success");
                    rtnData.put("msg", "成功添加用户");
                    userDetailInfoService.saveUserDetailInfo(userDetailInfo);
                    SecurityLogHelper.LogHIGHRIGHT(request, LogActionType.USER_NEW,userDetailInfo,"新建用户"+userDetailInfo.getDbUser().getUsername(),"");
                }
                break;
            case "delete":
                boolean b = userDetailInfoService.deleteUsersByUsername(dto.sid);
                if (b) {
                    rtnData.put("status", "success");
                    rtnData.put("msg", "成功删除用户");
                    SecurityLogHelper.LogHIGHRIGHT(request, LogActionType.USER_DELETE,userDetailInfo,"成功删除用户"+userDetailInfo.getDbUser().getUsername(),"");

                } else {
                    rtnData.put("error", "ERROR_UserNotExists");
                    rtnData.put("status", "fail");
                    rtnData.put("msg", "用户不存在，删除失败");
                }
                break;
            case "update":
                userDetailInfoService.saveUserDetailInfo(userDetailInfo);
                SecurityLogHelper.LogHIGHRIGHT(request, LogActionType.USER_UPDATE,userDetailInfo,"更新用户"+userDetailInfo.getDbUser().getUsername(),"");
                rtnData.put("success1", "success!");
                break;
            case "list":
                List<DbUser> users = userDetailInfoService.listAllUsers();

//                return users;
//                    return mapper.writeValueAsString(users);
//                return EntityGsonHelper.goEntityWithCollection2Gson(Users.class);
                break;
            case "updateauth":
                RequestHelper.showParameters(request.getParameterMap());
                String[] authArrays = request.getParameterValues("authArray");
                String newauth = request.getParameter("newAuth");
                Map<String, Object> map = userDetailInfoService.updateRoles(userDetailInfo, authArrays, newauth);

                SecurityLogHelper.LogHIGHRIGHT(request, LogActionType.USER_AUTH,newauth,"更新用户权限："+userDetailInfo.getDbUser().getUsername(),"");
                rtnData.putAll(map);

//                return users;
//                    return mapper.writeValueAsString(users);
//                return EntityGsonHelper.goEntityWithCollection2Gson(Users.class);
                break;
            default:

                break;
        }
//        return "";
        mapper.writeValue(response.getWriter(), rtnData);
    }

    @ModelAttribute("Department_list")
    public List<Department> Departmentlist() {
        return departmentRepository.findAll();
    }

}
