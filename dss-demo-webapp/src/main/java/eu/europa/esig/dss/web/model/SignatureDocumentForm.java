package eu.europa.esig.dss.web.model;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import eu.europa.esig.dss.DigestAlgorithm;
import eu.europa.esig.dss.EncryptionAlgorithm;
import eu.europa.esig.dss.SignatureForm;
import eu.europa.esig.dss.SignatureLevel;
import eu.europa.esig.dss.SignaturePackaging;
import eu.europa.esig.dss.SignatureTokenType;

public class SignatureDocumentForm {

	private Date signingDate;

	private MultipartFile documentToSign;

	@NotNull(message = "{error.signature.form.mandatory}")
	private SignatureForm signatureForm;

	@NotNull(message = "{error.signature.packaging.mandatory}")
	private SignaturePackaging signaturePackaging;

	@NotNull(message = "{error.signature.level.mandatory}")
	private SignatureLevel signatureLevel;

	@NotNull(message = "{error.digest.algo.mandatory}")
	private DigestAlgorithm digestAlgorithm;

	@NotNull(message = "{error.token.type.mandatory}")
	private SignatureTokenType token;

	private String pkcsPath;

	private MultipartFile pkcsFile;

	private String pkcsPassword;

	private String base64Certificate;

	private List<String> base64CertificateChain;

	private EncryptionAlgorithm encryptionAlgorithm;

	private String base64SignatureValue;

	// TODO claimedRoles, policy

	public Date getSigningDate() {
		return signingDate;
	}

	public void setSigningDate(Date signingDate) {
		this.signingDate = signingDate;
	}

	public MultipartFile getDocumentToSign() {
		return documentToSign;
	}

	public void setDocumentToSign(MultipartFile documentToSign) {
		this.documentToSign = documentToSign;
	}

	public SignatureForm getSignatureForm() {
		return signatureForm;
	}

	public void setSignatureForm(SignatureForm signatureForm) {
		this.signatureForm = signatureForm;
	}

	public SignaturePackaging getSignaturePackaging() {
		return signaturePackaging;
	}

	public void setSignaturePackaging(SignaturePackaging signaturePackaging) {
		this.signaturePackaging = signaturePackaging;
	}

	public SignatureLevel getSignatureLevel() {
		return signatureLevel;
	}

	public void setSignatureLevel(SignatureLevel signatureLevel) {
		this.signatureLevel = signatureLevel;
	}

	public DigestAlgorithm getDigestAlgorithm() {
		return digestAlgorithm;
	}

	public void setDigestAlgorithm(DigestAlgorithm digestAlgorithm) {
		this.digestAlgorithm = digestAlgorithm;
	}

	public SignatureTokenType getToken() {
		return token;
	}

	public void setToken(SignatureTokenType token) {
		this.token = token;
	}

	public String getBase64Certificate() {
		return base64Certificate;
	}

	public void setBase64Certificate(String base64Certificate) {
		this.base64Certificate = base64Certificate;
	}

	public List<String> getBase64CertificateChain() {
		return base64CertificateChain;
	}

	public void setBase64CertificateChain(List<String> base64CertificateChain) {
		this.base64CertificateChain = base64CertificateChain;
	}

	public EncryptionAlgorithm getEncryptionAlgorithm() {
		return encryptionAlgorithm;
	}

	public void setEncryptionAlgorithm(EncryptionAlgorithm encryptionAlgorithm) {
		this.encryptionAlgorithm = encryptionAlgorithm;
	}

	public String getBase64SignatureValue() {
		return base64SignatureValue;
	}

	public void setBase64SignatureValue(String base64SignatureValue) {
		this.base64SignatureValue = base64SignatureValue;
	}

	public String getPkcsPath() {
		return pkcsPath;
	}

	public void setPkcsPath(String pkcsPath) {
		this.pkcsPath = pkcsPath;
	}

	public MultipartFile getPkcsFile() {
		return pkcsFile;
	}

	public void setPkcsFile(MultipartFile pkcsFile) {
		this.pkcsFile = pkcsFile;
	}

	public String getPkcsPassword() {
		return pkcsPassword;
	}

	public void setPkcsPassword(String pkcsPassword) {
		this.pkcsPassword = pkcsPassword;
	}

	@AssertTrue(message = "{error.to.sign.file.mandatory}")
	public boolean isDocumentToSign() {
		return (documentToSign != null) && (!documentToSign.isEmpty());
	}

	@AssertTrue(message = "{error.file.mandatory}")
	public boolean isPkcsFile() {
		if (SignatureTokenType.PKCS12.equals(token)) {
			return (pkcsFile != null) && (!pkcsFile.isEmpty());
		} else {
			return true;
		}
	}

	@AssertTrue(message = "{error.path.mandatory}")
	public boolean isPkcsPathValid() {
		if (SignatureTokenType.PKCS11.equals(token)) {
			return StringUtils.isNotEmpty(pkcsPath);
		} else {
			return true;
		}
	}

	@AssertTrue(message = "{error.password.mandatory}")
	public boolean isPkcsPasswordValid() {
		if (SignatureTokenType.PKCS11.equals(token) || SignatureTokenType.PKCS12.equals(token)) {
			return StringUtils.isNotEmpty(pkcsPassword);
		} else {
			return true;
		}
	}

}
