let targetId;

$(document).ready(function () {
    alert("cookie")
    if ($.cookie('token')) {
        $.ajaxSetup({
            headers: {
                'Authorization': $.cookie('token')
            }
        })
    } else {
        window.location.href = '/user/loginView';
    }

    // id 가 query 인 녀석 위에서 엔터를 누르면 execSearch() 함수를 실행하라는 뜻입니다.
    $('#query').on('keypress', function (e) {
        if (e.key == 'Enter') {
            execSearch();
        }
    });
    $('#close').on('click', function () {
        $('#container').removeClass('active');
    })

    showArticles();
})

function showArticles() {
    // 1. GET /api/products 요청
    // 2. #product-container(관심상품 목록), #search-result-box(검색결과 목록) 비우기
    // 3. for 문 마다 addProductItem 함수 실행시키고 HTML 만들어서 #product-container 에 붙이기
    $.ajax({
        type: 'GET',
        url: '/api/articles',
        success: function (response) {
            $('#product-container').empty();
            for (let i = 0; i < response.length; i++) {
                let article = response[i];
                let tempHtml = addProductItem(article);
                $('#product-container').append(tempHtml);
            }
        }
    })
}

function addProductItem(article) {
    return `<div class="product-card" onclick="window.location.href='/api/articles/${article.id}'">
                <div class="card-header">
                </div>
                <div class="card-body">
                    <div class="title">
                        ${article.title}
                    </div>
                    <div class="lprice">
                        <span>${article.writer}</span>
                    </div>
                </div>
            </div>`;
}