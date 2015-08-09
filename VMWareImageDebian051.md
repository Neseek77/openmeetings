Package: http://webbase-design.de/projekte/Debian4.0r2-OpenMeetings0.5.1.zip

Debian4.0r2-OpenMeetings0.5.1.zip

MD5: 165a78cb62cf61539d8a318dd1b1df80

Users and passwords

User root: root

Password: openmeetings


Other user:

User: openmeetings

Password: openmeetings




User root MySQL:

User: root

Password: openmeetings


DB Name for OpenMeetings: openmeetings

User name for DB: openmeetings

Password name for DB: openmeetings


User for administrator in Openmeetings

User: openmeetings

Password: openmeetings

Notes:

Below are pre-requisites for running the OpenMeetings with VMWare.

1) You need to download VMWare Player:
http://www.vmware.com/products/player/

2) In order to run the OpenMeetings VMWare Image you would need to be
either on a DHCP Network so the OpenMeetings VMWare Image can acquire an
IP Address or you need to have a static IP Address which can be assigned to
the OpenMeetings VMWare Image.

If your network is a DHCP Enabled Network the OpenMeetings VMWare Image
would automatically be assigned an IP Address at the time of loading,
once the VMWare Image is loaded please login to the VMImage and follow the
below steps.


a) You can know the IP Address of the VMWare Image by typing ifconfig
from the terminal.

Example:

openmeetings# ifconfig eth0

The output will look like:

eth0      Link encap:Ethernet  direcciónHW 00:16:d4:99:24:51
> inet dirección:10.2.1.181  Difusión:10.2.255.255  Máscara:255.255.0.0
> dirección inet6: fe80::216:d4ff:fe99:2451/64 Alcance:Vínculo
> ARRIBA DIFUSIÓN CORRIENDO MULTICAST  MTU:1500  Metric:1
> RX packets:274854 errors:0 dropped:0 overruns:0 frame:0
> TX packets:469015 errors:0 dropped:0 overruns:0 carrier:0
> colisiones:0 txqueuelen:1000
> RX bytes:40619186 (38.7 MB)  TX bytes:690781270 (658.7 MB)
> Interrupción:16

The IP address for the above example is 10.2.1.181

You can go to this URL http://

&lt;YouIPAddress&gt;

:5080/openmeetings/ to
start/join the OpenMeetings Web
Meeting.

You can login to OpenMeetings using following data:

> Username : openmeetings
> Password : openmeetings

If the OpenMeetings VMWare Image do not get the IP address automatically
then you need to assign an IP Address to the OpenMeetings VMWare Image:

Please follow the below steps to assign a static IP Address to  your
VMWare Image:

Open the file by typing the below command from a terminal

openmeetings# pico /etc/network/interfaces

This will look like:

# The loopback network interface
auto lo
iface lo inet loopback

# The primary network interface
> auto eth0
> iface eth0 inet static
> address 10.1.0.4
> netmask 255.255.0.0
> network 10.1.0.0
> broadcast 10.1.255.255
> gateway 10.1.1.18
> dns-nameservers 10.1.0.2


Please note that the hardware address should remain the same as it is
for the vmware image.

2. Specify Name Server IP Address in resolv.conf

- Open the file by typing the below command from a terminal

openmeetings# pico /etc/resolv.conf

> - Modify the below parameters in the file.

> nameserver XXX.XXX.XXX.XXX
> nameserver XXX.XXX.XXX.XXX

Restart the Network Service.

- Type the below command to restart the network service.

openmeetings# /etc/init.d/networking restart

Confirm IP Address of your VMWare Image

- Type the below command to check IP Address of your VMWare Image

openmeetings# ifconfig eth0

Please ensure that you have provided the proper netmask / broadcast
ip address / default gateway/ dns server address.

Notes:

1) You can enter to Virtual Machine via ssh with root or openmeetings user.
2) If networking not working: rm
/etc/udev/rules.d/z25\_persistent-net.rules and then reboot (fixed in
new image).