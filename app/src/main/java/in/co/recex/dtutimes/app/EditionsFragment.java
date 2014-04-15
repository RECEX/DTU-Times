package in.co.recex.dtutimes.app;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.androidquery.AQuery;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Use the {@link EditionsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditionsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    AQuery listAQ = new AQuery(getActivity());

    ListView EditionsListView;

    JSONArray dataJsonArray;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditionsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditionsFragment newInstance(String param1, String param2) {
        EditionsFragment fragment = new EditionsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public EditionsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        //TODO: remove this line in the final code

        JSONObject staticData;
        try {
             staticData = new JSONObject("{\"link\":\"http://dtutimes.dce.edu/others/editions/DTUTIMESEDITION23.pdf\",\"photo\":\"http://dtutimes.dce.edu/images/published/23.jpg\",\"date\":\"April 2013\"}");
            Log.d("static Json Object" , staticData.toString());
            dataJsonArray = new JSONArray("[{\"link\":\"http://dtutimes.dce.edu/others/editions/DTUTIMESEDITION23.pdf\",\"photo\":\"http://dtutimes.dce.edu/images/published/23.jpg\",\"date\":\"April 2013\"}]");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_editions, container, false);
        if (view != null) {
            EditionsListView = (ListView) view.findViewById(R.id.editionsListView);
        }
        EditionsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(dataJsonArray.optJSONObject(i).optString("link"))));
            }
        });
        return view;
    }

    private void initializeListView() throws JSONException {


        ArrayAdapter<JSONObject> listAdapter = new ArrayAdapter<JSONObject>(getActivity(), R.layout.facebook_feed_fragment_list_item) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                // return super.getView(position, convertView, parent);
                convertView = getActivity().getLayoutInflater().inflate(R.layout.editions_fragment_list_item,null);
                JSONObject item = getItem(position);

                AQuery aQuery = listAQ.recycle(convertView);
                aQuery.id(R.id.editionsDate).text(item.optString("date"));
                if (item.has("photo"))
                    aQuery.progress(R.id.progress_circular).id(R.id.editionsPhoto).image(item.optString("photo"), true, true, 300, 0, null, AQuery.FADE_IN_NETWORK, 1.0f);
                return convertView;

            }
        };
        for (int j = 0; j < dataJsonArray.length(); j++) {
            if (dataJsonArray.optJSONObject(j) != null) {
                    listAdapter.add(dataJsonArray.getJSONObject(j));
            }
        }
        EditionsListView.setAdapter(listAdapter);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        getEditionsFromServerAsync fromServerAsync = new getEditionsFromServerAsync();
        fromServerAsync.execute();
    }

    public class getEditionsFromServerAsync extends AsyncTask <Void,Void,Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            JSONParser parser = new JSONParser();
            //TODO:set url variable over here
            String url = null;
            //TODO:Uncomment this code when getting from internet
            //dataJsonArray = parser.getJSONFromUrl(url,null);
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            //if (dataJsonArray!=null) {
                try {
                    initializeListView();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
        //    }
        //    else {
                //TODO:enter error handling mechanism here
         //   }
        }
    }
}
