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

import cloud.shopfly.b2c.core.system.model.enums.ProgressEnum;
import cloud.shopfly.b2c.core.system.model.vo.ProgressVo;
import cloud.shopfly.b2c.core.system.model.vo.TaskProgress;
import cloud.shopfly.b2c.core.system.service.ProgressManager;
import cloud.shopfly.b2c.framework.exception.ResourceNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 进度控制器
 *
 * @author kingapex
 * @version v1.0
 * @since v1.0
 * 2015年5月13日 下午5:57:48
 */
@RestController
@RequestMapping("/seller/task")
@Api(description = "进度控制器")
public class ProgressSellerController {

    @Autowired
    private ProgressManager progressManager;

    @ApiOperation("检测是否有任务正在进行,有任务返回任务id,无任务返回404")
    @ApiImplicitParam(name = "task_id", value = "任务id", dataType = "String", paramType = "path", required = true, example = "page_create")
    @GetMapping(value = "/{task_id}")
    public String hasTask(@PathVariable("task_id") String taskId) {

        /** 如果redis中有此id 视为有任务进行 */
        if (progressManager.getProgress(taskId) != null) {
            return taskId;
        }

        throw new ResourceNotFoundException("进度不存在");
    }


    @ApiOperation("查看任务进度")
    @ApiImplicitParam(name = "task_id", value = "任务id", dataType = "String", paramType = "path", required = true, example = "page_create")
    @GetMapping(value = "/{task_id}/progress")
    public ProgressVo viewProgress(@PathVariable("task_id") String taskId) {

        TaskProgress taskProgress = progressManager.getProgress(taskId);
        if (taskProgress == null) {
            return new ProgressVo(100, ProgressEnum.SUCCESS.name());
        }
        /** 如果是完成或者出错 需要移除任务 */
        if (!taskProgress.getTaskStatus().equals(ProgressEnum.DOING.name())) {
            progressManager.remove(taskId);
        }
        return new ProgressVo(taskProgress);


    }

    @ApiOperation("清除某任务")
    @ApiImplicitParam(name = "task_id", value = "任务id", dataType = "String", paramType = "path", required = true, example = "page_create")
    @DeleteMapping(value = "/{task_id}")
    public String clear(@PathVariable("task_id") String taskId) {
        progressManager.remove(taskId);
        return null;
    }
}
