function hdate() {
};

hdate.format = function(date, strFormat) {
	var dateObj = date || new Date();
	var strDate = strFormat || "yyyyMMdd";
	
	var year = dateObj.getFullYear();
	strDate = strDate.replace(/yyyy/g, year);
	strDate = strDate.replace(/yy/g, (year +"").substr(2));
	
	var month = dateObj.getMonth() + 1 < 10 ? "0" + (dateObj.getMonth() + 1) : dateObj.getMonth() + 1;
	strDate = strDate.replace(/MM/g, month);
	strDate = strDate.replace(/M/g, (month + "").substr(1));

	var day = dateObj.getDate() < 10 ? "0" + dateObj.getDate() : dateObj.getDate();
	strDate = strDate.replace(/dd/g, day);
	strDate = strDate.replace(/d/g, (day + "").substr(1));
	
	var hour = dateObj.getHours();
	strDate = strDate.replace(/HH/g, hour);
	strDate = strDate.replace(/H/g, (hour + "").substr(1));

	var min = dateObj.getMinutes();
	strDate = strDate.replace(/mm/g, min);
	strDate = strDate.replace(/m/g, (min + "").substr(1));

	var sec = dateObj.getSeconds();
	strDate = strDate.replace(/ss/g, sec);
	strDate = strDate.replace(/s/g, (sec + "").substr(1));

	return strDate;
};

hdate.yyyyMMdd = function(date) {
	return hdate.format(date, "yyyyMMdd");
};

hdate.yyyy_MM_dd = function(date) {
	return hdate.format(date, "yyyy-MM-dd");
};