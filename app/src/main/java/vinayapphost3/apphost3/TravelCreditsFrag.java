package vinayapphost3.apphost3;
/* Created By: Vinay Verma, SaffronStays */
/*
* class responsible for travel credit tab/fragment
* */
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class TravelCreditsFrag extends Fragment {

    View v;

    private String shareScheme;

    private ListView listViewTravelCredits;
    private String JSON_STRING;
    private String listingId;
    public Button bUnutilised;
    public Button bUtilised;
    public Button bExpired;
    public TextView textViewMessage;

    public float st_totalCreditsLeft = 0;
    public float st_totalCreditsUtilised = 0;
    public float st_totalCreditsExpired = 0;

    public TravelCreditsFrag() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_travel_credits, container, false);
        //constructor for TabAll to get user information
        TabAll activity = (TabAll) getActivity();
        listingId = activity.getListingId();
        shareScheme = activity.getShareScheme();

        listViewTravelCredits = (ListView) v.findViewById(R.id.listViewTravelCredits);

        getJSON();

        bUtilised = (Button)v.findViewById(R.id.bUtilised);

        bUnutilised = (Button)v.findViewById(R.id.bUnutilised);

        bExpired = (Button)v.findViewById(R.id.bExpired);

        textViewMessage = (TextView)v.findViewById(R.id.textViewMessage);

        return v;
    }

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
                showDashBoardMonthly();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(Config.URL_GET_TRAVEL_CREDITS,listingId);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    //function responsible for displaying data on tab
    private void showDashBoardMonthly(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> listUt = new ArrayList<HashMap<String, String>>();
        ArrayList<HashMap<String,String>> listUn = new ArrayList<HashMap<String, String>>();
        ArrayList<HashMap<String,String>> listEx = new ArrayList<HashMap<String, String>>();
        String st_bookingIdUt = null;
        String st_creditAmountUt = null;
        String st_expiryDateUt = null;
        String st_bookingIdUn = null;
        String st_creditAmountUn = null;
        String st_expiryDateUn = null;
        String st_bookingIdEx = null;
        String st_creditAmountEx = null;
        String st_expiryDateEx = null;

        String st_bookingId = null;
        String st_expiryDate = null;
        String st_expired = null;
        String st_credits = null;

        st_totalCreditsLeft = 0;
        st_totalCreditsUtilised = 0;
        st_totalCreditsExpired = 0;

        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                st_expired = jo.getString("expired");
                st_bookingId = jo.getString("bookingId");
                st_expiryDate = jo.getString("expiryDate");
                st_creditAmountUt = jo.getString("creditAmountUt");
                st_creditAmountUn = jo.getString("creditAmountUn");
                st_creditAmountEx = jo.getString("creditAmountEx");
                st_credits = jo.getString("creditAmount");

                if(st_expired.equals("N"))
                {
                    if(st_creditAmountUt.equals("ZERO"))
                    {
                        Log.d("travelCredits fragment", "utilised is zero: so unutilised section");
                        HashMap<String,String> mapUn = new HashMap<>();
                        mapUn.put(Config.TAG_CREDIT_BOOKING_ID, st_bookingId);
                        mapUn.put(Config.TAG_CREDIT_AMOUNT, st_creditAmountUn+" / "+st_credits);
                        mapUn.put(Config.TAG_EXPIRY_DATE, st_expiryDate);
                        listUn.add(mapUn);
                        st_totalCreditsLeft = st_totalCreditsLeft + jo.getInt("creditAmountUn");
                    }
                   else if(st_creditAmountUn.equals("ZERO"))
                    {
                        Log.d("travelCredits fragment", "unutilised is zero: so utilised section");
                        HashMap<String,String> mapUt = new HashMap<>();
                        mapUt.put(Config.TAG_CREDIT_BOOKING_ID, st_bookingId);
                        mapUt.put(Config.TAG_CREDIT_AMOUNT, st_creditAmountUt+" / "+st_credits);
                        mapUt.put(Config.TAG_EXPIRY_DATE, st_expiryDate);
                        listUt.add(mapUt);
                        st_totalCreditsUtilised = st_totalCreditsUtilised + jo.getInt("creditAmountUt");
                    }
                    else
                    {
                        HashMap<String,String> mapUn = new HashMap<>();
                        mapUn.put(Config.TAG_CREDIT_BOOKING_ID, st_bookingId);
                        mapUn.put(Config.TAG_CREDIT_AMOUNT, st_creditAmountUn+" / "+st_credits);
                        mapUn.put(Config.TAG_EXPIRY_DATE, st_expiryDate);
                        listUn.add(mapUn);

                        HashMap<String,String> mapUt = new HashMap<>();
                        mapUt.put(Config.TAG_CREDIT_BOOKING_ID, st_bookingId);
                        mapUt.put(Config.TAG_CREDIT_AMOUNT, st_creditAmountUt+" / "+st_credits);
                        mapUt.put(Config.TAG_EXPIRY_DATE, st_expiryDate);
                        listUt.add(mapUt);

                        st_totalCreditsLeft = st_totalCreditsLeft + jo.getInt("creditAmountUn");
                        st_totalCreditsUtilised = st_totalCreditsUtilised + jo.getInt("creditAmountUt");
                    }

                }
                else if(st_expired.equals("Y"))
                {
                    HashMap<String,String> mapEx = new HashMap<>();
                    mapEx.put(Config.TAG_CREDIT_BOOKING_ID, st_bookingId);
                    mapEx.put(Config.TAG_CREDIT_AMOUNT, st_creditAmountEx+" / "+st_credits);
                    mapEx.put(Config.TAG_EXPIRY_DATE, st_expiryDate);
                    listEx.add(mapEx);
                    st_totalCreditsExpired = st_totalCreditsExpired + jo.getInt("creditAmountEx");
                }
                //list.add(monthlyView);
            };

            textViewMessage.setText("Total Unutilised Credits: " + st_totalCreditsLeft);
            bUtilised.setTextColor(v.getResources().getColor((R.color.colorPrimary)));
            bUnutilised.setTextColor(v.getResources().getColor((R.color.colorAccent)));
            bExpired.setTextColor(v.getResources().getColor((R.color.colorPrimary)));
        } catch (JSONException e) {
            e.printStackTrace();
        }   //end of catch

        final ListAdapter adapterUt = new SimpleAdapter(
                getActivity(), listUt, R.layout.list_travel_credits_item,
                //getActivity(), list, R.layout.list_imp_item,
                new String[]{Config.TAG_CREDIT_BOOKING_ID,
                        Config.TAG_CREDIT_AMOUNT,
                        Config.TAG_EXPIRY_DATE
                },
                new int[]{R.id.creditBookingId,
                        R.id.creditAmount,
                        R.id.expiryDate
                });

        /**buttons for utilised, unutilised and expired credits**/
        bUtilised.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {
                listViewTravelCredits.setAdapter(adapterUt);
                textViewMessage.setText("Total Utilised Credits: " + st_totalCreditsUtilised);

                bUtilised.setTextColor(v.getResources().getColor((R.color.colorAccent)));
                bUnutilised.setTextColor(v.getResources().getColor((R.color.colorPrimary)));
                bExpired.setTextColor(v.getResources().getColor((R.color.colorPrimary)));
            }
        });

        final ListAdapter adapterUn = new SimpleAdapter(
                getActivity(), listUn, R.layout.list_travel_credits_item,
                new String[]{Config.TAG_CREDIT_BOOKING_ID,
                        Config.TAG_CREDIT_AMOUNT,
                        Config.TAG_EXPIRY_DATE
                },
                new int[]{R.id.creditBookingId,
                        R.id.creditAmount,
                        R.id.expiryDate
                });
        bUnutilised.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v2) {

                listViewTravelCredits.setAdapter(adapterUn);
                textViewMessage.setText("Total Unutilised Credits: " + st_totalCreditsLeft);

                bUtilised.setTextColor(getActivity().getResources().getColor((R.color.colorPrimary)));
                bUnutilised.setTextColor(v.getResources().getColor((R.color.colorAccent)));
                bExpired.setTextColor(v.getResources().getColor((R.color.colorPrimary)));
            }
        });

        final ListAdapter adapterEx = new SimpleAdapter(
                getActivity(), listEx, R.layout.list_travel_credits_item,
                new String[]{Config.TAG_CREDIT_BOOKING_ID,
                        Config.TAG_CREDIT_AMOUNT,
                        Config.TAG_EXPIRY_DATE
                },
                new int[]{R.id.creditBookingId,
                        R.id.creditAmount,
                        R.id.expiryDate
                });
        bExpired.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v3) {
                listViewTravelCredits.setAdapter(adapterEx);
                textViewMessage.setText("Total Expired Credits: " + st_totalCreditsExpired);

                bUtilised.setTextColor(v.getResources().getColor((R.color.colorPrimary)));
                bUnutilised.setTextColor(v.getResources().getColor((R.color.colorPrimary)));
                bExpired.setTextColor(v.getResources().getColor((R.color.colorAccent)));
            }
        });

        listViewTravelCredits.setAdapter(adapterUn);
    }   //end of showRemittance

}
