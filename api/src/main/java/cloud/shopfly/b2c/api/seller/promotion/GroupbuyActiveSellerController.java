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

import cloud.shopfly.b2c.core.promotion.groupbuy.model.dos.GroupbuyActiveDO;
import cloud.shopfly.b2c.core.promotion.groupbuy.model.vo.GroupbuyActiveVO;
import cloud.shopfly.b2c.core.promotion.groupbuy.service.GroupbuyActiveManager;
import cloud.shopfly.b2c.framework.database.Page;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.exception.SystemErrorCodeV1;
import cloud.shopfly.b2c.framework.util.DateUtil;
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
 * 团购活动表控制器
 * @author Snow
 * @version v7.0.0
 * @since v7.0.0
 * 2018-04-02 15:46:51
 */
@RestController
@RequestMapping("/seller/promotion/group-buy-actives")
@Api(description = "团购活动表相关API")
@Validated
public class GroupbuyActiveSellerController {

	@Autowired
	private GroupbuyActiveManager groupbuyActiveManager;

	@ApiOperation(value	= "查询团购活动表列表")
	@ApiImplicitParams({
		 	@ApiImplicitParam(name	= "page_no",	value =	"页码",	dataType = "int",	paramType =	"query"),
		 	@ApiImplicitParam(name	= "page_size",	value =	"每页显示数量", dataType = "int",	paramType =	"query")
	})
	@GetMapping
	public Page<GroupbuyActiveVO> list(@ApiIgnore  Integer pageNo, @ApiIgnore Integer pageSize)	{
		Page<GroupbuyActiveVO> page = this.groupbuyActiveManager.list(pageNo,pageSize);
		return page;

	}


	@ApiOperation(value	= "添加团购活动表", response = GroupbuyActiveDO.class)
	@ApiImplicitParam(name = "activeDO", value = "团购信息", required = true, dataType = "GroupbuyActiveDO", paramType = "body")
	@PostMapping
	public GroupbuyActiveDO add(@ApiIgnore @Valid  @RequestBody GroupbuyActiveDO activeDO)	{

		this.verifyParam(activeDO.getStartTime(),activeDO.getEndTime(),activeDO.getJoinEndTime());
		this.groupbuyActiveManager.add(activeDO);
		return	activeDO;
	}


	@ApiOperation(value	= "修改团购活动表", response = GroupbuyActiveDO.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name	= "id",	value =	"主键",	required = true, dataType = "int",	paramType =	"path")
	})
	@PutMapping(value = "/{id}")
	public GroupbuyActiveDO edit(@Valid @RequestBody GroupbuyActiveDO activeDO, @PathVariable Integer id) {
		this.verifyParam(activeDO.getStartTime(),activeDO.getEndTime(),activeDO.getJoinEndTime());
		this.groupbuyActiveManager.edit(activeDO,id);
		return	activeDO;
	}


	@DeleteMapping(value = "/{id}")
	@ApiOperation(value	= "删除团购活动表")
	@ApiImplicitParams({
			@ApiImplicitParam(name	= "id",	value =	"要删除的团购活动表主键",	required = true, dataType = "int",	paramType =	"path")
	})
	public	String	delete(@PathVariable Integer id) {
		this.groupbuyActiveManager.delete(id);
		return "";
	}


	@GetMapping(value =	"/{id}")
	@ApiOperation(value	= "查询一个团购活动表")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id",	value = "要查询的团购活动表主键",	required = true, dataType = "int",	paramType = "path")
	})
	public GroupbuyActiveDO get(@PathVariable	Integer	id)	{

		GroupbuyActiveDO groupbuyActive = this.groupbuyActiveManager.getModel(id);

		return	groupbuyActive;
	}

	/**
	 * 验证参数
	 * @param startTime	活动开始时间
	 * @param endTime	活动结束时间
	 * @param joinEndTime	报名截止时间
	 */
	private void verifyParam(long startTime,long endTime,long joinEndTime){

		long nowTime  = DateUtil.getDateline();

		//如果活动起始时间小于现在时间
		if(joinEndTime < nowTime){
			throw new ServiceException(SystemErrorCodeV1.INVALID_REQUEST_PARAMETER,"报名截止时间必须大于当前时间");
		}

		//如果活动开始时间小于 报名截止时间
		if (startTime < joinEndTime) {
			throw new ServiceException(SystemErrorCodeV1.INVALID_REQUEST_PARAMETER, "活动开始时间必须大于报名截止时间");
		}

		// 开始时间不能大于结束时间
		if (startTime > endTime ) {
			throw new ServiceException(SystemErrorCodeV1.INVALID_REQUEST_PARAMETER,"活动起始时间不能大于活动结束时间");
		}

	}

}
