# update the packages on the system
exec { "apt-get update":
  path => "/usr/bin",
}
# package { "apache2":
#   ensure  => present,
#   require => Exec["apt-get update"],
# }
# service { "apache2":
#   ensure  => "running",
#   require => Package["apache2"],
# }
# file { "/var/www/uptime-webpage":
#   ensure  => "link",
#   target  => "/vagrant/uptime-webpage",
#   require => Package["apache2"],
#   notify  => Service["apache2"],
# }

# Bootstrap ldap
node default {
  class { 'ldap':
    server      => true,
    ssl         => false,
  }
}
ldap::define::domain {'puppetlabs.test':
  basedn   => 'dc=puppetlabs,dc=test',
  rootdn   => 'cn=admin',
  rootpw   => 'test',
}