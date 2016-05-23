package vinayapphost3.apphost3;
/* Created By: Vinay Verma, SaffronStays */
/**
 * This is the configuration file for app
 * all urls global variable initialization are declared here only
 */
public class Config {

    public static final String APP_NAME = "SaffronStays";

   //Address of our scripts of the CRUD

/*
  //for SaffronBNB
   //Address of our scripts of the CRUD
    public static final String URL_ADD ="http://www.saffronbnb.com/Android/addImp.php";
    public static final String URL_LOGIN ="http://www.saffronbnb.com/Android/hostLogin.php";
    public static final String URL_CHANGE_USER ="http://www.saffronbnb.com/Android/changeUser.php";
    public static final String URL_GET_All_LISTING ="http://www.saffronbnb.com/Android/getAllListing.php?realid=";
    public static final String URL_GET_All_BOOKING ="http://www.saffronbnb.com/Android/getAllBooking.php?id=";
    public static final String URL_GET_ALL_IMP = "http://www.saffronbnb.com/Android/getAllImp.php?id=";
    public static final String URL_GET_ALL_EXP = "http://www.saffronbnb.com/Android/getAllExp.php?id=";
    public static final String URL_GET_DASHBOARD = "http://www.saffronbnb.com/Android/dashBoard.php?id=";
    public static final String URL_GET_MONTHLY_INFO = "http://www.saffronbnb.com/Android/getMonthlyInfo.php?id=";
    public static final String URL_GET_QUARTERLY_INFO = "http://www.saffronbnb.com/Android/getQuarterlyInfo.php?id=";
    public static final String URL_GET_ANNUALLY_INFO = "http://www.saffronbnb.com/Android/getAnnuallyInfo.php?id=";
    public static final String URL_GET_REMITTANCE = "http://www.saffronbnb.com/Android/getRemittance.php?id=";
    public static final String URL_UPDATE_IMP_STATUS = "http://www.saffronbnb.com/Android/updateImpStatus.php";
    public static final String URL_GET_TRAVEL_CREDITS = "http://www.saffronbnb.com/Android/getTravelCredits.php?id=";
    public static final String URL_GET_NOTIFICATION = "http://www.saffronbnb.com/Android/getNotifications.php?id=";
    public static final String URL_GET_MAIL_STATEMENT = "http://www.saffronbnb.com/Android/CreateExcel/AndroidFile/SS_Statement.php";
    public static final String URL_GET_IMP = "http://www.saffronbnb.com/Android/getImp.php?imp_id=";
    public static final String URL_UPDATE_IMP = "http://www.saffronbnb.com/Android/updateImp.php";
    public static final String URL_DELETE_IMP = "http://www.saffronbnb.com/Android/deleteImp.php?imp_id=";
*/
   //for SaffronStays
    //Address of our scripts of the CRUD
    public static final String URL_ADD ="https://www.saffronstays.com/Android/addImp.php";
    public static final String URL_LOGIN ="https://www.saffronstays.com/Android/hostLogin.php";
    public static final String URL_CHANGE_USER ="https://www.saffronstays.com/Android/changeUser.php";
    public static final String URL_GET_All_LISTING ="https://www.saffronstays.com/Android/getAllListing.php?realid=";
    public static final String URL_GET_All_BOOKING ="https://www.saffronstays.com/Android/getAllBooking.php?id=";
    public static final String URL_GET_ALL_IMP = "https://www.saffronstays.com/Android/getAllImp.php?id=";
    public static final String URL_GET_ALL_EXP = "https://www.saffronstays.com/Android/getAllExp.php?id=";
    public static final String URL_GET_DASHBOARD = "https://www.saffronstays.com/Android/dashBoard.php?id=";
    public static final String URL_GET_MONTHLY_INFO = "https://www.saffronstays.com/Android/getMonthlyInfo.php?id=";
    public static final String URL_GET_QUARTERLY_INFO = "https://www.saffronstays.com/Android/getQuarterlyInfo.php?id=";
    public static final String URL_GET_ANNUALLY_INFO = "https://www.saffronstays.com/Android/getAnnuallyInfo.php?id=";
    public static final String URL_GET_REMITTANCE = "https://www.saffronstays.com/Android/getRemittance.php?id=";
    public static final String URL_UPDATE_IMP_STATUS = "https://www.saffronstays.com/Android/updateImpStatus.php";
    public static final String URL_GET_TRAVEL_CREDITS = "https://www.saffronstays.com/Android/getTravelCredits.php?id=";
    public static final String URL_GET_NOTIFICATION = "https://www.saffronstays.com/Android/getNotifications.php?id=";
    public static final String URL_GET_MAIL_STATEMENT = "https://www.saffronstays.com/Android/CreateExcel/AndroidFile/SS_Statement.php";
    public static final String URL_GET_IMP = "https://www.saffronstays.com/Android/getImp.php?imp_id=";
    public static final String URL_UPDATE_IMP = "https://www.saffronstays.com/Android/updateImp.php";
    public static final String URL_DELETE_IMP = "https://www.saffronstays.com/Android/deleteImp.php?imp_id=";

    //Keys that will be used to send the request to php scripts
    public static final String KEY_IMP_ID = "imp_id";
    public static final String KEY_IMP_VED = "ved";
    public static final String KEY_IMP_TYPE = "type";
    public static final String KEY_IMP_PROPSEC = "propsec";
    public static final String KEY_IMP_USER = "username";
    public static final String KEY_IMP_PASS = "password";
    public static final String KEY_IMP_NEW_USER = "new_username";
    public static final String KEY_IMP_NEW_PASS = "new_password";

    public static final String KEY_FROM_DATE = "fromDate";
    public static final String KEY_TO_DATE = "toDate";
    public static final String KEY_MAIL_STATEMENT = "mailStatement";


    //JSON Tags
    public static final String TAG_JSON_ARRAY="result";
    public static final String TAG_IMP_ID = "imp_id";
    public static final String TAG_ID = "id";
    public static final String TAG_VED = "ved";
    public static final String TAG_NATURE = "nature";
    public static final String TAG_TYPE = "type";
    public static final String TAG_PROPSEC = "propsec";
    public static final String TAG_DESCRIPTION = "description";
    public static final String TAG_STATUS = "status";
    public static final String TAG_DATE = "date";
    public static final String TAG_COST = "cost";
    public static final String TAG_PAID_BY = "paidBy";


    public static final String LISTING_NO = "listing_no";
    public static final String PROPERTY_ID = "property_id";
    public static final String LISTING_ID = "listing_id";
    public static final String LISTING_TITLE = "listing_title";
    public static final String LISTING_LOCATION = "listing_location";
    public static final String REAL_OWNER_ID = "real_owner_id";
    public static final String SHARE_SCHEME = "shareScheme";
    //for booking info
    public static final String TAG_GUEST_NAME = "guestName";
    public static final String TAG_CHECKIN_DATE = "checkIn";
    public static final String TAG_CHECKOUT_DATE = "checkOut";
    public static final String TAG_NO_OF_GUESTS = "noOfGuest";
    public static final String TAG_NO_OF_CHILD = "noOfChild";
    public static final String TAG_AMAOUNT = "amount";
    public static final String TAG_BOOKING_ID = "bookingId";
    public static final String TAG_NO_OF_ROOM = "noOfRoom";
    public static final String TAG_CARE_ROOM_INCENTIVE = "careRoomIncentive";
    public static final String TAG_CARE_PERSON_INCENTIVE = "carePersonIncentive";
    public static final String TAG_OWNER_TRAVEL_CREDITS = "ownerTravelCredits";

    //for DashBoard
    public static final String TAG_BOOKING_COUNT = "bookingCount";
    public static final String TAG_TOTAL_PER_ROOM_NIGHT = "totPerRoomNight";
    public static final String TAG_TOTAL_ACCOMMODATION_CHARGES = "totAccCharges";
    public static final String TAG_SAFFRONSTAYS_FEES = "ssFees";
    public static final String TAG_EXPENSES_INCURRED = "expIncurred";
    public static final String TAG_CARE_INCENTIVE= "careIncentive";
    public static final String TAG_GROSS_MARGIN = "grossMargin";
    public static final String TAG_IMPROVEMENTS_COST = "impCosts";
    public static final String TAG_CAPEX_HOST = "capHost";
    public static final String TAG_RETURN_CAP_INVEST = "returnOnCapInvest";
    public static final String TAG_MON_REM_TO_HOME_OWNER = "monRemHomeOwner";
    public static final String TAG_MON_OWED_TO = "moneyOwedTo";
    public static final String TAG_OWED_MONEY = "owedMoney";
    public static final String TAG_TRAVEL_CREDIT_UT = "travelCreditUt";
    public static final String TAG_TRAVEL_CREDIT_UN = "travelCreditUn";
    public static final String TAG_TRAVEL_CREDIT_EX = "travelCreditEx";
    public static final String TAG_TOTAL_TRAVEL_CREDITS = "totalTravelCredits";

    //for monthly
    public static final String TAG_MON_DURATION = "duration";
    public static final String TAG_MON_BOOKING_COUNT = "monBookingCount";
    public static final String TAG_MON_TOTAL_PER_ROOM_NIGHT = "monTotPerRoomNight";
    public static final String TAG_MON_TOTAL_ACCOMMODATION_CHARGES = "monTotAccCharges";
    public static final String TAG_MON_SAFFRONSTAYS_FEES = "monSSFees";
    public static final String TAG_MON_EXPENSES_INCURRED = "monExpIncurred";
    public static final String TAG_MON_CARE_INCENTIVE = "monCareIncentive";
    public static final String TAG_MON_GROSS_MARGIN = "monGrossMargin";
    public static final String TAG_MON_IMPROVEMENTS_COST = "monImpCosts";
    public static final String TAG_MON_MON_REM_TO_HOME_OWNER = "monMonRemHomeOwner";
    public static final String TAG_MON_OPENING_BALANCE = "monOpeningBalance";
    public static final String TAG_MON_CLOSING_BALANCE = "monClosingBalance";
    public static final String TAG_MON_OWNER_TRAVEL_CREDITS = "monOwnerTravelCredits";
    public static final String TAG_MON_TRAVEL_CREDIT_UN = "monTravelCreditUn";
    public static final String TAG_MON_TRAVEL_CREDIT_EX = "monTravelCreditEx";
    public static final String TAG_MON_TOTAL_TRAVEL_CREDITS = "monTotalTravelCredits";

    //for remittance
    public static final String TAG_REM_DATE = "date";
    public static final String TAG_REM_AMOUNT = "amount";
    public static final String TAG_REM_TRANS_DETAILS = "transDetails";
    public static final String TAG_REM_MONTH = "remMonth";
    public static final String TAG_REM_ID = "rem_id";

    //for Travel Credit Module
    public static final String TAG_CREDIT_BOOKING_ID = "date";
    public static final String TAG_CREDIT_AMOUNT = "amount";
    public static final String TAG_EXPIRY_DATE = "transDetails";

    //employee id to pass with intent
    public static final String IMP_ID = "imp_id";

    //for push notification
    public static final String TAG_PUSH_BOOKING_COUNT = "notifiBookCount";
    public static final String TAG_PUSH_IMP_COUNT = "notifiImpCount";
    public static final String TAG_PUSH_REM_COUNT = "notifiRemCount";


}
