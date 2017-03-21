/*
 * Copyright (c) 2010-2016.  by Moses   All rights reserved.
 *
 */

package net.liuxuan.SprKi.repository;

import net.liuxuan.SprKi.entity.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

/**
 * Created by Moses on 2016/2/5.
 */
public interface BannerRepository extends JpaRepository<Banner, Long> {
    @Modifying
    @Query("update Banner m set m.name=?1 where m.id=?2")
    public void update(String bannerName, Long id);
}
