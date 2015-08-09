## Version 0.6 ##

  * new ApplicationAdapter Class (keep old one, add new one and change red5-config) ([Issue 593](https://code.google.com/p/openmeetings/issues/detail?id=593))
  * reconnect Users to each Scope instead of general Scope when they enter a room. So you only need to iterate through the Users of a Room instead of all Users (BIg workload in this Issue! maybe this can be split up into severals) => this also includes a rework of the RoomClient-List ([Issue 589](https://code.google.com/p/openmeetings/issues/detail?id=589), [Issue 364](https://code.google.com/p/openmeetings/issues/detail?id=364))
  * add some sort of graph that illustrates the event-model of the room-enter and room-leave events => this is important to have better control and overview of the logic of the RoomClients! to prevent Zombies and other issues ([Issue 592](https://code.google.com/p/openmeetings/issues/detail?id=592))
  * fix Zombie-Control Run (Zombies can be duplicated by VPN connections on OSx for example) ([Issue 583](https://code.google.com/p/openmeetings/issues/detail?id=583))
  * fix recorder (move recorded FLV to default scope so that record does not have to connect to each scope, only to default one)
  * fix overall chat to be able to send message in ~~every~~ default scope(fixed in [r1796](https://code.google.com/p/openmeetings/source/detail?r=1796))
  * fix screen sharing to add the scope-name to be able to send the Screen to the custom room-name(fixed in [r1796](https://code.google.com/p/openmeetings/source/detail?r=1796))
  * fix session object after changing the user-record ([Issue 594](https://code.google.com/p/openmeetings/issues/detail?id=594))
  * add print Jobs to Print-Service instead of Application Adapter(fixed in [r1796](https://code.google.com/p/openmeetings/source/detail?r=1796))

## Version 0.7 ##

  * add overview in Administration which user is in which room (fixed in [r1796](https://code.google.com/p/openmeetings/source/detail?r=1796))
  * add option in Administration to kick users out of room ([Issue 291](https://code.google.com/p/openmeetings/issues/detail?id=291))
  * enable to configure each room another domain to enable a multi server scenario (some kind of clustering where one server is central and redirects each user to the correct room)

## Version 0.8 ##

> ... to be continued