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

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;


/**
 * 规格项实体
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018-03-20 09:31:27
 */
@Table(name = "es_specification")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SpecificationDO implements Serializable {

    private static final long serialVersionUID = 5111769180376075L;

    /**
     * 主键
     */
    @Id(name = "spec_id")
    @ApiModelProperty(hidden = true)
    private Integer specId;
    /**
     * 规格项名称
     */
    @Column(name = "spec_name")
    @NotEmpty(message = "规格项名称不能为空")
    @ApiModelProperty(name = "spec_name", value = "规格项名称", required = true)
    private String specName;
    /**
     * 是否被删除0 删除   1  没有删除
     */
    @Column(name = "disabled")
    @ApiModelProperty(hidden = true)
    private Integer disabled;
    /**
     * 规格描述
     */
    @Column(name = "spec_memo")
    @ApiModelProperty(name = "spec_memo", value = "规格描述", required = false)
    private String specMemo;

    public SpecificationDO() {
    }


    public SpecificationDO(String specName, Integer disabled, String specMemo) {
        super();
        this.specName = specName;
        this.disabled = disabled;
        this.specMemo = specMemo;
    }

    @PrimaryKeyField
    public Integer getSpecId() {
        return specId;
    }

    public void setSpecId(Integer specId) {
        this.specId = specId;
    }

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
    }

    public Integer getDisabled() {
        return disabled;
    }

    public void setDisabled(Integer disabled) {
        this.disabled = disabled;
    }

    public String getSpecMemo() {
        return specMemo;
    }

    public void setSpecMemo(String specMemo) {
        this.specMemo = specMemo;
    }

    @Override
    public String toString() {
        return "SpecificationDO [specId=" + specId + ", specName=" + specName + ", disabled=" + disabled + ", specMemo="
                + specMemo +  "]";
    }

}