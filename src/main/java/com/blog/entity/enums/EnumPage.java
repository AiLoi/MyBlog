package com.blog.entity.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum EnumPage {



	TOTAL_COUNT("-1", -1),
	PAGE_INDEX("1", 1),
	PAGE_SIZE("20", 20);

	/** 描述 */
	private String name;
	/** 枚举值 */
	private Integer value;

	private EnumPage(String name, Integer value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	private static List<Map<String, String>> list;
	private static Map<Integer, String> map;

	public static List<Map<String, String>> toList() {
		if (list == null) {
			EnumPage[] ary = EnumPage.values();
			List<Map<String, String>> listTmp = new ArrayList<>();
			for (int i = 0; i < ary.length; i++) {
				Map<String, String> map = new HashMap<>();
				map.put("value", String.valueOf(ary[i].getValue()));
				map.put("name", ary[i].getName());
				listTmp.add(map);
			}
			list = listTmp;
		}
		return list;
	}

	public static Map<Integer, String> toMap() {
		if (map == null) {
			EnumPage[] ary = EnumPage.values();
			Map<Integer, String> enumMap = new HashMap<>();
			for (int num = 0; num < ary.length; num++) {
				enumMap.put(ary[num].getValue(), ary[num].getName());
			}
			map = enumMap;
		}
		return map;
	}

	public static String getNameByValue(Integer value) {
		return toMap().get(value);
	}
}
