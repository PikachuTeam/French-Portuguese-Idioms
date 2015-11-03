package tatteam.com.utility;

import android.support.design.widget.TabLayout;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by ThanhNH on 9/13/2015.
 */
public class CommonUtil {

    public static void fixedChangeTextSizeTabLayout(TabLayout tabLayout, float textSize) {
        try {
            ViewGroup tab = (ViewGroup) tabLayout.getChildAt(0);
            for (int i = 0; i < tab.getChildCount(); i++) {
                ViewGroup tabView = (ViewGroup) tab.getChildAt(i);
                TextView textView = (TextView) tabView.getChildAt(1);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
            }
        } catch (Exception ex) {

        }
    }
}
