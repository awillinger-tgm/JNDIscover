package jndiscover;

import jndiscover.ops.*;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class Main {
    public static void main(String[] args) {
        Map <String, Runnable> entryPoints = new HashMap<String, Runnable>() {{
            put("lookup", () -> Rebind.main(new String[]{}));
            put("list", () -> List.main(new String[]{}));
            put("bind", () -> Bind.main(new String[]{}));
            put("rebind", () -> Rebind.main(new String[]{}));
            put("unbind", () -> Unbind.main(new String[]{}));
            put("rename", () -> Rename.main(new String[]{}));
            put("create", () -> Create.main(new String[]{}));
            put("destroy", () -> Destroy.main(new String[]{}));
            put("get_all_attributes", () -> GetAllAttrs.main(new String[]{}));
            put("mod_attributes", () -> ModAttrs.main(new String[]{}));
            put("bind_attributes", () -> BindAttrs.main(new String[]{}));
            put("rebind_attributes", () -> RebindAttrs.main(new String[]{}));
            put("search", () -> Search.main(new String[]{}));
            put("search_with_filter_ret_all", () -> SearchWithFilterRetAll.main(new String[]{}));
            put("search_subtree", () -> SearchSubtree.main(new String[]{}));
            put("search_count_limit", () -> SearchCountLimit.main(new String[]{}));
            put("search_time_limit", () -> SearchTimeLimit.main(new String[]{}));
        }};

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

            if (args.length >= 2) {
                String hostAndPort = args[1];
                System.setProperty("ldap_server", hostAndPort);
            } else if(System.getProperty("ldap_server") == null) {
                System.setProperty("ldap_server", "localhost:389");
            }

            if(entryPoints.containsKey(command)) {
                Runnable entryPoint = entryPoints.get(command);

                entryPoint.run();

                System.exit(0);
            } else {
                System.out.println("Command: \"" + command + "\" not found.");
            }
        }
        help(descriptions);
    }

    public static void help(Map <String, String> descriptions) {
        System.out.println("Usage: java -jar <jarfile>.jar <command> [host:port]");
        System.out.println();
        System.out.println("Commands:");
        System.out.println();
        for(Map.Entry<String,String> entry: descriptions.entrySet()) {
            System.out.println(entry.getKey());
            System.out.println("\t" + entry.getValue());
        }
    }
}
