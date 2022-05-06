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
package cloud.shopfly.b2c.framework.context;

import cloud.shopfly.b2c.framework.security.model.Buyer;
import cloud.shopfly.b2c.framework.security.model.Seller;

/**
 * 用户信息hold接口
 * @author kingapex
 * @version 1.0
 * @since 7.1.0
 * 2019-05-28
 */
public interface UserHolder {

    /**
     * 获取seller
     * @return
     */
    Seller getSeller();

    /**
     * 获取buyer
     * @return
     */
    Buyer getBuyer();


}
