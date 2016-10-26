package cameraplugin.blacktorchstudios.camera_plugin;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.unity3d.player.UnityPlayer;
import com.unity3d.player.UnityPlayerActivity;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Ricardo.Barros on 25/10/2016.
 */

public class IntermediateActivity extends Activity {

    public static final int CAMERA_REQUEST = 1888;

    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        launchCamera();
    }

    private static FileWrapper createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = UnityPlayer.currentActivity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        String currentPhotoPath = "file:" + image.getAbsolutePath();
        FileWrapper fileWrapper = new FileWrapper(currentPhotoPath, image);
        return fileWrapper;
    }

    public void launchCamera(){
        Log.d("THIS_TAG", "bananas are rich in potassium");
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photoFile = null;
        FileWrapper fw = null;
        try{
            fw = createImageFile();
            fileWrapper = fw;
            photoFile = fw.getFile();
        }
        catch(IOException e){

        }
        if(photoFile != null){
            Uri fa = Uri.fromFile(photoFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fa);
            intent.putExtra("CameraFilePath", photoFile.getAbsolutePath());
            startActivityForResult(intent, CAMERA_REQUEST);
        }
    }
    FileWrapper fileWrapper = null;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
        super.onActivityResult(requestCode, resultCode, intent);
        Log.d("THIS_TAG", "bananas are also a great source of Magnesium");

        if(requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK){
            if(fileWrapper == null){
                Log.d("THIS_TAG", "fileWrapper is null");
            }else{
                Log.d("THIS_TAG", "intent is not null");
                UnityPlayer.UnitySendMessage("holder", "DealWithPhoto", fileWrapper.getFile().getAbsolutePath());
            }
            //UnityPlayer.UnitySendMessage("holder", "DealWithPhoto", intent.getExtras().getString("CameraFilePath"));
            //Log.d("THIS_TAG", intent.getExtras().getString("CameraFilePath"));
        }
        setResult(resultCode);
        finish();
    }
}
