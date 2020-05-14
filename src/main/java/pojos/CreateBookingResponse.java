package pojos;

public class CreateBookingResponse
{
    private Booking booking;

    private String bookingid;

    public Booking getBooking ()
    {
        return booking;
    }

    public void setBooking (Booking booking)
    {
        this.booking = booking;
    }

    public String getBookingid ()
    {
        return bookingid;
    }

    public void setBookingid (String bookingid)
    {
        this.bookingid = bookingid;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [booking = "+booking+", bookingid = "+bookingid+"]";
    }
}
