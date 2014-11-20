package jndiscover;

import jndiscover.ops.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class Main {
    public static void main(String[] args) {
        Map <String, Method> entryPoints = new HashMap<String, Method>();
        try {
            entryPoints.put("lookup", Lookup.class.getMethod("main", String[].class));
            entryPoints.put("list", List.class.getMethod("main", String[].class));
            entryPoints.put("bind", Bind.class.getMethod("main", String[].class));
            entryPoints.put("rebind", Rebind.class.getMethod("main", String[].class));
            entryPoints.put("unbind", Unbind.class.getMethod("main", String[].class));
            entryPoints.put("rename", Rename.class.getMethod("main", String[].class));
            entryPoints.put("create", Create.class.getMethod("main", String[].class));
            entryPoints.put("destroy", Destroy.class.getMethod("main", String[].class));
            entryPoints.put("get_all_attributes", GetAllAttrs.class.getMethod("main", String[].class));
            entryPoints.put("mod_attributes", ModAttrs.class.getMethod("main", String[].class));
            entryPoints.put("bind_attributes", BindAttrs.class.getMethod("main", String[].class));
            entryPoints.put("rebind_attributes", RebindAttrs.class.getMethod("main", String[].class));
            entryPoints.put("search", Search.class.getMethod("main", String[].class));
            entryPoints.put("search_with_filter_ret_all", SearchWithFilterRetAll.class.getMethod("main", String[].class));
            entryPoints.put("search_subtree", SearchSubtree.class.getMethod("main", String[].class));
            entryPoints.put("search_count_limit", SearchCountLimit.class.getMethod("main", String[].class));
            entryPoints.put("search_time_limit", SearchTimeLimit.class.getMethod("main", String[].class));
        } catch (NoSuchMethodException nsme) {
            System.err.println("Could not bind to some method.");
            System.err.print(nsme.getMessage());
        }

        Map <String, String> descriptions = new HashMap<String, String>() {{
            put("lookup", "Lookup an Object");
            put("list", "List the Context");
            put("bind", "Add a Binding");
            put("rebind", "Replace a Binding");
            put("unbind", "Remove a Binding");
            put("rename", "Rename");
            put("create", "Create a Subcontext");
            put("destroy", "Destroy a Subcontext");
            put("get_all_attributes", "Read Attributes");
            put("mod_attributes", "Modify Attributes");
            put("bind_attributes", "Add a binding with Attributes");
            put("rebind_attributes", "Replace a binding with Attributes");
            put("search", "Search - Basic");
            put("search_with_filter_ret_all", "Search - Filters");
            put("search_subtree", "Search - Scope");
            put("search_count_limit", "Search - Result count");
            put("search_time_limit", "Search - Time limit");
        }};

        if (args.length != 0) {
            String command = args[0];
            if(entryPoints.containsKey(command)) {
                Method entryPoint = entryPoints.get(command);

                try {
                    entryPoint.invoke(null);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }

                System.exit(0);
            } else {
                System.out.println("Command: \"" + command + "\" not found.");
            }
        }
        help(descriptions);
    }

    public static void help(Map <String, String> descriptions) {
        System.out.println("========");
        System.out.println("Commands");
        System.out.println("========");
        System.out.println();
        System.out.println("Command\tDescription");
        System.out.println();
        for(Map.Entry<String,String> entry: descriptions.entrySet()) {
            System.out.println(entry.getKey());
            System.out.println("\t" + entry.getValue());
        }
    }
}
