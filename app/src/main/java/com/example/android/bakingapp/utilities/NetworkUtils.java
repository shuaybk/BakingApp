package com.example.android.bakingapp.utilities;

public final class NetworkUtils {

    final static String BASE_DATA_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    public static String getDataUrl() {
        return BASE_DATA_URL;
    }
}
