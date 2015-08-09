> Openmeetings can now be easily packaged as Debian .deb package for further installation on many popular linuxes

# Building sources #

At first, you need to build your code distribution as described in the following [article](http://code.google.com/p/openmeetings/wiki/BuildSourceNew)


# Exporting the package directory structure #

The package directory structure can be obtained from SVN into any empty folder using the export command with the following URL: http://openmeetings.googlecode.com/svn/trunk/debian_package

We will call this folder **<debian package folder>** from now on.

After export, sources distribution should be placed into **<debian package folder>**/usr/share/red5-openmeetings folder

Please, make sure root is the owner of **<debian package folder>** and all of its contents using the chmod command to avoid any possible errors.

# Building the debian package #

Do not forget to modify DEBIAN/control file and set youself as a maintainer, set the appropriate version string and modify the description of the package. If you like you can edit some installation scripts for your needs.

After you have modified all the scripts to your current needs and placed source distribution into the appropriate folder, you need to update the DEBIAN/md5sums file accordingly. Typically this is done by switching to **<debian package folder>** and running the following command as a root:
`find . -type f ! -regex '.*DEBIAN/.*' -printf '%P\0' | xargs -r0 md5sum > DEBIAN/md5sums`
Then `cd ..` and build package using `sudo dpkg -b `**<debian package folder>** **<debian package name>**, where name typically is red5-openmeetings\_1.0.XXXX.noarch.deb, where XXXX is the build number. Voila, you are ready to distribute your own debian package.