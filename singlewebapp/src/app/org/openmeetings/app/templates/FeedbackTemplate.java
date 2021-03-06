package org.openmeetings.app.templates;

import java.io.StringWriter;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.openmeetings.app.remote.red5.ScopeApplicationAdapter;
import org.red5.logging.Red5LoggerFactory;
import org.slf4j.Logger;

public class FeedbackTemplate extends VelocityLoader {

	private static final String tamplateName = "feedback.vm";

	private static final Logger log = Red5LoggerFactory.getLogger(
			FeedbackTemplate.class, ScopeApplicationAdapter.webAppRootKey);

	public String getFeedBackTemplate(String username, String email,
			String message, Integer default_lang_id) {
		try {

			super.init();

			// TODO: Finish Feedback - Template
			// Fieldlanguagesvalues fValue =
			// fieldmanagment.getFieldByIdAndLanguage(new Long(499), new
			// Long(default_lang_id));

			/* lets make a Context and put data into it */

			VelocityContext context = new VelocityContext();

			context.put("username", username);
			context.put("email", email);
			context.put("message", message);

			/* lets render a template */

			StringWriter w = new StringWriter();
			Velocity.mergeTemplate(tamplateName, "UTF-8", context, w);

			return w.toString();

		} catch (Exception e) {
			log.error("Problem merging template : ", e);
			// System.out.println("Problem merging template : " + e );
		}
		return null;
	}
}
