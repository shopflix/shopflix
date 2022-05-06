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
package cloud.shopfly.b2c.core.trade.order.service;

import cloud.shopfly.b2c.core.trade.order.model.enums.PaymentTypeEnum;
import cloud.shopfly.b2c.core.trade.order.model.vo.ReceiptVO;
import cloud.shopfly.b2c.core.trade.order.model.vo.CheckoutParamVO;

/**
 * 结算参数 业务层接口
 *
 * @author Snow create in 2018/4/8
 * @version v2.0
 * @since v7.0.0
 */
public interface CheckoutParamManager {

    /**
     * 获取订单的创建参数<br>
     * 如果没有设置过参数，则用默认
     *
     * @return 结算参数
     */
    CheckoutParamVO getParam();


    /**
     * 设置收货地址id
     *
     * @param addressId 收货地址id
     */
    void setAddressId(Integer addressId);


    /**
     * 设置支付式
     *
     * @param paymentTypeEnum 支付方式{@link PaymentTypeEnum}
     */
    void setPaymentType(PaymentTypeEnum paymentTypeEnum);


    /**
     * 设置发票
     *
     * @param receipt 发票vo {@link  ReceiptVO }
     */
    void setReceipt(ReceiptVO receipt);

    /**
     * 取消发票
     */
    void deleteReceipt();


    /**
     * 设置送货时间
     *
     * @param receiveTime 送货时间
     */
    void setReceiveTime(String receiveTime);


    /**
     * 设置订单备注
     *
     * @param remark 订单备注
     */
    void setRemark(String remark);

    /**
     * 设置订单来源
     *
     * @param clientType 客户端来源
     */
    void setClientType(String clientType);


    /**
     * 批量设置所有参数
     *
     * @param param 结算参数 {@link CheckoutParamVO}
     */
    void setAll(CheckoutParamVO param);

    /**
     * 检测是否支持货到付款
     * @param paymentTypeEnum
     */
    void checkCod(PaymentTypeEnum paymentTypeEnum);

}
