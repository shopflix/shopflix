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
package cloud.shopfly.b2c.api.seller.statistics;

import cloud.shopfly.b2c.core.statistics.model.vo.SimpleChart;
import cloud.shopfly.b2c.core.statistics.service.CollectFrontStatisticsManager;
import cloud.shopfly.b2c.framework.database.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 商家统计 商品收藏前50
 *
 * @author mengyuanming
 * @version 2.0
 * @since 7.0
 * 2018年4月18日下午5:02:50
 */
@RestController
@RequestMapping("/seller/statistics/collect")
@Api(description = "商家统计  商品收藏前50")
public class CollectStatisticsSellerController {

    @Autowired
    private CollectFrontStatisticsManager collectFrontStatisticsManager;

    @ApiOperation(value = "收藏统计", response = SimpleChart.class)
    @GetMapping(value = "/chart")
    public SimpleChart getChart() {
        return this.collectFrontStatisticsManager.getChart();
    }

    @ApiOperation(value = "收藏列表数据", response = Page.class)
    @GetMapping(value = "/page")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page_no", value = "当前页码，默认为1", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "page_size", value = "页内数据量，默认为10", dataType = "int", paramType = "query")})
    public Page getPage(@ApiIgnore Integer pageNo, @ApiIgnore Integer pageSize) {
        return this.collectFrontStatisticsManager.getPage(pageNo, pageSize);
    }

}
