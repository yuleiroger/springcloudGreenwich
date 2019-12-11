package com.roger.springcloudGreenwich.service;

import com.roger.springcloudGreenwich.bean.Commodity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by admin on 2019/12/11.
 */
@Repository
public interface CommodityRepository extends ElasticsearchRepository<Commodity, String> {

}
