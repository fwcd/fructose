package com.fwcd.fructose.structs;

import java.util.Map;

public interface BidiMap<K, V> extends Map<K, V> {
	K getKey(Object value);
}
