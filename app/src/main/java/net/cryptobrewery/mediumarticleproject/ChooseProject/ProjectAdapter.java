package net.cryptobrewery.mediumarticleproject.ChooseProject;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.cryptobrewery.mediumarticleproject.R;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ViewHolder> {

    private MainContract.UserActions presenter;

     ProjectAdapter(MainContract.UserActions presenter) {
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_project,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        presenter.setUserProjects(viewHolder,i);
    }

    @Override
    public int getItemCount() {
        return presenter.fileAssocSize();
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvProjectName,tvProjectPath;
         ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProjectName = itemView.findViewById(R.id.tvProjectName);
            tvProjectPath = itemView.findViewById(R.id.TvProjectPath);
            itemView.setOnClickListener(v-> presenter.onProjectClick(getAdapterPosition()) );
        }
        public TextView getTvProjectName() {
            return tvProjectName;
        }
        public TextView getTvProjectPath() {
            return tvProjectPath;
        }


    }
}
