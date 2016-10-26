using UnityEngine;
using System.Collections;
using UnityEngine.UI;

public class TakePicture : MonoBehaviour {

    public CameraPluginn cameraPlugin;

    public GameObject confirmPictureScreen; // Panel -> Image, Text, Yes button, No button
    public GameObject congratzScreen;

    public void SnapShotButton()
    {
        confirmPictureScreen.SetActive(true);
        CameraPluginn.StartCamera();
        Image pic = confirmPictureScreen.GetComponentInChildren<Image>();
        pic = cameraPlugin.Picture;
    }

    public void Confirm()
    {
        confirmPictureScreen.SetActive(false);
        congratzScreen.SetActive(true);
    }

    public void Retake()
    {
        SnapShotButton();
    }



    
}
