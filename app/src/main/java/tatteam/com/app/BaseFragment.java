package tatteam.com.app;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tatteam.com.R;


/**
 * Created by ThanhNH on 2/3/2015.
 */

public class BaseFragment extends Fragment {

    public static void replaceFragment(FragmentManager fragmentManager, BaseFragment newFragment, String fragmentTag,
                                       String transactionName) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.fragment_slide_right_enter, R.anim.fragment_slide_left_exit,
                R.anim.fragment_slide_left_enter, R.anim.fragment_slide_right_exit);
        transaction.replace(R.id.main_fragment, newFragment, fragmentTag);
        transaction.addToBackStack(transactionName);
        transaction.commit();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        ((BaseActivity) getActivity()).loadADIfNeed();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void onBackPressed() {
        getFragmentManager().popBackStack();
    }

    public void makeSnackBar(String s) {
        BaseActivity activity = (BaseActivity) getActivity();
        activity.makeSnackBar(s);
    }

    public BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }

}
