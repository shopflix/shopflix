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
package cloud.shopfly.b2c.core.trade.cart.service.cartbuilder.impl;

import cloud.shopfly.b2c.core.trade.cart.model.enums.CartType;
import cloud.shopfly.b2c.core.trade.cart.model.enums.PromotionTarget;
import cloud.shopfly.b2c.core.trade.cart.model.vo.CartSkuVO;
import cloud.shopfly.b2c.core.trade.cart.model.vo.CartVO;
import cloud.shopfly.b2c.core.trade.cart.model.vo.PriceDetailVO;
import cloud.shopfly.b2c.core.trade.cart.model.vo.PromotionRule;
import cloud.shopfly.b2c.core.trade.cart.service.cartbuilder.CartPriceCalculator;

import cloud.shopfly.b2c.framework.util.CurrencyUtil;
import cloud.shopfly.b2c.framework.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by kingapex on 2018/12/10.
 *
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/12/10
 */
@Service(value = "cartPriceCalculator")
public class CartPriceCalculatorImpl implements CartPriceCalculator {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass().getName());


    @Override
    public PriceDetailVO countPrice(List<CartVO> cartList) {

        //根据规则计算价格
        PriceDetailVO priceDetailVO = this.countPriceWithRule(cartList);


        return priceDetailVO;
    }


    private PriceDetailVO countPriceWithRule(List<CartVO> cartList) {

        PriceDetailVO price = new PriceDetailVO();

        for (CartVO cart : cartList) {


            boolean freeShipping = false;
            PriceDetailVO cartPrice = new PriceDetailVO();
            cartPrice.setFreightPrice(cart.getPrice().getFreightPrice());
            for (CartSkuVO cartSku : cart.getSkuList()) {

                ////如果是结算页，则忽略未选中的   update by liuyulei 2019-05-07
                if (CartType.CHECKOUT.equals(cart.getCartType()) && cartSku.getChecked() == 0) {
                    continue;
                }
                PromotionRule skuRule = cartSku.getRule();

                if (skuRule != null) {

                    this.applyRule(cartSku, skuRule, cart);

                    //有一个商品免运费则全部免运费
                    if (!freeShipping) {
                        freeShipping = skuRule.getFreeShipping();
                    }

                } else {
                    skuRule = new PromotionRule(PromotionTarget.SKU);
                }
                //未选中的不计入合计中   update by liuyulei 2019-05-07
                if (cartSku.getChecked() == 0) {
                    continue;
                }
                //购物车全部商品的原价合
                cartPrice.setOriginalPrice(CurrencyUtil.add(cartPrice.getOriginalPrice(), CurrencyUtil.mul(cartSku.getOriginalPrice(), cartSku.getNum())));

                //购物车所有小计合
                cartPrice.setGoodsPrice(CurrencyUtil.add(cartPrice.getGoodsPrice(), cartSku.getSubtotal()));

                //购物车返现合
                cartPrice.setCashBack(CurrencyUtil.add(cartPrice.getCashBack(), skuRule.getReducedTotalPrice()));

                //购物车使用积分
                cartPrice.setExchangePoint(cartPrice.getExchangePoint() + skuRule.getUsePoint());

                //累计商品重量
                double weight = CurrencyUtil.mul(cartSku.getGoodsWeight(), cartSku.getNum());
                double cartWeight = CurrencyUtil.add(cart.getWeight(), weight);
                cart.setWeight(cartWeight);


            }


            //应用购物车级别的促销规则
            List<PromotionRule> cartRuleList = cart.getRuleList();

            boolean cartFreeShipping = false;
            for (PromotionRule rule : cartRuleList) {
                //应用购物车的促销规则
                this.applyCartRule(rule, cartPrice, cart);
                if (rule != null) {
                    cartFreeShipping = rule.getFreeShipping();
                }
            }


            //单品规则中有免运费或满减里有免运费
            if (freeShipping || cartFreeShipping) {
                cartPrice.setIsFreeFreight(1);
                cartPrice.setFreightPrice(0D);
            }


            //计算店铺商品总优惠金额
            double totalDiscount = CurrencyUtil.add(cartPrice.getCashBack(), cartPrice.getCouponPrice());
            cartPrice.setDiscountPrice(totalDiscount);

            //总价为商品价加运费
            double totalPrice = CurrencyUtil.add(cartPrice.getGoodsPrice(), cartPrice.getFreightPrice());
            cartPrice.setTotalPrice(totalPrice);
            cart.setPrice(cartPrice);

            price = price.plus(cartPrice);


        }


        if(logger.isDebugEnabled()){
            logger.debug("计算完优惠后购物车数据为：");
            logger.debug(cartList.toString());
            logger.debug("价格为：");
            logger.debug(price.toString());
        }

        return price;
    }

    /**
     * 为购物车应用一个购物车促销规则
     *
     * @param cartRule  购物车规则
     * @param cartPrice 购物车价格
     * @param cart      购物车
     */
    private void applyCartRule(PromotionRule cartRule, PriceDetailVO cartPrice, CartVO cart) {

        if (cartRule == null) {
            return;
        }

        //减掉要优惠的总金额
        if (cartRule.getReducedTotalPrice() != null) {

            double subtotal = cartPrice.getGoodsPrice();
            //减
            subtotal = CurrencyUtil.sub(subtotal, cartRule.getReducedTotalPrice());

            //总之不能为负数
            if (subtotal < 0) {
                subtotal = 0;
            }
            //设置
            cartPrice.setFullMinus(cartRule.getReducedTotalPrice());
            cartPrice.setGoodsPrice(subtotal);
            //购物车返现合
            cartPrice.setCashBack(CurrencyUtil.add(cartPrice.getCashBack(), cartRule.getReducedTotalPrice()));
        }

        //优惠券不计入,合计的时候会算
        if (cartRule.getUseCoupon() != null) {

            double subtotal = cartPrice.getGoodsPrice();
            //减
            subtotal = CurrencyUtil.sub(subtotal, cartRule.getUseCoupon().getAmount());

            //总之不能为负数
            if (subtotal < 0) {
                subtotal = 0;
            }
            cartPrice.setGoodsPrice(subtotal);
            cartPrice.setCouponPrice(cartRule.getUseCoupon().getAmount());

        }
        //赠送的优惠券
        if (cartRule.getCouponGift() != null) {
            cart.getGiftCouponList().add(cartRule.getCouponGift());
        }

        //赠送的赠品
        if (cartRule.getGoodsGift() != null) {
            cart.getGiftList().add(cartRule.getGoodsGift());
        }

        //赠送的积分
        if (cartRule.getPointGift() != null) {
            cart.setGiftPoint(cartRule.getPointGift());
        }


    }


    /**
     * 为购物车应用一个单品促销规则
     *
     * @param cartSku 购物车商品sku
     * @param skuRule 促销规则
     * @param cart    购物车
     */
    private void applyRule(CartSkuVO cartSku, PromotionRule skuRule, CartVO cart) {

        if (!StringUtil.isEmpty(skuRule.getInvalidReason())) {
            cartSku.setChecked(0);
            cartSku.setErrorMessage(skuRule.getInvalidReason());
        }

        //如果已经失效，标记为不选中和失效原因，且不参与计算价格
        if (skuRule.isInvalid()) {
            cartSku.setChecked(0);
            cartSku.setInvalid(1);
            return;
        }

        //设置促销tag
        if (!StringUtil.isEmpty(skuRule.getTag())) {
            cartSku.getPromotionTags().add(skuRule.getTag());
        }

        //减掉要优惠的总金额
        if (skuRule.getReducedTotalPrice() != null) {
            double subtotal = cartSku.getSubtotal();
            subtotal = CurrencyUtil.sub(subtotal, skuRule.getReducedTotalPrice());

            //总之不能为负数
            if (subtotal < 0) {
                subtotal = 0;
            }

            cartSku.setSubtotal(subtotal);
        }


        //单价
        if (skuRule.getReducedPrice() != null) {
            double originalPrice = cartSku.getOriginalPrice();
            double purchasePrice = CurrencyUtil.sub(originalPrice, skuRule.getReducedPrice());

            //总之不能为负数
            if (purchasePrice < 0) {
                purchasePrice = 0;
            }

            cartSku.setPurchasePrice(purchasePrice);
        }


        if (skuRule.getUsePoint() != null) {
            //要使用的积分
            double point = CurrencyUtil.div(skuRule.getUsePoint(), cartSku.getNum());

            cartSku.setPoint(new Double(point).intValue());
        }


        //赠送的优惠券
        if (skuRule.getCouponGift() != null) {
            cart.getGiftCouponList().add(skuRule.getCouponGift());
        }

        //赠送的赠品
        if (skuRule.getGoodsGift() != null) {
            cart.getGiftList().add(skuRule.getGoodsGift());
        }

        //赠送的积分
        if (skuRule.getPointGift() != null) {
            cart.setGiftPoint(skuRule.getPointGift());
        }


    }


}
