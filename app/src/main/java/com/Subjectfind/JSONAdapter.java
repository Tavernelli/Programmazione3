package com.Subjectfind;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONAdapter extends BaseAdapter {

    Context mContext;
    LayoutInflater mInflater;
    JSONArray mJson;

    public JSONAdapter(Context context, LayoutInflater inflater) {
        mContext = context;
        mInflater = inflater;
        mJson = new JSONArray ();
    }

    private static class ViewHolder {
        public TextView docenteTextView;
        public TextView mailTextView;
    }

    @Override
    public int getCount() {
        return mJson.length();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        try {
            return mJson.get(position);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    //get della view specificando la position convertView specifica la view
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //...
        ViewHolder holder;
        //la prima volta che la convertView è creata è vuota
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.cerca, null);
            holder = new ViewHolder();
            holder.docenteTextView = (TextView) convertView.findViewById(R.id.text_docente);
            holder.mailTextView = (TextView) convertView.findViewById(R.id.text_mail);
            //uso il tag per far si che si ricordi dell'ultimo oggetto lo copio in memoria
            convertView.setTag(holder);
        } else {
            //prende l'utltimo oggetto
            holder = (ViewHolder) convertView.getTag();
        }

        JSONObject jsonObject = (JSONObject)getItem(position);
        String docente = "";
        String mail = "";
        if (jsonObject.has("docente")) {
            docente = jsonObject.optString("docente");
        }
        if (jsonObject.has("materia")) {
            mail = jsonObject.optString("materia");
        }
        // invio alla textview
        holder.docenteTextView.setText(docente);
        holder.mailTextView.setText(mail);
        return convertView;
    }



    public void updateData(JSONArray jsonResponse) {
        // update the adapter
        mJson = jsonResponse;
        notifyDataSetChanged();
    }

}