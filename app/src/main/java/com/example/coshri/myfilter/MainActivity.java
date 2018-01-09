package com.example.coshri.myfilter;

import android.app.Activity;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    List<String> myStringList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myStringList = new ArrayList<>();
        myStringList.add("abcd");
        myStringList.add("abef");
        myStringList.add("bbcd");
        myStringList.add("abcdrt");
        myStringList.add("babcd");


        ListView listView = (ListView) findViewById(R.id.myListView);
        final ArrayAdapter listAdapter = new ArrayAdapter(getBaseContext(), R.layout.item_layout, myStringList)
        {
            @Override
            public Filter getFilter() {
                return new Filter() {
                    @Override
                    protected FilterResults performFiltering(CharSequence constraint) {
                        List<String> list = new ArrayList<>();
                        String str = constraint.toString();

                        for (String item : myStringList)
                        {
                            if(item.startsWith(str))
                                list.add(item);
                        }

                        FilterResults filterResults = new FilterResults();
                        filterResults.values = list;
                        filterResults.count = list.size();

                        return  filterResults;
                    }

                    @Override
                    protected void publishResults(CharSequence constraint, FilterResults results) {

                        // Now we have to inform the adapter about the new list filtered
                        Toast.makeText(getContext(), "publishResults", Toast.LENGTH_LONG).show();
                        if (results.count == 0)
                        {
                            clear();
                            addAll(myStringList);
                            notifyDataSetInvalidated();
                        }
                        else {


                            clear();
                            addAll((ArrayList)results.values);
                    //        myStringList = ;
                            //changeCursor((Cursor) results.values);
                            notifyDataSetChanged();
                            //    }

                        }
                    }
                };
            }
        };


        listView.setAdapter(listAdapter);


        SearchView searchView = (SearchView)findViewById(R.id.mySearchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query)
            {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                listAdapter.getFilter().filter(newText);
                listAdapter.notifyDataSetChanged();
                return false;
            }
        });




    }
}
