package ntust.tsm.verify;

import ntust.tsm.main.DatabaseOperation;
import ntust.tsm.main.Global;
import ntust.tsm.main.R;
import ntust.tsm.main.TSMActivity;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class VerifyTicketInformation extends Activity {
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verify_ticket_information);
        Bundle bundle = getIntent().getExtras();
        Global.verifyTicketID = bundle.getString("tid");
		String tid = Global.verifyTicketID;
    	DatabaseOperation mDbHelper = new DatabaseOperation(this);
		mDbHelper.open();
		Cursor cursor = mDbHelper.getQuery("tid",new String[]{tid});
		cursor.moveToNext();
        TextView txtTicketId = new TextView(this); 
        txtTicketId = (TextView)findViewById(R.id.txtTicketId);
        txtTicketId.setText("票券編號: " + cursor.getString(cursor.getColumnIndex("tid")));
        
        TextView txtTicketMovie = new TextView(this); 
        txtTicketMovie = (TextView)findViewById(R.id.txtTicketMovie);
        txtTicketMovie.setText("電影: " + cursor.getString(cursor.getColumnIndex("movie")));        
        TextView txtTicketTheater = new TextView(this); 
        txtTicketTheater = (TextView)findViewById(R.id.txtTicketTheater);
        txtTicketTheater.setText("戲院: " + cursor.getString(cursor.getColumnIndex("theater")));    
        TextView txtTicketDate = new TextView(this); 
        txtTicketDate = (TextView)findViewById(R.id.txtTicketDate);
        txtTicketDate.setText("日期: " + cursor.getString(cursor.getColumnIndex("date")));
        TextView txtTicketTime = new TextView(this); 
        txtTicketTime = (TextView)findViewById(R.id.txtTicketTime);
        txtTicketTime.setText("時間: " + cursor.getString(cursor.getColumnIndex("time")));

		mDbHelper.close();

		Button btnVerifyTicket = (Button) findViewById(R.id.btnVerifyTicket);
    	btnVerifyTicket.setOnClickListener(new Button.OnClickListener() {
    		public void onClick(View v) {
    				// TODO Auto-generated method stub			
    				try {
    					Intent intent=new Intent();
    			    	intent.setClass(VerifyTicketInformation.this, VerifyTicket.class);       	
    			    	startActivity(intent);
    			    	finish();
    					//Toast.makeText(Main.this,ntust.main.R.string.btnSubmit , Toast.LENGTH_SHORT).show();

    				} catch (Exception e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    					Toast.makeText(VerifyTicketInformation.this, "error:"+e.toString(), Toast.LENGTH_SHORT).show();
    				}
    			}
    	});
    	
	}

}
