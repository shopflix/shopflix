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
package cloud.shopfly.b2c.core.pagedata.service;

import java.util.List;

/**
 * 静态页面帮助页面
 * @author chopper
 * @version v1.0
 * @since v7.0
 * 2018/7/17 下午3:27
 * @Description:
 *
 */
public interface StaticsPageHelpManager {

    /**
     * 获取帮助页面总数
     * @return
     */
    Integer count();


    /**
     * 分页获取帮助
     * @param page
     * @param pageSize
     * @return
     */
    List helpList(Integer page, Integer pageSize);


}
