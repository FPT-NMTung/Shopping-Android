package fu.prm391.sampl.project.helper;

import android.text.TextUtils;
import android.util.Patterns;

public class StringHelpers {

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}
