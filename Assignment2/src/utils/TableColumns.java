package utils;

public class TableColumns {
	public class User{
		public static final String USER_ID = "user_id";
		public static final String TITLE = "title";
		public static final String FIRST_NAME = "first_name";
		public static final String LAST_NAME = "last_name";
		public static final String EMAIL = "email";
		public static final String PASSWORD = "password";
		public static final String IS_REGISTERED = "is_registered";
		public static final String GENDER = "gender";
		public static final String ROLE = "role";
	}
	
	public class Hotel{
		public static final String HOTEL_ID = "hotel_id";
		public static final String NAME = "name";
		public static final String LOCATION = "location";
		public static final String ADDRESS = "address";
		public static final String NO_OF_ROOMS = "no_of_rooms";
		public static final String RATING = "rating";
		public static final String DESCRIPTION = "description";
		public static final String JOIN_DATE = "join_date";
	}
	
	public class Bed{
		public static final String BED_ID = "bed_id";
		public static final String TYPE = "type";
		public static final String SIZE = "size";
	}
	
	public class Booking{
		public static final String BOOKING_ID = "booking_id";
		public static final String BOOKING_NUMBER = "booking_number";
		public static final String CUSTOMER_ID = "customer_id";
		public static final String ROOM_ID = "room_id";
		public static final String NO_OF_PEOPLE = "no_of_people";
		public static final String CHECK_IN_DATE = "check_in_date";
		public static final String CHECK_OUT_DATE = "check_out_date";
		public static final String PURPOSE = "purpose";
		public static final String BOOKING_DATE = "booking_date";
		public static final String PIN = "pin";
		public static final String IS_CANCELLED = "is_cancelled";
	}
	
	public class Location{
		public static final String LOCATION_ID = "location_id";
		public static final String NAME = "name";
		public static final String COUNTRY = "country";
	}
	
	public class Facility{
		public static final String FACILITY_ID = "facility_id";
		public static final String NAME = "name";
		public static final String CATEGORY = "category";
	}
	
	public class HotelFacility{
		public static final String HOTEL = "home";
		public static final String FACILITY = "facility";
	}
	
	public class Role{
		public static final String ROLE_ID = "role_id";
		public static final String NAME = "name";
	}
}
