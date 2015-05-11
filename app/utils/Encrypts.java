package utils;

import java.util.Map;

import org.apache.commons.collections.map.HashedMap;

import play.Logger;
import play.libs.Crypto;
import play.mvc.Http.Response;
import net.sf.json.JSONObject;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.shove.Convert;
import com.shove.JSONUtils;
import com.shove.security.Encrypt;

import constance.IConstance;

public class Encrypts {

	public static String MD5(String source){
		return Encrypt.MD5(source);
	}
	
	public static String MakeKeyMD5(String source){
		return Encrypt.MD5(source+IConstance.SEC_KEY);
	}
	
	public static String encryptTo(String input){
		if("".equals(input)){
			return "";
		}
		String key =Encrypt.encrypt3DES(input, IConstance.SEC_KEY);
		String value=Encrypt.MD5(key+IConstance.SEC_KEY).substring(0,10).toUpperCase();
		return value+key;
	}

	public static String decryptTo(String input){
		if("".equals(input)||input.length() < 10){
			return "";
		}
		String key =input.substring(10,input.length());
		String value=input.substring(0,10);
		String mValue =Encrypt.MD5(key+IConstance.SEC_KEY).substring(0,10).toUpperCase();
		if(value.equals(mValue)){
			return Encrypt.decrypt3DES(key, IConstance.SEC_KEY);
		}
		return "";
	}
	
	public static JSONObject encryptToken(){
		JSONObject obj = new JSONObject();
		String access_token = Response.current().cookies.get("key").value;
		long curTime = System.currentTimeMillis()+IConstance.EXPIRES_IN;
		access_token = access_token + ":" + curTime;
		access_token = Encrypts.encryptTo(access_token);
		obj.put("access_token", access_token);
		obj.put("expires_in", IConstance.EXPIRES_IN);
		return obj;
	}

	public static String decryptTocken(String access_token){
		if("".equals(access_token)){
			return "";
		}
		access_token = Encrypts.decryptTo(access_token);
		long curTime = System.currentTimeMillis();
		String [] token_array = access_token.split(":");
		
		if(token_array.length == 2){
			long token_time = Convert.strToLong(token_array[1], 0);
			if(token_time < curTime){
				return "";
			}
			return token_array[0];
		}
		return "";
	}
	
	
	public static void main(String[] args) {
		String str = Crypto.encryptAES("1");
		String str2 = Crypto.decryptAES(str);
	}
}
