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
package cloud.shopfly.b2c.core.distribution.model.enums;

import cloud.shopfly.b2c.framework.util.StringUtil;

/**
 * 提现审核状态枚举
 *
 * @author Chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/5/25 上午11:37
 */

public enum WithdrawStatusEnum {
    //提现状态
    APPLY("申请中"), VIA_AUDITING("审核成功"), FAIL_AUDITING("审核失败"),
    TRANSFER_ACCOUNTS("已转账");

    private String name;

    WithdrawStatusEnum(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public static String codeToName(String code) {
        if (StringUtil.isEmpty(code)) {
            return WithdrawStatusEnum.APPLY.getName();
        }
        if (code.equals(WithdrawStatusEnum.APPLY.name())) {
            return WithdrawStatusEnum.APPLY.getName();
        } else if (code.equals(WithdrawStatusEnum.VIA_AUDITING.name())) {
            return WithdrawStatusEnum.VIA_AUDITING.getName();
        } else if (code.equals(WithdrawStatusEnum.FAIL_AUDITING.name())) {
            return WithdrawStatusEnum.FAIL_AUDITING.getName();
        } else {
            return WithdrawStatusEnum.TRANSFER_ACCOUNTS.getName();
        }
    }

}
