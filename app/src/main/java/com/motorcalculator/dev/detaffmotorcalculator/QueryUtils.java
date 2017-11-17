package com.motorcalculator.dev.detaffmotorcalculator;

/**
 * Created by dev on 11/12/17.
 */
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import android.util.Log;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryUtils {

    /** Sample JSON response for a USGS query */
    private static final String LOG_TAG ="MOTOR_RS";
    private static  final String SAMPLE_JSON_RESPONSE= "{\n" +
            "    \"message\": null,\n" +
            "    \"status\": \"OK\",\n" +
            "    \"data\": {\n" +
            "        \"tx\": {\n" +
            "            \"ref\": \"MtoI2Qg8M-leymx2rgKBXQ\"\n" +
            "        },\n" +
            "        \"drivers\": [\n" +
            "            {\n" +
            "                \"name\": \"Pemandu Berhemah AL Pemandu Skema\",\n" +
            "                \"marital\": \"Single\",\n" +
            "                \"gender\": \"Male\",\n" +
            "                \"email\": \"sam.aljufri@gmail.com\",\n" +
            "                \"birth_date\": \"1989-06-17\",\n" +
            "                \"driver_no\": \"0\",\n" +
            "                \"residential_address1\": \"500, Jalan Mandiri 18\",\n" +
            "                \"residential_address2\": \"Rapat Jelapang Baru\",\n" +
            "                \"residential_address3\": \"30020 Perak\",\n" +
            "                \"mobile_no\": \"0131415926\",\n" +
            "                \"residential_state\": \"Perak\",\n" +
            "                \"residential_postcode\": \"30020\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"vehicle\": {\n" +
            "            \"make\": \"29\",\n" +
            "            \"variant\": \"HHV06A\",\n" +
            "            \"model\": \"11\",\n" +
            "            \"chassis_no\": \"ACR500001615\",\n" +
            "            \"make_year\": \"2006\"\n" +
            "        },\n" +
            "        \"quotation\": {\n" +
            "            \"pa\": {\n" +
            "                \"pa_gross\": \"61.00\",\n" +
            "                \"pa_after_rebate\": \"54.90\",\n" +
            "                \"pa_fee\": \"4.00\",\n" +
            "                \"pa_after_gst\": \"62.43\",\n" +
            "                \"pa_net_premium\": \"72.43\"\n" +
            "            },\n" +
            "            \"basic_contribution\": \"1240.23\",\n" +
            "            \"ncd_pct\": \"55.00\",\n" +
            "            \"ncd_amt\": \"524.45\",\n" +
            "            \"proposed_sum_covered\": \"16500.00\",\n" +
            "            \"sum_covered_min\": \"15750.00\",\n" +
            "            \"sum_covered_max\": \"19500.00\",\n" +
            "            \"gross_contribution\": \"761.00\",\n" +
            "            \"rebate_pct\": \"10.00\",\n" +
            "            \"contribution_after_rebate_amt\": \"684.90\",\n" +
            "            \"gst_pct\": \"6.00\",\n" +
            "            \"gst_amt\": \"41.10\",\n" +
            "            \"stamp_duty\": \"10.00\",\n" +
            "            \"total_contribution\": \"736.00\",\n" +
            "            \"insured_period\": \"NOTIMPL\",\n" +
            "            \"coverage_type\": \"MT\",\n" +
            "            \"addons\": [\n" +
            "                {\n" +
            "                    \"code\": \"B089\",\n" +
            "                    \"name\": \"Windscreen Coverage\",\n" +
            "                    \"amount\": \"120.00\"\n" +
            "                }\n" +
            "            ],\n" +
            "            \"total_payable\": \"808.43\"\n" +
            "        },\n" +
            "        \"addons_list\": [\n" +
            "            {\n" +
            "                \"code\": \"C57A\",\n" +
            "                \"name\": \"Basic Flood Cover\",\n" +
            "                \"display_order\": \"1\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"code\": \"B057\",\n" +
            "                \"name\": \"Extended Flood Cover\",\n" +
            "                \"display_order\": \"2\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"code\": \"B072\",\n" +
            "                \"name\": \"Legal Liability of Passenger for Acts of Negligence Coverage\",\n" +
            "                \"display_order\": \"3\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"code\": \"B089\",\n" +
            "                \"name\": \"Windscreen Coverage\",\n" +
            "                \"display_order\": \"4\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"code\": \"MT_CART\",\n" +
            "                \"name\": \"Compensation for Assessed Repair Time\",\n" +
            "                \"display_order\": \"5\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"code\": \"P10B\",\n" +
            "                \"name\": \"Loss of Use (LOU)\",\n" +
            "                \"display_order\": \"6\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"code\": \"PA\",\n" +
            "                \"name\": \"Personal Accident\",\n" +
            "                \"display_order\": \"7\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"quotation_no\": \"DS22017M-00171561-001\"\n" +
            "    }\n" +
            "}";

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    public static MotorQuotation fetchMotorData(String requestUrl, JSONObject jsonObject) {
        // Create URL object
        URL url;




        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;

        Log.e( "fetchMotorData JSON", jsonObject.toString());
        try {
            url = new URL(requestUrl);
            jsonResponse = makePOSTHttpRequest(url,jsonObject);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link Earthquake}s
         MotorQuotation motorQuotation = extractQuotationJSON(jsonResponse);

        // Return the list of {@link Earthquake}s
        return motorQuotation;
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makePOSTHttpRequest(URL url,JSONObject jsonObject) throws IOException {
        String jsonResponse = "";
        Log.e("url", url.toString());

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }
        Log.e("URL fetch", url.toString());
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            Log.e("URL fetch", url.toString());
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Accept","application/json");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("remote_user","UamRMUsLntmjDZFq4jdg");
            urlConnection.setRequestProperty("cache-control","no-cache");
            urlConnection.connect();
//            Log.e(LOG_TAG, " response code: " + urlConnection.getResponseCode());
            OutputStream os = urlConnection.getOutputStream();

            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(jsonObject.toString());

            writer.flush();
            writer.close();
//            Log.e("writter", writer.toString());
            os.close();

            Log.e(LOG_TAG, " response code: " + urlConnection.getResponseCode());

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200 ) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
                Log.e(LOG_TAG, "Success  " + jsonResponse);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the MotorRS JSON results.", e);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }

        Log.e(LOG_TAG, "json response  " + jsonResponse);
        return SAMPLE_JSON_RESPONSE;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    public static String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while(itr.hasNext()){

            String key= itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
    }
    /**
     *
     * get samepl jsonresponse
     */
    public static MotorQuotation extractSampleQuotation() {
        MotorQuotation motorQuotation = null;


        try {

            JSONObject baseJsonResponse = new JSONObject(SAMPLE_JSON_RESPONSE);

            // Extract the JSONArray associated with the key called "features",
            // which represents a list of features (or earthquakes).
            Log.e("Sample json reponse", SAMPLE_JSON_RESPONSE);
            JSONObject dataObject = baseJsonResponse.getJSONObject("data");
            JSONObject quotationObject = dataObject.getJSONObject("quotation");

            JSONObject properties = quotationObject.getJSONObject("pa");

            String message = baseJsonResponse.getString("message");
            String status = baseJsonResponse.getString("status");

            Double basic_contribution = quotationObject.getDouble("basic_contribution");

            Double gross_contribution = quotationObject.getDouble("gross_contribution");

            Double contribution_after_rebate_amt = quotationObject.getDouble("contribution_after_rebate_amt");

            Double gst_amt = quotationObject.getDouble("gst_amt");

            Double total_contribution = quotationObject.getDouble("total_contribution");

//            Double gross = properties.getDouble("pa_gross");
//
//            Double after_rebate = properties.getDouble("pa_after_rebate");
//
//            Double fee = properties.getDouble("pa_fee");
//
//            Double after_gst = properties.getDouble("pa_after_gst");
//
//            Double net_premium = properties.getDouble("pa_net_premium");

//
//            "basic_contribution": "1240.23",
//                    " +
//            "            \"ncd_pct\": \"55.00\",\n" +
//                    "            \"ncd_amt\": \"524.45\",\n" +
//                    "            \"proposed_sum_covered\": \"16500.00\",\n" +
//                    "            \"sum_covered_min\": \"15750.00\",\n" +
//                    "            \"sum_covered_max\": \"19500.00\",\n" +
//                    "            \"gross_contribution\": \"761.00\",\n" +
//                    "            \"rebate_pct\": \"10.00\",\n" +
//                    "            \"contribution_after_rebate_amt\": \"684.90\",\n" +
//                    "            \"gst_pct\": \"6.00\",\n" +
//                    "            \"gst_amt\": \"41.10\",\n" +
//                    "            \"stamp_duty\": \"10.00\",\n" +
//                    "            \"total_contribution\": \"736.00\",\n" +
//                    "            \"insured_period\": \"NOTIMPL\",\n" +
//                    "            \"coverage_type\": \"MT\",\n" +
//                    "            \"addons\": [\n"
            Log.e("motorquotation", basic_contribution +" "+ gross_contribution);
            motorQuotation = new MotorQuotation(message, status, basic_contribution, gross_contribution, contribution_after_rebate_amt, gst_amt, total_contribution);

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the MotorRS JSON results", e);
        }

        // Return the list of earthquakes
        return motorQuotation;
    }
//TODO
    public static MotorQuotation extractQuotationJSON(String MotorJSON) {
        MotorQuotation motorQuotation = null;
        if (TextUtils.isEmpty(MotorJSON)) {
            return null;
        }
        // Create an empty ArrayList that we can start adding earthquakes to
        // List<String> MotorRS = new ArrayList<String>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.

        try {

            // Create a JSONObject from the SAMPLE_JSON_RESPONSE string
            JSONObject baseJsonResponse = new JSONObject(MotorJSON);
            //System.out.print(baseJsonResponse);
//            Log.e("basereposnse", baseJsonResponse.toString());

            String status = baseJsonResponse.getString("status");
            String message = baseJsonResponse.getString("message");
            Log.e("query status" , status);
            if (status == "ERROR") {
                motorQuotation = new MotorQuotation(message, status, 0, 0, 0, 0, 0);
                return motorQuotation;
            } else {

                JSONObject dataObject = baseJsonResponse.getJSONObject("data");
                JSONObject quotationObject = dataObject.getJSONObject("quotation");


                JSONObject properties = quotationObject.getJSONObject("pa");


                Double gross = properties.getDouble("pa_gross");


                Double after_rebate = properties.getDouble("pa_after_rebate");


                Double fee = properties.getDouble("pa_fee");


                Double after_gst = properties.getDouble("pa_after_gst");

                Double net_premium = properties.getDouble("pa_net_premium");


                motorQuotation = new MotorQuotation(message, status, gross, after_rebate, fee, after_gst, net_premium);

            }

            //  }
        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the MotorRS JSON results", e);
        }

        // Return the list of earthquakes
        return motorQuotation;
    }

    }

