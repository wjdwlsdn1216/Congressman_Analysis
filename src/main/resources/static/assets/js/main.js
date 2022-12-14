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
    //default_image 이미지 안보이게 하고 preview_image 보이게함
    document.getElementById("default_image").style.display = "none";
    document.getElementById("preview_image").style.display = "";

    //버튼 강조 애니메이션
    document.getElementById("file_submit").className = "btn btn-primary animate__animated animate__heartBeat animate__infinite";

};

//의원검색 - 입력할 때마다 실행하여 입력된값으로 시작되는 이름을 찾아서 뿌려줌
function search() {

    var data = $("#input-name").val();

    var searchDto = {
        name: data
    };

    $.ajax({
        url: "/search",
        data: searchDto,
        type: "POST",
    }).done(function (res) {
        console.log(res);
        // res.name
        // res.bthDate
        // res.polyNm
        $("#resultDiv a").remove();
        for (let con of res) {
            $("#resultDiv").append("<a class=\"name-btn\" href='/congressman/detail?id="+ con.id + "'>" + con.name + "</a>");
        }
    });
}

//의원검색 - 검색버튼 클릭시 에러처리
function goDetail() {
    var data = $("#input-name").val();

    if (data !== "") {
        $.ajax({
            url: "/congressman/detail",
            type: "GET",
            data: {name: data},
            success: function () {
                $("#input-name-form").submit();
            },
            error: function (err) {
                alert("존재하지 않는 의원입니다!");
            }
        })
    } else {
        alert("이름을 입력하세요!");
    }
}

//사진선택안하고 찾기 눌렀을때 예외처리
$(function() {
    $("#file_submit").click(function() {
        var data = $("#input_file").val();
        if (!data) {
            alert("사진을 선택해주세요!");
            return false;
        }
    })
});

//꽃가루 이벤트
function splashTickles() {
    $("#startButton").trigger("click");
    setTimeout(function () {
        $("#stopButton").trigger("click");
    }, 3000);
}


//로딩
$(document).ready(function () {
    $('#loading').hide();

    $('#next').submit(function () {
        $('#loading').show();
        return true;
    });
});





