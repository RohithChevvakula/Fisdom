package pojos;

public class UpdateBookingResponse {
	 private String firstname;

	    private String additionalneeds;

	    private Bookingdates bookingdates;

	    private String totalprice;

	    private String depositpaid;

	    private String lastname;

	    public String getFirstname ()
	    {
	        return firstname;
	    }

	    public void setFirstname (String firstname)
	    {
	        this.firstname = firstname;
	    }

	    public String getAdditionalneeds ()
	    {
	        return additionalneeds;
	    }

	    public void setAdditionalneeds (String additionalneeds)
	    {
	        this.additionalneeds = additionalneeds;
	    }

	    public Bookingdates getBookingdates ()
	    {
	        return bookingdates;
	    }

	    public void setBookingdates (Bookingdates bookingdates)
	    {
	        this.bookingdates = bookingdates;
	    }

	    public String getTotalprice ()
	    {
	        return totalprice;
	    }

	    public void setTotalprice (String totalprice)
	    {
	        this.totalprice = totalprice;
	    }

	    public String getDepositpaid ()
	    {
	        return depositpaid;
	    }

	    public void setDepositpaid (String depositpaid)
	    {
	        this.depositpaid = depositpaid;
	    }

	    public String getLastname ()
	    {
	        return lastname;
	    }

	    public void setLastname (String lastname)
	    {
	        this.lastname = lastname;
	    }

	    @Override
	    public String toString()
	    {
	        return "ClassPojo [firstname = "+firstname+", additionalneeds = "+additionalneeds+", bookingdates = "+bookingdates+", totalprice = "+totalprice+", depositpaid = "+depositpaid+", lastname = "+lastname+"]";
	    }
}