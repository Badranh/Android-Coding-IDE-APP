package net.cryptobrewery.mediumarticleproject.ChooseProject;

public interface MainContract {

    interface ViewContract{
        void showLoading(String projectName);
        void refreshRecycler();
        void moveToWorkingProject();
        void showToast(String message);
        void cancelLoading();

    }

    interface UserActions{
        void setUserProjects(ProjectAdapter.ViewHolder viewHolder,int pos);
        void onDestroy();
        int fileAssocSize();
        void onProjectClick(int adapterPosition);
        void createProject(String projectName);
    }
}
