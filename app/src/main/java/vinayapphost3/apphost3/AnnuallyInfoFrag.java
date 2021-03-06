package vinayapphost3.apphost3;
/* Created By: Vinay Verma, SaffronStays */
/*
* class responsible for AnnuallyInformation tab/fragment
* only called in PROFIT share
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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class AnnuallyInfoFrag extends Fragment {

    View v;

    //variables for getting monthly Information

    private ListView listViewAnnually;
    private String JSON_STRING;
    private String listingId;
    private String shareScheme;

    public AnnuallyInfoFrag() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }   //end of onCreate

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //constroctor for TabAll to get user information
        TabAll activity = (TabAll) getActivity();
        listingId = activity.getListingId();
        shareScheme = activity.getShareScheme();

        if(shareScheme.equals("PROFIT"))
        {
            // Inflate the layout for this fragment
            v = inflater.inflate(R.layout.fragment_annually_info, container, false);
        }
        listViewAnnually = (ListView) v.findViewById(R.id.listViewAnnually);
        getJSON();
        return v;
    }   //end od onCreateView

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
                showDashBoardAnnually();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(Config.URL_GET_ANNUALLY_INFO,listingId);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    //function responsible for displaying data on fragment screen
    private void showDashBoardAnnually(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        String mon_st_duration = null;
        String mon_st_bookingCount = null;
        String mon_st_totPerRoomNight = null;
        String mon_st_totAccCharges = null;
        String mon_st_careIncentive = null;
        String mon_st_ssFees = null;
        String mon_st_expIncurred = null;
        String mon_st_monGrossMar = null;
        String mon_st_impCosts = null;
        String mon_st_monRemHomeOwner = null;
        String mon_st_closingBalance = null;
        String mon_st_openingBalance = null;
        String mon_st_ownerTravelCredits = null;

        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                mon_st_duration = jo.getString(Config.TAG_MON_DURATION);
                mon_st_bookingCount = jo.getString(Config.TAG_MON_BOOKING_COUNT);
                mon_st_totPerRoomNight = jo.getString(Config.TAG_MON_TOTAL_PER_ROOM_NIGHT);
                mon_st_totAccCharges = jo.getString(Config.TAG_MON_TOTAL_ACCOMMODATION_CHARGES);
                mon_st_ownerTravelCredits = jo.getString(Config.TAG_MON_OWNER_TRAVEL_CREDITS);
                mon_st_ssFees = jo.getString(Config.TAG_MON_SAFFRONSTAYS_FEES);
                mon_st_expIncurred = jo.getString(Config.TAG_MON_EXPENSES_INCURRED);
                mon_st_careIncentive = jo.getString(Config.TAG_MON_CARE_INCENTIVE);
                mon_st_monGrossMar = jo.getString(Config.TAG_MON_GROSS_MARGIN);
                mon_st_impCosts = jo.getString(Config.TAG_MON_IMPROVEMENTS_COST);
                mon_st_monRemHomeOwner = jo.getString(Config.TAG_MON_MON_REM_TO_HOME_OWNER);
                mon_st_openingBalance = jo.getString(Config.TAG_MON_OPENING_BALANCE);
                mon_st_closingBalance = jo.getString(Config.TAG_MON_CLOSING_BALANCE);

                HashMap<String,String> annuallyView = new HashMap<>();
                annuallyView.put(Config.TAG_MON_DURATION, mon_st_duration);
                annuallyView.put(Config.TAG_MON_OPENING_BALANCE, mon_st_openingBalance);
                annuallyView.put(Config.TAG_MON_BOOKING_COUNT, mon_st_bookingCount);
                annuallyView.put(Config.TAG_MON_TOTAL_PER_ROOM_NIGHT, mon_st_totPerRoomNight);
                annuallyView.put(Config.TAG_MON_TOTAL_ACCOMMODATION_CHARGES, mon_st_totAccCharges);
                annuallyView.put(Config.TAG_MON_OWNER_TRAVEL_CREDITS, mon_st_ownerTravelCredits);
                annuallyView.put(Config.TAG_MON_SAFFRONSTAYS_FEES, mon_st_ssFees);
                annuallyView.put(Config.TAG_MON_EXPENSES_INCURRED, mon_st_expIncurred);
                annuallyView.put(Config.TAG_MON_CARE_INCENTIVE, mon_st_careIncentive);
                annuallyView.put(Config.TAG_MON_GROSS_MARGIN, mon_st_monGrossMar);
                annuallyView.put(Config.TAG_MON_IMPROVEMENTS_COST, mon_st_impCosts);
                annuallyView.put(Config.TAG_MON_MON_REM_TO_HOME_OWNER, mon_st_monRemHomeOwner);
                annuallyView.put(Config.TAG_MON_CLOSING_BALANCE, mon_st_closingBalance);
                list.add(annuallyView);
            };

        } catch (JSONException e) {
            e.printStackTrace();
        }   //end of catch

        if(shareScheme.equals("PROFIT")) {
            //creating list adaptor and adding data to it
            ListAdapter adapter = new SimpleAdapter(
                    getActivity(), list, R.layout.list_annually_item,
                    //getActivity(), list, R.layout.list_imp_item,
                    new String[]{Config.TAG_MON_DURATION,
                            Config.TAG_MON_OPENING_BALANCE,
                            Config.TAG_MON_BOOKING_COUNT,
                            Config.TAG_MON_TOTAL_PER_ROOM_NIGHT,
                            Config.TAG_MON_TOTAL_ACCOMMODATION_CHARGES,
                            Config.TAG_MON_OWNER_TRAVEL_CREDITS,
                            Config.TAG_MON_EXPENSES_INCURRED,
                            Config.TAG_MON_CARE_INCENTIVE,
                            Config.TAG_MON_IMPROVEMENTS_COST,
                            Config.TAG_MON_GROSS_MARGIN,
                            Config.TAG_MON_MON_REM_TO_HOME_OWNER,
                            Config.TAG_MON_CLOSING_BALANCE},
                    new int[]{R.id.duration,
                            R.id.monOpeningBalance,
                            R.id.monBookings,
                            R.id.monTotPerRoomNight,
                            R.id.monAccCharges,
                            R.id.monOwnerTravelCredits,
                            R.id.monExpInc,
                            R.id.monCareIncentive,
                            R.id.monImpCosts,
                            R.id.monGrossMar,
                            R.id.monMonRemitted,
                            R.id.monClosingBalance});
            listViewAnnually.setAdapter(adapter);       //adding list adapter to view
        }

    }   //end of showDashBoardMonthly

}   //end of main class
