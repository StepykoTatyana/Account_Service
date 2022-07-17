//package com.example.Account_Service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.thymeleaf.spring5.SpringTemplateEngine;
//
//@Service
//public class DefaultEmailService implements EmailService{
//
//    @Autowired
//    private JavaMailSender emailSender;
//
//    @Autowired
//    private SpringTemplateEngine templateEngine;
//
//    @Override
//    public void sendMail(AbstractEmailContext email) throws MessagingException {
//        MimeMessage message = emailSender.createMimeMessage();
//        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message,
//                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
//                StandardCharsets.UTF_8.name());
//        Context context = new Context();
//        context.setVariables(email.getContext());
//        String emailContent = templateEngine.process(email.getTemplateLocation(), context);
//
//        mimeMessageHelper.setTo(email.getTo());
//        mimeMessageHelper.setSubject(email.getSubject());
//        mimeMessageHelper.setFrom(email.getFrom());
//        mimeMessageHelper.setText(emailContent, true);
//        emailSender.send(message);
//    }
//}