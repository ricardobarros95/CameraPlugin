package cameraplugin.blacktorchstudios.camera_plugin;

import android.content.Intent;
import android.os.Bundle;

import com.unity3d.player.UnityPlayer;
import com.unity3d.player.UnityPlayerActivity;

public class CameraActivity extends UnityPlayerActivity {

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
    }

    public static void startCameraActivity(){
        Intent intent = new Intent();
        intent.setAction("blacktorchstudios.IntermediateActivity");
        UnityPlayer.currentActivity.startActivityForResult(intent, IntermediateActivity.CAMERA_REQUEST);
    }
}
