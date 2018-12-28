package net.cryptobrewery.mediumarticleproject.WorkingProject;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.cryptobrewery.mediumarticleproject.R;

public class FilesAdapter extends RecyclerView.Adapter<FilesAdapter.ViewHolder> {

    private Contract.UserActions presenter;

    public FilesAdapter(Contract.UserActions presenter) {
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_file,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        presenter.setUserFiles(viewHolder,i);
    }

    @Override
    public int getItemCount() {
        return presenter.getFileCount();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView fileName;
         ViewHolder(@NonNull View itemView) {
            super(itemView);
            fileName = itemView.findViewById(R.id.TvFileName);
            itemView.setOnClickListener(v->presenter.onFileClick(getAdapterPosition()));
            itemView.setOnLongClickListener(l->{
                presenter.deleteFile(getAdapterPosition());
                return true;
            });
        }

        public TextView getFileName() {
            return fileName;
        }
    }
}
