package edu.ntu.cltk.android.manifest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.QName;
import org.dom4j.io.SAXReader;

import edu.ntu.cltk.data.ArrayUtil;
import edu.ntu.cltk.exception.Assertions;



/**
 * It's a diligent class, which will parse the manifest file immediately after knowing its path.
 * Usage:
 *    1. Create a new object of ManifestManager
 *    2. Get the elements in the Mannifest file
 *       - Permission
 *       - Activity
 *       - ...
 * Data Structure:
 * 
 * ManifestManager
 *    - manifestFile:  the location of manifest file
 *    - ma: Manifest application
 *      -- List of permissions
 *      -- List of activities
 *      -- List of services
 *      -- ...
 *    
 * @author pillar
 *
 */
public class ManifestManager {

	private String manifestFile;
	private InputStream is;
	private ManifestApplication ma;
	
	public static final String ANDROID_NAMESPACE = "http://schemas.android.com/apk/res/android";

	public ManifestManager(final String filePath) throws FileNotFoundException{
		this.manifestFile = filePath;
		this.ma = new ManifestApplication();
		this.is = new FileInputStream(manifestFile);
		parse(is);
	}
	
	public ManifestManager(final InputStream manifestIS){
		this.ma = new ManifestApplication();
		this.is = manifestIS;
		parse(is);
	}
	
	
	public String getManifestFile() {
		return manifestFile;
	}

	public void setManifestFile(String manifestFile) {
		this.manifestFile = manifestFile;
	}

	public ManifestApplication getMa() {
		return ma;
	}

	public void setMa(ManifestApplication ma) {
		this.ma = ma;
	}

	public List<ManifestPermission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<ManifestPermission> permissions) {
		this.permissions = permissions;
	}

	public List<ManifestUsesPermission> getUsesPermissions() {
		return usesPermissions;
	}

	public void setUsesPermissions(List<ManifestUsesPermission> usesPermissions) {
		this.usesPermissions = usesPermissions;
	}

	public List<ManifestUsesFeature> getUsesFeatures() {
		return usesFeatures;
	}

	public void setUsesFeatures(List<ManifestUsesFeature> usesFeatures) {
		this.usesFeatures = usesFeatures;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	private List<ManifestPermission> permissions = new ArrayList<ManifestPermission>();
	private List<ManifestUsesPermission> usesPermissions = new ArrayList<ManifestUsesPermission>();	
	private List<ManifestUsesFeature> usesFeatures = new ArrayList<ManifestUsesFeature>();
	private String packageName;
	private String versionCode;
	private String versionName;
	
	private void parse(InputStream is){
		SAXReader reader = new SAXReader();
		try {
			Document document = reader.read(is);
			Element element = document.getRootElement();
			this.packageName = element.attributeValue("package");
			this.versionCode = element.attributeValue("versionCode");
			this.versionName = element.attributeValue("versionName");
			
			for (Iterator<Element> iter = element.elementIterator(); iter.hasNext();){
				Element e = iter.next();
				if (e.getName().equals(ManifestUsesPermission.TAG)){
					this.usesPermissions.add(new ManifestUsesPermission(getAttributeValue(e, "android:name", null)));
				}else if (e.getName().equals(ManifestPermission.TAG)){
					this.permissions.add(new ManifestPermission(getAttributeValue(e, "android:name", null)));
				}else if (e.getName().equals(ManifestApplication.TAG)){
					
					String appName = getAttributeValue(e, "android:name", null);
					if (appName != null && appName.startsWith(".") && this.packageName != null){
						appName = this.packageName + appName;
					}
					ma.setName(appName);
					
					for (Iterator<Element> appIter = e.elementIterator(ManifestActivity.TAG); appIter.hasNext();){
						Element act = appIter.next();
						ManifestActivity mActivity = new ManifestActivity();
						String actName = getAttributeValue(act, "android:name", null);
						//Change it to the full-qualified name
						if (actName != null && actName.startsWith(".") && this.packageName != null){
							actName = this.packageName + actName;
						}
						mActivity.setName(actName);
						
						mActivity.addAllIntentFilters(parseIntentFilter(act));
						
						ma.addActivity(mActivity);
					}
					for (Iterator<Element> appIter = e.elementIterator(ManifestActivityAlias.TAG); appIter.hasNext();){
						Element act = appIter.next();
						ManifestActivityAlias mActivity = new ManifestActivityAlias();
						String actName = getAttributeValue(act, "android:name", null);
						//Change it to the full-qualified name
						if (actName != null && actName.startsWith(".") && this.packageName != null){
							actName = this.packageName + actName;
						}
						mActivity.setName(actName);
						
						mActivity.addAllIntentFilters(parseIntentFilter(act));
						
						ma.addActivity(mActivity);
					}
					for (Iterator<Element> appIter = e.elementIterator(ManifestService.TAG); appIter.hasNext();){
						Element service = appIter.next();
						ManifestService mService = new ManifestService();
						String serviceName = getAttributeValue(service, "android:name", null);
						//Change it to the full-qualified name
						if (serviceName != null && serviceName.startsWith(".") && this.packageName != null){
							serviceName = this.packageName + serviceName;
						}
						mService.setName(serviceName);
						
						mService.addAllIntentFilters(parseIntentFilter(service));
						
						ma.addService(mService);
					}
					for (Iterator<Element> appIter = e.elementIterator(ManifestProvider.TAG); appIter.hasNext();){
						
						Element provider = appIter.next();
						ManifestProvider mProvider = new ManifestProvider();
						String providerName = getAttributeValue(provider, "android:name", null);
						//Change it to the full=qualified name
						if (providerName != null && providerName.startsWith(".") && this.packageName != null){
							providerName = this.packageName + providerName;
						}
						mProvider.setName(providerName);
						
						String grantUriPermission = provider.elementText("grant-uri-permission");
						String metaData = provider.elementText("meta-data");
						String pathPermission = provider.elementText("path-permission");
						
						mProvider.setGrantUriPermission(grantUriPermission);
						mProvider.setMetaData(metaData);
						mProvider.setPathPermission(pathPermission);
						
						ma.addProvider(mProvider);		
					}
					for (Iterator<Element> appIter = e.elementIterator(ManifestReceiver.TAG); appIter.hasNext();){
						
						Element receiver = appIter.next();
						
						List<ManifestIntentFilter> intentFilters = parseIntentFilter(receiver);
						
						String receiverName = getAttributeValue(receiver, "android:name", null);
						String receiverExported = getAttributeValue(receiver, "android:exported", intentFilters.size()>0?"true":"false");
						String receiverEnabled = getAttributeValue(receiver, "android:enabled", "true");
						//Change it to the full-qualified name
						if (receiverName != null && receiverName.startsWith(".") && this.packageName != null){
							receiverName = this.packageName + receiverName;
						}
						

						ManifestReceiver mReceiver = new ManifestReceiver(receiverName, 
								Boolean.parseBoolean(receiverExported),
								Boolean.parseBoolean(receiverEnabled),
								intentFilters);
												
						ma.addReceiver(mReceiver);
					}
					
					for (Iterator<Element> fetIter = e.elementIterator(ManifestLibrary.TAG); fetIter.hasNext();){
						Element feature = fetIter.next();
						ma.addLibrary(new ManifestLibrary(getAttributeValue(feature, "android:name", null)));
					}
				}else if (e.getName().equals(ManifestUsesFeature.TAG)){
					this.usesFeatures.add(new ManifestUsesFeature(getAttributeValue(e, "android:name", null)));
				}
			}
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			System.err.println(e.getMessage());
		}
	}
	
	/**
	 * Get the attribute value for the given name
	 * @param element
	 * @param name
	 * @param defaultVal
	 * @return
	 */
	private String getAttributeValue(Element element, String name, String defaultVal){
		if (name.contains(":")){
			String[] arr = name.split(":");
			Assertions.checkArgument(arr.length == 2, "The name should be in the format of \"namespace:name\"");
			Namespace namespace = new Namespace(arr[0], ANDROID_NAMESPACE);
			QName qname = new QName(arr[1], namespace);
			return element.attributeValue(qname, defaultVal);
		}else{
			return element.attributeValue(name, defaultVal); 
		}
	}
	
	private List<ManifestIntentFilter> parseIntentFilter(Element e){
		
		List<ManifestIntentFilter> intents = new ArrayList<ManifestIntentFilter>();
		
		for (Iterator<Element> inIter = e.elementIterator(ManifestIntentFilter.TAG); inIter.hasNext();){
			Element filter = inIter.next();
			/*String action = filter.element("action")==null?null:filter.element("action").attributeValue("name");
			String category = filter.elementText("category")==null?null:filter.element("category").attributeValue("name");
			String data = filter.element("data")==null?null:filter.element("data").attributeValue("name");*/
			
			List<String> actions = new ArrayList<String>();
			for (Object a : filter.elements(ManifestIntentFilter.Action.TAG)){
				String action = getAttributeValue((Element)a, "android:name", null);
				actions.add(action);
			}
			
			List<String> categories = new ArrayList<String>();
			for (Object c : filter.elements(ManifestIntentFilter.Category.TAG)){
				String category = getAttributeValue((Element)c, "android:name", null);
				categories.add(category);
			}
			
			Map<String, String> data = new HashMap<String, String>();
			for (Object d : filter.elements(ManifestIntentFilter.Data.TAG)){
				for (Iterator<Attribute> aIter = ((Element)d).attributeIterator(); aIter.hasNext(); ){
					Attribute attr = aIter.next();
					String name = attr.getQualifiedName()/*+"|"+attr.getQName()+"|"+attr.getName()*/;
					String value = attr.getStringValue()/*+"|"+attr.getText()+"|"+attr.getValue()*/;
					data.put(name, value);
				}
			}
			
			intents.add(new ManifestIntentFilter(actions, categories, data));
			
		}
		
		return intents;
	}
	
	public static InputStream getAndroidManifestFromApk(String apkFile){
		
		File apkF = new File(apkFile);

		if (!apkF.exists())
			throw new RuntimeException("file '" + apkFile + "' does not exist!");

		// get AndroidManifest
		InputStream manifestIS = null;
		ZipFile archive = null;
		try {
			archive = new ZipFile(apkF);
			for (Enumeration entries = archive.entries(); entries.hasMoreElements();) {
				ZipEntry entry = (ZipEntry) entries.nextElement();
				String entryName = entry.getName();
				// We are dealing with the Android manifest
				if (entryName.equals("AndroidManifest.xml")) {
					manifestIS = archive.getInputStream(entry);
					break;
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("Error when looking for manifest in apk: " + e);
		} finally {
			/*if (archive != null)
				try {
					archive.close();
				} catch (IOException e) {
					throw new RuntimeException("Error when looking for manifest in apk: " + e);
				}*/
		}
		
		return manifestIS;
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("package: " + this.packageName + "\n");
		sb.append("version: " + this.versionCode + "\n");
		sb.append("uses-permission: " + ArrayUtil.serializeArray(usesPermissions, ", ")+"\n");
		sb.append("permission: " + ArrayUtil.serializeArray(permissions, ", ") + "\n");
		sb.append("uses-feature: " + ArrayUtil.serializeArray(usesFeatures, ", ") + "\n");
		sb.append("application: " + ma.toString() + "\n");
		return sb.toString();
	}
	
	
	/*public List<String> filterActivities(IntentModel im){
		
		List<String> receptors = new ArrayList<String>();
		
		List<ManifestActivity> activities = this.getMa().getActivities();
		for (ManifestActivity activity : activities){
			if (im.getClazz().contains(activity.getName()) || ArrayUtil.overlap(im.getActions(), activity.getAllActions())){
				receptors.add(activity.getName());
			}
		}
		return receptors;
	}
	
	public List<String> filterServices(IntentModel im){

		List<String> receptors = new ArrayList<String>();
		
		List<ManifestService> services = this.getMa().getServices();
		for (ManifestService service : services){
			if (im.getClazz().contains(service.getName()) || ArrayUtil.overlap(im.getActions(), service.getAllActions())){
				receptors.add(service.getName());
			}
		}
		return receptors;
	}
	
	public List<String> filterReceiver(IntentModel im){

		List<String> receptors = new ArrayList<String>();
		
		List<ManifestReceiver> receivers = this.getMa().getReceivers();
		for (ManifestReceiver receiver : receivers){
			if (im.getClazz().contains(receiver.getName()) || ArrayUtil.overlap(im.getActions(), receiver.getAllActions())){
				receptors.add(receiver.getName());
			}
		}
		return receptors;
	}*/
}
