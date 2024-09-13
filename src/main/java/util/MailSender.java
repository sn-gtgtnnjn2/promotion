package util;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSender {
    public static void sendMessege(String content) {
        // プロパティの設定
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // 認証情報の設定
        final String username = "saki.aacgcaaaggca@gmail.com";
        final String password = "xzsk qdkl dyhs pcrk";

        // セッションの作成
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // メールの作成
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("elecoms17@yahoo.co.jp"));
            message.setSubject("Test Mail");
            message.setText("This is a test mail from JavaMail API." + content);

            // メールの送信
            Transport.send(message);

            System.out.println("メールが送信されました。");

        } catch (MessagingException e) {
        	e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
