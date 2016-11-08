using UnityEngine;
using System.Collections;
using UnityEngine.UI;
using System;
using System.Collections.Generic;

public class TakePicture : MonoBehaviour {

    public CameraPluginn cameraPlugin;

    public GameObject confirmPictureScreen; // Panel -> Image, Text, Yes button, No button
    public GameObject congratzScreen;
    public GameObject prePicPanel;

    private string postPicURL = "http://selfienation.azurewebsites.net/api/photolibrary/";
    private string completeChallengeURL = "http://selfienation.azurewebsites.net/api/manager/complete";

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
        Image pic = confirmPictureScreen.transform.FindChild("Picture").GetComponent<Image>();
        pic.sprite = cameraPlugin.Picture.sprite;
        Debug.Log(DateTime.Now.ToString());
    }

    public void Confirm()
    {
        confirmPictureScreen.SetActive(false);
        congratzScreen.SetActive(true);
        Image pic = congratzScreen.transform.FindChild("Photo").GetComponent<Image>();
        pic.sprite = cameraPlugin.Picture.sprite;
        byte[] data = cameraPlugin.Texture.EncodeToJPG();
        UploadPhoto(data);
    }

    public void Retake()
    {
        prePicPanel.SetActive(true);
        SnapShotButton();
    }

    private void UploadPhoto(byte[] data)
    {
        string photoData = Convert.ToBase64String(data);

        Photo payload = new Photo();
        payload.PhotoData = photoData;
        string payloadJson = JsonUtility.ToJson(payload);
        byte[] payloadBytes = System.Text.Encoding.UTF8.GetBytes(payloadJson.ToCharArray());
        Dictionary<string, string> headers = new Dictionary<string, string>();
        headers.Add("Content-type", "application/json");
        WWW www = new WWW(postPicURL, payloadBytes, headers);
        StartCoroutine(UploadPhotoRoutine(www));
    }

    IEnumerator UploadPhotoRoutine(WWW www)
    {
        yield return www;
        if(www.error != null)
        {

        }
        else
        {
            Debug.Log(www.text);
            Debug.Log(www.error);
        }
    }

    void CompleteChallenge()
    {
        CompletedChallenge cc = new CompletedChallenge();
        string output = JsonUtility.ToJson(cc);
        byte[] payloadBytes = System.Text.Encoding.UTF8.GetBytes(output.ToCharArray());
        Dictionary<string, string> headers = new Dictionary<string, string>();
        headers.Add("Content-type", "application/json");
        WWW www = new WWW(completeChallengeURL, payloadBytes, headers);
        StartCoroutine(CompleteChallengeRoutine(www));
    }

    IEnumerator CompleteChallengeRoutine(WWW www)
    {
        yield return www;
    }

    
}

public class Photo
{
    public int PhotoId { get; set; }
    public string PhotoData { get; set; }
    public bool ProduceEvent { get; set; }
}

public class CompletedChallenge
{

}