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
 * Created by T-BayMax  17-9-29 下午8:49
 *
 */

package com.ike.sq.alliance.websocket;

import java.io.EOFException;
import java.io.IOException;
import java.net.ProtocolException;

import okio.Buffer;
import okio.BufferedSource;
import okio.ByteString;

/**
 * Created by T-BayMax on 2017/9/29.
 */

public class WebSocketReader {
    int opcode;
    boolean isClient;
    BufferedSource source;
  //  FrameCallback frameCallback;

 /*   public interface FrameCallback {
        void onReadMessage(String text) throws IOException;
        void onReadMessage(ByteString bytes) throws IOException;
        void onReadPing(ByteString buffer);
        void onReadPong(ByteString buffer);
        void onReadClose(int code, String reason);
    }
    WebSocketReader(boolean isClient, BufferedSource source, FrameCallback frameCallback) {
        if (source == null) throw new NullPointerException("source == null");
        if (frameCallback == null) throw new NullPointerException("frameCallback == null");
        this.isClient = isClient;
        this.source = source;
        this.frameCallback = frameCallback;
    }

    private void readMessageFrame() throws IOException {
        int opcode = this.opcode;
        if (opcode != OPCODE_TEXT && opcode != OPCODE_BINARY) {
            throw new ProtocolException("Unknown opcode: " + toHexString(opcode));
        }

        Buffer message = new Buffer();
        readMessage(message);

        if (opcode == OPCODE_TEXT) {
            frameCallback.onReadMessage(message.readUtf8());
        } else {
            frameCallback.onReadMessage(message.readByteString());
        }
    }
    private void readControlFrame() throws IOException {
        Buffer buffer = new Buffer();
        if (frameBytesRead < frameLength) {
            if (isClient) {
                source.readFully(buffer, frameLength);
            } else {
                while (frameBytesRead < frameLength) {
                    int toRead = (int) Math.min(frameLength - frameBytesRead, maskBuffer.length);
                    int read = source.read(maskBuffer, 0, toRead);
                    if (read == -1) throw new EOFException();
                    toggleMask(maskBuffer, read, maskKey, frameBytesRead);
                    buffer.write(maskBuffer, 0, read);
                    frameBytesRead += read;
                }
            }
        }

        switch (opcode) {
            case OPCODE_CONTROL_PING:
                frameCallback.onReadPing(buffer.readByteString());
                break;
            case OPCODE_CONTROL_PONG:
                frameCallback.onReadPong(buffer.readByteString());
                break;
        }
    }
        *//** Read headers and process any control frames until we reach a non-control frame. *//*
    void readUntilNonControlFrame() throws IOException {
        while (!closed) {
            readHeader();
            if (!isControlFrame) {
                break;
            }
            readControlFrame();
        }
    }

    *//**
     * Reads a message body into across one or more frames. Control frames that occur between
     * fragments will be processed. If the message payload is masked this will unmask as it's being
     * processed.
     *//*
    private void readMessage(Buffer sink) throws IOException {
        while (true) {
            if (closed) throw new IOException("closed");

            if (frameBytesRead == frameLength) {
                if (isFinalFrame) return; // We are exhausted and have no continuations.

                readUntilNonControlFrame();
                if (opcode != OPCODE_CONTINUATION) {
                    throw new ProtocolException("Expected continuation opcode. Got: " + toHexString(opcode));
                }
                if (isFinalFrame && frameLength == 0) {
                    return; // Fast-path for empty final frame.
                }
            }

            long toRead = frameLength - frameBytesRead;

            long read;
            if (isMasked) {
                toRead = Math.min(toRead, maskBuffer.length);
                read = source.read(maskBuffer, 0, (int) toRead);
                if (read == -1) throw new EOFException();
                toggleMask(maskBuffer, read, maskKey, frameBytesRead);
                sink.write(maskBuffer, 0, (int) read);
            } else {
                read = source.read(sink, toRead);
                if (read == -1) throw new EOFException();
            }

            frameBytesRead += read;
        }
    }
    void processNextFrame() throws IOException {
        readHeader();
        if (isControlFrame) {
            readControlFrame();
        } else {
            readMessageFrame();
        }
    }

    private void readHeader() throws IOException {
        if (closed) throw new IOException("closed");

        // Disable the timeout to read the first byte of a new frame.
        int b0;
        long timeoutBefore = source.timeout().timeoutNanos();
        source.timeout().clearTimeout();
        try {
            b0 = source.readByte() & 0xff;
        } finally {
            source.timeout().timeout(timeoutBefore, TimeUnit.NANOSECONDS);
        }

        opcode = b0 & B0_MASK_OPCODE;
        isFinalFrame = (b0 & B0_FLAG_FIN) != 0;
        isControlFrame = (b0 & OPCODE_FLAG_CONTROL) != 0;

        // Control frames must be final frames (cannot contain continuations).
        if (isControlFrame && !isFinalFrame) {
            throw new ProtocolException("Control frames must be final.");
        }

        boolean reservedFlag1 = (b0 & B0_FLAG_RSV1) != 0;
        boolean reservedFlag2 = (b0 & B0_FLAG_RSV2) != 0;
        boolean reservedFlag3 = (b0 & B0_FLAG_RSV3) != 0;
        if (reservedFlag1 || reservedFlag2 || reservedFlag3) {
            // Reserved flags are for extensions which we currently do not support.
            throw new ProtocolException("Reserved flags are unsupported.");
        }

        int b1 = source.readByte() & 0xff;

        isMasked = (b1 & B1_FLAG_MASK) != 0;
        if (isMasked == isClient) {
            // Masked payloads must be read on the server. Unmasked payloads must be read on the client.
            throw new ProtocolException(isClient
                    ? "Server-sent frames must not be masked."
                    : "Client-sent frames must be masked.");
        }

        // Get frame length, optionally reading from follow-up bytes if indicated by special values.
        frameLength = b1 & B1_MASK_LENGTH;
        if (frameLength == PAYLOAD_SHORT) {
            frameLength = source.readShort() & 0xffffL; // Value is unsigned.
        } else if (frameLength == PAYLOAD_LONG) {
            frameLength = source.readLong();
            if (frameLength < 0) {
                throw new ProtocolException(
                        "Frame length 0x" + Long.toHexString(frameLength) + " > 0x7FFFFFFFFFFFFFFF");
            }
        }
        frameBytesRead = 0;

        if (isControlFrame && frameLength > PAYLOAD_BYTE_MAX) {
            throw new ProtocolException("Control frame must be less than " + PAYLOAD_BYTE_MAX + "B.");
        }

        if (isMasked) {
            // Read the masking key as bytes so that they can be used directly for unmasking.
            source.readFully(maskKey);
        }
    }*/
}
