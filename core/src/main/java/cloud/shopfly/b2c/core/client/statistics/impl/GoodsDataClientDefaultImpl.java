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
package cloud.shopfly.b2c.core.client.statistics.impl;

import cloud.shopfly.b2c.core.client.statistics.GoodsDataClient;
import cloud.shopfly.b2c.core.statistics.model.dto.GoodsData;
import cloud.shopfly.b2c.core.statistics.service.GoodsDataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * GoodsDataClientDefaultImpl
 *
 * @author Chopper
 * @version v1.0
 * @since v7.0
 * 2018-08-14 下午2:37
 */
@Service
@ConditionalOnProperty(value = "shopfly.product", havingValue = "stand")
public class GoodsDataClientDefaultImpl implements GoodsDataClient {

    @Autowired
    private GoodsDataManager goodsDataManager;

    /**
     * 新增商品
     *
     * @param goodsIds 商品id
     */
    @Override
    public void addGoods(Integer[] goodsIds) {
        goodsDataManager.addGoods(goodsIds);
    }

    /**
     * 修改商品
     *
     * @param goodsIds 商品id
     */
    @Override
    public void updateGoods(Integer[] goodsIds) {
        goodsDataManager.updateGoods(goodsIds);
    }

    /**
     * 删除商品
     *
     * @param goodsIds 商品id
     */
    @Override
    public void deleteGoods(Integer[] goodsIds) {
        goodsDataManager.deleteGoods(goodsIds);
    }

    /**
     * 修改商品收藏数量
     *
     * @param goodsData
     */
    @Override
    public void updateCollection(GoodsData goodsData) {
        goodsDataManager.updateCollection(goodsData);
    }

    /**
     * 下架所有商品
     */
    @Override
    public void underAllGoods() {
        goodsDataManager.underAllGoods();
    }


}
