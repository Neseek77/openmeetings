package org.xmlcrm.test.init;

import junit.framework.TestCase;

import org.xmlcrm.app.data.basic.Fieldmanagment;
import org.xmlcrm.app.data.basic.Navimanagement;

public class AddDefaultfield extends TestCase {
	
	public AddDefaultfield (String testname){
		super(testname);
	}
	
	public void testaddDefaultField(){
		
//		Fieldmanagment.getInstance().addFourFieldValues("organisationtablelist_idrow", 164, "Organisations-ID", "Organisation-ID", "Organisation-ID", "Organisation-ID");
//		Fieldmanagment.getInstance().addFourFieldValues("organisationtablelist_namerow", 165, "Name", "name", "name", "name");
//		Fieldmanagment.getInstance().addFourFieldValues("uservalue_levelid1", 166, "Benutzer", "user", "user", "user");
//		Fieldmanagment.getInstance().addFourFieldValues("uservalue_levelid2", 167, "Moderator", "mod", "mod", "mod");
//		Fieldmanagment.getInstance().addFourFieldValues("uservalue_levelid3", 168, "Admin", "admin", "admin", "admin");
//		Fieldmanagment.getInstance().addFourFieldValues("uservalue_levellabel", 169, "Benuterrolle", "userlevel", "userlevel", "userlevel");
//		Fieldmanagment.getInstance().addFourFieldValues("orgvalue_header", 170, "Organisation", "organisation", "organisation", "organisation");
//		Fieldmanagment.getInstance().addFourFieldValues("orgvalue_orgname", 171, "Name", "name", "name", "name");
//		Fieldmanagment.getInstance().addFourFieldValues("orgvalue_orgname", 172, "Organisation hinzuf�gen", "add organisation", "add organisation", "add organisation");
//		Fieldmanagment.getInstance().addFourFieldValues("orgvalue_orgname", 173, "Organisation hinzuf�gen", "add organisation", "add organisation", "add organisation");
//		Fieldmanagment.getInstance().addFourFieldValues("orgvalue_userwin", 174, "abbrechen", "cancel", "cancel", "cancel");
//		Fieldmanagment.getInstance().addFourFieldValues("orgvalue_userwin", 175, "hinzuf�gen", "add", "add", "add");
//		Fieldmanagment.getInstance().addFourFieldValues("orgvalue_userwin", 176, "Organisation entfernen", "remove organisation", "remove organisation", "remove organisation");
//		Fieldmanagment.getInstance().addFourFieldValues("orgvalue_userlist", 177, "Benutzer", "user", "user", "user");
//		Fieldmanagment.getInstance().addFourFieldValues("orgvalue_userlistadd", 178, "Benutzer hinzuf�gen", "add user", "add user", "add user");
//		Fieldmanagment.getInstance().addFourFieldValues("orgvalue_userlistdelete", 179, "Benutzer entfernen", "delete user", "delete user", "delete user");
//		Fieldmanagment.getInstance().addFourFieldValues("orgvalue_userwinheader", 180, "Benutzer hinzuf�gen", "add user", "add user", "add user");
//		Fieldmanagment.getInstance().addFourFieldValues("orgvalue_userwinsearchfield", 181, "Benutzer suchen", "search user", "search user", "search user");
//		Fieldmanagment.getInstance().addFourFieldValues("orgvalue_userwinsearchbtn", 182, "suchen", "search", "search", "search");
//		Fieldmanagment.getInstance().addFourFieldValues("orgvalue_userwinsearchresult", 183, "Benutzer", "user", "user", "user");
//		Fieldmanagment.getInstance().addFourFieldValues("loginwin_chooseorganisation", 184, "Organisation", "organisation", "organisation", "organisation");
//		Fieldmanagment.getInstance().addFourFieldValues("loginwin_chooseorganisationbtn", 185, "ausw�hlen", "enter", "enter", "enter");
//		Fieldmanagment.getInstance().addFourFieldValues("navi_roomadmin", 186, "Konferenzr�ume", "conferencerooms", "conferencerooms", "conferencerooms");
//		Fieldmanagment.getInstance().addFourFieldValues("roomadmin_header", 187, "Konferenzr�ume", "Conferencerooms", "Conferencerooms", "Conferencerooms");
//		Fieldmanagment.getInstance().addFourFieldValues("roomadmin_header", 188, "ID", "id", "id", "id");
//		Fieldmanagment.getInstance().addFourFieldValues("roomadmin_header", 189, "Name", "Name", "Name", "Name");
//		Fieldmanagment.getInstance().addFourFieldValues("roomadmin_header", 190, "�ffentlich", "public", "public", "public");
//		Fieldmanagment.getInstance().addFourFieldValues("roomadmin_header", 191, "Organisationen", "organisations", "organisations", "organisations");
		
//		Fieldmanagment.getInstance().addFourFieldValues("roomadmin_header", 192, "Konferenzr�ume", "Conferencerooms", "Conferencerooms", "Conferencerooms");
//		Fieldmanagment.getInstance().addFourFieldValues("roomvalue_name", 193, "Name", "name", "name", "name");
//		Fieldmanagment.getInstance().addFourFieldValues("roomvalue_type", 194, "Typ", "type", "type", "type");
//		Fieldmanagment.getInstance().addFourFieldValues("roomvalue_ispublic", 195, "�ffentlich", "public", "public", "public");
//		Fieldmanagment.getInstance().addFourFieldValues("roomvalue_comment", 196, "Kommentar", "comment", "comment", "comment");
//		Fieldmanagment.getInstance().addFourFieldValues("whiteboard_saveicon", 197, "Speichern", "save", "save", "save");
//		Fieldmanagment.getInstance().addFourFieldValues("whiteboard_openicon", 198, "�ffnen", "load", "load", "load");
//		Fieldmanagment.getInstance().addFourFieldValues("whiteboard_saveaswinheader", 199, "Speichern unter", "save as", "save as", "save as");
//		Fieldmanagment.getInstance().addFourFieldValues("whiteboard_saveaswintext", 200, "Dateiname", "filename", "filename", "filename");
//		Fieldmanagment.getInstance().addFourFieldValues("whiteboard_saveaswintext", 201, "Dateiname", "filename", "filename", "filename");
//		Fieldmanagment.getInstance().addFourFieldValues("whiteboard_saveaswinbtn1", 202, "Abbrechen", "cancel", "cancel", "cancel");
//		Fieldmanagment.getInstance().addFourFieldValues("whiteboard_saveaswinbtn2", 203, "Speichern", "save", "save", "save");
//		Fieldmanagment.getInstance().addFourFieldValues("rpcerrorwin_header", 204, "Fehler", "error", "error", "error");
//		Fieldmanagment.getInstance().addFourFieldValues("loadwml_header", 205, "Laden", "loading", "loading", "loading");
//		Fieldmanagment.getInstance().addFourFieldValues("loadwml_messsload", 206, "Objekte geladen", "objects loaded", "objects loaded", "objects loaded");
//		Fieldmanagment.getInstance().addFourFieldValues("loadwml_messsync", 207, "Synchronisiere Clients. Restliche Clients: ", "snychronizing clients, clients to wait:", "snychronizing clients, clients to wait:", "snychronizing clients, clients to wait:");
//		Fieldmanagment.getInstance().addFourFieldValues("loadimage_messload", 208, "Lade Bilddaten", "Loading Imagedata", "Loading Imagedata", "Loading Imagedata");
//		Fieldmanagment.getInstance().addFourFieldValues("loadimage_messsync", 209, "Synchronisiere Clients. Restliche Clients: ", "snychronizing clients, clients to wait:", "snychronizing clients, clients to wait:", "snychronizing clients, clients to wait:");
//		Fieldmanagment.getInstance().addFourFieldValues("loadwml_confirmheader", 210, "Zeichenbrett leeren", "clear drawarea", "clear drawarea", "clear drawarea");
//		Fieldmanagment.getInstance().addFourFieldValues("loadwml_confirmmess", 211, "Zeichnbrett leeren, alle bisherigen �nderungen gehen damit verloren!", "clear drawarea, all data on whiteboard will be lost", "clear drawarea, all data on whiteboard will be lost", "clear drawarea, all data on whiteboard will be lost");
//		Fieldmanagment.getInstance().addFourFieldValues("loadwml_confirmmess2", 212, "Best�tigung anfordern vor dem Laden einer Datei", "Confirm before loading a file", "Confirm before loading a file", "Confirm before loading a file");
		
	}

}
