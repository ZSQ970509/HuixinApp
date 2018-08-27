package com.hc.android.huixin.view;

import android.widget.Spinner;
import java.util.ArrayList;

import com.hc.android.huixin.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * Spinner自定义样式类 2016年3月25日11:02:56
 * 
 */
public class SpinnerCustomer extends Spinner implements OnItemClickListener {

	public static SpinnerSelectDialog dialog = null;
	private ArrayList<String> list = new ArrayList<String>();
	public static String text;

	public SpinnerCustomer(Context context, AttributeSet attrs) {
		super(context, attrs);

	}

	// 如果视图定义了OnClickListener监听器，调用此方法来执行
	@Override
	public boolean performClick() {
		Context context = getContext();
		final LayoutInflater inflater = LayoutInflater.from(getContext());
		final View view = inflater.inflate(R.layout.spinnerformcustom, null);
		final ListView listview = (ListView) view.findViewById(R.id.formcustomspinner_list);
		SpinnerListviewAdapter adapters = new SpinnerListviewAdapter(context, getList());
		listview.setAdapter(adapters);
		listview.setOnItemClickListener(this);
		dialog = new SpinnerSelectDialog(context, R.style.dialog);// 创建Dialog并设置样式主题
		LayoutParams params = new LayoutParams(650, LayoutParams.FILL_PARENT);
		dialog.setCanceledOnTouchOutside(true);// 设置点击Dialog外部任意区域关闭Dialog
		dialog.show();
		dialog.addContentView(view, params);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> view, View itemView, int position, long id) {
		setSelection(position);
		setText(list.get(position));
		if (dialog != null) {
			dialog.dismiss();
			dialog = null;
		}
	}

	public ArrayList<String> getList() {
		return list;
	}

	public void setList(ArrayList<String> list) {

		this.list = list;

	}

	public void setStringList(String[] list) {
		ArrayList<String> templist = new ArrayList<String>();
		for (int i = 0; i < list.length; i++) {
			templist.add(list[i]);
		}

		this.list = templist;

	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}