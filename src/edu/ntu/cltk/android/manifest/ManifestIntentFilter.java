package edu.ntu.cltk.android.manifest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.istack.internal.NotNull;

import edu.ntu.cltk.data.ArrayUtil;
import edu.ntu.cltk.data.ListUtil;
import edu.ntu.cltk.data.ListUtil.Transformation;
import edu.ntu.cltk.data.NullUtil;


public class ManifestIntentFilter extends ManifestElement {
	
	public final static String TAG = "intent-filter";
	
	private final String name = "intent-filter";
	protected List<Action> actions;
	protected List<Category> categories;
	protected Data data;
	
	class Action{
		final static String TAG = "action";
		private String name;
		
		Action(String name){
			this.name = name;
		}
		
		String getName(){
			return this.name;
		}
		
		void setName(String name){
			this.name = name;
		}
		
		@Override
		public boolean equals(Object object){
			return object instanceof Action && name.equals(((Action)object).name);
		}
		
	}
	
	class Category{
		public final static String TAG = "category";
		private String name;
		
		Category(String name){
			this.name = name;
		}
		
		String getName(){
			return this.name;
		}
		
		void setName(String name){
			this.name = name;
		}
		
		@Override
		public boolean equals(Object object){
			return object instanceof Category && name.equals(((Category)object).name);
		}
	}
	
	class Data{
		public final static String TAG = "data";
		private Map<String, String> attributes = new HashMap<String, String>();
		
		Data(Map<String, String> attributes){
			this.attributes = attributes;
		}
		
		String getProperty(@NotNull String key){
			return this.attributes.get(key);
		}
		
		void setProperty(String key, String val){
			this.attributes.put(key, val);
		}
		
		Map<String, String> getAttributes(){
			return this.attributes;
		}
	}
	
	/**
	 * Initialize a ManifestIntentFilter object with action, category and data
	 * @param action
	 * @param category
	 * @param data
	 */
	public ManifestIntentFilter(List<String> actions, List<String> categories, Map<String, String> data){
		this.actions = new ArrayList<Action>();
		if (actions != null){
			for (String action : actions){
				this.actions.add(new Action(action));
			}
		}
		
		this.categories = new ArrayList<Category>();
		if (categories != null){
			for (String category : categories){
				this.categories.add(new Category(category));
			}
		}
		
		this.data = new Data(data);
	}
	
	@Override
	public String getName() {
		return name;
	}

	public List<String> getAllActions(){
		return ListUtil.transform(actions, new Transformation<Action, String>(){
			@Override
			public String apply(Action x) {
				return x.getName();
			}
		});
	}
	
	public List<String> getAllCategories(){
		return ListUtil.transform(categories, new Transformation<Category, String>(){
			@Override
			public String apply(Category x){
				return x.getName();
			}
		});
	}
	
	public Map<String, String> getData(){
		return data.attributes; 
	}
		
	@Override
	public boolean equals(Object obj){
		ManifestIntentFilter mif = (ManifestIntentFilter) obj;
		
		List<String> a1 = getAllActions();
		List<String> a2 = mif.getAllActions();
		
		if (NullUtil.countNullElement(a1, a2) == 1 || !ListUtil.equals(a1, a2))	return false;
		
		List<String> c1 = getAllCategories();
		List<String> c2 = mif.getAllCategories();
		
		if (NullUtil.countNullElement(c1, c2) == 1 || !ListUtil.equals(c1, c2))	return false;
		
		if (NullUtil.countNullElement(data, mif.getData()) == 1)	return false;
		if (NullUtil.countNullElement(data, mif.getData()) == 0 && !data.equals(mif.getData()))	return false;
		return true;
	}

	@Override
	public String toXml() {
		StringBuilder sb = new StringBuilder("<"+ManifestIntentFilter.TAG+">");
		if (this.getAllActions().size() > 0)
			sb.append(ArrayUtil.serializeArray(ListUtil.transform(this.getAllActions(), new Transformation<String, String>(){
				@Override
				public String apply(String x) {
					return String.format("<%s android:name=\"%s\" />", ManifestIntentFilter.Action.TAG, x);
				}
				
			}), ""));
		
		if (this.getAllCategories().size() > 0)
			sb.append(ArrayUtil.serializeArray(ListUtil.transform(this.getAllCategories(), new Transformation<String, String>(){
				@Override
				public String apply(String x) {
					return String.format("<%s android:name=\"%s\" />", ManifestIntentFilter.Category.TAG, x);
				}
				
			}), ""));
		
		if (this.getData() != null){
			sb.append("<"+ManifestIntentFilter.Data.TAG);
			for (String key : this.getData().keySet()){
				sb.append(" " + key + "="+this.getData().get(key));
			}			
			sb.append("/>");
		}
		sb.append("</"+ManifestIntentFilter.TAG+">");
		return sb.toString();
	}

}
