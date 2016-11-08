package cameraplugin.blacktorchstudios.camera_plugin;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.unity3d.player.UnityPlayer;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Ricardo.Barros on 25/10/2016.
 */

public class IntermediateActivity extends Activity {

    public static final int CAMERA_REQUEST = 1888;

    public String path;
    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);

        launchCamera();
    }

    private static File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File im = null;
        if(UnityPlayer.currentActivity == null){
            Log.e("THAT_TAG", "Unity current Activity is null");
        }
        else{
            File storageDir = UnityPlayer.currentActivity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            im = new File(storageDir, imageFileName);
            return im;
        }
        return im;
    }

    public void launchCamera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photoFile = null;
        try{
            photoFile = createImageFile();
            path = photoFile.getPath();
        }
        catch(IOException e){
            Log.e("THAT_TAG", e.getMessage());
        }
        if(photoFile != null){
            Uri fa = Uri.fromFile(photoFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fa);
            intent.putExtra("CameraFilePath", photoFile.getAbsolutePath());
            startActivityForResult(intent, CAMERA_REQUEST);
        }
   }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
        super.onActivityResult(requestCode, resultCode, intent);

        if(requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK){
            try{
                UnityPlayer.UnitySendMessage("holder", "DealWithPhoto", path);
            }catch(Exception e){
                Log.e("THAT_TAG", e.getMessage());
            }
        }

        setResult(resultCode);
        finish();
    }
}
