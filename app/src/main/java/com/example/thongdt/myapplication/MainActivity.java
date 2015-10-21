package com.example.thongdt.myapplication;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.thongdt.myapplication.activities.DetailActivity;
import com.example.thongdt.myapplication.activities.DetailActivity_;
import com.example.thongdt.myapplication.adapter.MenuRecyclerViewAdapter;
import com.example.thongdt.myapplication.fragment.DataFragment;
import com.example.thongdt.myapplication.fragment.DataFragment_;
import com.example.thongdt.myapplication.fragment.NewsFragment;
import com.example.thongdt.myapplication.fragment.NewsFragment_;
import com.example.thongdt.myapplication.fragment.VideoFragment;
import com.example.thongdt.myapplication.fragment.VideoFragment_;
import com.example.thongdt.myapplication.objects.Menu;
import com.example.thongdt.myapplication.utils.HeaderBar;
import com.example.thongdt.myapplication.utils.Link;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.File;
import java.util.ArrayList;

import static android.view.Gravity.START;

@EActivity(R.layout.activity_main)
public class MainActivity extends FragmentActivity {

    @ViewById(R.id.headerBar)
    HeaderBar mHeaderBar;

    @ViewById(R.id.frContainFragment)
    FrameLayout mFrameLayout;

    @ViewById(R.id.recyclerViewMenu)
    RecyclerView mRecyclerViewMenu;

    @ViewById(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @ViewById(R.id.tvLinkApplication)
    TextView mTvLinkApplication;

    private ArrayList<Menu> mMenus;
    private MenuRecyclerViewAdapter mMenuRecyclerViewAdapter;
    private boolean mIsDataFragment;

    @AfterViews
    void init() {

        File cacheDir = StorageUtils.getCacheDirectory(this);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .memoryCacheExtraOptions(480, 800) // default = device screen dimensions
                .diskCacheExtraOptions(480, 800, null)
                .threadPoolSize(3) // default
                .threadPriority(Thread.NORM_PRIORITY - 2) // default
                .tasksProcessingOrder(QueueProcessingType.FIFO) // default
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024)
                .memoryCacheSizePercentage(13) // default
                .diskCache(new UnlimitedDiskCache(cacheDir)) // default
                .diskCacheSize(50 * 1024 * 1024)
                .diskCacheFileCount(100)
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
                .imageDownloader(new BaseImageDownloader(this)) // default
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
                .writeDebugLogs()
                .build();

        ImageLoader.getInstance().init(config);

        mDrawerLayout.setScrimColor(0x00000000);

        mMenus = new ArrayList<>();
        mMenuRecyclerViewAdapter = new MenuRecyclerViewAdapter(mMenus);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerViewMenu.setLayoutManager(layoutManager);
        mRecyclerViewMenu.setAdapter(mMenuRecyclerViewAdapter);

        setListener();
    }

    private void setListener() {

        final FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        final NewsFragment newsFragment = new NewsFragment_();
        final DataFragment dataFragment = new DataFragment_();
        final VideoFragment videoFragment = new VideoFragment_();
        fragmentTransaction.add(R.id.frContainFragment, newsFragment).commit();
        mTvLinkApplication.setText(getResources().getString(R.string.textview_header_bar_news));

        getListMenuNews();
        mMenuRecyclerViewAdapter.notifyDataSetChanged();

        mHeaderBar.setOnItemClickListener(new HeaderBar.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                if (position == 0) {
                    openDrawer();
                } else if (position == 1) {
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frContainFragment, newsFragment).commit();
                    newsFragment.loadDocument();
                    getListMenuNews();
                    mTvLinkApplication.setText(getResources().getString(R.string.textview_header_bar_news));
                    mMenuRecyclerViewAdapter.notifyDataSetChanged();
                    mIsDataFragment = false;

                } else if (position == 2) {
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frContainFragment, dataFragment).commit();
                    getListMenuData();
                    mTvLinkApplication.setText(getResources().getString(R.string.textview_header_bar_data) + " > " + mMenus.get(0).getTitle());
                    mMenuRecyclerViewAdapter.notifyDataSetChanged();
                    mIsDataFragment = true;

                } else {
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frContainFragment, videoFragment).commit();
                    mTvLinkApplication.setText(getResources().getString(R.string.textview_header_bar_live_score));

                    mMenus.clear();
                    mMenuRecyclerViewAdapter.notifyDataSetChanged();
                }
            }
        });

        mDrawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                mHeaderBar.setBackgroundMenuSelected();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                mHeaderBar.setBackgroundMenuDefault();
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

        mMenuRecyclerViewAdapter.setOnItemClickListener(new MenuRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                mDrawerLayout.closeDrawer(Gravity.LEFT);
                setListDefault();
                mMenus.get(position).setSelected(true);
                mMenuRecyclerViewAdapter.notifyDataSetChanged();


                if (mIsDataFragment) {
                    mTvLinkApplication.setText(getResources().getString(R.string.textview_header_bar_data) + " > " + mMenus.get(position).getTitle());
                    dataFragment.loadInformationGame(Link.LINK_STARTED[position], Link.LINK_NOT_START[position]);
                } else {
                    mTvLinkApplication.setText(getResources().getString(R.string.textview_header_bar_news) + " > " + mMenus.get(position).getTitle());
                    if (position == 0) {
                        newsFragment.loadReportList(mMenus.get(position).getUrl());
                    } else {
                        newsFragment.loadDataSection(mMenus.get(position).getUrl());
                    }
                }
            }
        });
    }

    private void setListDefault() {
        for (int i = 0; i < mMenus.size(); i++) {
            mMenus.get(i).setSelected(false);
        }
    }

    @SuppressWarnings("ResourceType")
    private void openDrawer() {
        mDrawerLayout.openDrawer(START);
    }

    private void getListMenuData() {
        mMenus.clear();
        String[] title = getResources().getStringArray(R.array.list_recycler_menu_data);
        for (int i = 0; i < title.length; i++) {
            Menu menu = new Menu();
            menu.setTitle(title[i]);
            mMenus.add(menu);
        }
    }

    private void getListMenuNews() {
        mMenus.clear();
        String sTitles[] = getResources().getStringArray(R.array.list_recycler_menu_title);
        String sUrls[] = getResources().getStringArray(R.array.list_recycler_menu_url);
        for (int i = 0; i < sTitles.length; i++) {
            Menu menu = new Menu();
            menu.setTitle(sTitles[i]);
            menu.setUrl(sUrls[i]);
            mMenus.add(menu);
        }
    }
}
