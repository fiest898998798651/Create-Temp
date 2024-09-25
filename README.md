import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

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
            inbox.open(Folder.READ_WRITE); // Open folder in read-write mode

            Message[] messages = inbox.getMessages();
            System.out.println("Total Messages: " + messages.length);

            for (Message message : messages) {
                System.out.println("Email Subject: " + message.getSubject());
                message.setFlag(Flags.Flag.DELETED, true); // Mark message for deletion
            }

            // Close folder and expunge deleted messages
            inbox.close(true); // 'true' to expunge
            store.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
