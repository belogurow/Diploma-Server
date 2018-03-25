package ru.belogurow.socialnetworkserver.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.util.UUID;

/**
 * @author alexbelogurow
 */
public final class UuidHelper {
    public static final Logger LOGGER = LoggerFactory.getLogger(UuidHelper.class);

    public static UUID randomUUID() {
        return UUID.randomUUID();
    }

    public static byte[] randomBytes() {
        return convertUuid2Bytes(UUID.randomUUID());
    }

    public static UUID convertBytes2Uuid(byte[] bytes) {
        ByteBuffer bb = ByteBuffer.wrap(bytes);
        long high = bb.getLong();
        long low = bb.getLong();
        return new UUID(high, low);
    }

    public static byte[] convertUuid2Bytes(UUID uuid) {
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());

        byte[] result = bb.array();
        //noinspection ImplicitArrayToString надо именно в виде B@77d7694(byte[]), т.к. в таком виде выводит логер MyBatis
        LOGGER.debug("convertUuid2Bytes: {} -> {}", uuid, result.toString());
        return result;
    }
}
