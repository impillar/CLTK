package edu.ntu.cltk.android;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract.CommonDataKinds;
import android.provider.ContactsContract.Contacts;
import edu.ntu.cltk.data.ArrayUtil;

import java.util.ArrayList;
import java.util.List;

public class AndroidContentProviderUtils {

    /**
     * Before using this method, please ensure you have obtained the permission READ_CONTACTS
     *
     * @param context
     * @return
     */
    public static List<Friend> getContacts(Context context) {

        List<Friend> friends = new ArrayList<Friend>();

        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver.query(Contacts.CONTENT_URI, null, null, null, null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                Friend friend = new Friend();
                String contactId = cursor.getString(cursor.getColumnIndex(Contacts._ID));
                String name = cursor.getString(cursor.getColumnIndex(Contacts.DISPLAY_NAME));
                friend.setName(name);
                int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(Contacts.HAS_PHONE_NUMBER)));
                if (hasPhoneNumber > 0) {
                    Cursor phoneCursor = resolver.query(
                            CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            CommonDataKinds.Phone.CONTACT_ID + " = ? ",
                            new String[]{contactId},
                            null
                    );

                    while (phoneCursor.moveToNext()) {
                        String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(CommonDataKinds.Phone.NUMBER));
                        friend.addPhoneNumber(phoneNumber);
                    }
                    phoneCursor.close();

                    Cursor emailCursor = resolver.query(
                            CommonDataKinds.Email.CONTENT_URI,
                            null,
                            CommonDataKinds.Email.CONTACT_ID + " = ?",
                            new String[]{contactId},
                            null
                    );

                    while (emailCursor.moveToNext()) {
                        String email = emailCursor.getString(emailCursor.getColumnIndex(CommonDataKinds.Email.DATA));
                        friend.addEmail(email);
                    }

                    emailCursor.close();

                    friends.add(friend);
                }
            }
        }
        cursor.close();
        return friends;
    }

    /**
     * Before using this method, please ensure you have obtained the permission READ_SMS
     *
     * @param context
     * @return
     */
    public static List<SMS> getInboxSms(Context context) {
        List<SMS> smses = new ArrayList<SMS>();
        ContentResolver resolver = context.getContentResolver();
        String[] reqCols = new String[]{"_id", "address", "date", "type", "subject", "body"};
        Cursor cursor = resolver.query(
                Uri.parse("content://sms/inbox"),
                reqCols,
                null,
                null,
                null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                smses.add(cursorToSms(cursor));
            }
        }
        return smses;
    }

    public static List<SMS> getOutboxSms(Context context) {
        List<SMS> smses = new ArrayList<SMS>();
        // Create Sent box URI
        Uri sentURI = Uri.parse("content://sms/sent");

        // List required columns
        String[] reqCols = new String[]{"_id", "address", "date", "type", "subject", "body"};

        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver.query(sentURI, reqCols, null, null, null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                smses.add(cursorToSms(cursor));
            }
        }

        return smses;
    }

    public static List<SMS> getDraftSms(Context context) {
        List<SMS> smses = new ArrayList<SMS>();
        // Create Sent box URI
        Uri sentURI = Uri.parse("content://sms/draft");

        // List required columns
        String[] reqCols = new String[]{"_id", "address", "date", "type", "subject", "body"};

        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver.query(sentURI, reqCols, null, null, null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                smses.add(cursorToSms(cursor));
            }
        }

        return smses;
    }

    /**
     * Construct a SMS from a cursor
     *
     * @param cursor
     * @return
     */
    private static SMS cursorToSms(Cursor cursor) {
        SMS sms = new SMS();
        sms.setId(cursor.getInt(cursor.getColumnIndex("_id")));
        sms.setAddress(cursor.getString(cursor.getColumnIndex("address")));
        sms.setType(cursor.getInt(cursor.getColumnIndex("type")));
        sms.setSubject(cursor.getString(cursor.getColumnIndex("subject")));
        sms.setContent(cursor.getString(cursor.getColumnIndex("content")));
        sms.setDate(cursor.getInt(cursor.getColumnIndex("date")));
        return sms;
    }

    public static class Friend {
        String name;
        List<String> phoneNumbers;
        List<String> emails;

        public Friend() {
            this(null);
        }

        public Friend(String name) {
            this.name = name;
            phoneNumbers = new ArrayList<String>();
            emails = new ArrayList<String>();
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void addPhoneNumber(String phoneNumber) {
            this.phoneNumbers.add(phoneNumber);
        }

        public void addEmail(String email) {
            this.emails.add(email);
        }

        public List<String> getPhoneNumbers() {
            return this.phoneNumbers;
        }

        public void setPhoneNumbers(List<String> phoneNumbers) {
            this.phoneNumbers = phoneNumbers;
        }

        public List<String> getEmails() {
            return this.emails;
        }

        public void setEmails(List<String> emails) {
            this.emails = emails;
        }

        /**
         * Export a friend into a JSON format. For example:
         * <p>
         * {
         * "name": "pillar",
         * "phone": [87493,20394,3944],
         * "email": [a@dreamfirm.com,b@dreamfirm.com,c@dreamfirm.com]
         * }
         *
         * @return
         */
        public String exportToJson() {
            return String.format("{\"name\":%s,\"phone\":[%s],\"email\":[%s]}",
                    name,
                    ArrayUtil.serializeArray(phoneNumbers, ","),
                    ArrayUtil.serializeArray(emails, ","));
        }

        @Override
        public String toString() {
            return exportToJson();
        }
    }

    /**
     * The data structure for SMS content provider
     * Column ID    -   Column Name
     * 0         :     _id
     * 1         :    thread_id
     * 2         :    address
     * 3         :    person
     * 4         :    date
     * 5         :    protocol
     * 6         :    read
     * 7         :    status
     * 8         :    type
     * 9         :    reply_path_present
     * 10        :    subject
     * 11        :    body
     * 12        :    service_center
     * 13        :    locked
     * <p>
     * public static final int MESSAGE_TYPE_ALL    = 0;
     * public static final int MESSAGE_TYPE_INBOX  = 1;
     * public static final int MESSAGE_TYPE_SENT   = 2;
     * public static final int MESSAGE_TYPE_DRAFT  = 3;
     * public static final int MESSAGE_TYPE_OUTBOX = 4;
     * public static final int MESSAGE_TYPE_FAILED = 5; // for failed outgoing messages
     * public static final int MESSAGE_TYPE_QUEUED = 6; // for messages to send later
     *
     * @author pillar
     */
    public static class SMS {

        public static final int MESSAGE_TYPE_ALL = 0;
        public static final int MESSAGE_TYPE_INBOX = 1;
        public static final int MESSAGE_TYPE_SENT = 2;
        public static final int MESSAGE_TYPE_DRAFT = 3;
        public static final int MESSAGE_TYPE_OUTBOX = 4;
        public static final int MESSAGE_TYPE_FAILED = 5; // for failed outgoing messages
        public static final int MESSAGE_TYPE_QUEUED = 6; // for messages to send later

        private int id;
        private String address;
        private int date;
        private int type;
        private String subject;
        private String content;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getDate() {
            return date;
        }

        public void setDate(int date) {
            this.date = date;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public boolean isInbox() {
            return MESSAGE_TYPE_INBOX == type;
        }

        public boolean isOutbox() {
            return MESSAGE_TYPE_OUTBOX == type;
        }

        public boolean isDraft() {
            return MESSAGE_TYPE_DRAFT == type;
        }

        public boolean isSent() {
            return MESSAGE_TYPE_SENT == type;
        }

    }

}
