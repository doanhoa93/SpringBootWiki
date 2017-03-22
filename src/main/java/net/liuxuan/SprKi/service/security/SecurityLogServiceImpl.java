package net.liuxuan.SprKi.service.security;


import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.liuxuan.SprKi.entity.security.LogActionType;
import net.liuxuan.SprKi.entity.security.LogLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import net.liuxuan.SprKi.repository.security.SecurityLogRepository;
import net.liuxuan.SprKi.entity.security.SecurityLog;

import java.util.List;

/**
* Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
* ***************************************************************************
* 源文件名:  net.liuxuan.SprKi.service.security.SecurityLogServiceImpl
* 功能:
* 版本:	@version 1.0
* 编制日期: 2016/04/20 14:52
* 修改历史: (主要历史变动原因及说明)
* YYYY-MM-DD |    Author      |	 Change Description
* 2016-04-20  |    Moses        |     Created
*/
@Service
@Transactional(propagation= Propagation.REQUIRED,readOnly = false)
public class SecurityLogServiceImpl implements SecurityLogService{

    private static Logger log = LoggerFactory.getLogger(SecurityLogServiceImpl.class);

    @Autowired
    SecurityLogRepository securityLogRepository;


    @Transactional(propagation= Propagation.REQUIRED,readOnly = false)
    public void saveSecurityLog(SecurityLog securityLog){
        securityLogRepository.save(securityLog);
//        securityLogRepository.saveAndFlush(securityLog);
    }

    public SecurityLog findSecurityLogById(Long id){
        SecurityLog securityLog = securityLogRepository.findOne(id);
        return securityLog;
    }

    public void deleteSecurityLogById(Long id){
        securityLogRepository.delete(id);
//        securityLogRepository.findOne(id).setDisabled(true);
    }

    public List<SecurityLog> getLastestTenActivities(int num) {
        List<SecurityLog> securityLogs = securityLogRepository.findTop10ByLogLevelOrderByLogTimeDesc(LogLevel.ACTIVITYS);
        return securityLogs;
    }
}