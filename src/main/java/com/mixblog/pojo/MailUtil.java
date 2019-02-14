package com.mixblog.pojo;

import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailUtil {
	// �ʼ������ߵ�ַ
    private static final String SenderEmailAddr = "ldtutu@126.com";
    // �ʼ������������û�
    private static final String SMTPUserName = "ldtutu@126.com";
    // �ʼ���������������
    private static final String SMTPPassword = "Xxx990119";
    // �ʼ�����������SMTP������
    private static final String SMTPServerName = "smtp.126.com";
    // ��������
    private static final String TransportType = "smtp";
    // ����
    private static Properties props;
 
    private MailUtil() {}
 
    /*��̬������*/
    static {
        MailUtil.props = new Properties();
        // �洢�����ʼ�����������Ϣ
        MailUtil.props.put("mail.smtp.host", MailUtil.SMTPServerName);
        // ͬʱͨ����֤
        MailUtil.props.put("mail.smtp.auth", "true");
        MailUtil.props.put("mail.smtp.starttls.enable","true");
        //����Debug����
        MailUtil.props.setProperty("mail.debug","false");
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        props.setProperty("mail.smtp.starttls.enable", "false");
    }
    public static void activeUser(String to,String code) throws Exception {
        // 1.�������Ӷ���
        Session session = Session.getDefaultInstance(new Properties(), new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("user01@cxx.com", "123456");
            }
        });
        // 2.�����ʼ�����
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("user01@cxx.com"));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
        //����
        message.setSubject("��ӭ���ע��");
        //����
        message.setContent("<h1>��������ϵͳ�ļ����ʼ���������Ӽ����˺ţ�</h1><h3><a href='http://localhost:8081/active?code="+code+"'>http://localhost:8081/active?code="+code+"</a></h3>", "text/html;charset=utf-8");
        // 3.���ͼ����ʼ�
        Transport.send(message);
    }
 
    /**
     * �����ʼ�
     * @param emailAddr:�������ʼ���ַ
     * @param mailTitle:�ʼ�����
     * @param mailConcept:�ʼ�����
     */
    public static void sendMail(String emailAddr, String mailTitle, String mailConcept) {
        // ���������½�һ���ʼ��Ự��null������һ��Authenticator(��֤����) ����
        Session s = Session.getInstance(MailUtil.props, null);
        // ���õ��Ա�־,Ҫ�鿴�����ʼ��������ʼ���������ø÷���
        s.setDebug(false);
        // ���ʼ��Ự�½�һ����Ϣ����
        Message message = new MimeMessage(s);
        try {
            // ���÷�����
            Address from = new InternetAddress(MailUtil.SenderEmailAddr);
            message.setFrom(from);
 
            // �����ռ���
            Address to = new InternetAddress(emailAddr);
            message.setRecipient(Message.RecipientType.TO, to);
 
            // ��������
            message.setSubject(mailTitle);
            // �����ż�����
            message.setText(mailConcept);
            // ���÷���ʱ��
            message.setSentDate(new Date());
            // �洢�ʼ���Ϣ
            message.saveChanges();
 
            Transport transport = s.getTransport(MailUtil.TransportType);
            // Ҫ��������û��������룻
            transport.connect(MailUtil.SMTPServerName, MailUtil.SMTPUserName,
                    MailUtil.SMTPPassword);
 
            // �����ʼ�,���еڶ�����������������õ��ռ��˵�ַ
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            System.out.println("�����ʼ�,�ʼ���ַ:" + emailAddr + " ����:" + mailTitle
                    + " ����:" + mailConcept + "�ɹ�!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("�����ʼ�,�ʼ���ַ:" + emailAddr + " ����:" + mailTitle
                    + " ����:" + mailConcept + "ʧ��! ԭ����" + e.getMessage());
        }
    }

}
