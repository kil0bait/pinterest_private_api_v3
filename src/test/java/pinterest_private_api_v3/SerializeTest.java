package pinterest_private_api_v3;

import com.github.kil0bait.pinterest_private_api_v3.PIClient;
import com.github.kil0bait.pinterest_private_api_v3.responses.pins.PinsResponse;
import com.github.kil0bait.pinterest_private_api_v3.utils.PIUtils;
import okhttp3.OkHttpClient;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

import static com.github.kil0bait.pinterest_private_api_v3.utils.SerializeUtil.*;

public class SerializeTest {
    private static final String PATH = "temp/";
    private static final String CLIENT_FILE = PATH + "client.ser";
    private static final String COOKIE_FILE = PATH + "cookie.ser";
    private static final Logger log = LoggerFactory.getLogger(SerializeTest.class);
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
    public void serializePinsResponseTest() throws IOException {
        PinsResponse response = client.actions().homeFeeds(5, null).join();
        serialize(response, new File(PATH + "/response.ser"));
    }

    @Test
    public void deserializePinsResponseTest() throws IOException, ClassNotFoundException {
        PinsResponse response = deserialize(new File(PATH + "/response.ser"), PinsResponse.class);
        log.info("{} {} {}", response.getBookmark(), response.getPins().get(0).getImage_large_url(), response.getPins().get(0).getVideos());
    }
}
