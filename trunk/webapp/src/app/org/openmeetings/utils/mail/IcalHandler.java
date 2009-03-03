package org.openmeetings.utils.mail;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.net.URI;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Vector;

import org.red5.logging.Red5LoggerFactory;
import org.slf4j.Logger;

import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.TimeZone;
import net.fortuna.ical4j.model.TimeZoneRegistry;
import net.fortuna.ical4j.model.TimeZoneRegistryFactory;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.component.VTimeZone;
import net.fortuna.ical4j.model.parameter.Cn;
import net.fortuna.ical4j.model.parameter.Role;
import net.fortuna.ical4j.model.property.Attendee;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.Description;
import net.fortuna.ical4j.model.property.Method;
import net.fortuna.ical4j.model.property.Organizer;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Uid;
import net.fortuna.ical4j.model.property.Version;
import net.fortuna.ical4j.util.UidGenerator;

/**
 * 
 * @author o.becherer
 *
 */
public class IcalHandler {
	
	private static final Logger log = Red5LoggerFactory.getLogger(MailHandler.class, "openmeetings");

	
	/** ICal instance */
	private net.fortuna.ical4j.model.Calendar icsCalendar;
	
	/** TimeZone */
	private TimeZone timeZone;
	
	/** Creation of a new Event */
	public final static String ICAL_METHOD_REQUEST = "REQUEST";
	public final static String ICAL_METHOD_CANCEL = "CANCEL";
	public final static String ICAL_METHOD_REFRESH = "REFRESH";
	
	// Default is a new Request
	private String ICAL_METHOD = ICAL_METHOD_REQUEST;
	
	/**
	 * Konstruktor with DefaultTimeZone
	 * @param methodType (@see IcalHandler Cosntants)
	 * @throws Exception
	 */
	public IcalHandler(String methodType){
		log.debug("Icalhandler method type : " + methodType);
		
		icsCalendar = new net.fortuna.ical4j.model.Calendar();
		TimeZoneRegistry timeRegistry = TimeZoneRegistryFactory.getInstance().createRegistry();
		timeZone = timeRegistry.getTimeZone(java.util.TimeZone.getDefault().getID());
	
		icsCalendar.getProperties().add(new ProdId("-//Events Calendar//iCal4j 1.0//EN"));
		icsCalendar.getProperties().add(CalScale.GREGORIAN);
		icsCalendar.getProperties().add(Version.VERSION_2_0);
		
		// Switch Method Type
		if(methodType.equals(ICAL_METHOD_REFRESH))
			icsCalendar.getProperties().add(Method.REFRESH);
		else if(methodType.equals(ICAL_METHOD_CANCEL))
			icsCalendar.getProperties().add(Method.CANCEL);
		else
			icsCalendar.getProperties().add(Method.REQUEST);
			
	}
	
	
	/**
	 * 
	 * @param startDate use standard TimeZone!!
	 * @param endDate use standard time zone!!
	 * @param name meeting name
	 * @param attendees List of attendees (use getAttendeeData to retrieve valid records)
	 * @param description containing the meeting description
	 * @return UID of Meeting
	 */
	//---------------------------------------------------------------------------------------
	public String addNewMeeting(GregorianCalendar startDate, GregorianCalendar endDate, String name, Vector<HashMap<String, String>> attendees, String description, HashMap<String, String> organizer) throws Exception{
		log.debug("add new Meeting");
		
		startDate.setTimeZone(timeZone);
		endDate.setTimeZone(timeZone);
		
		DateTime start = new DateTime(startDate.getTime());
		DateTime end = new DateTime(endDate.getTime());
		VEvent meeting = new VEvent(start, end, name);

		// add timezone info..
		VTimeZone tz = timeZone.getVTimeZone();
		meeting.getProperties().add(tz.getTimeZoneId());
		
		meeting.getProperties().add(new Description(description));
		
		// generate unique identifier..
		UidGenerator ug = new UidGenerator("uidGen");
		Uid uid = ug.generateUid();
	
		log.debug("Generating Meeting UID : " + uid.getValue());
		
		meeting.getProperties().add(uid);
		
		for(int i = 0; i <attendees.size(); i++){
			HashMap<String, String> oneAtt = attendees.get(i);
			
			Attendee uno = new Attendee(URI.create(oneAtt.get("uri")));
			uno.getParameters().add(Role.REQ_PARTICIPANT);
			
			uno.getParameters().add(new Cn(oneAtt.get("cn")));
			meeting.getProperties().add(uno);
		}
		
		Organizer orger = new Organizer(URI.create(organizer.get("uri")));
		orger.getParameters().add(new Cn(organizer.get("cn")));
		
		meeting.getProperties().add(orger);
	
		icsCalendar.getComponents().add(meeting);
		
		return uid.getValue();
		
	}
	//---------------------------------------------------------------------------------------
	
	/**
	 * Use this function to build a valid record for the AttendeeList for addMeetings
	 * Generate a Attendee
	 */
	//------------------------------------------------------------------------------------------
	public HashMap<String, String> getAttendeeData(String emailAdress, String displayName){
		
		HashMap<String, String> oneRecord = new HashMap<String, String>();
		oneRecord.put("uri", "mailto:" + emailAdress);
		oneRecord.put("cn", displayName);
		
		return oneRecord;
		
	}
	//------------------------------------------------------------------------------------------
	
	
	/**
	 * Write iCal to File
	 */
	//------------------------------------------------------------------------------------------
	public void writeDataToFile(String filerPath) throws Exception{
		
		if(!filerPath.endsWith(".ics"))
			filerPath = filerPath + ".ics";
		
		
		FileOutputStream fout = new FileOutputStream(filerPath);

		CalendarOutputter outputter = new CalendarOutputter();
		outputter.output(icsCalendar, fout);
		
		
	}
	//------------------------------------------------------------------------------------------
	
	/**
	 * Get IcalBody as ByteArray
	 */
	//------------------------------------------------------------------------------------------
	public byte[] getIcalAsByteArray() throws Exception{
		
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		CalendarOutputter outputter = new CalendarOutputter();
		outputter.output(icsCalendar, bout);
		return bout.toByteArray();

	}
	//------------------------------------------------------------------------------------------
	
	
	/**
	 * Retrieving Data as String
	 */
	//------------------------------------------------------------------------------------------
	public String getICalDataAsString(){
		return icsCalendar.toString();
	}
	//------------------------------------------------------------------------------------------
	
}
