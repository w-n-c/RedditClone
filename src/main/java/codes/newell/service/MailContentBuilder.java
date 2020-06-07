package codes.newell.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


@Service
public class MailContentBuilder {

	private final TemplateEngine engine;
	
	@Autowired
	public MailContentBuilder(TemplateEngine engine) {
		this.engine = engine;
	}
	
	String build(String message) {
		Context context = new Context();
		context.setVariable("message", message);
		// use the mailTemplate with the provided context to build the message.
		return engine.process("mailTemplate", context);
	}
	

}
