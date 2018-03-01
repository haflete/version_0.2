package com.smartThings.haflete.portal.backBean.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.ResourceBundle.Control;

/**
 * Find messages from resource bundle
 */
public class ResourceBundleUtil {
	private static final Locale ARABIC = new Locale("ar");
    private static final Control UTF8CONTROL = new UTF8Control();
    
	/**
	 * Search in the resource bundle file and find the related message
	 * 
	 * @param key String Key as it is in resource bundle file
	 * @return message String message related to the {@Link key}
	 */
	public String findMsg(String key) {
		try {
			ResourceBundle crunchifyResourceBundle = ResourceBundle.getBundle("messages", ARABIC, UTF8CONTROL);
			
			System.out.println("crunchifyResourceBundle is null = " + crunchifyResourceBundle == null);
			System.out.println("crunchifyResourceBundle.getString(" + key + ") = " + crunchifyResourceBundle.getString(key));
			
			return crunchifyResourceBundle.getString(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "";
	}
}
	
class UTF8Control extends Control {
    public ResourceBundle newBundle
        (String baseName, Locale locale, String format, ClassLoader loader, boolean reload)
            throws IllegalAccessException, InstantiationException, IOException
    {
        String bundleName = toBundleName(baseName, locale);
        String resourceName = toResourceName(bundleName, "properties");
        ResourceBundle bundle = null;
        InputStream stream = null;
        if (reload) {
            URL url = loader.getResource(resourceName);
            if (url != null) {
                URLConnection connection = url.openConnection();
                if (connection != null) {
                    connection.setUseCaches(false);
                    stream = connection.getInputStream();
                }
            }
        } else {
            stream = loader.getResourceAsStream(resourceName);
        }
        if (stream != null) {
            try {
                // This line is changed to make it to read properties files as UTF-8.
                bundle = new PropertyResourceBundle(new InputStreamReader(stream, "UTF-8"));
            } finally {
                stream.close();
            }
        }
        return bundle;
    }
}
