package edu.ntu.cltk.android.manifest;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pillar
 */
public class ManifestActivity extends ManifestElement {

    public final static String TAG = "activity";
    protected List<ManifestIntentFilter> intentFilters = new ArrayList<ManifestIntentFilter>();
    private String name;

    @Override
    public String getName() {
        return name;
    }

    /**
     * We need to change the format of class from a.b.c.d to a/b/c/d
     * Because the value from soot is under this format
     *
     * @param name
     */

    public void setName(String name) {
        this.name = name.replace('.', '/');
    }

    public void addIntentFilter(ManifestIntentFilter intentFilter) {
        this.intentFilters.add(intentFilter);
    }

    public List<ManifestIntentFilter> getIntentFilters() {
        return this.intentFilters;
    }

    @Override
    public String toString() {
        return this.name;
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
