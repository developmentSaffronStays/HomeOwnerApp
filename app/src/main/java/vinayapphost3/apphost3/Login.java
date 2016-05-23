package vinayapphost3.apphost3;
/* Created By: Vinay Verma, SaffronStays */
//this file is responsible for user authentication before opening the app

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Login extends AppCompatActivity implements View.OnClickListener {

    //variable declaration
    private EditText editTextUser;
    private EditText editTextPass;
    private Button buttonSignin;

    // User Session Manager Class: used for managing user session
    UserSessionManager session;

    private SharedPreferences sharedPreferences;

    private String realOwnerId;
    private String status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);        //layout declaration

        //variable initialization
        // User Session Manager variable
        session = new UserSessionManager(getApplicationContext());
        editTextUser = (EditText) findViewById(R.id.editTextUser);
        editTextPass = (EditText) findViewById(R.id.editTextPass);
        buttonSignin = (Button) findViewById(R.id.buttonSignin);
        buttonSignin.setOnClickListener(this);
    }   //end of onCreate

    //function declaration for methods of signing In
    public void signin(){
        Log.d("login", "Come to signin");
        final String username = editTextUser.getText().toString().trim();
        final String password = editTextPass.getText().toString().trim();

        //run the Asynchronus(as new thread) task: Android Standard library function
        class SignIn extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;     //initialize a process dialog
            //initialization of methods of AsyncTask
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Login.this,"Signing In...","Wait...",false,false);
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                try {
                    //declaring JSON Object for getting data from php file on server
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
                    JSONObject c = result.getJSONObject(0);
                    status = c.getString(Config.TAG_STATUS);
                    realOwnerId = c.getString(Config.REAL_OWNER_ID);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(status!=null)
                {
                    if(status.equals("success"))    //if login is success
                    {
                        session.createUserLoginSession(username, realOwnerId);      //creating a user session for the same
                        Toast.makeText(Login.this, "welcome! " + username, Toast.LENGTH_LONG).show();
                        finish();
                        Intent intent = new Intent(Login.this, ViewListing.class);
                        intent.putExtra(Config.REAL_OWNER_ID, realOwnerId);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        // Add new Flag to start new Activity
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(Login.this, status+" "+realOwnerId, Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(Login.this, "Network Error!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            protected String doInBackground(Void... v) {
                //creating hashMap variable for sending user details to server
                HashMap<String,String> params = new HashMap<>();
                //assigning variables to hashMap
                params.put(Config.KEY_IMP_USER,username);
                params.put(Config.KEY_IMP_PASS,password);
                //calling request handler class
                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.URL_LOGIN, params);
                return res;

            }
        }
        if(validate()==true){       //if all fields are proper
            SignIn hostsign = new SignIn();
            hostsign.execute();
        }
        else{
            Toast.makeText(Login.this, "Fix the errors first", Toast.LENGTH_SHORT).show();
        }

    }

    //function for checking all fields are filled properly
    public boolean validate() {
        boolean valid = true;

        String username = editTextUser.getText().toString();
        //String email = editTextEmailUser.getText().toString();
        String password = editTextPass.getText().toString();

        if (username.isEmpty() || username.length() < 3) {
            editTextUser.setError("at least 3 characters");
            valid = false;
        } else {
            editTextUser.setError(null);
        }

       /* if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("enter a valid email address");
            valid = false;
        } else {
            editTextEmail.setError(null);
        }*/

        if (password.isEmpty() || password.length() < 4 || password.length() > 25) {
            editTextPass.setError("between 4 and 25 alphanumeric characters");
            valid = false;
        } else {
            editTextPass.setError(null);
        }

        return valid;
    }

    @Override
    public void onClick(View v) {
        if(v == buttonSignin){
            signin();

        }

    }


}      //end of main class
