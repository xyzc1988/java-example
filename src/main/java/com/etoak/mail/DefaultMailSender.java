package com.etoak.mail;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.velocity.VelocityEngineUtils;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Arrays;
import java.util.Map;

/**
 * Created on 2016/9/14.
 */
public class DefaultMailSender {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private JavaMailSender javaMailSender;
    private VelocityEngine velocityEngine;

    /**发件人*/
    private String from;
    /**收件人*/
    private String[] to;
    /**主题*/
    private String subject;
    /**正文模板文件编码*/
    private String tplEncoding;
    /**正文模板文件位置*/
    private String tplLocation;
    /**正文是否为html格式*/
    private boolean isHtml = false;
    /**正文模板参数*/
    private Map<String, Object> model;
    /**附件*/
    private Map<String, Object> attachments;
    /**富文本*/
    private Map<String, Object> inlines;

    /**回复人*/
    private String replyTo;
    /**抄送人*/
    private String[] cc;
    /**密送人*/
    private String[] bcc;

    /**发件人(InternetAddress型，可带昵称，优先于from)*/
    private InternetAddress fromAddr;
    /**收件人(InternetAddress型，可带昵称，优先于to)*/
    private InternetAddress[] toAddr;
    /**回复人(InternetAddress型，可带昵称，优先于replyTo)*/
    private InternetAddress replyToAddr;
    /**抄送人(InternetAddress型，可带昵称，优先于cc)*/
    private InternetAddress[] ccAddr;
    /**密送人(InternetAddress型，可带昵称，优先于bcc)*/
    private InternetAddress[] bccAddr;


    public boolean sendDefault(boolean multipart) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, multipart);
            if (fromAddr != null) {
                helper.setFrom(fromAddr);
            } else if (from != null) {
                helper.setFrom(from);
            }
            if (toAddr != null) {
                helper.setTo(toAddr);
            } else if (to != null) {
                helper.setTo(to);
            }
            if (replyToAddr != null) {
                helper.setReplyTo(replyToAddr);
            } else if (replyTo != null) {
                helper.setReplyTo(replyTo);
            }
            if (ccAddr != null) {
                helper.setCc(ccAddr);
            } else if (cc != null) {
                helper.setCc(cc);
            }
            if (bccAddr != null) {
                helper.setBcc(bccAddr);
            } else if (bcc != null) {
                helper.setBcc(bcc);
            }
            if (subject != null)
                helper.setSubject(subject);
            if (attachments != null && attachments.size() > 0) {
                for (Map.Entry<String, Object> attachment : attachments.entrySet()) {
                    Object value = attachment.getValue();
                    if (value instanceof File) {
                        helper.addAttachment(attachment.getKey(), (File) value);
                    } else if (value instanceof Resource) {
                        helper.addAttachment(attachment.getKey(), (Resource) value);
                    } else if (value instanceof byte[]) {
                        helper.addAttachment(attachment.getKey(), new ByteArrayResource((byte[]) value));
                    } else {
                        throw new RuntimeException(String.format("附件[%s]为不支持的类型！", attachment.getKey()));
                    }
                }
            }
            if (inlines != null && inlines.size() > 0) {
                for (Map.Entry<String, Object> inline : inlines.entrySet()) {
                    Object value = inline.getValue();
                    if (value instanceof File) {
                        helper.addInline(inline.getKey(), (File) value);
                    } else if (value instanceof Resource) {
                        helper.addInline(inline.getKey(), (Resource) value);
                    } else if (value instanceof byte[]) {
                        helper.addInline(inline.getKey(), new ByteArrayResource((byte[]) value));
                    } else {
                        throw new RuntimeException(String.format("富文本[%s]为不支持的类型！", inline.getKey()));
                    }
                }
            }

            helper.setText(getMessage(model), isHtml);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            LOGGER.error(String.format("邮件[%s]信息异常！", subject), e);
            return false;
        } catch (Exception e) {
            LOGGER.error(String.format("邮件[%s]发送失败！", subject), e);
            return false;
        }
        return true;
    }

    public boolean send(String from, String[] to, String subject, Map<String, Object> model) {
        this.setFrom(from).setTo(to).setSubject(subject).setModel(model);
        return sendDefault(false);
    }

    /**
     * 发送带附件的邮件
     * @param from
     * @param to
     * @param subject
     * @param model
     * @param attachments Map<附件名，附件> 其中附件的类型可为{@code java.io.File}或{@code org.springframework.core.io.Resource}或{@code byte[]}
     * @return
     */
    public boolean sendWithAttachment(String from, String[] to, String subject, Map<String, Object> model, Map<String, Object> attachments) {
        this.setFrom(from).setTo(to).setSubject(subject).setModel(model).setAttachments(attachments);
        return sendDefault(true);
    }

    /**
     * 发送带附件的邮件，收发件人地址用InternetAddress表示，可带昵称
     * @param from
     * @param to
     * @param subject
     * @param model
     * @param attachments Map<附件名，附件> 其中附件的类型可为{@code java.io.File}或{@code org.springframework.core.io.Resource}或{@code byte[]}
     * @return
     */
    public boolean sendWithAttachment(InternetAddress from, InternetAddress[] to, String subject, Map<String, Object> model, Map<String, Object> attachments) {
        this.setFromAddr(from).setToAddr(to).setSubject(subject).setModel(model).setAttachments(attachments);
        return sendDefault(true);
    }

    private String getMessage(Map<String, Object> model) throws VelocityException{
        return VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, tplLocation, tplEncoding, model);
    }

    public JavaMailSender getJavaMailSender() {
        return javaMailSender;
    }

    public DefaultMailSender setJavaMailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
        return this;
    }

    public VelocityEngine getVelocityEngine() {
        return velocityEngine;
    }

    public DefaultMailSender setVelocityEngine(VelocityEngine velocityEngine) {
        this.velocityEngine = velocityEngine;
        return this;
    }

    public String getFrom() {
        return from;
    }

    public DefaultMailSender setFrom(String from) {
        this.from = from;
        return this;
    }

    public String[] getTo() {
        return copyOf(to);
    }

    public DefaultMailSender setTo(String[] to) {
        this.to = copyOf(to);
        return this;
    }

    public String getSubject() {
        return subject;
    }

    public DefaultMailSender setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public String getTplEncoding() {
        return tplEncoding;
    }

    public DefaultMailSender setTplEncoding(String tplEncoding) {
        this.tplEncoding = tplEncoding;
        return this;
    }

    public String getTplLocation() {
        return tplLocation;
    }

    public DefaultMailSender setTplLocation(String tplLocation) {
        this.tplLocation = tplLocation;
        return this;
    }

    public boolean isHtml() {
        return isHtml;
    }

    public DefaultMailSender setHtml(boolean html) {
        isHtml = html;
        return this;
    }

    public Map<String, Object> getModel() {
        return model;
    }

    public DefaultMailSender setModel(Map<String, Object> model) {
        this.model = model;
        return this;
    }

    public String getReplyTo() {
        return replyTo;
    }

    public DefaultMailSender setReplyTo(String replyTo) {
        this.replyTo = replyTo;
        return this;
    }

    public String[] getCc() {
        return copyOf(cc);
    }

    public DefaultMailSender setCc(String[] cc) {
        this.cc = copyOf(cc);
        return this;
    }

    public String[] getBcc() {
        return copyOf(bcc);
    }

    public DefaultMailSender setBcc(String[] bcc) {
        this.bcc = copyOf(bcc);
        return this;
    }

    public Map<String, Object> getAttachments() {
        return attachments;
    }

    public DefaultMailSender setAttachments(Map<String, Object> attachments) {
        this.attachments = attachments;
        return this;
    }

    public Map<String, Object> getInlines() {
        return inlines;
    }

    public DefaultMailSender setInlines(Map<String, Object> inlines) {
        this.inlines = inlines;
        return this;
    }

    public InternetAddress getFromAddr() {
        return fromAddr;
    }

    public DefaultMailSender setFromAddr(InternetAddress fromAddr) {
        this.fromAddr = fromAddr;
        return this;
    }

    public InternetAddress[] getToAddr() {
        return copyOf(toAddr);
    }

    public DefaultMailSender setToAddr(InternetAddress[] toAddr) {
        this.toAddr = copyOf(toAddr);
        return this;
    }

    public InternetAddress getReplyToAddr() {
        return replyToAddr;
    }

    public DefaultMailSender setReplyToAddr(InternetAddress replyToAddr) {
        this.replyToAddr = replyToAddr;
        return this;
    }

    public InternetAddress[] getCcAddr() {
        return copyOf(ccAddr);
    }

    public DefaultMailSender setCcAddr(InternetAddress[] ccAddr) {
        this.ccAddr = copyOf(ccAddr);
        return this;
    }

    public InternetAddress[] getBccAddr() {
        return copyOf(bccAddr);
    }

    public DefaultMailSender setBccAddr(InternetAddress[] bccAddr) {
        this.bccAddr = copyOf(bccAddr);
        return this;
    }

    private <T> T[] copyOf(T[] array) {
        return array == null ? null : Arrays.copyOf(array, array.length);
    }
}
