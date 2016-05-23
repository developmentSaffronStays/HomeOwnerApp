package vinayapphost3.apphost3;
/* Created By: Vinay Verma, SaffronStays */
/*
* class responsible forRemittance tab/fragment
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


public class RemittanceFrag extends Fragment {

    View v;

    private ListView listViewRemittance;
    private String JSON_STRING;
    private String listingId;

    public RemittanceFrag() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }   //end of onCreate

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_remittance, container, false);
        //constructor for TabAll to get user information
        TabAll activity = (TabAll) getActivity();
        listingId = activity.getListingId();

        listViewRemittance = (ListView) v.findViewById(R.id.listViewRemittance);

        getJSON();

        return v;

    }   //end of onCreateView

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
                showRemittance();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                Log.d("FragRemittance", "doInBackground1: " + listingId);
                String s = rh.sendGetRequestParam(Config.URL_GET_REMITTANCE,listingId);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    //function responsible for displaying data on tab
    private void showRemittance(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        String st_rem_id = null;
        String st_date = null;
        String st_amount = null;
        String st_transDetails = null;
        String st_remMonth = null;

        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                st_rem_id = jo.getString(Config.TAG_REM_ID);
                st_date = jo.getString(Config.TAG_REM_DATE);
                st_amount = jo.getString(Config.TAG_REM_AMOUNT);
                st_remMonth = jo.getString(Config.TAG_REM_MONTH);
                st_transDetails = jo.getString(Config.TAG_REM_TRANS_DETAILS);

                HashMap<String,String> monthlyView = new HashMap<>();
                monthlyView.put(Config.TAG_REM_ID, st_rem_id);
                monthlyView.put(Config.TAG_REM_DATE, st_date);
                monthlyView.put(Config.TAG_REM_AMOUNT, st_amount);
                monthlyView.put(Config.TAG_REM_MONTH, st_remMonth);
                monthlyView.put(Config.TAG_REM_TRANS_DETAILS, st_transDetails);

                list.add(monthlyView);
            };

        } catch (JSONException e) {
            e.printStackTrace();
        }   //end of catch

        //creating list adaptor and adding data to it
        ListAdapter adapter = new SimpleAdapter(
                getActivity(), list, R.layout.list_remittance_item,
                //getActivity(), list, R.layout.list_imp_item,
                new String[]{Config.TAG_REM_DATE,
                        Config.TAG_REM_AMOUNT,
                        Config.TAG_REM_MONTH,
                        Config.TAG_REM_TRANS_DETAILS
                },
                new int[]{R.id.remDate,
                        R.id.remAmount,
                        R.id.remMonth,
                        R.id.transDetails
                });
        //adding list adaptor to list view
        listViewRemittance.setAdapter(adapter);


    }   //end of showRemittaance

}   //end of main class
