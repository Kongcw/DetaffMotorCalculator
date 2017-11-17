//package com.motorcalculator.dev.detaffmotorcalculator;
//
//import android.app.LoaderManager;
//import android.content.Intent;
//import android.content.Loader;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//public class OutputActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<MotorQuotation>{
//
//
//    Intent intent ;
//    String URl;
//    JSONObject jsonObject ;
//    TextView value_grosspremium;
//    TextView value_after_rebate;
//    TextView value_fees;
//    TextView value_after_gst;
//    TextView value_net_premium;
//
//    private static final int MotorLoaderID = 1;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_output);
//
//         value_grosspremium = (TextView)findViewById(R.id.value_GrossPremium);
//         value_after_rebate = (TextView)findViewById(R.id.value__AfterRebate);
//         value_fees = (TextView)findViewById(R.id.value_Fees);
//         value_after_gst = (TextView)findViewById(R.id.value_AfterGST);
//         value_net_premium = (TextView)findViewById(R.id.value_NetPremium);
//         intent = getIntent();
//         URl = intent.getStringExtra("MOTOR_URL");
//         Button btn_done = (Button)findViewById(R.id.Done);
//         btn_done.setOnClickListener(new View.OnClickListener(){
//
//            @Override
//            public void onClick(View view) {
////                Intent intent = new Intent(OutputActivity.this,InputActivity.class);
////                startActivity(intent);
//                    OutputActivity.super.onBackPressed();
//                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
//
//
//            }
//
//
//        });
//
//
//
//        try {
//             jsonObject = new JSONObject(intent.getStringExtra("JSONObjectInput"));
//
//
//        LoaderManager loaderManager = getLoaderManager();
//        loaderManager.initLoader(MotorLoaderID, null, this);
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    @Override
//    public Loader<MotorQuotation> onCreateLoader(int i, Bundle bundle) {
//
//        return new MotorQuotationLoader(OutputActivity.this,URl,jsonObject);
//    }
//
//    @Override
//    public void onLoadFinished(Loader<MotorQuotation> loader, MotorQuotation motorQuotation) {
//            Double grosspremium = motorQuotation.getBasic_contribution();
//            Double after_rebate = motorQuotation.getGross_contribution();
//            Double fees = motorQuotation.getContri_after_rebate();
//            Double after_gst = motorQuotation.getGst();
//            Double net_premium = motorQuotation.getTotal_contribution();
//
//            value_grosspremium.setText(String.valueOf(grosspremium));
//            value_after_rebate.setText(String.valueOf(after_rebate));
//            value_fees.setText(String.valueOf(fees));
//            value_after_gst.setText(String.valueOf(after_gst));
//            value_net_premium.setText(String.valueOf(net_premium));
//
//
//    }
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
//    }
//    @Override
//    public void onLoaderReset(Loader<MotorQuotation> loader) {
//
//    }
//}
