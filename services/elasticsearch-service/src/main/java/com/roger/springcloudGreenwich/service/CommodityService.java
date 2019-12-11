package com.roger.springcloudGreenwich.service;

import com.roger.springcloudGreenwich.bean.Commodity;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by admin on 2019/12/11.
 */
public interface CommodityService {
    long count();

    Commodity save(Commodity commodity);

    void delete(Commodity commodity);

    Iterable<Commodity> getAll();

    List<Commodity> getByName(String name);

    Page<Commodity> pageQuery(Integer pageNo, Integer pageSize, String kw);
}
