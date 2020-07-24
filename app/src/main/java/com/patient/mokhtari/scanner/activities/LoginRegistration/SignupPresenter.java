package com.patient.mokhtari.scanner.activities.LoginRegistration;

import android.content.Context;
import android.widget.Toast;

import com.example.UserValidator;
import com.example.exceptions.password.InvalidPasswordException;
import com.example.exceptions.password.InvalidPasswordFormatException;
import com.example.exceptions.password.InvalidPasswordLengthException;
import com.example.exceptions.password.NullPasswordException;
import com.example.exceptions.username.InvalidUsernameFormatException;
import com.example.exceptions.username.InvalidUsernameLengthException;
import com.example.exceptions.username.UsernameIsNullException;
import com.patient.mokhtari.scanner.activities.webservice.ConnectToServer;
import com.patient.mokhtari.scanner.activities.webservice.URLs;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

import static com.patient.mokhtari.scanner.activities.webservice.URLs.URL_SIGNUP;

public class SignupPresenter {
    final SignupActivity signupActivity;
    final Context context;

    public SignupPresenter(SignupActivity signupActivity, Context context) {
        this.signupActivity = signupActivity;
        this.context = context;
    }

    public boolean signup(String username, String password, String name, String bday, String gender, String address, String introduction) {

        if (!userPassValidator(username, password))
            return false;

        Map<String, String> param = new HashMap<>();
        param.put("username", username);
        param.put("password", password);
        param.put("name", name);
        param.put("bday", bday);
        param.put("gender", gender);
        param.put("address", address);
        param.put("introduction", introduction);

        ConnectToServer.any_send(result -> signupActivity.signupSuccessful(), param, URL_SIGNUP);
        return true;

    }

    public boolean checkUnique(String username) {
        signupActivity.showLoading_base();
        Map<String, String> param = new HashMap<>();
        String url;
        url = URLs.URL_SIGNUP_CHECK_UNIQUE_USERNAME;
        param.put("Username", username);
        ConnectToServer.any_send(result -> reciveUniquenessCheckRequeset(result), param, url);
        return true;
    }
    public boolean reciveUniquenessCheckRequeset(String result) throws JSONException {
        signupActivity.hideLoading_base();
        JSONObject obj = new JSONObject(result);
        if (obj.getString("message").equals("Unique")) {
            signupActivity.usernameIsUnique();
            return true;
        }
        else {
            String text =obj.getString("permessage");
            showAlarm(text);
            return false;
        }
    }
    public boolean userPassValidator(String username, String password) {
        signupActivity.setPasswordError(null);
        signupActivity.setUsernameError(null);
        UserValidator validator = UserValidator.builder().build();
        try {
            if (validator.validateUsername(username) &&
                    validator.validatePassword(username, password)) {
                return true;
                // TODO login, register or edit user
            }
        } catch (InvalidPasswordException e) { // Catch all exceptions you're interested to handle
            String text ="فرمت رمز اشتباه";
        //    showAlarm(text);
            signupActivity.setPasswordError("رمز عبور بین 6 تا 20 حرف باشد\nکاراکتر های غیر مجاز: &amp;%€@#");
        } catch (InvalidPasswordFormatException e) { // Catch all exceptions you're interested to handle
            String text ="فرمت رمز اشتباه";
         //   showAlarm(text);
            signupActivity.setPasswordError("رمز عبور بین 6 تا 20 حرف باشد\nکاراکتر های غیر مجاز: &amp;%€@#");
        } catch (InvalidPasswordLengthException e) { // Catch all exceptions you're interested to handle
            String text ="طول رمز کم است";
          //  showAlarm(text);
            signupActivity.setPasswordError("رمز عبور بین 6 تا 20 حرف باشد\nکاراکتر های غیر مجاز: &amp;%€@#");
        } catch (NullPasswordException e) { // Catch all exceptions you're interested to handle
            String text ="رمز خالی است";
           // showAlarm(text);
            signupActivity.setPasswordError("رمز عبور بین 6 تا 20 حرف باشد\nکاراکتر های غیر مجاز: &amp;%€@#");
        } catch (UsernameIsNullException e) { // Catch all exceptions you're interested to handle
            String text ="نام کاربری خالی است";
           // showAlarm(text);
            signupActivity.setUsernameError("نام کاربری باید بدون فاصله و انگلیسی باشد \nنام کاربری باید بین 3 و 25 حرف باشد");
        } catch (InvalidUsernameFormatException e) { // Catch all exceptions you're interested to handle
            String text ="فرمت نام کاربری اشتباه است";
          //  showAlarm(text);
            signupActivity.setUsernameError("نام کاربری باید بدون فاصله و انگلیسی باشد \nنام کاربری باید بین 3 و 25 حرف باشد");
        } catch (InvalidUsernameLengthException e) { // Catch all exceptions you're interested to handle
            String text ="طول نام کاربری کم است";
           // showAlarm(text);
            signupActivity.setUsernameError("نام کاربری باید بدون فاصله و انگلیسی باشد \nنام کاربری باید بین 3 و 25 حرف باشد");
        } catch (Exception e) { // Catch all exceptions you're interested to handle
            String text ="دوباره امتحان کنید";
            showAlarm(text);
            signupActivity.setUsernameError("نام کاربری باید بدون فاصله و انگلیسی باشد \nنام کاربری باید بین 3 و 25 حرف باشد");
        }
        return false;
    }

    public void showAlarm(String text){
        Toasty.warning(signupActivity, text, Toast.LENGTH_SHORT, true).show();
    }

}
