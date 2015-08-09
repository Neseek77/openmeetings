# Introduction #
The Quality of Service (QoS) is important for the online meetings, especially for the voice : http://en.wikipedia.org/wiki/Quality_of_service
This page contains several tips to improve the network performances.

# On the computer of each user #
/!\ This works only if you are alone on your internet connexion. If you are several users on the same connexion, read the next section.

  * Install a QoS software (For example [Traffic shapper](http://bandwidthcontroller.com/trafficShaperXp.html) for Windows or use qos4openmeentings.sh script for linux)

If you don't wish to install a QoS software, some tips to improve network performances :
  1. Close P2P softwares (Emule, bittorrent...) ([A list of P2P softwares](http://en.wikipedia.org/wiki/Comparison_of_file_sharing_applications))
  1. Stop downloads
  1. Close Skype (Skype uses your bandwidth for the other users)
  1. Close the web browser window (some websites launches the download of some videos)

# On the internet gateway #
Active the QoS feature of your internet gateway.
Some explanations for each system :
## Debian/Ubuntu systems ##
  1. Copy the file [qos4openmeetings.sh](http://openmeetings.googlecode.com/svn/branches/dev/xmlcrm/java/QoS/qos4openmeetings.sh) in /etc/network/if-pre-up.d/
  1. modify theses variables in the qos4openmeetings.sh file :
    * DEV: the name of the network interface connected on the Internet
    * RATEUP: the maximum upload rate (in kbit/s)
    * OIPIPS: the IP address of your openmeetings server
  1. type in a console: sudo chmod +x /etc/network/if-pre-up.d/qos4openmeetings.sh
  1. type: /etc/network/if-pre-up.d/qos4openmeetings.sh