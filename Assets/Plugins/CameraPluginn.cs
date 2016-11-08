using UnityEngine;
using System.Collections;
using UnityEngine.UI;
using System.IO;
using System;

[RequireComponent(typeof(Image))]
public class CameraPluginn : MonoBehaviour
{
    public Image Picture { get; set; }
    public bool PictureReady { get; set; }

    public Texture2D Texture { get; set; }

    public static void StartCamera()
    {
        Debug.Log("Start Camera: " + DateTime.Now.ToString());
        AndroidJavaClass cameraActivity = new AndroidJavaClass("cameraplugin.blacktorchstudios.camera_plugin.CameraActivity");
        cameraActivity.CallStatic("startCameraActivity");
    }

    public void DealWithPhoto(string message)
    {
        Debug.Log("OnActivityResult: " + DateTime.Now.ToString());
        if (System.IO.File.Exists(message))
        {
            byte[] data = System.IO.File.ReadAllBytes(message);
            Debug.Log("Read all bytes: " + DateTime.Now.ToString());
            Texture2D tex = new Texture2D(1, 1);
           
            tex.LoadImage(data);
            Debug.Log("Load Image: " + DateTime.Now.ToString());

            Texture2D target = new Texture2D(tex.height, tex.width, tex.format, false);
            
            Color32[] pixels = tex.GetPixels32(0);
            pixels = rotateTextureGrid(pixels, tex.width, tex.height);
            target.SetPixels32(pixels);
            target.Apply();
            Debug.Log("Rotate Picture: " + DateTime.Now.ToString());
            Texture = target;

            Sprite s = Sprite.Create(target, new Rect(0, 0, target.width, target.height), new Vector2(0.5f, 0.5f));
            Picture = GetComponent<Image>();
            Picture.sprite = s;
            PictureReady = true;
            Debug.Log("Sprite Created: " + DateTime.Now.ToString());
        }
        else
        {
            Debug.LogError("File not found at path: " + message);
        }
    }
    public Color32[] rotateTextureGrid(Color32[] tex, int wid, int hi)
    {
        Color32[] ret = new Color32[wid * hi];      //reminder we are flipping these in the target

        for (int y = 0; y < hi; y++)
        {
            for (int x = 0; x < wid; x++)
            {
                ret[y + (wid - 1 - x) * hi] = tex[x + y * wid];         //juggle the pixels around//juggle the pixels around

            }
        }

        return ret;
    }
}
