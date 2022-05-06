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
package cloud.shopfly.b2c.core.client.system.impl;

import cloud.shopfly.b2c.core.client.system.SmsClient;
import cloud.shopfly.b2c.core.base.SceneType;
import cloud.shopfly.b2c.core.base.model.vo.SmsSendVO;
import cloud.shopfly.b2c.core.base.service.SmsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * 短信实现
 *
 * @author zh
 * @version v7.0
 * @date 18/7/31 上午11:13
 * @since v7.0
 */
@Service
@ConditionalOnProperty(value="shopfly.product", havingValue="stand")
public class SmsClientDefaultImpl implements SmsClient {

    @Autowired
    private SmsManager smsManager;


    @Override
    public boolean valid(String scene, String mobile, String code) {
        return smsManager.valid(scene, mobile, code);
    }

    @Override
    public void sendSmsMessage(String byName, String mobile, SceneType sceneType) {
        this.smsManager.sendSmsMessage(byName, mobile, sceneType);
    }

    @Override
    public void send(SmsSendVO smsSendVO) {
        smsManager.send(smsSendVO);
    }
}
