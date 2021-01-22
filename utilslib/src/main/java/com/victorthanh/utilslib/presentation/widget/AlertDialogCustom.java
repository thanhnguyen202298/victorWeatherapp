package com.victorthanh.utilslib.presentation.widget;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.androidadvance.topsnackbar.TSnackbar;
import com.victorthanh.utilslib.R;

public class AlertDialogCustom {
    public static TSnackbar showTopSnackbar(View view, int msgId, boolean success) {
        return showTopSnackbar(view, view.getContext().getString(msgId), success);
    }

    public static TSnackbar showTopSnackbar(View view, String message, boolean success) {
        TSnackbar snackbar = TSnackbar.make(view, message, TSnackbar.LENGTH_SHORT);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundResource(R.color.colorRuler);
        TextView textView = (TextView) snackbarView
                .findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);

        snackbar.show();
        return snackbar;
    }
}
