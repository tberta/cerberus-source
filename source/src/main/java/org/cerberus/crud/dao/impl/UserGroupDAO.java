/* DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
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
package org.cerberus.crud.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.cerberus.crud.dao.IUserGroupDAO;
import org.cerberus.database.DatabaseSpring;
import org.cerberus.crud.entity.UserGroup;
import org.cerberus.crud.entity.User;
import org.cerberus.crud.factory.IFactoryGroup;
import org.cerberus.log.MyLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * {Insert class description here}
 *
 * @author Tiago Bernardes
 * @version 1.0, 09/08/2013
 * @since 2.0.0
 */
@Repository
public class UserGroupDAO implements IUserGroupDAO {

    @Autowired
    private DatabaseSpring databaseSpring;
    @Autowired
    private IFactoryGroup factoryGroup;

    @Override
    public boolean addGroupToUser(UserGroup group, User user) {
        boolean bool = false;
        final String query = "INSERT INTO usergroup (Login, GroupName) VALUES (?, ?)";

        Connection connection = this.databaseSpring.connect();
        try {
            PreparedStatement preStat = connection.prepareStatement(query);
            try {
                preStat.setString(1, user.getLogin());
                preStat.setString(2, group.getGroup());

                int res = preStat.executeUpdate();
                bool = res > 0;
            } catch (SQLException exception) {
                MyLogger.log(UserDAO.class.getName(), Level.ERROR, "Unable to execute query : "+exception.toString());
            } finally {
                preStat.close();
            }
        } catch (SQLException exception) {
            MyLogger.log(UserDAO.class.getName(), Level.ERROR, "Unable to execute query : "+exception.toString());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                MyLogger.log(TestCaseStepActionControlDAO.class.getName(), Level.WARN, e.toString());
            }
        }
        return bool;
    }

    @Override
    public boolean removeGroupFromUser(UserGroup group, User user) {
        boolean bool = false;
        final String query = "DELETE FROM usergroup WHERE login = ? AND groupname = ?";

        Connection connection = this.databaseSpring.connect();
        try {
            PreparedStatement preStat = connection.prepareStatement(query);
            try {
                preStat.setString(1, user.getLogin());
                preStat.setString(2, group.getGroup());

                int res = preStat.executeUpdate();
                bool = res > 0;
            } catch (SQLException exception) {
                MyLogger.log(UserDAO.class.getName(), Level.ERROR, "Unable to execute query : "+exception.toString());
            } finally {
                preStat.close();
            }
        } catch (SQLException exception) {
            MyLogger.log(UserDAO.class.getName(), Level.ERROR, "Unable to execute query : "+exception.toString());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                MyLogger.log(TestCaseStepActionControlDAO.class.getName(), Level.WARN, e.toString());
            }
        }
        return bool;
    }

    @Override
    public List<UserGroup> findGroupByKey(String login) {
        List<UserGroup> list = null;
        final String query = "SELECT groupname FROM usergroup WHERE login = ? ORDER BY groupname";

        Connection connection = this.databaseSpring.connect();
        try {
            PreparedStatement preStat = connection.prepareStatement(query);
            try {
                preStat.setString(1, login);

                ResultSet resultSet = preStat.executeQuery();
                try {
                    list = new ArrayList<UserGroup>();
                    while (resultSet.next()) {
                        UserGroup group = factoryGroup.create(resultSet.getString("groupname"));
                        list.add(group);
                    }
                } catch (SQLException exception) {
                    MyLogger.log(UserDAO.class.getName(), Level.ERROR, "Unable to execute query : "+exception.toString());
                } finally {
                    resultSet.close();
                }
            } catch (SQLException exception) {
                MyLogger.log(UserDAO.class.getName(), Level.ERROR, "Unable to execute query : "+exception.toString());
            } finally {
                preStat.close();
            }
        } catch (SQLException exception) {
            MyLogger.log(UserDAO.class.getName(), Level.ERROR, "Unable to execute query : "+exception.toString());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                MyLogger.log(TestCaseStepActionControlDAO.class.getName(), Level.WARN, e.toString());
            }
        }
        return list;
    }
}
