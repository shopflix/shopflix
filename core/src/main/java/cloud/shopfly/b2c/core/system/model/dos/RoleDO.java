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
package cloud.shopfly.b2c.core.system.model.dos;

import cloud.shopfly.b2c.framework.database.annotation.Column;
import cloud.shopfly.b2c.framework.database.annotation.Id;
import cloud.shopfly.b2c.framework.database.annotation.PrimaryKeyField;
import cloud.shopfly.b2c.framework.database.annotation.Table;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;


/**
 * 角色表实体
 *
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-06-26 20:19:36
 */
@Table(name = "es_role")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class RoleDO implements Serializable {

    private static final long serialVersionUID = 7874065238889473L;

    /**
     * 主键ID
     */
    @Id(name = "role_id")
    @ApiModelProperty(hidden = true)
    private Integer roleId;
    /**
     * 角色名称
     */
    @Column(name = "role_name")
    @NotEmpty(message = "角色名称不能为空")
    @ApiModelProperty(name = "role_name", value = "角色名称", required = true)
    private String roleName;
    /**
     * 角色介绍
     */
    @Column(name = "auth_ids")
    @ApiModelProperty(name = "auth_ids", value = "角色介绍", required = false)
    private String authIds;
    /**
     * 角色描述
     */
    @Column(name = "role_describe")
    @ApiModelProperty(name = "role_describe", value = "角色描述", required = false)
    private String roleDescribe;

    @PrimaryKeyField
    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getAuthIds() {
        return authIds;
    }

    public void setAuthIds(String authIds) {
        this.authIds = authIds;
    }

    public String getRoleDescribe() {
        return roleDescribe;
    }

    public void setRoleDescribe(String roleDescribe) {
        this.roleDescribe = roleDescribe;
    }


    @Override
    public String toString() {
        return "Role{" +
                "roleId=" + roleId +
                ", roleName='" + roleName + '\'' +
                ", authIds='" + authIds + '\'' +
                ", roleDescribe='" + roleDescribe + '\'' +
                '}';
    }


}