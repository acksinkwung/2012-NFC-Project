package ntust.tsm.main;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class DatabaseOperation {
	private Context mCtx = null;
	private DatabaseHelper dbHelper;
	private SQLiteDatabase db;

	// DB�غc��
	public DatabaseOperation(Context ctx) {
		this.mCtx = ctx;
	}

	// �}�ҳs����Ʈw
	public DatabaseOperation open() throws SQLException {
		dbHelper = new DatabaseHelper(mCtx);
		db = dbHelper.getWritableDatabase();
		/*try {
			db.execSQL("SELECT * FROM tickets");
		} catch (Exception e) {
			db.execSQL(DATABASE_CREATE);
		}
		try {
			db.execSQL("SELECT * FROM acquire_tickets");
		} catch (Exception e) {
			db.execSQL(DATABASE_CREATE_ACQUIRE);
		}
		*/
		return this;
	}

	// ���o��Ʈw���������
	public Cursor getAll() {
		return db.rawQuery("SELECT * FROM tickets", null);
	}
	// ���o��Ʈw���������
	public Cursor getAllAcquire() {
		return db.rawQuery("SELECT * FROM acquire_tickets", null);
	}

	// ���o��Ʈw���S�w���
	public Cursor getQuery(String strw, String[] query) {

		return db.rawQuery(
				"SELECT * FROM tickets WHERE " + strw + "=?", query);
	}
	public Cursor getQueryAcquire(String strw, String[] query) {

		return db.rawQuery(
				"SELECT * FROM "+DATABASE_TABLE_ACQUIRE+" WHERE " + strw + "=?", query);
	}
	// �R�����
	public boolean delete(String rowId) {
		return db.delete(DATABASE_TABLE, KEY_tid + "=" + rowId, null) > 0;
	}
	public boolean deleteAcquire() {
		return db.delete(DATABASE_TABLE_ACQUIRE, null, null) > 0;
	}
	// �s�W���
	public boolean insert(String tid,String theater,String movie,String date,String time) {
		ContentValues values = new ContentValues();
		values.put("tid", tid);
		values.put("theater", theater);
		values.put("movie", movie);
		values.put("date", date);
		values.put("time", time);
		return db.insert(DATABASE_TABLE, null, values) > 0;
	}
	public void setDefault() {
		db.execSQL("DROP TABLE " + DATABASE_TABLE);
		db.execSQL(DATABASE_CREATE);
	}
	public void setAcquireDefault() {

		db.execSQL(DATABASE_CREATE_ACQUIRE);
	}
	public boolean insertAcquire(String tid,String theater,String movie,String date,String time) {
		ContentValues values = new ContentValues();
		values.put("tid", tid);
		values.put("theater", theater);
		values.put("movie", movie);
		values.put("date", date);
		values.put("time", time);
		return db.insert(DATABASE_TABLE_ACQUIRE, null, values) > 0;
	}
	// �����s����Ʈw
	public void close() {
		dbHelper.close();
	}

	// �]�w��Ʈw�һݤ��Ѽ�
	private static final String DATABASE_NAME = "sp_tsm.db";
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_TABLE = "tickets";
	private static final String DATABASE_TABLE_ACQUIRE = "acquire_tickets";
	// �]�w���W��
	public static final String KEY_tid = "tid";
	public static final String KEY_theater = "theater";
	public static final String KEY_movie = "movie";
	public static final String KEY_date = "date";
	public static final String KEY_time = "time";
	private static final String DATABASE_CREATE = "create table "+DATABASE_TABLE+"("
			+ "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ KEY_tid + " TEXT,"
			+ KEY_theater + " TEXT,"
			+ KEY_movie + " TEXT,"
			+ KEY_date + " TEXT,"
			+ KEY_time + " TEXT);";
	private static final String DATABASE_CREATE_ACQUIRE = "create table "+DATABASE_TABLE_ACQUIRE+"("
			+ "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ KEY_tid + " TEXT,"
			+ KEY_theater + " TEXT,"
			+ KEY_movie + " TEXT,"
			+ KEY_date + " TEXT,"
			+ KEY_time + " TEXT);";
	private static class DatabaseHelper extends SQLiteOpenHelper {

		public DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}

		// ��l�Ƹ�Ʈw
		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub

		
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			
		}


	}
}
