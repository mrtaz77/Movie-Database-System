package system.moviedatabase;

public class StringRectifier {
    public static String  rectify(String string) {
        StringBuilder stringBuilder = new StringBuilder(string);
        for(int i = 0; i< stringBuilder.length(); i++) {
            if(i==0 || stringBuilder.charAt(i-1)==' ')stringBuilder.replace(i,i+1,stringBuilder.substring(i,i+1).toUpperCase());
            else stringBuilder.replace(i,i+1,stringBuilder.substring(i,i+1).toLowerCase());
        }
        return String.valueOf(stringBuilder);
    }
}
