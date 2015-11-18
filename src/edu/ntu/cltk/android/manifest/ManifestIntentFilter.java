package edu.ntu.cltk.android.manifest;


import org.dom4j.Element;

public class ManifestIntentFilter extends ManifestElement {

    public final static String TAG = "intent-filter";

    protected String action;
    protected String category;
    protected String data;

    public ManifestIntentFilter(String action, String category, String data) {
        super(TAG);
        this.action = action;
        this.category = category;
        this.data = data;
    }

    public ManifestIntentFilter(Element intentFilter) {
        this.action = intentFilter.elementText("action");
        this.category = intentFilter.elementText("category");
        this.data = intentFilter.elementText("data");
    }


    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ManifestIntentFilter)) {
            return false;
        }
        ManifestIntentFilter mif = (ManifestIntentFilter) obj;
        if (action == null) {
            if (mif.action != null)
                return false;
        } else {
            if (mif.action == null || !mif.action.equals(action))
                return false;
        }

        if (category == null) {
            if (mif.category != null)
                return false;
        } else {
            if (mif.category == null || !mif.category.equals(category))
                return false;
        }

        if (data == null) {
            if (mif.data != null)
                return false;
        } else {
            if (mif.data == null || !mif.data.equals(data))
                return false;
        }
        return true;
    }

}
