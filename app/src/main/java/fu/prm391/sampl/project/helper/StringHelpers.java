package fu.prm391.sampl.project.helper;

import android.graphics.Paint;
import android.icu.text.NumberFormat;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class StringHelpers {

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public static String currencyFormatter(Double number) {
        String COUNTRY = "US";
        String LANGUAGE = "en";
        String result = NumberFormat.getCurrencyInstance(new Locale(LANGUAGE, COUNTRY)).format(number);
        return result;
    }

    public static String currencyFormatterWithPercent(Double number, int percent) {
        String COUNTRY = "US";
        String LANGUAGE = "en";
        Double finalPrice = (number - number * percent / 100);
        String result = NumberFormat.getCurrencyInstance(new Locale(LANGUAGE, COUNTRY)).format(finalPrice);
        return result;
    }

    public static String percentageNumber(int number) {
        String result = number + "%";
        return result;
    }

    public static String numberLessThanTenFormat(int number) {
        String result = "";
        if (number < 10) {
            result = "0" + number;
        } else {
            result = String.valueOf(number);
        }
        return result;
    }
}
