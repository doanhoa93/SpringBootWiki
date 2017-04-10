package net.liuxuan.SprKi.service.labthink;

import net.liuxuan.SprKi.entity.labthink.Department;
import net.liuxuan.SprKi.entity.labthink.DeviceKind;
import net.liuxuan.SprKi.entity.labthink.DeviceType;
import net.liuxuan.SprKi.entity.labthink.Devices;

import java.util.List;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.SprKi.service.labthink.DepartmentService
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2016/4/14 11:08
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2016/4/14  |    Moses       |     Created
 */
public interface DevicesService {

    /**
     * Gets all devices.
     *
     * @return the all devices
     */
    List<Devices> getAllDevices();

    /**
     * Check devices exists boolean.
     *
     * @param devicename the devicename
     * @return the boolean
     */
    boolean checkDevicesExists(String devicename);

    /**
     * Save devices.
     *
     * @param obj the obj
     */
    void saveDevices(Devices obj);

    /**
     * Delete devices by id boolean.
     *
     * @param sid the sid
     * @return the boolean
     */
    boolean deleteDevicesById(String sid);

    /**
     * Gets devices by id.
     *
     * @param id the id
     * @return the devices by id
     */
    Devices getDevicesById(Long id);





}
