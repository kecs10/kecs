package util;

import java.util.ArrayList;
 

public class MailDTO {
    
	public MailDTO(){}
    
    String cdType				=	null;    	// 메일구분
    String mailFrom			=	null;		// 보내는사람주소
    String personal			=	null;		// 보내는사람
    String mailTo				=	null;    	// 받는사람주소
    String title					=	null; 	// 제목
    String content				=	null;     //	내용
    String mailserver  		=	null;		// 메일서버
 
    ArrayList filepath			=	null;		// 첨부파일 경로
    ArrayList filename		=	null;		// 첨부파일명
    
    /* 세금계산서 승인요청 메일 */
    String mana_Bill_Numb	=	null;	// 세금계산서 번호
	String mana_Sebu_Type	=	null;	// 매출/매입구분 
    String head_Docu_Date	=	null; // 작성일
    String head_Bill_Type		=	null; // 세금계산서종류
    String head_Dema_Indi	=	null; // 영수청구구분
    
    String supp_Part_Iden		=	null; // 공급자 사업자번호
    String supp_Orgn_Name	=	null; // 공급자 상호
    String supp_Part_Name	=	null; // 공급자 대표자명
    String supp_Addr_Text		=	null; // 공급자 주소
    String supp_Busi_Type	=	null; // 공급자 업태
    String supp_Busi_Clas		=	null; // 공급자 업종
    String supp_Cont_Dept		=	null;	// 공급자 담당부서명
    String supp_Pers_Name	=	null;	// 공급자 담당자명
    String supp_Pers_Tele		=	null;	// 공급자 담당자 전화번호
    String supp_Pers_Mail		=	null;	// 공급자 담당자 이메일
    String supp_Pers_Id		=	null;	// 공급자 CSBill 아이디
    
    String buye_Part_Iden		=	null; // 공급받는자 사업자번호
    String buye_Orgn_Name	=	null; // 공급받는자 상호
    String buye_Part_Name	=	null; // 공급받는자 대표자명
    String buye_Addr_Text		=	null; // 공급받는자 주소
    String buye_Busi_Type	=	null; // 공급받는자 업태
    String buye_Busi_Clas		=	null; // 공급받는자 업종
    String buye_Cont_Dept		=	null;	// 공급받는자 담당부서명
    String buye_Pers_Name	=	null;	// 공급받는자 담당자명
    String buye_Pers_Tele		=	null;	// 공급받는자 담당자 전화번호
    String buye_Pers_Mail		=	null;	// 공급받는자 담당자 이메일
    String buye_Pers_Id		=	null;	// 공급받는자 CSBill 아이디
    
    String brok_Part_Iden		=	null; // 대행사 사업자번호
    String brok_Orgn_Name	=	null; // 대행사 상호
    String brok_Part_Name	=	null; // 대행사 대표자명
    String brok_Addr_Text		=	null; // 대행사 주소
    String brok_Busi_Type		=	null; // 대행사 업태
    String brok_Busi_Clas		=	null; // 대행사 업종
    String brok_Cont_Dept		=	null;	// 대행사 담당부서명
    String brok_Pers_Name	=	null;	// 대행사 담당자명
    String brok_Pers_Tele		=	null;	// 대행사 담당자 전화번호
    String brok_Pers_Mail		=	null;	// 대행사 담당자 이메일
    
    String summ_Char_Amou=	null; // 공급가액합계
    String summ_Tax0_Amou=	null; // 세액합계
    String summ_Tota_Quan	=	null; // 총수량
    String summ_Tota_Amou	=	null; // 총금액
	
    String mana_Oppo_Flag	=	null;	// 역발행 여부
    String mana_Excl_Flag	=	null;	// 대량발행여부
    
    String scm_Name				=	null;	// 업체코드
    
    /* 계약서 업체서명 요청 */
    String cont_Numb			=	null;	// 계약번호
    String cont_Chng_Numb	=	null;	// 변경차수
    String cont_Date				=	null;	// 계약일자
    String cont_Date_From		=	null;	// 계약이행시작일자
    String cont_Date_To		=	null;	//	계약이행종료일자
    String cont_Amt				=	null;	//	계약금액
    String cont_Nm				=	null;	// 계약명	
    String cont_Reje_Text		=	null;	// 계약거부사유
    String cont_Proc_Stat		=	null;	// 계약진행상태
    
    /* 비밀번호 분실 */
    String pw	= null;				// 비밀번호
    String busi_N = null;			// 사업자번호
    String cu_Nm = null;			// 신청인명
    String cu_Id = null;				// 신청인 아이디
    String email = null;				// Email
    
    /* 받는사람 정보 */
    String fir_Cu_Id	= null;		// 받는사람 아이디
    String fir_Cu_Nm	= null;		// 받는사람 이름
    String fir_Email	= null;		// 받는사람 이메일
    
    /* 빠른상담 */
    String cont_Type	= null;		//상담내용
    String phone		= null;		//상담자 연락처1
    
    /* 폐기 승인요청 */
    String disuse_Reason				=	null;		// 폐기요청 사유
    String buye_Pers_Hp00			=	null;		// 공급받는자 담당자 핸드폰번호
    
    /* 발행취소 승인요청 */
    String issue_Cancel_Reason	=	null;		// 발행취소 요청/거부 사유
    
    /* 회원 or 비회원 여부 */
    String outpartner = null;
        
    
    public String getOutpartner() {
		return outpartner;
	}

	public void setOutpartner(String outpartner) {
		this.outpartner = outpartner;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCu_Id() {
		return cu_Id;
	}

	public void setCu_Id(String cu_Id) {
		this.cu_Id = cu_Id;
	}

	public String getCu_Nm() {
		return cu_Nm;
	}

	public void setCu_Nm(String cu_Nm) {
		this.cu_Nm = cu_Nm;
	}

	public String getBusi_N() {
		return busi_N;
	}

	public void setBusi_N(String busi_N) {
		this.busi_N = busi_N;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getCdType() {
		return cdType;
	}
	
	public void setCdType(String cdType) {
		this.cdType = cdType;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public ArrayList getFilename() {
		return filename;
	}
	
	public void setFilename(ArrayList filename) {
		this.filename = filename;
	}
	
	public ArrayList getFilepath() {
		return filepath;
	}
	
	public void setFilepath(ArrayList filepath) {
		this.filepath = filepath;
	}
	
	public String getMailFrom() {
		return mailFrom;
	}
	
	public void setMailFrom(String mailFrom) {
		this.mailFrom = mailFrom;
	}
	
	public String getMailserver() {
		return mailserver;
	}
	
	public void setMailserver(String mailserver) {
		this.mailserver = mailserver;
	}
	
	public String getMailTo() {
		return mailTo;
	}
	
	public void setMailTo(String mailTo) {
		this.mailTo = mailTo;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	public String getPersonal() {
		return personal;
	}

	public void setPersonal(String personal) {
		this.personal = personal;
	}

	public String getBrok_Addr_Text() {
		return brok_Addr_Text;
	}

	public void setBrok_Addr_Text(String brok_Addr_Text) {
		this.brok_Addr_Text = brok_Addr_Text;
	}

	public String getBrok_Busi_Clas() {
		return brok_Busi_Clas;
	}

	public void setBrok_Busi_Clas(String brok_Busi_Clas) {
		this.brok_Busi_Clas = brok_Busi_Clas;
	}

	public String getBrok_Busi_Type() {
		return brok_Busi_Type;
	}

	public void setBrok_Busi_Type(String brok_Busi_Type) {
		this.brok_Busi_Type = brok_Busi_Type;
	}

	public String getBrok_Cont_Dept() {
		return brok_Cont_Dept;
	}

	public void setBrok_Cont_Dept(String brok_Cont_Dept) {
		this.brok_Cont_Dept = brok_Cont_Dept;
	}

	public String getBrok_Orgn_Name() {
		return brok_Orgn_Name;
	}

	public void setBrok_Orgn_Name(String brok_Orgn_Name) {
		this.brok_Orgn_Name = brok_Orgn_Name;
	}

	public String getBrok_Part_Iden() {
		return brok_Part_Iden;
	}

	public void setBrok_Part_Iden(String brok_Part_Iden) {
		this.brok_Part_Iden = brok_Part_Iden;
	}

	public String getBrok_Part_Name() {
		return brok_Part_Name;
	}

	public void setBrok_Part_Name(String brok_Part_Name) {
		this.brok_Part_Name = brok_Part_Name;
	}

	public String getBrok_Pers_Mail() {
		return brok_Pers_Mail;
	}

	public void setBrok_Pers_Mail(String brok_Pers_Mail) {
		this.brok_Pers_Mail = brok_Pers_Mail;
	}

	public String getBrok_Pers_Name() {
		return brok_Pers_Name;
	}

	public void setBrok_Pers_Name(String brok_Pers_Name) {
		this.brok_Pers_Name = brok_Pers_Name;
	}

	public String getBrok_Pers_Tele() {
		return brok_Pers_Tele;
	}

	public void setBrok_Pers_Tele(String brok_Pers_Tele) {
		this.brok_Pers_Tele = brok_Pers_Tele;
	}

	public String getBuye_Addr_Text() {
		return buye_Addr_Text;
	}

	public void setBuye_Addr_Text(String buye_Addr_Text) {
		this.buye_Addr_Text = buye_Addr_Text;
	}

	public String getBuye_Busi_Clas() {
		return buye_Busi_Clas;
	}

	public void setBuye_Busi_Clas(String buye_Busi_Clas) {
		this.buye_Busi_Clas = buye_Busi_Clas;
	}

	public String getBuye_Busi_Type() {
		return buye_Busi_Type;
	}

	public void setBuye_Busi_Type(String buye_Busi_Type) {
		this.buye_Busi_Type = buye_Busi_Type;
	}

	public String getBuye_Cont_Dept() {
		return buye_Cont_Dept;
	}

	public void setBuye_Cont_Dept(String buye_Cont_Dept) {
		this.buye_Cont_Dept = buye_Cont_Dept;
	}

	public String getBuye_Orgn_Name() {
		return buye_Orgn_Name;
	}

	public void setBuye_Orgn_Name(String buye_Orgn_Name) {
		this.buye_Orgn_Name = buye_Orgn_Name;
	}

	public String getBuye_Part_Iden() {
		return buye_Part_Iden;
	}

	public void setBuye_Part_Iden(String buye_Part_Iden) {
		this.buye_Part_Iden = buye_Part_Iden;
	}

	public String getBuye_Part_Name() {
		return buye_Part_Name;
	}

	public void setBuye_Part_Name(String buye_Part_Name) {
		this.buye_Part_Name = buye_Part_Name;
	}

	public String getBuye_Pers_Mail() {
		return buye_Pers_Mail;
	}

	public void setBuye_Pers_Mail(String buye_Pers_Mail) {
		this.buye_Pers_Mail = buye_Pers_Mail;
	}

	public String getBuye_Pers_Name() {
		return buye_Pers_Name;
	}

	public void setBuye_Pers_Name(String buye_Pers_Name) {
		this.buye_Pers_Name = buye_Pers_Name;
	}

	public String getBuye_Pers_Tele() {
		return buye_Pers_Tele;
	}

	public void setBuye_Pers_Tele(String buye_Pers_Tele) {
		this.buye_Pers_Tele = buye_Pers_Tele;
	}

	public String getHead_Bill_Type() {
		return head_Bill_Type;
	}

	public void setHead_Bill_Type(String head_Bill_Type) {
		this.head_Bill_Type = head_Bill_Type;
	}

	public String getHead_Dema_Indi() {
		return head_Dema_Indi;
	}

	public void setHead_Dema_Indi(String head_Dema_Indi) {
		this.head_Dema_Indi = head_Dema_Indi;
	}

	public String getHead_Docu_Date() {
		return head_Docu_Date;
	}

	public void setHead_Docu_Date(String head_Docu_Date) {
		this.head_Docu_Date = head_Docu_Date;
	}

	public String getMana_Bill_Numb() {
		return mana_Bill_Numb;
	}

	public void setMana_Bill_Numb(String mana_Bill_Numb) {
		this.mana_Bill_Numb = mana_Bill_Numb;
	}

	public String getMana_Excl_Flag() {
		return mana_Excl_Flag;
	}

	public void setMana_Excl_Flag(String mana_Excl_Flag) {
		this.mana_Excl_Flag = mana_Excl_Flag;
	}

	public String getMana_Oppo_Flag() {
		return mana_Oppo_Flag;
	}

	public void setMana_Oppo_Flag(String mana_Oppo_Flag) {
		this.mana_Oppo_Flag = mana_Oppo_Flag;
	}

	public String getMana_Sebu_Type() {
		return mana_Sebu_Type;
	}

	public void setMana_Sebu_Type(String mana_Sebu_Type) {
		this.mana_Sebu_Type = mana_Sebu_Type;
	}

	public String getSumm_Char_Amou() {
		return summ_Char_Amou;
	}

	public void setSumm_Char_Amou(String summ_Char_Amou) {
		this.summ_Char_Amou = summ_Char_Amou;
	}

	public String getSumm_Tax0_Amou() {
		return summ_Tax0_Amou;
	}

	public void setSumm_Tax0_Amou(String summ_Tax0_Amou) {
		this.summ_Tax0_Amou = summ_Tax0_Amou;
	}

	public String getSumm_Tota_Amou() {
		return summ_Tota_Amou;
	}

	public void setSumm_Tota_Amou(String summ_Tota_Amou) {
		this.summ_Tota_Amou = summ_Tota_Amou;
	}

	public String getSumm_Tota_Quan() {
		return summ_Tota_Quan;
	}

	public void setSumm_Tota_Quan(String summ_Tota_Quan) {
		this.summ_Tota_Quan = summ_Tota_Quan;
	}

	public String getSupp_Addr_Text() {
		return supp_Addr_Text;
	}

	public void setSupp_Addr_Text(String supp_Addr_Text) {
		this.supp_Addr_Text = supp_Addr_Text;
	}

	public String getSupp_Busi_Clas() {
		return supp_Busi_Clas;
	}

	public void setSupp_Busi_Clas(String supp_Busi_Clas) {
		this.supp_Busi_Clas = supp_Busi_Clas;
	}

	public String getSupp_Busi_Type() {
		return supp_Busi_Type;
	}

	public void setSupp_Busi_Type(String supp_Busi_Type) {
		this.supp_Busi_Type = supp_Busi_Type;
	}

	public String getSupp_Cont_Dept() {
		return supp_Cont_Dept;
	}

	public void setSupp_Cont_Dept(String supp_Cont_Dept) {
		this.supp_Cont_Dept = supp_Cont_Dept;
	}

	public String getSupp_Orgn_Name() {
		return supp_Orgn_Name;
	}

	public void setSupp_Orgn_Name(String supp_Orgn_Name) {
		this.supp_Orgn_Name = supp_Orgn_Name;
	}

	public String getSupp_Part_Iden() {
		return supp_Part_Iden;
	}

	public void setSupp_Part_Iden(String supp_Part_Iden) {
		this.supp_Part_Iden = supp_Part_Iden;
	}

	public String getSupp_Part_Name() {
		return supp_Part_Name;
	}

	public void setSupp_Part_Name(String supp_Part_Name) {
		this.supp_Part_Name = supp_Part_Name;
	}

	public String getSupp_Pers_Mail() {
		return supp_Pers_Mail;
	}

	public void setSupp_Pers_Mail(String supp_Pers_Mail) {
		this.supp_Pers_Mail = supp_Pers_Mail;
	}

	public String getSupp_Pers_Name() {
		return supp_Pers_Name;
	}

	public void setSupp_Pers_Name(String supp_Pers_Name) {
		this.supp_Pers_Name = supp_Pers_Name;
	}

	public String getSupp_Pers_Tele() {
		return supp_Pers_Tele;
	}

	public void setSupp_Pers_Tele(String supp_Pers_Tele) {
		this.supp_Pers_Tele = supp_Pers_Tele;
	}

	/**
	 * @return fir_Cu_Id
	 */
	public String getFir_Cu_Id() {
		return fir_Cu_Id;
	}

	/**
	 * @param fir_Cu_Id 설정하려는 fir_Cu_Id
	 */
	public void setFir_Cu_Id(String fir_Cu_Id) {
		this.fir_Cu_Id = fir_Cu_Id;
	}

	/**
	 * @return fir_Cu_Nm
	 */
	public String getFir_Cu_Nm() {
		return fir_Cu_Nm;
	}

	/**
	 * @param fir_Cu_Nm 설정하려는 fir_Cu_Nm
	 */
	public void setFir_Cu_Nm(String fir_Cu_Nm) {
		this.fir_Cu_Nm = fir_Cu_Nm;
	}

	/**
	 * @return fir_Email
	 */
	public String getFir_Email() {
		return fir_Email;
	}

	/**
	 * @param fir_Email 설정하려는 fir_Email
	 */
	public void setFir_Email(String fir_Email) {
		this.fir_Email = fir_Email;
	}

	/**
	 * @return cont_Type
	 */
	public String getCont_Type() {
		return cont_Type;
	}

	/**
	 * @param cont_Type 설정하려는 cont_Type
	 */
	public void setCont_Type(String cont_Type) {
		this.cont_Type = cont_Type;
	}

	/**
	 * @return phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone 설정하려는 phone
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCont_Amt() {
		return cont_Amt;
	}

	public void setCont_Amt(String cont_Amt) {
		this.cont_Amt = cont_Amt;
	}

	public String getCont_Date() {
		return cont_Date;
	}

	public void setCont_Date(String cont_Date) {
		this.cont_Date = cont_Date;
	}

	public String getCont_Date_From() {
		return cont_Date_From;
	}

	public void setCont_Date_From(String cont_Date_From) {
		this.cont_Date_From = cont_Date_From;
	}

	public String getCont_Date_To() {
		return cont_Date_To;
	}

	public void setCont_Date_To(String cont_Date_To) {
		this.cont_Date_To = cont_Date_To;
	}

	public String getCont_Nm() {
		return cont_Nm;
	}

	public void setCont_Nm(String cont_Nm) {
		this.cont_Nm = cont_Nm;
	}

	public String getCont_Chng_Numb() {
		return cont_Chng_Numb;
	}

	public void setCont_Chng_Numb(String cont_Chng_Numb) {
		this.cont_Chng_Numb = cont_Chng_Numb;
	}

	public String getCont_Numb() {
		return cont_Numb;
	}

	public void setCont_Numb(String cont_Numb) {
		this.cont_Numb = cont_Numb;
	}

	public String getCont_Reje_Text() {
		return cont_Reje_Text;
	}

	public void setCont_Reje_Text(String cont_Reje_Text) {
		this.cont_Reje_Text = cont_Reje_Text;
	}

	/**
	 * @return issue_Cancel_Reason
	 */
	public String getIssue_Cancel_Reason() {
		return issue_Cancel_Reason;
	}

	/**
	 * @param issue_Cancel_Reason 설정하려는 issue_Cancel_Reason
	 */
	public void setIssue_Cancel_Reason(String issue_Cancel_Reason) {
		this.issue_Cancel_Reason = issue_Cancel_Reason;
	}

	/**
	 * @return buye_Pers_Hp00
	 */
	public String getBuye_Pers_Hp00() {
		return buye_Pers_Hp00;
	}

	/**
	 * @param buye_Pers_Hp00 설정하려는 buye_Pers_Hp00
	 */
	public void setBuye_Pers_Hp00(String buye_Pers_Hp00) {
		this.buye_Pers_Hp00 = buye_Pers_Hp00;
	}

	public String getDisuse_Reason() {
		return disuse_Reason;
	}

	public void setDisuse_Reason(String disuse_Reason) {
		this.disuse_Reason = disuse_Reason;
	}

	public String getCont_Proc_Stat() {
		return cont_Proc_Stat;
	}

	public void setCont_Proc_Stat(String cont_Proc_Stat) {
		this.cont_Proc_Stat = cont_Proc_Stat;
	}

	public String getScm_Name() {
		return scm_Name;
	}

	public void setScm_Name(String scm_Name) {
		this.scm_Name = scm_Name;
	}

	public String getSupp_Pers_Id() {
		return supp_Pers_Id;
	}

	public void setSupp_Pers_Id(String supp_Pers_Id) {
		this.supp_Pers_Id = supp_Pers_Id;
	}

	public String getBuye_Pers_Id() {
		return buye_Pers_Id;
	}

	public void setBuye_Pers_Id(String buye_Pers_Id) {
		this.buye_Pers_Id = buye_Pers_Id;
	}
}
