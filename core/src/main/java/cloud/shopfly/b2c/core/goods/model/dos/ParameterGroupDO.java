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
package cloud.shopfly.b2c.core.goods.model.dos;

import cloud.shopfly.b2c.framework.database.annotation.Column;
import cloud.shopfly.b2c.framework.database.annotation.Id;
import cloud.shopfly.b2c.framework.database.annotation.PrimaryKeyField;
import cloud.shopfly.b2c.framework.database.annotation.Table;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


/**
 * 参数组实体
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018-03-20 16:14:17
 */
@Table(name = "es_parameter_group")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ParameterGroupDO implements Serializable {

    private static final long serialVersionUID = 2394621849767871L;

    /**
     * 主键
     */
    @Id(name = "group_id")
    @ApiModelProperty(hidden = true)
    private Integer groupId;
    /**
     * 参数组名称
     */
    @Column(name = "group_name")
    @ApiModelProperty(name = "group_name", value = "参数组名称", required = true)
    @NotEmpty(message = "参数组名称不能为空")
    @Length(max = 50, message = "参数组名称不能超过50字")
    private String groupName;
    /**
     * 关联分类id
     */
    @Column(name = "category_id")
    @ApiModelProperty(name = "category_id", value = "关联分类id", required = true)
    @NotNull(message = "关联的分类不能为空")
    private Integer categoryId;
    /**
     * 排序
     */
    @Column(name = "sort")
    @ApiModelProperty(hidden = true)
    private Integer sort;

    @PrimaryKeyField
    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "ParameterGroupDO [groupId=" + groupId + ", groupName=" + groupName + ", categoryId=" + categoryId
                + ", sort=" + sort + "]";
    }

}