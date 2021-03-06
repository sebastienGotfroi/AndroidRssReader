package com.sebastien.testontouch.testontouch.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.sebastien.testontouch.R;
import com.sebastien.testontouch.testontouch.bean.Flux;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Sébastien on 18-07-16.
 */
public class DeleteFluxCellAdapter  extends RecyclerView.Adapter<DeleteFluxCellAdapter.MyViewHolder>{

    private Set<Flux> listFlux;

    private Set<Flux> listSelectedFlux = new HashSet<>();

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.cell_flux_delete, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.display((Flux) listFlux.toArray()[position]);
    }

    @Override
    public int getItemCount() {
        return listFlux ==null ? 0 :listFlux.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final CheckBox checkBox;

        private final TextView textViewFlux;
        private Flux currentFlux;

        public MyViewHolder (final View itemView) {
            super(itemView);

            checkBox = ((CheckBox) itemView.findViewById(R.id.checkbox_delete));

            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(checkBox.isChecked() == true)
                        listSelectedFlux.add(currentFlux);
                    else
                        listSelectedFlux.remove(currentFlux);
                }
            });

            textViewFlux = ((TextView) itemView.findViewById(R.id.flux_delete));
        }

        public void display (Flux flux){
            currentFlux = flux;
            textViewFlux.setText(currentFlux.displayFlux());
        }

    }
    public Set<Flux> getListFlux() {
        return listFlux;
    }

    public void setListFlux(Set<Flux> listFlux) {
        this.listFlux = listFlux;
    }

    public Set<Flux> getListSelectedFlux() {
        return listSelectedFlux;
    }

    public void setListSelectedFlux(Set<Flux> listSelectedFlux) {
        this.listSelectedFlux = listSelectedFlux;
    }
}
