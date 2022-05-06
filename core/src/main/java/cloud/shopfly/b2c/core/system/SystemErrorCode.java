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
package cloud.shopfly.b2c.core.system;


/**
 * 系统设置异常
 *
 * @author zh
 * @version v1.0
 * @since v1.0
 * 2018年3月22日 上午10:40:05
 */
public enum SystemErrorCode {

    //上传方案异常编码
    E800("此上传方案已经存在"),
    E801("不允许上传的图片格式"),
    E802("上传图片失败"),
    E803("删除图片失败"),
    E804("发送邮件失败"),
    E805("地区不合法"),
    E806("楼层找不到"),
    //上传方案异常编码
    E900("此上传方案已经存在"),
    E901("不允许上传的图片格式"),
    E902("上传图片失败"),
    E903("删除图片失败"),
    E904("发送邮件失败"),
    E905("地区不合法"),
    E906("发票内容超出限制，最多为6个"),
    E907("发票内容重复"),
    //文章使用异常编码
    E950("特殊的文章分类，不可修改"),
    E951("分类添加失败"),
    E952("特殊的文章，不能删除"),
    E953("站点导航，参数不合法"),
    E954("热门关键字，参数不合法"),
    E955("文章，参数不合法"),
    E956("焦点图参数不合法，参数不合法"),
    E908("推送参数为空"),
    E909("推送失败"),
    E910("电子面单方案已经存在"),
    E911("电子面单生成失败"),
    E912("电子面单参数错误"),
    E913("菜单唯一标识重复"),
    E914("菜单级别最多为3级"),
    E915("管理员名称已经存在"),
    E916("必须保留一个超级管理员"),
    E917("管理员密码不能为空"),
    E918("管理员账号或密码错误"),
    E919("短信平台方案已经存在"),
    E920("必须保留一个开启状态的短信平台"),
    E921("原密码错误"),
    E922("原始密码不能为空"),
    E923("新密码不能为空"),
    E924("角色不能删除"),
    E925("菜单名称重复"),
    E926("演示站点禁止此操作"),

    E214("物流公司有误"),
    E226("运费模版被使用")
    ;

    private String describe;

    SystemErrorCode(String des) {
        this.describe = des;
    }

    /**
     * 获取异常码
     *
     * @return
     */
    public String code() {
        return this.name().replaceAll("E", "");
    }


}
