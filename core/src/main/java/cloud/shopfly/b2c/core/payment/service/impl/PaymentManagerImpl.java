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
package cloud.shopfly.b2c.core.payment.service.impl;

import cloud.shopfly.b2c.core.payment.PaymentErrorCode;
import cloud.shopfly.b2c.core.payment.model.dos.PaymentBillDO;
import cloud.shopfly.b2c.core.payment.model.dos.PaymentMethodDO;
import cloud.shopfly.b2c.core.payment.model.dto.PayParam;
import cloud.shopfly.b2c.core.payment.model.enums.ClientType;
import cloud.shopfly.b2c.core.payment.model.enums.TradeType;
import cloud.shopfly.b2c.core.payment.model.vo.PayBill;
import cloud.shopfly.b2c.core.payment.service.PaymentBillManager;
import cloud.shopfly.b2c.core.payment.service.PaymentManager;
import cloud.shopfly.b2c.core.payment.service.PaymentMethodManager;
import cloud.shopfly.b2c.core.payment.service.PaymentPluginManager;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.logs.Debugger;
import cloud.shopfly.b2c.framework.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


/**
 * Payment bill management implementation
 *
 * @author kingapex
 * @version 1.0
 * @since 7.1.0
 * 2019-04-10
 */
@Service
public class PaymentManagerImpl implements PaymentManager {

    @Autowired
    private PaymentMethodManager paymentMethodManager;

    @Autowired
    private PaymentBillManager paymentBillManager;

    @Autowired
    private List<PaymentPluginManager> paymentPluginList;

    @Autowired
    private Debugger debugger;

    @Override
    public Map pay(PayBill bill) {

        debugger.log("Prepare for the followingbillTo pay");
        debugger.log(bill.toString());

        // Check payment method
        PaymentMethodDO paymentMethod = this.paymentMethodManager.getByPluginId(bill.getPluginId());
        if (paymentMethod == null) {
            debugger.log("No corresponding payment method was found[" + bill.getPluginId() + "]");
            throw new ServiceException(PaymentErrorCode.E501.code(), "No corresponding payment method was found[" + bill.getPluginId() + "]");
        }

        // Use the system time plus 4 random digits to generate a flow number
        String billSn = System.currentTimeMillis() + "" + StringUtil.getRandStr(4);

        debugger.log("Generate process numbers for bills：" + billSn);

        // Generate payment flow
        PaymentBillDO paymentBill = new PaymentBillDO(bill.getSn(), billSn, null, 0, bill.getTradeType().name(), paymentMethod.getMethodName(), bill.getOrderPrice(), paymentMethod.getPluginId());
        // Save payment parameters

        switch (bill.getClientType()) {
            case PC:
                paymentBill.setPayConfig(paymentMethod.getPcConfig());
                break;
            case WAP:
                paymentBill.setPayConfig(paymentMethod.getWapConfig());
                break;
            case NATIVE:
                paymentBill.setPayConfig(paymentMethod.getAppNativeConfig());
                break;
            case REACT:
                paymentBill.setPayConfig(paymentMethod.getAppReactConfig());
                break;
            case MINI:
                paymentBill.setPayConfig(paymentMethod.getMiniConfig());
                break;
            default:
                break;
        }

        // Store the payment slip
        this.paymentBillManager.add(paymentBill);

        debugger.log("The bill was stored successfully");

        bill.setBillSn(billSn);

        // Call up the appropriate payment plug-in
        PaymentPluginManager paymentPlugin = this.findPlugin(bill.getPluginId());

        debugger.log("Start calling the payment plug-in：" + bill.getPluginId());
        return paymentPlugin.pay(bill);
    }


    @Override
    public void payReturn(TradeType tradeType, String paymentPluginId) {
        PaymentPluginManager plugin = this.findPlugin(paymentPluginId);
        if (plugin != null) {
            plugin.onReturn(tradeType);
        }
    }

    @Override
    public String payCallback(TradeType tradeType, String paymentPluginId, ClientType clientType) {
        PaymentPluginManager plugin = this.findPlugin(paymentPluginId);
        if (plugin != null) {
            return plugin.onCallback(tradeType, clientType);
        }
        return "fail";
    }

    @Override
    public String queryResult(PayParam param) {

        PaymentPluginManager plugin = this.findPlugin(param.getPaymentPluginId());

        PaymentBillDO paymentBill = this.paymentBillManager.getBillBySnAndTradeType(param.getSn(), param.getTradeType());
        // If a callback has been paid, no query is required
        if (paymentBill.getIsPay() == 1) {
            return "success";
        }

        PayBill bill = new PayBill();
        bill.setBillSn(paymentBill.getOutTradeNo());
        bill.setClientType(ClientType.valueOf(param.getClientType()));
        bill.setTradeType(TradeType.valueOf(param.getTradeType()));
        bill.setSn(param.getSn());
        bill.setOrderPrice(paymentBill.getTradePrice());

        return plugin.onQuery(bill);
    }


    /**
     * Find payment plug-ins
     *
     * @param pluginId
     * @return
     */
    private PaymentPluginManager findPlugin(String pluginId) {
        for (PaymentPluginManager plugin : paymentPluginList) {
            if (plugin.getPluginId().equals(pluginId)) {
                return plugin;
            }
        }
        return null;
    }


}
