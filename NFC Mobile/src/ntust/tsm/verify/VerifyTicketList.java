package ntust.tsm.verify;

import ntust.tsm.acquire.AcquireTicket;
import ntust.tsm.acquire.AcquireTicketInformation;
import ntust.tsm.acquire.AcquireTicketList;
import ntust.tsm.main.DatabaseOperation;
import ntust.tsm.main.R;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class VerifyTicketList extends ListActivity  {
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
    		bundle.putString("tid", cursor.getString(1));
    		Intent intent = new Intent();
    		intent.putExtras(bundle);
        	intent.setClass(VerifyTicketList.this, VerifyTicketInformation.class); 
        	VerifyTicketList.this.startActivity(intent);
        	finish();
        }

	};
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verify_ticket_list);

    	Cursor mNotesCursor;
    	DatabaseOperation mDbHelper = new DatabaseOperation(this);
		mDbHelper.open();
    	mNotesCursor = mDbHelper.getAll();
		
		startManagingCursor(mNotesCursor);
		String[] from = new String[] { "tid" };
		int[] to = new int[] { android.R.id.text1 };
	
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
				R.layout.verify_ticket_content,
				mNotesCursor, from, to);
		
		findViews();
		setListensers();
		setListAdapter(adapter);
		mDbHelper.close();
    }


}
