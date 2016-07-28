package com.sebastien.testontouch.testontouch.popup;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sebastien.testontouch.R;
import com.sebastien.testontouch.testontouch.fragment.MainFragment;
import com.sebastien.testontouch.testontouch.service.impl.RssServiceImpl;

public class PopupAdd extends AlertDialog {

    private EditText editText;
    private MainFragment mainFragment;

    public PopupAdd (Context context, MainFragment mainFragment){
        super(context);
        this.mainFragment = mainFragment;
    }

    @Override
    public void onCreate(Bundle saveInstaceState){
        setTitle(R.string.title_addFlux);
        setContentView(R.layout.popup_add_flux);

        Button buttonOk = (Button) findViewById(R.id.button_ok);
        Button buttonCancel = (Button) findViewById(R.id.button_cancel);
        editText = (EditText) findViewById(R.id.editText_url);

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new RssServiceImpl().addNewTheme(editText.getText().toString(), getContext());
                mainFragment.refreshListOfAricles();
                dismiss();
            }
        });
    }
}
