package ru.core.profilems.dto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ThreadLocalMap {
    private static final ThreadLocal<Map<String, Object>> CONTEXT_HOLDER = ThreadLocal.withInitial(HashMap::new);

    public static <T> T get(String key, Class<T> type) {
        Object value = CONTEXT_HOLDER.get().get(key);

        return type.isInstance(value) ? type.cast(value) : null;
    }

    public static void put(String key, Object value) {
        CONTEXT_HOLDER.get().put(key, value);
    }

    public static void remove(String key) {
        CONTEXT_HOLDER.get().remove(key);
    }

    public static void clearMap() {
        CONTEXT_HOLDER.get().clear();
    }

    public static void clear() {
        CONTEXT_HOLDER.remove();
    }
}
