package com.macnss.Core;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

/**
 * The Cloudinary class provides a centralized instance of the Cloudinary client
 * for interacting with the Cloudinary service.
 */
public class cloudinary {

    private static volatile Cloudinary cloudinary = null;

    private cloudinary() {
    }

    static {
        if (cloudinary == null) {
            synchronized (cloudinary.class) {
                if (cloudinary == null) {
                    cloudinary = new Cloudinary(
                            ObjectUtils.asMap(
                                    "cloud_name", env.get("CLOUDINARY_CLOUD_NAME"),
                                    "api_key", env.get("CLOUDINARY_API_KEY"),
                                    "api_secret", env.get("CLOUDINARY_API_SECRET")
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
