package in.co.recex.dtutimes.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.List;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.view.CardListView;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FacebookFeedFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FacebookFeedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FacebookFeedFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private CardListView cardListView;
    private ListView feedCardListView;

    private int ifLog;

    //facebook objects
    Request request;
    List<String> feedStringArray;
    private JSONArray dataJsonArray;

    private List<JSONObject> Jsonlist;

    private void listFacebookFeed() {

        Bundle bundle = new Bundle();
        bundle.putString("fields", "feed.fields(type,message,caption,name,full_picture,link)");
        if (Session.getActiveSession().getState() == SessionState.OPENED) {
            request = new Request(Session.getActiveSession(), "dtutimes", bundle, HttpMethod.GET, new Request.Callback() {


                @Override
                public void onCompleted(Response response) {

                    if (response.getError() != null) {
                        //TODO: add error handling code here
                        Log.e("listFacebookFeed - OnCompleted", response.getError().toString());
                    } else {
                        if (response.getGraphObject() == null) {
                            //TODO: add error handling code here
                            Log.e("listFacebookFeed - OnCompleted", "GraphObject null");
                        } else {
                            GraphObject graphObject = response.getGraphObject();
                            JSONObject feedJsonObject = (JSONObject) graphObject.getProperty("feed");

                            try {
                                dataJsonArray = feedJsonObject.getJSONArray("data");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            if (ifLog == 1) {
                                Log.d("dataJsonArray", dataJsonArray.toString());
                            }
                            //TODO: Remove the code below later on..
                            /*if (dataJsonArray!=null){
                                feedStringArray = new ArrayList<String>();

                                for (int i=0;i<dataJsonArray.length();i++){
                                    JSONObject tempJsonObject;

                                    try {
                                        tempJsonObject = dataJsonArray.getJSONObject(i);
                                        Log.d("tempJsonObject",tempJsonObject.toString());
                                        String objectType = tempJsonObject.getString("type");

                                        if (objectType.equals("status") &&tempJsonObject.has("message")) {
                                            feedStringArray.add(tempJsonObject.getString("message"));
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                            }*/
                            try {
                                initializeListView();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            });
        } else {
            //go back to login screen
            if (ifLog == 1)
                Log.e("listFacebookFeed", "Session isnt active bitch.");
            Intent intent = new Intent(getActivity(), FbLoginActivity.class);
            getActivity().startActivity(intent);
        }

        request.executeAsync();
    }

    private void initializeListView() throws JSONException {
        //TODO: instantiate adapter, and set adapter to view here
        /*final ArrayList<Card> cardList = new ArrayList<Card>();
        for (int i =0; i< dataJsonArray.length();i++){
            JSONObject jsonObject=null;
            try {
                 jsonObject = dataJsonArray.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            GooglePlaySmallCard card= null;
            if (jsonObject!=null) {
                 if (ifLog==1)
                     Log.d("dataJsonObject",jsonObject.toString());
                 card = new GooglePlaySmallCard(getActivity(), jsonObject);
            }

            if (card!=null)
                cardList.add(card);




        CardArrayAdapter cardArrayAdapter = new CardArrayAdapter(getActivity(),cardList);
        if (cardListView!=null)
            cardListView.setAdapter(cardArrayAdapter);
            */


        ArrayAdapter<JSONObject> listAdapter = new ArrayAdapter<JSONObject>(getActivity(), R.layout.facebook_feed_fragment_list_item) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                // return super.getView(position, convertView, parent);
                convertView = getActivity().getLayoutInflater().inflate(R.layout.facebook_feed_fragment_list_item, null);
                JSONObject item = getItem(position);

                AQuery aQuery = listAQ.recycle(convertView);
                aQuery.id(R.id.feedItemType).text(item.optString("type"));
                if (item.has("full_picture"))
                    aQuery.progress(R.id.progress_circular).id(R.id.feedImageView).image(item.optString("full_picture"), true, true, 300, 0, null, AQuery.FADE_IN_NETWORK, 1.0f);

                String type = item.optString("type");
                String message = item.optString("message");
                String caption = item.optString("caption");
                String link = item.optString("link");
                String name = item.optString("name");

                if (type.equals("link")) {
                    //TODO: setup code to get onclick listener
                    aQuery.id(R.id.feedItemType).text("Link"); //type text view
                    if (name != null) {
                        aQuery.id(R.id.feedMessage).text(name);
                    }
                }

                if (type.equals("status")) {
                    if (message != null) {
                        aQuery.id(R.id.feedMessage).text(message);
                    }
                    aQuery.id(R.id.feedItemType).text("Status");
                }

                if (type.equals("photo")) {
                    aQuery.id(R.id.feedItemType).text("Photo");
                    if (caption != null) {
                        aQuery.id(R.id.feedMessage).text(caption);
                    }
                }
                return convertView;

            }
        };
        for (int j = 0; j < dataJsonArray.length(); j++) {
            if (dataJsonArray.optJSONObject(j) != null) {
                if (!(dataJsonArray.optJSONObject(j).optString("type").equals("status") && (dataJsonArray.optJSONObject(j).optString("message") == null || dataJsonArray.optJSONObject(j).optString("message").equals(""))))
                    listAdapter.add(dataJsonArray.getJSONObject(j));
            }
        }

        feedCardListView.setAdapter(listAdapter);

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FacebookFeedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FacebookFeedFragment newInstance(String param1, String param2) {
        FacebookFeedFragment fragment = new FacebookFeedFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public FacebookFeedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_facebook_feed, container, false);
        if (view != null) {
            feedCardListView = (ListView) view.findViewById(R.id.feedListView);
        }
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        ifLog = Integer.valueOf(getActivity().getString(R.string.ifLog));
        listFacebookFeed();

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }


    /**
     * This class provides a simple card as Google Play
     *
     * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
     */
    public class GooglePlaySmallCard extends Card {

        protected TextView messageTextView;
        protected TextView typeTextView;
        protected ImageView facebookImageView;

        protected String facebookImageURL;


        protected String message;
        protected String type;
        protected String caption;
        protected String link;
        protected String name;

        public File image;

        protected JSONObject cardData;


        public GooglePlaySmallCard(Context context, JSONObject jsonObject) {
            this(context, R.layout.facebook_feed_fragment_list_item, jsonObject);
        }

        public GooglePlaySmallCard(Context context, int innerLayout, JSONObject jsonObject) {
            super(context, innerLayout);
            cardData = jsonObject;
            init();
        }

        private void init() {


            try {

                type = cardData.getString("type");
                if (cardData.has("message"))
                    message = cardData.getString("message");
                if (cardData.has("caption")) {
                    caption = cardData.getString("caption");
                }
                if (cardData.has("link"))
                    link = cardData.getString("link");
                if (cardData.has("full_picture"))
                    facebookImageURL = cardData.getString("full_picture");
                if (cardData.has("name"))
                    name = cardData.getString("name");
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

        @Override
        public void setupInnerViewElements(ViewGroup parent, View view) {

            //Retrieve elements
            messageTextView = (TextView) parent.findViewById(R.id.feedMessage);
            typeTextView = (TextView) parent.findViewById(R.id.feedItemType);
            facebookImageView = (ImageView) parent.findViewById(R.id.feedImageView);

                /*if (facebookImageURL != null) {
                    // TODO: load photo from link onto facebookImageView using androidQuery here.
                    facebookImageView.setVisibility(View.VISIBLE);
                    typeTextView.setText("Photo");
                    AQuery aq = new AQuery(getActivity());
                    WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
                    Display display = wm.getDefaultDisplay();

                    Point size = new Point();
                    display.getSize(size);
                    aq.id(R.id.feedImageView).progress(R.id.progress_circular).image(facebookImageURL, true, true, 0, 0, null, AQuery.FADE_IN_NETWORK, 1.0f);
                }*/


            if (type.equals("link")) {
                if (link != null) {
                    setOnClickListener(new OnCardClickListener() {
                        @Override
                        public void onClick(Card card, View view) {
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                            getActivity().startActivity(browserIntent);
                        }
                    });
                }
                typeTextView.setText("Link");
                if (name != null) {
                    messageTextView.setText(name);
                }
            }

            if (type.equals("status")) {
                if (message != null) {
                    messageTextView.setText(message);
                }
                typeTextView.setText("Status");
            }

            if (type.equals("photo")) {
                typeTextView.setText("Photo");
                if (caption != null) {
                    messageTextView.setText(caption);
                }
            }
            if (facebookImageURL != null) {
                // TODO: load photo from link onto facebookImageView using androidQuery here.
                AQuery aQuery = new AQuery(getActivity());
                facebookImageView.setVisibility(View.VISIBLE);
                image = aQuery.getCachedFile(facebookImageURL);
                while (image == null) {
                }
                Bitmap bitmap = BitmapFactory.decodeFile(image.getPath());
                facebookImageView.setImageBitmap(bitmap);
            }

        }


    }

    AQuery listAQ = new AQuery(getActivity());


}
