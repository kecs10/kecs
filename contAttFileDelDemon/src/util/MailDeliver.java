package util;

import java.io.*;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;    
import javax.activation.*;

/**
 *
 * @version 1.0, 07/19/07
 * @author  Chul Hong Park
 * @since   
 */
public class MailDeliver
{
	// 메일을 보낼 SMTP서버와 프로그램이름 설정
	String MAIL_HOSTNAME;
	String charSet 		= "UTF-8";
	Properties props 	= new Properties();
	Session session 	= null;

	/**
	 * 인증 없는 메일서버 셋팅 생성자
	 *
	 * @param hostName	메일 호스트명
	 */
	public MailDeliver(String hostName)
	{
		this(hostName, null, null);
	}

	/**
	 * 메일서버의 환경을 지정하는 생성자
	 * 메일서버에 인증이 필요하다면 이 생성자를 사용하세요.
	 *
	 * @param	 hostName	 메일 호스트명
	 * @param	 userId	사용자ID
	 * @param	 userPasswd	사용자 패스워드
	 */
	public MailDeliver(String hostName, String userId, String userPasswd)
	{
		this.MAIL_HOSTNAME 	= hostName;
		props.put("mail.smtp.host", MAIL_HOSTNAME);
		
		//MailAuthenticator auth = null;
		//if(userId != null)
		//{
		//	auth = new MailAuthenticator(userId, userPasswd);
		//	props.put("mail.smtp.auth", "true");
		//}

		session = Session.getInstance(props, null);
	}

	/**
	 * 첨부파일을 포함하지 않는 메일 전송
	 *
	 * @param	 from	 보내는 사람
	 * @param	 to	 받는 사람
	 * @param	 subject	 제목
	 * @param	 message	메세지
	 */
	public void sendMail(InternetAddress from, InternetAddress to, String subject, String message) throws MessagingException
	{
		String[][] header = new String[3][3];
		header[0][0] = "Content-Type";
		header[0][1] = "text/html";
		header[1][0] = "charset";
		header[1][1] = "utf-8";
		header[2][0] = "X-Mailer";
		header[2][1] = "mail";
		String[] messageAry = null;
		if(message != null)
			messageAry = new String[]{message};
		
		sendMail(from, to, subject, messageAry, header);
	}

	/**
	 * 첨부파일을 포함하지 않는 메일을 전송
	 * 
	 * @param	 from	 보내는 사람
	 * @param	 to	 받는 사람
	 * @param	 subject	 제목
	 * @param	 message	메세지
	 * @param	 header
	 */	
	public void sendMail(InternetAddress from, InternetAddress to, String subject, String[] message, String[][] header) throws MessagingException
	{
		int messageLength = 0;
		if(message != null)
		{
			messageLength = message.length;
			for(int cnt = 0; cnt < messageLength; cnt++)
			{
				if(message[cnt] == null)
					throw new NullPointerException("Message contains 'null' character");
			}
		}

		MimeMessage msg = new MimeMessage(session);
		msg.setHeader("X-Mailer", "beatoffice MailDeliver");
		
		
		String addr = from.getAddress();
		String personal = from.getPersonal();
		
		//20100514 SMH 보내는사람, 제목 인코딩 작업
		try{
			msg.setFrom(new InternetAddress(addr,personal,"UTF-8"));
		//msg.setFrom(from);
			msg.setRecipient(Message.RecipientType.TO, to);
			//msg.setSubject(subject);
			msg.setSubject(MimeUtility.encodeText(subject,"UTF-8","B"));
			
		}catch(Exception e){LogWrapper.biz.debug(e.getMessage());}
		
		msg.setSentDate(new Date());

		//바디 메세지 셋팅
		MimeBodyPart[] mbp = new MimeBodyPart[messageLength];
		for(int cnt = 0; cnt < messageLength; cnt++)
		{
			mbp[cnt] = new MimeBodyPart();
			mbp[cnt].setContent(message[cnt], "text/html; charset=" + charSet);
			//mbp[cnt].setText(message[cnt]);
		}

		for(int cnt = messageLength, cnt1 = 0; cnt < messageLength; cnt++, cnt1++)
			mbp[cnt] = new MimeBodyPart();

		Multipart mp = new MimeMultipart();
		for(int cnt = 0; cnt < mbp.length; cnt++)
			mp.addBodyPart(mbp[cnt]);

		msg.setContent(mp);

		// 메일 전송
		Transport.send(msg);
	}
	
	/**
	 * 첨부파일을 포함한 메일 전송
	 *
	 * @param from 보내는 사람
	 * @param to	 받는 사람
	 * @param subject	제목
	 * @param message	메세지
	 * @param filePath	보낼 파일의 로컬패스. 반드시 경로에 파일명이 포함되어야 합니다.
	 * @param filePath	보낼 파일명. 메일에 보여질 파일명 
	 */
	public void sendMail(InternetAddress from, InternetAddress to, String subject, String message, ArrayList filePath, ArrayList fileName) throws FileNotFoundException, MessagingException
	{
		String[][] header = new String[3][3];
		header[0][0] = "Content-Type";
		header[0][1] = "text/html";
		header[1][0] = "charset";
		header[1][1] = "utf-8";
		header[2][0] = "X-Mailer";
		header[2][1] = "mail";
		String[] messageAry = null;
		if(message != null)
			messageAry = new String[]{message};
		
		sendMail(from, to, subject, messageAry, filePath, fileName, header);
	}

	/**
	 * 첨부파일이 포함된 메일을 전송
	 * 
	 * @param from 보내는 사람
	 * @param to	 받는 사람
	 * @param subject	제목
	 * @param message	메세지
	 * @param filePath	보낼 파일의 로컬패스. 반드시 경로에 파일명이 포함되어야 합니다.
	 * @param header
	 */	
	public void sendMail(InternetAddress from, InternetAddress to, String subject, String[] message, ArrayList filePath, ArrayList fileName, String[][] header) throws FileNotFoundException, MessagingException
	{
		int messageLength 	= 0;
		int filePathLength 		= 0;
		if(message != null)
		{
			messageLength 		= message.length;
			for(int cnt = 0; cnt < messageLength; cnt++)
			{
				if(message[cnt] == null)
					throw new NullPointerException("Message contains 'null' character");
			}
		}

		if(filePath != null)
		{
			
			filePathLength		=	filePath.size();
			for(int cnt = 0; cnt < filePathLength; cnt++)
			{
				if(filePath.get(cnt) == null)
					throw new NullPointerException("filePath contains 'null' character");
				File file = new File((String)filePath.get(cnt));
				if(!file.exists())
					throw new FileNotFoundException((String)filePath.get(cnt));
			}
		}

		MimeMessage msg = new MimeMessage(session);
		msg.setFrom(from);
		msg.setRecipient(Message.RecipientType.TO, to);
		msg.setSubject(subject);
		msg.setSentDate(new Date());

		//바디 메세지 셋팅
		MimeBodyPart[] mbp = new MimeBodyPart[messageLength + filePathLength];
		for(int cnt = 0; cnt < messageLength; cnt++)
		{
			mbp[cnt] = new MimeBodyPart();
			mbp[cnt].setHeader("content-type", "text/html");
			mbp[cnt].setContent(message[cnt], "text/html; charset=euc-kr");
			//mbp[cnt].setText(message[cnt]);
			/*
			body.setHeader("content-type", "text/html");
			body.setHeader("charset", "EUC-KR");
			body.setHeader("Content-Transfer-Encoding", "8bit");
			*/

		}

		for(int cnt = messageLength, cnt1 = 0; cnt < messageLength + filePathLength; cnt++, cnt1++)
		{
			mbp[cnt] 					= new MimeBodyPart();
			FileDataSource fds 		= new FileDataSource((String)filePath.get(cnt1));
			mbp[cnt].setDataHandler(new DataHandler(fds));
			try{
			//	mbp[cnt].setFileName(MimeUtility.encodeWord(fds.getName()));
				mbp[cnt].setFileName(MimeUtility.encodeWord((String)fileName.get(cnt1)));
			}catch(UnsupportedEncodingException e){}
		}

		Multipart mp = new MimeMultipart();
		for(int cnt = 0; cnt < mbp.length; cnt++)
			mp.addBodyPart(mbp[cnt]);

		msg.setContent(mp);

		// 메일 전송
		Transport.send(msg);
		
		// 임시 저장돼 있는 파일 삭제함.
		if(filePath != null)
		{
			filePathLength		=	filePath.size();
			for(int cnt = 0; cnt < filePathLength; cnt++)
			{
				if(filePath.get(cnt) == null)
					throw new NullPointerException("filePath contains 'null' character");
				File file = new File((String)filePath.get(cnt));
				if(file.exists())
					file.delete();
			}
		}
	}

	
	/**
	 * sets character set for 'text/html; charset=euc-kr' statement in Mail header.
	 * 국제화 페이지를 설정할때는 utf-8로 셋팅하면 됩니다.
	 *
	 * @param	 charSet	 character set
	 */
	public void setCharSet(String charSet)
	{
		this.charSet = charSet;
	}

	
}