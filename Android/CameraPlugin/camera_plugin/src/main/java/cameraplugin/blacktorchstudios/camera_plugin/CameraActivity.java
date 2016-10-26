package cameraplugin.blacktorchstudios.camera_plugin;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.support.v4.content.*;

import com.unity3d.player.UnityPlayer;
import com.unity3d.player.UnityPlayerActivity;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.R.attr.data;
import static android.content.ContentValues.TAG;
import static java.lang.System.out;

public class CameraActivity extends UnityPlayerActivity {

    public static CameraActivity instance;
    private static final int CAMERA_REQUEST = 1888;

    public static CameraActivity getInstance() {
        if (instance == null) {
            instance = new CameraActivity();
            return instance;
        } else {
            return instance;
        }
    }
    private String CurrentPhotoPath = "";

    private static FileWrapper createImageFile() throws IOException{
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
            photoFile = fw.getFile();
        }
        catch(IOException e){

        }
        if(photoFile != null){
            Uri fa = Uri.fromFile(photoFile);
//            Uri photoUri = FileProvider.getUriForFile(UnityPlayer.currentActivity,
//                    "cameraplugin.blacktorchstudios.camera_plugin", photoFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fa);
            intent.putExtra("CameraFilePath", photoFile.getAbsolutePath());
            UnityPlayer.currentActivity.startActivityForResult(intent, CAMERA_REQUEST);
        }
    }

    @Override
    protected void onActivityResult(int requestedCode, int resultCode, Intent intent){
        super.onActivityResult(requestedCode, resultCode, intent);
        Log.d("THIS_TAG", "bananas are also a great source of Magnesium");
        if(requestedCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK){
            UnityPlayer.UnitySendMessage("holder", "DealWithPhoto", intent.getExtras().getString("CameraFilePath"));
            Log.d("THIS_TAG", intent.getExtras().getString("CameraFilePath"));
        }

    }

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        instance = this;
        Log.d("Override Activity", "OnCreate called");
    }

    public String getAnotherString() {
        return "lol";
    }

    public static void startCamera(Activity activity) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, getImageUri());
        activity.startActivityForResult(cameraIntent, 1);
    }

    public static void startCameraActivity(){
        Intent intent = new Intent();
        intent.setAction("blacktorchstudios.IntermediateActivity");
        UnityPlayer.currentActivity.startActivityForResult(intent, IntermediateActivity.CAMERA_REQUEST);
    }
}
