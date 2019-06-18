function hdate() {
};

hdate.format = function(date, strFormat) {
	var dateObj = date || new Date();
	var strDate = strFormat || "yyyyMMdd";
	
	var year = dateObj.getFullYear();
	strDate = strDate.replace(/yyyy/g, year);
	strDate = strDate.replace(/yy/g, (year +"").substr(2));
	
	var month = "" + (dateObj.getMonth() + 1);
	strDate = strDate.replace(/MM/g, month.padLeft(2, '0'));
	strDate = strDate.replace(/M/g, month);

	var day = "" + dateObj.getDate();
	strDate = strDate.replace(/dd/g, day.padLeft(2, '0'));
	strDate = strDate.replace(/d/g, day);
	
	var hour = "" + dateObj.getHours();
	strDate = strDate.replace(/HH/g, hour.padLeft(2, '0'));
	strDate = strDate.replace(/H/g, hour);

	var min = "" + dateObj.getMinutes();
	strDate = strDate.replace(/mm/g, min.padLeft(2, '0'));
	strDate = strDate.replace(/m/g, min);

	var sec = "" + dateObj.getSeconds();
	strDate = strDate.replace(/ss/g, sec.padLeft(2, '0'));
	strDate = strDate.replace(/s/g, sec);

	return strDate;
};

hdate.yyyyMMdd = function(date) {
	return hdate.format(date, "yyyyMMdd");
};

hdate.yyyy_MM_dd = function(date) {
	return hdate.format(date, "yyyy-MM-dd");
};

hdate.span = function(date1, date2, f) {
	var s = new Date(date2).getTime() - new Date(date1).getTime();
	if(f == "h")
	  return (s / 3600000).toFixed(2); 
};