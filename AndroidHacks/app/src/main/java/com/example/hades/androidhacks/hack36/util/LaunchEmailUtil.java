package com.example.hades.androidhacks.hack36.util;

import android.content.Context;
import android.content.Intent;

/**
 * 用于提供反馈邮件的 intent
 */
public class LaunchEmailUtil {
    public static void launchEmailToIntent(Context context) {
        Intent msg = new Intent(Intent.ACTION_SEND);

        StringBuilder body = new StringBuilder("\n\n----------\n");
        body.append(EnvironmentInfoUtil.getApplicationInfo(context));
        msg.putExtra(Intent.EXTRA_EMAIL, new String[]{"feed@back.com"});
        msg.putExtra(Intent.EXTRA_SUBJECT, "[50AH] Feedback");
        msg.putExtra(Intent.EXTRA_TEXT, body.toString());

        msg.setType("message/rfc822");
        context.startActivity(Intent.createChooser(msg, "Send feedback"));
    }
}
