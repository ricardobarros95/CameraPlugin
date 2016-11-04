using UnityEngine;
using UnityEditor;
using System.Collections;

public class CreateHolder
{
    [MenuItem ("Native Camera/Create Holder")]
    private static void CreateHolderObject()
    {
        CameraPluginn holder = GameObject.FindObjectOfType<CameraPluginn>();
        if(holder == null)
        {
            holder = new GameObject().AddComponent<CameraPluginn>();
            holder.gameObject.name = "holder";
        }
        else
        {
            return;
        }
    }


}
