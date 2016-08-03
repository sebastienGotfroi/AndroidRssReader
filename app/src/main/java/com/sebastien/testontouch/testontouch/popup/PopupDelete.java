package com.sebastien.testontouch.testontouch.popup;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.sebastien.testontouch.R;
import com.sebastien.testontouch.testontouch.Adapter.DeleteFluxCellAdapter;
import com.sebastien.testontouch.testontouch.fragment.MainFragment;
import com.sebastien.testontouch.testontouch.service.impl.RssServiceImpl;

/**
 * Created by Sébastien on 18-07-16.
 */
public class PopupDelete extends AlertDialog {

    private DeleteFluxCellAdapter deleteFluxCellAdapter;
    private MainFragment mainFragment;

    public PopupDelete (Context context, MainFragment mainFragment){
        super(context);
        this.mainFragment = mainFragment;
    }

    @Override
    public void onCreate(Bundle saveInstaceState){
        setTitle(R.string.title_deleteFlux);
        setContentView(R.layout.popup_delete_flux);

        Button buttonDelete = (Button) findViewById(R.id.button_delete);
        Button buttonCancel = (Button) findViewById(R.id.button_cancel);

        final RecyclerView rv = (RecyclerView) findViewById(R.id.list_flux_delete);
        rv.setLayoutManager(new LinearLayoutManager(getOwnerActivity()));

        deleteFluxCellAdapter = new DeleteFluxCellAdapter();
        rv.setAdapter(deleteFluxCellAdapter);

        deleteFluxCellAdapter.setListFlux(RssServiceImpl.getRssService().getThemes(this.getContext()));

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RssServiceImpl.getRssService().deleteThemes(getContext(), deleteFluxCellAdapter.getListSelectedFlux());
                mainFragment.refreshListOfAricles();
                dismiss();
            }
        });
    }
}
