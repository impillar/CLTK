package edu.ntu.cltk.android.manifest;

import edu.ntu.cltk.data.ArrayUtil;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ManifestApplication extends ManifestElement {

    public final static String TAG = "application";

    private List<ManifestActivity> activities = new ArrayList<ManifestActivity>();
    private List<ManifestActivityAlias> alias = new ArrayList<ManifestActivityAlias>();
    private List<ManifestService> services = new ArrayList<ManifestService>();
    private List<ManifestReceiver> receivers = new ArrayList<ManifestReceiver>();
    private List<ManifestProvider> providers = new ArrayList<ManifestProvider>();
    private List<ManifestLibrary> libraries = new ArrayList<ManifestLibrary>();

    public ManifestApplication() {
        super(TAG);
    }

    public ManifestApplication(Element e, String packageName) {

        String appName = Utility.getQName(packageName, e.attributeValue("name"));
        setName(appName);

        for (Iterator appIter = e.elementIterator(ManifestActivity.TAG); appIter.hasNext(); ) {
            Element act = (Element) appIter.next();
            String actName = Utility.getQName(packageName, act.attributeValue("name"));
            ManifestActivity mActivity = new ManifestActivity(actName);

            for (Iterator inIter = act.elementIterator(ManifestIntentFilter.TAG); inIter.hasNext(); ) {
                Element filter = (Element) inIter.next();
                String action = filter.element("action") == null ? null : filter.element("action").attributeValue("name");
                String category = filter.elementText("category") == null ? null : filter.element("category").attributeValue("name");
                String data = filter.element("data") == null ? null : filter.element("data").attributeValue("name");

                mActivity.addIntentFilter(new ManifestIntentFilter(action, category, data));

            }

            addActivity(mActivity);
        }
        for (Iterator appIter = e.elementIterator(ManifestActivityAlias.TAG); appIter.hasNext(); ) {
            Element act = (Element) appIter.next();
//                        FIXME may be wrong
            String actName = Utility.getQName(packageName, act.attributeValue("name"));
            ManifestActivityAlias mActivity = new ManifestActivityAlias(actName);

            for (Iterator inIter = act.elementIterator(ManifestIntentFilter.TAG); inIter.hasNext(); ) {
                Element filter = (Element) inIter.next();
                ManifestIntentFilter mif = new ManifestIntentFilter(filter);
                mActivity.addIntentFilter(mif);
            }

            addActivity(mActivity);
        }
        for (Iterator appIter = e.elementIterator(ManifestService.TAG); appIter.hasNext(); ) {
            Element service = (Element) appIter.next();
            String serviceName = Utility.getQName(packageName, service.attributeValue("name"));
            ManifestService mService = new ManifestService(serviceName);

            for (Iterator inIter = service.elementIterator(ManifestIntentFilter.TAG); inIter.hasNext(); ) {
                Element ifElement = (Element) inIter.next();
                ManifestIntentFilter intentFilter = new ManifestIntentFilter(ifElement);
                mService.addIntentFilter(intentFilter);
            }

            addService(mService);
        }
        for (Iterator appIter = e.elementIterator(ManifestProvider.TAG); appIter.hasNext(); ) {

            Element provider = (Element) appIter.next();
            String providerName = Utility.getQName(packageName, provider.attributeValue("name"));
            ManifestProvider mProvider = new ManifestProvider(providerName);

            String grantUriPermission = provider.elementText("grant-uri-permission");
            String metaData = provider.elementText("meta-data");
            String pathPermission = provider.elementText("path-permission");

            mProvider.setGrantUriPermission(grantUriPermission);
            mProvider.setMetaData(metaData);
            mProvider.setPathPermission(pathPermission);

            addProvider(mProvider);

        }
        for (Iterator appIter = e.elementIterator(ManifestReceiver.TAG); appIter.hasNext(); ) {

            Element receiver = (Element) appIter.next();
            String receiverName = Utility.getQName(packageName, receiver.attributeValue("name"));
            ManifestReceiver mReceiver = new ManifestReceiver(receiverName);

            for (Iterator inIter = receiver.elementIterator(ManifestIntentFilter.TAG); inIter.hasNext(); ) {
                Element filter = (Element) inIter.next();
                ManifestIntentFilter intentFilter = new ManifestIntentFilter(filter);
                mReceiver.addIntentFilter(intentFilter);
            }

            addReceiver(mReceiver);
        }

        for (Iterator fetIter = e.elementIterator(ManifestLibrary.TAG); fetIter.hasNext(); ) {
            Element feature = (Element) fetIter.next();
            addLibrary(new ManifestLibrary(feature.attributeValue("name")));
        }
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

    @SuppressWarnings({"StringBufferReplaceableByString", "StringConcatenationInsideStringBufferAppend"})
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
