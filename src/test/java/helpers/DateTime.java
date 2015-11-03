package helpers;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Gadzilla on 01.11.2015.
 */
public class DateTime {

    public static String getCurrentTime(String dateTimeFormat) {
        return new SimpleDateFormat(dateTimeFormat).format(Calendar.getInstance().getTime());
    }

}
