package org.openmeetings.app.templates;

import java.io.StringWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.openmeetings.app.data.basic.Configurationmanagement;
import org.openmeetings.app.data.basic.Fieldmanagment;
import org.openmeetings.app.hibernate.beans.lang.Fieldlanguagesvalues;

public class ResetPasswordTemplate extends VelocityLoader{
	
	private static final String tamplateName = "resetPass.vm";

	private static final Logger log = LoggerFactory.getLogger(FeedbackTemplate.class);

	private ResetPasswordTemplate() {
		super();
	}

	private static ResetPasswordTemplate instance = null;

	public static synchronized ResetPasswordTemplate getInstance() {
		if (instance == null) {
			instance = new ResetPasswordTemplate();
		}
		return instance;
	}

	public String getResetPasswordTemplate(String reset_link, Long default_lang_id){
        try {
        	
        	Fieldlanguagesvalues labelid513 = Fieldmanagment.getInstance().getFieldByIdAndLanguage(new Long(513), default_lang_id);
        	Fieldlanguagesvalues labelid514 = Fieldmanagment.getInstance().getFieldByIdAndLanguage(new Long(514), default_lang_id);
        	Fieldlanguagesvalues labelid515 = Fieldmanagment.getInstance().getFieldByIdAndLanguage(new Long(515), default_lang_id);
        	Fieldlanguagesvalues labelid516 = Fieldmanagment.getInstance().getFieldByIdAndLanguage(new Long(516), default_lang_id);
        	
	        /* lets make a Context and put data into it */
	        VelocityContext context = new VelocityContext();
	        context.put("reset_link", reset_link);
	        context.put("reset_link2", reset_link);
	        context.put("labelid513", labelid513.getValue());
	        context.put("labelid514", labelid514.getValue());
	        context.put("labelid515", labelid515.getValue());
	        context.put("labelid516", labelid516.getValue());
	
	        /* lets render a template */
	        StringWriter w = new StringWriter();
            Velocity.mergeTemplate(tamplateName, "UTF-8", context, w );
            
            return w.toString();         
            
        } catch (Exception e ) {
        	log.error("Problem merging template : " , e );
            //System.out.println("Problem merging template : " + e );
        }
        return null;
	}

}
