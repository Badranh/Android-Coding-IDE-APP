package net.cryptobrewery.mediumarticleproject.ChooseProject;

import android.os.Environment;

import net.cryptobrewery.mediumarticleproject.FileUtils.FileSystem;
import net.cryptobrewery.mediumarticleproject.MainModel.Projects;
import net.cryptobrewery.mediumarticleproject.MainModel.WorkingProject;
import java.io.File;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

class Presenter implements MainContract.UserActions {
    private MainContract.ViewContract view;
    private Projects model;
    private WorkingProject model1;
    private  Disposable subscribe;
    private FileSystem fileSystem;
    private static final File root = new File(Environment.getExternalStorageDirectory()+File.separator+"projects");


    Presenter(MainContract.ViewContract  view) {
        this.view = view;
        init();
    }

    private void init() {
        //init filesystem object
        fileSystem = new FileSystem();
        //this will initialize main directory of projects for only one time
        shouldWeCreateDirectory();
        //get all dirs in main projects dir with RxJava
        model = Projects.getInstance();
        model1 = WorkingProject.getInstance();
        model.getFilesAssociated().clear();
            subscribe = model.listDirs(root)
                  .observeOn(Schedulers.io())
                  .subscribeOn(AndroidSchedulers.mainThread())
                   .subscribe(s -> {
                        model.addAssociatedFile(s);
                        view.refreshRecycler();
                      });

    }

    @Override
     public void setUserProjects(ProjectAdapter.ViewHolder viewHolder, int pos) {
            String path = "sdcard/projects/"+model.getFilesAssociated().get(pos);
            viewHolder.getTvProjectName().setText(model.getFilesAssociated().get(pos));
            viewHolder.getTvProjectPath().setText(path);
     }

    @Override
    public void onDestroy() {
        if(subscribe!=null && !subscribe.isDisposed())
            subscribe.dispose();
        model = null;
    }

    @Override
    public int fileAssocSize() {
        return model.getFilesAssociated().size();
    }

    @Override
    public void onProjectClick(int adapterPosition) {
        //clear previously loaded file if any...
        model1.getProjectFiles().clear();
        //show loading
        view.showLoading(model.getFilesAssociated().get(adapterPosition));
        //set the full path of project we chose
        model1.setProjectFullPath(root.getPath()+"/"+model.getFilesAssociated().get(adapterPosition));
        //get all files in directory
        model1.listFilesInDir(new File(root.getAbsoluteFile()+"/"+model.getFilesAssociated().get(adapterPosition)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(String s) {
                        model1.addFileToProject(s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.cancelLoading();
                        view.showToast("Project Loading Failed,Please Try Again");
                    }
                    @Override
                    public void onComplete() {
                        view.moveToWorkingProject();
                    }
                });
    }

    @Override
    public void createProject(String projectName) {
        if(projectName.contains(".") || projectName.contains(" ")){
            view.showToast("Wrong Project Name");
            return;
        }
        if(fileSystem.createDir(Environment.getExternalStorageDirectory()+File.separator+"projects/"+projectName)){
            model.addAssociatedFile(projectName);
            view.showToast("project successfully created");
        }else{
            view.showToast("project already found");
        }
    }


    private void shouldWeCreateDirectory(){
        fileSystem.createDir(Environment.getExternalStorageDirectory()+File.separator+"projects/");
    }




 }
