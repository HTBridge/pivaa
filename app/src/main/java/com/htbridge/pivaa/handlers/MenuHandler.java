package com.htbridge.pivaa.handlers;


import android.app.Activity;
import android.app.Service;
import android.content.Intent;

import com.htbridge.pivaa.AboutActivity;
import com.htbridge.pivaa.BroadcastReceiverActivity;
import com.htbridge.pivaa.ContentProviderActivity;
import com.htbridge.pivaa.DatabaseActivity;
import com.htbridge.pivaa.EncryptionActivity;
import com.htbridge.pivaa.LoadCodeActivity;
import com.htbridge.pivaa.MainActivity;
import com.htbridge.pivaa.R;
import com.htbridge.pivaa.SerializeActivity;
import com.htbridge.pivaa.ServiceActivity;
import com.htbridge.pivaa.WebviewActivity;

public class MenuHandler extends Activity {
    public MenuHandler() {

    }

    /**
     * Menu router
     * @param mActivity
     * @param itemId
     * @return
     */
    public boolean route(Activity mActivity, int itemId) {
        switch (itemId) {
            case R.id.action_settings:
                return true;

            case R.id.action_login:
                mActivity.startActivity(new Intent(mActivity, MainActivity.class));
                return true;

            case R.id.action_encryption:
                mActivity.startActivity(new Intent(mActivity, EncryptionActivity.class));
                return true;

            case R.id.action_webview:
                mActivity.startActivity(new Intent(mActivity, WebviewActivity.class));
                return true;

            case R.id.action_database:
                mActivity.startActivity(new Intent(mActivity, DatabaseActivity.class));
                return true;

            case R.id.action_load_code:
                mActivity.startActivity(new Intent(mActivity, LoadCodeActivity.class));
                return true;

            case R.id.action_service:
                mActivity.startActivity(new Intent(mActivity, ServiceActivity.class));
                return true;

            case R.id.action_broadcast_receiver:
                mActivity.startActivity(new Intent(mActivity, BroadcastReceiverActivity.class));
                return true;

            case R.id.action_serialize:
                mActivity.startActivity(new Intent(mActivity, SerializeActivity.class));
                return true;

            case R.id.action_content_provider:
                mActivity.startActivity(new Intent(mActivity, ContentProviderActivity.class));
                return true;

            case R.id.action_about:
                mActivity.startActivity(new Intent(mActivity, AboutActivity.class));
                return true;

            default:
                return true;
                //return super.onOptionsItemSelected(item);
        }
    }
}
