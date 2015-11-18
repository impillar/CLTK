package edu.ntu.cltk.android.manifest;

import java.util.ArrayList;
import java.util.List;

public class ManifestActivity extends ManifestElement {

    public final static String TAG = "activity";
    protected List<ManifestIntentFilter> intentFilters = new ArrayList<ManifestIntentFilter>();

    public ManifestActivity(String name) {
        super(name);
    }


    public void addIntentFilter(ManifestIntentFilter intentFilter) {
        this.intentFilters.add(intentFilter);
    }

    public List<ManifestIntentFilter> getIntentFilters() {
        return this.intentFilters;
    }

    public boolean containsAction(String action) {
        for (ManifestIntentFilter filter : intentFilters) {
            if (filter.getAction().equalsIgnoreCase(action)) {
                return true;
            }
        }
        return false;
    }

    public List<String> getAllActions() {
        List<String> actions = new ArrayList<String>();
        for (ManifestIntentFilter filter : intentFilters) {
            actions.add(filter.getAction());
        }
        return actions;
    }

}
