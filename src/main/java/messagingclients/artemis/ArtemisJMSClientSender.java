/*
 * Copyright 2018, EnMasse authors.
 * License: Apache License 2.0 (see the file LICENSE or http://apache.org/licenses/LICENSE-2.0.html).
 */
package messagingclients.artemis;


import messagingclients.ClientArgumentMap;
import messagingclients.ClientType;
import messagingclients.proton.java.ProtonJMSClientSender;


public class ArtemisJMSClientSender extends ProtonJMSClientSender {
    public ArtemisJMSClientSender() {
        this.setClientType(ClientType.CLI_JAVA_ARTEMIS_JMS_SENDER);
    }

    @Override
    protected ClientArgumentMap transformArguments(ClientArgumentMap args) {
        args = javaBrokerTransformation(args);
        return args;
    }
}
