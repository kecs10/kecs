/*
------------------------Class 설명-------------------------------
ID          	:   MailTransfer
용도       	:   메일발송
주요기능 	:   1. MailDeliver의 sendMail() 호출
					2. 받는 메일주소,보내는 메일주소, 제목, 내용을 취합

-------------------Modified Log----------------------------------
ver         date                author      description
1.0         2008.01.15        박철홍      First Coding

================================================================*/

package util;


import javax.mail.internet.InternetAddress;


public class MailTransfer {

	public MailTransfer(){}

	public void sendMail( MailDTO mailDTO) throws Exception{  

		LogWrapper.biz.debug("◁◀ ["+this.getClass().getName()+"] Start ▶▷");        
		MailDeliver mailDeliver = null;

		/******************************************
		 * 공통코드의 메일정보를 가져온다.
		 * -메일서버정보.
		 ******************************************/       

		try{     

			/************** 보내는 사람 **************************/
			InternetAddress from	=	new InternetAddress();
			from.setAddress(mailDTO.getMailFrom());
			from.setPersonal(mailDTO.getPersonal());

			/****************************************************/

			/**************** 받는 사람 ***************************/
			InternetAddress to = new InternetAddress(mailDTO.getMailTo());

			String address = to.getAddress();

			int addr_index = address.indexOf("@");

			if(addr_index !=-1){
					/******************************************************/

					LogWrapper.biz.debug("◁◀ Mail Sending...                      ▶▷");      
					LogWrapper.biz.debug("▶▷ Server : "+mailDTO.getMailserver());   
					LogWrapper.biz.debug("▶▷ cdType : "+mailDTO.getCdType());   
					LogWrapper.biz.debug("▶▷ From : "+from);
					LogWrapper.biz.debug("▶▷ From Personal : "+mailDTO.getPersonal());            
					LogWrapper.biz.debug("▶▷ To : "+to);
					LogWrapper.biz.debug("▶▷ Title	: "+mailDTO.getTitle()); 
					LogWrapper.biz.debug("▶▷ Content	: "+mailDTO.getContent()); 

					mailDeliver	=	new MailDeliver(mailDTO.getMailserver());

					/*==◇◆◇◆◇◆◇◆◇◆ 메일 발송 ◇◆◇◆◇◆◇◆◇◆  ==*/
					/*>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<*/
					mailDeliver.sendMail(from, to, mailDTO.getTitle(), mailDTO.getContent());
			} else{
				LogWrapper.biz.debug("주소형식이 틀립니다.");
				throw new Exception("주소형식이 틀립니다.");
			}
		}catch(Exception e){

			LogWrapper.biz.debug("◁◀ ["+this.getClass().getName()+"] Error :"+e.getMessage()+" ▶▷");                  
			throw e;    
		}finally{

		}

		mailDTO = null;
		LogWrapper.biz.debug("◁◀ ["+this.getClass().getName()+"] End ▶▷");        
	}
}
