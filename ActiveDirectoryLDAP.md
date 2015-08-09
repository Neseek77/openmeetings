#LDAP Authentication using LDAP.

# Introduction #

LDAP authentication using Active Directory to Authenticate user access.


# Details #


The configuration file needs to be in /webapps/openmeetings/conf and a sample file is as below

```
#ConfigurationFile for LDAP Auth
#ConfigKey 'ldap_config_path' must be set in DataBase Configration of OM and point to this file
#o.becherer,14.01.2009

#specify the LDAP Server type
ldap_server_type=AD

#LDAP URL
# This is the URL used to access your LDAP server.
ldap_conn_url=ldap://<IPofSERVER>:389

#Login distinguished name (DN) for Authentification on LDAP Server - keep emtpy if not required
ldap_admin_dn=<DNofAuthUser>

#Loginpass for Authentification on LDAP Server - keep emtpy if not requiered
ldap_passwd=<AuthPass>

#base to search for userdata(of user, that wants to login)
ldap_search_base=<BaseDN>

# Fieldnames (can differ between Ldap servers)
field_user_principal=userPrincipalName

# Ldap auth type(SIMPLE,NONE)
ldap_auth_type=SIMPLE

# Ldap-password synchronization to OM DB
ldap_sync_password_to_om=no


# Ldap user attributes mapping
# Set the following internal OM user attributes to their corresponding Ldap-attribute
ldap_user_attr_lastname=sn
ldap_user_attr_firstname=givenName
ldap_user_attr_mail=mail
ldap_user_attr_street=streetAddress
ldap_user_attr_additionalname=description
ldap_user_attr_fax=facsimileTelephoneNumber
ldap_user_attr_zip=postalCode
ldap_user_attr_country=co
ldap_user_attr_town=l
ldap_user_attr_phone=telephoneNumber
```

where


&lt;IPofSERVER&gt;

 is the IP address of your LDAP server


&lt;DNofAuthUser&gt;

 is the full DN of your auth user, but using : instead of =


&lt;AuthPass&gt;

 is the password for the auth user


&lt;BaseDN&gt;

 is the base DN that you want to search from

Due to the way that the authentication works in openmeetings you need to use the userPrincipalName for the field\_user\_principal and not the sAMAccountName

Once you have created the file, login to openmeetings as a local admin user and go to the Administration tab, and then the Ldap tab.

Give the configuration a name.

Put the file name that you have created into the "Config file name" box

Put a tick in the "AddDomain to username" box

Put your AD domain name (as in domain.internal) into the domain box

If you now open a new browser and go to http://f.q.d.n:5080 you can change the Domain from "local DB [internal](internal.md)" in the bottom box, to the name that you created and login using a domain account.