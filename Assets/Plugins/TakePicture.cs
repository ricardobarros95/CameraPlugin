using UnityEngine;
using System.Collections;
using UnityEngine.UI;
using System;

public class TakePicture : MonoBehaviour {

    public CameraPluginn cameraPlugin;

    public GameObject confirmPictureScreen; // Panel -> Image, Text, Yes button, No button
    public GameObject congratzScreen;
    public GameObject prePicPanel;

    public void SnapShotButton()
    {
        confirmPictureScreen.SetActive(true);
        StartCoroutine(StartCameraRoutine());
    }

    IEnumerator StartCameraRoutine()
    {
        CameraPluginn.StartCamera();
    
        yield return new WaitUntil(() => cameraPlugin.PictureReady == true);
        Debug.Log("Picture is Ready" + DateTime.Now.ToString());
        prePicPanel.SetActive(false);
        Image pic = confirmPictureScreen.transform.FindChild("Image").GetComponent<Image>();
        pic.sprite = cameraPlugin.Picture.sprite;
        Debug.Log(DateTime.Now.ToString());
    }

    public void Confirm()
    {
        confirmPictureScreen.SetActive(false);
        congratzScreen.SetActive(true);
        Image pic = congratzScreen.transform.FindChild("Image").GetComponent<Image>();
        pic.sprite = cameraPlugin.Picture.sprite;
    }

    public void Retake()
    {
        prePicPanel.SetActive(true);
        SnapShotButton();
    }



    
}
