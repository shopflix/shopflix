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
package cloud.shopfly.b2c.api.seller.system;

import cloud.shopfly.b2c.core.system.model.dos.LogiCompanyDO;
import cloud.shopfly.b2c.core.system.service.LogiCompanyManager;
import cloud.shopfly.b2c.framework.database.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 物流公司控制器
 *
 * @author zjp
 * @version v7.0.0
 * @since v7.0.0
 * 2018-03-29 15:10:38
 */
@RestController
@RequestMapping("/seller/systems/logi-companies")
@Api(description = "物流公司相关API")
@Validated
public class LogiCompanySellerController {

    @Autowired
    private LogiCompanyManager logiManager;


    @ApiOperation(value = "查询物流公司列表", response = LogiCompanyDO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page_no", value = "页码", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "page_size", value = "每页显示数量", required = true, dataType = "int", paramType = "query")
    })
    @GetMapping
    public Page list(@ApiIgnore @NotNull(message = "页码不能为空") Integer pageNo, @ApiIgnore @NotNull(message = "每页数量不能为空") Integer pageSize) {

        return this.logiManager.list(pageNo, pageSize);
    }


    @ApiOperation(value = "添加物流公司", response = LogiCompanyDO.class)
    @PostMapping
    public LogiCompanyDO add(@Valid LogiCompanyDO logi) {

        this.logiManager.add(logi);

        return logi;
    }

    @PutMapping(value = "/{id}")
    @ApiOperation(value = "修改物流公司", response = LogiCompanyDO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", required = true, dataType = "int", paramType = "path")
    })
    public LogiCompanyDO edit(@Valid LogiCompanyDO logi, @PathVariable("id") Integer id) {

        this.logiManager.edit(logi, id);

        return logi;
    }


    @DeleteMapping
    @ApiOperation(value = "删除物流公司")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "要删除的物流公司主键", required = true, dataType = "int", paramType = "query", allowMultiple = true)
    })
    public String delete(Integer[] id) {
        this.logiManager.delete(id);

        return null;
    }


    @GetMapping(value = "/{id}")
    @ApiOperation(value = "查询一个物流公司")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "要查询的物流公司主键", required = true, dataType = "int", paramType = "path")
    })
    public LogiCompanyDO get(@PathVariable("id") Integer id) {

        LogiCompanyDO logi = this.logiManager.getModel(id);

        return logi;
    }

}
