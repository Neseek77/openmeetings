package org.xmlcrm.test.basic;

import org.xmlcrm.app.data.basic.Fieldmanagment;
import org.xmlcrm.app.data.basic.Languagemanagement;

import junit.framework.TestCase;

public class StartUpLanguageFieldsConference extends TestCase {
	
	public StartUpLanguageFieldsConference(String testname){
		super(testname);
	}
	
	public void testStartUpLanguageFieldsConference(){
		
		Languagemanagement.getInstance().addLanguage("deutsch");
		Languagemanagement.getInstance().addLanguage("english");
		Languagemanagement.getInstance().addLanguage("french");		
		Languagemanagement.getInstance().addLanguage("spanish");		
		
		Fieldmanagment.getInstance().addFourFieldValues("conference", 1, "Konferenz", "Confernce", "Conf�rence", "Conferencia");
		Fieldmanagment.getInstance().addFourFieldValues("meeting", 2, "Meeting", "Meeting", "Meeting", "Encuentro");
		Fieldmanagment.getInstance().addFourFieldValues("classroom", 3, "Auditorium", "Auditorium", "Auditorium", "Auditorio");
		Fieldmanagment.getInstance().addFourFieldValues("settings", 4, "Einstellungen", "Settings", "Param�tres", "Calibraci�n");
		Fieldmanagment.getInstance().addFourFieldValues("benutzer", 5, "Benutzer", "User", "Client", "Usuario");
		Fieldmanagment.getInstance().addFourFieldValues("admin", 6, "Administration", "Administration", "Administration", "Administration");
		Fieldmanagment.getInstance().addFourFieldValues("stop", 7, "Stop", "Stop", "Stop", "Stop");
		Fieldmanagment.getInstance().addFourFieldValues("record", 8, "Record", "Record", "Record", "Record");
		Fieldmanagment.getInstance().addFourFieldValues("nofile", 9, "Keine Datei vorhanden", "Keine Datei vorhanden", "Keine Datei vorhanden", "Keine Datei vorhanden");
		Fieldmanagment.getInstance().addFourFieldValues("recordbyteacher", 10, "Aufnahme nur f�r Lehrer verf�gbar", "Aufnahme nur f�r Lehrer verf�gbar", "Aufnahme nur f�r Lehrer verf�gbar", "Aufnahme nur f�r Lehrer verf�gbar");
		Fieldmanagment.getInstance().addFourFieldValues("connectedusers", 11, "verbunden Benutzer:", "verbunden Benutzer:", "verbunden Benutzer:", "verbunden Benutzer:");
		Fieldmanagment.getInstance().addFourFieldValues("startconf", 12, "Konferenz starten", "Konferenz starten", "Konferenz starten", "Konferenz starten");
		Fieldmanagment.getInstance().addFourFieldValues("myname", 13, "Mein Name", "Mein Name", "Mein Name", "Mein Name");
		Fieldmanagment.getInstance().addFourFieldValues("videoconference", 14, "VideoConference", "VideoConference", "VideoConference", "VideoConference");
		Fieldmanagment.getInstance().addFourFieldValues("import", 15, "Pr�sentation importieren", "Pr�sentation importieren", "Pr�sentation importieren", "Pr�sentation importieren");
		Fieldmanagment.getInstance().addFourFieldValues("refreshfiles", 16, "Liste neu laden", "Liste neu laden", "Liste neu laden", "Liste neu laden");
		Fieldmanagment.getInstance().addFourFieldValues("tomainfile", 17, "Zum Hauptverzeichnis", "Zum Hauptverzeichnis", "Zum Hauptverzeichnis", "Zum Hauptverzeichnis");
		Fieldmanagment.getInstance().addFourFieldValues("newpoll", 18, "neue Umfrage", "neue Umfrage", "neue Umfrage", "neue Umfrage");
		Fieldmanagment.getInstance().addFourFieldValues("newpollheader", 19, "Eine neue Umfrage f�r die Konferenz.", "Eine neue Umfrage f�r die Konferenz.", "Eine neue Umfrage f�r die Konferenz.", "Eine neue Umfrage f�r die Konferenz.");
		Fieldmanagment.getInstance().addFourFieldValues("question", 20, "Frage:", "Frage:", "Frage:", "Frage:");
		Fieldmanagment.getInstance().addFourFieldValues("polltype", 21, "Umfragenart:", "Umfragenart:", "Umfragenart:", "Umfragenart:");
		Fieldmanagment.getInstance().addFourFieldValues("create", 22, "anlegen", "anlegen", "anlegen", "anlegen");
		Fieldmanagment.getInstance().addFourFieldValues("infomessage", 23, "Info: Jeder verbundene Benutzer erh�lt eine Nachricht mit der neuen Umfrage.", "Info: Jeder verbundene Benutzer erh�lt eine Nachricht mit der neuen Umfrage.", "Info: Jeder verbundene Benutzer erh�lt eine Nachricht mit der neuen Umfrage.", "Info: Jeder verbundene Benutzer erh�lt eine Nachricht mit der neuen Umfrage.");
		Fieldmanagment.getInstance().addFourFieldValues("creatpoll", 24, "Umfrage anlegen", "Umfrage anlegen", "Umfrage anlegen", "Umfrage anlegen");
		Fieldmanagment.getInstance().addFourFieldValues("cancel", 25, "abbrechen", "abbrechen", "abbrechen", "abbrechen");
		Fieldmanagment.getInstance().addFourFieldValues("yesno", 26, "Ja/Nein", "Ja/Nein", "Ja/Nein", "Ja/Nein");
		Fieldmanagment.getInstance().addFourFieldValues("numeric", 27, "Numerisch 1-10", "Numerisch 1-10", "Numerisch 1-10", "Numerisch 1-10");
		Fieldmanagment.getInstance().addFourFieldValues("poll", 28, "Umfrage", "Umfrage", "Umfrage", "Umfrage");
		Fieldmanagment.getInstance().addFourFieldValues("moderation", 29, "Sie m�ssen Moderator/Lehrer in diesem Raum sein um eine Umfrage anzulegen.", "Sie m�ssen Moderator/Lehrer in diesem Raum sein um eine Umfrage anzulegen.", "Sie m�ssen Moderator/Lehrer in diesem Raum sein um eine Umfrage anzulegen.", "Sie m�ssen Moderator/Lehrer in diesem Raum sein um eine Umfrage anzulegen.");
		Fieldmanagment.getInstance().addFourFieldValues("vote", 30, "Ihr Stimme wurde abgegeben.", "Ihr Stimme wurde abgegeben.", "Ihr Stimme wurde abgegeben.", "Ihr Stimme wurde abgegeben.");
		Fieldmanagment.getInstance().addFourFieldValues("alreadyvoted", 31, "Sie haben f�r diese Umfrage bereits ihr Votum abgegeben.", "Sie haben f�r diese Umfrage bereits ihr Votum abgegeben.", "Sie haben f�r diese Umfrage bereits ihr Votum abgegeben.", "Sie haben f�r diese Umfrage bereits ihr Votum abgegeben.");
		Fieldmanagment.getInstance().addFourFieldValues("voting", 32, "abstimmen!", "abstimmen!", "abstimmen!", "abstimmen!");
		Fieldmanagment.getInstance().addFourFieldValues("answer", 33, "Ihre Antwort:", "Ihre Antwort:", "Ihre Antwort:", "Ihre Antwort:");
		Fieldmanagment.getInstance().addFourFieldValues("yes", 34, "Ja", "Ja", "Ja", "Ja");
		Fieldmanagment.getInstance().addFourFieldValues("no", 35, "Nein", "Nein", "Nein", "Nein");
		Fieldmanagment.getInstance().addFourFieldValues("questionwant", 36, "will wissen:", "will wissen:", "will wissen:", "will wissen:");
		Fieldmanagment.getInstance().addFourFieldValues("pollresults", 37, "Umfrageergebnisse", "Umfrageergebnisse", "Umfrageergebnisse", "Umfrageergebnisse");
		Fieldmanagment.getInstance().addFourFieldValues("question", 38, "Frage:", "Frage:", "Frage:", "Frage:");
		Fieldmanagment.getInstance().addFourFieldValues("results", 39, "Antworten:", "Antworten:", "Antworten:", "Antworten:");
		Fieldmanagment.getInstance().addFourFieldValues("answers", 40, "Ergebnis:", "Ergebnis:", "Ergebnis:", "Ergebnis:");
		Fieldmanagment.getInstance().addFourFieldValues("nopoll", 41, "Es gibt zur Zeit keine Umfage.", "Es gibt zur Zeit keine Umfage.", "Es gibt zur Zeit keine Umfage.", "Es gibt zur Zeit keine Umfage.");
		Fieldmanagment.getInstance().addFourFieldValues("votings", 42, "abstimmen!", "abstimmen!", "abstimmen!", "abstimmen!");
		Fieldmanagment.getInstance().addFourFieldValues("meeting", 43, "Meeting (max 4 Pl�tze)", "Meeting (max 4 Pl�tze)", "Meeting (max 4 Pl�tze)", "Meeting (max 4 Pl�tze)");
		Fieldmanagment.getInstance().addFourFieldValues("conference", 44, "Conference (max 50 Pl�tze)", "Conference (max 50 Pl�tze)", "Conference (max 50 Pl�tze)", "Conference (max 50 Pl�tze)");
		Fieldmanagment.getInstance().addFourFieldValues("type", 45, "Modus", "Modus", "Modus", "Modus");
		Fieldmanagment.getInstance().addFourFieldValues("remainingseats", 46, "verbleibende Pl�tze", "verbleibende Pl�tze", "verbleibende Pl�tze", "verbleibende Pl�tze");
		Fieldmanagment.getInstance().addFourFieldValues("alreadychosen", 47, "Bereits vergeben", "Bereits vergeben", "Bereits vergeben", "Bereits vergeben");
		Fieldmanagment.getInstance().addFourFieldValues("enter", 48, "Eintreten", "Eintreten", "Eintreten", "Eintreten");
		Fieldmanagment.getInstance().addFourFieldValues("modleave", 49, "Der Moderator/Lehrer dieses Raums hat den Raum verlassen.", "Der Moderator/Lehrer dieses Raums hat den Raum verlassen.", "Der Moderator/Lehrer dieses Raums hat den Raum verlassen.", "Der Moderator/Lehrer dieses Raums hat den Raum verlassen.");
		Fieldmanagment.getInstance().addFourFieldValues("systemmessage", 50, "Systemnachricht", "Systemnachricht", "Systemnachricht", "Systemnachricht");
		Fieldmanagment.getInstance().addFourFieldValues("chossedevice", 51, "Ger�teauswahl", "Ger�teauswahl", "Ger�teauswahl", "Ger�teauswahl");
		Fieldmanagment.getInstance().addFourFieldValues("choosecam", 52, "Kamera w�hlen:", "Kamera w�hlen:", "Kamera w�hlen:", "Kamera w�hlen:");
		Fieldmanagment.getInstance().addFourFieldValues("choosemic", 53, "Mikrophon w�hlen:", "Mikrophon w�hlen:", "Mikrophon w�hlen:", "Mikrophon w�hlen:");
		Fieldmanagment.getInstance().addFourFieldValues("ok", 54, "ok", "ok", "ok", "ok");
		Fieldmanagment.getInstance().addFourFieldValues("cancel2", 55, "abbrechen", "abbrechen", "abbrechen", "abbrechen");
		Fieldmanagment.getInstance().addFourFieldValues("reconeectneeded", 56, "Sie m�ssen sich erneut verbinden damit die �nderungen wirksam werden.", "Sie m�ssen sich erneut verbinden damit die �nderungen wirksam werden.", "Sie m�ssen sich erneut verbinden damit die �nderungen wirksam werden.", "Sie m�ssen sich erneut verbinden damit die �nderungen wirksam werden.");
		Fieldmanagment.getInstance().addFourFieldValues("editsetup", 57, "Einstellungen �ndern.", "Einstellungen �ndern.", "Einstellungen �ndern.", "Einstellungen �ndern.");
		Fieldmanagment.getInstance().addFourFieldValues("course", 58, "Kurs:", "Kurs:", "Kurs:", "Kurs:");
		Fieldmanagment.getInstance().addFourFieldValues("language", 59, "Kurssprache:", "Kurssprache:", "Kurssprache:", "Kurssprache:");
		Fieldmanagment.getInstance().addFourFieldValues("ok2", 60, "ok", "ok", "ok", "ok");
		Fieldmanagment.getInstance().addFourFieldValues("cancel3", 61, "abbrechen", "abbrechen", "abbrechen", "abbrechen");
		Fieldmanagment.getInstance().addFourFieldValues("clearwhiteboard", 62, "Zeichenbrett leeren", "Zeichenbrett leeren", "Zeichenbrett leeren", "Zeichenbrett leeren");
		Fieldmanagment.getInstance().addFourFieldValues("clearwhiteboardquestion", 63, "Soll dass Zeichenbrett geleert werden bevor ein neues Bild hinzugef�gt wird?", "Soll dass Zeichenbrett geleert werden bevor ein neues Bild hinzugef�gt wird?", "Soll dass Zeichenbrett geleert werden bevor ein neues Bild hinzugef�gt wird?", "Soll dass Zeichenbrett geleert werden bevor ein neues Bild hinzugef�gt wird?");
		Fieldmanagment.getInstance().addFourFieldValues("dontaskagain", 64, "Nicht nochmal fragen", "Nicht nochmal fragen", "Nicht nochmal fragen", "Nicht nochmal fragen");
		Fieldmanagment.getInstance().addFourFieldValues("no", 65, "nein", "nein", "nein", "nein");
		Fieldmanagment.getInstance().addFourFieldValues("editsetup2", 66, "Einstellungen bearbeiten", "Einstellungen bearbeiten", "Einstellungen bearbeiten", "Einstellungen bearbeiten");
		Fieldmanagment.getInstance().addFourFieldValues("needconfirmationwhiteboard", 67, "Best�tigung anfordern bevor das Zeichenbrett geleert wird.", "Best�tigung anfordern bevor das Zeichenbrett geleert wird.", "Best�tigung anfordern bevor das Zeichenbrett geleert wird.", "Best�tigung anfordern bevor das Zeichenbrett geleert wird.");
		Fieldmanagment.getInstance().addFourFieldValues("userinfo", 68, "User Info", "User Info", "User Info", "User Info");
		Fieldmanagment.getInstance().addFourFieldValues("cleardrawarea", 69, "Clear DrawArea", "Clear DrawArea", "Clear DrawArea", "Clear DrawArea");
		Fieldmanagment.getInstance().addFourFieldValues("undo", 70, "Undo", "Undo", "Undo", "Undo");
		Fieldmanagment.getInstance().addFourFieldValues("redo", 71, "Redo", "Redo", "Redo", "Redo");
		Fieldmanagment.getInstance().addFourFieldValues("selectobject", 72, "Select an Object", "Select an Object", "Select an Object", "Select an Object");
		Fieldmanagment.getInstance().addFourFieldValues("text", 73, "Text", "Text", "Text", "Text");
		Fieldmanagment.getInstance().addFourFieldValues("paint", 74, "Paint", "Paint", "Paint", "Paint");
		Fieldmanagment.getInstance().addFourFieldValues("drawline", 75, "Draw line", "Draw line", "Draw line", "Draw line");
		Fieldmanagment.getInstance().addFourFieldValues("drawu", 76, "Draw underline", "Draw underline", "Draw underline", "Draw underline");
		Fieldmanagment.getInstance().addFourFieldValues("rect", 77, "Rectangle", "Rectangle", "Rectangle", "Rectangle");
		Fieldmanagment.getInstance().addFourFieldValues("ellipse", 78, "Ellipse", "Ellipse", "Ellipse", "Ellipse");
		Fieldmanagment.getInstance().addFourFieldValues("arrow", 79, "Arrow", "Arrow", "Arrow", "Arrow");
		Fieldmanagment.getInstance().addFourFieldValues("deletechosen", 80, "delete chosen Item", "delete chosen Item", "delete chosen Item", "delete chosen Item");
		Fieldmanagment.getInstance().addFourFieldValues("appliymod", 81, "Apply for moderation", "Apply for moderation", "Apply for moderation", "Apply for moderation");
		Fieldmanagment.getInstance().addFourFieldValues("apply", 82, "apply", "apply", "apply", "apply");
		Fieldmanagment.getInstance().addFourFieldValues("cancel", 83, "cancel", "cancel", "cancel", "cancel");
		Fieldmanagment.getInstance().addFourFieldValues("mod", 84, "Become moderator", "Become moderator", "Become moderator", "Become moderator");
		Fieldmanagment.getInstance().addFourFieldValues("close", 85, "close", "close", "close", "close");
		Fieldmanagment.getInstance().addFourFieldValues("italic", 86, "italic", "italic", "italic", "italic");
		Fieldmanagment.getInstance().addFourFieldValues("bold", 87, "bold", "bold", "bold", "bold");
		Fieldmanagment.getInstance().addFourFieldValues("waiting", 88, "WAITING", "WAITING", "WAITING", "WAITING");
		Fieldmanagment.getInstance().addFourFieldValues("applyMessage", 89, "A User wants to apply for moderation:", "A User wants to apply for moderation:", "A User wants to apply for moderation:", "A User wants to apply for moderation:");
		Fieldmanagment.getInstance().addFourFieldValues("accept", 90, "accept", "accept", "accept", "accept");
		Fieldmanagment.getInstance().addFourFieldValues("reject", 91, "reject", "reject", "reject", "reject");
		Fieldmanagment.getInstance().addFourFieldValues("cancel", 92, "cancel", "cancel", "cancel", "cancel");
		Fieldmanagment.getInstance().addFourFieldValues("sendmodrequestmessage", 93, "Sending request to following Users", "Sending request to following Users", "Sending request to following Users", "Sending request to following Users");
		Fieldmanagment.getInstance().addFourFieldValues("accept", 94, "Accepted", "Accepted", "Accepted", "Accepted");
		Fieldmanagment.getInstance().addFourFieldValues("reject", 95, "Rejected", "Rejected", "Rejected", "Rejected");
		Fieldmanagment.getInstance().addFourFieldValues("changemod", 96, "Change Moderator", "Change Moderator", "Change Moderator", "Change Moderator");
		Fieldmanagment.getInstance().addFourFieldValues("nonmoderrormessage", 97, "You are not moderating this course!", "You are not moderating this course!", "You are not moderating this course!", "You are not moderating this course!");
		Fieldmanagment.getInstance().addFourFieldValues("moderator", 98, "Moderator:", "Moderator:", "Moderator:", "Moderator:");
		Fieldmanagment.getInstance().addFourFieldValues("roomfullmessage", 99, "This Room is full. Sorry please try again later.", "This Room is full. Sorry please try again later.", "This Room is full. Sorry please try again later.", "This Room is full. Sorry please try again later.");
		Fieldmanagment.getInstance().addFourFieldValues("elllipse", 100, "Ellipse", "Ellipse", "Ellipse", "Ellipse");
		Fieldmanagment.getInstance().addFourFieldValues("close", 101, "close", "close", "close", "close");
		Fieldmanagment.getInstance().addFourFieldValues("AuthError", 102, "Eingabefehler", "input data error", "input data error", "input data error");
		Fieldmanagment.getInstance().addFourFieldValues("min4username", 103, "Der Benutzername muss mindestens 4 Zeichen lang sein", "username must be 4 characters at least", "username must be 4 characters at least", "username must be 4 characters at least");
		Fieldmanagment.getInstance().addFourFieldValues("min4pass", 104, "Das Passwort muss mindestens 4 Zeichen lang sein", "userpass must be 4 characters at least", "userpass must be 4 characters at least", "userpass must be 4 characters at least");
		Fieldmanagment.getInstance().addFourFieldValues("usernametaken", 105, "Der Benutzername ist bereits vergeben", "The username is already taken", "The username is already taken", "The username is already taken");
		Fieldmanagment.getInstance().addFourFieldValues("emailtaken", 106, "Die EMail ist bereits registriert", "The email is already registered", "The email is already registered", "The email is already registered");
		Fieldmanagment.getInstance().addFourFieldValues("emailtaken", 107, "Ein Fehler wurde geworfen bitte kontaktieren Sie das Admin Team", "System error please contact System-Admins", "System error please contact System-Admins", "System error please contact System-Admins");
		Fieldmanagment.getInstance().addFourFieldValues("Authlogin", 108, "Login", "Login", "Login", "Login");
		Fieldmanagment.getInstance().addFourFieldValues("Authuser", 109, "Benutzer:", "User:", "User:", "User:");
		Fieldmanagment.getInstance().addFourFieldValues("Authpass", 110, "Pass:", "Pass:", "Pass:", "Pass:");
		Fieldmanagment.getInstance().addFourFieldValues("Authlang", 111, "Language", "Language", "Language", "Language");
		Fieldmanagment.getInstance().addFourFieldValues("Authreg", 112, "Login", "Login", "Login", "Login");
		Fieldmanagment.getInstance().addFourFieldValues("regformhead", 113, "Sign Up", "Sign Up", "Sign Up", "Sign Up");
		Fieldmanagment.getInstance().addFourFieldValues("regformuser", 114, "Benutzer:", "User:", "User:", "User:");
		Fieldmanagment.getInstance().addFourFieldValues("regformpass", 115, "Pass:", "Pass:", "Pass:", "Pass:");
		Fieldmanagment.getInstance().addFourFieldValues("regformretype", 116, "Wiederhole:", "ReType:", "ReType:", "ReType:");
		Fieldmanagment.getInstance().addFourFieldValues("regformfirstname", 117, "Vorname:", "Firstname:", "Firstname:", "Firstname:");
		Fieldmanagment.getInstance().addFourFieldValues("regformlastname", 118, "Nachname:", "Lastname:", "Lastname:", "Lastname:");
		Fieldmanagment.getInstance().addFourFieldValues("regformmail", 119, "EMail:", "Mail:", "Mail:", "Mail:");
		Fieldmanagment.getInstance().addFourFieldValues("regformstate", 120, "Land:", "Country:", "Country:", "Country:");
		Fieldmanagment.getInstance().addFourFieldValues("regformbtn1", 121, "Registrieren", "Register", "Register", "Register");
		Fieldmanagment.getInstance().addFourFieldValues("regformbtn2", 122, "Abbrechen", "Cancel", "Cancel", "Cancel");
		Fieldmanagment.getInstance().addFourFieldValues("Authbtn2", 123, "Register", "Register", "Register", "Register");
		Fieldmanagment.getInstance().addFourFieldValues("dashboard", 124, "Home", "home", "home", "home");
		Fieldmanagment.getInstance().addFourFieldValues("useradmin", 125, "Benutzer", "Users", "Users", "Users");
		Fieldmanagment.getInstance().addFourFieldValues("groupadmin", 126, "Gruppen", "Groups", "Groups", "groups");
		Fieldmanagment.getInstance().addFourFieldValues("orgadmin", 127, "Organisationen", "Organisations", "Organisations", "Organisations");
		
		
	}
	

}
