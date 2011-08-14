package org.openmeetings.app.templates;

import java.io.StringWriter;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.openmeetings.app.data.basic.Fieldmanagment;
import org.openmeetings.app.persistence.beans.lang.Fieldlanguagesvalues;
import org.openmeetings.app.remote.red5.ScopeApplicationAdapter;
import org.red5.logging.Red5LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public class RegisterUserTemplate extends VelocityLoader {

	@Autowired
	private Fieldmanagment fieldmanagment;

	private static final String templateName = "register_mail.vm";

	private static final String templateNameVerification = "register_verification_mail.vm";

	private static final Logger log = Red5LoggerFactory.getLogger(
			RegisterUserTemplate.class, ScopeApplicationAdapter.webAppRootKey);

	public String getRegisterUserWithVerificationTemplate(String username,
			String userpass, String email, Long default_lang_id,
			String verification_url) {
		try {

			super.init();

			Fieldlanguagesvalues labelid506 = fieldmanagment
					.getFieldByIdAndLanguage(new Long(506), default_lang_id);
			Fieldlanguagesvalues labelid507 = fieldmanagment
					.getFieldByIdAndLanguage(new Long(507), default_lang_id);
			Fieldlanguagesvalues labelid508 = fieldmanagment
					.getFieldByIdAndLanguage(new Long(508), default_lang_id);
			Fieldlanguagesvalues labelid509 = fieldmanagment
					.getFieldByIdAndLanguage(new Long(509), default_lang_id);
			Fieldlanguagesvalues labelid510 = fieldmanagment
					.getFieldByIdAndLanguage(new Long(510), default_lang_id);
			Fieldlanguagesvalues labelid511 = fieldmanagment
					.getFieldByIdAndLanguage(new Long(511), default_lang_id);

			Fieldlanguagesvalues labelid667 = fieldmanagment
					.getFieldByIdAndLanguage(new Long(667), default_lang_id);
			Fieldlanguagesvalues labelid668 = fieldmanagment
					.getFieldByIdAndLanguage(new Long(668), default_lang_id);

			/* lets make a Context and put data into it */
			VelocityContext context = new VelocityContext();

			context.put("username", username);
			context.put("userpass", userpass);
			context.put("mail", email);
			context.put("verification_url", verification_url);
			context.put("labelid506", labelid506.getValue());
			context.put("labelid507", labelid507.getValue());
			context.put("labelid508", labelid508.getValue());
			context.put("labelid509", labelid509.getValue());
			context.put("labelid510", labelid510.getValue());
			context.put("labelid511", labelid511.getValue());
			context.put("labelid667", labelid667.getValue());
			context.put("labelid668", labelid668.getValue());

			/* lets render a template */

			StringWriter w = new StringWriter();
			Velocity.mergeTemplate(templateNameVerification, "UTF-8", context,
					w);

			// System.out.println(" template : " + w );

			return w.toString();

		} catch (Exception e) {
			log.error("Problem merging template : ", e);
			// System.out.println("Problem merging template : " + e );
		}
		return null;
	}

	public String getRegisterUserTemplate(String username, String userpass,
			String email, Long default_lang_id) {
		try {

			Fieldlanguagesvalues labelid506 = fieldmanagment
					.getFieldByIdAndLanguage(new Long(506), default_lang_id);
			Fieldlanguagesvalues labelid507 = fieldmanagment
					.getFieldByIdAndLanguage(new Long(507), default_lang_id);
			Fieldlanguagesvalues labelid508 = fieldmanagment
					.getFieldByIdAndLanguage(new Long(508), default_lang_id);
			Fieldlanguagesvalues labelid509 = fieldmanagment
					.getFieldByIdAndLanguage(new Long(509), default_lang_id);
			Fieldlanguagesvalues labelid510 = fieldmanagment
					.getFieldByIdAndLanguage(new Long(510), default_lang_id);
			Fieldlanguagesvalues labelid511 = fieldmanagment
					.getFieldByIdAndLanguage(new Long(511), default_lang_id);

			/* lets make a Context and put data into it */
			VelocityContext context = new VelocityContext();

			context.put("username", username);
			context.put("userpass", userpass);
			context.put("mail", email);
			context.put("labelid506", labelid506.getValue());
			context.put("labelid507", labelid507.getValue());
			context.put("labelid508", labelid508.getValue());
			context.put("labelid509", labelid509.getValue());
			context.put("labelid510", labelid510.getValue());
			context.put("labelid511", labelid511.getValue());

			/* lets render a template */

			StringWriter w = new StringWriter();
			Velocity.mergeTemplate(templateName, "UTF-8", context, w);

			// System.out.println(" template : " + w );

			return w.toString();

		} catch (Exception e) {
			log.error("Problem merging template : ", e);
			// System.out.println("Problem merging template : " + e );
		}
		return null;
	}
}
