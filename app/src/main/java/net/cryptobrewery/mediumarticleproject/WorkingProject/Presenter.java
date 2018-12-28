package net.cryptobrewery.mediumarticleproject.WorkingProject;

import android.os.Handler;
import net.cryptobrewery.mediumarticleproject.FileUtils.FileSystem;
import net.cryptobrewery.mediumarticleproject.MainModel.WorkingProject;
import java.io.File;

public class Presenter implements Contract.UserActions {

    private WorkingProject model = WorkingProject.getInstance();
    private Contract.ViewContract view;
    private String currentFile;
    private boolean codeChangedSinceLastSave = false;
    private FileSystem fileSystem;

     Presenter(Contract.ViewContract view) {
        this.view = view;
        init();
    }

    private void init() {
         fileSystem = new FileSystem();
    }

    @Override
    public void onDestroy() {
        fileSystem = null;
    }

    @Override
    public void setUserFiles(FilesAdapter.ViewHolder viewHolder, int pos) {
        viewHolder.getFileName().setText(model.getProjectFiles().get(pos));
    }

    @Override
    public void onFileClick(int adapterPosition) {
        currentFile = model.getProjectFiles().get(adapterPosition);
        File file = new File(model.getProjectFullPath(),currentFile);
        view.setCode(fileSystem.readFile(file));
    }

    @Override
    public int getFileCount() {
        return model.getProjectFiles().size();
    }

    @Override
    public void codeChanged() {
        codeChangedSinceLastSave=true;
    }

    @Override
    public void addFile(String filename) {
         if(filename.contains(" ") || !filename.contains(".") ){
             view.showToast("File name is not correct;");
             return;
         }
        if(fileSystem.createFile(filename,model.getProjectFullPath())){
            view.showToast("File Created");
            model.addFileToProject(filename);
            view.refreshFiles();
        }else{
            view.showToast("File Already Exists");
        }
    }

    @Override
    public void deleteFile(int pos) { view.deleteFileWarning();
        if(fileSystem.deleteFile(model.getProjectFullPath()+"/"+model.getProjectFiles().get(pos))){
            model.getProjectFiles().remove(pos);
            view.refreshFiles();
        }else{
            view.showToast("Failed To Delete");
        }
    }

    @Override
    public void saveCurFile() {
        view.showToast("Saving...");
        if(codeChangedSinceLastSave && !view.getCode().trim().isEmpty() && currentFile!=null){
            if(fileSystem.saveFile(view.getCode(),model.getProjectFullPath(),currentFile)){
                view.showToast("Saved Successfully");
            }else{
                view.showToast("Couldn't Save ");
            }
        }
        else
            view.showToast("file has not been selected/changed");
    }


}
