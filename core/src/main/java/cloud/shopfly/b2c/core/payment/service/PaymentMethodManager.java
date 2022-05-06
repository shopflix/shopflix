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
package cloud.shopfly.b2c.core.payment.service;

import cloud.shopfly.b2c.core.payment.model.dos.PaymentMethodDO;
import cloud.shopfly.b2c.core.payment.model.vo.PaymentMethodVO;
import cloud.shopfly.b2c.core.payment.model.vo.PaymentPluginVO;
import cloud.shopfly.b2c.framework.database.Page;

import java.util.List;

/**
 * 支付方式表业务层
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018-04-11 16:06:57
 */
public interface PaymentMethodManager {

    /**
     * 查询支付方式表列表
     *
     * @param page     页码
     * @param pageSize 每页数量
     * @return Page
     */
    Page list(int page, int pageSize);

    /**
     * 添加支付方式表
     *
     * @param paymentMethod   支付方式表
     * @param paymentPluginId 插件id
     * @return PaymentMethod 支付方式表
     */
    PaymentMethodDO add(PaymentPluginVO paymentMethod, String paymentPluginId);

    /**
     * 修改支付方式表
     *
     * @param paymentMethod 支付方式表
     * @param id            支付方式表主键
     * @return PaymentMethod 支付方式表
     */
    PaymentMethodDO edit(PaymentMethodDO paymentMethod, Integer id);

    /**
     * 删除支付方式表
     *
     * @param id 支付方式表主键
     */
    void delete(Integer id);

    /**
     * 根据支付插件id获取支付方式详细
     *
     * @param pluginId
     * @return
     */
    PaymentMethodDO getByPluginId(String pluginId);

    /**
     * 查询某客户端支持的支付方式
     *
     * @param clientType
     * @return
     */
    List<PaymentMethodVO> queryMethodByClient(String clientType);

    /**
     * 根据插件id获取VO对象
     *
     * @param pluginId
     * @return
     */
    PaymentPluginVO getByPlugin(String pluginId);

}