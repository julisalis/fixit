package ar.com.utn.afip;

import ar.com.utn.afip.exceptions.AfipCertificadosException;
import ar.com.utn.afip.exceptions.AfipCmsException;
import ar.com.utn.afip.exceptions.AfipWsaaException;
import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import org.apache.axis.AxisFault;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.Base64;
import org.apache.axis.encoding.XMLType;
import org.bouncycastle.cms.CMSProcessable;
import org.bouncycastle.cms.CMSProcessableByteArray;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;

import javax.xml.rpc.ParameterMode;
import java.io.FileInputStream;
import java.io.Reader;
import java.io.StringReader;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.CertStore;
import java.security.cert.CollectionCertStoreParameters;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by jsalischiker on 16/06/17.
 */
public class Autenticador {

    public LoginTicketRequest buildLoginTicketRequest(AutenticadorConfig configuration) {
        PrivateKey pKey = null;
        X509Certificate pCertificate = null;
        byte [] asn1_cms = null;
        CertStore cstore = null;
        String loginTicketRequest_xml;
        String signerDN = null;

        //
        // Manage Keys & Certificates
        //
        try {
            // Create a keystore using keys from the pkcs#12 p12file
            KeyStore ks = KeyStore.getInstance("pkcs12");
            FileInputStream p12stream = new FileInputStream ( configuration.getP12Filename() ) ;
            ks.load(p12stream, configuration.getP12pass().toCharArray());
            p12stream.close();

            // Get Certificate & Private key from KeyStore
            pKey = (PrivateKey) ks.getKey(configuration.getSigner(), configuration.getP12pass().toCharArray());
            pCertificate = (X509Certificate)ks.getCertificate(configuration.getSigner());

            signerDN = pCertificate.getSubjectDN().toString();

            // Create a list of Certificates to include in the final CMS
            List<X509Certificate> certList = new ArrayList<X509Certificate>();
            certList.add(pCertificate);

            if (Security.getProvider("BC") == null) {
                Security.addProvider(new BouncyCastleProvider());
            }

            cstore = CertStore.getInstance("Collection", new CollectionCertStoreParameters(certList), "BC");
        }
        catch (Exception e) {
            throw new AfipCertificadosException(e.getMessage());
        }

        //
        // Create XML Message
        //
        loginTicketRequest_xml = createLoginTicketRequest(signerDN, configuration.getDstDN(), configuration.getService(), configuration.getTicketTime());

        //
        // Create CMS Message
        //
        try {
            // Create a new empty CMS Message
            CMSSignedDataGenerator gen = new CMSSignedDataGenerator();

            // Add a Signer to the Message
            gen.addSigner(pKey, pCertificate, CMSSignedDataGenerator.DIGEST_SHA1);

            // Add the Certificate to the Message
            gen.addCertificatesAndCRLs(cstore);

            // Add the data (XML) to the Message
            CMSProcessable data = new CMSProcessableByteArray(loginTicketRequest_xml.getBytes());

            // Add a Sign of the Data to the Message
            CMSSignedData signed = gen.generate(data, true, "BC");

            //
            asn1_cms = signed.getEncoded();
        }
        catch (Exception e) {
            throw new AfipCmsException(e.getMessage());
        }

        return new LoginTicketRequest(asn1_cms);

    }

    private String createLoginTicketRequest (String SignerDN, String dstDN, String service, Long TicketTime) {

        String LoginTicketRequest_xml;

        Date GenTime = new Date();
        GregorianCalendar gentime = new GregorianCalendar();
        GregorianCalendar exptime = new GregorianCalendar();
        String UniqueId = new Long(GenTime.getTime() / 1000).toString();

        exptime.setTime(new Date(GenTime.getTime()+TicketTime));

        XMLGregorianCalendarImpl XMLGenTime = new XMLGregorianCalendarImpl(gentime);
        XMLGregorianCalendarImpl XMLExpTime = new XMLGregorianCalendarImpl(exptime);

        LoginTicketRequest_xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
                +"<loginTicketRequest version=\"1.0\">"
                +"<header>"
                +"<source>" + SignerDN + "</source>"
                +"<destination>" + dstDN + "</destination>"
                +"<uniqueId>" + UniqueId + "</uniqueId>"
                +"<generationTime>" + XMLGenTime + "</generationTime>"
                +"<expirationTime>" + XMLExpTime + "</expirationTime>"
                +"</header>"
                +"<service>" + service + "</service>"
                +"</loginTicketRequest>";

        //System.out.println("TRA: " + LoginTicketRequest_xml);

        return (LoginTicketRequest_xml);
    }


    public LoginTicketResponse invokeWSAA(LoginTicketRequest request, String endpoint) {
        String loginTicketResponse = null;
        try {

            Service service = new Service();
            Call call = (Call) service.createCall();

            //
            // Prepare the call for the Web service
            //
            call.setTargetEndpointAddress( new java.net.URL(endpoint) );
            call.setOperationName("loginCms");
            call.addParameter( "request", XMLType.XSD_STRING, ParameterMode.IN );
            call.setReturnType( XMLType.XSD_STRING );

            //
            // Make the actual call and assgn the answer to a String
            //
            loginTicketResponse = (String) call.invoke(new Object [] {
                    Base64.encode (request.getData()) } );


        } catch (AxisFault af) {
            throw new AfipWsaaException(af.getFaultCode().getLocalPart(), af.getFaultReason());
        } catch (Exception e) {
            throw new AfipWsaaException("unknown.error",e.getMessage());
        }
        return new LoginTicketResponse(loginTicketResponse);
    }

    public TicketAcceso obtenerTA(LoginTicketResponse response) {
        // Get token & sign from LoginTicketResponse
        try {
            Reader tokenReader = new StringReader(response.getData());
            Document tokenDoc = new SAXReader(false).read(tokenReader);

            String token = tokenDoc.valueOf("/loginTicketResponse/credentials/token");
            String sign = tokenDoc.valueOf("/loginTicketResponse/credentials/sign");

            return new TicketAcceso(token, sign);
        } catch (Exception e) {
            throw new AfipWsaaException("obtain.token.sign",e.getMessage());
        }

    }
}
