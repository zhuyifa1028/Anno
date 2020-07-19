/**是否手机号码*/
jQuery.validator.addMethod("is_phone", function(value, element) {
	var reg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|(17[0-9]{1}))+\d{8})$/;
	return reg.test(value);
}, "请输入正确的手机号码");

/**验证手机号码是否存在*/
jQuery.validator.addMethod("exist_phone", function(value, element) {
	var _value = element.defaultValue || '';
	if(!!_value && value == _value) {
		return true;
	} else {
		var result = false;
		// 设置同步
		$.ajaxSetup({
			async: false
		});
		
		$.post(_admin + "/validate/exist/phone", {phone: value}, function(data) {
			result = data.succeed == true;
		});
		
		// 恢复异步
		$.ajaxSetup({
			async: true
		});
		return result;
	}
}, "该手机号码已存在");

/**验证用户名称是否存在*/
jQuery.validator.addMethod("exist_account", function(value, element) {
	var _value = element.defaultValue || '';
	if(!!_value && value == _value) {
		return true;
	} else {
		var result = false;
		// 设置同步
		$.ajaxSetup({
			async: false
		});
		
		$.post(_admin + "/validate/exist/account", {account: value}, function(data) {
			result = data.succeed == true;
		});
		
		// 恢复异步
		$.ajaxSetup({
			async: true
		});
		return result;
	}
}, "该用户名称已存在");