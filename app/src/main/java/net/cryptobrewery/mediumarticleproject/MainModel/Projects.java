package net.cryptobrewery.mediumarticleproject.MainModel;

import java.io.File;
import java.util.ArrayList;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;


public class Projects {
    private  ArrayList<String> filesAssociated= new ArrayList<>() ;
    private static Projects Instance = new Projects();

    public static Projects getInstance(){
        if(Instance == null){
            Instance = new Projects();
        }
        return Instance;
    }


    public void addAssociatedFile( String file) {
        filesAssociated.add(file);
    }

    public  ArrayList<String> getFilesAssociated() {
        return filesAssociated;
    }


    public Observable<String> listDirs(File f) {
        return Observable.create(emitter -> {
            File[] files = f.listFiles();
            for(File file:files) {
                if(file.isDirectory())
                    emitter.onNext(file.getName());
            }
        });

    }

}
