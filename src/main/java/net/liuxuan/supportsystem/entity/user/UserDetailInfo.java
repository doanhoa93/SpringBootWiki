package net.liuxuan.supportsystem.entity.user;


import lombok.Data;
import lombok.NoArgsConstructor;
import net.liuxuan.supportsystem.entity.labthink.Department;
import net.liuxuan.supportsystem.entity.security.DbUser;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.SprKi.entity.user.UserDetailInfo
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2016/03/28 09:54
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2016-03-28  |    Moses        |     Created
 */
@Data
@NoArgsConstructor
@Entity  //实体类
@Table(name = "Sprki_Labthink_UserDetailInfo")
public class UserDetailInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false)
    protected Long id;

    //    @OneToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "dbUser")
    @OneToOne(mappedBy = "userDetailInfo")
    protected DbUser dbUser;
    @Column(name = "disabled", nullable = false)
    protected boolean disabled = false;
    @Column(nullable = true, length = 50)
    protected String firstName;
    @Column(nullable = true, length = 50)
    protected String lastName;
    @Column(nullable = true, length = 20)
    protected String gender;
    @Column(nullable = true, length = 50)
    protected String email;
    @Column(nullable = true, length = 300)
    protected String avatar;
    @Column(nullable = true, length = 500)
    protected String selfDescribe;
    @OneToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "department")
    protected Department department;
    @Transient
    protected boolean onlinestatus;


    public String getFullNameCN() {
        String rtn = "";
        if (!StringUtils.isBlank(lastName)) {
            rtn = rtn + lastName;
        }
        if (!StringUtils.isBlank(firstName)) {
            rtn = rtn + firstName;
        }
        if (StringUtils.isBlank(rtn)) {
            rtn = dbUser.getUsername();
        }
        return rtn;
    }
}