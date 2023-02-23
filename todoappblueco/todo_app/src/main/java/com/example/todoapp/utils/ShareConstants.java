package com.example.todoapp.utils;

public interface ShareConstants {
    class RedisHashKey {
        public static final String USER_OTP = "USER_OTP";
        public static final String OTP_UNSUCCESSFUL = "OTP_UNSUCCESSFUL";
        public static final String USER_ACTION = "USER_ACTION";
        public static final String SMS_RATE_LIMIT = "SMS_RATE_LIMIT";
        public static final String DEVICE_LOGIN_ANONYMOUS = "DEVICE_LOGIN_ANONYMOUS";

    }

    class OS {
        public static final int ANDROID = 1;
        public static final int IOS = 2;
    }

    class OtpAction {
        public static final int REGISTER = 1;
        public static final int RESET_PASSWORD = 2;
        public static final int REGISTER_VIA_FACEBOOK = 3;
    }

    class ONOFF {
        public static final int ON = 1;
        public static final int OFF = 0;
    }

    class USER_STATUS {
        public static final int ENABLE = 1;
        public static final int DISABLE = 0;

        public static boolean isValid(int status) {
            return status == ENABLE || status == DISABLE;
        }
    }

    class OTP_ACTION {
        public static final int REGISTER = 1;
        public static final int VERIFY_PHONE_NUMBER = 2;
        public static final int FORGOT_PASSWORD = 3;
        public static final int LOGIN_BY_OTP = 4;
        public static final int REGISTER_BY_FACEBOOK = 5;
        public static final int REGISTER_BY_APPLE = 6;

        public static final int LINK_BY_FACEBOOK = 7;

        public static final int LINK_BY_APPLE_ID = 8;
    }

    class APPLICATION {
        public static final String CLIENT_ID_HEADER = "X-CLIENT-ID";
        public static final String CLIENT_SECRET_HEADER = "X-CLIENT-SECRET";
        public static final String APP_USER_ROLE = "X-APP-USER_ROLE";
        public static final String APP_NAME = "X-APP-NAME";
        public static final int APP_TYPE_CLIENT = 1;
        public static final int APP_TYPE_ADMIN = 2;
    }

    class LOGIN_TYPE {
        public static final int PHONE = 1;
        public static final int FACEBOOK = 2;
        public static final int EMAIL = 3;
        public static final int OTP = 4;
        public static final int APPLE_ID = 5;
        public static final int ANONYMOUS = 6;
    }

    class USER_TYPE {
        public static final int ANONYMOUS = 1;
        public static final int NORMAL = 2;
    }

    class SOCICAL {
        public static final int FACEBOOK = 1;
        public static final int APPLE = 2;
    }

    class USER {
        public static final String USER_ID_HEADER = "x-authenticated-userid";
        public static final String USER_ID_CORE = "x-user-id";
        public static final String USER_TYPE = "user-type";

        public static final int USER_CHANGE_STATUS_CORE = 1;
        public static final int USER_CHANGE_STATUS_APPLICATION = 2;

    }

    class USER_MANAGEMENT_ACTION {
        public static final int UPDATE_APPLICATION = 1;
        public static final int CHANGE_USER_STATUS = 2;

        public static final int CMS_USER_DELETE = 0;
        public static final int CMS_USER_ADD = 1;
        public static final int CMS_USER_UPDATE = 2;
    }

    class COMMON_RESPONSE {
        public static final String SUCCESS = "200";
        public static final String ERROR = "500";
        public static final int ENTITY_NOT_FOUND = 100;
        public static final int ENTITY_EXISTED = 101;
    }

    class LOGIN_ANONYMOUS {
        public static final int CREATE_USER_ANONYMOUS = 1;
        public static final int MERGE_USER = 2;


        public static final int ENABLE = 1;
        public static final int MERGE = 2;

    }

    class REST_HEADER {
        public static final String traceId = "X-B3-TraceId";
        public static final String DEVICE_Id = "device-id";
        public static final String TOKEN_HEADER = "x-authen-token";
        public static final String AGENT_HEADER = "platform";
        public static final String TOKEN_PREFIX = "bearer ";
    }

    class KYC{
        public static final int CMT = 1;
        public static final int CAN_CUOC = 2;
        public static final int KHAC = 3;


        public static final int NO_REQUEST = 0;
        public static final int REJECT = 3;
        public static final int REQUEST = 1;
        public static final int APPROVE = 2;
    }
}
