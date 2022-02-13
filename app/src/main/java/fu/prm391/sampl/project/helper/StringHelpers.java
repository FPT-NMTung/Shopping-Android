package fu.prm391.sampl.project.helper;

import android.icu.text.NumberFormat;
import android.text.TextUtils;
import android.util.Patterns;

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
}
