package sanpi.adwise.request;

import com.android.volley.Request;
import com.android.volley.Response;
import  com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by San-Pi on 24.09.2016.
 */

public class RegisterRequest extends StringRequest
{
    private static final String REGISTER_REQUEST_URL = "http://adwiseapp.com/myphp/Register.php";
    private Map<String, String> params;

    public RegisterRequest(String name, String surname, String email, String password,
                        Response.Listener<String> listener)
    {
        super(Request.Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("name", name);
        params.put("surname", surname);
        params.put("email", email);
        params.put("password", password);
    }
    @Override
    public Map<String, String> getParams(){
        return params;
    }
}
