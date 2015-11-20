package edu.ntu.cltk.android.manifest;


import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.dom4j.Attribute;
import org.dom4j.Element;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManifestIntentFilter extends ManifestElement {

    class Data {
        private final String PREFIX = "android:";
        private Map<String, String> attributeMap = new HashMap<String, String>();

        Data(Element e) {
            for (Object object : e.attributes()) {
                Attribute attr = (Attribute) object;
                attributeMap.put(attr.getName(), attr.getValue());
            }
        }

        String getProperty(String prop) {
            if (!prop.startsWith(PREFIX)) {
                prop = PREFIX + prop;
            }
            return attributeMap.get(prop);
        }
    }

    class Action {
        private String name;

        Action(Element e) {
            name = Utility.getAttributeValue(e ,"android:name");
        }
    }

    class Category {
        private String name;

        Category(Element e) {
            name = Utility.getAttributeValue(e ,"android:category");
        }
    }

    //

    public final static String TAG = "intent-filter";

    private List<Action> actions;
    private List<Category> categories;
    private List<Data> dataList;


    public boolean containsAction(final String action) {
        return Iterables.any(actions, new Predicate<Action>() {
            @Override
            public boolean apply(Action input) {
                return input.name.equalsIgnoreCase(action);
            }
        });
    }

    public List<String> getAllActions() {
        Function<Action, String> itToAction = new Function<Action, String>() {
            @Override
            public String apply(Action input) {
                return input.name;
            }
        };
        return Lists.transform(actions, itToAction);
    }

    public ManifestIntentFilter(Element intentFilter) {
    }

}
