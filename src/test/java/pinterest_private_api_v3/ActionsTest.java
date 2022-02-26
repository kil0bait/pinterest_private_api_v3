package pinterest_private_api_v3;

import com.github.kil0bait.pinterest_private_api_v3.PIClient;
import com.github.kil0bait.pinterest_private_api_v3.models.pin.PinImage;
import com.github.kil0bait.pinterest_private_api_v3.requests.feeds.FeedsRequest;
import com.github.kil0bait.pinterest_private_api_v3.responses.feeds.FeedsResponse;
import com.github.kil0bait.pinterest_private_api_v3.utils.PIUtils;
import okhttp3.OkHttpClient;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class ActionsTest {
    private static final String PATH = "temp/";
    private static final String CLIENT_FILE = PATH + "client.ser";
    private static final String COOKIE_FILE = PATH + "cookie.ser";
    private static final Logger log = LoggerFactory.getLogger(ActionsTest.class);
    private static PIClient client;

    @BeforeClass
    public static void globalSetUp() throws IOException, ClassNotFoundException {
        OkHttpClient.Builder clientBuilder = PIUtils.defaultHttpClientBuilder();
        TestUtils.setUpProxy(clientBuilder);
        client = PIClient.deserialize(new File(CLIENT_FILE), new File(COOKIE_FILE), clientBuilder);
    }

    @AfterClass
    public static void globalTearDown() throws IOException {
        client.serialize(new File(CLIENT_FILE), new File(COOKIE_FILE));
    }

    @Test
    public void clientTokenTest() {
        log.info(client.getAuthToken());
        Assert.assertNotNull(client.getAuthToken());
    }

    @Test
    public void feedPinsTest() {
        FeedsResponse response = new FeedsRequest().execute(client).join();
        for (PinImage i : response.getData())
            log.info(i.getImage_large_url() + "\r\n" + i.getImage_medium_url());
        Assert.assertTrue(response.getData().size() > 0);
    }
}
