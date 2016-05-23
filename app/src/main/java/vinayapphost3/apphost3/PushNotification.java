package vinayapphost3.apphost3;
/* Created By: Vinay Verma, SaffronStays */
/*
* this class is responsible for push notification being sent to home owner
* for any defined event trigger
* now events are designed as any new booking, expense, remittance
* */

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class PushNotification extends Service {

    private String JSON_STRING;
    // int NOTIFICATION_ID = 1241421;
    //defined time interval for which app will check weather an event triggered in server
    public static final long NOTIFY_INTERVAL = 12*3600*1000; // 12 hour

    public String sendNotification = null;
    public int totalBooking = 0;
    public int totalImp = 0;
    public int totalRem = 0;

    public String realOwner;
    // run on another Thread to avoid crash
    private Handler mHandler = new Handler();
    // timer handling
    private Timer mTimer = null;

    // User Session Manager Class
    UserSessionManager session;

    //blank constructor
    public PushNotification() {
    }

    //this will make sure that task will not stop when app gets closed
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;//throw new UnsupportedOperationException("Not yet implemented");
    }

    //function defined tasks when notification is removed
    //basically we used notification as alarm service so it will be used periodically and can not be killed
    @Override
    public void onTaskRemoved(Intent rootIntent) {
        // TODO Auto-generated method stub
        Intent restartService = new Intent(getApplicationContext(),this.getClass());
        restartService.setPackage(getPackageName());
        PendingIntent restartServicePI = PendingIntent.getService(
                getApplicationContext(), 1, restartService,
                PendingIntent.FLAG_ONE_SHOT);
        //Restart the service once it has been killed android
        AlarmManager alarmService = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        alarmService.set(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + 100, restartServicePI);
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        // super.onCreate();
        // cancel if already existed
        if(mTimer != null) {
            mTimer.cancel();
        } else {
            // recreate new
            mTimer = new Timer();
        }
        // User Session Manager
        session = new UserSessionManager(getApplicationContext());
        //getting user session details
        HashMap<String, String> user = session.getUserDetails();
        String name = user.get(UserSessionManager.KEY_UNAME);
        realOwner = user.get(UserSessionManager.KEY_REAL_OWNER);
        // schedule task
        mTimer.scheduleAtFixedRate(new TimeDisplayTimerTask(), 0, NOTIFY_INTERVAL);
        // sendNotification("new msg notification");
    }

    //function definition for pushing notifications
    private void sendNotification(String msg) {

        Intent onClickIntent;

        //function will only work when user is logged in in app
        //else ask for login
        if(!session.isUserLoggedIn()){
            onClickIntent = new Intent(this, MainActivity.class);
        }
        else{
            //send directly to listing display page
            onClickIntent = new Intent(this, ViewListing.class);
        }
        //define a notification manager service
        NotificationManager mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, onClickIntent, 0);
        //defining notification builder with properties on notifications
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setVisibility(Notification.VISIBILITY_PUBLIC)
                        .setSmallIcon(R.drawable.splash1)
                        .setContentTitle("SS Notification")
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(msg))
                        .setAutoCancel(true)
                        .setContentText(msg);
        mBuilder.setVibrate(new long[]{1000, 1000});
        mBuilder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
        mBuilder.setContentIntent(contentIntent);
        Notification note = mBuilder.build();
        note.defaults |= Notification.DEFAULT_VIBRATE;
        note.defaults |= Notification.DEFAULT_SOUND;

        //for generating random ID for the notification to send multiple Notifications
        java.util.Date date=new Date();
        int numberRandom = Math.abs((int)(date.getTime()));
        //send the notification command
        mNotificationManager.notify(numberRandom, mBuilder.build());
    }

    //getting data from server on a new thread
    class TimeDisplayTimerTask extends TimerTask {
        @Override
        public void run() {
            // run on another thread
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    getJSON();  //function declaration for getting JSON data
                }
            });
        }

        private String getDateTime() {
            // get date time in custom format
            SimpleDateFormat sdf = new SimpleDateFormat("[yyyy/MM/dd - HH:mm:ss]");
            return sdf.format(new Date());
        }
    }

    //function definition for getting data from server as asynchronous task
    private void getJSON(){
        //defining a Asynchronous task
        class GetJSON extends AsyncTask<Void,Void,String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                JSON_STRING = s;
                if(JSON_STRING.isEmpty())
                {

                }
                else
                {
                    //getting user data from session manager
                    HashMap<String, String> user = session.getUserDetails();
                    String name = user.get(UserSessionManager.KEY_UNAME);
                    realOwner = user.get(UserSessionManager.KEY_REAL_OWNER);
                    getData();
                }

            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(Config.URL_GET_NOTIFICATION, realOwner);
                return s;
            }
        }

        GetJSON gj = new GetJSON();
        gj.execute();
    }       //end of getJSON

    //this function is responsible for getting data from server
    private void getData(){
        JSONObject jsonObject = null;
        String notify = null;
        String bookingCount = null;
        String impCount = null;
        String remCount = null;

        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            //for all JSON data
            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                notify = jo.getString("notify");
                bookingCount = jo.getString(Config.TAG_PUSH_BOOKING_COUNT);
                impCount = jo.getString(Config.TAG_PUSH_IMP_COUNT);
                remCount = jo.getString(Config.TAG_PUSH_REM_COUNT);

                //we are getting information from server if an event is already notified to user or not
                if(notify.equals("YES"))
                {
                    sendNotification = notify;
                    if(!bookingCount.equals("ZERO"))
                    {
                        totalBooking =  jo.getInt(Config.TAG_PUSH_BOOKING_COUNT);
                        if(totalBooking<2)
                        {
                            sendNotification("You Have "+totalBooking+" New Booking");
                        }
                        else
                        {
                            sendNotification("You Have "+totalBooking+" New Bookings");
                        }
                    }
                    if(!impCount.equals("ZERO"))
                    {
                        totalImp = jo.getInt(Config.TAG_PUSH_IMP_COUNT);
                        if(totalImp<2)
                        {
                            sendNotification("You Have "+totalImp+" New Expense");
                        }
                        else {
                            sendNotification("You Have " + totalImp + " New Expenses");
                        }
                    }
                    if(!remCount.equals("ZERO"))
                    {
                        totalRem = jo.getInt(Config.TAG_PUSH_REM_COUNT);
                        if(totalRem<2)
                        {
                            sendNotification("You Have "+totalRem+" New Remittance");
                        }
                        else {
                            sendNotification("You Have " + totalRem + " New Remittances");
                        }
                    }
                }
                else
                {
                    sendNotification = notify;
                }

            };

        } catch (JSONException e) {
            e.printStackTrace();
        }   //end of catch
    }   //end of getData



}
