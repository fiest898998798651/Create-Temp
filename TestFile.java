import javax.mail.*;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.AndTerm;
import javax.mail.search.ReceivedDateTerm;
import javax.mail.search.SearchTerm;
import javax.mail.search.SubjectTerm;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Calendar;

public class EmailReaderAndDeleter {

    public static void main(String[] args) {
        String host = "imap.gmail.com"; // IMAP server
        String username = "your-email@gmail.com"; // Your email
        String password = "your-password"; // Your password

        Properties properties = new Properties();
        properties.put("mail.store.protocol", "imaps");
        properties.put("mail.imap.host", host);
        properties.put("mail.imap.port", "993");
        properties.put("mail.imap.starttls.enable", "true");

        try {
            Session session = Session.getDefaultInstance(properties, null);
            Store store = session.getStore("imaps");
            store.connect(host, username, password);

            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_WRITE);

            // Create SearchTerm for today's emails
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            Date today = cal.getTime();

            SearchTerm dateTerm = new ReceivedDateTerm(ComparisonTerm.EQ, today);

            // Get today's messages
            Message[] messages = inbox.search(dateTerm);
            System.out.println("Total Messages for Today: " + messages.length);

            // Loop through today's messages
            for (Message message : messages) {
                System.out.println("Email Subject: " + message.getSubject());

                // Check if message has an attachment
                if (message.isMimeType("multipart/*")) {
                    Multipart multipart = (Multipart) message.getContent();

                    for (int i = 0; i < multipart.getCount(); i++) {
                        BodyPart bodyPart = multipart.getBodyPart(i);

                        // Check if the part is a file attachment
                        if (Part.ATTACHMENT.equalsIgnoreCase(bodyPart.getDisposition())) {
                            String fileName = bodyPart.getFileName();
                            System.out.println("Attachment found: " + fileName);

                            // Mark the message for deletion
                            message.setFlag(Flags.Flag.DELETED, true);
                        }
                    }
                }
            }

            // Close the folder and expunge the deleted messages
            inbox.close(true); // 'true' to expunge
            store.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
