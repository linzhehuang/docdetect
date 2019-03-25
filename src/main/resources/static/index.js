	function ajax() {
		var ajaxXHR = createXMLHttpRequest();
		var ajaxData = {
			requestType: arguments[0].requestType || "POST",
			contentType: arguments[0].contentType || "application/json",
			url: arguments[0].url || "",
			async: arguments[0].async || true,
			data: arguments[0].data || "{}",
			success: arguments[0].success || function() {},
			error: arguments[0].error || function() {}
		};
		// Send the request.
		with(ajaxXHR) {
			onreadystatechange = function() {
				if(readyState == 4) {
					if(status == 200) {
						ajaxData.success(ajaxXHR);
					} else {
						ajaxData.error(ajaxXHR);
					}
				}
			};
			open(ajaxData.requestType, ajaxData.url, ajaxData.async);
			if(ajaxData.contentType != true) setRequestHeader("Content-Type", ajaxData.contentType);
			send(formatData(ajaxData.data));
		}
		// Create XMLHttpRequest object.
		function createXMLHttpRequest() {
			if(window.ActiveXObject) return new ActiveXObject("Microsoft.XMLHTTP");
			else if(window.XMLHttpRequest) return new XMLHttpRequest();
		}
		// Format the data.
		function formatData(data) {
			if(ajaxData.contentType == "application/json") return JSON.stringify(data);
			else {
				return data;
			}
		}
	}
	function convertJSONString(jsonString) {
		return eval('(' + jsonString + ')');
	}
	function $(idName) {
		return document.getElementById(idName);
	}
	function $$(tagName) {
		return document.createElement(tagName);
	}
	// 
	var baseURL = 'http://localhost:8080';  // Comment this line before build project.
	function uploadZipFile() {
		var formData = new FormData($('fileForm'));
		ajax({
			requestType: 'POST',
			url: baseURL + '/uploadzip',
			data: formData,
			contentType: true,
			success: (response) => {
				var o = convertJSONString(response.responseText)
				if (o.success) {
					alert("Upload success!");
				} else {
					alert("Upload failed!");
				}
			},
			error: (response) => {
				alert("Upload failed!");
			}
		});
	}
	function process() {
		ajax({
			requestType: 'POST',
			url: baseURL + '/process',
			contentType: true,
			success: (response) => {
				var o = convertJSONString(response.responseText);
				console.log(o);
				if (o.success) {
					$('result').innerHTML = "";
					o.result.forEach((item) => {
						$('result').innerHTML += item.files[0] + " and ";
						$('result').innerHTML += item.files[1] + " | ";
						$('result').innerHTML += Math.floor(item.result*100) + "%<br/>";
					});
				} else {
					alert("Process failed!");
				}
			},
			error: (response) => {
				alert("Process failed!");
			}
		});
	}
	