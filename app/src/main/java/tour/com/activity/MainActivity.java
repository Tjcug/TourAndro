package tour.com.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import org.other.Constant.Constant;
import org.other.fragment.BaseFragment;
import org.other.ui.BottomControlPanel.BottomPanelCallback;
import org.other.ui.BottomControlPanel;

public class MainActivity extends BaseActivity implements
        BottomPanelCallback{
    private BottomControlPanel bottomPanel = null;
    private FragmentManager fragmentManager = null;
    private FragmentTransaction fragmentTransaction = null;

    public static String currFragTag = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        fragmentManager = getFragmentManager();
        setDefaultFirstFragment(Constant.FRAGMENT_FLAG_Assistant);
    }

    private void initUI() {
        bottomPanel = (BottomControlPanel) findViewById(R.id.bottom_layout);
        if (bottomPanel != null) {
            bottomPanel.initBottomPanel();
            bottomPanel.setBottomCallback(this);
        }
    }

    @Override
    public void onBottomPanelClick(int itemId) {
        String tag = "";
        if ((itemId & Constant.BTN_FLAG_Person) != 0) {
            tag = Constant.FRAGMENT_FLAG_Person;
        } else if ((itemId & Constant.BTN_FLAG_Forum) != 0) {
            tag = Constant.FRAGMENT_FLAG_Forum;
        }
        else if ((itemId & Constant.BTN_FLAG_Assistant) != 0) {
            tag = Constant.FRAGMENT_FLAG_Assistant;
        }
        setTabSelection(tag); // 切换Fragment
    }
    private void setDefaultFirstFragment(String tag) {
        Log.i("yan", "setDefaultFirstFragment enter... currFragTag = "
                + currFragTag);
        setTabSelection(tag);
        bottomPanel.defaultBtnChecked();
        Log.i("yan", "setDefaultFirstFragment exit...");
    }

    private void commitTransactions(String tag) {
        if (fragmentTransaction != null && !fragmentTransaction.isEmpty()) {
            fragmentTransaction.commit();
            currFragTag = tag;
            fragmentTransaction = null;
        }
    }

    private FragmentTransaction ensureTransaction() {
        if (fragmentTransaction == null) {
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

        }
        return fragmentTransaction;

    }

    private void attachFragment(int layout, Fragment f, String tag) {
        if (f != null) {
            if (f.isDetached()) {
                ensureTransaction();
                fragmentTransaction.attach(f);

            } else if (!f.isAdded()) {
                ensureTransaction();
                fragmentTransaction.add(layout, f, tag);
            }
        }
    }

    private Fragment getFragment(String tag) {

        Fragment f = fragmentManager.findFragmentByTag(tag);

        if (f == null) {
            //Toast.makeText(getApplicationContext(),"fragment = null tag = " + tag, Toast.LENGTH_SHORT).show();
            f = BaseFragment.newInstance(getApplicationContext(), tag);
        }
        return f;

    }

    private void detachFragment(Fragment f) {

        if (f != null && !f.isDetached()) {
            ensureTransaction();
            fragmentTransaction.detach(f);
        }
    }

    /**
     * 切换fragment
     *
     * @param tag
     */
    private void switchFragment(String tag) {
        if (TextUtils.equals(tag, currFragTag)) {
            return;
        }
        // 把上一个fragment detach掉
        if (currFragTag != null && !currFragTag.equals("")) {
            detachFragment(getFragment(currFragTag));
        }
        attachFragment(R.id.fragment_content, getFragment(tag), tag);
        commitTransactions(tag);
    }

    /**
     * 设置选中的Tag
     *
     * @param tag
     */
    public void setTabSelection(String tag) {
        // 开启一个Fragment事务
        fragmentTransaction = fragmentManager.beginTransaction();
        if (TextUtils.equals(tag, Constant.FRAGMENT_FLAG_Forum)) {
            Toast.makeText(MainActivity.this,"论坛",Toast.LENGTH_LONG).show();
        } else if (TextUtils.equals(tag, Constant.FRAGMENT_FLAG_Assistant)) {
            Toast.makeText(MainActivity.this,"私人助手",Toast.LENGTH_LONG).show();
        }else if (TextUtils.equals(tag, Constant.FRAGMENT_FLAG_Person)) {
            Toast.makeText(MainActivity.this,"个人主页",Toast.LENGTH_LONG).show();
        }
		/*
		 * if(TextUtils.equals(tag, Constant.FRAGMENT_FLAG_MESSAGE)){ if
		 * (messageFragment == null) { messageFragment = new MessageFragment();
		 * }
		 *
		 * }else if(TextUtils.equals(tag, Constant.FRAGMENT_FLAG_CONTACTS)){ if
		 * (contactsFragment == null) { contactsFragment = new
		 * ContactsFragment(); }
		 *
		 * }else if(TextUtils.equals(tag, Constant.FRAGMENT_FLAG_NEWS)){ if
		 * (newsFragment == null) { newsFragment = new NewsFragment(); }
		 *
		 * }else if(TextUtils.equals(tag,Constant.FRAGMENT_FLAG_SETTING)){ if
		 * (settingFragment == null) { settingFragment = new SettingFragment();
		 * } }else if(TextUtils.equals(tag, Constant.FRAGMENT_FLAG_SIMPLE)){ if
		 * (simpleFragment == null) { simpleFragment = new SimpleFragment(); }
		 *
		 * }
		 */
		/*
		 * if(TextUtils.equals(tag,Constant.FRAGMENT_FLAG_Contents)){
		 *
		 * } else
		 */
        switchFragment(tag);

    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        currFragTag = "";
    }
}
