package org.shaastra.qmshelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.shaastra.qmshelper.Register_old.SubmitRegistration;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.opengl.Visibility;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class NewUser extends FragmentActivity implements OnDateSetListener   {
	
EditText fnameET,lnameET,emailET,crollET,mobnumET ,bcodeET,cbranchET,cnameET,cityET,passwordET;
Spinner branch_spinner;
RadioGroup genderRG,accomRG;
TextView dateView,idTV,faq;
Button updatebut,regbut;

String gender, dobS,email,accomodation, fname ,lname ,croll,mobnum,cbranch,cname,city,password;
String[] branches="School\nArts\nAccounting\nApplied Mechanics\nMechatronics\nAerospace Engineering\nAutomobile Engineering\nBiotech / Biochemical / Biomedical\nBiology\nCeramic Engineering\nChemical Engineering\nChemistry\nDesign\nEngineering Design\nCivil Engineering\nComputer Science and Engineering\nElectronics and Communications Engineering\nElectrical and Electronics Engineering\nElectrical Engineering\nElectronics and Instrumentation Engineering\nEngineering Physics\nEconomics\nFashion Technology\nHumanities and Social Sciences\nIndustrial Production\nProduction\nInformation Technology and Information Science\nManagement\nManufacturing\nMathematics\nMetallurgy and Material Science\nMechanical Engineering\nOcean Engineering and Naval Architecture\nPhysics\nTelecom\nTextile Engineering\nOthers".split("\n");
ArrayAdapter<String> branch_data;
private Calendar calendar,dob;
    int request_Code=1;

Singleton app;

private int year, month, day,age;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newuser);


        app = (Singleton) getApplicationContext();

		dateView = (TextView) findViewById(R.id.DOB);
	      calendar = Calendar.getInstance();
	      year = calendar.get(Calendar.YEAR);
	      month = calendar.get(Calendar.MONTH);
	      day = calendar.get(Calendar.DAY_OF_MONTH);
	      dob= Calendar.getInstance();
	      branch_data= new ArrayAdapter<String>(NewUser.this,android.R.layout.simple_spinner_item, branches);
	      dateView.setText("DOB : --/--/----"); 
	      //dateView.setText("DOB : " + day+"/"+(month+1)+"/"+year);
	      gender="M";
	      accomodation="0";
	      setTitle("New User");
	      //showDate(year, month, day);
	      branch_spinner=(Spinner)findViewById(R.id.branch_spinner);
	      branch_data.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	      branch_spinner.setAdapter(branch_data); 
	      
	      idTV=(TextView) findViewById(R.id.idTV);
	      faq=(TextView) findViewById(R.id.faq);
	      		fnameET=(EditText) findViewById(R.id.fname);
				lnameET=(EditText) findViewById(R.id.lname);
				emailET =(EditText) findViewById(R.id.emailid_reg);
				crollET =(EditText) findViewById(R.id.croll);
				mobnumET =(EditText) findViewById(R.id.mobnum);
				cbranchET =(EditText) findViewById(R.id.cbranch);
				cnameET =(EditText) findViewById(R.id.cname);
				cityET =(EditText) findViewById(R.id.city);
				passwordET =(EditText) findViewById(R.id.pw);
				bcodeET=(EditText) findViewById(R.id.bcodeET);
				
				idTV.setText(getResources().getString(R.string.id_name)+" : "+getResources().getString(R.string.id_default));
				faq.setText(getResources().getString(R.string.faq));
				emailET.setEnabled(true);
		boolean a = false;
		ConnectivityManager connectivityManager = (ConnectivityManager) this
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		a = activeNetworkInfo != null && activeNetworkInfo.isConnected();
		/*if (a) {
			try {
				new ExistingDetails().execute();
				
			} catch (Exception e) {
			}
		} else {
			Toast.makeText(
					this,
					"No internet connection. Check your connection and "
							+ "try again later", Toast.LENGTH_SHORT).show();
		}
		*/
		
		updatebut=(Button) findViewById(R.id.update);
		updatebut.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stubduration
				
				try{
					/*if(passwordET.getText().toString().length()<6)
						Toast.makeText(getApplicationContext(), "The password should have at least 6 characters!", Toast.LENGTH_LONG).show();
					else*/
						new UpdateDetails().execute();	
				}catch(Exception e){
					Toast.makeText(getApplicationContext(), "Not connected to internet", Toast.LENGTH_SHORT).show();
				}
				
				
			}
		});
		updatebut.setVisibility(View.GONE);
		genderRG = (RadioGroup)findViewById(R.id.radioGroup1);
		accomRG = (RadioGroup) findViewById(R.id.radioGroup2);
		genderRG.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {

			    boolean checked = ((RadioButton) findViewById(checkedId)).isChecked();
				switch(checkedId) {
				case R.id.Male:
		            if (checked)
		                gender="M";
		            break;
		        case R.id.Female:
		            if (checked)
		                gender="F";
		            break;
				}
				
			}
		});
        genderRG.check(R.id.Male);
        accomRG.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {

			    boolean checked = ((RadioButton) findViewById(checkedId)).isChecked();
				switch(checkedId) {
		        case R.id.want:
		            if (checked)
		                accomodation="1";
		            break;
		        case R.id.notwanted:
		            if (checked)
		                accomodation="0";
		            break;
				
				}
				
			}
		});
regbut=(Button) findViewById(R.id.confirm_reg);
regbut.setOnClickListener(new View.OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stubduration
		
		try{
				
			fname =fnameET.getText().toString();
			lname =lnameET.getText().toString();
			email =emailET.getText().toString();
			password =email;//passwordET.getText().toString();
			if(fname.isEmpty()||lname.isEmpty()||email.isEmpty())
				Toast.makeText(getApplicationContext(), "Please fill in Name and email ID.", Toast.LENGTH_LONG).show();
			else if(!isValidEmailAddress(email))
				Toast.makeText(getApplicationContext(), "Please enter a valid email ID.", Toast.LENGTH_LONG).show();
			/*else if(password.length()<6){
				Toast.makeText(getApplicationContext(), "The password should have at least 6 characters!", Toast.LENGTH_LONG).show();
				}*/
			else{
				if(emailET.isEnabled()){
					new SubmitRegistration().execute();
					
					}
				
				new UpdateDetails().execute();
			}
		}catch(Exception e){
			//Toast.makeText(getApplicationContext(), "Not connected to internet", Toast.LENGTH_SHORT).show();
		}
	}});
	regbut.setVisibility(View.VISIBLE);
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
		
	}
	 public boolean isValidEmailAddress(String email) {
	        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
	        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
	        java.util.regex.Matcher m = p.matcher(email);
	        return m.matches();
	 }
	public void onRadioButtonClicked(View view) {
	    // Is the button now checked?
	    // Check which radio button was clicked
	    switch(view.getId()) {
	        
	    }
	}
	
	@Override
		protected void onDestroy() {
			// TODO Auto-generated method stub

		SharedPreferences uid = getApplicationContext().getSharedPreferences("uid",0);
			uid.edit().clear().commit();
			super.onDestroy();
			
		}


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }
    

    public void onDateSet(DatePicker view, int year, int month, int day) {
        //do some stuff for example write on log and update TextField on activity
        Log.w("DatePicker","Date = " + year);
        dob= Calendar.getInstance();
        dob.set(year, month, day);
        this.year=year;
        this.month=month;
        this.day=day;
   
        dateView.setText("DOB : " + day+"/"+(month+1)+"/"+year);
    }
	public void showDatePickerDialog(View v) {
	    DialogFragment newFragment = new DatePickerFragment();
	    Log.d("DatePicker","got in");
	    newFragment.show(getSupportFragmentManager(), "datePicker");
	    Log.d("DatePicker","got in1");
	}
	@SuppressLint("ValidFragment")
    public class DatePickerFragment extends DialogFragment{

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
        	Log.d("DatePicker","got in3");
            // Use the current date as the default date in the picker
        	/*final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);*/

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), (OnDateSetListener)getActivity(), year, month, day);
        }

    }
	public void Clear(View v){
		//To Clear all fields for next new user
		SharedPreferences uid = getApplicationContext().getSharedPreferences("uid",0);
		ViewGroup group = (ViewGroup)findViewById(R.id.submainlayout);
		for (int i = 0, count = group.getChildCount(); i < count; ++i) {
		    View view = group.getChildAt(i);
		    if (view instanceof EditText) {
		        ((EditText)view).setText("");
		    }
		}
		bcodeET.setText("");
		branch_spinner.setSelection(0);
		genderRG.check(R.id.Male);
		accomRG.check(R.id.notwanted);
		emailET.setEnabled(true);
		idTV.setText(getResources().getString(R.string.id_name)+" : "+getResources().getString(R.string.id_default));
		uid.edit().clear().commit();
		updatebut.setVisibility(View.GONE);
		regbut.setVisibility(View.VISIBLE);
		final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
		dob.set(year, month, day);
		dateView.setText("DOB : --/--/----"); 
        fnameET.requestFocus();
	}

	public void Scan(View v){
        Intent intent = new Intent(getApplicationContext(), SimpleScannerActivity.class);
        startActivityForResult(intent, request_Code);
		/*IntentIntegrator scanIntegrator = new IntentIntegrator(this);
		// start scanning
		try {
			scanIntegrator.initiateScan();
		} catch (Exception e) {
			Log.e("<---Scan Error 1--->", ">>" + e.getMessage());
		}*/
	}
    public void BulkScan(View v){
        Intent intent = new Intent(getApplicationContext(), BulkScannerActivity.class);
        startActivity(intent);

    }
    public void GetBulkData(View v){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose from bulk scan data");
        final String[] bulk=app.getBulk();
        if(bulk.length==0){
            Toast.makeText(getApplicationContext(), "First scan using bulk scan mode!", Toast.LENGTH_SHORT).show();
        }
        else{
        builder.setItems(bulk, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
               String bcode=bulk[item];

                int ind=bcode.indexOf("."),check=bcode.indexOf("✔");
                if(check==-1) {
                    bulk[item] = "✔" + bulk[item];
                    app.setBulk(bulk);
                }
                bcode=bcode.substring(ind+2);
                bcodeET.setText(bcode);
                Toast.makeText(getApplicationContext(),"Chose "+bcode, Toast.LENGTH_SHORT).show();
            }
        }).show();}


    }
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		// retrieve result of scanning - instantiate ZXing object
		//IntentResult scanningResult = IntentIntegrator.parseActivityResult(
		//		requestCode, resultCode, intent);

		// Check for resultCode showing un-executed activity, fixes appcrash.
        if (requestCode == request_Code) {
            if (resultCode == RESULT_CANCELED) {
                Log.v("debug back crash", resultCode + "");
                // invalid scan data or scan canceled
                Toast toast = Toast.makeText(getApplicationContext(),
                        "No scan data received!", Toast.LENGTH_SHORT);
                toast.show();
                return;
            } else if (resultCode == RESULT_OK) {
                String bcode = intent.getStringExtra("barcode");
                bcodeET.setText(bcode);
                return;
            }
            else {
                // invalid scan data or scan canceled
                Toast toast = Toast.makeText(getApplicationContext(),
                        "No scan data received!", Toast.LENGTH_SHORT);
                toast.show();
            }
        }

		// check we have a valid result
		/*if (scanningResult != null) {
			String scanContent;
			String scanFormat;
			
				//tvr1.setText("Sending...");
				// gcontent from Intent Result
				scanContent = scanningResult.getContents();
				// get format name of data scanned
				scanFormat = scanningResult.getFormatName();
				bcodeET.setText(scanContent);
				// output to UI
				Log.i("Inside onActivityResult", "reached");
				boolean scanDone = true;
				if (scanContent != null) {
					//sendData();
				} else {
					//tvr1.setText("No data scanned");
					Log.e("<---Error Scan--->", ">>No Data Scanned");
				}
			
		}*/
	}

	class UpdateDetails extends AsyncTask<String, String, String> {
		private ProgressDialog pDialog;
		String token;
		JSONParser jsonParser = new JSONParser();
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(NewUser.this);
			pDialog.setMessage("Updating Details");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		
		
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			
			final List<NameValuePair> paramse = new ArrayList<NameValuePair>();
			String fname =fnameET.getText().toString();
			String lname =lnameET.getText().toString();
			String croll =crollET.getText().toString();
			String mobnum =mobnumET.getText().toString();
			String cbranch =branch_spinner.getSelectedItem().toString();//cbranchET.getText().toString();
			String cname =cnameET.getText().toString();
			String city =cityET.getText().toString();
			String password =passwordET.getText().toString();
			String bcode =bcodeET.getText().toString();
			Calendar cur=Calendar.getInstance();
			cur.setTime(new Date());
			int age = cur.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
		
		    if (dob.get(Calendar.MONTH) > cur.get(Calendar.MONTH) || 
		        (dob.get(Calendar.MONTH) == cur.get(Calendar.MONTH) && dob.get(Calendar.DATE) > cur.get(Calendar.DATE))) {
		        age--;
		    }
			int month = dob.get(Calendar.MONTH);
			int day = dob.get(Calendar.DATE);
			int year = dob.get(Calendar.YEAR);
			String DOBstring =Integer.toString(year)+"-"+String.format("%02d", month+1)+"-"+String.format("%02d", day);
			Log.d("G&A",gender+accomodation);
			/*if(password.length()<6){
				Toast.makeText(getActivity(), "The password should have at least 6 characters!", Toast.LENGTH_LONG).show();
				return null;}
				*/
			if(!fname.isEmpty())
			paramse.add(new BasicNameValuePair("first_name", fname));
			if(!lname.isEmpty())
			paramse.add(new BasicNameValuePair("last_name", lname));
			if(!croll.isEmpty())
			paramse.add(new BasicNameValuePair("college_roll", croll));
			if(!mobnum.isEmpty())
			paramse.add(new BasicNameValuePair("mobile_number", mobnum));
			if(!cbranch.isEmpty())
			paramse.add(new BasicNameValuePair("branch", cbranch));
			if(!cname.isEmpty())
			paramse.add(new BasicNameValuePair("college_text", cname));
			if(!city.isEmpty())
			paramse.add(new BasicNameValuePair("city", city));
			if(!bcode.isEmpty())
				paramse.add(new BasicNameValuePair("barcode", bcode));
			if(!password.isEmpty())
			paramse.add(new BasicNameValuePair("password", password));
			paramse.add(new BasicNameValuePair("want_accomodation", accomodation));
			paramse.add(new BasicNameValuePair("gender", gender));
			Log.d("date",DOBstring);
			Log.d("age",Integer.toString(age));
			Log.d("bcode",bcode);
			if(age!=0){
			paramse.add(new BasicNameValuePair("dob", DOBstring));
			paramse.add(new BasicNameValuePair("age", Integer.toString(age)));
			}
			SharedPreferences uid = getApplicationContext().getSharedPreferences("uid",0);
			token = uid.getString("uid", "nothing");
			
					String url = "api/mobile/profile/";
					JSONObject json = jsonParser.makeHttpRequest(url, "POST", paramse, token);
					Log.d("Update Results",json.toString());
			return null;
		}

		@Override
		protected void onPostExecute(String file_url) {
			pDialog.dismiss();
			if(token.equals("nothing")){
				Toast.makeText(getApplicationContext(), "ERR: Can't update Details", Toast.LENGTH_LONG).show();
				emailET.setEnabled(true);

				regbut.setVisibility(View.VISIBLE);
				updatebut.setVisibility(View.GONE);
			}
			else{
				Toast.makeText(getApplicationContext(), "Details Updated Successfully", Toast.LENGTH_SHORT).show();
				regbut.setVisibility(View.GONE);
				updatebut.setVisibility(View.VISIBLE);
			}
			/*Intent i=new Intent(PostView.this,PostView.class);
            i.putExtra("post_id", post_id);
           i.putExtra("WallName", wall_name);
            i.putExtra("passed", "notif");
            startActivity(i);8*/
		}
	}

	class SubmitRegistration extends AsyncTask<String, String, String> {
		private ProgressDialog pDialog;
		JSONParser jsonParser = new JSONParser();
		int error;
		String errmsg,user_id;
		@Override
		protected void onPreExecute() {
			Log.d("milestone01submitreg","got here");
			super.onPreExecute();
			error=0; errmsg="Generic Error"; user_id=null;
			pDialog = new ProgressDialog(NewUser.this);
			pDialog.setMessage("Making New Account");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		
		
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			Log.d("milestone02","got here");
			final List<NameValuePair> paramse = new ArrayList<NameValuePair>();
			
				
			paramse.add(new BasicNameValuePair("first_name", fname));
			paramse.add(new BasicNameValuePair("last_name", lname));
			paramse.add(new BasicNameValuePair("email", email));
			paramse.add(new BasicNameValuePair("password", password));
			SharedPreferences uid = getApplicationContext().getSharedPreferences("uid",0);
			
			
					String url = "participant_registration/";
					JSONObject json = jsonParser.makeHttpRequest(url, "POST", paramse, null);
					
					Log.d("Registration Results",json.toString());
					String tx = null;
						user_id=json.optString("user_id");
						tx = json.optString("token");
						if(tx.isEmpty()){
						error=1;
							errmsg=json.optJSONArray("email").optString(0);
						// TODO Auto-generated catch block
						}
						else{
					
					SharedPreferences.Editor editor = uid.edit();
					editor.putString("uid", tx);
					editor.putString("user", email);
					editor.commit();
						}
					
			return null;
		}

		@Override
		protected void onPostExecute(String file_url) {
			pDialog.dismiss();
			Log.d("milestoneend","got here");
			if(error==1){
				TextView msg = new TextView(NewUser.this);
				msg.setText(Html.fromHtml(errmsg));
				msg.setTextSize(16);
				msg.setTextColor(Color.GRAY);
				msg.setMovementMethod(LinkMovementMethod.getInstance());
				AlertDialog.Builder builder1 = new AlertDialog.Builder(NewUser.this);
	            builder1.setView(msg);
	            builder1.setCancelable(true);
	            builder1.setPositiveButton("OK",
	                    new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int id) {
	                    dialog.cancel();
	                }
	            });

	            AlertDialog alert11 = builder1.create();
	            alert11.show();
			}
			else{
				idTV.setText(getResources().getString(R.string.id_name)+" : "+getResources().getString(R.string.id_prefix)+String.format("%05d", Integer.parseInt(user_id)));
				Toast.makeText(getApplicationContext(), "Account made", Toast.LENGTH_SHORT).show();
				emailET.setEnabled(false);
				mobnumET.requestFocus();
				//Intent open = new Intent("org.shaastra.eventregistration.Menu");
				//startActivity(open);
			}
			/*Intent i=new Intent(PostView.this,PostView.class);
            i.putExtra("post_id", post_id);
           i.putExtra("WallName", wall_name);
            i.putExtra("passed", "notif");
            startActivity(i);8*/
		}
	}

}
