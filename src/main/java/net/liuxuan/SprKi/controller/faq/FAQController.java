package net.liuxuan.SprKi.controller.faq;

import net.liuxuan.SprKi.entity.CMSCategory;
import net.liuxuan.SprKi.entity.CMSCategoryEditor;
import net.liuxuan.SprKi.entity.DTO.FAQSearchDTO;
import net.liuxuan.SprKi.entity.labthink.Department;
import net.liuxuan.SprKi.entity.labthink.Devices;
import net.liuxuan.SprKi.entity.labthink.FAQContent;
import net.liuxuan.SprKi.repository.CMSCategoryRepository;
import net.liuxuan.SprKi.repository.labthink.DepartmentRepository;
import net.liuxuan.SprKi.repository.labthink.DevicesRepository;
import net.liuxuan.SprKi.service.labthink.FAQContentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.SprKi.controller.faq.FAQController
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2016/3/8 15:52
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2016/3/8  |    Moses       |     Created
 */

@Controller
@PreAuthorize("hasRole('ROLE_USER')")
public class FAQController {
    private static Logger log =  LoggerFactory.getLogger(FAQController.class);

    @Resource
    DevicesRepository devicesRepository;

    @Resource
    DepartmentRepository departmentRepository;

    @Resource
    CMSCategoryRepository cmsCategoryRepository;

    @Autowired
    FAQContentService faqContentService;




    @RequestMapping(value = "/faq", method = RequestMethod.GET)
//    @PreAuthorize("hasRole('ROLE_USER')")
    public String getFAQ(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model)  {

        log.info("-FAQController.getFAQ() Method");
//        model.put("message", "Editor");
        model.put("title", "FAQ 编辑界面");
        FAQContent faq = new FAQContent();
        faq.setDescription("dddddddddddddddd");
        model.put("faq", faq);
//        devicesRepository.findAll();
        List<Devices> devicesAll = devicesRepository.findAll();
//        devicesAll.forEach(devices -> {devices.setDeviceType(null);devices.setDevicename(null);});
//        model.put("devicesAll",devicesAll);
//        UserDetails u = (UserDetails) SystemHelper.getAuthentication().getPrincipal();
        return "faq/faq_edit";
    }

    @RequestMapping(value = "/faq/{id}", method = RequestMethod.GET)
//    @PreAuthorize("hasRole('ROLE_USER')")
    public String getFAQID(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response, Map<String, Object> model)  {

        FAQContent faq = faqContentService.findById(id);


        log.trace("faq to show publish_date is:{}",faq.getPublishDate());
        log.trace("faq to show lastUpdate_date is:{}",faq.getLastUpdateDate());

        model.put("faq", faq);
//        devicesRepository.findAll();
        List<Devices> devicesAll = devicesRepository.findAll();
//        devicesAll.forEach(devices -> {devices.setDeviceType(null);devices.setDevicename(null);});
//        model.put("devicesAll",devicesAll);
//        UserDetails u = (UserDetails) SystemHelper.getAuthentication().getPrincipal();
        return "faq/faq_edit";
    }

    @RequestMapping(value = "/faq/show/{id}", method = RequestMethod.GET)
//    @PreAuthorize("hasRole('ROLE_USER')")
    public String showFAQID(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response, Map<String, Object> model)  {

        FAQContent faq = faqContentService.findById(id);

        model.put("faq", faq);
//        devicesRepository.findAll();
        List<Devices> devicesAll = devicesRepository.findAll();
//        devicesAll.forEach(devices -> {devices.setDeviceType(null);devices.setDevicename(null);});
//        model.put("devicesAll",devicesAll);
//        UserDetails u = (UserDetails) SystemHelper.getAuthentication().getPrincipal();
        return "faq/faq_show";
    }

    @RequestMapping(value = "/faq/delete/{id}", method = RequestMethod.GET)
//    @PreAuthorize("hasRole('ROLE_USER')")
    public String deleteFAQID(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response, Map<String, Object> model)  {

//        FAQContent faq = faqContentService.findById(id);
        faqContentService.deleteFAQContentById(id);
        return "redirect:/faqlist";
    }


    @RequestMapping(value = "/faqlist")
//    @PreAuthorize("hasRole('ROLE_USER')")
    public String getFAQList(FAQSearchDTO dto, HttpServletRequest request, HttpServletResponse response, Map<String, Object> model)  {
        log.debug("===getFAQList logged ,the DTO value is : {}",dto);
//        log.debug("===getFAQList logged ,the isnull is : {}",dto.isAllNull());
        boolean dtoAllNull = dto.isAllNull();
        model.put("dtoNull", dtoAllNull);
        if(dtoAllNull){
            //参数全为空
        }

        List<FAQContent> allFAQContents = faqContentService.findAllFAQContentsByDto(dto);
        log.info("list size is {}",allFAQContents.size());
        model.put("allfaqlist",allFAQContents);
        model.put("dto",dto);
        return "faq/faq_list";
    }


    @RequestMapping(value = "/faqpost", method = RequestMethod.POST)
//    @PreAuthorize("hasRole('ROLE_USER')")
    public String postFAQ(FAQContent faq,HttpServletRequest request, Map<String, Object> model) {

        Map<String, String[]> parameterMap = request.getParameterMap();
        for (String s : parameterMap.keySet()) {
            String[] strings = parameterMap.get(s);
            for (int i = 0; i < strings.length; i++) {
                log.info("===Parameter key:{} ,values{}:{}",s,i,strings[i]);
            }
        }


        faqContentService.saveFAQContent(faq);


        faq.setDescription("cccccc");
        model.put("faq", faq);
        model.put("title", "FAQ 编辑界面");
        return "faq/faq_edit";

    }




    @ModelAttribute("Devices_list")
    public List<Devices> Deviceslist() {
        return devicesRepository.findAll();
    }
    @ModelAttribute("Department_list")
    public List<Department> Departmentlist() {
        return departmentRepository.findAll();
    }
    @ModelAttribute("Category_list")
    public List<CMSCategory> Categorylist() {
        return cmsCategoryRepository.findAll();
    }

//    @Autowired
//    CMSCategoryEditor cmsCategoryEditor;

    @Bean
    private CMSCategoryEditor cmsCategoryEditor(){
        return new CMSCategoryEditor();
    }


    @InitBinder
    protected void initBinder(
            WebDataBinder binder) throws ServletException {
        binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        dateFormat.setLenient(false);
        dateFormat.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
//        binder.registerCustomEditor(CMSCategory.class,cmsCategoryEditor());
//        binder.registerCustomEditor(Department.class,new DepartmentEditor());
    }
}
