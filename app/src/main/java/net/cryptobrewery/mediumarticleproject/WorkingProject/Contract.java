package net.cryptobrewery.mediumarticleproject.WorkingProject;

import net.cryptobrewery.mediumarticleproject.ChooseProject.ProjectAdapter;

public interface Contract {
    interface ViewContract{
        void setCode(String code);
        String getCode();
        void showToast(String message);
        void refreshFiles();
        void deleteFileWarning();
    }
    interface UserActions{
        void onDestroy();
        void setUserFiles(FilesAdapter.ViewHolder viewHolder, int pos);
        void onFileClick(int adapterPosition);
        int getFileCount();
        void codeChanged();
        void addFile(String filename);
        void deleteFile(int pos);
        void saveCurFile();
    }
}
