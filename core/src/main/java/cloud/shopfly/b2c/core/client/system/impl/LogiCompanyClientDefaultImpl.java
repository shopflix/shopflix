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

import cloud.shopfly.b2c.core.client.system.LogiCompanyClient;
import cloud.shopfly.b2c.core.system.model.dos.LogiCompanyDO;
import cloud.shopfly.b2c.core.system.service.LogiCompanyManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version v7.0
 * @Description:
 * @Author: zjp
 * @Date: 2018/7/26 14:18
 */
@Service
@ConditionalOnProperty(value = "shopfly.product", havingValue = "stand")
public class LogiCompanyClientDefaultImpl implements LogiCompanyClient {

    @Autowired
    private LogiCompanyManager logiCompanyManager;

    @Override
    public LogiCompanyDO getLogiByCode(String code) {
        return logiCompanyManager.getLogiByCode(code);
    }

    @Override
    public LogiCompanyDO getModel(Integer id) {
        return logiCompanyManager.getModel(id);
    }

    @Override
    public List<LogiCompanyDO> list() {
        return logiCompanyManager.list();
    }
}
