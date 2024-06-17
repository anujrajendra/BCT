package com.cris.custom.indicator.message;

import com.onwbp.base.text.Severity;
import com.onwbp.base.text.UserMessage;
import com.onwbp.base.text.UserMessageRef;
import java.util.Locale;
import java.util.MissingResourceException;

@SuppressWarnings("rawtypes")
public final class Messages {
	private static final String PROPERTIES_FILE_NAME = "Messages";

	public static String get(Class pBundleClass, Locale pLocale, String pKey, Object... pValues) {
		try {
			UserMessage message = getInfo(pBundleClass, pKey, pValues);
			return message.formatMessage(pLocale);
		} catch (MissingResourceException exception) {
			return pKey;
		}
	}

	public static UserMessage get(Class pBundleClass, Severity pSeverity, String pKey, Object... pValues) {
		String bundleName = String.valueOf(pBundleClass.getPackage().getName()) + "." + PROPERTIES_FILE_NAME;
		ClassLoader classLoader = pBundleClass.getClassLoader();
		UserMessageRef message = new UserMessageRef(pSeverity, pKey, bundleName, pValues, classLoader);
		return (UserMessage) message;
	}

	public static UserMessage getError(Class pBundleClass, String pKey, Object... pValues) {
		return get(pBundleClass, Severity.ERROR, pKey, pValues);
	}

	public static UserMessage getInfo(Class pBundleClass, String pKey, Object... pValues) {
		return get(pBundleClass, Severity.INFO, pKey, pValues);
	}

	public static UserMessage getWarning(Class pBundleClass, String pKey, Object... pValues) {
		return get(pBundleClass, Severity.WARNING, pKey, pValues);
	}
}