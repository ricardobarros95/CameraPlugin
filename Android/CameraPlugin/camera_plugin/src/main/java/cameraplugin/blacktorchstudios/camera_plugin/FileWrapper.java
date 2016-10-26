package cameraplugin.blacktorchstudios.camera_plugin;

import java.io.File;

/**
 * Created by Ricardo.Barros on 24/10/2016.
 */

public class FileWrapper {
    public String path;
    public File file;

    public String getPath(){return path;}
    public void setPath(String path){this.path = path;}

    public File getFile(){return file;}
    public void setFile(File file){this.file = file;}

    public FileWrapper(String path, File file){
        this.path = path;
        this.file = file;
    }
}
