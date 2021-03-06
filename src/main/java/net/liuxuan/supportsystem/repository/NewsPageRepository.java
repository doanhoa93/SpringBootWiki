package net.liuxuan.supportsystem.repository;

import net.liuxuan.supportsystem.entity.NewsPage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
/**
* Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
* ***************************************************************************
* 源文件名:  net.liuxuan.SprKi.repository.NewsPageRepository
* 功能:
* 版本:	@version 1.0
* 编制日期: 2017/03/29 13:17
* 修改历史: (主要历史变动原因及说明)
* YYYY-MM-DD |    Author      |	 Change Description
* 2017-03-29  |    Moses        |     Created
*/

public interface NewsPageRepository extends JpaRepository<NewsPage, Long>, JpaSpecificationExecutor<NewsPage> {

//    List<NewsPage> findByNewsPageName(String  name);
//    List<NewsPage> findByNewsPageNameNot(String  NotName);
    List<NewsPage> findByDisabledFalse();
    List<NewsPage> findByDisabledFalseOrderByShowDateDesc();

    List<NewsPage> findTop20ByDisabledFalseOrderByLastUpdateDateDesc();
    List<NewsPage> findTop20ByDisabledFalseOrderByShowDateDesc();
    List<NewsPage> findTop20ByDisabledFalseOrderByShowDateDescLastUpdateDateDesc();

    Page<NewsPage> findAllByDisabledFalseOrderByLastUpdateDateDesc(Pageable pageable);
    Page<NewsPage> findAllByDisabledFalseOrderByShowDateDesc(Pageable pageable);
//    List<NewsPage> findByNewsPageNameNotOrderByNewsPageName(String roleNotName);

}


