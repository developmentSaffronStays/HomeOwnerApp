package vinayapphost3.apphost3;
/* Created By: Vinay Verma, SaffronStays */
/*
* this class is responsible for all tabs in one screen
* very important and complex class for app
* called after clicking any listing
* all tabs are added as fragments
* */
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class TabAll extends AppCompatActivity {

    //variables for creating the tabview

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private String shareScheme;

    private int[] tabIcons = {
            R.drawable.ic_dashboard_white_24dp,
            R.drawable.ic_receipt_white_24dp,
            R.drawable.ic_book_white_24dp,
            R.drawable.ic_event_note_white_24dp,
            R.drawable.ic_account_balance_white_24dp,
            R.drawable.ic_account_balance_wallet_white_24dp,
            R.drawable.ic_content_paste_white_24dp
    };

    private int[] profitTabIcons = {
            R.drawable.ic_dashboard_white_24dp,
            R.drawable.ic_receipt_white_24dp,
            R.drawable.ic_book_white_24dp,
            R.drawable.ic_event_note_white_24dp,
            R.drawable.ic_date_range_white_24dp,
            R.drawable.ic_event_white_24dp,
            R.drawable.ic_account_balance_white_24dp,
            R.drawable.ic_account_balance_wallet_white_24dp,
            R.drawable.ic_content_paste_white_24dp
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_all);

        getListingId();
        getShareScheme();
        getUserName();

        shareScheme = getShareScheme();

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
    }   //end of onCreate

    //these function may be called from several other class for getting user information
    //may be used as a constructor calling outside
    public String getListingId()
    {
        Intent intent = getIntent();
        String listID = intent.getStringExtra(Config.LISTING_ID);
        return listID;
    }

    public String getShareScheme()
    {
        Intent intent = getIntent();
        String shareScheme = intent.getStringExtra(Config.SHARE_SCHEME);
        return shareScheme;
    }

    public String getUserName()
    {
        Intent intent = getIntent();
        String userName = intent.getStringExtra(Config.KEY_IMP_USER);
        return userName;
    }

    //function responsible for multiple tab icons based on if its PROFIT or REVENUE share
    private void setupTabIcons() {
        if(shareScheme.equals("PROFIT"))
        {
            tabLayout.getTabAt(0).setIcon(profitTabIcons[0]);
            tabLayout.getTabAt(1).setIcon(profitTabIcons[1]);
            tabLayout.getTabAt(2).setIcon(profitTabIcons[2]);
            tabLayout.getTabAt(3).setIcon(profitTabIcons[3]);
            tabLayout.getTabAt(4).setIcon(profitTabIcons[4]);
            tabLayout.getTabAt(5).setIcon(profitTabIcons[5]);
            tabLayout.getTabAt(6).setIcon(profitTabIcons[6]);
            tabLayout.getTabAt(7).setIcon(profitTabIcons[7]);
            tabLayout.getTabAt(8).setIcon(profitTabIcons[8]);
        }
        else
        {
            tabLayout.getTabAt(0).setIcon(tabIcons[0]);
            tabLayout.getTabAt(1).setIcon(tabIcons[1]);
            tabLayout.getTabAt(2).setIcon(tabIcons[2]);
            tabLayout.getTabAt(3).setIcon(tabIcons[3]);
            tabLayout.getTabAt(4).setIcon(tabIcons[4]);
            tabLayout.getTabAt(5).setIcon(tabIcons[5]);
            tabLayout.getTabAt(6).setIcon(tabIcons[6]);
        }
    }

    //view pager setting for all tabs
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        if(shareScheme.equals("PROFIT"))
        {
            adapter.addFrag(new DashBoardFrag(), "DASHBOARD");
            adapter.addFrag(new ExpenseFrag(), "EXPENSES");
            adapter.addFrag(new BookingFrag(), "BOOKINGS");
            adapter.addFrag(new MonthlyInfoFrag(), "MONTHLY");
            adapter.addFrag(new QuarterlyInfoFrag(), "QUARTERLY");
            adapter.addFrag(new AnnuallyInfoFrag(), "ANNUALLY");
            adapter.addFrag(new RemittanceFrag(), "REMITTANCES");
            adapter.addFrag(new TravelCreditsFrag(), "TRAVEL CREDITS");
            adapter.addFrag(new MailStatement(), "MAIL STATEMENT");
            viewPager.setAdapter(adapter);
        }
        else
        {
            adapter.addFrag(new DashBoardFrag(), "DASHBOARD");
            adapter.addFrag(new ExpenseFrag(), "EXPENSES");
            adapter.addFrag(new BookingFrag(), "BOOKINGS");
            adapter.addFrag(new MonthlyInfoFrag(), "MONTHLY");
            adapter.addFrag(new RemittanceFrag(), "REMITTANCES");
            adapter.addFrag(new TravelCreditsFrag(), "TRAVEL CREDITS");
            adapter.addFrag(new MailStatement(), "MAIL STATEMENT");
            viewPager.setAdapter(adapter);
        }
    }

    //view pager adaptor setting for adding tabs into fragment
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}   //end of main class
