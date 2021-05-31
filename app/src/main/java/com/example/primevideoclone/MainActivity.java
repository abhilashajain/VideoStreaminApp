package com.example.primevideoclone;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.primevideoclone.adapter.BannerMoviesPagerAdapter;
import com.example.primevideoclone.adapter.MainRecyclerAdapter;
import com.example.primevideoclone.model.AllCategory;
import com.example.primevideoclone.model.BannerMovies;
import com.example.primevideoclone.model.CategoryItem;
import com.example.primevideoclone.retrofit.RetrofitClient;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

//import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
//import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;

public class MainActivity extends AppCompatActivity {

    BannerMoviesPagerAdapter bannerMoviesPagerAdapter;
    TabLayout indicatorTab, categoryTab;
    ViewPager bannerMoviesViewPager;
    List<BannerMovies> homeBannerList;
    List<BannerMovies> tvShowList;
    List<BannerMovies> movieBannerList;
    List<BannerMovies> kidsBannerList;
    Timer slideTimer;

    NestedScrollView nestedScrollView;
    AppBarLayout appBarLayout;

    MainRecyclerAdapter mainRecyclerAdapter;
    RecyclerView mainRecycler;
    List<AllCategory> allCategoryList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        indicatorTab = findViewById(R.id.tab_indicator);
        categoryTab = findViewById(R.id.tabLayout);
        nestedScrollView = findViewById(R.id.nested_scroll);
        appBarLayout = findViewById(R.id.appbar);

        homeBannerList = new ArrayList<>();
        tvShowList = new ArrayList<>();
        movieBannerList = new ArrayList<>();
        kidsBannerList = new ArrayList<>();

        //fetch banner data from server
        getBannerDate();

        categoryTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch(tab.getPosition()) {
                    case 1:
                        setScrollDefaultState();
                        setBannerMoviesPagerAdapter(tvShowList);
                        return;
                    case 2:
                        setScrollDefaultState();
                        setBannerMoviesPagerAdapter(movieBannerList);
                        return;
                    case 3:
                        setScrollDefaultState();
                        setBannerMoviesPagerAdapter(kidsBannerList);
                        return;
                    default:
                        setScrollDefaultState();
                        setBannerMoviesPagerAdapter(homeBannerList);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        List<CategoryItem> homeCatListItem1 = new ArrayList<>();
        homeCatListItem1.add(new CategoryItem(1, "GOLD", "https://awsstreaming.s3.us-east-2.amazonaws.com/gold.jpg","https://awsstreaming.s3.us-east-2.amazonaws.com/9convert.com+-+Gold+Theatrical+Trailer++Akshay+Kumar++Mouni++Kunal++Amit++Vineet++Sunny++15th+August+2018.mp4"));
        homeCatListItem1.add(new CategoryItem(2, "Mortal Kombat", "https://awsstreaming.s3.us-east-2.amazonaws.com/mortal.jpg","https://awsstreaming.s3.us-east-2.amazonaws.com/9convert.com+-+Mortal+Kombat+2021++Official+Red+Band+Trailer.mp4"));
        homeCatListItem1.add(new CategoryItem(3, "Schindlers List", "https://awsstreaming.s3.us-east-2.amazonaws.com/schindler.jpg","https://awsstreaming.s3.us-east-2.amazonaws.com/9convert.com+-+The+Girl+in+Red++Schindlers+List+39+Movie+CLIP+1993+HD.mp4"));
        homeCatListItem1.add(new CategoryItem(4, "Inception", "https://awsstreaming.s3.us-east-2.amazonaws.com/inception.jpg","https://awsstreaming.s3.us-east-2.amazonaws.com/Inception+(2010)+Official+Trailer+%231+-+Christopher+Nolan+Movie+HD.mp4"));
        homeCatListItem1.add(new CategoryItem(5, "The Dark Knight", "https://awsstreaming.s3.us-east-2.amazonaws.com/darkknight.jpg","https://awsstreaming.s3.us-east-2.amazonaws.com/9convert.com+-+Batman+interrogates+the+Joker++The+Dark+Knight+4k+HDR.mp4"));

        List<CategoryItem> homeCatListItem2 = new ArrayList<>();
        homeCatListItem2.add(new CategoryItem(1,"Formula 1 - Drive to survive ", "https://awsstreaming.s3.us-east-2.amazonaws.com/wp8271780.jpg","https://awsstreaming.s3.us-east-2.amazonaws.com/9convert.com+-+Formula+1+Drive+to+Survive++Official+Trailer+HD++Netflix.mp4"));
        homeCatListItem2.add(new CategoryItem(2, "Rocky III - Eye of the Tiger", "https://awsstreaming.s3.us-east-2.amazonaws.com/u-g-Q1BUC0M0.jpg","https://awsstreaming.s3.us-east-2.amazonaws.com/9convert.com+-+Rocky+III++Eye+of+the+Tiger++Survivor.mp4"));
        homeCatListItem2.add(new CategoryItem(3, "Grand Tour", "https://awsstreaming.s3.us-east-2.amazonaws.com/690bf8103809a1d8b5d72174d4f37206a8b3e1435b04bb1efa38e47b94dce79d._RI_V_TTW_.jpg","https://awsstreaming.s3.us-east-2.amazonaws.com/The+Grand+Tour+_+Season+1+_+Official+Trailer.mp4"));
        homeCatListItem2.add(new CategoryItem(4, "All or Nothing Tottenham Hotspur", "https://awsstreaming.s3.us-east-2.amazonaws.com/allornothing.jpg","https://awsstreaming.s3.us-east-2.amazonaws.com/All+or+Nothing+_+Tottenham+Hotspur+_+Best+Moments.mp4"));
        homeCatListItem2.add(new CategoryItem(5, "Manchester City : All or nothing", "https://awsstreaming.s3.us-east-2.amazonaws.com/mancity.jpg","https://awsstreaming.s3.us-east-2.amazonaws.com/9convert.com+-+All+or+Nothing+Manchester+City++Amazon+Prime+Original+Trailer.mp4"));

        List<CategoryItem> homeCatListItem3 = new ArrayList<>();
        homeCatListItem3.add(new CategoryItem(1, "Ice Age", "https://awsstreaming.s3.us-east-2.amazonaws.com/iceage.jpg","https://awsstreaming.s3.us-east-2.amazonaws.com/9convert.com+-+ICE+AGE+All+Best+Movie+Clips+2002.mp"));
        homeCatListItem3.add(new CategoryItem(2, "Shrek meets Donkey", "https://awsstreaming.s3.us-east-2.amazonaws.com/shrek.jpg","https://awsstreaming.s3.us-east-2.amazonaws.com/9convert.com+-+Shrek+meets+Donkey.mp4"));
        homeCatListItem3.add(new CategoryItem(3, "Stork ", "https://awsstreaming.s3.us-east-2.amazonaws.com/Storks.jpg","https://awsstreaming.s3.us-east-2.amazonaws.com/9convert.com+-+Storks+2016++Wolves+Love+Babies+Scene+310++Movieclips.mp4"));
        homeCatListItem3.add(new CategoryItem(4, "Cars", "https://awsstreaming.s3.us-east-2.amazonaws.com/cars.jpg","https://awsstreaming.s3.us-east-2.amazonaws.com/9convert.com+-+Best+Opening+Races+From+Pixars+Cars++Pixar+Cars.mp4"));
        homeCatListItem3.add(new CategoryItem(5, "Finding Nemo", "https://awsstreaming.s3.us-east-2.amazonaws.com/findingnemo.jpg","https://awsstreaming.s3.us-east-2.amazonaws.com/cars.jpg"));

        List<CategoryItem> homeCatListItem4 = new ArrayList<>();
        homeCatListItem4.add(new CategoryItem(1, "Dark", "https://awsstreaming.s3.us-east-2.amazonaws.com/Dark.png","https://awsstreaming.s3.us-east-2.amazonaws.com/9convert.com+-+Dark++Teaser+HD++Netflix.mp4"));
        homeCatListItem4.add(new CategoryItem(2, "Money Heist", "https://awsstreaming.s3.us-east-2.amazonaws.com/MoneyHeist.jpg","https://awsstreaming.s3.us-east-2.amazonaws.com/9convert.com+-+Money+Heist++Part+1+Recap+Casa+de+Papel+English.mp4"));
        homeCatListItem4.add(new CategoryItem(3, "Inside Edge", "https://awsstreaming.s3.us-east-2.amazonaws.com/inside-edge.JPEG","https://awsstreaming.s3.us-east-2.amazonaws.com/Inside+Edge+_+(Explicit)++Official+Trailer+%5BHD%5D+_+All+Episodes+July+10+2017+_+Amazon+Prime+Video.mp4"));
        homeCatListItem4.add(new CategoryItem(4, "Prison Break", "https://awsstreaming.s3.us-east-2.amazonaws.com/prison.jpg","https://awsstreaming.s3.us-east-2.amazonaws.com/Top+10+Best+Prison+Break+Moments.mp4"));
        homeCatListItem4.add(new CategoryItem(5, "The Office", "https://awsstreaming.s3.us-east-2.amazonaws.com/the+office.jpg","https://s3.console.aws.amazon.com/s3/object/awsstreaming?region=us-east-2&prefix=9convert.com+-+Fire+Drill+++The+Office+US.mp4"));


        allCategoryList = new ArrayList<>();
        allCategoryList.add(new AllCategory(1, "Watch next TV and movies", homeCatListItem1));
        allCategoryList.add(new AllCategory(2, "Sports", homeCatListItem2));
        allCategoryList.add(new AllCategory(3, "Kids and family movies", homeCatListItem3));
        allCategoryList.add(new AllCategory(3, "Amazon Orignal series", homeCatListItem4));


        //here we pass array to recycler setup method
        setMainRecycler(allCategoryList);

    }

    private void setBannerMoviesPagerAdapter(List<BannerMovies> bannerMoviesList){
        bannerMoviesViewPager = findViewById(R.id.banner_viewPager);
        bannerMoviesPagerAdapter = new BannerMoviesPagerAdapter(this,bannerMoviesList);
        bannerMoviesViewPager.setAdapter(bannerMoviesPagerAdapter);
        indicatorTab.setupWithViewPager(bannerMoviesViewPager);

        slideTimer = new Timer();
        slideTimer.scheduleAtFixedRate(new Autoslider(), 4000, 6000);
        indicatorTab.setupWithViewPager(bannerMoviesViewPager, true);
    }

    class Autoslider extends TimerTask {

        @Override
        public void run() {
            MainActivity.this.runOnUiThread(() -> {
                if (bannerMoviesViewPager.getCurrentItem() < homeBannerList.size() - 1) {
                    bannerMoviesViewPager.setCurrentItem(bannerMoviesViewPager.getCurrentItem() + 1);
                } else {
                    bannerMoviesViewPager.setCurrentItem(0);
                }
            });
        }
    }

    public void setMainRecycler(List<AllCategory> allCategoryList){
        mainRecycler = findViewById(R.id.main_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        mainRecycler.setLayoutManager(layoutManager);
        mainRecyclerAdapter = new MainRecyclerAdapter(this, allCategoryList);
        mainRecycler.setAdapter(mainRecyclerAdapter);

    }

    private void setScrollDefaultState(){
        nestedScrollView.fullScroll(View.FOCUS_UP);
        nestedScrollView.scrollTo(0,0);
        appBarLayout.setExpanded(true);
    }

    private void getBannerDate(){
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(RetrofitClient.getRetrofitClient().getAllBanners()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<BannerMovies>>() {
                    @Override
                    public void onNext(List<BannerMovies> bannerMovies) {
                        for(int i = 0; i < bannerMovies.size(); i++){
                            if(bannerMovies.get(i).getBannerCategoryId().toString().equals("1")){
                                homeBannerList.add(bannerMovies.get(i));
                            }else if(bannerMovies.get(i).getBannerCategoryId().toString().equals("2")){
                                tvShowList.add(bannerMovies.get(i));
                            }else if(bannerMovies.get(i).getBannerCategoryId().toString().equals("3")){
                                movieBannerList.add(bannerMovies.get(i));
                            }else if(bannerMovies.get(i).getBannerCategoryId().toString().equals("4")){
                                kidsBannerList.add(bannerMovies.get(i));
                            }else{

                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("bannerData", ""+e);
                    }

                    @Override
                    public void onComplete() {
                        //this is default tab selected
                        setBannerMoviesPagerAdapter(homeBannerList);
                    }
                })

        );
    }
}