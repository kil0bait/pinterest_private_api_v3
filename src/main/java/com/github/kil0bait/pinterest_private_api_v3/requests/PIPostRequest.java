package com.github.kil0bait.pinterest_private_api_v3.requests;

import com.github.kil0bait.pinterest_private_api_v3.PIClient;
import com.github.kil0bait.pinterest_private_api_v3.models.PIBaseModel;
import com.github.kil0bait.pinterest_private_api_v3.models.PIPayload;
import com.github.kil0bait.pinterest_private_api_v3.responses.PIResponse;
import com.github.kil0bait.pinterest_private_api_v3.utils.PIUtils;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class PIPostRequest<T extends PIResponse> extends PIRequest<T> {
    private static final Logger log = LoggerFactory.getLogger(PIPostRequest.class);

    protected abstract PIBaseModel getPayload(PIClient client);

    protected boolean isSigned() {
        return true;
    }

    @Override
    public Request formRequest(PIClient client) {
        Request.Builder req = new Request.Builder().url(this.formUrl(client));
        this.applyHeaders(client, req);
        req.post(this.getRequestBody(client));

        return req.build();
    }

    protected RequestBody getRequestBody(PIClient client) {
        if (getPayload(client) == null) {
            return RequestBody.create("", null);
        }
        String payload = PIUtils.objectToJson(getPayload(client) instanceof PIPayload
                ? client.setPIPayloadDefaults((PIPayload) getPayload(client))
                : getPayload(client));
        log.debug("Payload : {}", payload);
        if (isSigned()) {
            return RequestBody.create(PIUtils.generateSignature(payload),
                    MediaType.parse("application/x-www-form-urlencoded; charset=UTF-8"));
        } else {
            return RequestBody.create(payload, MediaType.parse("application/json; charset=UTF-8"));
        }
    }

}
