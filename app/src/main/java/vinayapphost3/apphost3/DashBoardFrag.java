package vinayapphost3.apphost3;
/* Created By: Vinay Verma, SaffronStays */
/*
* this class is responsible for dashboard tab/fragment
* */
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class DashBoardFrag extends Fragment {

    //variables
    private String JSON_STRING;
    private String listingId;
    private String shareScheme;

    private TextView confBook;
    private TextView totPerRoomNight;
    private TextView totAccCharges;
    private TextView totSaffronFees;
    private TextView expInc;
    private TextView careIncentive;
    private TextView grossMar;
    private TextView improvements;
    private TextView capHost;
    private TextView returnCapInvest;
    private TextView monRemitted;
    private TextView textViewMonOwedToSS_Ho;
    private TextView monOwedToSS_Ho;
    private TextView travelCreditUt;
    private TextView travelCreditUn;
    private TextView travelCreditEx;
    private TextView totalTravelCredits;

    View v;

    public DashBoardFrag() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }//end of onCreate

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        TabAll activity = (TabAll) getActivity();
        listingId = activity.getListingId();
        shareScheme = activity.getShareScheme();
        //dashboard layout is different for PROFIT and REVENUE share
        if(shareScheme.equals("PROFIT"))
        {
            // Inflate the layout for this fragment
            v = inflater.inflate(R.layout.profit_fragment_dash_board, container, false);
        }
        else
        {
            // Inflate the layout for this fragment
            v = inflater.inflate(R.layout.fragment_dash_board, container, false);
        }

        confBook = (TextView) v.findViewById(R.id.confBook);
        totPerRoomNight = (TextView) v.findViewById(R.id.totPerRoomNight);
        totAccCharges = (TextView) v.findViewById(R.id.totAccCharges);
        totSaffronFees = (TextView) v.findViewById(R.id.totSaffronFees);
        expInc = (TextView) v.findViewById(R.id.expInc);
        careIncentive = (TextView) v.findViewById(R.id.careIncentive);
        grossMar = (TextView) v.findViewById(R.id.grossMar);
        improvements = (TextView) v.findViewById(R.id.improvements);
        capHost = (TextView) v.findViewById(R.id.capHost);
        returnCapInvest = (TextView) v.findViewById(R.id.returnCapInvest);
        monRemitted = (TextView) v.findViewById(R.id.monRemitted);
        textViewMonOwedToSS_Ho = (TextView) v.findViewById(R.id.textViewMonOwedToSS_Ho);
        monOwedToSS_Ho = (TextView) v.findViewById(R.id.monOwedToSS_Ho);
        travelCreditUt = (TextView) v.findViewById(R.id.travelCreditUt);
        travelCreditUn = (TextView) v.findViewById(R.id.travelCreditUn);
        travelCreditEx = (TextView) v.findViewById(R.id.travelCreditEx);
        totalTravelCredits = (TextView) v.findViewById(R.id.totalTravelCredits);

        getJSON();

        return v;
    }//end of onCreateView

    //getting data from server as asynchronous task
    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getActivity(),"Fetching Data","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showDashBoard();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(Config.URL_GET_DASHBOARD,listingId);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    //function responsible for displaying data on fragment
    private void showDashBoard(){
        JSONObject jsonObject = null;
        String bookingCount = null;
        String st_totPerRoomNight = null;
        String st_totAccCharges = null;
        String st_ssFees = null;
        String st_expIncurred = null;
        String st_careIncentive = null;
        String st_grossMargin = null;
        String st_impCosts = null;
        String st_capHost = null;
        String st_returnCapInvest = null;
        String st_monRemHomeOwner = null;
        String st_moneyOwedTo = null;
        String st_owedMoney = null;
        String st_travelCreditEx = null;
        String st_travelCreditUt = null;
        String st_travelCreditUn = null;
        String st_totalTravelCredits = null;
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                bookingCount = jo.getString(Config.TAG_BOOKING_COUNT);
                st_totPerRoomNight = jo.getString(Config.TAG_TOTAL_PER_ROOM_NIGHT);
                st_totAccCharges = jo.getString(Config.TAG_TOTAL_ACCOMMODATION_CHARGES);
                st_ssFees = jo.getString(Config.TAG_SAFFRONSTAYS_FEES);
                st_expIncurred = jo.getString(Config.TAG_EXPENSES_INCURRED);
                st_careIncentive= jo.getString(Config.TAG_CARE_INCENTIVE);
                st_grossMargin = jo.getString(Config.TAG_GROSS_MARGIN);
                st_impCosts = jo.getString(Config.TAG_IMPROVEMENTS_COST);
                st_capHost = jo.getString(Config.TAG_CAPEX_HOST);
                st_returnCapInvest = jo.getString(Config.TAG_RETURN_CAP_INVEST);
                st_monRemHomeOwner = jo.getString(Config.TAG_MON_REM_TO_HOME_OWNER);
                st_moneyOwedTo = jo.getString(Config.TAG_MON_OWED_TO);
                st_owedMoney = jo.getString(Config.TAG_OWED_MONEY);
                st_travelCreditEx = jo.getString(Config.TAG_TRAVEL_CREDIT_EX);
                st_travelCreditUt = jo.getString(Config.TAG_TRAVEL_CREDIT_UT);
                st_travelCreditUn = jo.getString(Config.TAG_TRAVEL_CREDIT_UN);
                st_totalTravelCredits = jo.getString(Config.TAG_TOTAL_TRAVEL_CREDITS);

            };

        } catch (JSONException e) {
            e.printStackTrace();
        }   //end of catch

        if(shareScheme.equals("PROFIT"))
        {
            confBook.setText(bookingCount);
            totPerRoomNight.setText(st_totPerRoomNight);
            totAccCharges.setText(st_totAccCharges);
            expInc.setText(st_expIncurred);
            careIncentive.setText(st_careIncentive);
            grossMar.setText(st_grossMargin);
            improvements.setText(st_impCosts);
            capHost.setText(st_capHost);
            returnCapInvest.setText(st_returnCapInvest);
            monRemitted.setText(st_monRemHomeOwner);
            textViewMonOwedToSS_Ho.setText(st_moneyOwedTo);
            monOwedToSS_Ho.setText(st_owedMoney);
            travelCreditUt.setText(st_travelCreditUt);
            travelCreditUn.setText(st_travelCreditUn);
            travelCreditEx.setText(st_travelCreditEx);
            totalTravelCredits.setText(st_totalTravelCredits);
        }
        else{
            confBook.setText(bookingCount);
            totPerRoomNight.setText(st_totPerRoomNight);
            totAccCharges.setText(st_totAccCharges);
            totSaffronFees.setText(st_ssFees);
            expInc.setText(st_expIncurred);
            careIncentive.setText(st_careIncentive);
            grossMar.setText(st_grossMargin);
            improvements.setText(st_impCosts);
            capHost.setText(st_capHost);
            returnCapInvest.setText(st_returnCapInvest);
            monRemitted.setText(st_monRemHomeOwner);
            textViewMonOwedToSS_Ho.setText(st_moneyOwedTo);
            monOwedToSS_Ho.setText(st_owedMoney);
            travelCreditUt.setText(st_travelCreditUt);
            travelCreditUn.setText(st_travelCreditUn);
            travelCreditEx.setText(st_travelCreditEx);
            totalTravelCredits.setText(st_totalTravelCredits);
        }


    }   //end of showDashBoard


}   //end of main class
