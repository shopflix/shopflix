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
package cloud.shopfly.b2c.api.seller.promotion;

import cloud.shopfly.b2c.core.promotion.minus.model.dos.MinusDO;
import cloud.shopfly.b2c.core.promotion.minus.model.vo.MinusVO;
import cloud.shopfly.b2c.core.promotion.minus.service.MinusManager;
import cloud.shopfly.b2c.core.promotion.tool.support.PromotionValid;
import cloud.shopfly.b2c.framework.database.Page;
import cloud.shopfly.b2c.framework.exception.NoPermissionException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

/**
 * 单品立减控制器
 * @author Snow
 * @version v7.0.0
 * @since v7.0.0
 * 2018-03-23 19:52:27
 */
@RestController
@RequestMapping("/seller/promotion/minus")
@Api(description = "单品立减相关API")
@Validated
public class MinusSellerController {

	@Autowired
	private MinusManager minusManager;


	@ApiOperation(value	= "查询单品立减列表", response = MinusDO.class)
	@ApiImplicitParams({
		 @ApiImplicitParam(name	= "page_no",	value =	"页码", dataType = "int",	paramType =	"query"),
		 @ApiImplicitParam(name	= "page_size",	value =	"每页显示数量", dataType = "int",	paramType =	"query"),
		 @ApiImplicitParam(name	= "keywords",	value =	"关键字", dataType = "String",	paramType =	"query"),
	})
	@GetMapping
	public Page<MinusVO> list(@ApiIgnore Integer pageNo, @ApiIgnore Integer pageSize, String keywords)	{
		return	this.minusManager.list(pageNo,pageSize,keywords);
	}


	@ApiOperation(value	= "添加单品立减", response = MinusVO.class)
	@ApiImplicitParam(name = "minus", value = "单品立减信息", required = true, dataType = "MinusVO", paramType = "body")
	@PostMapping
	public MinusVO add(@ApiIgnore @Valid @RequestBody MinusVO minus) {

		PromotionValid.paramValid(minus.getStartTime(),minus.getEndTime(),minus.getRangeType(),minus.getGoodsList());
		this.minusManager.add(minus);
		return minus;

	}

	@PutMapping(value = "/{id}")
	@ApiOperation(value	= "修改单品立减", response = MinusVO.class)
	@ApiImplicitParams({
		 @ApiImplicitParam(name	= "id",	value =	"主键",	required = true, dataType = "int",	paramType =	"path")
	})
	public MinusVO edit(@Valid @RequestBody MinusVO minus, @PathVariable Integer id) {

		this.minusManager.verifyAuth(id);
		PromotionValid.paramValid(minus.getStartTime(),minus.getEndTime(),minus.getRangeType(),minus.getGoodsList());
		minus.setMinusId(id);
		this.minusManager.edit(minus,id);

		return	minus;
	}


	@DeleteMapping(value = "/{id}")
	@ApiOperation(value	= "删除单品立减")
	@ApiImplicitParams({
		 @ApiImplicitParam(name	= "id",	value =	"要删除的单品立减主键",	required = true, dataType = "int",	paramType =	"path")
	})
	public	String	delete(@PathVariable Integer id) {

		this.minusManager.verifyAuth(id);
		this.minusManager.delete(id);

		return "";
	}


	@GetMapping(value =	"/{id}")
	@ApiOperation(value	= "查询一个单品立减")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id",	value = "要查询的单品立减主键",	required = true, dataType = "int",	paramType = "path")
	})
	public MinusVO get(@PathVariable Integer id)	{
		MinusVO minusVO = this.minusManager.getFromDB(id);

		//验证越权操作
		if (minusVO == null){
			throw new NoPermissionException("无权操作");
		}

		return	minusVO;
	}


}
