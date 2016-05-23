package vinayapphost3.apphost3;
/* Created By: Vinay Verma, SaffronStays */
/*
* class responsible for Expense tab/fragment
* very complex class
* */

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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


public class ExpenseFrag extends Fragment {

    private ListView listView;
    private ListView listViewApp;

    private String JSON_STRING;
    private String listingId;

    String app_status=null;
    View v;

    public ExpenseFrag() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }   //end onCreate

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_expense, container, false);

        //constructor for TabAll to get user information
        TabAll activity = (TabAll) getActivity();
        listingId = activity.getListingId();
        listView = (ListView) v.findViewById(R.id.listViewImp);
        listViewApp = (ListView) v.findViewById(R.id.listViewAppImp);

        getJSON();
        return v;
    }   //end of onCreateView

    //function responsible for changing layout dynamically
    public void setViewLayout(int id){
        LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(id, null);
        ViewGroup rootView = (ViewGroup)getView();
        rootView.removeAllViews();
        rootView.addView(v);
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
                showImprovements();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(Config.URL_GET_ALL_IMP,listingId);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    //function responsible for displaying data on tab
    private void showImprovements(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        ArrayList<HashMap<String,String>> not_list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String id = jo.getString(Config.TAG_ID);
                String ved = jo.getString(Config.TAG_VED);
                String nature = jo.getString(Config.TAG_NATURE);
                String paidBy = jo.getString(Config.TAG_PAID_BY);
                String impType = jo.getString(Config.TAG_TYPE);
                String propSec = jo.getString(Config.TAG_PROPSEC);
                String description = jo.getString(Config.TAG_DESCRIPTION);
                String status = jo.getString(Config.TAG_STATUS);
                app_status = jo.getString(Config.TAG_STATUS);
                String date = jo.getString(Config.TAG_DATE);
                String cost = jo.getString(Config.TAG_COST);
                String impId = jo.getString(Config.TAG_IMP_ID);

                HashMap<String,String> improvements = new HashMap<>();
                improvements.put(Config.TAG_ID, id);
                improvements.put(Config.TAG_VED, ved);
                improvements.put(Config.TAG_NATURE, nature);
                improvements.put(Config.TAG_PAID_BY, paidBy);
                improvements.put(Config.TAG_TYPE, impType);
                improvements.put(Config.TAG_PROPSEC, propSec);
                improvements.put(Config.TAG_DESCRIPTION, description);
                improvements.put(Config.TAG_STATUS, status);
                improvements.put(Config.TAG_DATE, date);
                improvements.put(Config.TAG_COST, cost);
                improvements.put(Config.TAG_IMP_ID, impId);


                if(app_status.equals("PENDING FOR APPROVAL")){
                    list.add(improvements);
                    setViewLayout(R.layout.fragment_expense_pending);
                    listView = (ListView) v.findViewById(R.id.listViewImp);
                    listViewApp = (ListView) v.findViewById(R.id.listViewAppImp);

                    ListAdapter adapter = new SimpleAdapter(
                            getActivity(), list, R.layout.list_exp_pending_item,
                           new String[]{Config.TAG_VED,Config.TAG_NATURE,Config.TAG_PAID_BY,Config.TAG_TYPE, Config.TAG_PROPSEC,Config.TAG_DESCRIPTION,Config.TAG_STATUS,Config.TAG_DATE,Config.TAG_COST,Config.TAG_IMP_ID },
                           new int[]{R.id.ved,R.id.capopEx,R.id.paidBy, R.id.impType,R.id.propSec,R.id.description,R.id.status,R.id.date,R.id.cost,R.id.impActualId})
                    {
                        //since we SimpleAdapter doesnot support the button inside that only ment for String and Images
                        //so we need to modify its getView method by overloading button
                        //since button has not defined in TabAll activity xml but defined in xml used for SimpleAdapter
                        // so need to define the button inside getView of SimpleAdapter
                        @Override
                        public View getView (int position, View convertView, ViewGroup parent)
                        {
                            View v2 = super.getView(position, convertView, parent);

                            final TextView edr = (TextView)v2.findViewById(R.id.impActualId);
                            final Button bApp=(Button)v2.findViewById(R.id.buttonApprove);
                            bApp.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v3) {
                                    // TODO Auto-generated method stub
                                    confirmApproveReject("APPROVE",edr.getText().toString(),"APPROVED");
                                }
                            });
                            final Button bRej=(Button)v2.findViewById(R.id.buttonReject);
                            bRej.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v4) {
                                    // TODO Auto-generated method stub
                                    confirmApproveReject("REJECT",edr.getText().toString(),"REJECTED");
                                }
                            });

                            return v2;
                        }

                    };
                    listView.setAdapter(adapter);
                    ListAdapter adapter2 = new SimpleAdapter(
                            getActivity(), not_list, R.layout.list_exp_item,
                            new String[]{Config.TAG_VED,Config.TAG_NATURE,Config.TAG_PAID_BY,Config.TAG_TYPE, Config.TAG_PROPSEC,Config.TAG_DESCRIPTION,Config.TAG_STATUS,Config.TAG_DATE,Config.TAG_COST },
                            new int[]{R.id.ved, R.id.capopEx,R.id.paidBy,R.id.impType,R.id.propSec,R.id.description,R.id.status,R.id.date,R.id.cost});
                    listViewApp.setAdapter(adapter2);
                }
                else{
                    not_list.add(improvements);
                    ListAdapter adapter2 = new SimpleAdapter(
                            getActivity(), not_list, R.layout.list_exp_item,
                            //getActivity(), list, R.layout.list_imp_item,
                            new String[]{Config.TAG_VED,Config.TAG_NATURE,Config.TAG_PAID_BY,Config.TAG_TYPE, Config.TAG_PROPSEC,Config.TAG_DESCRIPTION,Config.TAG_STATUS,Config.TAG_DATE,Config.TAG_COST },
                            new int[]{R.id.ved, R.id.capopEx,R.id.paidBy,R.id.impType,R.id.propSec,R.id.description,R.id.status,R.id.date,R.id.cost});
                    listViewApp.setAdapter(adapter2);
                }

            }      //for loop
            ;

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }   //end of showImprovements

    //function responsible for approve/reject button
    private void confirmApproveReject(final String inputVal,final String imprId,final String status){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setMessage("Are you sure, you want to "+inputVal+" ?");
        alertDialogBuilder.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        updateImpStatus(imprId,status);
                        Intent intent = new Intent(getActivity(), TabAll.class);
                        intent.putExtra(Config.LISTING_ID, listingId);
                        startActivity(intent);
                    }
                });

        alertDialogBuilder.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Toast.makeText(getActivity(), "Canceled", Toast.LENGTH_SHORT).show();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }   //end confirmApproveReject

    //update approval rejection status as Asynchronous task
    private void updateImpStatus(final String listId1,final String appStatus1){

        class UpdateImpStatus extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getActivity(),"Updating...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getActivity(),s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String,String> hashMap3 = new HashMap<>();
                hashMap3.put(Config.TAG_ID,listId1);
                hashMap3.put(Config.TAG_STATUS,appStatus1);
                RequestHandler rh3 = new RequestHandler();
                String s = rh3.sendPostRequest(Config.URL_UPDATE_IMP_STATUS, hashMap3);

                return s;
            }
        }

        UpdateImpStatus impApp2 = new UpdateImpStatus();
        impApp2.execute();
    }


}   //end of main class
