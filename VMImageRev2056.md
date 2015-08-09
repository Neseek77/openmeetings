I've (CTpaHHoe) created an openmeetings virtual machine. The virtual machine is
running on OS Debian Lenny.

The version installed is rev2056. Red5 and openoffice.org daemons
(called red5 and ooomeetings) use an account 'vconf'.
Openmeetings admistrator uses the account 'vadmin'.

Also, the administrator is a member of a group 'admins' that lets him
run commands as a root. For details see sudoers config file.

To operate the virtual machine I'm using VMWare Server.
To use a network service, I installed and configured NAT port
forwarding for the ports (rmtp) 1935, 5080, 8088 and (ssh) 2222

Passwords:
Linux:
vconf: openmeetings
vadmin: openmeetings

MySQL:
root: openmeetings

Application
login: admin password: admin

download URL  :  http://www.expressaas.com/distrib/release/openmeetings-vm-r01.7z
SHA1:  f2a7773a47cdb2c8a1f9bbe25c3fd913fc800a7f

I would be glad to your feedback

PS:
Sorry, I used 7zip archiver instead of the usual zip to reduce
traffic.
7zip archiver is available at this link: http://www.7-zip.org/download.html