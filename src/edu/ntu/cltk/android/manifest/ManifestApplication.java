package edu.ntu.cltk.android.manifest;

import edu.ntu.cltk.data.ArrayUtil;

import java.util.ArrayList;
import java.util.List;

public class ManifestApplication extends ManifestElement {

    public final static String TAG = "application";

    private String name;
    private List<ManifestActivity> activities = new ArrayList<ManifestActivity>();
    private List<ManifestActivityAlias> alias = new ArrayList<ManifestActivityAlias>();
    private List<ManifestService> services = new ArrayList<ManifestService>();
    private List<ManifestReceiver> receivers = new ArrayList<ManifestReceiver>();
    private List<ManifestProvider> providers = new ArrayList<ManifestProvider>();
    private List<ManifestLibrary> libraries = new ArrayList<ManifestLibrary>();

    public ManifestApplication() {
        this.name = "";
    }

    public ManifestApplication(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ManifestActivity> getActivities() {
        return activities;
    }

    public void setActivities(List<ManifestActivity> activities) {
        this.activities = activities;
    }

    public void addActivity(ManifestActivity activity) {
        this.activities.add(activity);
    }

    public List<ManifestActivityAlias> getAlias() {
        return alias;
    }

    public void setAlias(List<ManifestActivityAlias> alias) {
        this.alias = alias;
    }

    public void addAlias(ManifestActivityAlias alias) {
        this.alias.add(alias);
    }

    public List<ManifestService> getServices() {
        return services;
    }

    public void setServices(List<ManifestService> services) {
        this.services = services;
    }

    public void addService(ManifestService service) {
        this.services.add(service);
    }

    public List<ManifestReceiver> getReceivers() {
        return receivers;
    }

    public void setReceivers(List<ManifestReceiver> receivers) {
        this.receivers = receivers;
    }

    public void addReceiver(ManifestReceiver receiver) {
        this.receivers.add(receiver);
    }

    public List<ManifestProvider> getProviders() {
        return providers;
    }

    public void setProviders(List<ManifestProvider> providers) {
        this.providers = providers;
    }

    public void addProvider(ManifestProvider provider) {
        this.providers.add(provider);
    }

    public List<ManifestLibrary> getLibraries() {
        return this.libraries;
    }

    public void setLibraries(List<ManifestLibrary> libraries) {
        this.libraries = libraries;
    }

    public void addLibrary(ManifestLibrary library) {
        this.libraries.add(library);
    }

    @SuppressWarnings("StringBufferReplaceableByString")
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("name: " + this.name + "\n");
        sb.append("activities: " + ArrayUtil.serializeArray(activities, ", ") + "\n");
        sb.append("activities-alias: " + ArrayUtil.serializeArray(alias, ", ") + "\n");
        sb.append("service: " + ArrayUtil.serializeArray(services, ", ") + "\n");
        sb.append("receivers: " + ArrayUtil.serializeArray(services, ", ") + "\n");
        sb.append("providers: " + ArrayUtil.serializeArray(providers, ", ") + "\n");
        sb.append("libraries: " + ArrayUtil.serializeArray(libraries, ", ") + "\n");
        return sb.toString();
    }
}
