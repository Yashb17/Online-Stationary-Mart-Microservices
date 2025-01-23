package com.onlinestationarymart.email_service.constants;

public final class EmailConstants {

    public static final String ORDER_RECEIVED_EMAIL_SUBJECT = "Your Order is received!.";
    public static final String ORDER_CANCELLED_EMAIL_SUBJECT = "Your Order is cancelled!.";
    public static final String ORDER_OUT_FOR_DELIVERY_EMAIL_SUBJECT = "Your Order is Out For Delivery!.";
    public static final String ORDER_DELIVERED_EMAIL_SUBJECT = "Your Order is Delivered!.";
    public static final String REFILLING_REQUIRED_EMAIL_SUBJECT = "Urgent Refilling Required!.";

    public static final String ORDER_RECEIVED_EMAIL_TEMPLATE = "Dear %s,\n" +
            "\n" +
            "Greetings for the day!." +
            "\n" +
            "Thank you for your order %s. We are pleased to inform you that we have received your purchase totaling %s. Your order includes the following items: %s.\n" +
            "\n" +
            "We are now processing your order and will notify you once it has been shipped and out for delivery. Should you have any questions or require further assistance, please do not hesitate to contact us at %s. Thank you for choosing Online Stationary Mart.\n" +
            "\n" +
            "Thanks,\n" +
            "\n" +
            "Online Stationary Mart";

    public static final String ORDER_CANCELLED_EMAIL_TEMPLATE = "Dear %s,\n" +
            "\n" +
            "Greetings for the day!." +
            "\n" +
            "On your request we have cancelled your order %s. Your purchase totaling was %s. Your order included the following items: %s.\n" +
            "\n" +
            "We look to have again at our app. Should you have any questions or require further assistance, please do not hesitate to contact us at %s. Thank you for choosing Online Stationary Mart.\n" +
            "\n" +
            "Thanks,\n" +
            "\n" +
            "Online Stationary Mart";

    public static final String ORDER_DELIVERED_EMAIL_TEMPLATE = "Dear %s,\n" +
            "\n" +
            "Greetings for the day!." +
            "\n" +
            "This regarding your order %s with purchase totaling %s with following items included: %s.\n" +
            "\n" +
            "We are pleased to inform you that your order has been Delivered. Keep Shopping with us!!. Should you have any questions or require further assistance, please do not hesitate to contact us at %s. Thank you for choosing Online Stationary Mart.\n" +
            "\n" +
            "Thanks,\n" +
            "\n" +
            "Online Stationary Mart";

    public static final String ORDER_OUT_FOR_DELIVERY_EMAIL_TEMPLATE = "Dear %s,\n" +
            "\n" +
            "Greetings for the day!." +
            "\n" +
            "This regarding your order %s with purchase totaling %s with following items included: %s.\n" +
            "\n" +
            "We are pleased to inform you that your order is Out For Delivery and will reach you in a while!. Should you have any questions or require further assistance, please do not hesitate to contact us at %s. Thank you for choosing Online Stationary Mart.\n" +
            "\n" +
            "Thanks,\n" +
            "\n" +
            "Online Stationary Mart";

    public static final String REFILLING_REQUIRED_EMAIL_TEMPLATE = "Dear Yash Bhatnagar,\n" +
            "\n" +
            "Greetings for the day!." +
            "\n" +
            "You might need to have a look at the below mentioned products whose inventory count is below your given threshold.\n" +
            "\n" +
            "Products List: %s\n" +
            "\n" +
            "Thanks,\n" +
            "\n" +
            "Online Stationary Mart";
}
