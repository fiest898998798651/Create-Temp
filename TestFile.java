Test
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        // The input date string
        String aa = "Tue Sep 24 10:58:04 ICT 2024";
        
        // Create a SimpleDateFormat for parsing the input date string
        SimpleDateFormat inputFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
        
        // Create a SimpleDateFormat for formatting the date in "DD/MM/YYYY" format
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
        
        try {
            // Parse the input date string into a Date object
            Date date = inputFormat.parse(aa);
            
            // Format the Date object into the desired output format
            String formattedDate = outputFormat.format(date);
            
            // Output the formatted date
            System.out.println(formattedDate);  // Output: 24/09/2024
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
