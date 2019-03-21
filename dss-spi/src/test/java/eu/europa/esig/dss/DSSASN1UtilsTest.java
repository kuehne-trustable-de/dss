/**
 * DSS - Digital Signature Services
 * Copyright (C) 2015 European Commission, provided under the CEF programme
 * 
 * This file is part of the "DSS - Digital Signature Services" project.
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
package eu.europa.esig.dss;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.cms.AttributeTable;
import org.bouncycastle.asn1.x509.IssuerSerial;
import org.bouncycastle.asn1.x509.qualified.ETSIQCObjectIdentifiers;
import org.bouncycastle.cert.X509CertificateHolder;
import org.junit.BeforeClass;
import org.junit.Test;

import eu.europa.esig.dss.utils.Utils;
import eu.europa.esig.dss.x509.CertificateToken;

public class DSSASN1UtilsTest {

	private static CertificateToken certificateWithAIA;

	private static CertificateToken certificateOCSP;

	@BeforeClass
	public static void init() {
		certificateWithAIA = DSSUtils.loadCertificate(new File("src/test/resources/TSP_Certificate_2014.crt"));
		assertNotNull(certificateWithAIA);

		certificateOCSP = DSSUtils.loadCertificateFromBase64EncodedString(
				"MIIEXjCCAkagAwIBAgILBAAAAAABWLd6HkYwDQYJKoZIhvcNAQELBQAwMzELMAkGA1UEBhMCQkUxEzARBgNVBAMTCkNpdGl6ZW4gQ0ExDzANBgNVBAUTBjIwMTYzMTAeFw0xNjEyMTAxMTAwMDBaFw0xODAxMjkxMTAwMDBaMC4xHzAdBgNVBAMTFkJlbGdpdW0gT0NTUCBSZXNwb25kZXIxCzAJBgNVBAYTAkJFMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAzD0B0c4gBx/wumeE2l/Wcz5FoMSUIuRNIySH2pJ3yfKR/u/FWCOzcrJvDMdmgzR33zGb4/fZel9YlI6xcN08Yd7GkP0/WtbHUhGUPERV76Vvyrk2K/EH/IG2gtxYB+7pkA/ZZycdyjc4IxHzBOiGofP9lDkPD05GSqI7MjVf6sNkZSnHcQSKwkaCGhAshJMjHzShEsSzOgX9kXceBFPTt6Hd2prVmnMTyAwURbQ6gFHbgfxB8JLMya95U6391nGQC66ScH1GhIwd9KSn+yBY0cazJ3nIrc8wd0yGYBgPK78jN3MvAsb1ydfs7kE+Wf95z9oRMiw62Glxh/ksLS/tTQIDAQABo3gwdjAOBgNVHQ8BAf8EBAMCB4AwHQYDVR0OBBYEFBgKRBywCTroyvAErr7p657558Y9MBMGA1UdJQQMMAoGCCsGAQUFBwMJMB8GA1UdIwQYMBaAFM6Al2fQrdlOxJlqgCcikM0RNRCHMA8GCSsGAQUFBzABBQQCBQAwDQYJKoZIhvcNAQELBQADggIBAFuZrqcwt23UiiJdRst66MEBRyKbgPsQM81Uq4FVrAnV8z3l8DDUv+A29KzCPO0GnHSatqA7DNhhMzoBRC42PqCpuvrj8VEWHd43AuPOLaikE04a5tVh6DgW8b00s6Yyf/PuDHCsg2C2MqY71MUR9GcnI7ngR2SyWQGpbsf/wfjujNxEB0+SOwMDTgIAikaueHGZbYkwvlRpL6wm2ENvrE8OvKt7NlNsaWJ4KtQo0QS5Ku+Y2BDA3bX+g8eNLQkaXTycgL4X3MyE5pBOl1OW3KOjJdfyLF+Sii+JKjNf8ZQWk0xvkBEI+nhCzDXhtKAcrkTKlXE25MiUnYoRsXkXgrzYftxAMxvFOXJji/hnX5Fe/3SBAHaE+jU6yC5nk6Q9ERii8mL0nHouMlZWSiAuXtlZDFrzwtLD2ITBECe4X60BDQfb/caO2u3HcWoG1AOvGxfQB0cMmP2njCdDf8UOqryiyky4t7Jj3ghOvETjWlwMw5ObhZ8yj8p6qFAt7+EVJfpUc1gDAolS/hJoLzohbL5LnCAnUAWsFpvG3qW1ky+X0MePXi6q/boqj2tcC4IDdsYS6RHPBvzl5+yLDccrGx1s/7vQYTMNyX0dYZzuxFZxx0bttWfjqLz3hFHlAEVmLCyUkSz761CbaT9u/G4tPP4Q8ApFfSskPI57lbLWIcwP");
		assertNotNull(certificateOCSP);
	}

	@Test
	public void getDigestSignaturePolicy() throws Exception {
		FileInputStream fis = new FileInputStream("src/test/resources/signature-policy-example.der");
		byte[] policyBytes = Utils.toByteArray(fis);
		Utils.closeQuietly(fis);

		byte[] signaturePolicyDigest = DSSASN1Utils.getAsn1SignaturePolicyDigest(DigestAlgorithm.SHA256, policyBytes);
		String hexSignaturePolicyDigest = Utils.toHex(signaturePolicyDigest);

		assertEquals("fe71e01aedd99f444238602d4e98f47bbab405c58c0e3811b9511dcd58c3c983", hexSignaturePolicyDigest);
	}

	@Test
	public void getCertificatePolicies() {
		List<CertificatePolicy> policyIdentifiers = DSSASN1Utils.getCertificatePolicies(certificateWithAIA);
		assertEquals(2, policyIdentifiers.size());
		CertificatePolicy certificatePolicy1 = policyIdentifiers.get(0);
		assertEquals("1.3.171.1.1.10.8.1", certificatePolicy1.getOid());
		assertEquals("https://repository.luxtrust.lu", certificatePolicy1.getCpsUrl());

		CertificatePolicy certificatePolicy2 = policyIdentifiers.get(1);
		assertEquals("0.4.0.2042.1.3", certificatePolicy2.getOid());
		assertNull(certificatePolicy2.getCpsUrl());
	}

	@Test
	public void getQCStatementsIdList() {
		List<String> qcStatementsIdList = DSSASN1Utils.getQCStatementsIdList(certificateWithAIA);
		assertTrue(Utils.isCollectionEmpty(qcStatementsIdList));

		CertificateToken certificate = DSSUtils.loadCertificate(new File("src/test/resources/ec.europa.eu.crt"));
		qcStatementsIdList = DSSASN1Utils.getQCStatementsIdList(certificate);
		assertTrue(Utils.isCollectionNotEmpty(qcStatementsIdList));
		assertTrue(qcStatementsIdList.contains(ETSIQCObjectIdentifiers.id_etsi_qcs_LimiteValue.getId()));
	}

	@Test
	public void getSKI() {
		byte[] ski = DSSASN1Utils.getSki(certificateWithAIA);
		assertEquals("4c4c4cfcacace6bb", Utils.toHex(ski));

		CertificateToken certNoSKIextension = DSSUtils.loadCertificateFromBase64EncodedString(
				"MIICaDCCAdSgAwIBAgIDDIOqMAoGBiskAwMBAgUAMG8xCzAJBgNVBAYTAkRFMT0wOwYDVQQKFDRSZWd1bGllcnVuZ3NiZWjIb3JkZSBmyHVyIFRlbGVrb21tdW5pa2F0aW9uIHVuZCBQb3N0MSEwDAYHAoIGAQoHFBMBMTARBgNVBAMUCjVSLUNBIDE6UE4wIhgPMjAwMDAzMjIwODU1NTFaGA8yMDA1MDMyMjA4NTU1MVowbzELMAkGA1UEBhMCREUxPTA7BgNVBAoUNFJlZ3VsaWVydW5nc2JlaMhvcmRlIGbIdXIgVGVsZWtvbW11bmlrYXRpb24gdW5kIFBvc3QxITAMBgcCggYBCgcUEwExMBEGA1UEAxQKNVItQ0EgMTpQTjCBoTANBgkqhkiG9w0BAQEFAAOBjwAwgYsCgYEAih5BUycfBpqKhU8RDsaSvV5AtzWeXQRColL9CH3t0DKnhjKAlJ8iccFtJNv+d3bh8bb9sh0maRSo647xP7hsHTjKgTE4zM5BYNfXvST79OtcMgAzrnDiGjQIIWv8xbfV1MqxxdtZJygrwzRMb9jGCAGoJEymoyzAMNG7tSdBWnUCBQDAAAABoxIwEDAOBgNVHQ8BAf8EBAMCAQYwCgYGKyQDAwECBQADgYEAOaK8ihVSBUcL2IdVBxZYYUKwMz5m7H3zqhN8W9w+iafWudH6b+aahkbENEwzg3C3v5g8nze7v7ssacQze657LHjP+e7ksUDIgcS4R1pU2eN16bjSP/qGPF3rhrIEHoK5nJULkjkZYTtNiOvmQ/+G70TXDi3Os/TwLlWRvu+7YLM=");
		assertNull(DSSASN1Utils.getSki(certNoSKIextension));

		assertNull(DSSASN1Utils.getSki(certNoSKIextension, false));
		assertNotNull(DSSASN1Utils.getSki(certNoSKIextension, true));

		CertificateToken c1 = DSSUtils.loadCertificateFromBase64EncodedString(
				"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");
		CertificateToken c2 = DSSUtils.loadCertificateFromBase64EncodedString(
				"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");

		byte[] skiC1 = DSSASN1Utils.getSki(c1);
		assertNotNull(skiC1);
		byte[] skiC2 = DSSASN1Utils.getSki(c2);
		assertNotNull(skiC2);

		assertFalse(Arrays.equals(skiC1, skiC2));

		byte[] encodedPKC1 = c1.getPublicKey().getEncoded();
		byte[] encodedPKC2 = c2.getPublicKey().getEncoded();

		assertArrayEquals(encodedPKC1, encodedPKC2);
		
		byte[] fixedSkiC1 = DSSASN1Utils.computeSkiFromCert(c1);
		byte[] fixedSkiC2 = DSSASN1Utils.computeSkiFromCert(c2);
		
		assertArrayEquals(fixedSkiC1, fixedSkiC2);
	}

	@Test
	public void getAccessLocation() {
		CertificateToken certificate = DSSUtils.loadCertificate(new File("src/test/resources/ec.europa.eu.crt"));
		List<String> ocspAccessLocations = DSSASN1Utils.getOCSPAccessLocations(certificate);
		assertEquals(1, Utils.collectionSize(ocspAccessLocations));
		assertEquals("http://ocsp.luxtrust.lu", ocspAccessLocations.get(0));
	}

	@Test
	public void getCAAccessLocations() {
		CertificateToken certificate = DSSUtils.loadCertificate(new File("src/test/resources/ec.europa.eu.crt"));
		List<String> caLocations = DSSASN1Utils.getCAAccessLocations(certificate);
		assertEquals(1, Utils.collectionSize(caLocations));
		assertEquals("http://ca.luxtrust.lu/LTQCA.crt", caLocations.get(0));
	}

	@Test
	public void getCrlUrls() {
		CertificateToken certificate = DSSUtils.loadCertificate(new File("src/test/resources/ec.europa.eu.crt"));
		List<String> crlUrls = DSSASN1Utils.getCrlUrls(certificate);
		assertEquals(1, Utils.collectionSize(crlUrls));
		assertEquals("http://crl.luxtrust.lu/LTQCA.crl", crlUrls.get(0));
	}

	@Test
	public void getCertificateHolder() {
		CertificateToken token = DSSUtils.loadCertificate(new File("src/test/resources/ec.europa.eu.crt"));
		X509CertificateHolder certificateHolder = DSSASN1Utils.getX509CertificateHolder(token);
		assertNotNull(certificateHolder);
		CertificateToken token2 = DSSASN1Utils.getCertificate(certificateHolder);
		assertEquals(token, token2);
	}

	@Test
	public void getUtf8String() {
		assertNotNull(DSSASN1Utils.getUtf8String(certificateWithAIA.getSubjectX500Principal()));
		assertNotNull(DSSASN1Utils.getUtf8String(certificateWithAIA.getIssuerX500Principal()));
	}

	@Test
	public void getSubjectCommonName() {
		assertEquals("tts.luxtrust.lu", DSSASN1Utils.getSubjectCommonName(certificateWithAIA));
	}

	@Test
	public void getHumanReadableName() {
		assertEquals("tts.luxtrust.lu", DSSASN1Utils.getHumanReadableName(certificateWithAIA));
	}

	@Test
	public void getIssuerSerial() {
		IssuerSerial issuerSerial = DSSASN1Utils.getIssuerSerial(certificateWithAIA);
		assertNotNull(issuerSerial);
		assertNotNull(issuerSerial.getIssuer());
		assertNotNull(issuerSerial.getSerial());
	}

	@Test
	public void isOCSPSigning() {
		assertTrue(DSSASN1Utils.isOCSPSigning(certificateOCSP));
		assertFalse(DSSASN1Utils.isOCSPSigning(certificateWithAIA));
	}

	@Test
	public void hasIdPkixOcspNoCheckExtension() {
		assertTrue(DSSASN1Utils.hasIdPkixOcspNoCheckExtension(certificateOCSP));
		assertFalse(DSSASN1Utils.hasIdPkixOcspNoCheckExtension(certificateWithAIA));
	}

	@Test
	public void getAlgorithmIdentifier() {
		assertNotNull(DSSASN1Utils.getAlgorithmIdentifier(DigestAlgorithm.SHA256));
	}

	@Test
	public void isEmpty() {
		assertTrue(DSSASN1Utils.isEmpty(null));
		assertTrue(DSSASN1Utils.isEmpty(new AttributeTable(new Hashtable<>())));
		Hashtable<ASN1ObjectIdentifier, Object> nonEmpty = new Hashtable<ASN1ObjectIdentifier, Object>();
		nonEmpty.put(new ASN1ObjectIdentifier("1.2.3.4.5"), 4);
		assertFalse(DSSASN1Utils.isEmpty(new AttributeTable(nonEmpty)));
	}

	@Test
	public void emptyIfNull() {
		assertNotNull(DSSASN1Utils.emptyIfNull(null));

		Hashtable<ASN1ObjectIdentifier, Object> nonEmpty = new Hashtable<ASN1ObjectIdentifier, Object>();
		nonEmpty.put(new ASN1ObjectIdentifier("1.2.3.4.5"), 4);
		AttributeTable attributeTable = new AttributeTable(nonEmpty);

		AttributeTable emptyIfNull = DSSASN1Utils.emptyIfNull(attributeTable);
		assertNotNull(emptyIfNull);
		assertEquals(attributeTable, emptyIfNull);
	}

	@Test
	public void readOCSPAccessLocationsAndStopOnceLoopDetected() {
		CertificateToken caTokenA = DSSUtils.loadCertificateFromBase64EncodedString("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");
		CertificateToken caTokenB = DSSUtils.loadCertificateFromBase64EncodedString("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");
		assertTrue(caTokenA.isSignedBy(caTokenB));
		assertTrue(caTokenB.isSignedBy(caTokenA));
		List<String> ocspAccessLocations = DSSASN1Utils.getOCSPAccessLocations(caTokenA);
		assertNotNull(ocspAccessLocations);
	}
	
	@Test
	public void getIssuerInfo() {
		String issuerV2base64 = "MFYwUaRPME0xEDAOBgNVBAMMB2dvb2QtY2ExGTAXBgNVBAoMEE5vd2luYSBTb2x1dGlvbnMxETAPBgNVBAsMCFBLSS1URVNUMQswCQYDVQQGEwJMVQIBCg==";
		IssuerSerialInfo issuerInfo = DSSASN1Utils.getIssuerInfo(Utils.fromBase64(issuerV2base64));
		assertNotNull(issuerInfo);
		assertNotNull(issuerInfo.getIssuerName());
		assertNotNull(issuerInfo.getSerialNumber());
	}

}
