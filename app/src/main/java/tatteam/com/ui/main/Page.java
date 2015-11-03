package tatteam.com.ui.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tatteam.com.app.BaseActivity;


/**
 * Created by ThanhNH on 9/11/2015.
 */
public abstract class Page {

    protected BaseActivity activity;
    protected View content;

    public Page(BaseActivity activity) {
        this(activity, null);
    }

    public Page(BaseActivity activity, ViewGroup parent) {
        this.activity = activity;
        if (parent != null) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
            content = inflater.inflate(getContentId(), parent, false);
        } else {
            content = View.inflate(activity, getContentId(), null);
        }
    }

    protected abstract int getContentId();

    public View getContent() {
        return content;
    }

    public void destroy() {
    }
}
