package com.github.kil0bait.pinterest_private_api_v3.utils;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SerializableCookieJar implements CookieJar, Serializable {

    @Serial
    private static final long serialVersionUID = 3535861063208032446L;

    Map<String, List<SerializableCookie>> map = new HashMap<>();



    @NotNull
    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        return map.getOrDefault(url.host(), new ArrayList<>()).stream()
                .map(c -> c.cookie)
                .collect(Collectors.toList());
    }

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        List<SerializableCookie> list =
                cookies.stream().map(SerializableCookie::new).collect(Collectors.toList());
        if (map.putIfAbsent(url.host(), list) != null) {
            map.get(url.host()).addAll(list);
        }
    }

    public static class SerializableCookie implements Serializable {

        public SerializableCookie(Cookie cookie) {
            this.cookie = cookie;
        }

        @Serial
        private static final long serialVersionUID = 8719205416398644829L;

        private transient Cookie cookie;

        private static final long NON_VALID_EXPIRES_AT = -1L;

        @Serial
        private void writeObject(ObjectOutputStream out) throws IOException {
            out.writeObject(cookie.name());
            out.writeObject(cookie.value());
            out.writeLong(cookie.persistent() ? cookie.expiresAt() : NON_VALID_EXPIRES_AT);
            out.writeObject(cookie.domain());
            out.writeObject(cookie.path());
            out.writeBoolean(cookie.secure());
            out.writeBoolean(cookie.httpOnly());
            out.writeBoolean(cookie.hostOnly());
        }

        @Serial
        private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
            Cookie.Builder builder = new Cookie.Builder();

            builder.name((String) in.readObject());

            builder.value((String) in.readObject());

            long expiresAt = in.readLong();
            if (expiresAt != NON_VALID_EXPIRES_AT) {
                builder.expiresAt(expiresAt);
            }

            final String domain = (String) in.readObject();
            builder.domain(domain);

            builder.path((String) in.readObject());

            if (in.readBoolean())
                builder.secure();

            if (in.readBoolean())
                builder.httpOnly();

            if (in.readBoolean())
                builder.hostOnlyDomain(domain);

            cookie = builder.build();
        }

    }

}
