echo "============="
echo "loglevel ldif"
echo "============="
ldapmodify -Y EXTERNAL -H ldapi:/// -f /vagrant/loglevel.ldif

echo "============="
echo "memberof ldif"
echo "============="
ldapadd -Y EXTERNAL -H ldapi:/// -f /vagrant/memberof.ldif 

echo "==========="
echo "refint ldif"
echo "==========="
ldapadd -Y EXTERNAL -H ldapi:/// -f /vagrant/refint.ldif

echo "========="
echo "java ldif"
echo "========="
ldapadd -Y EXTERNAL -H ldapi:/// -f /etc/ldap/schema/java.ldif

echo "============="
echo "tutorial ldif"
echo "============="
ldapmodify -a -c -v -h localhost -p 389 -D "cn=admin,dc=JNDITutorial" -w password -f /vagrant/tutorial.ldif
