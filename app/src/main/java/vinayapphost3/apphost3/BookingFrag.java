package vinayapphost3.apphost3;
/* Created By: Vinay Verma, SaffronStays */
/*
* class responsible for booking tab/fragment
*
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


public class BookingFrag extends Fragment {

    private Bundle data;
    View v;

    //variables for getting booking Information
    private ListView listViewBook;
    private ListView profitListViewBook;
    private String JSON_STRING;
    private String listingId;
    private String shareScheme;

    public BookingFrag() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }   //end of onCreate

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //constructor called for TabAll to get user information
        TabAll activity = (TabAll) getActivity();
        listingId = activity.getListingId();
        shareScheme = activity.getShareScheme();
        //different layout for PROFIT and REVENUE share
        if(shareScheme.equals("PROFIT"))
        {
            // Inflate the layout for this fragment
            v = inflater.inflate(R.layout.profit_fragment_booking, container, false);
        }
        else
        {
            // Inflate the layout for this fragment
            v = inflater.inflate(R.layout.fragment_booking, container, false);
        }

        listViewBook = (ListView) v.findViewById(R.id.listViewBook);
        profitListViewBook = (ListView) v.findViewById(R.id.profitListViewBook);
        //listViewBook.setOnItemClickListener(getActivity());
        getJSON();

        return v;
    }   //end of onCreateView

    //function responsible for displaying data
    private void showImprovements() {
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                String name = jo.getString(Config.TAG_GUEST_NAME);
                String checkIn = jo.getString(Config.TAG_CHECKIN_DATE);
                String checkOut = jo.getString(Config.TAG_CHECKOUT_DATE);
                String noOfGuest = jo.getString(Config.TAG_NO_OF_GUESTS);
                String noOfChild = jo.getString(Config.TAG_NO_OF_CHILD);
                String amount = jo.getString(Config.TAG_AMAOUNT);
                String bookingId = jo.getString(Config.TAG_BOOKING_ID);
                String noOfRoom = jo.getString(Config.TAG_NO_OF_ROOM);
                String careRoomIncentive = jo.getString(Config.TAG_CARE_ROOM_INCENTIVE);
                String carePersonIncentive = jo.getString(Config.TAG_CARE_PERSON_INCENTIVE);
                String ownerTravelCredits = jo.getString(Config.TAG_OWNER_TRAVEL_CREDITS);

                HashMap<String, String> bookings = new HashMap<>();
                bookings.put(Config.TAG_BOOKING_ID, bookingId);
                bookings.put(Config.TAG_GUEST_NAME,name);
                bookings.put(Config.TAG_CHECKIN_DATE, checkIn);
                bookings.put(Config.TAG_CHECKOUT_DATE, checkOut);
                bookings.put(Config.TAG_NO_OF_GUESTS, noOfGuest);
                bookings.put(Config.TAG_NO_OF_CHILD, noOfChild);
                bookings.put(Config.TAG_AMAOUNT, amount);
                bookings.put(Config.TAG_NO_OF_ROOM, noOfRoom);
                bookings.put(Config.TAG_CARE_ROOM_INCENTIVE, careRoomIncentive);
                bookings.put(Config.TAG_CARE_PERSON_INCENTIVE, carePersonIncentive);
                bookings.put(Config.TAG_OWNER_TRAVEL_CREDITS, ownerTravelCredits);
                list.add(bookings);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(shareScheme.equals("PROFIT"))
        {
            Log.d("BookingFrag0:", "came under profit layout list item: " + shareScheme);
            ListAdapter adapter = new SimpleAdapter(getActivity(), list, R.layout.profit_list_booking_item,
                    new String[]{ Config.TAG_BOOKING_ID, Config.TAG_CHECKIN_DATE, Config.TAG_CHECKOUT_DATE,Config.TAG_GUEST_NAME, Config.TAG_NO_OF_GUESTS, Config.TAG_NO_OF_CHILD, Config.TAG_NO_OF_ROOM, Config.TAG_AMAOUNT},
                    new int[]{ R.id.bookingId, R.id.checkIn, R.id.checkOut,R.id.guestName, R.id.noOfGuest, R.id.noOfChild, R.id.noOfRoom, R.id.amount});

            profitListViewBook.setAdapter(adapter);
        }
        else
        {
            Log.d("BookingFrag0:", "came under other layout list item: " + shareScheme);
            ListAdapter adapter = new SimpleAdapter(getActivity(), list, R.layout.list_booking_item,
                    new String[]{ Config.TAG_BOOKING_ID, Config.TAG_CHECKIN_DATE, Config.TAG_CHECKOUT_DATE,Config.TAG_GUEST_NAME, Config.TAG_NO_OF_GUESTS, Config.TAG_NO_OF_CHILD, Config.TAG_NO_OF_ROOM, Config.TAG_CARE_ROOM_INCENTIVE, Config.TAG_CARE_PERSON_INCENTIVE, Config.TAG_AMAOUNT, Config.TAG_OWNER_TRAVEL_CREDITS},
                    new int[]{ R.id.bookingId, R.id.checkIn, R.id.checkOut,R.id.guestName, R.id.noOfGuest, R.id.noOfChild, R.id.noOfRoom, R.id.careRoomIncentive, R.id.carePersonIncentive, R.id.amount, R.id.ownerTravelCredits});

            listViewBook.setAdapter(adapter);
        }

    }

    //function responsible for getting data as asynchronous task
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
                String s = rh.sendGetRequestParam(Config.URL_GET_All_BOOKING,listingId);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }   //end of getJSON

}   //end of maiin class
