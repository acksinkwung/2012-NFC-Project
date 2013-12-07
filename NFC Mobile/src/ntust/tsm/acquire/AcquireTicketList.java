package ntust.tsm.acquire;

import ntust.tsm.main.DatabaseOperation;
import ntust.tsm.main.Global;
import ntust.tsm.main.NFCModel;
import ntust.tsm.main.R;
import ntust.tsm.verify.VerifyTicket;
import ntust.tsm.verify.VerifyTicketList;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import java.net.URLDecoder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AcquireTicketList extends ListActivity  {
	private ListView lv;

	// 給定物件Listerser
	private void setListensers() {
		lv.setOnItemClickListener(permit);
	}
	private void findViews() {
		lv = getListView();
	}
	private ListView.OnItemClickListener permit = new ListView.OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			Cursor cursor = (Cursor) lv.getItemAtPosition(position); 
			Bundle bundle = new Bundle();	
    		bundle.putInt("number", position);
    		Intent intent = new Intent();
    		intent.putExtras(bundle);
        	intent.setClass(AcquireTicketList.this, AcquireTicketInformation.class); 
        	AcquireTicketList.this.startActivity(intent);
        	finish();
        }

	};
	static String message = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acquire_ticket_list);
        message = URLDecoder.decode(NFCModel.getResult());
        Cursor mNotesCursor;
    	DatabaseOperation mDbHelper = new DatabaseOperation(this);

		try {
			mDbHelper.open();
			mDbHelper.deleteAcquire();
			mDbHelper.setAcquireDefault();
		}catch(Exception e){
			
		}
		JSONArray jsonArray;
		try {
			jsonArray = new JSONArray(message);
	 
	        for (int i = 0; i < jsonArray.length(); i++) {
	        	
	            JSONObject jsonObject = jsonArray.getJSONObject(i);
	            
	            Global.tid[i] = jsonObject.getString("tid");
	            Global.theater[i] = jsonObject.getString("theater");
	            Global.movie[i] = jsonObject.getString("movie");
	            Global.date[i] = jsonObject.getString("date");
	            Global.time[i] = jsonObject.getString("time");
	        	mDbHelper.insertAcquire(
	        			Global.tid[i],
	        			Global.theater[i],
	        			Global.movie[i],
	        			Global.date[i],
	        			Global.time[i]);
	        	mDbHelper.insert(
	        			Global.tid[i],
	        			Global.theater[i],
	        			Global.movie[i],
	        			Global.date[i],
	        			Global.time[i]);
	        }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	mNotesCursor = mDbHelper.getAllAcquire();
		startManagingCursor(mNotesCursor);
		String[] from = new String[] { "tid" };
		int[] to = new int[] { android.R.id.text1 };
	
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
				R.layout.acquire_ticket_content,
				mNotesCursor, from, to);
		
		findViews();
		setListensers();
		setListAdapter(adapter);
		mDbHelper.close();
    }
    @Override
    public void onResume() {
        super.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
    }
}