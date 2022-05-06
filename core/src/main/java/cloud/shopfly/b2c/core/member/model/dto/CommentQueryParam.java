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
package cloud.shopfly.b2c.core.member.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
 * 评论查询条件
 *
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018-05-03 10:38:00
 */
@ApiModel
public class CommentQueryParam {

    @ApiModelProperty(value = "评论内容", name = "content")
    private String content;

    @ApiModelProperty(value = "商品id", name = "goods_id")
    private Integer goodsId;

    @ApiModelProperty(value = "商品名称", name = "goods_name")
    private String goodsName;

    @ApiModelProperty(value = "好中差评", name = "grade")
    private String grade;

    @ApiModelProperty(value = "是否有图", name = "have_image")
    private Boolean haveImage;

    @ApiModelProperty(value = "模糊查询的关键字", name = "keyword")
    private String keyword;

    @ApiModelProperty(value = "会员id", name = "member_id")
    private Integer memberId;

    @ApiModelProperty(value = "会员名称", name = "member_name")
    private String memberName;

    @ApiModelProperty(value = "页码", name = "page_no")
    private Integer pageNo;

    @ApiModelProperty(value = "分页数", name = "page_size")
    private Integer pageSize;

    @ApiModelProperty(value = "回复状态", name = "reply_status")
    private Integer replyStatus;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Boolean getHaveImage() {
        return haveImage;
    }

    public void setHaveImage(Boolean haveImage) {
        this.haveImage = haveImage;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getReplyStatus() {
        return replyStatus;
    }

    public void setReplyStatus(Integer replyStatus) {
        this.replyStatus = replyStatus;
    }

}
