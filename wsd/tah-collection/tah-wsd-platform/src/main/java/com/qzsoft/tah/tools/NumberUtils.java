package com.qzsoft.tah.tools;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

/**
 *
 * 数字处理相关工具类
 *
 */
public class NumberUtils {

	private static final int DEF_DIV_SCALE = 10;

	public static BigDecimal getBigDecimal(String num) {
		return (null != num && !("").equals(num)) ? new BigDecimal(num.replace(",", "")) : BigDecimal.ZERO;
	}

	public static BigDecimal getBigDecimal(Object o) {
		String num = o == null ? "" : o.toString();
		return StringUtils.isNotBlank(num) ? new BigDecimal(num) : BigDecimal.ZERO;
	}

	public static BigDecimal div(BigDecimal v1, BigDecimal v2) {
		return div(v1, v2, DEF_DIV_SCALE);
	}

	public static BigDecimal div(BigDecimal v1, BigDecimal v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b1 = new BigDecimal(Double.toString(v1.doubleValue()));
		BigDecimal b2 = new BigDecimal(Double.toString(v2.doubleValue()));
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP);
	}
}
