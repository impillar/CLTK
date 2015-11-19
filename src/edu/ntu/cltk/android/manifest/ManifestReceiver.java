package edu.ntu.cltk.android.manifest;

import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class ManifestReceiver extends ManifestElement {

    public final static String TAG = "receiver";
    protected List<ManifestIntentFilter> intentFilters = new ArrayList<ManifestIntentFilter>();

    protected ManifestReceiver(Element element) {
    }


    public void addIntentFilter(ManifestIntentFilter intentFilter) {
        this.intentFilters.add(intentFilter);
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
