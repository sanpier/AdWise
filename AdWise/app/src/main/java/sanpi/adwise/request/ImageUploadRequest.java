package sanpi.adwise.request;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by San-Pi on 03.10.2016.
 */

public class ImageUploadRequest extends StringRequest
{
    private static final String IMAGE_UPLOAD_REQUEST_URL = "http://adwiseapp.com/myphp/UploadImage.php";
    private Map<String, String> params;

    public ImageUploadRequest(String email, String image, Response.Listener<String> listener)
    {
        super(Request.Method.POST, IMAGE_UPLOAD_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("email", email);
        params.put("image", image);
    }
    @Override
    public Map<String, String> getParams(){
        return params;
    }
}