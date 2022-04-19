package land.map.common

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import land.map.amir.R
import land.map.feature.domain.model.CarLocationDto

/**
 * Util kotlin file
 * The most used methods are placed in this file
 */

//This function bitmap of vector
 fun extractBitMapOfVector(context: Context, vectorResId: Int): BitmapDescriptor? {
    // below line is use to generate a drawable.
    val drawable = ContextCompat.getDrawable(context, vectorResId)

    // below line is use to set bounds to our vector drawable.
    drawable!!.setBounds(
        0,
        0,
        drawable.intrinsicWidth,
        drawable.intrinsicHeight
    )

    // below line is use to create a bitmap for our
    // drawable which we have added.
    val bitmap = Bitmap.createBitmap(
        drawable.intrinsicWidth,
        drawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )

    // below line is use to add bitmap in our canvas.
    val canvas = Canvas(bitmap)

    // below line is use to draw our
    // vector drawable in canvas.
    drawable.draw(canvas)

    // after generating our bitmap we are returning our bitmap.
    return BitmapDescriptorFactory.fromBitmap(bitmap)
}

//This function separates the pooling car from the taxi and displays a special icon for each
 fun showLocationOfCar(
    carLocation: CarLocationDto,
    simplePoint: LatLng,
    map:GoogleMap,
    context: Context,
    zoomLevel:Float
) {
    if (carLocation.fleetType == "POOLING") {
        // if type was pooling select green car
        map.addMarker(
            MarkerOptions().icon(
                context?.let { item ->
                    extractBitMapOfVector(
                        item,
                        R.drawable.ic_car_green
                    )
                }).position(
                simplePoint
            )
        )
    } else {
        // if type was taxi select green car
        map.addMarker(
            MarkerOptions().icon(
                context?.let { item ->
                    extractBitMapOfVector(
                        item,
                        R.drawable.ic_car_yellow
                    )
                }
            ).position(
                simplePoint
            )
        )
    }

    map.moveCamera(
        CameraUpdateFactory.newLatLngZoom(
            simplePoint,zoomLevel
        )
    )
}