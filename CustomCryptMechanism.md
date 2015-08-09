# Configuration of Custom Crypt-Style #

You can use custom Crypt-Types, but you should decide during installation which Type of encryption you want to use. (available since [r1019](https://code.google.com/p/openmeetings/source/detail?r=1019))
By default two type are available:

  * org.xmlcrm.utils.crypt.MD5Implementation - this uses common MD5 Crypt like PHP does, this is the default one (results in something like: fe01ce2a7fbac8fafaed7c982a04e229)
  * org.xmlcrm.utils.crypt.MD5CryptImplementation - does use BSD-Style of encryption using a salt (results in something like: $1$GMsj7F2I$5S3r9CeukXGXNwf6b4sph1)

You can edit the config-key during Installation or later in the Administration Panel. But if you change it using the Administration-Panel previous passwords might be not working anymore as they are encrypted with another algorithm.

# Writing your own Crypt-Style #

To add your own crypt style you need to write a class which implements the interface:
org.xmlcrm.utils.cryptCryptString

and extends the Adapter:
org.xmlcrm.utils.CryptStringAdapter

Example of an Implementation:
```
package org.xmlcrm.utils.crypt;

import java.security.NoSuchAlgorithmException;

public class MD5Implementation extends CryptStringAdapter implements CryptString {

	@Override
	public String createPassPhrase(String userGivenPass) {
		String passPhrase = null;
		try {
			passPhrase = MD5.do_checksum(userGivenPass);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return passPhrase;
	}

	@Override
	public Boolean verifyPassword(String passGiven, String passwdFromDb) {
		return (passwdFromDb.equals(createPassPhrase(passGiven)));
	}
	
}
```

To add your own Encryption-Class you need to add your class to the OpenMeetings-Webapp (make it available to the webapp-classpath) and use your custom-class-name instead of
org.xmlcrm.utils.crypt.MD5Implementation during the Installation or at runtime by editing the config-key `crypt_ClassName`

# Credits #

credits goto Mika for sharing his Implementation of the MD5Crypt-Style