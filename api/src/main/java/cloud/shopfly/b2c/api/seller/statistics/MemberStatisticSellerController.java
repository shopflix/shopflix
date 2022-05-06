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

import cloud.shopfly.b2c.core.base.SearchCriteria;
import cloud.shopfly.b2c.core.statistics.model.vo.SimpleChart;
import cloud.shopfly.b2c.core.statistics.service.MemberStatisticManager;
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
 * 会员统计分析
 *
 * @author Chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/4/28 下午5:10
 */
@RestController
@Api(description = "后台-》会员分析-》")
@RequestMapping("/seller/statistics/member")
public class MemberStatisticSellerController {

    @Autowired
    private MemberStatisticManager memberAnalysisManager;

    /**
     * 后台-》会员分析-》新增会员统计
     *
     * @param searchCriteria
     * @return
     */
    @ApiOperation("新增会员统计")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cycle_type", value = "日期类型", dataType = "String", paramType = "query", required = true, allowableValues = "YEAR,MONTH"),
            @ApiImplicitParam(name = "year", value = "年份", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "month", value = "月份", dataType = "int", paramType = "query")
    })
    @GetMapping(value = "/increase/member")
    public SimpleChart adminIncreaseMember(@ApiIgnore SearchCriteria searchCriteria) {
        return this.memberAnalysisManager.getIncreaseMember(searchCriteria);
    }

    @ApiOperation("新增会员统计 page")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cycle_type", value = "日期类型", dataType = "String", paramType = "query", required = true, allowableValues = "YEAR,MONTH"),
            @ApiImplicitParam(name = "year", value = "年份", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "month", value = "月份", dataType = "int", paramType = "query")
    })
    @GetMapping(value = "/increase/member/page")
    public Page adminIncreaseMemberPage(@ApiIgnore SearchCriteria searchCriteria) {
        return this.memberAnalysisManager.getIncreaseMemberPage(searchCriteria);
    }

    @ApiOperation("会员下单量统计-》下单量")
    @GetMapping(value = "/order/quantity")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cycle_type", value = "日期类型", dataType = "String", paramType = "query", required = true, allowableValues = "YEAR,MONTH"),
            @ApiImplicitParam(name = "year", value = "年份", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "month", value = "月份", dataType = "int", paramType = "query"),
    })
    public SimpleChart adminMemberOrderQuantity(@ApiIgnore SearchCriteria searchCriteria) {
        return this.memberAnalysisManager.getMemberOrderQuantity(searchCriteria);
    }


    @ApiOperation("会员下单量统计-》下单量 page")
    @GetMapping(value = "/order/quantity/page")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cycle_type", value = "日期类型", dataType = "String", paramType = "query", required = true, allowableValues = "YEAR,MONTH"),
            @ApiImplicitParam(name = "year", value = "年份", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "month", value = "月份", dataType = "int", paramType = "query"),
    })
    public Page adminMemberOrderQuantityPage(@ApiIgnore SearchCriteria searchCriteria) {
        return this.memberAnalysisManager.getMemberOrderQuantityPage(searchCriteria);
    }


    @ApiOperation("会员下单量统计-》下单商品数")
    @GetMapping(value = "/order/goods/num")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cycle_type", value = "日期类型", dataType = "String", paramType = "query", required = true, allowableValues = "YEAR,MONTH"),
            @ApiImplicitParam(name = "year", value = "年份", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "month", value = "月份", dataType = "int", paramType = "query"),
    })
    public SimpleChart adminMemberGoodsNum(@ApiIgnore SearchCriteria searchCriteria) {
        return this.memberAnalysisManager.getMemberGoodsNum(searchCriteria);
    }


    @ApiOperation("会员下单量统计-》下单商品数page")
    @GetMapping(value = "/order/goods/num/page")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cycle_type", value = "日期类型", dataType = "String", paramType = "query", required = true, allowableValues = "YEAR,MONTH"),
            @ApiImplicitParam(name = "year", value = "年份", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "month", value = "月份", dataType = "int", paramType = "query"),
    })
    public Page adminMemberGoodsNumPage(@ApiIgnore SearchCriteria searchCriteria) {
        return this.memberAnalysisManager.getMemberGoodsNumPage(searchCriteria);
    }

    @ApiOperation("会员下单量统计-》下单金额")
    @GetMapping(value = "/order/money")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cycle_type", value = "日期类型", dataType = "String", paramType = "query", required = true, allowableValues = "YEAR,MONTH"),
            @ApiImplicitParam(name = "year", value = "年份", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "month", value = "月份", dataType = "int", paramType = "query"),
    })
    public SimpleChart adminMemberMoney(@ApiIgnore SearchCriteria searchCriteria) {
        return this.memberAnalysisManager.getMemberMoney(searchCriteria);
    }

    @ApiOperation("下单金额page")
    @GetMapping(value = "/order/money/page")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cycle_type", value = "日期类型", dataType = "String", paramType = "query", required = true, allowableValues = "YEAR,MONTH"),
            @ApiImplicitParam(name = "year", value = "年份", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "month", value = "月份", dataType = "int", paramType = "query"),
    })
    public Page adminMemberMoneyPage(@ApiIgnore SearchCriteria searchCriteria) {
        return this.memberAnalysisManager.getMemberMoneyPage(searchCriteria);
    }

}
