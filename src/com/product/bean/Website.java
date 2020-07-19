package com.product.bean;

/**
 * 网站信息-实体类
 * 
 * @author ymc
 */
public class Website extends BaseBean {

	private static final long serialVersionUID = 1L;
	
	/** 系统缓存(网站信息) */
	public static final String DEFAULT_WEBSITE_KEY = "website";
	
    /**
     * 网站名称
     */
    private String websiteName;

    /**
     * 网站网址
     */
    private String websiteDomain;

    /**
     * 关键字词
     */
    private String websiteKeywords;

    /**
     * 网站描述
     */
    private String websiteDescription;

    /**
     * 网站备案
     */
    private String websiteIcp;

    /**
     * 网站传真
     */
    private String websiteFaxes;

    /**
     * 网站电话
     */
    private String websiteTel;

    /**
     * 咨询热线
     */
    private String websiteHotline;

    /**
     * 网站地址
     */
    private String websiteAddress;

    /**
     * 网站协议
     */
    private String websiteProtocol;

    /**
     * 网站图片
     */
    private String websiteLogo;

    /**
     * 网站配置
     */
    private String websiteConfig;

    public String getWebsiteName() {
        return websiteName;
    }

    public void setWebsiteName(String websiteName) {
        this.websiteName = websiteName == null ? null : websiteName.trim();
    }

    public String getWebsiteDomain() {
        return websiteDomain;
    }

    public void setWebsiteDomain(String websiteDomain) {
        this.websiteDomain = websiteDomain == null ? null : websiteDomain.trim();
    }

    public String getWebsiteKeywords() {
        return websiteKeywords;
    }

    public void setWebsiteKeywords(String websiteKeywords) {
        this.websiteKeywords = websiteKeywords == null ? null : websiteKeywords.trim();
    }

    public String getWebsiteDescription() {
        return websiteDescription;
    }

    public void setWebsiteDescription(String websiteDescription) {
        this.websiteDescription = websiteDescription == null ? null : websiteDescription.trim();
    }

    public String getWebsiteIcp() {
        return websiteIcp;
    }

    public void setWebsiteIcp(String websiteIcp) {
        this.websiteIcp = websiteIcp == null ? null : websiteIcp.trim();
    }

    public String getWebsiteFaxes() {
        return websiteFaxes;
    }

    public void setWebsiteFaxes(String websiteFaxes) {
        this.websiteFaxes = websiteFaxes == null ? null : websiteFaxes.trim();
    }

    public String getWebsiteTel() {
        return websiteTel;
    }

    public void setWebsiteTel(String websiteTel) {
        this.websiteTel = websiteTel == null ? null : websiteTel.trim();
    }

    public String getWebsiteHotline() {
        return websiteHotline;
    }

    public void setWebsiteHotline(String websiteHotline) {
        this.websiteHotline = websiteHotline == null ? null : websiteHotline.trim();
    }

    public String getWebsiteAddress() {
        return websiteAddress;
    }

    public void setWebsiteAddress(String websiteAddress) {
        this.websiteAddress = websiteAddress == null ? null : websiteAddress.trim();
    }

    public String getWebsiteProtocol() {
        return websiteProtocol;
    }

    public void setWebsiteProtocol(String websiteProtocol) {
        this.websiteProtocol = websiteProtocol == null ? null : websiteProtocol.trim();
    }

    public String getWebsiteLogo() {
        return websiteLogo;
    }

    public void setWebsiteLogo(String websiteLogo) {
        this.websiteLogo = websiteLogo == null ? null : websiteLogo.trim();
    }

    public String getWebsiteConfig() {
        return websiteConfig;
    }

    public void setWebsiteConfig(String websiteConfig) {
        this.websiteConfig = websiteConfig == null ? null : websiteConfig.trim();
    }
}