package edu.ntu.cltk.android.manifest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import edu.ntu.cltk.android.manifest.ManifestActivity;
import edu.ntu.cltk.android.manifest.ManifestActivityAlias;
import edu.ntu.cltk.android.manifest.ManifestApplication;
import edu.ntu.cltk.android.manifest.ManifestIntentFilter;
import edu.ntu.cltk.android.manifest.ManifestLibrary;
import edu.ntu.cltk.android.manifest.ManifestPermission;
import edu.ntu.cltk.android.manifest.ManifestProvider;
import edu.ntu.cltk.android.manifest.ManifestReceiver;
import edu.ntu.cltk.android.manifest.ManifestService;
import edu.ntu.cltk.android.manifest.ManifestUsesFeature;
import edu.ntu.cltk.android.manifest.ManifestUsesPermission;
import edu.ntu.cltk.data.ArrayUtil;



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

	public ManifestManager(final String filePath) throws FileNotFoundException{
		this.manifestFile = filePath;
		this.ma = new ManifestApplication();
		this.is = new FileInputStream(manifestFile);
		parse(is);
	}
	
	public ManifestManager(final InputStream is){
		this.is = is;
		this.ma = new ManifestApplication();
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
			
			for (Iterator iter = element.elementIterator(); iter.hasNext();){
				Element e = (Element) iter.next();
				if (e.getName().equals(ManifestUsesPermission.TAG)){
					this.usesPermissions.add(new ManifestUsesPermission(e.attributeValue("name")));
				}else if (e.getName().equals(ManifestPermission.TAG)){
					this.permissions.add(new ManifestPermission(e.attributeValue("name")));
				}else if (e.getName().equals(ManifestApplication.TAG)){
					
					String appName = e.attributeValue("name");
					if (appName != null && appName.startsWith(".") && this.packageName != null){
						appName = this.packageName + appName;
					}
					ma.setName(appName);
					
					for (Iterator appIter = e.elementIterator(ManifestActivity.TAG); appIter.hasNext();){
						Element act = (Element) appIter.next();
						ManifestActivity mActivity = new ManifestActivity();
						String actName = act.attributeValue("name");
						//Change it to the full-qualified name
						if (actName != null && actName.startsWith(".") && this.packageName != null){
							actName = this.packageName + actName;
						}
						mActivity.setName(actName);
						
						for (Iterator inIter = act.elementIterator(ManifestIntentFilter.TAG); inIter.hasNext();){
							Element filter = (Element) inIter.next();
							String action = filter.element("action")==null?null:filter.element("action").attributeValue("name");
							String category = filter.elementText("category")==null?null:filter.element("category").attributeValue("name");
							String data = filter.element("data")==null?null:filter.element("data").attributeValue("name");
							
							mActivity.addIntentFilter(new ManifestIntentFilter(action, category, data));
							
						}
						
						ma.addActivity(mActivity);
					}
					for (Iterator appIter = e.elementIterator(ManifestActivityAlias.TAG); appIter.hasNext();){
						Element act = (Element) appIter.next();
						ManifestActivityAlias mActivity = new ManifestActivityAlias();
						String actName = act.attributeValue("name");
						//Change it to the full-qualified name
						if (actName != null && actName.startsWith(".") && this.packageName != null){
							actName = this.packageName + actName;
						}
						mActivity.setName(actName);
						
						for (Iterator inIter = act.elementIterator(ManifestIntentFilter.TAG); inIter.hasNext();){
							Element filter = (Element) inIter.next();
							String action = filter.elementText("action");
							String category = filter.elementText("category");
							String data = filter.elementText("data");
							
							mActivity.addIntentFilter(new ManifestIntentFilter(action, category, data));
							
						}
						
						ma.addActivity(mActivity);
					}
					for (Iterator appIter = e.elementIterator(ManifestService.TAG); appIter.hasNext();){
						Element service = (Element) appIter.next();
						ManifestService mService = new ManifestService();
						String serviceName = service.attributeValue("name");
						//Change it to the full-qualified name
						if (serviceName != null && serviceName.startsWith(".") && this.packageName != null){
							serviceName = this.packageName + serviceName;
						}
						mService.setName(serviceName);
						
						for (Iterator inIter = service.elementIterator(ManifestIntentFilter.TAG); inIter.hasNext();){
							Element filter = (Element) inIter.next();
							String action = filter.elementText("action");
							String category = filter.elementText("category");
							String data = filter.elementText("data");
							
							mService.addIntentFilter(new ManifestIntentFilter(action, category, data));
						}
						
						ma.addService(mService);
					}
					for (Iterator appIter = e.elementIterator(ManifestProvider.TAG); appIter.hasNext();){
						
						Element provider = (Element) appIter.next();
						ManifestProvider mProvider = new ManifestProvider();
						String providerName = provider.attributeValue("name");
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
					for (Iterator appIter = e.elementIterator(ManifestReceiver.TAG); appIter.hasNext();){
						
						Element receiver = (Element) appIter.next();
						ManifestReceiver mReceiver = new ManifestReceiver();
						String receiverName = receiver.attributeValue("name");
						//Change it to the full-qualified name
						if (receiverName != null && receiverName.startsWith(".") && this.packageName != null){
							receiverName = this.packageName + receiverName;
						}
						mReceiver.setName(receiverName);
						
						for (Iterator inIter = receiver.elementIterator(ManifestIntentFilter.TAG); inIter.hasNext();){
							Element filter = (Element) inIter.next();
							String action = filter.elementText("action");
							String category = filter.elementText("category");
							String data = filter.elementText("data");
							
							mReceiver.addIntentFilter(new ManifestIntentFilter(action, category, data));
						}
						
						ma.addReceiver(mReceiver);
					}
					
					for (Iterator fetIter = e.elementIterator(ManifestLibrary.TAG); fetIter.hasNext();){
						Element feature = (Element) fetIter.next();
						ma.addLibrary(new ManifestLibrary(feature.attributeValue("name")));
					}
				}else if (e.getName().equals(ManifestUsesFeature.TAG)){
					this.usesFeatures.add(new ManifestUsesFeature(e.attributeValue("name")));
				}
			}
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static InputStream getInputStreamFromApk(String apkFile){
		
		File apkF = new File(apkFile);

		if (!apkF.exists())
			throw new RuntimeException("file '" + apkFile + "' does not exist!");

		// get AndroidManifest
		InputStream manifestIS = null;
		ZipFile archive = null;
		try {
			archive = new ZipFile(apkF);
			for (@SuppressWarnings("rawtypes") Enumeration entries = archive.entries(); entries.hasMoreElements();) {
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
			if (archive != null)
				try {
					archive.close();
				} catch (IOException e) {
					throw new RuntimeException("Error when looking for manifest in apk: " + e);
				}
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
