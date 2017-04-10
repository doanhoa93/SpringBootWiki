package net.liuxuan.SprKi.service.labthink;

import net.liuxuan.SprKi.entity.labthink.DeviceKind;
import net.liuxuan.SprKi.repository.labthink.DeviceKindRepository;
import net.liuxuan.spring.constants.JPAConstants;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.SprKi.service.labthink.DeviceKindServiceImpl
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2017/03/31 08:50
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2017-03-31  |    Moses        |     Created
 */
@Service
@Transactional
public class DeviceKindServiceImpl implements DeviceKindService {

    private static Logger log = LoggerFactory.getLogger(DeviceKindServiceImpl.class);

    @Autowired
    DeviceKindRepository deviceKindRepository;

    /**
     * Gets all device kind.
     *
     * @return the all device kind
     */
    @Override
    public List<DeviceKind> getAllDeviceKind() {
        return deviceKindRepository.findByDeviceKindNameNot(JPAConstants.DELETEDOBJECTSTR);
    }

    /**
     * Check device kind exists boolean.
     *
     * @param deviceKindName the device kind name
     * @return the boolean
     */
    @Override
    public boolean checkDeviceKindExists(String deviceKindName) {
        List<DeviceKind> list = deviceKindRepository.findByDeviceKindNameOrDeviceKindNameENOrDeviceKindNameCN(deviceKindName, deviceKindName, deviceKindName);
//        List<DeviceKind> list = deviceKindRepository.findByDeviceKindName(deviceKindName);
        if (list.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Save device kind.
     *
     * @param obj the devices
     */
    @Override
    public void saveDeviceKind(DeviceKind obj) {
        deviceKindRepository.save(obj);
    }

    /**
     * Delete device kind by id boolean.
     *
     * @param sid the sid
     * @return the boolean
     */
    @Override
    public boolean deleteDeviceKindById(String sid) {
        if (NumberUtils.isNumber(sid)) {
            Long id = Long.parseLong(sid);
            DeviceKind obj = deviceKindRepository.getOne(id);
            if (obj != null) {
                obj.setDeviceKindName(JPAConstants.DELETEDOBJECTSTR);
                obj.setDeviceKindNameCN(JPAConstants.DELETEDOBJECTSTR);
                obj.setDeviceKindNameEN(JPAConstants.DELETEDOBJECTSTR);
//                obj.setDepartmentName(JPAConstants.DELETEDOBJECTSTR);
//                obj.setDepartmentNameCN(JPAConstants.DELETEDOBJECTSTR);
                return true;
            }
        }
        return false;
    }

    /**
     * Gets device kind by id.
     *
     * @param id the id
     * @return the device kind by id
     */
    @Override
    public DeviceKind getDeviceKindById(Long id) {
        DeviceKind obj = deviceKindRepository.getOne(id);
        return obj;
    }
}