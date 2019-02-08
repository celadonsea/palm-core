package com.celadonsea.palm.client;

import com.celadonsea.palm.config.MessageClientConfig;
import com.celadonsea.palm.security.CredentialStore;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class MessageClientFactoryTest {

    private static final String TEST_CLIENT_DIALECT = "com.celadonsea.palm.client.TestMessageClient";

    private static final String SOME_UNKNOWN_DIALECT = "com.celadonse.palm.client.UnknownClientImplementation";

    @Test
    public void shouldCreateMqttClient() {
        MessageClientConfig messageClientConfig = createConfig(TEST_CLIENT_DIALECT, false);
        MessageClient client = MessageClientFactory.getFactory().getClient(messageClientConfig);
        Assert.assertNotNull(client);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotCreateSecuredMqttClientWithUnsecuredMethod() {
        MessageClientConfig messageClientConfig = createConfig(TEST_CLIENT_DIALECT, true);
        MessageClientFactory.getFactory().getClient(messageClientConfig);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotCreateUnsupportedClient() {
        MessageClientConfig messageClientConfig = createConfig(SOME_UNKNOWN_DIALECT, false);
        MessageClientFactory.getFactory().getClient(messageClientConfig);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotCreateSecuredMqttClientWithEmptyCredentialStore() {
        MessageClientConfig messageClientConfig = createConfig(TEST_CLIENT_DIALECT, true);
        MessageClientFactory.getFactory().getClient(messageClientConfig, null);
    }

    @Test
    public void shouldCreateSecuredMqttClient() {
        MessageClientConfig messageClientConfig = createConfig(TEST_CLIENT_DIALECT, true);
        CredentialStore credentialStore = createCredentialStore();
        MessageClient client = MessageClientFactory.getFactory().getClient(messageClientConfig, credentialStore);
        Assert.assertNotNull(client);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotCreateUnsupportedSecuredClient() {
        MessageClientConfig messageClientConfig = createConfig(SOME_UNKNOWN_DIALECT, false);
        CredentialStore credentialStore = createCredentialStore();
        MessageClientFactory.getFactory().getClient(messageClientConfig, credentialStore);
    }

    @Test
    public void shouldCreateUnsecuredMqttClientWithSecuredMethod() {
        MessageClientConfig messageClientConfig = createConfig(TEST_CLIENT_DIALECT, false);
        MessageClient client = MessageClientFactory.getFactory().getClient(messageClientConfig, null);
        Assert.assertNotNull(client);
    }

    private MessageClientConfig createConfig(final String clientDialect, final boolean secured) {
        return new MessageClientConfig() {
            @Override
            public String getClientDialect() {
                return clientDialect;
            }

            @Override
            public String getClientId() {
                return null;
            }

            @Override
            public String getBrokerUrl() {
                return null;
            }

            @Override
            public int getConnectionTimeout() {
                return 0;
            }

            @Override
            public int getQos() {
                return 0;
            }

            @Override
            public boolean isConnectionSecured() {
                return secured;
            }

            @Override
            public int getMaxThread() {
                return 10;
            }

            @Override
            public int getThreadKeepAliveTime() {
                return 1;
            }
        };
    }

    private CredentialStore createCredentialStore() {
        return new CredentialStore() {
            @Override
            public InputStream getCertificate() {
                return new ByteArrayInputStream("certificate".getBytes());
            }

            @Override
            public InputStream getPrivateKey() {
                return new ByteArrayInputStream("privatekey".getBytes());
            }
        };
    }
}
