
//이미지 프리뷰 기능
function previewImage() {
	// 파일리더 생성
	var preview = new FileReader();
	preview.onload = function (e) {
		// img id 값
		document.getElementById("preview_image").src = e.target.result;
	};
	// input id 값
	preview.readAsDataURL(document.getElementById("input_file").files[0]);
	document.getElementById("preview_image").style.display="";
};