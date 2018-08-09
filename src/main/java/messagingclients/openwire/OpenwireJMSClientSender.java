/*
 * Copyright 2018, EnMasse authors.
 * License: Apache License 2.0 (see the file LICENSE or http://apache.org/licenses/LICENSE-2.0.html).
 */
package messagingclients.openwire;

import messagingclients.ClientArgumentMap;
import messagingclients.ClientType;
import messagingclients.proton.java.ProtonJMSClientSender;


public class OpenwireJMSClientSender extends ProtonJMSClientSender {
    public OpenwireJMSClientSender() {
        this.setClientType(ClientType.CLI_JAVA_OPENWIRE_JMS_SENDER);
    }

    @Override
    protected ClientArgumentMap transformArguments(ClientArgumentMap args) {
        args = javaBrokerTransformation(args);
        return args;
    }
}
