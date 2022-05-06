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
package cloud.shopfly.b2c.core.pagedata.model.vo;

import cloud.shopfly.b2c.framework.database.annotation.Column;
import cloud.shopfly.b2c.framework.database.annotation.Id;
import cloud.shopfly.b2c.framework.database.annotation.Table;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;


/**
 * 文章分类实体
 *
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018-06-11 15:01:32
 */
@Table(name = "es_article_category")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ArticleCategoryVO implements Serializable {

    private static final long serialVersionUID = 5257682283507488L;

    /**
     * 主键
     */
    @Id(name = "id")
    @ApiModelProperty(hidden = true)
    private Integer id;

    /**
     * 父id
     */
    @ApiModelProperty(name = "parent_id", value = "父分类id", required = false)
    private Integer parentId;
    /**
     * 分类名称
     */
    @Column(name = "name")
    @ApiModelProperty(name = "name", value = "分类名称", required = false)
    private String name;

    @ApiModelProperty(name = "children", value = "子分类", required = false)
    private List<ArticleCategoryVO> children;

    private List<ArticleVO> articles;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ArticleCategoryVO> getChildren() {
        return children;
    }

    public void setChildren(List<ArticleCategoryVO> children) {
        this.children = children;
    }

    public List<ArticleVO> getArticles() {
        return articles;
    }

    public void setArticles(List<ArticleVO> articles) {
        this.articles = articles;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    @Override
    public String toString() {
        return "ArticleCategoryVO{" +
                "id=" + id +
                ", parentId=" + parentId +
                ", name='" + name + '\'' +
                ", children=" + children +
                ", articles=" + articles +
                '}';
    }
}