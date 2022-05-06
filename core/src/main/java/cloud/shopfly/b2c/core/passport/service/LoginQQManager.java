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
package cloud.shopfly.b2c.core.passport.service;



import cloud.shopfly.b2c.core.member.model.dto.QQUserDTO;

import java.util.Map;

/**
 * QQ统一登陆服务
 * @author cs
 * @since v1.0
 * @version 7.2.2
 * 2020/09/24
 */
public interface LoginQQManager {





    /**
     * 获取unionid
     * @param accessToken QQh5授权返回的code
     * @return
     */
    Map qqWapLogin(String accessToken, String uuid);

    /**
     * QQ app登陆
     * @param qqUserDTO
     * @return
     */
    Map qqAppLogin(String uuid, QQUserDTO qqUserDTO);

    /**
     * 获取wap端appid
     * @return
     */
    String getAppid();

}
