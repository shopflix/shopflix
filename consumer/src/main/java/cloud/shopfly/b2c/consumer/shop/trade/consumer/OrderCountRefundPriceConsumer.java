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
package cloud.shopfly.b2c.consumer.shop.trade.consumer;

import cloud.shopfly.b2c.consumer.core.event.OrderStatusChangeEvent;
import cloud.shopfly.b2c.core.base.message.OrderStatusChangeMsg;
import cloud.shopfly.b2c.core.client.trade.OrderClient;
import cloud.shopfly.b2c.core.trade.order.model.dos.OrderDO;
import cloud.shopfly.b2c.core.trade.order.model.enums.OrderStatusEnum;
import cloud.shopfly.b2c.core.trade.order.model.enums.PaymentTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 订单状态改变消费
 * 订单付款后修改订单项的可退款金额
 *
 * @author duanmingyu
 * @version v1.0
 * @Description:
 * @date 2019-05-10
 * @since v7.1
 */
@Component
public class OrderCountRefundPriceConsumer implements OrderStatusChangeEvent {

    private Logger logger = LoggerFactory.getLogger(getClass().getName());

    @Autowired
    private OrderClient orderClient;

    @Override
    public void orderChange(OrderStatusChangeMsg orderStatusChangeMsg) {
        try {

            OrderDO order = orderStatusChangeMsg.getOrderDO();
            String paymentType = order.getPaymentType();
            OrderStatusEnum orderStatus = orderStatusChangeMsg.getNewStatus();
            //在线支付&&订单已支付
            boolean online = PaymentTypeEnum.ONLINE.value().equals(paymentType) && OrderStatusEnum.PAID_OFF.equals(orderStatus);
            //货到付款&&订单已收货
            boolean cod = PaymentTypeEnum.COD.value().equals(paymentType) && OrderStatusEnum.ROG.equals(orderStatus);
            //在线支付&&订单已支付 或者  货到付款&&订单已收货
            if (online || cod) {
                this.orderClient.addOrderItemRefundPrice(orderStatusChangeMsg.getOrderDO());
            }
        } catch (Exception e) {
            logger.error("订单变更消息异常:", e);
            e.printStackTrace();
        }
    }
}
