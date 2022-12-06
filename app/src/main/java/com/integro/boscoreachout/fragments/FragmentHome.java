 package com.integro.boscoreachout.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.demono.AutoScrollViewPager;
import com.integro.boscoreachout.R;
import com.integro.boscoreachout.activities.AboutUsActivity;
import com.integro.boscoreachout.activities.OurProjectsActivity;
import com.integro.boscoreachout.activities.WebActivity;
import com.integro.boscoreachout.adapters.CoverPhotosAdapter;
import com.integro.boscoreachout.adapters.NewsViewPagerAdapter;
import com.integro.boscoreachout.apis.ApiClients;
import com.integro.boscoreachout.apis.ApiServices;
import com.integro.boscoreachout.model.CoverPhotos;
import com.integro.boscoreachout.model.CoverPhotosList;
import com.integro.boscoreachout.model.News;
import com.integro.boscoreachout.model.NewsList;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentHome extends Fragment {

    private static final String TAG = "FragmentHome";

    TextView tvAboutUs, tvOurProjects, tvSupportCenter;
    LinearLayout llDonate;

    private ApiServices apiServices;
    private AutoScrollViewPager viewPagerNews;
    private ArrayList<News> newsArrayList;
    Call<NewsList> newsListCall;
    private NewsViewPagerAdapter newsViewPagerAdapter;

    private AutoScrollViewPager vpCoverPhotos;
    private ArrayList<CoverPhotos> coverPhotosArrayList;
    Call<CoverPhotosList> coverPhotosListCall;
    private CoverPhotosAdapter coverPhotosAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        apiServices = ApiClients.getClients().create(ApiServices.class);

        newsArrayList = new ArrayList<>();
        viewPagerNews = view.findViewById(R.id.au_news);

        coverPhotosArrayList = new ArrayList<>();
        vpCoverPhotos = view.findViewById(R.id.vpCoverPhotos);

        tvAboutUs = view.findViewById(R.id.tv_aboutus);
        llDonate = view.findViewById(R.id.ll_donate);
        tvOurProjects = view.findViewById(R.id.tv_ourprojects);
        tvSupportCenter = view.findViewById(R.id.tv_supportcenter);

        tvOurProjects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentOurProjects = new Intent(getContext(), OurProjectsActivity.class);
                startActivity(intentOurProjects);
            }
        });
        tvAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAboutUs = new Intent(getContext(), AboutUsActivity.class);
                startActivity(intentAboutUs);
            }
        });

        llDonate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), WebActivity.class);
                intent.putExtra("TAG","http://www.bronews.org/payment.php");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });
        getNewsList();
        getCoverPhotosList();
        return view;
    }

    public void getNewsList() {
        String date = "2020-11-16 02:01:41";
        newsListCall = apiServices.getNewsList(date);
        newsListCall.enqueue(new Callback<NewsList>() {
            @Override
            public void onResponse(Call<NewsList> call, Response<NewsList> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess() == 1) {
                        int size = response.body().getNewsArrayList().size();
                        if (size > 0) {
                            newsArrayList.addAll(response.body().getNewsArrayList());
                            newsViewPagerAdapter = new NewsViewPagerAdapter(getContext(), newsArrayList);
                            viewPagerNews.setAdapter(newsViewPagerAdapter);
                            viewPagerNews.startAutoScroll(3000);
                            viewPagerNews.setCycle(true);

                        } else {
                            Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<NewsList> call, Throwable t) {
                Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getCoverPhotosList() {
        coverPhotosListCall = apiServices.getCoverPhotosLIst();
        coverPhotosListCall.enqueue(new Callback<CoverPhotosList>() {
            @Override
            public void onResponse(Call<CoverPhotosList> call, Response<CoverPhotosList> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                if (response.body().getCoverPhotosArrayList() == null) {
                    return;
                }
                int size = response.body().getCoverPhotosArrayList().size();
                Log.d(TAG, "onResponse: size "+size);
                if (size > 0) {
                    coverPhotosArrayList.addAll(response.body().getCoverPhotosArrayList());
                    coverPhotosAdapter = new CoverPhotosAdapter(getContext(), coverPhotosArrayList);
                    vpCoverPhotos.setAdapter(coverPhotosAdapter);
                    vpCoverPhotos.startAutoScroll(3000);
                    vpCoverPhotos.setCycle(true);
                }
            }

            @Override
            public void onFailure(Call<CoverPhotosList> call, Throwable t) {
                Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}