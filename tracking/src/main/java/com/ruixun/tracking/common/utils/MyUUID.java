package com.ruixun.tracking.common.utils;

import java.util.UUID;

public class MyUUID {
	public static String getUUID() {
		return UUID.randomUUID().toString().replaceAll("-","");
	}
}
