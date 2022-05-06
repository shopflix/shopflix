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
package cloud.shopfly.b2c.consumer.shop.promotion;

import cloud.shopfly.b2c.consumer.core.event.GoodsChangeEvent;
import cloud.shopfly.b2c.core.base.message.GoodsChangeMsg;
import cloud.shopfly.b2c.core.promotion.groupbuy.service.GroupbuyGoodsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
* @author liuyulei
 * @version 1.0
 * @Description:  修改团购商品信息
 * @date 2019/5/15 9:50
 * @since v7.0
 */
@Component
public class GroupBuyGoodsChangeConsumer implements GoodsChangeEvent {

    @Autowired
    private GroupbuyGoodsManager groupbuyGoodsManager;

    @Override
    public void goodsChange(GoodsChangeMsg goodsChangeMsg) {
        if (GoodsChangeMsg.MANUAL_UPDATE_OPERATION == goodsChangeMsg.getOperationType()) {
            this.groupbuyGoodsManager.updateGoodsInfo(goodsChangeMsg.getGoodsIds());
        }
    }
}
