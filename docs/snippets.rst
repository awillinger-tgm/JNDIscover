Code snippets and results
=========================

Every one of these snippets prescents the following snippet to initialize the
context:

.. code:: java

    // Set up the environment for creating the initial context
    Hashtable<String, Object> env = new Hashtable<String, Object>(11);
    env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
    env.put(Context.PROVIDER_URL, "ldap://localhost:3890/o=JNDIscover");
    
    // Create the initial context
    Context ctx = new InitialContext(env);


And should be ended with:

.. code:: java

    ctx.close();

This can be placed in a finally block, for example.

To run one of 

java -jar ?.jar 

Lookup an Object
~~~~~~~~~~~~~~~~

.. code:: java

    // Perform lookup and cast to target type
    LdapContext b = (LdapContext) ctx.lookup("cn=Rosanna Lee,ou=People");
    System.out.println(b);

Found in jndiscover/Lookup.java

List the Context
~~~~~~~~~~~~~~~~

.. code:: java

    // Get listing of context
    NamingEnumeration list = ctx.list("ou=People");

    // Go through each item in list
    while (list.hasMore()) {
        NameClassPair nc = (NameClassPair)list.next();
        System.out.println(nc);
    }


Found in jndiscover/List.java

Add a Binding
~~~~~~~~~~~~~

.. code:: java

    // Create the object to be bound
    Fruit fruit = new Fruit("orange");

    // Perform the bind
    ctx.bind("cn=Favorite Fruit", fruit);

    // Check that it is bound
    Object obj = ctx.lookup("cn=Favorite Fruit");
    System.out.println(obj);


Found in jndiscover/Bind.java

Replace a Binding
~~~~~~~~~~~~~~~~~

also called rebinding.

.. code:: java

    // Create the object to be bound
    Fruit fruit = new Fruit("lemon");
    
    // Perform the bind
    ctx.rebind("cn=Favorite Fruit", fruit);

    // Check that it is bound
    Object obj = ctx.lookup("cn=Favorite Fruit");
    System.out.println(obj);


Found in jndiscover/Rebind.java

Remove a Binding
~~~~~~~~~~~~~~~~

.. code:: java

    // Remove the binding
    ctx.unbind("cn=Favorite Fruit");

    // Check that it is gone
    Object obj = null;
    
    try {
        obj = ctx.lookup("cn=Favorite Fruit");
    } catch (NameNotFoundException ne) {
        System.out.println("unbind successful");
        return;
    }

    System.out.println("unbind failed; object still there: " + obj);


Found in jndiscover/Unbind.java

Rename
~~~~~~

.. code:: java

    // Rename to Scott J
    ctx.rename("cn=Scott Jones", "cn=Scott J");
    
    // Check that it is there using new name
    Object obj = ctx.lookup("cn=Scott J");
    System.out.println(obj);
    
    // Rename back to Scott Jones
    ctx.rename("cn=Scott J", "cn=Scott Jones");
    
    // Check that it is there with original name
    obj = ctx.lookup("cn=Scott Jones");
    System.out.println(obj);


Found in jndiscover/Rename.java

Create a Subcontext
~~~~~~~~~~~~~~~~~~~

.. code:: java

    // Create attributes to be associated with the new context
    Attributes attrs = new BasicAttributes(true); // case-ignore
    Attribute objclass = new BasicAttribute("objectclass");
    objclass.add("top");
    objclass.add("organizationalUnit");
    attrs.put(objclass);
    
    // Create the context
    Context result = ctx.createSubcontext("ou=NewOu", attrs);
    
    // Check that it was created by listing its parent
    NamingEnumeration list = ctx.list("");
    
    // Go through each item in list
    while (list.hasMore()) {
        NameClassPair nc = (NameClassPair)list.next();
        System.out.println(nc);
    }
    
    // Close the contexts when we're done
    result.close();


Found in jndiscover/Create.java

Destroy a Subcontext
~~~~~~~~~~~~~~~~~~~~

.. code:: java

    // Destroy the context
    ctx.destroySubcontext("ou=NewOu");
    
    // Check that it has been destroyed by listing its parent
    NamingEnumeration list = ctx.list("");
    
    // Go through each item in list
    while (list.hasMore()) {
        NameClassPair nc = (NameClassPair)list.next();
        System.out.println(nc);
    }


Found in jndiscover/Destroy.java

Attribute names
~~~~~~~~~~~~~~~

See other sections, for example Lookup an Object.

Read Attributes
~~~~~~~~~~~~~~~

.. code:: java

    // Get all the attributes of named object
    Attributes attrs = ctx.getAttributes("cn=Ted Geisel, ou=People");
    
    for (NamingEnumeration ae = attrs.getAll(); ae.hasMore();) {
        Attribute attr = (Attribute)ae.next();
        System.out.println("attribute: " + attr.getID());
        
        /* print each value */
        for (NamingEnumeration e = attr.getAll();
            e.hasMore();
            System.out.println("value: " + e.next()));
    }


Found in jndiscover/GetAllAttrs.java

Modify Attributes
~~~~~~~~~~~~~~~~~

.. code:: java

    String name = "cn=Ted Geisel, ou=People";
    
    // Specify the changes to make
    ModificationItem[] mods = new ModificationItem[3];
    
    // Replace the "mail" attribute with a new value
    mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE,
    new BasicAttribute("mail", "geisel@wizards.com"));
    
    // Add additional value to "telephonenumber"
    mods[1] = new ModificationItem(DirContext.ADD_ATTRIBUTE,
    new BasicAttribute("telephonenumber", "+1 555 555 5555"));
    
    // Remove the "jpegphoto" attribute
    mods[2] = new ModificationItem(DirContext.REMOVE_ATTRIBUTE,
    new BasicAttribute("jpegphoto"));
    
    // Perform the requested modifications on the named object
    ctx.modifyAttributes(name, mods);


Found in jndiscover/ModAttrs.java

Add a binding with Attributes
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

The snippet from the Add a binding section will be expanded:

.. code:: java

    [..]
    // Create attributes to be associated with the object
    Attributes attrs = new BasicAttributes(true); // case-ignore
    Attribute objclass = new BasicAttribute("objectclass");
    objclass.add("top");
    objclass.add("organizationalUnit");
    attrs.put(objclass);


And the bind() call will be expanded with the attrs object:

.. code:: java

    // Perform bind
    ctx.bind("ou=favorite, ou=Fruits", fruit, attrs);

Found in jndiscover/BindAttrs.java

Replace a binding with Attributes
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Similar to the above.

.. code:: java

    [..]
    // Create the object to be bound
    Fruit fruit = new Fruit("lemon");
    
    // Create attributes to be associated with the object
    Attributes attrs = new BasicAttributes(true); // case-ignore
    Attribute objclass = new BasicAttribute("objectclass");
    objclass.add("top");
    objclass.add("organizationalUnit");
    attrs.put(objclass);

And the rebinding requires an unbind of the "old" bind first.

.. code:: java

    // Perform bind
    ctx.rebind("ou=favorite, ou=Fruits", fruit, attrs);

Found in jndiscover/RebindAttrs.java

Search - Basic
~~~~~~~~~~~~~~

.. code:: java

    // Specify the ids of the attributes to return
    String[] attrIDs = {"sn", "telephonenumber", "golfhandicap", "mail"};
    
    // Specify the attributes to match
    // Ask for objects that have the attribute 
    // sn == Smith and the "mail" attribute.
    Attributes matchAttrs = new BasicAttributes(true); // ignore case
    matchAttrs.put(new BasicAttribute("sn", "Smith"));
    matchAttrs.put(new BasicAttribute("mail"));
    
    // Search for objects that have those matching attributes
    NamingEnumeration answer = ctx.search("ou=People", matchAttrs, attrIDs);
    
    // Print the answer
    while (answer.hasMore()) {
        SearchResult sr = (SearchResult) answer.next();
        System.out.println(">>>" + sr.getName());
        GetAllAttrs.printAttrs(sr.getAttributes());
    }


Found in jndiscover/Search.java

Search - Filters
~~~~~~~~~~~~~~~~

.. code:: java

    // Create default search controls
    SearchControls ctls = new SearchControls();
    
    // Specify the search filter to match
    // Ask for objects with attribute sn == Smith and which have
    // the "mail" attribute.
    String filter = "(&(sn=Smith)(mail=*))";
    
    // Search for objects using filter
    NamingEnumeration answer = ctx.search("ou=People", filter, ctls);
    
    // Print the answer
    while (answer.hasMore()) {
        SearchResult sr = (SearchResult) answer.next();
        System.out.println(">>>" + sr.getName());
        GetAllAttrs.printAttrs(sr.getAttributes());
    }


Found in jndiscover/SearchWithFilterRetAll.java

Search - Scope
~~~~~~~~~~~~~~

We are doing a Subtree searc here.

.. code:: java

    // Specify the ids of the attributes to return
    String[] attrIDs = {"sn", "telephonenumber", "golfhandicap", "mail"};
    SearchControls ctls = new SearchControls();
    ctls.setReturningAttributes(attrIDs);
    ctls.setSearchScope(SearchControls.SUBTREE_SCOPE);
    
    // Specify the search filter to match
    // Ask for objects with attribute sn == Smith and which have
    // the "mail" attribute.
    String filter = "(&(sn=Smith)(mail=*))";
    
    // Search subtree for objects using filter
    NamingEnumeration answer = ctx.search("", filter, ctls);
    
    // Print the answer
    while (answer.hasMore()) {
        SearchResult sr = (SearchResult) answer.next();
        System.out.println(">>>" + sr.getName());
        GetAllAttrs.printAttrs(sr.getAttributes());
    }


Found in jndiscover/SearchSubtree.java

Search - Result count
~~~~~~~~~~~~~~~~~~~~~

.. code:: java

    // Set the search controls to limit the count to 1
    SearchControls ctls = new SearchControls();
    ctls.setCountLimit(1);

Found in jndiscover/SearchCountLimit.java

Search - Time limit
~~~~~~~~~~~~~~~~~~~

.. code:: java

    // Set the search controls to limit the time to 1 second (1000 ms)
    SearchControls ctls = new SearchControls();
    ctls.setTimeLimit(1000);

Found in jndiscover/SearchTimeLimit.java