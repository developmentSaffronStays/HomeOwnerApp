package vinayapphost3.apphost3;
/* Created By: Vinay Verma, SaffronStays */
/*
* this is main Activity(start point) class of app
* */
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    // User Session Manager Class for managing user session
    UserSessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //for push notification on any new event defined on push notification class
        startService(new Intent(getApplicationContext(), PushNotification.class));

        // User Session Manager
        session = new UserSessionManager(getApplicationContext());

        //defining image view for start animation of app
        final ImageView iv = (ImageView) findViewById(R.id.imageView2);
        final ImageView iv2 = (ImageView) findViewById(R.id.imageView3);
        //defining animation sequences
        final Animation an1 = AnimationUtils.loadAnimation(getBaseContext(), R.anim.rotate);
        final Animation an0 = AnimationUtils.loadAnimation(getBaseContext(), R.anim.fade_in);
        final Animation an2 = AnimationUtils.loadAnimation(getBaseContext(), R.anim.fade_out);
        //apply animation to view
        iv2.startAnimation(an1);
        iv.startAnimation(an0);

        //function declaration for animations
        an0.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // iv.startAnimation(an2);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        an1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                iv.startAnimation(an2);
                iv2.startAnimation(an2);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        an2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                // Check user login (this is the important point)
                // If User is not logged in , This will redirect user to LoginActivity
                // and finish current activity from activity stack.
                finish();
                session.checkLogin();

                // get user data from session
              //  HashMap<String, String> user = session.getUserDetails();
                // get name
                //String name = user.get(UserSessionManager.KEY_NAME);
                // get email
                //String email = user.get(UserSessionManager.KEY_EMAIL);

                // Show user data on activity
              //  Toast.makeText(MainActivity.this, "welcome! " + name, Toast.LENGTH_LONG).show();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


        /*
        Button button = (Button) findViewById(R.id.buttonSignin);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLogin();
            }
        });*/
    }
    /*
    private void goToLogin() {
        Intent intent = new Intent(this, Login.class);
        //Intent intent = new Intent(this, TabAll.class);
        startActivity(intent);
    }*/
    //to add shortcut


}       //end of main class
