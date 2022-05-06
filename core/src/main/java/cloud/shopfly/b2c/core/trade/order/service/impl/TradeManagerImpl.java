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
package cloud.shopfly.b2c.core.trade.order.service.impl;

import cloud.shopfly.b2c.core.trade.order.model.vo.TradeVO;
import cloud.shopfly.b2c.core.trade.order.service.*;
import cloud.shopfly.b2c.core.client.goods.GoodsClient;
import cloud.shopfly.b2c.core.client.member.MemberAddressClient;
import cloud.shopfly.b2c.core.client.member.MemberClient;
import cloud.shopfly.b2c.core.member.model.dos.MemberAddress;
import cloud.shopfly.b2c.core.payment.model.enums.ClientType;
import cloud.shopfly.b2c.core.trade.cart.model.vo.CartView;
import cloud.shopfly.b2c.core.trade.cart.service.CartReadManager;
import cloud.shopfly.b2c.core.trade.order.model.vo.CheckoutParamVO;
import cloud.shopfly.b2c.core.trade.order.service.*;
import cloud.shopfly.b2c.framework.util.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
 * 交易业务类
 *
 * @author Snow create in 2018/4/8
 * @version 2.1
 * 使用TradeCreator来创建交易
 * @since v7.0.0
 */
@Service
@Primary
public class TradeManagerImpl implements TradeManager {
    protected final Log logger = LogFactory.getLog(this.getClass());


    @Autowired
    protected CheckoutParamManager checkoutParamManager;

    @Autowired
    protected CartReadManager cartReadManager;

    @Autowired
    protected ShippingManager shippingManager;

    @Autowired
    protected GoodsClient goodsClient;


    @Autowired
    protected TradeSnCreator tradeSnCreator;

    @Autowired
    protected MemberAddressClient memberAddressClient;

    @Autowired
    protected MemberClient memberClient;

    @Autowired
    protected TradeIntodbManager tradeIntodbManager;


    @Override
    public TradeVO createTrade(String client) {

        this.setClientType(client);
        CheckoutParamVO param = checkoutParamManager.getParam();
        CartView cartView = this.cartReadManager.getCheckedItems();
        MemberAddress memberAddress = this.memberAddressClient.getModel(param.getAddressId());

        TradeCreator tradeCreator = new DefaultTradeCreator(param, cartView, memberAddress).setTradeSnCreator(tradeSnCreator).setGoodsClient(goodsClient).setMemberClient(memberClient).setShippingManager(shippingManager);

        //检测配置范围-> 检测商品合法性 -> 检测促销活动合法性 -> 创建交易
        TradeVO tradeVO = tradeCreator.checkShipRange().checkGoods().checkPromotion().createTrade();

        //订单入库
        this.tradeIntodbManager.intoDB(tradeVO);

        return tradeVO;
    }


    /**
     * 设置client type
     *
     * @param client
     */
    protected void setClientType(String client) {

        String clientType = null;
        if (StringUtil.isWap()) {
            clientType = ClientType.WAP.name();
        } else if (ClientType.REACT.getClient().equals(client)) {

            clientType = ClientType.REACT.getClient();

        } else {
            clientType = ClientType.PC.name();
        }

        this.checkoutParamManager.setClientType(clientType);
    }


}
