package com.example.mapquest;

import static android.content.ContentValues.TAG;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.here.sdk.core.GeoCoordinates;
import com.here.sdk.mapview.MapError;
import com.here.sdk.mapview.MapScene;
import com.here.sdk.mapview.MapScheme;
import com.here.sdk.mapview.MapView;
import com.here.sdk.mapview.VisibilityState;

public class MainActivity extends AppCompatActivity {

    private MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get a MapView instance from the layout.
        mapView = findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);

        mapView.setOnReadyListener(new MapView.OnReadyListener() {
            @Override
            public void onMapViewReady() {
                // This will be called each time after this activity is resumed.
                // It will not be called before the first map scene was loaded.
                // Any code that requires map data may not work as expected beforehand.
                Log.d(TAG, "HERE Rendering Engine attached.");
            }
        });

        loadMapScene();
        mapView.getMapScene().setLayerVisibility(MapScene.Layers.LANDMARKS, VisibilityState.VISIBLE);
        mapView.getMapScene().setLayerVisibility(MapScene.Layers.TRAFFIC_FLOW, VisibilityState.VISIBLE);
        mapView.getMapScene().setLayerVisibility(MapScene.Layers.TRAFFIC_INCIDENTS, VisibilityState.VISIBLE);
        mapView.getMapScene().setLayerVisibility(MapScene.Layers.SAFETY_CAMERAS, VisibilityState.VISIBLE);
        mapView.getMapScene().setLayerVisibility(MapScene.Layers.VEHICLE_RESTRICTIONS, VisibilityState.VISIBLE);
        mapView.getMapScene().setLayerVisibility(MapScene.Layers.EXTRUDED_BUILDINGS, VisibilityState.VISIBLE);
        mapView.getMapScene().setLayerVisibility(MapScene.Layers.BUILDING_FOOTPRINTS, VisibilityState.VISIBLE);

    }

    private void loadMapScene() {
        // Load a scene from the HERE SDK to render the map with a map scheme.
        mapView.getMapScene().loadScene(MapScheme.NORMAL_DAY, new MapScene.LoadSceneCallback() {
            @Override
            public void onLoadScene(@Nullable MapError mapError) {
                if (mapError == null) {
                    double distanceInMeters = 1000 * 10;
                    mapView.getCamera().lookAt(
                            new GeoCoordinates(52.530932, 13.384915), distanceInMeters);
                } else {
                    Log.d(TAG, "Loading map failed: mapError: " + mapError.name());
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

}