Code snippets & results
=======================

Lookup an Object
~~~~~~~~~~~~~~~~

.. code:: java

    // Set up the environment for creating the initial context
    Hashtable<String, Object> env = new Hashtable<String, Object>(11);
    env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
    env.put(Context.PROVIDER_URL, "ldap://localhost:3890/o=JNDIscover");
    
    // Create the initial context
    Context ctx = new InitialContext(env);

    // Perform lookup and cast to target type
    LdapContext b = (LdapContext) ctx.lookup("cn=Rosanna Lee,ou=People");
    System.out.println(b);
        
    // Close the context when we're done
    ctx.close();

Found in ops/Lookup.java

List the Context
~~~~~~~~~~~~~~~~

.. code:: java

    // Set up the environment for creating the initial context
    Hashtable<String, Object> env = new Hashtable<String, Object>(11);
    env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
    env.put(Context.PROVIDER_URL, "ldap://localhost:3890/o=JNDIscover");

    // Create the initial context
    Context ctx = new InitialContext(env);

    // Get listing of context
    NamingEnumeration list = ctx.list("ou=People");

    // Go through each item in list
    while (list.hasMore()) {
        NameClassPair nc = (NameClassPair)list.next();
        System.out.println(nc);
    }

    // Close the context when we're done
    ctx.close();

Found in ops/List.java

Add a Binding
~~~~~~~~~~~~~

.. code:: bash

    Hashtable<String, Object> env = new Hashtable<String, Object>(11);
    env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
    env.put(Context.PROVIDER_URL, "ldap://localhost:3890/o=JNDIscover");
    
    // Create the initial context
    Context ctx = new InitialContext(env);
    
    // Create the object to be bound
    Fruit fruit = new Fruit("orange");

    // Perform the bind
    ctx.bind("cn=Favorite Fruit", fruit);

    // Check that it is bound
    Object obj = ctx.lookup("cn=Favorite Fruit");
    System.out.println(obj);

    // Close the context when we're done
    ctx.close();

Found in ops/Bind.java

Replace a Binding
~~~~~~~~~~~~~~~~~

also called rebinding.

.. code:: bash

    Hashtable<String, Object> env = new Hashtable<String, Object>(11);
	env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
	env.put(Context.PROVIDER_URL, "ldap://localhost:3890/o=JNDIscover");
    
	// Create the initial context
	Context ctx = new InitialContext(env);
    
	// Create the object to be bound
	Fruit fruit = new Fruit("lemon");
    
    // Perform the bind
	ctx.rebind("cn=Favorite Fruit", fruit);

	// Check that it is bound
	Object obj = ctx.lookup("cn=Favorite Fruit");
	System.out.println(obj);

	// Close the context when we're done
	ctx.close();

Found in ops/Rebind.java

Remove a Binding
~~~~~~~~~~~~~~~~

.. code:: java

    Hashtable<String, Object> env = new Hashtable<String, Object>(11);
	env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
	env.put(Context.PROVIDER_URL, "ldap://localhost:3890/o=JNDIscover");
    
	// Create the initial context
    Context ctx = new InitialContext(env);
    
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

	// Close the context when we're done
	ctx.close();

Found in ops/Unbind.java

Rename
~~~~~~

.. code:: java

    Hashtable<String, Object> env = new Hashtable<String, Object>(11);
	env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
	env.put(Context.PROVIDER_URL, "ldap://localhost:3890/ou=People,o=JNDIscover");
    
	// Create the initial context
	Context ctx = new InitialContext(env);
    
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
    
	// Close the context when we're done
	ctx.close();

Found in ops/Rename.java

Create a Subcontext
~~~~~~~~~~~~~~~~~~~

.. code:: java

    Hashtable<String, Object> env = new Hashtable<String, Object>(11);
	env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
	env.put(Context.PROVIDER_URL, "ldap://localhost:3890/o=JNDIscover");
    
	// Create the initial context
	DirContext ctx = new InitialDirContext(env);
    
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
    ctx.close();

Found in ops/Create.java

Destroy a Subcontext
~~~~~~~~~~~~~~~~~~~~

.. code:: java

    Hashtable<String, Object> env = new Hashtable<String, Object>(11);
    env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
	env.put(Context.PROVIDER_URL, "ldap://localhost:3890/o=JNDIscover");
    
	// Create the initial context
	Context ctx = new InitialContext(env);
    
	// Destroy the context
	ctx.destroySubcontext("ou=NewOu");
    
	// Check that it has been destroyed by listing its parent
	NamingEnumeration list = ctx.list("");
    
	// Go through each item in list
	while (list.hasMore()) {
		NameClassPair nc = (NameClassPair)list.next();
		System.out.println(nc);
	}
    
	// Close the context when we're done
	ctx.close();

Found in ops/Destroy.java

Attribute names
~~~~~~~~~~~~~~~

See other sections, for example Lookup an Object.

Read Attributes
~~~~~~~~~~~~~~~

.. code:: java

    Hashtable<String, Object> env = new Hashtable<String, Object>(11);
	env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
	env.put(Context.PROVIDER_URL, "ldap://localhost:3890/o=JNDIscover");
    
	// Create the initial context
    DirContext ctx = new InitialDirContext(env);
    
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
    
	// Close the context when we're done
	ctx.close();


Found in ops/GetAllAttrs.java

Modify Attributes
~~~~~~~~~~~~~~~~~

Please take a look into the ModAttrs.java, a code "snippet" would be too long
to list here.

Add a Binding with Attributes
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Replace a Binding with Attributes
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Search - Basic
~~~~~~~~~~~~~~

Search - Filters
~~~~~~~~~~~~~~~~

Search - Scope
~~~~~~~~~~~~~~

Search - Result count
~~~~~~~~~~~~~~~~~~~~~

Search - Time limit
~~~~~~~~~~~~~~~~~~~