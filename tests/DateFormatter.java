import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatter {

    static long format(String humanReadableTime) {
        DateFormat formatter = new SimpleDateFormat("hh:mm:ss");
        try {
            Date date = formatter.parse(humanReadableTime);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
