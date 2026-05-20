package com.tripmate.ai.ui.map

import android.preference.PreferenceManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polyline

@Composable
fun OsmMapView(
    modifier: Modifier = Modifier,
    currentLocation: GeoPoint?,
    waypoints: List<GeoPoint>,
    onMapReady: (MapView) -> Unit
) {
    val context = LocalContext.current
    
    // Initialize OSMDroid config
    val map = remember {
        Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context))
        MapView(context).apply {
            setMultiTouchControls(true)
            // Default dark theme styling can be achieved via tiles or custom overlay,
            // but for simplicity we use default tiles first.
            
            // Set default zoom
            controller.setZoom(15.0)
        }
    }

    DisposableEffect(map) {
        onMapReady(map)
        onDispose {
            map.onDetach()
        }
    }

    AndroidView(
        modifier = modifier,
        factory = { map },
        update = { mapView ->
            mapView.overlays.clear()

            // Draw route
            if (waypoints.size > 1) {
                val polyline = Polyline().apply {
                    setPoints(waypoints)
                    color = android.graphics.Color.CYAN
                    width = 10f
                }
                mapView.overlays.add(polyline)
            }

            // Draw current location marker
            currentLocation?.let { loc ->
                val marker = Marker(mapView).apply {
                    position = loc
                    setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                    title = "You are here"
                    // Can set custom icon here for futuristic look
                }
                mapView.overlays.add(marker)
                mapView.controller.animateTo(loc)
            }
            
            mapView.invalidate()
        }
    )
}
