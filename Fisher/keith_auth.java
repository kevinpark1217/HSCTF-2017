/*
 * Decompiled with CFR 0_121.
 *
 * Could not load the following classes:
 *  Keith
 */
import java.io.InputStream;
import java.io.PrintStream;
import java.security.MessageDigest;
import java.util.Scanner;

public class Keith {
    public String b(String string) {
        return string.substring(string.length() / 2) + string.substring(0, string.length() / 2);
    }

    public String a(String string) {
        String c = "";
        for (int i = 0; i < 1024; ++i) {
            string = this.b(string.substring(string.length() / 2)) + this.b(string.substring(0, string.length() / 2));
            c = c + string + "^";
        }
        return c;
    }

    public String h(byte[] bytes) {
        String text = "";
        for (byte b : bytes) {
            text = text + b + "^";
        }
        return text;
    }

    public String encrypt(String plain) {
        String text = this.a(plain);
        if (text.hashCode() == 1670400264) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                md.update(text.getBytes("UTF-8"));
                return this.h(md.digest());
            }
            catch (Exception md) {}
        } else if (text.hashCode() == -559038737) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                md.update("@@@@^*&&*@$#*&)&*@#&*$!&@#*@!^&%(*$%($#(%(*$%&(**&^$*&@(()!)@*&#$7#87".getBytes("UTF-8"));
                return this.h(md.digest());
            }
            catch (Exception md) {}
        } else if (text.hashCode() == -1163005939) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                md.update("*&W$&*57492374678&*#*&5&HUHGJFS*&Y$IU&*T#$*T%U(^*($Y%Y4hi".getBytes("UTF-8"));
                return this.h(md.digest());
            }
            catch (Exception md) {
                // empty catch block
            }
        }
        return "$*%(&*($@)*($#@*(&W^%*$(#)*&^@#$(#@$^&*$%(&*$#&*(%$#*(%&*$#";
    }

    public boolean check(String string) {
        return string.equals("-22^29^-11^-55^-12^22^82^53^-89^2^29^-65^37^116^63^27^-109^93^-3^-115^28^-40^1^109^-7^94^105^1^79^116^19^41^");
    }

    public static void main(String[] args) {
        System.out.print("I cannot tell you the flag but I'm more than happy to check if you have the right flag: ");
        Keith keith = new Keith();
        if (keith.check(keith.encrypt(new Scanner(System.in).nextLine()))) {
            System.out.println("Congrats! You have the flag!");
        } else {
            System.out.println("Er...You might want to check that over.");
        }
    }
}