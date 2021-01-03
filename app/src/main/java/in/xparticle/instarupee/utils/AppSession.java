package in.xparticle.instarupee.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class AppSession {

    private static final String SESSION_NAME = "apps.nms";
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor prefsEditor;

    public AppSession(Context context){
        mSharedPreferences = context.getSharedPreferences(SESSION_NAME,Context.MODE_PRIVATE);
        prefsEditor = mSharedPreferences.edit();
    }

    public boolean isLongIn(){
        return mSharedPreferences.getBoolean("Login",false);
    }
    public void setLogin(boolean Login){
        prefsEditor = mSharedPreferences.edit();
        prefsEditor.putBoolean("Login",Login);
        prefsEditor.commit();
    }

    public void setFirstName(String firstName){
        prefsEditor = mSharedPreferences.edit();
        prefsEditor.putString("firstName",firstName);
        prefsEditor.commit();
    }

    public String getFirstName(){
        prefsEditor = mSharedPreferences.edit();
        return mSharedPreferences.getString("firstName","");
    }

    public void setLastName(String lastName){
        prefsEditor = mSharedPreferences.edit();
        prefsEditor.putString("lastName",lastName);
    }

    public String getLastName(String lastName){
        return mSharedPreferences.getString("firstName","");
    }

    public void setPhoneNumber(String phoneNumber){
        prefsEditor = mSharedPreferences.edit();
        prefsEditor.putString("phoneNumber",phoneNumber);
    }

    public String getPhoneNumber(){
        return mSharedPreferences.getString("phoneNumber","");
    }

    public void setEmail(String email){
        prefsEditor = mSharedPreferences.edit();
        prefsEditor.putString("email",email);
    }

    public String getEmail(){
        return mSharedPreferences.getString("email","");
    }

    public void setCity(String city){
        prefsEditor = mSharedPreferences.edit();
        prefsEditor.putString("city",city);
    }

    public String getCity(){
        return mSharedPreferences.getString("city","");
    }

    public void setState(String state){
        prefsEditor = mSharedPreferences.edit();
        prefsEditor.putString("state",state);
    }

    public String getState(){
        return mSharedPreferences.getString("state","");
    }

    public void setPassword(String password){
        prefsEditor = mSharedPreferences.edit();
        prefsEditor.putString("password",password);
    }

    public String getPassword(){
        return mSharedPreferences.getString("password","");
    }

}
