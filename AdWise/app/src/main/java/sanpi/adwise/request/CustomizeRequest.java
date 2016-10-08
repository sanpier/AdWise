package sanpi.adwise.request;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by San-Pi on 03.10.2016.
 */

public class CustomizeRequest extends StringRequest
{
    private static final String CUSTOMIZE_REQUEST_URL = "http://adwiseapp.com/myphp/Customize.php";
    private Map<String, String> params;

    public CustomizeRequest(String email, String gender, String birthdate, String department, String city,
                           Response.Listener<String> listener)
    {
        super(Request.Method.POST, CUSTOMIZE_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("email", email);
        params.put("gender", gender);
        params.put("birthdate", birthdate);
        params.put("department", department);
        params.put("city", city);
    }
    @Override
    public Map<String, String> getParams(){
        return params;
    }
}