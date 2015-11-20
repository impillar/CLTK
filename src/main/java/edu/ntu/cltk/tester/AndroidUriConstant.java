package edu.ntu.cltk.tester;

import android.provider.ContactsContract;

public class AndroidUriConstant {

    /**
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("LOOKUP_URI: " + ContactsContract.Contacts.CONTENT_LOOKUP_URI);
        System.out.println("CONTENT_URI: " + ContactsContract.Contacts.CONTENT_URI);
        System.out.println("CONTENT_FILTER_URI: " + ContactsContract.PhoneLookup.CONTENT_FILTER_URI);
        System.out.println("CONTENT_FILTER_URI: " + ContactsContract.Contacts.CONTENT_FILTER_URI);
        System.out.println("ContactsContract.Data: " + ContactsContract.Data.CONTENT_URI);
    }

}
