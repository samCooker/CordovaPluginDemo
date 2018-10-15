package com.chaochuang.oa.dataaec.util;
public enum AesType {

	ECB("ECB", "0"), CBC("CBC", "1"), CFB("CFB", "2"), OFB("OFB", "3");
	private String k;
	private String v;

	AesType(String k, String v) {
		this.k = k;
		this.v = v;
	}

	public String key() {
		return this.k;
	}

	public String value() {
		return this.v;
	}

	public static AesType get(int id) {
		AesType[] vs = AesType.values();
		for (int i = 0; i < vs.length; i++) {
			AesType d = vs[i];
			if (d.key().equals(id))
				return d;
		}
		return AesType.CBC;
	}

}
