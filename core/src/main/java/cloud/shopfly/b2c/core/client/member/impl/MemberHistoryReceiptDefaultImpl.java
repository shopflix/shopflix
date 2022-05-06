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
package cloud.shopfly.b2c.core.client.member.impl;

import cloud.shopfly.b2c.core.client.member.MemberHistoryReceiptClient;
import cloud.shopfly.b2c.core.member.model.dos.ReceiptHistory;
import cloud.shopfly.b2c.core.member.service.ReceiptHistoryManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * 会员历史默认发票实现
 *
 * @author zh
 * @version v7.0
 * @date 18/7/27 下午2:57
 * @since v7.0
 */

@Service
@ConditionalOnProperty(value = "shopfly.product", havingValue = "stand")
public class MemberHistoryReceiptDefaultImpl implements MemberHistoryReceiptClient {

    @Autowired
    private ReceiptHistoryManager receiptHistoryManager;

    @Override
    public ReceiptHistory getReceiptHistory(String orderSn) {
        return receiptHistoryManager.getReceiptHistory(orderSn);
    }

    @Override
    public ReceiptHistory add(ReceiptHistory receiptHistory) {
        return receiptHistoryManager.add(receiptHistory);
    }
}
