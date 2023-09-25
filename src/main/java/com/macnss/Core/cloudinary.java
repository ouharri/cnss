package com.macnss.Core;

import com.cloudinary.*;
import com.cloudinary.utils.ObjectUtils;

import java.sql.DriverManager;
import java.sql.SQLException;

public class cloudinary {

    private static volatile Cloudinary cloudinary = null;

    private cloudinary() {
    }

    static {
        if (cloudinary == null) {
            synchronized (cloudinary.class) {  // Utilisez la classe cloudinary pour la synchronisation
                if (cloudinary == null) {
                    cloudinary = new Cloudinary(ObjectUtils.asMap(
                            "cloud_name", env.get("CLOUDINARY_CLOUD_NAME"),
                            "api_key", env.get("CLOUDINARY_API_KEY"),
                            "api_secret", env.get("CLOUDINARY_API_SECRET")));
                }
            }
        }
    }

    public static Cloudinary getCloudinary() {
        return cloudinary;
    }
}
