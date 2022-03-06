package pinterest_private_api_v3;

import com.github.kil0bait.pinterest_private_api_v3.PIClient;
import com.github.kil0bait.pinterest_private_api_v3.models.users.Board;
import com.github.kil0bait.pinterest_private_api_v3.models.users.User;
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
import java.util.List;

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
    public void followingTest() {
        List<User> users = client.actions().meFollowing().join().getUsers();
        for (User user : users)
            log.info("{} {}", user.getId(), user.getUsername());
    }

    @Test
    public void boardsTest() {
        client.actions().meFollowing().join().getUsers()
                .forEach(user -> client.actions().userBoards(user.getId()).join().getBoards()
                        .forEach(board -> log.info("{} {} {}", board.getId(), board.getName(), board.getUrl())));
    }

    @Test
    public void boardPinsTest() {
        User user = client.actions().meFollowing().join().getUsers().stream().findFirst().orElseThrow();
        Board motiv = client.actions().userBoards(user.getId()).join().getBoards().stream()
                .filter(board -> board.getName().equals("motiv"))
                .findFirst().orElseThrow();
        client.actions().boardPins(motiv.getId(), null).join().getPins().stream()
                .filter(boardPin -> boardPin.getType().equals("pin"))
                .forEach(boardPin -> log.info("{} [{}:{}] {}", boardPin.getId(),
                        boardPin.getImage_large_size_pixels().getWidth(),
                        boardPin.getImage_large_size_pixels().getHeight(),
                        boardPin.getImage_large_url()));
    }

    @Test
    public void boardFeedPinsTest() {
        User user = client.actions().meFollowing().join().getUsers().stream().findFirst().orElseThrow();
        Board motiv = client.actions().userBoards(user.getId()).join().getBoards().stream()
                .filter(board -> board.getName().equals("motiv"))
                .findFirst().orElseThrow();
        client.actions().boardIdeasFeedPins(motiv.getId(), null).join().getPins().stream()
                .filter(boardPin -> boardPin.getType().equals("pin"))
                .forEach(boardPin -> log.info("{} [{}:{}] {}", boardPin.getId(),
                        boardPin.getImage_large_size_pixels().getWidth(),
                        boardPin.getImage_large_size_pixels().getHeight(),
                        boardPin.getImage_large_url()));
    }

    @Test
    public void homeFeedPinsTest() {
        client.actions().homeFeeds(null).join().getPins()
                .forEach(boardPin -> log.info("{} {}", boardPin.getImage_large_url(), boardPin.getType()));
    }

    @Test
    public void pinsWithAmountTest() {
        User user = client.actions().meFollowing().join().getUsers().stream().findFirst().orElseThrow();
        Board motiv = client.actions().userBoards(user.getId()).join().getBoards().stream()
                .filter(board -> board.getName().equals("motiv"))
                .findFirst().orElseThrow();
        client.actions().boardIdeasFeedPins(motiv.getId(), 12)
                .join().getPins().stream()
                .filter(boardPin -> boardPin.getType().equals("pin"))
                .forEach(boardPin -> log.info("{} [{}:{}] {}", boardPin.getId(),
                        boardPin.getImage_large_size_pixels().getWidth(),
                        boardPin.getImage_large_size_pixels().getHeight(),
                        boardPin.getImage_large_url()));
    }

    @Test
    public void searchPinsTest() {
        client.actions().searchPins("memes", null).join().getPins()
                .forEach(boardPin -> log.info("{} {}", boardPin.getImage_large_url(), boardPin.getType()));
    }
}
