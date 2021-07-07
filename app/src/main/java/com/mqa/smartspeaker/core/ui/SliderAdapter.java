package com.mqa.smartspeaker.core.ui;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.mqa.smartspeaker.MainActivity;
import com.mqa.smartspeaker.R;
import com.mqa.smartspeaker.core.domain.model.SliderItem;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderViewHolder> {

    private List<SliderItem> sliderItems;
    private ViewPager2 viewPager2;
//    private Context context;

    public SliderAdapter(List<SliderItem> sliderItems, ViewPager2 viewPager2) {
        this.sliderItems = sliderItems;
        this.viewPager2 = viewPager2;
//        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new SliderViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.item_intro, parent, false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SliderViewHolder holder, int position) {
        holder.setImage(sliderItems.get(position));
        holder.setTitle(sliderItems.get(position));
        holder.setSubtitle(sliderItems.get(position));
        holder.setState(sliderItems.get(position));
        holder.setNext(sliderItems.get(position));
    }

    @Override
    public int getItemCount() {
        return sliderItems.size();
    }

    public class SliderViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView title;
        private TextView subtitle;
        private ImageView state;
        private TextView next;
        private LinearLayout btn_next;

        SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.IV_welcome);
            title = itemView.findViewById(R.id.TV_title);
            subtitle = itemView.findViewById(R.id.TV_subtitle);
            state = itemView.findViewById(R.id.IV_state_intro);
            next = itemView.findViewById(R.id.TV_next);
            btn_next = itemView.findViewById(R.id.CL_next);
            btn_next.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (viewPager2.getCurrentItem() >= 2) {
                        Log.e("habis", "");
                        Intent intent = new Intent(v.getContext(), MainActivity.class);
                        v.getContext().startActivity(intent);
                    } else {
                        Log.e("posisi:", ""+viewPager2.getCurrentItem());
                        viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1, true);
                    }
                }
            });
        }

        public void setImage(SliderItem imageItem) {
            image.setImageResource(imageItem.getImage());
        }

        public void setTitle(SliderItem items) {
            this.title.setText(items.getTitle());
        }

        public void setSubtitle(SliderItem items) {
            this.subtitle.setText(items.getSubtitle());
        }

        public void setState(SliderItem items) {
            this.state.setImageResource(items.getState());
        }

        public void setNext(SliderItem items) {
            this.next.setText(items.getNext());
        }
    }
}
