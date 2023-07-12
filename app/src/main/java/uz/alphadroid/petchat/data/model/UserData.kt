package uz.alphadroid.petchat.data.model

import java.io.Serializable

data class UserData(val name:String?=null,val phone:String?=null,val photoUrl:String?=null):Serializable{
     constructor():this(null,null,null)
}