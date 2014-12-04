echo "========="
echo "java ldif"
echo "========="
ldapadd -Y EXTERNAL -H ldapi:/// -f /etc/ldap/schema/java.ldif

echo "============="
echo "tutorial ldif"
echo "============="
ldapmodify -a -c -v -h localhost -p 389 -D "cn=JNDITutorial, dc=JNDITutorial" -w JNDITutorial -f /vagrant/tutorial.ldif
