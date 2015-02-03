
package com.dekkoh.activities;

import java.util.List;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

import com.dekkoh.BaseFragmentActivity;
import com.dekkoh.R;
import com.dekkoh.custom.adapter.MyConnectionsAdapter;
import com.dekkoh.datamodel.DekkohUserConnection;
import com.dekkoh.service.APIProcessor;
import com.dekkoh.util.Log;

public class MyConnectionsActivity extends BaseFragmentActivity {
    private ListView connectionsListView;
    private List<DekkohUserConnection> dekkohUserConnectionList;
    private MyConnectionsAdapter myConnectionsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_connections_activity_layout);
        connectionsListView = (ListView) findViewById(R.id.connectionsListView);
        new GetUserConectionsTask().execute();
    }

    private class GetUserConectionsTask extends AsyncTask<Void, Void, List<DekkohUserConnection>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialogHandler.showCustomProgressDialog(activity);
        }

        @Override
        protected List<DekkohUserConnection> doInBackground(Void... params) {
            try {
                return APIProcessor.getUserConnections(activity);
            } catch (Exception e) {
                Log.e(e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<DekkohUserConnection> result) {
            super.onPostExecute(result);
            progressDialogHandler.dismissCustomProgressDialog(activity);
            if (result != null) {
                dekkohUserConnectionList = result;
                myConnectionsAdapter = new MyConnectionsAdapter(activity,
                        R.layout.conections_list_item, result, fragmentManager);
                connectionsListView.setAdapter(myConnectionsAdapter);
            }
        }
    }
}
