/*
 *  Copyright 2008-2022 Shopfly.cloud Group.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package cloud.shopfly.b2c.core.statistics.service.impl;

import cloud.shopfly.b2c.core.statistics.model.vo.ChartSeries;
import cloud.shopfly.b2c.core.statistics.model.vo.SimpleChart;
import cloud.shopfly.b2c.core.statistics.service.CollectFrontStatisticsManager;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.database.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 商家中心，商品收藏统计实现类
 *
 * @author mengyuanming
 * @version 2.0
 * @since 7.0
 * 2018年4月20日下午4:35:26
 */
@Service
public class CollectFrontStatisticsManagerImpl implements CollectFrontStatisticsManager {

    @Autowired
    
    private DaoSupport daoSupport;

    /**
     * 商品收藏数量统计
     *
     * @return simpleChart 简单图表数据
     */
    @Override
    public SimpleChart getChart() {

        // 从es_sss_goods_data表中查询，商品名称，收藏数量
        String sql = " SELECT goods_id,favorite_num,goods_name FROM es_sss_goods_data ORDER BY favorite_num DESC LIMIT 50 ";

        List<Map<String, Object>> list = this.daoSupport.queryForList(sql);

        // 收藏数量数组，对应chart数据
        String[] data = new String[list.size()];

        // 商品名数组，对应chart数据名称
        String[] localName = new String[list.size()];

        // x轴刻度，从1开始，以数据量为准，没有数据则为0
        String[] xAxis = new String[list.size()];

        // 如果有数据，则加入数组
        if (!list.isEmpty()) {
            int i = 0;
            for (Map<String, Object> map : list) {
                data[i] = map.get("favorite_num").toString();
                localName[i] = map.get("goods_name").toString();
                xAxis[i] = i + 1 + "";
                i++;
            }
        }

        ChartSeries series = new ChartSeries("收藏数", data, localName);

        // 数据，x轴刻度，y轴刻度
        return new SimpleChart(series, xAxis, new String[0]);
    }

    /**
     * 商品收藏统计表格
     *
     * @param pageNo，页码
     * @param pageSize，页面数据量
     * @return Page 分页数据
     */
    @Override
    public Page getPage(Integer pageNo, Integer pageSize) {

        // 获取商品名，收藏数量，商品价格的正在出售的商品，按收藏数量排序
        String sql = "select goods_id,goods_name,favorite_num,price from es_sss_goods_data where  market_enable = 1 order by favorite_num desc";

        return this.daoSupport.queryForPage(sql, pageNo, pageSize);
    }

}
