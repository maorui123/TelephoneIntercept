package com.maorui.telephoneintercept.util;

import java.util.ArrayList;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.text.TextUtils;
import android.util.Log;

import com.maorui.telephoneintercept.bean.ContactsBean;
import com.maorui.telephoneintercept.service.PhoneListenService;

public class ContactsUtils {

	/**
	 * 获取联系人信息,包括：名称、号码、头像
	 * 
	 * @return
	 */
	public static ArrayList<ContactsBean> getAllContacts(Context ctx) {

		ArrayList<ContactsBean> al = new ArrayList<ContactsBean>();

		ContentResolver resolver = ctx.getContentResolver();
		String[] columns = new String[] { Phone.DISPLAY_NAME, Phone.NUMBER, Phone.PHOTO_ID, Phone.CONTACT_ID };
		Cursor cursor = resolver.query(Phone.CONTENT_URI, columns, null, null, null);

		while (cursor.moveToNext()) {

			ContactsBean node = new ContactsBean();

			String name = cursor.getString(cursor.getColumnIndex(Phone.DISPLAY_NAME));
			String number = cursor.getString(cursor.getColumnIndex(Phone.NUMBER));

			if (TextUtils.isEmpty(number)) { // 判断号码是否为空
				continue;
			}
			long photoId = cursor.getLong(cursor.getColumnIndex(Phone.PHOTO_ID));
			long contactId = cursor.getLong(cursor.getColumnIndex(Phone.CONTACT_ID));

			node.setId(contactId + "");
			node.setName(name);
			node.setNumber(number);
			node.setPhoto(photoId + "");

			al.add(node);
		}
		cursor.close();

		return al;

	}

	/**
	 * 获取联系人头像
	 * 
	 * @param people_id
	 * @return
	 */
	public static byte[] getPhoto(Context ctx, String photo_id) {
		String selection = ContactsContract.Data._ID + " = " + photo_id;

		String[] projection = new String[] { ContactsContract.Data.DATA15 };
		Cursor cur = ctx.getContentResolver().query(ContactsContract.Data.CONTENT_URI, projection, selection, null, null);
		cur.moveToFirst();
		byte[] contactIcon = cur.getBlob(0);
		cur.close();
		Log.i("contactsUtils", "conTactIcon:" + contactIcon);
		if (contactIcon == null) {
			return null;
		} else {
			return contactIcon;
		}
	}

	//
	// /**
	// * 设置联系人头像 如果有头像，就不会出错，如果没有头像，就会出错。1：正确 2：错误
	// */
	// public void setPhoto() {
	// // 以下代码将字节数组转化成Bitmap对象，然后再ImageView中显示出来
	// ImageView image = null;
	// image = (ImageView) this.findViewById(R.id.imageview1);
	// String contactId = "1"; // 2
	// byte[] photo = getPhoto(contactId);
	// Bitmap map = BitmapFactory.decodeByteArray(photo, 0, photo.length);
	// image.setImageBitmap(map);
	// }

	public static void startService(Context ctx) {
		System.out.println("PhoneListenService 启动监听电话的服务");
		Intent service = new Intent(ctx, PhoneListenService.class);
		ctx.startService(service);
	}

}
