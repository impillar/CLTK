package edu.ntu.cltk.android.manifest;

import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class ManifestService extends ManifestElement {

    public final static String TAG = "service";

    protected List<ManifestIntentFilter> intentFilters = new ArrayList<ManifestIntentFilter>();

    protected ManifestService(Element element) {
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
