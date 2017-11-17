package com.motorcalculator.dev.detaffmotorcalculator;

/**
 * Created by dev on 11/12/17.
 */

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import org.json.JSONObject;


/**
 * Loads a list of earthquakes by using an AsyncTask to perform the
 * network request to the given URL.
 */
public class MotorQuotationLoader extends AsyncTaskLoader<MotorQuotation> {

    /** Tag for log messages */
    private static final String LOG_TAG = MotorQuotationLoader.class.getName();

    /** Query URL */
    private String mUrl;
    private JSONObject mjsonObject;

    /**
     * Constructs a new {@link MotorQuotationLoader}.
     *
     * @param context of the activity
     * @param url to load data from
     */
    public MotorQuotationLoader(Context context, String url, JSONObject jsonObject) {
        super(context);
        mUrl = url;
        mjsonObject =jsonObject;
    }
    public MotorQuotationLoader(Context context) {
        super(context);

    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public MotorQuotation loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of earthquakes.
        MotorQuotation motorQuotation = QueryUtils.extractSampleQuotation();
//        MotorQuotation motorQuotation = QueryUtils.fetchMotorData(mUrl, mjsonObject);
        Log.e("Motorloader ", String.valueOf(motorQuotation.getBasic_contribution()));
        return  motorQuotation;
    }
}
