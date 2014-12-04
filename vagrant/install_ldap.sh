debconf-set-selections /vagrant/slapd.seed
apt-get -y update
apt-get -y install slapd ldap-utils libldap-2.4-2 libdb4.6
