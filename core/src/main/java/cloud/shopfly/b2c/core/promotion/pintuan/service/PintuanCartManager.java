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

import cloud.shopfly.b2c.core.trade.cart.model.vo.CartSkuOriginVo;
import cloud.shopfly.b2c.core.trade.cart.model.vo.CartView;

/**
 * Created by kingapex on 2019-01-23.
 * 拼团购物车业务类接口
 *
 * @author kingapex
 * @version 1.0
 * @since 7.1.0
 * 2019-01-23
 */
public interface PintuanCartManager {


    /**
     * 获取拼团购物车
     *
     * @return 购物车视图
     */
    CartView getCart();


    /**
     * 将一个拼团的sku加入到购物车中
     *
     * @param skuId
     * @param num   加入的数量
     * @return
     */
    CartSkuOriginVo addSku(Integer skuId, Integer num);


}
