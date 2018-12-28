package net.cryptobrewery.mediumarticleproject.MainModel;

import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.schedulers.Schedulers;

public class WorkingProject {

    private String projectName;
    private String projectFullPath;
    private List<String> projectFiles = new ArrayList<>();

    private static WorkingProject Instance;

    public static WorkingProject getInstance(){
        if(Instance == null){
            Instance = new WorkingProject();
        }
        return Instance;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectFullPath() {
        return projectFullPath;
    }

    public void setProjectFullPath(String projectFullPath) {
        this.projectFullPath = projectFullPath;
    }

    public void addFileToProject(String a){
            projectFiles.add(a);
    }

    public Observable<String> listFilesInDir(File f) {
        return Observable.create(emitter -> {
            File[] files = f.listFiles();
            for(File file:files) {
                if(!file.isDirectory())
                    emitter.onNext(file.getName());
            }
            emitter.onComplete();
        });
    }

    public List<String> getProjectFiles() {
        return projectFiles;
    }
}
