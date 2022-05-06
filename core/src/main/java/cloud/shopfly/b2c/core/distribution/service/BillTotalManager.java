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
package cloud.shopfly.b2c.core.distribution.service;

import cloud.shopfly.b2c.core.distribution.model.dos.BillTotalDO;
import cloud.shopfly.b2c.framework.database.Page;

/**
 * 用户结算单
 *
 * @author Chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/5/22 上午9:30
 */

public interface BillTotalManager {
    /**
     * 获取结算page
     *
     * @param page     页码
     * @param pageSize 分页大小
     * @return
     */
    Page page(int page, int pageSize);

    /**
     * 新增一个总结算单
     *
     * @param billTotal
     * @return
     */
    BillTotalDO add(BillTotalDO billTotal);

    /**
     * 获取
     *
     * @param startTime
     * @return
     */
    BillTotalDO getTotalByStart(Long startTime);
}
