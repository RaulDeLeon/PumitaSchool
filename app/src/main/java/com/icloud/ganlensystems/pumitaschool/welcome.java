package com.icloud.ganlensystems.pumitaschool;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class welcome extends AppCompatActivity {


    private ViewPager mPager;
    private int[] layouts = {R.layout.activity_ventana_1,R.layout.activity_ventana_2,
            R.layout.activity_ventana_3,R.layout.activity_ventana_4,R.layout.activity_ventana_5};
    private MpagerAdapter mpagerAdapter;
    private LinearLayout Dots_Layout;
    private ImageView[] dots;
    private Button Bnskip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(new PreferenceManager(this).checkPreference())
        {
            loadHome();
        }

        if(Build.VERSION.SDK_INT >= 19)
        {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        setContentView(R.layout.activity_welcome);

        mPager = (ViewPager) findViewById(R.id.viewPager);
        mpagerAdapter = new MpagerAdapter(layouts,this);
        mPager.setAdapter(mpagerAdapter);

        Dots_Layout = (LinearLayout) findViewById(R.id.dotsLayout);
        Bnskip = (Button) findViewById(R.id.btnSkip);
        Bnskip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadHome();
                new PreferenceManager(welcome.this).writePrefeneces();
            }
        });
        createDots(0);

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                createDots(position);
                if(position == layouts.length-1)
                {
                    Bnskip.setText("Iniciar");
                }
                else {
                    Bnskip.setText("Omitir");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void createDots(int current_position)
    {
        if(Dots_Layout!=null)
            Dots_Layout.removeAllViews();

        dots = new ImageView[layouts.length];

        for (int i=0;i<layouts.length;i++)
        {
            dots[i] = new ImageView(this);
            if(i == current_position)
            {
                dots[i].setImageDrawable(ContextCompat.getDrawable(this,R.drawable.inactive_dots));
            }
            else {
                dots[i].setImageDrawable(ContextCompat.getDrawable(this,R.drawable.active_dots));
            }

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(5,0,5,0);

            Dots_Layout.addView(dots[i],params);

        }
    }

    private void loadHome()
    {
        startActivity(new Intent(welcome.this,bienvenido.class));
        finish();
    }

}
