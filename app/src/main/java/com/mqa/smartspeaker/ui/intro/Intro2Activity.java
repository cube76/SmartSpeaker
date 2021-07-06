package com.mqa.smartspeaker.ui.intro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.mqa.smartspeaker.R;
import com.mqa.smartspeaker.core.domain.model.SliderItem;
import com.mqa.smartspeaker.core.ui.SliderAdapter;
import com.mqa.smartspeaker.databinding.ActivityIntroBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Intro2Activity extends AppCompatActivity{

    private ViewPager2 viewPager2;
    private ActivityIntroBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIntroBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        viewPager2 = findViewById(R.id.VP_intro);

        List<SliderItem> sliderItems = new ArrayList<>();
        sliderItems.add(new SliderItem(R.drawable.intro_1, getString(R.string.kenalan), getString(R.string.kenalan_sub), R.drawable.state_1, "Lewati"));
        sliderItems.add(new SliderItem(R.drawable.intro_2, getString(R.string.hadir), getString(R.string.hadir_sub), R.drawable.state_2, "Lewati"));
        sliderItems.add(new SliderItem(R.drawable.intro_3, getString(R.string.mulai), getString(R.string.mulai_sub), R.drawable.state_3, "Mulai"));

        viewPager2.setAdapter(new SliderAdapter(sliderItems, viewPager2));

        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull @NotNull View page, float position) {
                float r =1-Math.abs(position);
                page.setScaleY(0.85f+r*0.15f);
            }
        });
        viewPager2.setPageTransformer(compositePageTransformer);
    }

}