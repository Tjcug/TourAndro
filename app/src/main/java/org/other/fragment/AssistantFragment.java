package org.other.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.other.Constant.Constant;

import tour.com.activity.MainActivity;
import tour.com.activity.R;

/**
 * Created by dell-pc on 2016/1/1.
 */
public class AssistantFragment extends BaseFragment{
    private MainActivity mMainActivity ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO 自动生成的方法存根
        super.onCreate(savedInstanceState);
        mMainActivity = (MainActivity)getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contactsLayout = inflater.inflate(R.layout.fragment_assistant,
                container, false);
        return contactsLayout;
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.currFragTag= Constant.FRAGMENT_FLAG_Assistant;
    }

}
