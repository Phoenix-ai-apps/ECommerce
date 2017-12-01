package com.demo.ecommerce.utils;


import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.demo.ecommerce.R;

public class DialogUtils {



    public static Dialog showValidationDailog(Context context, String message) {

        Dialog alert = new Dialog(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View alertDlgView = inflater.inflate(R.layout.dialog_validation, null);

        TextView txtMessage = (TextView) alertDlgView.findViewById(R.id.txt_validation);
     //   txtMessage.setTransformationMethod(null);

        Button btnClose = (Button) alertDlgView.findViewById(R.id.btn_close);
        btnClose.setTransformationMethod(null);

        if (message != null && message.trim().length() > 0) {
            txtMessage.setText(message.trim());
        }

        btnClose.setOnClickListener(v -> alert.dismiss());

        alert.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alert.setContentView(alertDlgView);
        alert.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        alert.setCanceledOnTouchOutside(false);
        alert.show();

        return alert;
    }


}
