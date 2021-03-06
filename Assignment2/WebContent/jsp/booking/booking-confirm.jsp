<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<title>Hotel Go - User</title>
<jsp:include page="../include/include.jsp"></jsp:include>
<script type="text/javascript">
$(document).ready(function(){
	$('.message .close').on('click', function() {
	    $(this).closest('.message').transition('fade');
	});
	
	$(".ui.dropdown").dropdown();
	
	$(".tabular.menu .item").tab();
	
	$("#fromDate")
	.calendar({
		endCalendar: $("#toDate"),
		formatter: {
			date: function(date, settings){
				if(!date) return;
				return date.format("yyyy-mm-dd");
			} 
		},
		onChange: function(date){
			checkRoom({fromDate: date.format("yyyy-mm-dd hh:MM:ss TT")})
		}
	});
	$("#toDate")
	.calendar({
		startCalendar: $("#fromDate"),
		formatter: {
			date: function(date, settings){
				if(!date) return;
				return date.format("yyyy-mm-dd");
			} 
		},
		onChange: function(date){
			checkRoom({toDate: date.format("yyyy-mm-dd hh:MM:ss TT")})
		}
	});
	
	$("form[name='booking']")
	.form({
		fields:{
			noOfPeople: ['empty','integer[1..${requestScope.roomView.roomCapacity}]'],
			fromDate: 'empty',
			toDate: 'empty',
			purpose: 'empty'
		},
		inline: true
	})
	
	$("#checkRoom").on("click",checkRoom)
	
	function checkRoom(obj){
		var form = $("form[name='booking']");
		var data = {
			roomId: ${requestScope.roomView.roomId},
			checkInDate: obj&&obj.fromDate?obj.fromDate:form.form("get value", "fromDate"),
			checkOutDate: obj&&obj.toDate?obj.toDate:form.form("get value", "toDate")
		};
		$.ajax({
			url: "${pageContext.request.contextPath}/rest/booking/checkAvailable",
			method: "post",
			data: JSON.stringify(data),
			dataType: "json"
		}).then(function(data){
			$("#checkRoomMessage").css("display","inline");
			if(data.available){
				$("#checkRoomMessage > i").addClass("green checkmark");
				$("#checkRoomMessage > i").removeClass("red remove");
				$("#checkRoomMessage > span").text(data.msg);
				$(".submit.button").removeClass("disabled");
			} else {
				$("#checkRoomMessage > i").removeClass("green checkmark");
				$("#checkRoomMessage > i").addClass("red remove");
				$("#checkRoomMessage > span").text(data.msg);
				$(".submit.button").addClass("disabled");
			}
			
		})
	}
	
	checkRoom();
	
	//hack
	$(".ui.reset.button").each(function(idx, ele){$(ele).on("click",function(){$(this).parent().form("reset")})})
})
</script>
</head>
<body>
<jsp:include page="../include/header.jsp"></jsp:include>
<div class="ui basic segment">
	<div class="ui container">
		<h1 class="ui header">
			<i class="calendar outline icon"></i>
			<div class="content">
				Booking ${requestScope.roomView.roomNo}, ${requestScope.roomView.hotelName }
				<div class="sub header">Confirm your booking and fill in required information</div>
			</div>
		</h1>
		<form novalidate name="booking" class="ui form" method="post" action="${pageContext.request.contextPath }/booking/create?confirm=1&id=${requestScope.roomView.roomId}">
			<div class="four wide field">
				<label>Number of People (Max: ${requestScope.roomView.roomCapacity})</label>
				<input type="number" min="1" max="${requestScope.roomView.roomCapacity}" name="noOfPeople"/>
			</div>
			<div class="four wide ui calendar field" id="fromDate">
				<label>Check in Date</label>
				<div class="ui left icon input">
					<i class="calendar icon"></i>
					<input type="text" name="fromDate" value="${requestScope.fromDate }"/>
				</div>
			</div>
			<div class="four wide ui calendar field" id="toDate">
				<label>Check out Date</label>
				<div class="ui left icon input">
					<i class="calendar icon"></i>
					<input type="text" name="toDate" value="${requestScope.toDate }" />
				</div>
				
			</div>
			<div class="four wide field">
				<label>Purpose</label>
				<input type="text" name="purpose" value="${requestScope.bookingView.purpose }" maxlength="20"/>
			</div>
		
			<button class="ui green disabled submit button">Reserve</button>
			<button class="ui blue disabled submit button" formaction="${pageContext.request.contextPath }/booking/save?id=${requestScope.roomView.roomId}">Save</button>
			<button class="ui button" type="button" id="checkRoom">Check Room Availability</button>
			<span id="checkRoomMessage" style="display:none">
				<i class="red remove icon"></i>
				<span></span>
			</span>
		</form>
		
		
	</div>
</div>

</body>
</html>