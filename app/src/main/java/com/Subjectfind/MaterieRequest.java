package com.Subjectfind;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


public class MaterieRequest extends StringRequest {
    private static final String REQUEST_URL = "http://andreatavernelli.altervista.org/Register/Request.php";
    private Map<String, String> params;

    public MaterieRequest(String jsonMaterie, Response.Listener<String> listener) {
        super(Request.Method.POST, REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("materie", jsonMaterie);

    }


    @Override
    public Map<String, String> getParams() {
        return params;
    }


}
