package codes.newell.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import codes.newell.exceptions.SpringRedditException;
import codes.newell.model.NotificationEmail;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MailService {

	private final JavaMailSender sender;
	private final MailContentBuilder builder;

	@Autowired
	public MailService(JavaMailSender sender, MailContentBuilder builder) {
		this.sender = sender;
		this.builder = builder;
	}

	@Async
	public void sendMail(NotificationEmail email) {
		MimeMessagePreparator preparator = message -> {
			MimeMessageHelper helper = new MimeMessageHelper(message);
			helper.setFrom("springreddit@email.com");
			helper.setTo(email.getRecipient());
			helper.setSubject(email.getSubject());
			helper.setText(builder.build(email.getBody()));
		};
		try {
			sender.send(preparator);
			log.info("Activation email sent");
		} catch (MailException e) {
			throw new SpringRedditException("Exception occurred when sending mail to " + email.getRecipient(), e);
		}
	}
}
