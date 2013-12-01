package com.example.startui;

import java.io.File;
import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.util.Log;
import android.app.ProgressDialog;
import android.content.Context;

@SuppressWarnings("deprecation")
public class MovieUploader extends AsyncTask<String, Integer, Integer> {

	ProgressDialog dialog;
	Context context;

	public MovieUploader(Context context) {
		this.context = context;
	}

	@Override
	protected Integer doInBackground(String... params) {
		// TODO Auto-generated method stub
		try {
			String fileName = params[0];

			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost("http://usi.mods.jp/upload.php");
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			MultipartEntity multipartEntity = new MultipartEntity(
					HttpMultipartMode.BROWSER_COMPATIBLE);

			File file = new File(fileName);
			FileBody fileBody = new FileBody(file);
			multipartEntity.addPart("f1", fileBody);

			httpPost.setEntity(multipartEntity);
			httpClient.execute(httpPost, responseHandler);
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			Log.d("Upload err", "x01");
		} catch (IOException e) {
			e.printStackTrace();
			Log.d("Upload err", "x02");
		}

		return null;
	}

	protected void onPostExecute(Integer result) {
		if (dialog != null) {
			dialog.dismiss();
		}
	}

	@Override
	protected void onPreExecute() {
		dialog = new ProgressDialog(context);
		dialog.setTitle("Please wait");
		dialog.setMessage("Uploading...");
		dialog.show();
	}

}
