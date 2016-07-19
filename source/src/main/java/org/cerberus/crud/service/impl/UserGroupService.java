/*
 * Cerberus  Copyright (C) 2013  vertigo17
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This file is part of Cerberus.
 *
 * Cerberus is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Cerberus is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Cerberus.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.cerberus.crud.service.impl;

import java.util.List;

import org.cerberus.crud.dao.IUserGroupDAO;
import org.cerberus.crud.entity.UserGroup;
import org.cerberus.crud.entity.MessageGeneral;
import org.cerberus.enums.MessageGeneralEnum;
import org.cerberus.crud.entity.User;
import org.cerberus.exception.CerberusException;
import org.cerberus.crud.service.IUserGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * {Insert class description here}
 *
 * @author Tiago Bernardes
 * @version 1.0, 14/08/2013
 * @since 2.0.0
 */
@Service
public class UserGroupService implements IUserGroupService {

    @Autowired
    private IUserGroupDAO userGroupDAO;

    @Override
    public void updateUserGroups(User user, List<UserGroup> newGroups) throws CerberusException {

        List<UserGroup> oldGroups = this.findGroupByKey(user.getLogin());

        //delete if don't exist in new
        for (UserGroup old : oldGroups) {
            if (!newGroups.contains(old)) {
                this.removeGroupFromUser(old, user);
            }
        }
        //insert if don't exist in old
        for (UserGroup group : newGroups) {
            if (!oldGroups.contains(group)) {
                this.addGroupToUser(group, user);
            }
        }
    }

    private void addGroupToUser(UserGroup group, User user) throws CerberusException {
        if (!userGroupDAO.addGroupToUser(group, user)) {
            //TODO define message => error occur trying to add group user
            throw new CerberusException(new MessageGeneral(MessageGeneralEnum.NO_DATA_FOUND));
        }
    }

    private void removeGroupFromUser(UserGroup group, User user) throws CerberusException {
        if (!userGroupDAO.removeGroupFromUser(group, user)) {
            //TODO define message => error occur trying to delete group user
            throw new CerberusException(new MessageGeneral(MessageGeneralEnum.NO_DATA_FOUND));
        }
    }

    @Override
    public List<UserGroup> findGroupByKey(String login) throws CerberusException {
        List<UserGroup> list = userGroupDAO.findGroupByKey(login);
        if (list == null) {
            //TODO define message => error occur trying to find group user
            throw new CerberusException(new MessageGeneral(MessageGeneralEnum.NO_DATA_FOUND));
        }
        return list;
    }
}
