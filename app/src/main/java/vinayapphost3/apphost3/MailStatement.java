package vinayapphost3.apphost3;
/* Created By: Vinay Verma, SaffronStays */
/*
* this class is responsible for mailing .xls and .pdf statement to home owners
* */
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class MailStatement extends Fragment implements View.OnClickListener{

    View v;
    private String listingId;
    private String shareScheme;
    private TextView fromDate;
    private TextView toDate;
    private EditText mailId;
    private Button buttonSubmit;
    String stFromDate;
    String stToDate;
    String stMailId;
    String userName;

    // User Session Manager Class
    UserSessionManager session;

    public MailStatement() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //creating constructor of tabAll to get user details
        TabAll activity = (TabAll) getActivity();
        listingId = activity.getListingId();
        shareScheme = activity.getShareScheme();
        userName = activity.getUserName();
        // Inflate the layout for this fragment
       v = inflater.inflate(R.layout.fragment_mail_statement, container, false);

        mailId = (EditText) v.findViewById(R.id.mailId);
        mailId.setText(userName);
        fromDate = (TextView) v.findViewById(R.id.fromDate);
        fromDate.setOnClickListener(this);
        toDate = (TextView) v.findViewById(R.id.toDate);
        toDate.setOnClickListener(this);

        buttonSubmit = (Button) v.findViewById(R.id.buttonSubmit);
        buttonSubmit.setOnClickListener(this);

        return v;
    }

    //for selecting date as an input for generating statement
    private DatePickerDialog.OnDateSetListener datePickerListenerFromD
            = new DatePickerDialog.OnDateSetListener() {
        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            stFromDate = selectedYear+"/"+(selectedMonth+1)+"/"+selectedDay;
            fromDate.setText(selectedDay+"/"+(getMonth(selectedMonth+1))+"/"+selectedYear);
        }
    };
    //for selecting date as an input for generating statement
    private DatePickerDialog.OnDateSetListener datePickerListenerToD
            = new DatePickerDialog.OnDateSetListener() {
        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            stToDate = selectedYear+"/"+(selectedMonth+1)+"/"+selectedDay;
            toDate.setText(selectedDay+"/"+(getMonth(selectedMonth+1))+"/"+selectedYear);
        }
    };

    //defining tasks when user click on different buttons
    public void onClick(View v) {
        if(v == fromDate){
            DatePickerDialog dialog = new DatePickerDialog(getActivity(), datePickerListenerFromD, 2016,01,01);
            dialog.show();
        }
        if(v == toDate){
            DatePickerDialog dialog = new DatePickerDialog(getActivity(), datePickerListenerToD, 2016,01,01);
            dialog.show();
        }
        if(v == buttonSubmit){
           // if(validate()==true)
            {
                stMailId = mailId.getText().toString();
                sendDetails(listingId,stFromDate,stToDate,stMailId);
            }
        }
    }

    //lookup for creating months as string
    public String getMonth(int x)
    {
        String mName = null;
        switch(x)
        {
            case 1: mName = "January"; break;
            case 2: mName = "February"; break;
            case 3: mName = "March"; break;
            case 4: mName = "April"; break;
            case 5: mName = "May"; break;
            case 6: mName = "June"; break;
            case 7: mName = "July"; break;
            case 8: mName = "August"; break;
            case 9: mName = "September"; break;
            case 10: mName = "October"; break;
            case 11: mName = "November"; break;
            case 12: mName = "December"; break;
        }
        return mName;
    }

    //validation of fields
    public boolean validate() {
        boolean valid = true;

        String stFromD = fromDate.getText().toString();
        String email = mailId.getText().toString();
        String stToD = toDate.getText().toString();

        if (stFromD.isEmpty() || stFromD.length() < 3) {
            fromDate.setError("Set From Month Properly");
            valid = false;
        } else {
            fromDate.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mailId.setError("enter a valid email address");
            valid = false;
        } else {
            mailId.setError(null);
        }

        if (stToD.isEmpty() || stToD.length() < 4) {
            toDate.setError("between 4 and 25 alphanumeric characters");
            valid = false;
        } else {
            toDate.setError(null);
        }
        return valid;
    }

    //get details from server as asynchronous task
    private void sendDetails(final String id, final String fDate, final String tDate, final String mId){

        class SendDetails extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getActivity(), "Sending Mail...", "Please Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                if(s.equals("Statement Created and Mailed"))
                {
                    Toast.makeText(getActivity(),s+" to "+mId,Toast.LENGTH_LONG).show();
                }
                else if(s.equals("Sorry!, Can't Enter More Than An Year"))
                {
                    Toast.makeText(getActivity(),s,Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getActivity(),"Sending Failed, Please Try Again",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            protected String doInBackground(Void... params) {
                //sending data to server in a hash map
                HashMap<String,String> hashMap3 = new HashMap<>();
                hashMap3.put(Config.TAG_ID,id);
                hashMap3.put(Config.KEY_FROM_DATE,fDate);
                hashMap3.put(Config.KEY_TO_DATE,tDate);
                hashMap3.put(Config.KEY_MAIL_STATEMENT,mId);
                RequestHandler rh3 = new RequestHandler();
                String s = rh3.sendPostRequest(Config.URL_GET_MAIL_STATEMENT,hashMap3);
                return s;
            }
        }

        SendDetails sendDetails = new SendDetails();
        sendDetails.execute();
    }
}
