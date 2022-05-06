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
package cloud.shopfly.b2c.consumer.shop.goods;

import cloud.shopfly.b2c.consumer.core.event.OrderStatusChangeEvent;
import cloud.shopfly.b2c.consumer.core.event.RefundStatusChangeEvent;
import cloud.shopfly.b2c.core.aftersale.model.dos.RefundDO;
import cloud.shopfly.b2c.core.aftersale.model.dos.RefundGoodsDO;
import cloud.shopfly.b2c.core.aftersale.model.enums.RefundStatusEnum;
import cloud.shopfly.b2c.core.aftersale.model.enums.RefuseTypeEnum;
import cloud.shopfly.b2c.core.base.message.OrderStatusChangeMsg;
import cloud.shopfly.b2c.core.base.message.RefundChangeMsg;
import cloud.shopfly.b2c.core.client.goods.GoodsQuantityClient;
import cloud.shopfly.b2c.core.client.trade.AfterSaleClient;
import cloud.shopfly.b2c.core.client.trade.OrderClient;
import cloud.shopfly.b2c.core.goods.model.enums.QuantityType;
import cloud.shopfly.b2c.core.goods.model.vo.GoodsQuantityVO;
import cloud.shopfly.b2c.core.trade.order.model.dos.OrderDO;
import cloud.shopfly.b2c.core.trade.order.model.enums.OrderStatusEnum;
import cloud.shopfly.b2c.core.trade.order.model.enums.PayStatusEnum;
import cloud.shopfly.b2c.core.trade.order.model.enums.ShipStatusEnum;
import cloud.shopfly.b2c.core.trade.order.model.vo.OrderSkuVO;
import cloud.shopfly.b2c.core.trade.sdk.model.OrderDetailDTO;
import cloud.shopfly.b2c.framework.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品库存增加/扣减
 *
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018年6月22日 上午10:18:20
 */
@Service
public class GoodsQuantityChangeConsumer implements OrderStatusChangeEvent, RefundStatusChangeEvent {

    @Autowired
    private GoodsQuantityClient goodsQuantityClient;

    @Autowired
    private AfterSaleClient afterSaleClient;

    @Autowired
    private OrderClient orderClient;

    private final Logger logger = LoggerFactory.getLogger(getClass().getName());


    /**
     * 订单变化处理
     *
     * @param orderMessage
     */
    @Override
    public void orderChange(OrderStatusChangeMsg orderMessage) {
        //发货
        if (orderMessage.getNewStatus().name().equals(OrderStatusEnum.SHIPPED.name())) {
            //获取订单信息
            OrderDO order = orderMessage.getOrderDO();
            String itemsJson = order.getItemsJson();
            //订单中的sku集合
            List<OrderSkuVO> list = JsonUtil.jsonToList(itemsJson, OrderSkuVO.class);
            List<GoodsQuantityVO> quantityVOList = new ArrayList<>();
            for (OrderSkuVO sku : list) {
                GoodsQuantityVO goodsQuantity = new GoodsQuantityVO();

                goodsQuantity.setGoodsId(sku.getGoodsId());

                //设置为要减掉的库存
                goodsQuantity.setQuantity(0 - sku.getNum());
                //发货要减少实际的库存
                goodsQuantity.setQuantityType(QuantityType.actual);

                goodsQuantity.setSkuId(sku.getSkuId());

                quantityVOList.add(goodsQuantity);
            }
            //扣减库存
            goodsQuantityClient.updateSkuQuantity(quantityVOList);

        }

        //付款前 订单取消
        if (orderMessage.getNewStatus().name().equals(OrderStatusEnum.CANCELLED.name()) && orderMessage.getOrderDO().getPayStatus().equals(PayStatusEnum.PAY_NO.name())) {

            List<GoodsQuantityVO> quantityVOList = new ArrayList<>();

            OrderDO order = orderMessage.getOrderDO();
            String itemsJson = order.getItemsJson();
            List<OrderSkuVO> list = JsonUtil.jsonToList(itemsJson, OrderSkuVO.class);

            for (OrderSkuVO sku : list) {

                GoodsQuantityVO goodsQuantity = new GoodsQuantityVO();
                goodsQuantity.setQuantity(sku.getNum());
                goodsQuantity.setGoodsId(sku.getGoodsId());

                //取消订单要恢复下单时占用的可用库存
                goodsQuantity.setQuantity(sku.getNum());
                goodsQuantity.setQuantityType(QuantityType.enable);
                goodsQuantity.setSkuId(sku.getSkuId());
                quantityVOList.add(goodsQuantity);

            }

            goodsQuantityClient.updateSkuQuantity(quantityVOList);

        }

    }

    @Override
    public void refund(RefundChangeMsg refundChangeMsg) {
        RefundDO refund = refundChangeMsg.getRefund();
        //获取当前订单信息
        OrderDetailDTO orderDetailDTO = orderClient.getModel(refundChangeMsg.getRefund().getOrderSn());
        //退款 当商家审核已通过且未发货 增加可用库存
        boolean bool = refund.getRefuseType().equals(RefuseTypeEnum.RETURN_MONEY.name()) && orderDetailDTO.getShipStatus().equals(ShipStatusEnum.SHIP_NO.name()) && refundChangeMsg.getRefundStatusEnum().name().equals(RefundStatusEnum.PASS.name());
        List<RefundGoodsDO> goodsList = afterSaleClient.getRefundGoods(refund.getSn());
        if (bool) {

            List<GoodsQuantityVO> quantityVOList = new ArrayList<>();

            for (RefundGoodsDO goods : goodsList) {
                // 商品入库
                GoodsQuantityVO goodsQuantity = new GoodsQuantityVO();
                goodsQuantity.setSkuId(goods.getSkuId());
                goodsQuantity.setGoodsId(goods.getGoodsId());
                goodsQuantity.setQuantity(goods.getReturnNum());
                goodsQuantity.setQuantityType(QuantityType.enable);
                quantityVOList.add(goodsQuantity);
            }

            goodsQuantityClient.updateSkuQuantity(quantityVOList);

        }

        //退货且订单入库，增加库存
        bool = refund.getRefuseType().equals(RefuseTypeEnum.RETURN_GOODS.name()) && refundChangeMsg.getRefundStatusEnum().equals(RefundStatusEnum.STOCK_IN);
        if (bool) {

            List<GoodsQuantityVO> quantityVOList = new ArrayList<>();

            for (RefundGoodsDO goods : goodsList) {
                // 商品入库
                GoodsQuantityVO goodsQuantity = new GoodsQuantityVO();
                goodsQuantity.setSkuId(goods.getSkuId());
                goodsQuantity.setGoodsId(goods.getGoodsId());
                goodsQuantity.setQuantity(goods.getReturnNum());

                //先增加实际库存
                goodsQuantity.setQuantityType(QuantityType.actual);
                quantityVOList.add(goodsQuantity);
            }

            //先增加实际库存
            goodsQuantityClient.updateSkuQuantity(quantityVOList);

            quantityVOList.forEach(goodsQuantityVO -> {
                goodsQuantityVO.setQuantityType(QuantityType.enable);
            });
            //再增加可用库存
            goodsQuantityClient.updateSkuQuantity(quantityVOList);
        }


    }
}
