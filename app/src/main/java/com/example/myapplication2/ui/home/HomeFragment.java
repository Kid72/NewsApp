package com.example.myapplication2.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication2.CategoryEnt;
import com.example.myapplication2.NewsEnt;
import com.example.myapplication2.HomeRecyclerViewAdapter;
import com.example.myapplication2.R;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment implements HomeRecyclerViewAdapter.ItemClickListener{

    private HomeViewModel homeViewModel;
    private RecyclerView homeRecyclerView;
    private static String MY_PREFS_NAME = "LAST_UPDATE";
    private HomeRecyclerViewAdapter homeRecyclerViewAdapter;

    private ArrayList<NewsEnt> arrayList = new ArrayList<>();

    private List<NewsEnt> mData;
    private LayoutInflater mInflater;
    private HomeRecyclerViewAdapter.ItemClickListener mClickListener;
    private Context context;
    private RealmResults<NewsEnt> newsArray;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        homeRecyclerView = root.findViewById(R.id.homeRecyclerView);
        homeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));



        Realm realm = Realm.getDefaultInstance();
        newsArray = realm.where(NewsEnt.class).findAll();

        homeRecyclerViewAdapter = new HomeRecyclerViewAdapter(getContext(), newsArray, true);
        homeRecyclerViewAdapter.setClickListener(this);



        getNews();

        homeRecyclerView.setAdapter(homeRecyclerViewAdapter);
        homeRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1)) {
                    Toast.makeText(getContext(), "Last", Toast.LENGTH_LONG).show();
                    new GetOldNews().execute();
                }
            }
        });

        return root;
    }



    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(getContext(), "position: " + position, Toast.LENGTH_SHORT).show();
    }


    private void getNews(){
        SharedPreferences shared = getContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        if(shared.getLong("update", 0) < 1){
            SharedPreferences.Editor editor = getContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
            editor.putLong("update", System.currentTimeMillis());
            editor.apply();
            new GetOldNews().execute();

        }else{
            long time = 600;
            System.out.println(System.currentTimeMillis());
            System.out.println(shared.getLong("update", 0));
            if(System.currentTimeMillis() - shared.getLong("update", 0) > time){
                new GetOldNews().execute();
            }
        }



    }

    private class GetData extends AsyncTask<String, Void, JSONArray> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(getActivity(), "23", Toast.LENGTH_SHORT).show();



        }

        @Override
        protected JSONArray doInBackground(String... params) {
            try {
                JSONArray json = new JSONArray(IOUtils.toString(new URL("http://10.0.2.2:8080/home/education/news"), Charset.forName("UTF-8")));
                return json;

            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(JSONArray result) {
            super.onPostExecute(result);
            for(int i = 0; i < result.length(); i++){
                try {
                    JSONObject jsonObject = result.getJSONObject(i);
                    NewsEnt newsEnt = new NewsEnt(
                            jsonObject.getString("id"),
                            jsonObject.getString("last_update"),
                            jsonObject.getString("modification_date"),
                            jsonObject.getInt("sequence"),
                            jsonObject.getInt("owner_site"),
                            jsonObject.getString("created_date"),
                            jsonObject.getString("description"),
                            jsonObject.getString("lang"),
                            jsonObject.getString("link"),
                            jsonObject.getString("image_link"),
                            jsonObject.getString("category")
                    );


                    Realm realm = Realm.getDefaultInstance();
                    RealmResults<NewsEnt> realmResults = realm.where(NewsEnt.class)
                            .equalTo("sequence", newsEnt.getSequence()).findAll();
                    if(realmResults.isEmpty()) {
                        realm.beginTransaction();
                        realm.copyToRealm(newsEnt);
                        realm.commitTransaction();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            Realm realm = Realm.getDefaultInstance();
            newsArray = realm.where(NewsEnt.class).findAll();
            homeRecyclerViewAdapter.updateRealmResults(newsArray);
            homeRecyclerViewAdapter.notifyDataSetChanged();



            //textView.setText(result.toString());
        }

    }

    public String getOldSequenceByCategory(String category){
        Realm realm = Realm.getDefaultInstance();
        RealmResults<NewsEnt> realmResults = realm.where(NewsEnt.class).equalTo("category", category).sort("sequence", Sort.ASCENDING).findAll();
        if(realmResults.isEmpty()){
            return null;
        }else{
            return String.valueOf(realmResults.get(0).getSequence());
        }

    }

    public String getNewSequenceByCategory(String category){
        Realm realm = Realm.getDefaultInstance();
        RealmResults<NewsEnt> realmResults = realm.where(NewsEnt.class).equalTo("category", category).sort("sequence", Sort.DESCENDING).findAll();
        if(realmResults.isEmpty()){
            return null;
        }else{
            return String.valueOf(realmResults.get(0).getSequence());
        }

    }


    private class GetOldNews extends AsyncTask<String, Void, ArrayList<JSONArray>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(getActivity(), "23", Toast.LENGTH_SHORT).show();



        }

        @Override
        protected ArrayList<JSONArray> doInBackground(String... params) {
            ArrayList<JSONArray> jsonList = new ArrayList<>();
            try {

                Realm realm = Realm.getDefaultInstance();
                RealmResults<CategoryEnt> realmResults = realm.where(CategoryEnt.class).findAll();
                for (CategoryEnt categoryEnt : realmResults){
                    String oldSequence = getOldSequenceByCategory(String.valueOf(categoryEnt.getNumber()));
                    if(oldSequence == null){
                        jsonList.add(new JSONArray(IOUtils.toString(new URL("http://10.0.2.2:8080/home/education/newscategory/" + categoryEnt.getNumber()), Charset.forName("UTF-8"))));
                    }else{
                        jsonList.add(new JSONArray(IOUtils.toString(new URL("http://10.0.2.2:8080/home/education/oldnews/" + oldSequence), Charset.forName("UTF-8"))));
                    }

                }


                return jsonList;

            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<JSONArray> resultArray) {
            super.onPostExecute(resultArray);

        if(resultArray != null && !resultArray.isEmpty()) {
            for (JSONArray result : resultArray) {
                for (int i = 0; i < result.length(); i++) {
                    try {
                        JSONObject jsonObject = result.getJSONObject(i);
                        NewsEnt newsEnt = new NewsEnt(
                                jsonObject.getString("id"),
                                jsonObject.getString("last_update"),
                                jsonObject.getString("modification_date"),
                                jsonObject.getInt("sequence"),
                                jsonObject.getInt("owner_site"),
                                jsonObject.getString("created_date"),
                                jsonObject.getString("description"),
                                jsonObject.getString("lang"),
                                jsonObject.getString("link"),
                                jsonObject.getString("image_link"),
                                jsonObject.getString("category")
                        );


                        Realm realm = Realm.getDefaultInstance();
                        RealmResults<NewsEnt> realmResults = realm.where(NewsEnt.class)
                                .equalTo("sequence", newsEnt.getSequence()).findAll();
                        if (realmResults.isEmpty()) {
                            realm.beginTransaction();
                            realm.copyToRealm(newsEnt);
                            realm.commitTransaction();
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
            Realm realm = Realm.getDefaultInstance();
            newsArray = realm.where(NewsEnt.class).findAll();
            homeRecyclerViewAdapter.updateRealmResults(newsArray);
            homeRecyclerViewAdapter.notifyDataSetChanged();



            //textView.setText(result.toString());
        }

    }


    private static String getCategoryName(String categoryNumber, Context context){
        List<String> arrayList = Arrays.asList(context.getResources().getStringArray(R.array.categories));
        return arrayList.get(Integer.parseInt(categoryNumber));
    }

    private static Drawable getCategoryBackground(String categoryNumber, Context context){
        if(categoryNumber.equals("0")){
            return context.getResources().getDrawable(R.drawable.gradient_1_rose);
        }else if (categoryNumber.equals("1")){
            return context.getResources().getDrawable(R.drawable.gradient_2_blue);
        }else if (categoryNumber.equals("2")){
            return context.getResources().getDrawable(R.drawable.gradient_3_purple);
        }else if (categoryNumber.equals("3")){
            return context.getResources().getDrawable(R.drawable.gradient_4_piglet);
        }else if (categoryNumber.equals("4")){
            return context.getResources().getDrawable(R.drawable.gradient_5_mauve);
        }else if (categoryNumber.equals("5")){
            return context.getResources().getDrawable(R.drawable.gradient_6_grey);
        }else if (categoryNumber.equals("6")){
            return context.getResources().getDrawable(R.drawable.gradient_7_memory);
        }else if (categoryNumber.equals("7")){
            return context.getResources().getDrawable(R.drawable.gradient_8_social);
        }else if (categoryNumber.equals("8")){
            return context.getResources().getDrawable(R.drawable.gradient_9_cherry);
        }else if (categoryNumber.equals("9")){
            return context.getResources().getDrawable(R.drawable.gradient_10_pinky);
        }else if (categoryNumber.equals("10")){
            return context.getResources().getDrawable(R.drawable.gradient_11_lush);
        }else if (categoryNumber.equals("11")){
            return context.getResources().getDrawable(R.drawable.gradient_12_kashmir);
        }else if (categoryNumber.equals("12")){
            return context.getResources().getDrawable(R.drawable.gradient_13_green);
        }else if (categoryNumber.equals("13")){
            return context.getResources().getDrawable(R.drawable.gradient_14_blue);
        }else if (categoryNumber.equals("14")){
            return context.getResources().getDrawable(R.drawable.gradient_15_cyan);
        }else{
            return context.getResources().getDrawable(R.drawable.gradient_15_cyan);
        }

    }

}
