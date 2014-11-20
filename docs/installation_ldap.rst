Installation of LDAP
====================

We decided to use Vagrant, as it easens up the installation of OpenLDAP a lot.
First, change to the directory where the container should be downloaded, then
do a known git clone:

.. code:: bash

    cd vagrant
    git clone https://github.com/cforcey/vagrant_ubuntu_openldap.git


The above was only done once.
Now, OpenLDAP can simply be started by typing:


.. code:: bash

    cd vagrant/
    vagrant up


This starts a Vagrant container containing OpenLDAP, which listens on port 3890.