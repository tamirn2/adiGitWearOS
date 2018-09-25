package com.brock.adi.project;

import android.support.annotation.NonNull;
import android.support.wear.widget.WearableRecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ExercisesAdapter extends WearableRecyclerView.Adapter<ExercisesAdapter.ViewHolder>{

    public class ViewHolder extends WearableRecyclerView.ViewHolder{

        ImageView image;
        TextView exerciseName;
        View parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            exerciseName = itemView.findViewById(R.id.exercise_name);
            parentLayout = itemView.findViewById(R.id.parent_layout);
            parentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    FlowManager.instance.updateNFCConnected(mExerciseNames.get(position));
                }
            });
        }
    }


    private ArrayList<String> mExerciseNames = new ArrayList<>();
    private ArrayList<Integer> mExerciseImages = new ArrayList<>();

//    public ExercisesAdapter(ArrayList<String> exerciseNames, ArrayList<Integer> exerciseImages) {
    public ExercisesAdapter() {
        mExerciseNames.add("Bench Press");
        mExerciseNames.add("Bicep Curl");
        mExerciseNames.add("Push-ups");
        mExerciseNames.add("Pull-ups");
        mExerciseNames.add("Squats");
        mExerciseNames.add("Crunches");

        mExerciseImages.add(R.drawable.bench_press_dark);
        mExerciseImages.add(R.drawable.bicep_curls);
        mExerciseImages.add(R.drawable.pushups);
        mExerciseImages.add(R.drawable.pullups);
        mExerciseImages.add(R.drawable.squats);
        mExerciseImages.add(R.drawable.crunches);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.layout_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.exerciseName.setText(mExerciseNames.get(position));
        holder.image.setImageResource(mExerciseImages.get(position));
    }

    @Override
    public int getItemCount() {
        return mExerciseNames.size();
    }


}
