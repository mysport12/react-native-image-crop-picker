package com.reactnative.ivpusic.imagepicker;

import static androidx.exifinterface.media.ExifInterface.TAG_APERTURE_VALUE;
import static androidx.exifinterface.media.ExifInterface.TAG_DATETIME;
import static androidx.exifinterface.media.ExifInterface.TAG_DATETIME_DIGITIZED;
import static androidx.exifinterface.media.ExifInterface.TAG_EXPOSURE_TIME;
import static androidx.exifinterface.media.ExifInterface.TAG_FLASH;
import static androidx.exifinterface.media.ExifInterface.TAG_FOCAL_LENGTH;
import static androidx.exifinterface.media.ExifInterface.TAG_GPS_ALTITUDE;
import static androidx.exifinterface.media.ExifInterface.TAG_GPS_ALTITUDE_REF;
import static androidx.exifinterface.media.ExifInterface.TAG_GPS_DATESTAMP;
import static androidx.exifinterface.media.ExifInterface.TAG_GPS_LATITUDE;
import static androidx.exifinterface.media.ExifInterface.TAG_GPS_LATITUDE_REF;
import static androidx.exifinterface.media.ExifInterface.TAG_GPS_LONGITUDE;
import static androidx.exifinterface.media.ExifInterface.TAG_GPS_LONGITUDE_REF;
import static androidx.exifinterface.media.ExifInterface.TAG_GPS_PROCESSING_METHOD;
import static androidx.exifinterface.media.ExifInterface.TAG_GPS_TIMESTAMP;
import static androidx.exifinterface.media.ExifInterface.TAG_IMAGE_LENGTH;
import static androidx.exifinterface.media.ExifInterface.TAG_IMAGE_WIDTH;
import static androidx.exifinterface.media.ExifInterface.TAG_ISO_SPEED;
import static androidx.exifinterface.media.ExifInterface.TAG_MAKE;
import static androidx.exifinterface.media.ExifInterface.TAG_MODEL;
import static androidx.exifinterface.media.ExifInterface.TAG_ORIENTATION;
import static androidx.exifinterface.media.ExifInterface.TAG_SUBSEC_TIME;
import static androidx.exifinterface.media.ExifInterface.TAG_SUBSEC_TIME_DIGITIZED;
import static androidx.exifinterface.media.ExifInterface.TAG_SUBSEC_TIME_ORIGINAL;
import static androidx.exifinterface.media.ExifInterface.TAG_WHITE_BALANCE;

import androidx.exifinterface.media.ExifInterface;

import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class ExifExtractor {

    static WritableMap extract(String path) throws IOException {
        WritableMap exifData = new WritableNativeMap();

        List<String> attributes = new ArrayList<>(Arrays.asList(
                TAG_APERTURE_VALUE,
                TAG_DATETIME,
                TAG_DATETIME_DIGITIZED,
                TAG_EXPOSURE_TIME,
                TAG_FLASH,
                TAG_FOCAL_LENGTH,
                TAG_GPS_ALTITUDE,
                TAG_GPS_ALTITUDE_REF,
                TAG_GPS_DATESTAMP,
                TAG_GPS_LATITUDE,
                TAG_GPS_LATITUDE_REF,
                TAG_GPS_LONGITUDE,
                TAG_GPS_LONGITUDE_REF,
                TAG_GPS_PROCESSING_METHOD,
                TAG_GPS_TIMESTAMP,
                TAG_IMAGE_LENGTH,
                TAG_IMAGE_WIDTH,
                TAG_ISO_SPEED,
                TAG_MAKE,
                TAG_MODEL,
                TAG_ORIENTATION,
                TAG_SUBSEC_TIME_DIGITIZED,
                TAG_SUBSEC_TIME_ORIGINAL,
                TAG_SUBSEC_TIME,
                TAG_WHITE_BALANCE));

        ExifInterface exif = new ExifInterface(path);

        try {
            GeoDegree geoDegree = new GeoDegree(exif);
            if (geoDegree.getLatitude() != null && geoDegree.getLongitude() != null) {
                exifData.putDouble("Latitude", geoDegree.getLatitude());
                exifData.putDouble("Longitude", geoDegree.getLongitude());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (String attribute : attributes) {
            String value = exif.getAttribute(attribute);
            exifData.putString(attribute, value);
        }

        return exifData;
    }
}