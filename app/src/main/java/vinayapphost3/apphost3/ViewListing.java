package vinayapphost3.apphost3;
/* Created By: Vinay Verma, SaffronStays */
/*
* this class is responsible for showing listing details of home owner
* //first page which user came to, after login
* */

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ViewListing extends AppCompatActivity implements View.OnClickListener,ListView.OnItemClickListener {

    private Button buttonLogout;
    private ListView listView;

    private String JSON_STRING;

    private String realOwnerId;
    private String userMailId;


    // User Session Manager Class
    UserSessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_listing);

        // User Session Manager
        session = new UserSessionManager(getApplicationContext());

       //creating a hashmap and storing data on it
       HashMap<String, String> user = session.getUserDetails();
        String name = user.get(UserSessionManager.KEY_UNAME);
        userMailId = user.get(UserSessionManager.KEY_UNAME);
        realOwnerId = user.get(UserSessionManager.KEY_REAL_OWNER);

         buttonLogout = (Button) findViewById(R.id.buttonLogout);
         buttonLogout.setOnClickListener(this);

        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(ViewListing.this);

        getJSON();
    }   //end of onCreate

    //function responsible to display listing data on screen
    private void showListings(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
            //for all json data
            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String listNo = String.valueOf(i + 1);
                String propertyId = jo.getString(Config.PROPERTY_ID);
                String listingId = jo.getString(Config.LISTING_ID);
                String title = jo.getString(Config.LISTING_TITLE);
                String location = jo.getString(Config.LISTING_LOCATION);
                String shareScheme = jo.getString(Config.SHARE_SCHEME);
                //storing data on hashmap
                HashMap<String,String> listings = new HashMap<>();
                listings.put(Config.LISTING_NO ,listNo);
                listings.put(Config.PROPERTY_ID ,propertyId);
                listings.put(Config.LISTING_TITLE,title);
                listings.put(Config.LISTING_LOCATION, location);
                listings.put(Config.LISTING_ID ,listingId);
                listings.put(Config.SHARE_SCHEME ,shareScheme);
                list.add(listings);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //creating a list adaptor and assigning values to it
        ListAdapter adapter = new SimpleAdapter(
                ViewListing.this, list, R.layout.list_listing_item,
                new String[]{Config.LISTING_NO,Config.PROPERTY_ID,Config.LISTING_TITLE,Config.LISTING_LOCATION},
                new int[]{R.id.listNo,R.id.PropId,R.id.listTitle,R.id.listLoc});
        //connecting list adaptor to list view
        listView.setAdapter(adapter);
    }       //end of showListing

    //function responsible to get data from server as asynchronous task
    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ViewListing.this,"Fetching Data","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                if(JSON_STRING.isEmpty())
                {
                    Toast.makeText(ViewListing.this, "Network Error!", Toast.LENGTH_LONG).show();
                }
                else
                {
                    showListings();
                    HashMap<String, String> user = session.getUserDetails();
                    String name = user.get(UserSessionManager.KEY_UNAME);
                    String realOwner = user.get(UserSessionManager.KEY_REAL_OWNER);
                    Toast.makeText(ViewListing.this, "welcome! " + name, Toast.LENGTH_LONG).show();
                }

            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(Config.URL_GET_All_LISTING, realOwnerId);
                return s;
            }
        }

        GetJSON gj = new GetJSON();
        gj.execute();
    }       //end of getJSON

    //click event for logout button
    @Override
    public void onClick(View v) {
          if(v == buttonLogout){
             logout();
         }

    }

    //function responsible for click event on any list item
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Intent intent = new Intent(this, ViewAllImp.class);

        HashMap<String,String> listing =(HashMap)parent.getItemAtPosition(position);
        String listingId = listing.get(Config.LISTING_ID).toString();

        String shareScheme = listing.get(Config.SHARE_SCHEME).toString();

            Intent intent = new Intent(this, TabAll.class);
            intent.putExtra(Config.LISTING_ID, listingId);
            intent.putExtra(Config.SHARE_SCHEME, shareScheme);
            intent.putExtra(Config.KEY_IMP_USER, userMailId);
            startActivity(intent);

    }

    //session logout
    public void logout()
    {
        finish();
        session.logoutUser();
       // Intent intent = new Intent(ViewListing.this, Login.class);
       // startActivity(intent);
    }
}   //end of main class
