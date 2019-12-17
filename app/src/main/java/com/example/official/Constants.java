package com.example.official;


public class Constants {

    public static final String LOGTAG = "CarteBlanche";

    public static final String DB_WORKSHOP_ID_KEY = "workshopId";
    public static final String DB_WORKSHOP_NAME_KEY = "workshopName";
    public static final String DB_WORKSHOP_STATUS_KEY = "workshopStatus";
    public static final String DB_ORDER_ID_KEY = "orderId";

    public static final String DB_PARTICIPANT_ID_KEY = "id";
    public static final String DB_PARTICIPANT_NAME_KEY = "Name";
    public static final String DB_PARTICIPANT_COLLEGE_KEY = "College";
    public static final String DB_PARTICIPANT_EMAIL_KEY = "Email";

    public static final String INTENT_PARTICIPANT_DETAILS_NAME = "participantDetailsJson";
    public static final String INTENT_WORKSHOP_LIST_NAME = "workshopListJson";
    public static final String INTENT_WORKSHOP_DETAILS_NAME = "workshopDetailsJson";
    public static final String INTENT_PARTICIPANT_LIST_NAME = "participantListJson";
    public static final String INTENT_ORG_ACCESS_NAME = "access";
    public static final String INTENT_ORG_ID_NAME = "organizerId";
    public static final String INTENT_QR_CODE_NAME = "qrCode";

    public static final String SERVICE_BASE = "http://cb.csmit.org/apk/";
    public static final String SERVICE_GET_DETAILS_BY_ORDER_ID = "getDetailsByOrderId.php";
    public static final String SERVICE_GET_DETAILS_BY_WORKSHOP_ID = "getDetailsByWorkshopId.php";
    public static final String SERVICE_GET_DETAILS_BY_PARTICIPANT_ID = "getDetailsByParticipantId.php";
    public static final String SERVICE_CHECK_LOGIN = "checkLogin.php";

    public static final String REQUEST_USERNAME_NAME = "user";
    public static final String REQUEST_PASSWORD_NAME = "pass";
    public static final String REQUEST_ORDER_ID_NAME = "orderId";

    public static final String RESPONSE_STATUS_NAME = "status";
    public static final String RESPONSE_ORG_ID_NAME = "organizerId";
    public static final String RESPONSE_ORG_ACCESS_NAME = "access";
    public static final String RESPONSE_PARTICIPANT_DETAILS_NAME = "participant";
    public static final String RESPONSE_WORKSHOPS_LIST_NAME = "workshopsList";

    public static final String RESPONSE_LOGIN_SUCCESS_VALUE = "SUCCESS";
    public static final String RESPONSE_LOGIN_FAILURE_VALUE = "FAILURE";


}
