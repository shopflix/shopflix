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
package cloud.shopfly.b2c.core.promotion.pintuan.service;

import cloud.shopfly.b2c.core.promotion.model.PromotionAbnormalGoods;
import cloud.shopfly.b2c.core.promotion.pintuan.model.PinTuanGoodsVO;
import cloud.shopfly.b2c.core.goods.model.vo.GoodsSkuVO;
import cloud.shopfly.b2c.core.promotion.pintuan.model.PintuanGoodsDO;
import cloud.shopfly.b2c.framework.database.Page;

import java.util.List;

/**
 * 拼团商品业务层
 *
 * @author admin
 * @version vv1.0.0
 * @since vv7.1.0
 * 2019-01-22 11:20:56
 */
public interface PintuanGoodsManager {

    /**
     * 查询拼团商品列表
     *
     * @param page     页码
     * @param pageSize 每页数量
     * @return Page
     */
    Page list(int page, int pageSize);


    /**
     * 添加拼团商品
     *
     * @param pintuanGoods 拼团商品
     * @return PintuanGoods 拼团商品
     */
    PintuanGoodsDO add(PintuanGoodsDO pintuanGoods);


    /**
     * 批量保存拼团商品数据
     *
     * @param pintuanId        拼团id
     * @param pintuanGoodsList 要批量添加的拼团商品
     */
    void save(Integer pintuanId, List<PintuanGoodsDO> pintuanGoodsList);

    /**
     * 修改拼团商品
     *
     * @param pintuanGoods 拼团商品
     * @param id           拼团商品主键
     * @return PintuanGoods 拼团商品
     */
    PintuanGoodsDO edit(PintuanGoodsDO pintuanGoods, Integer id);

    /**
     * 删除拼团商品
     *
     * @param id 拼团商品主键
     */
    void delete(Integer id);

    /**
     * 获取拼团商品
     *
     * @param id 拼团商品主键
     * @return PintuanGoods  拼团商品
     */
    PintuanGoodsDO getModel(Integer id);

    /**
     * 获取拼团商品
     *
     * @param pintuanId
     * @param skuId
     * @return
     */
    PintuanGoodsDO getModel(Integer pintuanId, Integer skuId);

    /**
     * 获取拼团商品详细，包括拼团本身的信息
     *
     * @param skuId skuid
     * @return 商品详细vo
     */
    PinTuanGoodsVO getDetail(Integer skuId);


    /**
     * 获取某商品参加拼团的sku
     *
     * @param goodsId
     * @param pintuanId
     * @return
     */
    List<GoodsSkuVO> skus(Integer goodsId, Integer pintuanId);

    /**
     * 更新已团数量
     *
     * @param id  拼团商品id
     * @param num 数量
     */
    void addQuantity(Integer id, Integer num);

    /**
     * 获取某活动所有商品
     *
     * @param promotionId
     * @return
     */
    List<PinTuanGoodsVO> all(Integer promotionId);


    /**
     * 关闭一个活动的促销商品索引
     *
     * @param promotionId
     */
    void delIndex(Integer promotionId);

    /**
     * 开启一个活动的促销商品索引
     *
     * @param promotionId
     * @return
     */
    boolean addIndex(Integer promotionId);


    /**
     * 商品查询
     *
     * @param page        页码
     * @param pageSize    分页大小
     * @param promotionId 促销id
     * @param name        商品名称
     * @return
     */
    Page page(Integer page, Integer pageSize, Integer promotionId, String name);

    /**
     * 查询指定时间范围，是否有参与其他活动
     *
     * @param skuIds      商品id集合
     * @param startTime   开始时间
     * @param endTime     结束时间
     * @param promotionID 促销id
     * @return
     */
    List<PromotionAbnormalGoods> checkPromotion(Integer[] skuIds, Long startTime, Long endTime, Integer promotionID);

}