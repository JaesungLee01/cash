package cash.vo;

public class Cashbook {
	private String memberId;
	private int cashbookNo;
	private String category;
	private String cashbookDate;
	private int price;
	private String memo;
	private String updatedate;
	private String createdate;
	public Cashbook() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Cashbook(String memberId, int cashbookNo, String category, String cashbookDate, int price, String memo,
			String updatedate, String createdate) {
		super();
		this.memberId = memberId;
		this.cashbookNo = cashbookNo;
		this.category = category;
		this.cashbookDate = cashbookDate;
		this.price = price;
		this.memo = memo;
		this.updatedate = updatedate;
		this.createdate = createdate;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public int getCashbookNo() {
		return cashbookNo;
	}
	public void setCashbookNo(int cashbookNo) {
		this.cashbookNo = cashbookNo;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getCashbookDate() {
		return cashbookDate;
	}
	public void setCashbookDate(String cashbookDate) {
		this.cashbookDate = cashbookDate;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getUpdatedate() {
		return updatedate;
	}
	public void setUpdatedate(String updatedate) {
		this.updatedate = updatedate;
	}
	public String getCreatedate() {
		return createdate;
	}
	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}
	@Override
	public String toString() {
		return "Cashbook [memberId=" + memberId + ", cashbookNo=" + cashbookNo + ", category=" + category
				+ ", cashbookDate=" + cashbookDate + ", price=" + price + ", memo=" + memo + ", updatedate="
				+ updatedate + ", createdate=" + createdate + "]";
	}

}	
