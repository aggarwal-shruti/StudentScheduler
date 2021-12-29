package com.example.studentscheduler.Adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.studentscheduler.Data;
import com.example.studentscheduler.R;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private TextView textView;       //created manually
    private String[] localDataSet;
    List<Data> list = new CopyOnWriteArrayList<>();

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */

    // step 1: Initialize the dataset of the Adapter.

    public CustomAdapter(String[] dataset)
    {
        localDataSet = dataset;

    }

    // step 2 : Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.activity_custom_adapter, viewGroup, false);
        //created manually
        return new ViewHolder(view);
    }

    //  step 3 : Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element

        viewHolder.setIsRecyclable(false);
        viewHolder.textView.setText(localDataSet[position]);
        String time = viewHolder.textView.getText().toString();
        final String[] myData = {""};
        viewHolder.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                myData[0] = s.toString();

                Data data = new Data();
                data.setTime(viewHolder.textView.getText().toString());
                data.setData(myData[0]);
                if(!list.isEmpty())
                {
                    for(Data d:list)
                    {
                        if(d.getTime().equals(time))
                            list.remove(d);

                    }
                    list.add(data);

                }
                else
                {
                    list.add(data);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });


        for(Data d:list)
        {
            if(time==d.getTime())
                viewHolder.editText.setText(d.getData());
        }



               //manually created
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.length;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private  TextView textView;
        private EditText editText;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            textView = (TextView) view.findViewById(R.id.custom_adapter_text);
            editText = view.findViewById(R.id.custom_adapter_editText);

        }

    }

    public List<Data> getList()
    {
        return list;
    }


}

