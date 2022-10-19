const categorySelectInput = document.querySelector(".category-select .product-input");
const searchInput = document.querySelector(".product-search .product-input");
const searchButton = document.querySelector(".search-button");

class ProductListReqParams{
    constructor(page, category, searchValue) {
    this.page = page;
    this.category = category;
    this.searchValue = searchValue;
    }

    getPage() {
        return this.page;
    }
    setPage(page){
        this.page = page;
    }
    getCategory(){
        return this.category;
    }
    setCategory(category) {
        this.category = category;
    }
    getSearchValue() {
        return this.searchValue;
    }
    setSearchValue(searchValue) {
        this.searchValue = searchValue;
    }

    getProductListReqParams(){
        return {
            page: this.page,
            category: this.category,
            searchValue: this.searchValue
        };
    }
}

class ProductApi{

    productDataRequest(productListReqParams){
        let responseData = null;

        $.ajax({
            async: false,
            type: "get",
            url: "/api/admin/products",
            data: productListReqParams.getProductListReqParams(),
            dataType: "json",
            success: (response) => {
                responseData = response.data;
                console.log(response);
            },
            error: (error) => {
                console.log(error);
            }
        });

        return responseData;
    }
}

class ProductListService {

    constructor() {
        this.productApi = new ProductApi();
        this.topOptionService = new TopOptionService();
        this.productListReqParams = new ProductListReqParams(1, "ALL", "");
        this.loadProductList(this.productListReqParams);
    }

    loadProductList(productListReqParams){
        const responseData = this.productApi.productDataRequest(productListReqParams);
        if(this.isSuccessReqStatus(responseData)){
            if(responseData.length > 0){
                this.topOptionService.loadPageMovement(this, this.productListReqParams, responseData[0].productTotalCount);

            }
        }
    }

    isSuccessReqStatus(responseData){
        return responseData != null;
    }

}

class TopOptionService{
    constructor() {
        this.pageMovement = new PageMovement();
    }

    loadPageMovement(productListService, productListReqParams, productTotalCount){
        this.pageMovement.createMoveButtons(productListReqParams.getPage(), productTotalCount);
        this.pageMovement.addEvent(productListService, productListReqParams);
    }
}

class PageMovement {
    pageButtons = document.querySelector(".page-buttons");

    getEndPageNumber(productTotalCount){
        return (productTotalCount % 10 == 0) ? productTotalCount / 10 : Math.floor(productTotalCount / 10) + 1;
    }

    createMoveButtons(nowPage, productTotalCount){
        this.pageButtons.innerHTML = "";

       this.createPreButton(nowPage);
       this.createNumberButton(nowPage, productTotalCount);
       this.createPostButton(nowPage);
    }

    createNumberButton(nowPage, productTotalCount){
        let startIndex = nowPage % 5 == 0 ? nowPage - 4 : nowPage - (nowPage % 5) + 1;
        let endIndex = startIndex + 4 <= this.getEndPageNumber(productTotalCount) ? startIndex + 4 : this.getEndPageNumber(productTotalCount)(productTotalCount);


        for(let i = startIndex; i <= endIndex; i++) {
            if(i == nowPage) {
                this.pageButtons.innerHTML += `<a href="javascript:void(0)" class="a-selected"><li>${i}</li></a>`;
            }else {
                this.pageButtons.innerHTML += `<a href="javascript:void(0)"><li>${i}</li></a>`;
            }
        }
    }

    createPreButton(nowPage){
        if(nowPage != 1){
            this.pageButtons.innerHTML = `<a href="javascript:void(0)"><li>&#60;</li></a>`;
        }
    }
    createPostButton(nowPage, productTotalCount){
        let maxPage = this.getEndPageNumber(productTotalCount)
        if(nowPage != maxPage){
            this.pageButtons.innerHTML = `<a href="javascript:void(0)"><li>&#60;</li></a>`;
        }
    }

    addEvent(productListService,productListReqParams){
            const pageNumbers = this.pageButtons.querySelectorAll("li");

            for(let i = 0; i < pageNumbers.length; i++) {
                pageNumbers[i].onclick = () => {
                    let pageNumberText = pageNumbers[i].textContent;

                    if(pageNumberText == "<") {
                        productListReqParams.setPage(productListReqParams.getPage() - 1);
                    }else if(pageNumberText == ">") {
                        productListReqParams.setPage(productListReqParams.getPage() + 1);
                    }else {
                        productListReqParams.setPage(pageNumberText);
                    }

                    productListService.loadProductList(productListReqParams);
                }
            }

    }


}




let 상품리스트상단기능서비스 = {
    페이지이동버튼이벤트등록: function() {
        const pageNumbers = this.getPageButtonsObj().querySelectorAll("li");

        for(let i = 0; i < pageNumbers.length; i++) {
            pageNumbers[i].onclick = () => {
                let pageNumberText = pageNumbers[i].textContent;

                if(pageNumberText == "<") {
                    --상품리스트requestParams.page;
                }else if(pageNumberText == ">") {
                    ++상품리스트requestParams.page;
                }else {
                    상품리스트requestParams.page = pageNumberText;
                }

                상품리스트서비스.상품리스트불러오기();
            }
        }
    }
}


let 상품리스트서비스 = {
    상품리스트불러오기: function() {
        //responseData 에 값 들어오고
        const responseData = this.상품리스트데이터요청();
        if(this.상품리스트데이터요청성공확인(responseData)) {

            if(responseData.length > 0) { //
                상품리스트상단기능서비스.init(상품리스트requestParams.page, responseData[0].productTotalCount);
                상품리스트상단기능서비스.페이지이동버튼생성();
                상품리스트상단기능서비스.페이지이동버튼이벤트등록();
                //상품리스트 불러오기
                상품리스트목록.master상품정보생성(responseData)
            }else{
                alert("등록된 상품이 없습니다.");
                location.reload();
            }
        }
    },
    상품리스트데이터요청성공확인: (responseData) => responseData != null,///null 아니면 ture
    //요청은 이거밖에 없음
    상품리스트데이터요청: () => {
        let responseData = null;

        $.ajax({
            async: false,
            type: "get",
            url: "/api/admin/products",
            data: 상품리스트requestParams,
            dataType: "json",
            success: (response) => {
                responseData = response.data;
                console.log(response);
            },
            error: (error) => {
                console.log(error);
            }
        });

        return responseData;
    },


}


let productRepository = {
    상품관련리스트: new Array(),
    clear상품관련리스트: function() {
        this.상품관련리스트.forEach(list => {
            while(list.length != 0) {
                list.pop();//0이 될때까지 뒤에서 지움
            }
        });
    },
    push상품관련리스트: function(list) {
        this.상품관련리스트.push(list);
    }
}












let productDataList = null;
let productImgList = null;
let productFileImgList = new Array();
let productImageFiles = new Array();








categorySelectInput.onchange = () => {
    page = 1;
    category = categorySelectInput.value;
    getList();
}

searchInput.onkeyup = () => {
    if(window.event.keyCode == 13) {
        searchButton.click();
    }
}
searchButton.onclick = () => {
    page = 1;
    category = categorySelectInput.value;
    searchText = searchInput.value;
    getList();
}
const 상품리스트목록 ={
    master상품정보생성: function(responseData){
        const listBody = document.querySelector(".list-body");

        listBody.innerHTML = "";

        responseData.forEach((product) => {
            listBody.innerHTML += `
        <tr>
            <td class="product-id">${product.id}</td>
            <td>${product.category}</td>
            <td>${product.name}</td>
            <td>${product.price}<span>원</span></td>
            <td>${product.color}</td>
            <td>${product.size}</td>
            <td><button type="button" class="list-button detail-button"><i class="fa-regular fa-file-lines"></i></button></td>
            <td><button type="button" class="list-button delete-button"><i class="fa-regular fa-trash-can"></i></button></td>
        </tr>
        <tr class="product-detail detail-invisible">
            
        </tr>
        `;
        });
    }
}

                    //=response.data
function addProducts(productList) {


    const detailButtons = document.querySelectorAll(".detail-button");
    const productDetails = document.querySelectorAll(".product-detail");

    detailButtons.forEach((detailButton, index) => {
        detailButton.onclick = () => {

            if(productDetails[index].classList.contains("detail-invisible")) {
                let confirmationOfModification = false;
                let changeFlag = false;

                productDetails.forEach((productDetail, index2) => {
                    if(!productDetail.classList.contains("detail-invisible") && index2 != index){
                        confirmationOfModification = true;
                    }
                });

                productDetails.forEach((productDetail, index2) => {
                    if(!productDetail.classList.contains("detail-invisible") && index2 != index){
                        changeFlag = confirm("수정을 취소하시겠습니까?");
                        if(changeFlag) {
                            productDetail.classList.add("detail-invisible");
                            productDetail.innerHTML = "";
                            getProductDetail(productDetails[index], index);
                            productDetails[index].classList.remove("detail-invisible");
                        }
                    }else {
                        if(confirmationOfModification && changeFlag) {
                            getProductDetail(productDetails[index], index);
                            productDetails[index].classList.remove("detail-invisible");
                        }else if(!confirmationOfModification) {
                            getProductDetail(productDetails[index], index);
                            productDetails[index].classList.remove("detail-invisible");
                        }
                    }
                });

            }else{
                if(confirm("수정을 취소하시겠습니까?")){
                    productDetails[index].classList.add("detail-invisible");
                    productDetails[index].innerHTML = "";
                }
            }
        }
    });
}

function getProductDetail(productDetail, index) {
    productImgList = productDataList[index].productImgFiles;

    productDetail.innerHTML = `
    <td colspan="8">
        <table class="product-info">
            <tr>
                <td><input type="text" class="product-input" value="${productDataList[index].price}" placeholder="가격"></td>
                <td><input type="text" class="product-input" value="${productDataList[index].color}" placeholder="색상"></td>
                <td><input type="text" class="product-input" value="${productDataList[index].size}" placeholder="사이즈"></td>
            </tr>
            <tr>
                <td colspan="3">
                    <textarea class="product-input" placeholder="간략 설명">${productDataList[index].infoSimple}</textarea>
                </td>
            </tr>
            <tr>
                <td colspan="3">
                    <textarea class="product-input" placeholder="상세 설명">${productDataList[index].infoDetail}</textarea>
                </td>
            </tr>
            <tr>
                <td colspan="3">
                    <textarea class="product-input" placeholder="기타 설명">${productDataList[index].infoOption}</textarea>
                </td>
            </tr>
            <tr>
                <td colspan="3">
                    <textarea class="product-input" placeholder="관리 방법">${productDataList[index].infoManagement}</textarea>
                </td>
            </tr>
            <tr>
                <td colspan="3">
                    <textarea class="product-input" placeholder="배송 설명">${productDataList[index].infoShipping}</textarea>
                </td>
            </tr>
            <tr>
                <td colspan="3">
                    <form enctype="multipart/form-data">
                        <div class="product-img-inputs">
                            <label>상품 이미지</label>
                            <button type="button" class="add-button">추가</button>
                            <input type="file" class="file-input product-invisible" name="file" multiple>
                        </div>
                    </form>
                    <div class="product-images">

                    </div>
                </td>
            </tr>
            <tr>
                <td colspan="3">
                    <button type="button" class="black-button update-button">수정하기</button>
                </td>
            </tr>
        </table>
    </td>
    `;

    loadImageList();
    addImageFile();
}

function loadImageList() {
    const productImages = document.querySelector(".product-images");
    productImages.innerHTML = "";

    productImgList.forEach(img => {
        productImages.innerHTML += `
            <div class="img-box">
                <i class="fa-solid fa-xmark pre-delete"></i>
                <img class="product-img" src="/image/product/${img.temp_name}">
            </div>
        `;
    });

    productFileImgList.forEach((img) => {
        productImages.innerHTML += `
            <div class="img-box">
                <i class="fa-solid fa-xmark post-delete"></i>
                <img class="product-img" src="${img}">
            </div>
        `;
    });

    const preDeleteButton = document.querySelectorAll(".pre-delete");
    preDeleteButton.forEach((xbutton, index) => {
        xbutton.onclick = () => {
            if(confirm("상품 이미지를 지우시겠습니까?")) {
                productImgList.splice(index, 1);
                loadImageList()
            }
        };
    })

    const postDeleteButton = document.querySelectorAll(".post-delete");
    postDeleteButton.forEach((xbutton, index) => {
        xbutton.onclick = () => {
            if(confirm("상품 이미지를 지우시겠습니까?")) {
                productFileImgList.splice(index, 1);
                loadImageList()
            }
        };
    })
}

function addImageFile() {
    const fileAddButton = document.querySelector(".add-button");
    const fileInput = document.querySelector(".file-input");



    fileAddButton.onclick = () => {
        fileInput.click();
    }

    fileInput.onchange = () => {
        const formData = new FormData(document.querySelector("form"));
        let changeFlge = false;

        formData.forEach((value) => {
            if(value.size != 0) {
                productImageFiles.push(value);
                changeFlge = true;
            }
        });

        if(changeFlge){
            getImageFiles(productImageFiles);
            fileInput.value = null;
        }
    }
}

function getImageFiles(productImageFiles) {

    while(productFileImgList.length != 0) {
        productFileImgList.pop();
    }

    productImageFiles.forEach((file, i) => {
        const reader = new FileReader();

        reader.onload = (e) => {
            console.log("이미지 파일 하나를 리스트에 추가합니다.")

            productFileImgList.push(e.target.result);
            console.log("index: " + i);
            if(i == productImageFiles.length - 1) {
                console.log("마지막 인덱스일 때만 실행")
                loadImageList();
            }
        }

        setTimeout(() => {reader.readAsDataURL(file);}, i * 100);
    });

}



window.onload = () => {
    //생성만 땅 해주면 ~
    new ProductListService();
}
