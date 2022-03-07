package pinterest_private_api_v3;

import com.github.kil0bait.pinterest_private_api_v3.PIClient;
import com.github.kil0bait.pinterest_private_api_v3.models.pin.Pin;
import com.github.kil0bait.pinterest_private_api_v3.models.users.Board;
import com.github.kil0bait.pinterest_private_api_v3.models.users.User;
import com.github.kil0bait.pinterest_private_api_v3.responses.pins.PinsResponse;
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
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        client.actions().boardPins(motiv.getId(), null, null).join().getPins().stream()
                .filter(pin -> pin.getType().equals("pin"))
                .forEach(pin -> log.info("{} [{}:{}] {}", pin.getId(),
                        pin.getImage_large_size_pixels().getWidth(),
                        pin.getImage_large_size_pixels().getHeight(),
                        pin.getImage_large_url()));
    }

    @Test
    public void boardFeedPinsTest() {
        User user = client.actions().meFollowing().join().getUsers().stream().findFirst().orElseThrow();
        Board motiv = client.actions().userBoards(user.getId()).join().getBoards().stream()
                .filter(board -> board.getName().equals("motiv"))
                .findFirst().orElseThrow();
        client.actions().boardIdeasFeedPins(motiv.getId(), null, null).join().getPins().stream()
                .filter(pin -> pin.getType().equals("pin"))
                .forEach(pin -> log.info("{} [{}:{}] {}", pin.getId(),
                        pin.getImage_large_size_pixels().getWidth(),
                        pin.getImage_large_size_pixels().getHeight(),
                        pin.getImage_large_url()));
    }

    @Test
    public void homeFeedPinsTest() {
        client.actions().homeFeeds(null, null).join().getPins()
                .forEach(pin -> log.info("{} {}", pin.getImage_large_url(), pin.getType()));
    }

    @Test
    public void pinsWithAmountTest() {
        User user = client.actions().meFollowing().join().getUsers().stream().findFirst().orElseThrow();
        Board motiv = client.actions().userBoards(user.getId()).join().getBoards().stream()
                .filter(board -> board.getName().equals("motiv"))
                .findFirst().orElseThrow();
        client.actions().boardIdeasFeedPins(motiv.getId(), 12, null)
                .join().getPins().stream()
                .filter(pin -> pin.getType().equals("pin"))
                .forEach(pin -> log.info("{} [{}:{}] {}", pin.getId(),
                        pin.getImage_large_size_pixels().getWidth(),
                        pin.getImage_large_size_pixels().getHeight(),
                        pin.getImage_large_url()));
    }

    @Test
    public void searchPinsTest() {
        client.actions().searchPins("memes videos", null, null).join().getPins()
                .forEach(pin -> log.info("{} {} {} {}", pin.getRepin_count(), pin.getLike_count(), pin.getImage_large_url(), pin.getVideoUrl()));
    }

    @Test
    public void pinsUniquenessTest() {
        int pinsAmount = 10;
        int iterations = 5;
        Set<Pin> noBookmarkPins = new HashSet<>();
        for (int i = 0; i < iterations; i++)
            client.actions().boardIdeasFeedPins("729090695863834997", pinsAmount, null).join().getPins()
                    .forEach(pin -> {
                        log.info("{} {}", pin.getImage_large_url(), pin.getType());
                        noBookmarkPins.add(pin);
                    });
        Set<Pin> bookmarkPins = new HashSet<>();
        PinsResponse response = null;
        for (int i = 0; i < iterations; i++) {
            response = client.actions().boardIdeasFeedPins("729090695863834997", pinsAmount, response).join();
            response.getPins().forEach(pin -> {
                log.info("{} {}", pin.getImage_large_url(), pin.getType());
                bookmarkPins.add(pin);
            });
        }
        log.info("No Bookmark : {} pins, {} uniqueness",
                noBookmarkPins.size(), ((float) noBookmarkPins.size()) / (pinsAmount * iterations));
        log.info("+  Bookmark : {} pins, {} uniqueness",
                bookmarkPins.size(), ((float) bookmarkPins.size()) / (pinsAmount * iterations));
    }

    @Test
    public void findVideosTest() {
        PinsResponse response = null;
        for (int i = 0; i < 5; i++) {
            response = client.actions().searchPins("tik", 20, response).join();
            response.getPins().stream()
                    .filter(pin -> pin.getVideos() != null)
                    .sorted(Comparator.comparingInt(Pin::getComment_count))
                    .forEach(pin -> log.info("{} {} {} {}",
                            pin.getComment_count(), pin.getGrid_title(), pin.getThumbnailUrl(), pin.getVideoUrl()));
        }
    }
}
