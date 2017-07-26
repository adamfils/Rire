package tech.zongogroup.rire_soteh;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

import devlight.io.library.ntb.NavigationTabBar;

/**
 * Created by user on 23-Jul-17.
 */

public class HomeActivity extends AppCompatActivity {

    NavigationTabBar navigationTabBar;
    ArrayList<NavigationTabBar.Model> models;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homeactivity);

        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.showOverflowMenu();

        navigationTabBar = (NavigationTabBar) findViewById(R.id.nav_bar);
        models = new ArrayList<>();
        models.add(new NavigationTabBar.Model.Builder(
                getResources().getDrawable(R.drawable.ic_home), Color.parseColor("#632de9"))
                .title("Globe").badgeTitle("5").build());
        models.add(new NavigationTabBar.Model.Builder(
                getResources().getDrawable(R.drawable.ic_category), Color.parseColor("#632de9"))
                .title("Hot").badgeTitle("15").build());
        models.add(new NavigationTabBar.Model.Builder(
                getResources().getDrawable(R.drawable.ic_notifications), Color.parseColor("#632de9"))
                .title("Account").badgeTitle("5").build());
        models.add(new NavigationTabBar.Model.Builder(
                getResources().getDrawable(R.drawable.ic_account), Color.parseColor("#632de9"))
                .title("Account").badgeTitle("5").build());

        navigationTabBar.setModels(models);
        navigationTabBar.setBehaviorEnabled(true);
        navigationTabBar.setTitleMode(NavigationTabBar.TitleMode.ACTIVE);
        navigationTabBar.setBadgeGravity(NavigationTabBar.BadgeGravity.TOP);
        navigationTabBar.setBadgePosition(NavigationTabBar.BadgePosition.RIGHT);
        navigationTabBar.setBadgeSize(30);
        navigationTabBar.setTitleSize(30);
        setFragment(new FragHome());
        navigationTabBar.setModelIndex(0);
        navigationTabBar.animate();



    }

    protected void setFragment(Fragment fragment){
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.fragment_frame,fragment);
        t.commit();
        navigationTabBar.setBehaviorEnabled(true);
    }

    public void UploadVideo(View v){
        startActivity(new Intent(this,UploadVideo.class));
    }
}
