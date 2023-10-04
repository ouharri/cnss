package com.macnss.app.Providers;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.macnss.Core.environment;

/**
 * The Cloudinary class provides a centralized instance of the Cloudinary client
 * for interacting with the Cloudinary service.
 */
public class cloudinaryProvider {

    private static volatile Cloudinary cloudinary = null;

    private cloudinaryProvider() {
    }

    static {
        if (cloudinary == null) {
            synchronized (cloudinaryProvider.class) {
                if (cloudinary == null) {
                    cloudinary = new Cloudinary(
                            ObjectUtils.asMap(
                                    "cloud_name", environment.get("CLOUDINARY_CLOUD_NAME"),
                                    "api_key", environment.get("CLOUDINARY_API_KEY"),
                                    "api_secret", environment.get("CLOUDINARY_API_SECRET")
                            )
                    );
                }
            }
        }
    }

    /**
     * Retrieves the Cloudinary client instance.
     *
     * @return The Cloudinary client instance.
     */
    public static Cloudinary getCloudinary() {
        return cloudinary;
    }
}
