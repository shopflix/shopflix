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
package cloud.shopfly.b2c.core.system.service;

import cloud.shopfly.b2c.core.system.model.dos.Menu;
import cloud.shopfly.b2c.core.system.model.vo.MenuVO;
import cloud.shopfly.b2c.core.system.model.vo.MenusVO;
import cloud.shopfly.b2c.framework.database.Page;

import java.util.List;

/**
 * 菜单管理业务层
 *
 * @author zh
 * @version v7.0
 * @since v7.0.0
 * 2018-06-19 09:46:02
 */
public interface MenuManager {

    /**
     * 查询菜单管理列表
     *
     * @param page     页码
     * @param pageSize 每页数量
     * @return Page
     */
    Page list(int page, int pageSize);

    /**
     * 添加菜单管理
     *
     * @param menu 菜单管理
     * @return Menu 菜单管理
     */
    Menu add(MenuVO menu);

    /**
     * 修改菜单管理
     *
     * @param menu 菜单管理
     * @param id   菜单管理主键
     * @return Menu 菜单管理
     */
    Menu edit(Menu menu, Integer id);

    /**
     * 删除菜单管理
     *
     * @param id 菜单管理主键
     */
    void delete(Integer id);

    /**
     * 获取菜单管理
     *
     * @param id 菜单管理主键
     * @return MenuVO  菜单管理
     */
    Menu getModel(Integer id);

    /**
     * 根据id获取菜单集合
     *
     * @param id 菜单的id
     * @return
     */
    List<MenusVO> getMenuTree(Integer id);

    /**
     * 获取菜单管理
     *
     * @param identifier 菜单的唯一标识
     * @return MenuVO  菜单管理
     */
    Menu getMenuByIdentifier(String identifier);

}