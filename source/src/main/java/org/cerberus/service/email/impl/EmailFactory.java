/**
 * Cerberus Copyright (C) 2013 - 2017 cerberustesting
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
package org.cerberus.service.email.impl;

import org.cerberus.service.email.IEmailFactory;
import org.springframework.stereotype.Service;

/**
 *
 * @author bcivel
 */
@Service
public class EmailFactory implements IEmailFactory {

    public Email create(String host, int smtpPort, String userName, String password, boolean setTls, String subject, String body,
            String from, String to, String cc) {
        Email email = new Email();
        email.setBody(body);
        email.setCc(cc);
        email.setFrom(from);
        email.setHost(host);
        email.setPassword(password);
        email.setSetTls(setTls);
        email.setSmtpPort(smtpPort);
        email.setSubject(subject);
        email.setTo(to);
        email.setUserName(userName);
        return email;
    }
}
