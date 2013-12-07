package ntust.tsm.acquire;

import ntust.tsm.main.Global;
import ntust.tsm.main.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class AcquireTicketInformation extends Activity {
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acquire_ticket_information);
        
        Bundle bundle = this.getIntent().getExtras();
        int number = bundle.getInt("number");
        TextView txtTicketId = new TextView(this); 
        txtTicketId = (TextView)findViewById(R.id.txtTicketId);
        txtTicketId.setText("票券編號: " + Global.tid[number]);
        TextView txtTicketMovie = new TextView(this); 
        txtTicketMovie = (TextView)findViewById(R.id.txtTicketMovie);
        txtTicketMovie.setText("電影: " + Global.movie[number]);        
        TextView txtTicketTheater = new TextView(this); 
        txtTicketTheater = (TextView)findViewById(R.id.txtTicketTheater);
        txtTicketTheater.setText("戲院: " + Global.theater[number]);    
        TextView txtTicketDate = new TextView(this); 
        txtTicketDate = (TextView)findViewById(R.id.txtTicketDate);
        txtTicketDate.setText("日期: " + Global.date[number]);
        TextView txtTicketTime = new TextView(this); 
        txtTicketTime = (TextView)findViewById(R.id.txtTicketTime);
        txtTicketTime.setText("時間: " + Global.time[number]);
        TextView txtTicketNNumber = new TextView(this); 
	}

}