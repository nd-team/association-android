/*
 *
 *  ,--^----------,--------,-----,-------^--,
 *   | |||||||||   `--------'     |          O
 *   `+---------------------------^----------|
 *     `\_,-------, _________________________|
 *       / XXXXXX /`|     /
 *      / XXXXXX /  `\   /
 *     / XXXXXX /\______(
 *    / XXXXXX /
 *   / XXXXXX /
 *  (________(
 *   `------'
 *
 * Created by T-BayMax  17-9-29 下午8:46
 *
 */

package com.ike.sq.alliance.websocket;

import java.io.IOException;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okhttp3.internal.Internal;
import okhttp3.internal.connection.StreamAllocation;
import okio.ByteString;

/**
 * Created by T-BayMax on 2017/9/29.
 */

public class RealWebSocket implements WebSocket{//, WebSocketReader.FrameCallback {
    Request originalRequest;
    WebSocketListener listener;
    Random random;
    String key;
    Runnable writerRunnable;
/*
    public RealWebSocket(Request request, WebSocketListener listener, Random random) {
        if (!"GET".equals(request.method())) {
            throw new IllegalArgumentException("Request must be GET: " + request.method());
        }
        this.originalRequest = request;
        this.listener = listener;
        this.random = random;

        byte[] nonce = new byte[16];
        random.nextBytes(nonce);
        this.key = ByteString.of(nonce).base64();

        this.writerRunnable = new Runnable() {
            @Override public void run() {
                try {
                    while (writeOneFrame()) {
                    }
                } catch (IOException e) {
                    failWebSocket(e, null);
                }
            }
        };
    }
    public void connect(OkHttpClient client) {
        client = client.newBuilder()
                .protocols(ONLY_HTTP1)
                .build();
        final int pingIntervalMillis = client.pingIntervalMillis();
        final Request request = originalRequest.newBuilder()
                .header("Upgrade", "websocket")
                .header("Connection", "Upgrade")
                .header("Sec-WebSocket-Key", key)
                .header("Sec-WebSocket-Version", "13")
                .build();
        call = Internal.instance.newWebSocketCall(client, request);
        call.enqueue(new Callback() {
            @Override public void onResponse(Call call, Response response) {
                try {
                    checkResponse(response);
                } catch (ProtocolException e) {
                    failWebSocket(e, response);
                    closeQuietly(response);
                    return;
                }

                // Promote the HTTP streams into web socket streams.
                StreamAllocation streamAllocation = Internal.instance.streamAllocation(call);
                streamAllocation.noNewStreams(); // Prevent connection pooling!
                Streams streams = streamAllocation.connection().newWebSocketStreams(streamAllocation);

                // Process all web socket messages.
                try {
                    listener.onOpen(RealWebSocket.this, response);
                    String name = "OkHttp WebSocket " + request.url().redact();
                    initReaderAndWriter(name, pingIntervalMillis, streams);
                    streamAllocation.connection().socket().setSoTimeout(0);
                    loopReader();
                } catch (Exception e) {
                    failWebSocket(e, null);
                }
            }

            @Override public void onFailure(Call call, IOException e) {
                failWebSocket(e, null);
            }
        });
    }
    void checkResponse(Response response) throws ProtocolException {
        if (response.code() != 101) {
            throw new ProtocolException("Expected HTTP 101 response but was '"
                    + response.code() + " " + response.message() + "'");
        }

        String headerConnection = response.header("Connection");
        if (!"Upgrade".equalsIgnoreCase(headerConnection)) {
            throw new ProtocolException("Expected 'Connection' header value 'Upgrade' but was '"
                    + headerConnection + "'");
        }

        String headerUpgrade = response.header("Upgrade");
        if (!"websocket".equalsIgnoreCase(headerUpgrade)) {
            throw new ProtocolException(
                    "Expected 'Upgrade' header value 'websocket' but was '" + headerUpgrade + "'");
        }

        String headerAccept = response.header("Sec-WebSocket-Accept");
        String acceptExpected = ByteString.encodeUtf8(key + WebSocketProtocol.ACCEPT_MAGIC)
                .sha1().base64();
        if (!acceptExpected.equals(headerAccept)) {
            throw new ProtocolException("Expected 'Sec-WebSocket-Accept' header value '"
                    + acceptExpected + "' but was '" + headerAccept + "'");
        }
    }

    public void failWebSocket(Exception e, Response response) {
        Streams streamsToClose;
        synchronized (this) {
            if (failed) return; // Already failed.
            failed = true;
            streamsToClose = this.streams;
            this.streams = null;
            if (cancelFuture != null) cancelFuture.cancel(false);
            if (executor != null) executor.shutdown();
        }

        try {
            listener.onFailure(this, e, response);
        } finally {
            closeQuietly(streamsToClose);
        }
    }

    @Override
    public void onReadMessage(String text) throws IOException {

    }

    @Override
    public void onReadMessage(ByteString bytes) throws IOException {

    }

    @Override
    public void onReadPing(ByteString buffer) {

    }

    @Override
    public void onReadPong(ByteString buffer) {

    }

    @Override
    public void onReadClose(int code, String reason) {

    }
*/
    @Override
    public Request request() {
        return null;
    }

    @Override
    public long queueSize() {
        return 0;
    }

    @Override
    public boolean send(String text) {
        return false;
    }

    @Override
    public boolean send(ByteString bytes) {
        return false;
    }

    @Override
    public boolean close(int code, String reason) {
        return false;
    }

    @Override
    public void cancel() {

    }
}
