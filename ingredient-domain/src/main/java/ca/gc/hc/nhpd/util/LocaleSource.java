package ca.gc.hc.nhpd.util;

import java.util.Locale;

/*******************************************************************************
 * An interface that requires the implementor to supply the user's locale when
 * requested.
 */
public interface LocaleSource {
    /***************************************************************************
     * Gets the Locale suitable for the current user.
     */
    public Locale getUserLocale();
}
