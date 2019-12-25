package com.example.official;


public class Constants {

    public static final String LOGTAG = "CarteBlanche";

    public static final String DB_WORKSHOP_ID_NAME = "workshopId";
    public static final String DB_WORKSHOP_NAME_NAME = "Name";
    public static final String DB_WORKSHOP_DATE_NAME = "Date";
    public static final String DB_ORDER_ID_NAME = "orderId";
    public static final String DB_EVENT_NAME_NAME = "eventName";
    public static final String DB_EVENT_DATE_NAME = "eventDate";
    public static final String DB_WORKSHOP_PRICE_NAME = "Price";

    public static final String DB_PARTICIPANT_ID_NAME = "id";
    public static final String DB_PARTICIPANT_NAME_NAME = "Name";
    public static final String DB_PARTICIPANT_COLLEGE_NAME = "College";
    public static final String DB_PARTICIPANT_EMAIL_NAME = "Email";

    public static final String INTENT_PARTICIPANT_DETAILS_NAME = "participantDetailsJson";
    public static final String INTENT_WORKSHOP_LIST_NAME = "workshopListJson";
    public static final String INTENT_WORKSHOP_DETAILS_NAME = "workshopDetailsJson";
    public static final String INTENT_PARTICIPANT_LIST_NAME = "participantListJson";
    public static final String INTENT_ORG_ACCESS_NAME = "access";
    public static final String INTENT_ORG_ID_NAME = "organizerId";
    public static final String INTENT_QR_CODE_NAME = "qrCode";
    public static final String INTENT_MESSAGE_NAME = "message";

    //public static final String SERVICE_BASE = "http://cb.csmit.org/apk/";
    public static final String SERVICE_BASE = "http://192.168.0.106/";
    public static final String SERVICE_GET_DETAILS = "getDetails.php";
    public static final String SERVICE_CHECK_LOGIN = "checkLogin.php";
    public static final String SERVICE_PUT_DETAILS = "putDetails.php";
    public static final String SERVICE_AUTOFILL = "autofill.php";
    public static final String SERVICE_GET_WORKSHOP_DETAILS = "getWorkshopDetails.php";

    public static final String REQUEST_USERNAME_NAME = "user";
    public static final String REQUEST_PASSWORD_NAME = "pass";
    public static final String REQUEST_ID_NAME = "id";
    public static final String REQUEST_JAN4_WORKSHOP_ID_NAME = "jan4WorkshopId";
    public static final String REQUEST_JAN5_WORKSHOP_ID_NAME = "jan5WorkshopId";
    public static final String REQUEST_TICKET_ID_NAME = "ticketId";
    public static final String REQUEST_ORG_ID_NAME = "organizerId";
    public static final String REQUEST_PARTICIPANT_ID_NAME = "participantId";
    public static final String REQUEST_PARTICIPANT_EMAIL_NAME = "participantEmail";
    public static final String REQUEST_PREFIX_NAME = "prefix";
    public static final String REQUEST_TICKET_ONLINE_NAME = "ticketOnline";

    public static final String RESPONSE_STATUS_NAME = "status";
    public static final String RESPONSE_MESSAGE_NAME = "message";
    public static final String RESPONSE_ORG_ID_NAME = "organizerId";
    public static final String RESPONSE_ORG_ACCESS_NAME = "access";
    public static final String RESPONSE_PARTICIPANT_DETAILS_NAME = "participant";
    public static final String RESPONSE_EVENT_LIST_NAME = "eventsList";
    public static final String RESPONSE_WORKSHOP_DETAILS_NAME = "workshop";
    public static final String RESPONSE_PARTICIPANT_LIST_NAME = "participantsList";
    public static final String RESPONSE_DATA_NAME = "data";
    public static final String RESPONSE_TICKET_RECEIVED_NAME = "ticketReceived";

    public static final String RESPONSE_SUCCESS_VALUE = "SUCCESS";
    public static final String RESPONSE_FAILURE_VALUE = "FAILURE";
    public static final String RESPONSE_NULL_VALUE = "null";

    public static final int ROLLING_EVENT_DATE_VALUE = 29;

}
