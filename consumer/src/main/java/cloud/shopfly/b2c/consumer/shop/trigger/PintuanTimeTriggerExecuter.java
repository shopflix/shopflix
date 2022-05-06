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
package cloud.shopfly.b2c.consumer.shop.trigger;

import cloud.shopfly.b2c.core.base.message.PintuanChangeMsg;
import cloud.shopfly.b2c.core.base.rabbitmq.TimeExecute;
import cloud.shopfly.b2c.core.promotion.pintuan.model.Pintuan;
import cloud.shopfly.b2c.core.promotion.pintuan.service.PintuanManager;
import cloud.shopfly.b2c.core.promotion.tool.model.enums.PromotionStatusEnum;
import cloud.shopfly.b2c.framework.trigger.Interface.TimeTrigger;
import cloud.shopfly.b2c.framework.trigger.Interface.TimeTriggerExecuter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 拼团定时开启关闭活动 延时任务执行器
 *
 * @author Chopper
 * @version v1.0
 * @since v7.0
 * 2019-02-13 下午5:34
 */
@Component("pintuanTimeTriggerExecute")
public class PintuanTimeTriggerExecuter implements TimeTriggerExecuter {

    @Autowired
    private TimeTrigger timeTrigger;

    @Autowired
    private PintuanManager pintuanManager;

    protected final Log logger = LogFactory.getLog(this.getClass());

    /**
     * 执行任务
     *
     * @param object 任务参数
     */
    @Override
    public void execute(Object object) {
        PintuanChangeMsg pintuanChangeMsg = (PintuanChangeMsg) object;

        //如果是要开启活动
        if (pintuanChangeMsg.getOptionType() == 1) {
            Pintuan pintuan = pintuanManager.getModel(pintuanChangeMsg.getPintuanId());
            if (pintuan.getStatus().equals(PromotionStatusEnum.WAIT.name())) {
                pintuanManager.openPromotion(pintuanChangeMsg.getPintuanId());
                //开启活动后，立马设置一个关闭的流程
                pintuanChangeMsg.setOptionType(0);
                timeTrigger.add(TimeExecute.PINTUAN_EXECUTER, pintuanChangeMsg, pintuan.getEndTime(), "TIME_TRIGGER_" + pintuan.getPromotionId());
                if (logger.isDebugEnabled()) {
                    this.logger.debug("活动[" + pintuan.getPromotionName() + "]开始,id=[" + pintuan.getPromotionId() + "]");
                }
            }
        } else {
            //拼团活动结束
            Pintuan pintuan = pintuanManager.getModel(pintuanChangeMsg.getPintuanId());
            if (pintuan.getStatus().equals(PromotionStatusEnum.UNDERWAY.name())) {
                pintuanManager.closePromotion(pintuanChangeMsg.getPintuanId());
            }
            if (logger.isDebugEnabled()) {
                this.logger.debug("活动[" + pintuan.getPromotionName() + "]结束,id=[" + pintuan.getPromotionId() + "]");
            }
        }
    }
}
