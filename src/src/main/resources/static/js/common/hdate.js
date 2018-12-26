function hdate() {
};

hdate.format = function(date, strFormat) {

	var dateObj = date || new Date();
	var strDate = strFormat || "yyyyMMdd";
	
	var year = dateObj.getFullYear();
	strFormat.replace(/yyyy/g, year);
	strFormat.replace(/yy/g, year.substr(2));
	
	var month = dateObj.getMonth() + 1 < 10 ? "0" + (dateObj.getMonth() + 1) : dateObj.getMonth() + 1;
	strFormat.replace(/MM/g, month);
	strFormat.replace(/M/g, month.substr(1));

	var day = dateObj.getDate() < 10 ? "0" + dateObj.getDate() : dateObj.getDate();
	strFormat.replace(/dd/g, day);
	strFormat.replace(/d/g, day.substr(1));
	
	var hour = dateObj.getHours();
	strFormat.replace(/HH/g, hour);
	strFormat.replace(/H/g, hour.substr(1));

	var min = dateObj.getMinutes();
	strFormat.replace(/mm/g, min);
	strFormat.replace(/m/g, min.substr(1));

	var sec = dateObj.getSeconds();
	strFormat.replace(/ss/g, sec);
	strFormat.replace(/s/g, sec.substr(1));

	return strFormat;
};

hdate.yyyyMMdd = function(date) {
	return hdate.format(date, "yyyyMMdd");
};