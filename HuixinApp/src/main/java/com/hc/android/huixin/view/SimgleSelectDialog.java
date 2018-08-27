package com.hc.android.huixin.view;

import com.hc.android.huixin.R;
import com.hc.android.huixin.view.CustomDialog.Builder;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class SimgleSelectDialog extends Dialog {

	public SimgleSelectDialog(Context context) {
		super(context);
	}

	public SimgleSelectDialog(Context context, int theme) {
		super(context, theme);
	}

	public static class Builder {
		private Context context;
		private String title;
		private String[] message;

		private View contentView;
		private OnItemClickListener OnItemClickListener;
		public Builder(Context context) {
			this.context = context;
		}

		public Builder setItemMessage( String[] message) {
			this.message = message;
			return this;
		}

		/**
		 * Set the Dialog title from resource
		 * 
		 * @param title
		 * @return
		 */
		public Builder setTitle(int title) {
			this.title = (String) context.getText(title);
			return this;
		}

		/**
		 * Set the Dialog title from String
		 * 
		 * @param title
		 * @return
		 */

		public Builder setTitle(String title) {
			this.title = title;
			return this;
		}

		public Builder setContentView(View v) {
			this.contentView = v;
			return this;
		}
		
		public Builder setOnItemClick( OnItemClickListener listener) {
		
			this.OnItemClickListener = listener;
			return this;
		}
		

		public SimgleSelectDialog create() {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			// instantiate the dialog with the custom Theme
			final SimgleSelectDialog dialog = new SimgleSelectDialog(context, R.style.Dialog);
			View layout = inflater.inflate(R.layout.dialog_singleselect_layout, null);
			dialog.addContentView(layout, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			// set the dialog title
			((TextView) layout.findViewById(R.id.title)).setText(title);

			if (OnItemClickListener!=null) {
				((ListView) layout.findViewById(R.id.message)).setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
						// TODO Auto-generated method stub
						OnItemClickListener.onItemClick(arg0, arg1, arg2, arg3);
						
						dialog.dismiss();
					}
				});
			}
			
			
			// set the content message
			if (message != null) {
				((ListView) layout.findViewById(R.id.message)).setAdapter(new ArrayAdapter<String>(context, R.layout.item_simple_list_1,message));
		
			
				
				
			} else if (contentView != null) {
				// if no message set
				// add the contentView to the dialog body
				((LinearLayout) layout.findViewById(R.id.content)).removeAllViews();
				((LinearLayout) layout.findViewById(R.id.content)).addView(contentView,
						new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
			}
			dialog.setContentView(layout);
			return dialog;
		}
	}

}