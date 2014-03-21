package edu.ntu.cltk.exception;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class CLTKRuntimeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9173720130074721002L;

	/**
     * Constructs a new <code>IllegalArgumentException with specified formatted detail message.
     * Message formatting is delegated to {@link java.text.MessageFormat}.
     * @param pattern format specifier
     * @param arguments format arguments
     * @return built exception
     */
    public static IllegalArgumentException createIllegalArgumentException(final String pattern,
                                                                          final Object ... arguments) {
        return new IllegalArgumentException() {

            /** Serializable version identifier. */
            private static final long serialVersionUID = -6555453980658317913L;

            /** {@inheritDoc} */
            @Override
            public String getMessage() {
                return buildMessage(Locale.US, pattern, arguments);
            }

            /** {@inheritDoc} */
            @Override
            public String getLocalizedMessage() {
                return buildMessage(Locale.getDefault(), pattern, arguments);
            }

        };
    }
    
    /**
     * Builds a message string by from a pattern and its arguments.
     * @param locale Locale in which the message should be translated
     * @param pattern format specifier
     * @param arguments format arguments
     * @return a message string
     */
    private static String buildMessage(final Locale locale, final String pattern,
                                       final Object ... arguments) {
        return (pattern == null) ? "" : new MessageFormat(translate(pattern, locale), locale).format(arguments);
    }
    
    /**
     * Translate a string to a given locale.
     * @param s string to translate
     * @param locale locale into which to translate the string
     * @return translated string or original string
     * for unsupported locales or unknown strings
     */
    private static String translate(final String s, final Locale locale) {
        try {
            ResourceBundle bundle =
                    ResourceBundle.getBundle("org.apache.commons.math.MessagesResources", locale);
            if (bundle.getLocale().getLanguage().equals(locale.getLanguage())) {
                // the value of the resource is the translated string
                return bundle.getString(s);
            }

        } catch (MissingResourceException mre) {
            // do nothing here
        }

        // the locale is not supported or the resource is unknown
        // don't translate and fall back to using the string as is
        return s;

    }
}
