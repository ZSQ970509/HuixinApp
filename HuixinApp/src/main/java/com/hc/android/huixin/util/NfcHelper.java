package com.hc.android.huixin.util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Locale;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.nfc.tech.NfcA;
import android.nfc.tech.NfcB;
import android.nfc.tech.NfcF;
import android.nfc.tech.NfcV;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;

public class NfcHelper {

	private static final String TAG = NfcHelper.class.getSimpleName();
	private static NfcHelper instance;
	private NfcAdapter mAdapter;
	private Activity mActivity;

	private NfcHelper() {

	}

	public static NfcHelper getInstance() {
		if (instance == null) {
			instance = new NfcHelper();
		}
		return instance;
	}

	public boolean isSupportNfc() {
		return mAdapter != null ? true : false;
	}

	public boolean isEnableNfc() {
		if (mAdapter != null) {
			return mAdapter.isEnabled();
		} else {
			return false;
		}
	}

	public void init(Activity activity) {
		mActivity = activity;
		try {
			mAdapter = NfcAdapter.getDefaultAdapter(mActivity);
			if (mAdapter == null) {
				Toast.makeText(mActivity, "手机没有NFC功能！", Toast.LENGTH_SHORT).show();
				return;
			}
			if (mAdapter != null && !mAdapter.isEnabled()) {
				Toast.makeText(mActivity, "手机没有开启NFC功能！请去设置界面打开。", Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void onResume() {
		if (mAdapter == null) {
			return;
		}
		PendingIntent mPendingIntent = PendingIntent.getActivity(mActivity, 0,
				new Intent(mActivity, mActivity.getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
		IntentFilter ndef = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
		IntentFilter[] mFilters = new IntentFilter[] { ndef };
		String[][] mTechLists = new String[][] { new String[] { NfcF.class.getName() },
				new String[] { NfcA.class.getName() }, new String[] { NfcB.class.getName() },
				new String[] { NfcV.class.getName() } };
		if (mAdapter != null) {
			mAdapter.enableForegroundDispatch(mActivity, mPendingIntent, mFilters, mTechLists);
		}
	}

	public void onPause() {
		if (mAdapter != null) {
			mAdapter.disableForegroundDispatch(mActivity);
		}
	}

	public String readNfcTag(Intent intent) {
		if (mAdapter == null || intent == null) {
			return "";
		}
		intent.setAction(NfcAdapter.ACTION_NDEF_DISCOVERED);
		StringBuilder textRecord = new StringBuilder();
		Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
		if (rawMsgs != null) {
			NdefMessage[] msgs = new NdefMessage[rawMsgs.length];
			for (int i = 0; i < rawMsgs.length; i++) {
				msgs[i] = (NdefMessage) rawMsgs[i];
			}
			try {
				for (NdefMessage msg : msgs) {
					for (NdefRecord record : msg.getRecords()) {
						textRecord.append(parse(record));
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				Log.e(TAG, e.getMessage());
			}
		}
		return textRecord.toString();
	}

	public String readNFCTagId(Intent intent) {
		if (mAdapter == null || intent == null) {
			return "";
		}
		String tagId = "";
		try {
			byte[] bytesid = intent.getByteArrayExtra(NfcAdapter.EXTRA_ID);
			tagId = ByteArrayToHexString(bytesid);
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, e.getMessage());
		}
		return tagId;
	}

	private String ByteArrayToHexString(byte[] inarray) { // converts byte
															// arrays to string
		int i, j, in;
		String[] hex = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F" };
		String out = "";

		for (j = 0; j < inarray.length; ++j) {
			in = inarray[j] & 0xff;
			i = (in >> 4) & 0x0f;
			out += hex[i];
			i = in & 0x0f;
			out += hex[i];
		}
		return out;
	}

	public boolean writeNfcTag(Intent intent, String text, boolean readOnly) {
		boolean result = false;
		if (mAdapter == null || intent == null) {
			return result;
		}
		Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
		NdefMessage ndefMessage = new NdefMessage(new NdefRecord[] { createTextRecord(text) });
		int size = ndefMessage.toByteArray().length;
		try {
			Ndef ndef = Ndef.get(tag);
			if (ndef != null) {
				ndef.connect();
				if (ndef.isWritable() && size < ndef.getMaxSize()) {
					ndef.writeNdefMessage(ndefMessage);
					if (readOnly && ndef.canMakeReadOnly()) {
						ndef.makeReadOnly();
					}
					result = true;
				}
			} else {
				NdefFormatable format = NdefFormatable.get(tag);
				if (format != null) {
					try {
						format.connect();
						if (readOnly) {
							format.formatReadOnly(ndefMessage);
						} else {
							format.format(ndefMessage);
						}
						result = true;
					} catch (Exception e) {
						e.printStackTrace();
						Log.e(TAG, e.getMessage());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, e.getMessage());
		}
		return result;
	}

	private String parse(NdefRecord record) {
		String text = "";
		if (record.getTnf() == NdefRecord.TNF_WELL_KNOWN && Arrays.equals(record.getType(), NdefRecord.RTD_TEXT)) {
			try {
				byte[] payload = record.getPayload();
				String textEncoding = ((payload[0] & 0x80) == 0) ? "UTF-8" : "UTF-16";
				int languageCodeLength = payload[0] & 0x3f;
				text = new String(payload, languageCodeLength + 1, payload.length - languageCodeLength - 1,
						textEncoding);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				Log.e(TAG, e.getMessage());
			}
		}
		return text;
	}

	public NdefRecord createTextRecord(String text) {
		byte[] langBytes = Locale.CHINA.getLanguage().getBytes(Charset.forName("US-ASCII"));
		Charset utfEncoding = Charset.forName("UTF-8");
		byte[] textBytes = text.getBytes(utfEncoding);
		int utfBit = 0;
		char status = (char) (utfBit + langBytes.length);
		byte[] data = new byte[1 + langBytes.length + textBytes.length];
		data[0] = (byte) status;
		System.arraycopy(langBytes, 0, data, 1, langBytes.length);
		System.arraycopy(textBytes, 0, data, 1 + langBytes.length, textBytes.length);
		NdefRecord record = new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, new byte[0], data);
		return record;
	}
}
