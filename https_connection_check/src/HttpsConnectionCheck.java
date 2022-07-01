import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.*;
import java.security.cert.CertificateException;

public class HttpsConnectionCheck {

    public static void main(String[] args) {
        String url = args[0];
        String trustStoreFile = args[1];
        String trustStorePassword = args[2];
        String httpUsername = args[3];
        String httpPassword = args[4];

        System.out.println("attempt to connect to: " + url);
        try (FileInputStream truststoreFile = new FileInputStream(trustStoreFile)) {
            initSslContext(trustStorePassword, truststoreFile);
        } catch (NoSuchAlgorithmException | KeyStoreException | CertificateException | IOException ex) {
            System.err.println("can't read properly data from remote host:" + ex.getMessage());
            System.exit(1);
        } catch (KeyManagementException ex) {
            System.err.println("key management exception :" + ex.getMessage());
            System.exit(2);
        }

        HttpURLConnection urlConnection = null;
        try {
            URL httpsUrl = new URL(url);
            String encoding = new sun.misc.BASE64Encoder().encode((httpUsername + ":" + httpPassword).getBytes());
            urlConnection = (HttpURLConnection) httpsUrl.openConnection();
            urlConnection.setRequestProperty("Authorization", "Basic " + encoding);
            urlConnection.connect();

            System.out.println(urlConnection.getResponseCode());
        } catch (MalformedURLException ex) {
            System.err.println("wrong url :" + ex.getMessage());
            System.exit(3);
        } catch (IOException ex) {
            System.err.println("input/output exception: :" + ex.getMessage());
            System.exit(4);
        } finally {
            urlConnection.disconnect();
        }
    }

    private static void initSslContext(String trustStorePassword, FileInputStream truststoreFile) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException, CertificateException {
        SSLContext.getInstance("TLSv1.2").init(new KeyManager[]{}, getTrustManagers(getKeyStore(trustStorePassword, truststoreFile)), new SecureRandom());
    }

    private static KeyStore getKeyStore(String trustStorePassword, FileInputStream truststoreFile) throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException {
        KeyStore truststore = KeyStore.getInstance(KeyStore.getDefaultType());
        truststore.load(truststoreFile, trustStorePassword.toCharArray());
        return truststore;
    }

    private static TrustManager[] getTrustManagers(KeyStore truststore) throws NoSuchAlgorithmException, KeyStoreException {
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(truststore);
        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
        return trustManagers;
    }
}
