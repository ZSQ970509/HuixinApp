package com.king.photo.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.hc.android.huixin.R;
import com.hc.android.huixin.network.MaintenanceTypeItem;
import com.hc.android.huixin.network.NetworkApi;
import com.hc.android.huixin.util.ToastHelp;
import com.king.photo.adapter.PagingBaseadapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class PagedQueryResultHelper {
	private static int initialFromRecordNum = 1;// 每页从第1条开始
	private static int initialToRecordNum = 8;// 每页可以显示5条
	ActivityAdjustProjectMain contextActivity;
	// ArrayList<String[]> str = new ArrayList<String[]>();

	public PagedQueryResultHelper(ActivityAdjustProjectMain contextActivity, String title) {// 构造函数用来初始化，主要是用来接收传过来的activity做为上下文对象
		this.contextActivity = contextActivity;
		mtitle = title;
		findViews();// 调用此方法找到传过来的activity的控件对象
		setOnClickListener();// 给控件设置监听
		if (currentPageText != null) {// 如果所有页面只有一页，那就只显示一页
			setCurrentPage(0);
		}
		initialToRecordNum = 8;

	}

	public String initialQuery() {
		setRecordNumEachPage(initialToRecordNum - initialFromRecordNum + 1);// 设置每页的记录条数
		// setRecordNumEachPage(initialToRecordNum );
		return query();
	}

	private static void setCurrentPage(int currentPage) {// 设置当前页
		PagedQueryResultHelper.currentPage = currentPage;
		currentPageText.setText(String.valueOf(currentPage + 1));
	}

	public void setRecordNumEachPage(int recordNumEachPage) {// 设置每页的记录条数
		this.recordNumEachPage = recordNumEachPage;
		recordNumEachPageText.setText(String.valueOf(recordNumEachPage));
	}

	public void setTotalRecord(int totalRecord) {// 设置总记录数
		this.totalRecord = totalRecord;
		totalRecordNumText.setText(String.valueOf(totalRecord));
		getTotalPageNumText();
	}

	private TextView totalPageNumText;
	private TextView totalRecordNumText;
	private TextView recordNumEachPageText;
	private Button firstPage;
	private Button pageDown;
	private Button pageUp;
	private Button lastPage;
	private static EditText currentPageText;
	private Button toPage;
	private Button toCancel;
	private int totalPage;
	private static int totalRecord;
	private int recordNumEachPage;
	private static int currentPage;
	// private String countTableName;

	String mKeyword;
	String mCode;
	Handler mHandler = new Handler();
	String mtitle;
	// private DataTableAdapter dataTableAdapter;

	public void setQueryParams(String keyword, String code) {
		String pageIndex = currentPage + "";
		String pageSize = initialToRecordNum + "";
		totalRecord = 0;
		totalPage = 0;
		totalPageNumText.setText(String.valueOf(0));
		totalRecordNumText.setText(String.valueOf(0));
		mKeyword = keyword;
		mCode = code;

	}

	private class GetTotalRulesAsyncTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			return new NetworkApi().GetotalAdjustPage(params[0], params[1], params[2], params[3]);
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result != null) {
				try {
					JSONObject json = new JSONObject(result);
					totalRecord = (Integer) json.get("total");
					if (totalRecord <= 0) {
						contextActivity.finish();
						ToastHelp.showToast(contextActivity, "没有搜索到内容");
						return;
					}

					if (totalRecord != 0 && PagedQueryResultHelper.totalRecord == 0 && currentPageText != null) {
						PagedQueryResultHelper.setCurrentPage(1);
					}

					setTotalRecord(totalRecord);

					ListView listView = (ListView) contextActivity.findViewById(R.id.list);
					ArrayList<String> str = new ArrayList<String>();

					JSONArray josnArray = json.getJSONArray("rows");

					PagingBaseadapter baseadapter = new PagingBaseadapter(contextActivity, mtitle, josnArray);
					listView.setAdapter(baseadapter);// 注意，关键点：将数据显示在listview
				} catch (Exception e) {
					Log.e("Exception", e.getMessage());
				}
			}
		}

	}

	public int getCurrentPage() {
		return currentPage;
	}

	private void findViews() {
		totalPageNumText = (TextView) contextActivity.findViewById(R.id.total_page_num);
		totalRecordNumText = (TextView) contextActivity.findViewById(R.id.total_record_num);
		recordNumEachPageText = (TextView) contextActivity.findViewById(R.id.record_num_each_page);
		firstPage = (Button) (contextActivity).findViewById(R.id.first_page);
		pageDown = (Button) contextActivity.findViewById(R.id.page_down);
		pageUp = (Button) contextActivity.findViewById(R.id.page_up);
		lastPage = (Button) contextActivity.findViewById(R.id.last_page);
		currentPageText = (EditText) contextActivity.findViewById(R.id.current_page);
		toPage = (Button) contextActivity.findViewById(R.id.to_page);
		toCancel = (Button) contextActivity.findViewById(R.id.to_cancel);

	}

	private void setOnClickListener() {
		if (firstPage != null) {
			firstPage.setOnClickListener(contextActivity);
		}
		if (pageDown != null) {
			pageDown.setOnClickListener(contextActivity);
		}
		if (pageUp != null) {
			pageUp.setOnClickListener(contextActivity);
		}
		if (lastPage != null) {
			lastPage.setOnClickListener(contextActivity);
		}
		if (toPage != null) {
			toPage.setOnClickListener(contextActivity);
		}
		if (toCancel != null) {
			toCancel.setOnClickListener(contextActivity);
		}

	}

	private void getRecordNumEachPageText() {
		try {
			recordNumEachPage = Integer.parseInt(recordNumEachPageText.getText().toString());
		} catch (NumberFormatException e) {
			Log.e("NumberFormatException", e.getMessage());
		}
	}

	private boolean outOfEachPageRange() {
		recordNumEachPage = Integer.parseInt(recordNumEachPageText.getText().toString());
		if (recordNumEachPage > initialToRecordNum || recordNumEachPage < 1) {
			return true;
		}

		return false;
	}

	private void getTotalPageNumText() {
		getRecordNumEachPageText();
		if (recordNumEachPage == 0)
			return;
		if (totalRecord == 0) {
			totalPage = 0;
			totalPageNumText.setText(String.valueOf(0));
			return;
		}
		if (totalRecord % recordNumEachPage == 0) {// java中分页常用到的计算公式
			totalPage = totalRecord / recordNumEachPage;
		} else {
			totalPage = totalRecord / recordNumEachPage + 1;
		}
		totalPageNumText.setText(String.valueOf(totalPage));
	}

	public void pageDown() {
		if (outOfEachPageRange()) {
			AlertDialog.Builder dialog = new AlertDialog.Builder(contextActivity);
			dialog.setTitle("提示：").setMessage("每页显示条数在1~20之间！").setPositiveButton("确认", null).show();
			return;
		}
		if (currentPage != totalPage) {
			++currentPage;

			if (currentPage >= totalPage) {
				setCurrentPage(--currentPage);
			} else {
				setCurrentPage(currentPage);
			}
			getTotalPageNumText();
			query();
		}
	}

	public void pageUp() {

		if (outOfEachPageRange()) {
			AlertDialog.Builder dialog = new AlertDialog.Builder(contextActivity);
			dialog.setTitle("提示：").setMessage("每页显示条数在1~20之间！").setPositiveButton("确认", null).show();
			return;
		}
		// if (currentPage != 1 && currentPage != 0) {
		if (currentPage != 0) {
			setCurrentPage(--currentPage);
			getTotalPageNumText();
			query();
		}
	}

	public void firstPage() {
		if (outOfEachPageRange()) {
			AlertDialog.Builder dialog = new AlertDialog.Builder(contextActivity);
			dialog.setTitle("提示：").setMessage("每页显示条数在1~20之间！").setPositiveButton("确认", null).show();
			return;
		}
		getTotalPageNumText();
		if (totalPage > 0) {
			setCurrentPage(0);
			query();
		}
	}

	public void lastPage() {
		if (outOfEachPageRange()) {
			AlertDialog.Builder dialog = new AlertDialog.Builder(contextActivity);
			dialog.setTitle("提示：").setMessage("每页显示条数在1~20之间！").setPositiveButton("确认", null).show();
			return;
		}
		getTotalPageNumText();
		if (totalPage != 1 && totalPage != 0) {
			setCurrentPage(totalPage - 1);

			query();
		}
	}

	public void toPage() {
		if (outOfEachPageRange()) {
			AlertDialog.Builder dialog = new AlertDialog.Builder(contextActivity);
			dialog.setTitle("提示：").setMessage("每页显示条数在1~20之间！").setPositiveButton("确认", null).show();
			return;
		}
		try {
			currentPage = Integer.parseInt(currentPageText.getText().toString());
			int tempPage = Integer.parseInt(currentPageText.getText().toString());
			if (currentPage > totalPage) {
				AlertDialog.Builder dialog = new AlertDialog.Builder(contextActivity);
				dialog.setTitle("提示：发生错误").setMessage("总共只有" + totalPage + "页，你选择的是" + tempPage)
						.setPositiveButton("确认", null).show();
				return;
			}
			currentPage = tempPage;
		} catch (NumberFormatException e) {
			Log.e("NumberFormatException", e.getMessage());
			return;
		}

		getTotalPageNumText();
		new GetTotalRulesAsyncTask().execute(mKeyword, currentPage + "", initialToRecordNum + "", mCode);

		currentPageText.setText(String.valueOf(currentPage));
		currentPage--;
	}

	public String query() {// 此方法要重点注意，因为这里是根据webservice查询到的数据，所以要传递来方法和参数

		new GetTotalRulesAsyncTask().execute(mKeyword, currentPage + 1 + "", initialToRecordNum + "", mCode);

		return "True";// 返回数据
	}

	public void toCancel() {
		contextActivity.finish();
	}

	public String searchToFirstPage() {
		setCurrentPage(0);

		return "True";
	}
}
