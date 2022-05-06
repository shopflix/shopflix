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
package cloud.shopfly.b2c.core.goods.service;

import cloud.shopfly.b2c.core.goods.model.dos.GoodsDO;
import cloud.shopfly.b2c.core.goods.model.dto.GoodsQueryParam;
import cloud.shopfly.b2c.core.goods.model.vo.CacheGoods;
import cloud.shopfly.b2c.core.goods.model.vo.GoodsSelectLine;
import cloud.shopfly.b2c.core.goods.model.vo.GoodsVO;
import cloud.shopfly.b2c.framework.database.Page;

import java.util.List;
import java.util.Map;

/**
 * 商品业务层
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0 2018-03-21 11:23:10
 */
public interface GoodsQueryManager {

    /**
     * 获取商品
     *
     * @param id 商品主键
     * @return Goods 商品
     */
    GoodsDO getModel(Integer id);

    /**
     * 查询商品的好平率
     *
     * @param goodsId
     * @return
     */
    Double getGoodsGrade(Integer goodsId);

    /**
     * 缓存中查询商品的信息
     *
     * @param goodsId
     * @return
     */
    CacheGoods getFromCache(Integer goodsId);

    /**
     * 查看商品是否在配送区域
     *
     * @param goodsId
     * @param areaId
     * @return
     */
    Integer checkArea(Integer goodsId, Integer areaId);

    /**
     * 获取商品分类路径
     *
     * @param id
     * @return
     */
    String queryCategoryPath(Integer id);

    /**
     * 查询商品列表
     *
     * @param goodsQueryParam
     * @return
     */
    Page list(GoodsQueryParam goodsQueryParam);

    /**
     * 查询预警货品的商品
     *
     * @param pageNo
     * @param pageSize
     * @param keyword
     * @return
     */
    Page warningGoodsList(int pageNo, int pageSize, String keyword);

    /**
     * 商家查询商品,编辑查看使用
     *
     * @param goodsId
     * @return
     */
    GoodsVO queryGoods(Integer goodsId);

    /**
     * 查询多个商品的基本信息
     *
     * @param goodsIds
     * @return
     */
    List<GoodsSelectLine> query(Integer[] goodsIds);

    /**
     * 根据条件查询商品数
     *
     * @param status   商品状态
     * @param disabled 商品是否已删除
     * @return 商品数
     */
    Integer getGoodsCountByParam(Integer status, Integer disabled);

    /**
     * 查询很多商品的信息和参数信息
     *
     * @param goodsIds 商品id集合
     * @return
     */
    List<Map<String, Object>> getGoodsAndParams(Integer[] goodsIds);

    /**
     * 根据商品id集合查询商品信息
     *
     * @param goodsIds 商品ids
     * @return  商品信息
     */
    List<Map<String, Object>> getGoods(Integer[] goodsIds);

    /**
     * 获取所有商品的id集合
     * @return
     */
    List<Integer> getAllGoodsId();

}