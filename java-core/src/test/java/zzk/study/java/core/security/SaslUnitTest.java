package zzk.study.java.core.security;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.security.auth.callback.*;
import javax.security.sasl.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SaslUnitTest {

    private static final String MECHANISM = "DIGEST-MD5";
    private static final String SERVER_NAME = "myServer";
    private static final String PROTOCOL = "myProtocol";
    private static final String AUTHORIZATION_ID = null;
    private static final String QOP_LEVEL = "auth-conf";

    private SaslServer saslServer;
    private SaslClient saslClient;

    @Before
    public void setUp() throws SaslException {

        ServerCallbackHandler serverHandler = new ServerCallbackHandler();
        ClientCallbackHandler clientHandler = new ClientCallbackHandler();

        Map<String, String> props = new HashMap<>();
        props.put(Sasl.QOP, QOP_LEVEL);

        saslServer = Sasl.createSaslServer(MECHANISM, PROTOCOL, SERVER_NAME, props, serverHandler);
        saslClient = Sasl.createSaslClient(new String[] { MECHANISM }, AUTHORIZATION_ID, PROTOCOL, SERVER_NAME, props, clientHandler);

    }

    @Test
    public void givenHandlers_whenStarted_thenAutenticationWorks() throws SaslException {

        byte[] challenge;
        byte[] response;

        challenge = saslServer.evaluateResponse(new byte[0]);
        response = saslClient.evaluateChallenge(challenge);

        challenge = saslServer.evaluateResponse(response);
        response = saslClient.evaluateChallenge(challenge);

        assertTrue(saslServer.isComplete());
        assertTrue(saslClient.isComplete());

        String qop = (String) saslClient.getNegotiatedProperty(Sasl.QOP);
        assertEquals("auth-conf", qop);

        byte[] outgoing = "Baeldung".getBytes();
        byte[] secureOutgoing = saslClient.wrap(outgoing, 0, outgoing.length);

        byte[] secureIncoming = secureOutgoing;
        byte[] incoming = saslServer.unwrap(secureIncoming, 0, secureIncoming.length);
        assertEquals("Baeldung", new String(incoming, StandardCharsets.UTF_8));
    }

    @After
    public void tearDown() throws SaslException {
        saslClient.dispose();
        saslServer.dispose();
    }

    public static class ClientCallbackHandler implements CallbackHandler {

        @Override
        public void handle(Callback[] cbs) throws IOException, UnsupportedCallbackException {
            for (Callback cb : cbs) {
                if (cb instanceof NameCallback) {
                    NameCallback nc = (NameCallback) cb;
                    nc.setName("username");
                } else if (cb instanceof PasswordCallback) {
                    PasswordCallback pc = (PasswordCallback) cb;
                    pc.setPassword("password".toCharArray());
                } else if (cb instanceof RealmCallback) {
                    RealmCallback rc = (RealmCallback) cb;
                    rc.setText("myServer");
                }
            }
        }
    }

    public static class ServerCallbackHandler implements CallbackHandler {

        @Override
        public void handle(Callback[] cbs) throws IOException, UnsupportedCallbackException {
            for (Callback cb : cbs) {
                if (cb instanceof AuthorizeCallback) {
                    AuthorizeCallback ac = (AuthorizeCallback) cb;
                    ac.setAuthorized(true);
                } else if (cb instanceof NameCallback) {
                    NameCallback nc = (NameCallback) cb;
                    nc.setName("username");

                } else if (cb instanceof PasswordCallback) {
                    PasswordCallback pc = (PasswordCallback) cb;
                    pc.setPassword("password".toCharArray());
                } else if (cb instanceof RealmCallback) {
                    RealmCallback rc = (RealmCallback) cb;
                    rc.setText("myServer");
                }
            }
        }
    }

}
