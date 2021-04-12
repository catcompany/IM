package com.imorning.im.util;

import java.util.regex.Pattern;

import android.widget.EditText;

/**
 * 类名：VerifyUtils
 * 说明：验证账号 密码是否合法
 */
public class VerifyUtils {
	public static boolean isNull(EditText editText) {
		String text = editText.getText().toString().trim();
		return text.length() <= 0;
	}

	public static boolean matchAccount(String text) {
		return Pattern.compile("^[a-z0-9_-]{6,18}$").matcher(text).matches();
	}

	public static boolean matchEmail(String text) {
		return Pattern.compile("\\w[\\w.-]*@[\\w.]+\\.\\w+").matcher(text)
				.matches();
	}
}
