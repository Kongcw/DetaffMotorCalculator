package com.motorcalculator.dev.detaffmotorcalculator;

import android.app.LoaderManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class InputActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<MotorQuotation> {

    JSONObject jsonObject;

    TextView value_grosspremium;
    TextView value_after_rebate;
    TextView value_fees;
    TextView value_after_gst;
    TextView value_net_premium;

    Spinner edittxtCoverage;
    EditText edittxtIDType;
    EditText edittxtIDNo;
    EditText edittxrPostCode;
    TextView edittxtVehicleRegNo;
    EditText edittxtEmail;

    String coverage;
    String idType;
    String idNo;
    String postcode;
    String vehRegNo;
    String email;
    private static final int MotorLoaderID = 1;
    private static final String UAT_MOTOR_URL = "http://10.250.104.167:23525/MotorRs/api/v1/motor/quote";

    TextView textview;
    JSONObject input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_input);

        value_grosspremium = (TextView) findViewById(R.id.value_GrossPremium);
        value_after_rebate = (TextView) findViewById(R.id.value__AfterRebate);
        value_fees = (TextView) findViewById(R.id.value_Fees);
        value_after_gst = (TextView) findViewById(R.id.value_AfterGST);
        value_net_premium = (TextView) findViewById(R.id.value_NetPremium);
        edittxtCoverage = (Spinner) findViewById(R.id.editCoverage);
//        edittxtIDType = (EditText) findViewById(R.id.editIDType);
        edittxtIDNo = (EditText) findViewById(R.id.editIDNo);
        edittxrPostCode = (EditText) findViewById(R.id.editPostcode);
        edittxtVehicleRegNo = (TextView) findViewById(R.id.editVehicleRegNo);
        edittxtEmail = (EditText) findViewById(R.id.editEmail);

        ArrayAdapter<CharSequence> coverageadapter = ArrayAdapter.createFromResource(this,
                R.array.coveragesoption, R.layout.spinner);

        coverageadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        edittxtCoverage.setAdapter(coverageadapter);
        Button calculate = (Button) findViewById(R.id.button_calculate);
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoaderManager loaderManager = getLoaderManager();
                loaderManager.initLoader(MotorLoaderID, null, InputActivity.this);
            }
        });

    }


//
//    public void calculate(View view) {
//
//
//        jsonObject = getInput();
//        LoaderManager loaderManager = getLoaderManager();
//        loaderManager.initLoader(MotorLoaderID,null,this);
//////        Intent outputactivity = new Intent(InputActivity.this, OutputActivity.class);
////        outputactivity.putExtra("JSONObjectInput", getInput().toString());
////        outputactivity.putExtra("MOTOR_URL", UAT_MOTOR_URL);
////        PendingIntent pendingIntent =
////                TaskStackBuilder.create(InputActivity.this)
////                        // add all of DetailsActivity's parents to the stack,
////                        // followed by DetailsActivity itself
////                        .addNextIntentWithParentStack(outputactivity)
////                        .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
////        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
////        builder.setContentIntent(pendingIntent);
//
////        startActivity(outputactivity);
////        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
//
//    }

    public  JSONObject getInput() {

        coverage = "TP";//edittxtCoverage.getText().toString();
        idType = edittxtIDType.getText().toString();
        idNo = edittxtIDNo.getText().toString();
        postcode = edittxrPostCode.getText().toString();
        vehRegNo = edittxtVehicleRegNo.getText().toString();
        email = edittxtEmail.getText().toString();
        JSONObject getInputJSONObject = new JSONObject();
        JSONObject driverdetails = new JSONObject();
        JSONArray emaillist = new JSONArray();




        try {
            driverdetails.put("email",email );
            emaillist.put(driverdetails);
            getInputJSONObject.put("coverage_type", coverage);
            getInputJSONObject.put("id_type", idType);
            getInputJSONObject.put("id_value", idNo);
            getInputJSONObject.put("vehicle_postcode", postcode);
            getInputJSONObject.put("vehicle_reg_no", vehRegNo);
            getInputJSONObject.put("drivers", emaillist);

            Log.e("getinputjson" , getInputJSONObject.toString());

//        {"coverage_type":"MT","id_type":"1","id_value":"890617025847",
//                "vehicle_postcode":"50000","vehicle_reg_no":"WUV6152",
//                "vehicle":{},"drivers":[{"email":"sam.aljufri@gmail.com"}],
//            "tx":{"ref":null},"addons":[{"code":"caps","value":true}]}
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getInputJSONObject;
    }
    @Override
    public Loader<MotorQuotation> onCreateLoader(int i, Bundle bundle) {

        return new MotorQuotationLoader(InputActivity.this,UAT_MOTOR_URL,jsonObject);
    }

    @Override
    public void onLoadFinished(Loader<MotorQuotation> loader, MotorQuotation motorQuotation) {

        Log.e("onload finish", String.valueOf(motorQuotation.getBasic_contribution()));
        Double basic_contribution = motorQuotation.getBasic_contribution();
        Double gross_contribution = motorQuotation.getGross_contribution();
        Double contri_after_rebate = motorQuotation.getContri_after_rebate();
        Double gst = motorQuotation.getGst();
        Double total_contribution = motorQuotation.getTotal_contribution();

        value_grosspremium.setText(String.valueOf(basic_contribution));
        value_after_rebate.setText(String.valueOf(gross_contribution));
        value_fees.setText(String.valueOf(contri_after_rebate));
        value_after_gst.setText(String.valueOf(gst));
        value_net_premium.setText(String.valueOf(total_contribution));


    }

    @Override
    public void onLoaderReset(Loader<MotorQuotation> loader) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }





}
