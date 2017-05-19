package com.surya.findme;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Surya on 19-05-2017.
 */

public class Utils {

    public static void openMaps(Context context, LatLng latLng){
        String geoUri = "http://maps.google.com/maps?q=loc:" + latLng.latitude + "," + latLng.longitude ;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
        context.startActivity(intent);
    }
}
