/**
 * 
 */
function loginValidation(){
   var password = document.getElementById("pass");
   var userID = document.getElementById("userID");
   if(password.value == "" || userID.value == ""){
	   alert("Please fill all the highlighted fields")
	   if(password.value == ""){
          password.focus();
	      password.style.border = "solid 3px red";
	   }
	   if(userID.value == ""){
          userID.focus();
	      userID.style.border = "solid 3px red";
	   }
	  return false;
   }
}

function registerationValidation(){
	   var password = document.getElementById("pass");
	   var userId = document.getElementById("userID");
	   var userN = document.getElementById("userName");
	   
	   if(password.value == "" || userId.value == "" || userN.value == ""){
		  alert("Please fill all the highlighted fields");
		  if(password.value == ""){
	          password.focus();
		      password.style.border = "solid 3px red";
		  }
		  if(userId.value == ""){
	          userId.focus();
		      userId.style.border = "solid 3px red";
		  }
		  if(userN.value == ""){
	          userN.focus();
		      userN.style.border = "solid 3px red";
		  }
		  return false;
	   }
}

function newTaskValidation(){
	var taskName = document.getElementById("taskName");
	if(taskName.value == ""){
		taskName.focus();
		taskName.style.border = "solid 2px red"
		return false;
	}
}

function setMinDate(){
	let today = new Date(),
    day = today.getDate(),
    month = today.getMonth()+1, //January is 0
    year = today.getFullYear();
    if(day<10){
        day='0'+day
    } 
    if(month<10){
        month='0'+month
    }
    today = year+'-'+month+'-'+day;
    document.getElementById("expDate").setAttribute("min", today);
}


